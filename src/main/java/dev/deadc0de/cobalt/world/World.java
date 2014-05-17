package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.MovableView;
import dev.deadc0de.cobalt.graphics.MutableSprite;
import dev.deadc0de.cobalt.graphics.GraphicsStack;
import dev.deadc0de.cobalt.graphics.SpritesLayer;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.input.InputFocusStack;
import dev.deadc0de.cobalt.text.MenuEntry;
import dev.deadc0de.cobalt.text.MenuFacade;
import dev.deadc0de.cobalt.text.TextFacade;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.Supplier;

public class World implements ZoneChanger, Updatable {

    private final Map<String, Zone> zones;
    private final GraphicsStack.Frame graphics;
    private final InputFocusStack input;
    private final TextFacade textFacade;
    private final MutableSprite mainCharacterSprite;
    private final MainCharacterController mainCharacterController;
    private final Point viewRelativePosition;
    private final MovableView view;
    private Zone currentZone;
    private Updatable currentUpdatable;

    public World(TextFacade textFacade, SpritesRepository<?> spritesRepository, GraphicsStack.Frame graphics, InputFocusStack input, Point viewRelativePosition, MovableView view) {
        zones = new HashMap<>();
        this.graphics = graphics;
        this.input = input;
        this.textFacade = textFacade;
        mainCharacterSprite = new MutableSprite(null, null);
        mainCharacterController = new MainCharacterController(new MainCharacterElement(mainCharacterSprite::setState, this::onCharacterMoved), this::showPauseMenu);
        this.viewRelativePosition = viewRelativePosition;
        this.view = view;
        loadZones(textFacade, spritesRepository);
    }

    private void onCharacterMoved(int dx, int dy) {
        mainCharacterSprite.setPosition(mainCharacterSprite.position().add(new Point(dx, dy)));
        view.move(dx, dy);
    }

    private void showPauseMenu(Runnable onMenuClose) {
        textFacade.showMenu(onMenuClose, 7, new Point(56, 0), new Exit());
    }

    private void loadZones(TextFacade textFacade, SpritesRepository<?> spritesRepository) {
        final Iterable<ZoneFactory> zoneFactories = ServiceLoader.load(ZoneFactory.class);
        for (ZoneFactory zoneFactory : zoneFactories) {
            final Zone zone = zoneFactory.createZone(textFacade, this, spritesRepository);
            zones.put(zone.name, zone);
        }
    }

    @Override
    public void changeZone(String name, int row, int column, Point mainCharacterPosition) {
        if (currentZone != null) {
            popZone();
        }
        currentZone = zones.get(name);
        updatePosition(mainCharacterPosition);
        final Supplier<Set<ZoneInput>> activeInputProvider = input.pushFocus(ZoneInput.class, () -> EnumSet.noneOf(ZoneInput.class));
        mainCharacterController.changeEnvironment(activeInputProvider, currentZone.environment, row, column);
        graphics.pushSingleSourceLayer(currentZone.backgroundName);
        final SpritesLayer environmentSpritesLayer = graphics.pushSpritesLayer();
        environmentSpritesLayer.addSprites(currentZone.spritesSource.get().iterator());
        final SpritesLayer mainCharacterSpriteLayer = graphics.pushSpritesLayer();
        mainCharacterSpriteLayer.addSprite(mainCharacterSprite);
        final Zone zoneReference = currentZone;
        currentUpdatable = () -> {
            zoneReference.updatables.get().forEach(Runnable::run);
            mainCharacterController.update();
        };
    }

    private void popZone() {
        currentZone = null;
        input.popFocus();
        graphics.popLayer();
        graphics.popLayer();
        graphics.popLayer();
    }

    private void updatePosition(Point mainCharacterPosition) {
        mainCharacterSprite.setPosition(mainCharacterPosition);
        view.relocate(mainCharacterPosition.x + viewRelativePosition.x, mainCharacterPosition.y + viewRelativePosition.y);
    }

    @Override
    public void update() {
        currentUpdatable.update();
    }

    private static class Exit implements MenuEntry {

        @Override
        public String label() {
            return "EXIT";
        }

        @Override
        public void onSelected(MenuFacade menuFacade) {
            System.exit(0);
        }
    }
}

package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.MovableView;
import dev.deadc0de.cobalt.graphics.MutableSprite;
import dev.deadc0de.cobalt.graphics.OverlayingSpritesLayer;
import dev.deadc0de.cobalt.graphics.RenderingLayer;
import dev.deadc0de.cobalt.graphics.RenderingStack;
import dev.deadc0de.cobalt.input.InputFocusStack;
import dev.deadc0de.cobalt.text.TextFacade;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.image.Image;

public class World implements ZoneChanger, Updatable {

    private final Map<String, Zone> zones;
    private final RenderingStack graphics;
    private final InputFocusStack input;
    private final MutableSprite mainCharacterSprite;
    private final MainCharacterElement mainCharacterElement;
    private final Point viewRelativePosition;
    private final MovableView view;
    private Zone currentZone;
    private Updatable currentUpdatable;

    public World(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository, RenderingStack graphics, InputFocusStack input, Point viewRelativePosition, MovableView view) {
        zones = new HashMap<>();
        this.graphics = graphics;
        this.input = input;
        mainCharacterSprite = new MutableSprite(null, null);
        mainCharacterElement = new MainCharacterElement(mainCharacterSprite::setState, this::onCharacterMoved);
        this.viewRelativePosition = viewRelativePosition;
        this.view = view;
        loadZones(textFacade, imagesRepository, spritesRegionsRepository);
    }

    private void onCharacterMoved(int dx, int dy) {
        mainCharacterSprite.setPosition(mainCharacterSprite.position().add(new Point(dx, dy)));
        view.x += dx;
        view.y += dy;
    }

    private void loadZones(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository) {
        final Iterable<ZoneFactory> zoneFactories = ServiceLoader.load(ZoneFactory.class);
        for (ZoneFactory zoneFactory : zoneFactories) {
            final Zone zone = zoneFactory.createZone(textFacade, this, imagesRepository, spritesRegionsRepository);
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
        final MainCharacterController mainCharacterController = createMainCharacterController(currentZone.environment, row, column);
        final RenderingLayer layer = new OverlayingSpritesLayer(new ZoneRenderingLayer(currentZone), () -> Stream.of(mainCharacterSprite));
        graphics.pushLayer(layer, view);
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
    }

    private void updatePosition(Point mainCharacterPosition) {
        mainCharacterSprite.setPosition(mainCharacterPosition);
        view.x = mainCharacterPosition.x + viewRelativePosition.x;
        view.y = mainCharacterPosition.y + viewRelativePosition.y;
    }

    private MainCharacterController createMainCharacterController(ZoneEnvironment environment, int row, int column) {
        final Supplier<Set<ZoneInput>> activeInputProvider = input.pushFocus(ZoneInput.class, () -> EnumSet.noneOf(ZoneInput.class));
        final MainCharacterController controller = new MainCharacterController(mainCharacterElement, activeInputProvider, environment, row, column);
        return controller;
    }

    @Override
    public void update() {
        currentUpdatable.update();
    }
}

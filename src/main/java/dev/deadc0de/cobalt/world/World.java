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

public class World {

    private final Map<String, Zone> zones;
    private final RenderingStack graphics;
    private final InputFocusStack input;
    private final Point viewRelativePosition;
    private Zone currentZone;

    public World(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository, RenderingStack graphics, InputFocusStack input, Point viewRelativePosition) {
        zones = new HashMap<>();
        this.graphics = graphics;
        this.input = input;
        this.viewRelativePosition = viewRelativePosition;
        loadZones(textFacade, imagesRepository, spritesRegionsRepository);
    }

    private void loadZones(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository) {
        final Iterable<ZoneFactory> zoneFactories = ServiceLoader.load(ZoneFactory.class);
        for (ZoneFactory zoneFactory : zoneFactories) {
            final Zone zone = zoneFactory.createZone(textFacade, imagesRepository, spritesRegionsRepository);
            zones.put(zone.name, zone);
        }
    }

    public Updatable pushZone(String name, int row, int column, Point mainCharacterPosition, MovableView view) {
        if (currentZone != null) {
            popZone();
        }
        currentZone = zones.get(name);
        final MutableSprite mainCharacterSprite = createMainCharacterSprite(mainCharacterPosition, view);
        final MainCharacterController mainCharacterController = createMainCharacterController(mainCharacterSprite, currentZone.environment, row, column, view);
        final RenderingLayer layer = new OverlayingSpritesLayer(new ZoneRenderingLayer(currentZone), () -> Stream.of(mainCharacterSprite));
        graphics.pushLayer(layer, view);
        final Zone zoneReference = currentZone;
        return () -> {
            zoneReference.updatables.get().forEach(Runnable::run);
            mainCharacterController.update();
        };
    }

    private MutableSprite createMainCharacterSprite(Point mainCharacterPosition, MovableView view) {
        final MutableSprite mainCharacterSprite = new MutableSprite(null, mainCharacterPosition);
        view.x = mainCharacterPosition.x + viewRelativePosition.x;
        view.y = mainCharacterPosition.y + viewRelativePosition.y;
        return mainCharacterSprite;
    }

    private MainCharacterController createMainCharacterController(MutableSprite mainCharacterSprite, ZoneEnvironment environment, int row, int column, MovableView view) {
        final PositionTracker onCharacterMoved = (x, y) -> {
            mainCharacterSprite.setPosition(mainCharacterSprite.position().add(new Point(x, y)));
            view.x += x;
            view.y += y;
        };
        final MainCharacterElement mainCharacterElement = new MainCharacterElement(mainCharacterSprite::setState, onCharacterMoved);
        final Supplier<Set<ZoneInput>> activeInputProvider = input.pushFocus(ZoneInput.class, () -> EnumSet.noneOf(ZoneInput.class));
        final MainCharacterController controller = new MainCharacterController(mainCharacterElement, activeInputProvider, environment, row, column);
        return controller;
    }

    private void popZone() {
        currentZone = null;
        input.popFocus();
        graphics.popLayer();
    }

    public Zone currentZone() {
        if (currentZone == null) {
            throw new IllegalStateException("no zone is currently loaded");
        }
        return currentZone;
    }
}

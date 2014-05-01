package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.GraphicsFacade;
import dev.deadc0de.cobalt.graphics.View;
import dev.deadc0de.cobalt.text.TextFacade;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javafx.scene.image.Image;

public class World {

    private final Map<String, Zone> zones;
    private final GraphicsFacade graphics;
    private Zone currentZone;

    public World(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Function<String, Region>> spritesRegionsRepository, GraphicsFacade graphics) {
        zones = new HashMap<>();
        this.graphics = graphics;
        loadZones(textFacade, imagesRepository, spritesRegionsRepository);
    }

    private void loadZones(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Function<String, Region>> spritesRegionsRepository) {
        final Iterable<ZoneFactory> zoneFactories = ServiceLoader.load(ZoneFactory.class);
        for (ZoneFactory zoneFactory : zoneFactories) {
            final Zone zone = zoneFactory.createZone(textFacade, imagesRepository, spritesRegionsRepository);
            zones.put(zone.name, zone);
        }
    }

    public Runnable pushZone(String name, View view) {
        if (currentZone != null) {
            popZone();
        }
        currentZone = zones.get(name);
        final Runnable backgroundLayer = graphics.pushImageLayer(currentZone.backgroundName, view);
        final Runnable spritesLayer = graphics.pushSpritesLayer(currentZone.spritesGroup, currentZone.spritesSource, view);
        final Zone zoneReference = currentZone;
        return () -> {
            zoneReference.updatables.get().forEach(Runnable::run);
            backgroundLayer.run();
            spritesLayer.run();
        };
    }

    private void popZone() {
        currentZone = null;
        graphics.pop();
        graphics.pop();
    }

    public Zone currentZone() {
        if (currentZone == null) {
            throw new IllegalStateException("no zone is currently loaded");
        }
        return currentZone;
    }
}

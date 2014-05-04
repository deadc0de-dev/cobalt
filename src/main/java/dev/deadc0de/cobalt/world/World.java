package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.OverlayingSpritesLayer;
import dev.deadc0de.cobalt.graphics.RenderingLayer;
import dev.deadc0de.cobalt.graphics.RenderingStack;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.View;
import dev.deadc0de.cobalt.text.TextFacade;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;
import javafx.scene.image.Image;

public class World {

    private final Map<String, Zone> zones;
    private final RenderingStack graphics;
    private final Function<ZoneEnvironment, Sprite> mainCharacterSpriteFactory;
    private Zone currentZone;

    public World(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository, RenderingStack graphics, Function<ZoneEnvironment, Sprite> mainCharacterSpriteFactory) {
        zones = new HashMap<>();
        this.graphics = graphics;
        this.mainCharacterSpriteFactory = mainCharacterSpriteFactory;
        loadZones(textFacade, imagesRepository, spritesRegionsRepository);
    }

    private void loadZones(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository) {
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
        final Sprite mainCharacterSprite = mainCharacterSpriteFactory.apply(currentZone.environment);
        final RenderingLayer layer = new OverlayingSpritesLayer(new ZoneRenderingLayer(currentZone), () -> Stream.of(mainCharacterSprite));
        graphics.pushLayer(layer, view);
        final Zone zoneReference = currentZone;
        return () -> {
            zoneReference.updatables.get().forEach(Runnable::run);
        };
    }

    private void popZone() {
        currentZone = null;
        graphics.popLayer();
    }

    public Zone currentZone() {
        if (currentZone == null) {
            throw new IllegalStateException("no zone is currently loaded");
        }
        return currentZone;
    }
}

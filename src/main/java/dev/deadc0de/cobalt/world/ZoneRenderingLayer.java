package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.graphics.RenderingLayer;
import dev.deadc0de.cobalt.graphics.Sprite;
import java.util.stream.Stream;

public class ZoneRenderingLayer implements RenderingLayer {

    private final Zone zone;

    public ZoneRenderingLayer(Zone zone) {
        this.zone = zone;
    }

    @Override
    public String background() {
        return zone.backgroundName;
    }

    @Override
    public Stream<Sprite> sprites() {
        return zone.spritesSource.get();
    }
}

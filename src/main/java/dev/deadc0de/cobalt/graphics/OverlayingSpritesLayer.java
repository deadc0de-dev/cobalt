package dev.deadc0de.cobalt.graphics;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class OverlayingSpritesLayer implements RenderingLayer {

    private final RenderingLayer layer;
    private final Supplier<Stream<Sprite>> overlay;

    public OverlayingSpritesLayer(RenderingLayer layer, Supplier<Stream<Sprite>> overlay) {
        this.layer = layer;
        this.overlay = overlay;
    }

    @Override
    public String background() {
        return layer.background();
    }

    @Override
    public Stream<Sprite> sprites() {
        return Stream.concat(layer.sprites(), overlay.get());
    }
}

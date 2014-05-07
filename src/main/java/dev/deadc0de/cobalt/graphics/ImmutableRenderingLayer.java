package dev.deadc0de.cobalt.graphics;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ImmutableRenderingLayer implements RenderingLayer {

    private final String background;
    private final Supplier<Stream<Sprite>> sprites;

    public ImmutableRenderingLayer(String background, Supplier<Stream<Sprite>> sprites) {
        this.background = background;
        this.sprites = sprites;
    }

    @Override
    public String background() {
        return background;
    }

    @Override
    public Stream<Sprite> sprites() {
        return sprites.get();
    }
}

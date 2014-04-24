package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.View;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpritesRenderer implements Runnable {

    private final Image sprites;
    private final Function<String, Region> spritesRegions;
    private final Supplier<Stream<Sprite>> spritesPositions;
    private final View view;
    private final GraphicsContext graphics;

    public SpritesRenderer(Image sprites, Function<String, Region> spritesRegions, Supplier<Stream<Sprite>> spritesPositions, View view, GraphicsContext graphics) {
        this.sprites = sprites;
        this.spritesRegions = spritesRegions;
        this.spritesPositions = spritesPositions;
        this.view = view;
        this.graphics = graphics;
    }

    @Override
    public void run() {
        graphics.clearRect(0, 0, view.size.width, view.size.height);
        spritesPositions.get().forEach(sprite -> {
            final Region spriteRegion = spritesRegions.apply(sprite.state());
            final Point position = sprite.position();
            final int x = position.x - view.x;
            final int y = position.y - view.y;
            graphics.drawImage(sprites, spriteRegion.position.x, spriteRegion.position.y, spriteRegion.size.width, spriteRegion.size.height, x, y, spriteRegion.size.width, spriteRegion.size.height);
        });
    }
}

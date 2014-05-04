package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.View;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpritesRenderer implements Updatable {

    private final Function<String, Image> spritesImages;
    private final Function<String, Region> spritesRegions;
    private final Supplier<Stream<Sprite>> spritesPositions;
    private final View view;
    private final GraphicsContext graphics;

    public SpritesRenderer(Function<String, Image> spritesImages, Function<String, Region> spritesRegions, Supplier<Stream<Sprite>> spritesPositions, View view, GraphicsContext graphics) {
        this.spritesImages = spritesImages;
        this.spritesRegions = spritesRegions;
        this.spritesPositions = spritesPositions;
        this.view = view;
        this.graphics = graphics;
    }

    @Override
    public void update() {
        graphics.clearRect(0, 0, view.width(), view.height());
        spritesPositions.get().forEach(sprite -> {
            final Image image = spritesImages.apply(sprite.state());
            final Region spriteRegion = spritesRegions.apply(sprite.state());
            final Point position = sprite.position();
            final int x = position.x - view.x();
            final int y = position.y - view.y();
            graphics.drawImage(image, spriteRegion.position.x, spriteRegion.position.y, spriteRegion.size.width, spriteRegion.size.height, x, y, spriteRegion.size.width, spriteRegion.size.height);
        });
    }
}

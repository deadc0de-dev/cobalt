package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.View;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Pair;

public class SpritesRenderer implements Updatable {

    private final SpritesRepository spritesRepository;
    private final Supplier<Stream<Sprite>> spritesPositions;
    private final View view;
    private final GraphicsContext graphics;

    public SpritesRenderer(SpritesRepository spritesRepository, Supplier<Stream<Sprite>> spritesPositions, View view, GraphicsContext graphics) {
        this.spritesRepository = spritesRepository;
        this.spritesPositions = spritesPositions;
        this.view = view;
        this.graphics = graphics;
    }

    @Override
    public void update() {
        graphics.clearRect(0, 0, view.width(), view.height());
        spritesPositions.get().forEach(sprite -> {
            final Pair<Image, Region> spriteSource = spritesRepository.getSpriteSource(sprite.state());
            final Image image = spriteSource.getKey();
            final Region spriteRegion = spriteSource.getValue();
            final Point position = sprite.position();
            final int x = position.x - view.x();
            final int y = position.y - view.y();
            graphics.drawImage(image, spriteRegion.position.x, spriteRegion.position.y, spriteRegion.size.width, spriteRegion.size.height, x, y, spriteRegion.size.width, spriteRegion.size.height);
        });
    }
}

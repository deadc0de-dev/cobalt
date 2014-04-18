package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpritesRenderingLayer implements RenderingLayer {

    private final Image sprites;
    private final Map<String, Region> spritesRegions;
    private final Supplier<Stream<Sprite>> spritesPositions;

    public SpritesRenderingLayer(Image sprites, Map<String, Region> spritesRegions, Supplier<Stream<Sprite>> spritesPositions) {
        this.sprites = sprites;
        this.spritesRegions = spritesRegions;
        this.spritesPositions = spritesPositions;
    }

    @Override
    public void render(GraphicsContext graphics, Region region) {
        spritesPositions.get().forEach(sprite -> {
            final Region spriteRegion = spritesRegions.get(sprite.state());
            final Point position = sprite.position();
            final int x = position.x - region.position.x;
            final int y = position.y - region.position.y;
            graphics.drawImage(sprites, spriteRegion.position.x, spriteRegion.position.y, spriteRegion.size.width, spriteRegion.size.height, x, y, spriteRegion.size.width, spriteRegion.size.height);
        });
    }
}

package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SpritesRenderingLayer<K> implements RenderingLayer {

    private final Image sprites;
    private final Map<K, Region> spritesRegions;
    private final Supplier<Stream<Map.Entry<K, Point>>> spritesPositionsSupplier;

    public SpritesRenderingLayer(Image sprites, Map<K, Region> spritesRegions, Supplier<Stream<Map.Entry<K, Point>>> spritesPositionsSupplier) {
        this.sprites = sprites;
        this.spritesRegions = spritesRegions;
        this.spritesPositionsSupplier = spritesPositionsSupplier;
    }

    @Override
    public void render(GraphicsContext graphics, Region region) {
        final Stream<Map.Entry<Region, Point>> spritePositions = spritesPositionsSupplier.get().map(this::mapSpriteRegion);
        spritePositions.forEach(sprite -> {
            final Region spriteRegion = sprite.getKey();
            final Point position = sprite.getValue();
            final int x = position.x - region.position.x;
            final int y = position.y - region.position.y;
            graphics.drawImage(sprites, spriteRegion.position.x, spriteRegion.position.y, spriteRegion.size.width, spriteRegion.size.height, x, y, spriteRegion.size.width, spriteRegion.size.height);
        });
    }

    private Map.Entry<Region, Point> mapSpriteRegion(Map.Entry<K, Point> sprite) {
        return new AbstractMap.SimpleImmutableEntry<>(spritesRegions.get(sprite.getKey()), sprite.getValue());
    }
}

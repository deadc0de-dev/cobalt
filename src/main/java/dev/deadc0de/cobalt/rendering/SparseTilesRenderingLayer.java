package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SparseTilesRenderingLayer implements RenderingLayer {

    private final Image tiles;
    private final Map<Region, Point> tilePositions;

    public SparseTilesRenderingLayer(Image tiles, Map<Region, Point> tilePositions) {
        this.tiles = tiles;
        this.tilePositions = tilePositions;
    }

    @Override
    public void render(GraphicsContext graphics, Region region) {
        tilePositions.forEach((tile, position) -> {
            final int x = position.x - region.position.x;
            final int y = position.y - region.position.y;
            graphics.drawImage(tiles, tile.position.x, tile.position.y, tile.size.width, tile.size.height, x, y, tile.size.width, tile.size.height);
        });
    }
}

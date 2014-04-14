package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.grid.Grid;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TiledRenderingLayer implements RenderingLayer {

    private final Image tiles;
    private final Grid<Point> tilePositions;
    private final int tileSize;

    public TiledRenderingLayer(Image tiles, Grid<Point> tilePositions, int tileSize) {
        this.tiles = tiles;
        this.tilePositions = tilePositions;
        this.tileSize = tileSize;
    }

    @Override
    public void render(GraphicsContext graphics, Region region) {
        if (region.endPosition.x < 0 || region.endPosition.y < 0) {
            return;
        }
        final int startRow = region.position.y % tileSize;
        final int startColumn = region.position.x % tileSize;
        final int endRow = ((region.endPosition.y - 1) % tileSize) + 1;
        final int endColumn = ((region.endPosition.x - 1) % tileSize) + 1;
        for (int r = startRow, y = r * tileSize - region.position.y; r < endRow && r < tilePositions.rows(); r++, y += tileSize) {
            for (int c = startColumn, x = c * tileSize - region.position.x; c < endColumn && c < tilePositions.columns(); c++, x += tileSize) {
                final Point tilePosition = tilePositions.getAt(r, c);
                graphics.drawImage(tiles, tilePosition.x, tilePosition.y, tileSize, tileSize, x, y, tileSize, tileSize);
            }
        }
    }
}

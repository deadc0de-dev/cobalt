package dev.deadc0de.cobalt.geometry;

public class Region {

    public final Point position;
    public final Dimension size;
    public final Point endPosition;

    public Region(Point position, Dimension size) {
        this.endPosition = new Point(position.x + size.width, position.y + size.height);
        if (position.x > 0 && size.width > 0 && endPosition.x < 0) {
            throw new IllegalArgumentException("region max x overflows");
        }
        if (position.y > 0 && size.height > 0 && endPosition.y < 0) {
            throw new IllegalArgumentException("region max y overflows");
        }
        this.position = position;
        this.size = size;
    }

    public Region(int x, int y, int width, int height) {
        this(new Point(x, y), new Dimension(width, height));
    }
}

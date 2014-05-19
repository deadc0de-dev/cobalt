package dev.deadc0de.cobalt.geometry;

public class Point {

    private static final Point ZERO = new Point(0, 0);

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Point zero() {
        return ZERO;
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }
}

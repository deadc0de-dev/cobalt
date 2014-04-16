package dev.deadc0de.cobalt.geometry;

public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point point) {
        return new Point(x + point.x, y + point.y);
    }
}

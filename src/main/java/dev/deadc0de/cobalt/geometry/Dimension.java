package dev.deadc0de.cobalt.geometry;

public class Dimension {

    public final int width;
    public final int height;

    public Dimension(int width, int height) {
        if (width < 0) {
            throw new IllegalArgumentException("dimension width cannot be negative: " + width);
        }
        if (height < 0) {
            throw new IllegalArgumentException("dimension height cannot be negative: " + height);
        }
        this.width = width;
        this.height = height;
    }
}

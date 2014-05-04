package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Dimension;

public class MovableView implements View {

    public int x;
    public int y;
    private final Dimension size;

    public MovableView(int x, int y, Dimension size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public int width() {
        return size.width;
    }

    @Override
    public int height() {
        return size.height;
    }

    @Override
    public Dimension size() {
        return size;
    }
}

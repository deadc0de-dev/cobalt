package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Dimension;

public class FixedSizeView implements MovableView {

    private int x;
    private int y;
    private final Dimension size;

    public FixedSizeView(Dimension size, int x, int y) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public FixedSizeView(Dimension size) {
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

    @Override
    public void relocate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void relocateX(int x) {
        this.x = x;
    }

    @Override
    public void relocateY(int y) {
        this.y = y;
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void moveX(int dx) {
        x += dx;
    }

    @Override
    public void moveY(int dy) {
        y += dy;
    }
}

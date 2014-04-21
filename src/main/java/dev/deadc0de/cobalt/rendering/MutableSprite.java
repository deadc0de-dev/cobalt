package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;

public class MutableSprite implements Sprite {

    private String state;
    private Point position;

    public MutableSprite(String state, Point position) {
        this.state = state;
        this.position = position;
    }

    @Override
    public String state() {
        return state;
    }

    @Override
    public Point position() {
        return position;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}

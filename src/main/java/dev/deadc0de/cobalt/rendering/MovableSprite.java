package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Point;
import java.util.function.Supplier;

public class MovableSprite implements Sprite {

    private final Supplier<String> state;
    private final Supplier<Point> position;

    public MovableSprite(Supplier<String> state, Supplier<Point> position) {
        this.state = state;
        this.position = position;
    }

    @Override
    public String state() {
        return state.get();
    }

    @Override
    public Point position() {
        return position.get();
    }
}

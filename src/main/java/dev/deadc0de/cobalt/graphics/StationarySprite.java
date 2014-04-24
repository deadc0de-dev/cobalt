package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Point;
import java.util.function.Supplier;

public class StationarySprite implements Sprite {

    private final Supplier<String> state;
    private final Point position;

    public StationarySprite(Supplier<String> state, Point position) {
        this.state = state;
        this.position = position;
    }

    @Override
    public String state() {
        return state.get();
    }

    @Override
    public Point position() {
        return position;
    }
}

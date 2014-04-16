package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Point;

public interface Sprite<S> {

    static final Point STAND_STILL = new Point(0, 0);

    S state();

    default Point direction() {
        return STAND_STILL;
    }

    default void update() {
    }
}

package dev.deadc0de.cobalt.world;

public enum Direction {

    UP,
    DOWN,
    LEFT,
    RIGHT;

    public Direction opposite() {
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                throw new IllegalStateException("unknown direction: " + this);
        }
    }
}

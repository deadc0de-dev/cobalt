package dev.deadc0de.cobalt.world;

public class Cell {

    private static final Runnable NOOP = () -> {
    };

    public final String type;
    public final Runnable action;

    public Cell(String type) {
        this.type = type;
        this.action = NOOP;
    }

    public Cell(String type, Runnable action) {
        this.type = type;
        this.action = action;
    }
}

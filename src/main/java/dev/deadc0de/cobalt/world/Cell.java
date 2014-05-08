package dev.deadc0de.cobalt.world;

public interface Cell {

    boolean isTraversable();

    default void onSelected(Direction toward) {
    }

    default void beforeEnter(Direction toward) {
    }

    default void onEnter(Direction toward) {
    }

    default void beforeLeave(Direction toward) {
    }

    default void onLeave(Direction toward) {
    }

    static Cell traversable(boolean traversable) {
        return () -> traversable;
    }
}

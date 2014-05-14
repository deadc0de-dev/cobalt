package dev.deadc0de.cobalt.world;

public interface Cell {

    static final boolean INTERRUPT_ACTION = false;
    static final boolean CONTINUE_ACTION = true;

    boolean isTraversable();

    default boolean onSelected(Direction toward) {
        return CONTINUE_ACTION;
    }

    default boolean beforeEnter(Direction toward) {
        return CONTINUE_ACTION;
    }

    default void onEnter(Direction toward) {
    }

    default boolean beforeLeave(Direction toward) {
        return CONTINUE_ACTION;
    }

    default void onLeave(Direction toward) {
    }

    static Cell traversable(boolean traversable) {
        return () -> traversable;
    }
}

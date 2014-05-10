package dev.deadc0de.cobalt.graphics;

public interface MovableView extends View {

    void relocate(int x, int y);

    default void relocateX(int x) {
        relocate(x, y());
    }

    default void relocateY(int y) {
        relocate(x(), y);
    }

    void move(int dx, int dy);

    default void moveX(int dx) {
        move(dx, 0);
    }

    default void moveY(int dy) {
        move(0, dy);
    }
}

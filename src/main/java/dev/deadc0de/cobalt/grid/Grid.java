package dev.deadc0de.cobalt.grid;

public interface Grid<E> {

    void setAt(int row, int column, E element);

    E getAt(int row, int column);

    int rows();

    int columns();
}

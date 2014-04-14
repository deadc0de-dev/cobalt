package dev.deadc0de.cobalt.grid;

public class ArrayGrid<E> implements Grid<E> {

    private final int rows;
    private final int columns;
    private final E[] grid;

    public ArrayGrid(int rows, int columns) {
        if (rows < 0) {
            throw new IllegalArgumentException("grid rows count cannot be negative");
        }
        if (columns < 0) {
            throw new IllegalArgumentException("grid columns count cannot be negative");
        }
        this.rows = rows;
        this.columns = columns;
        this.grid = (E[]) new Object[rows * columns];
    }

    @Override
    public void setAt(int row, int column, E element) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("row out of grid bounds");
        }
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("column out of grid bounds");
        }
        grid[row * columns + column] = element;
    }

    @Override
    public E getAt(int row, int column) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("row out of grid bounds");
        }
        if (column < 0 || column >= columns) {
            throw new IllegalArgumentException("column out of grid bounds");
        }
        return grid[row * columns + column];
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public int columns() {
        return columns;
    }
}

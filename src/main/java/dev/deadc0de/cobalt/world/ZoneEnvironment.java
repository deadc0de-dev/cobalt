package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.grid.Grid;

public class ZoneEnvironment {

    private final Grid<Cell> environment;
    private final Cell defaultCell;

    public ZoneEnvironment(Grid<Cell> emnvironment, Cell defaultCell) {
        this.environment = emnvironment;
        this.defaultCell = defaultCell;
    }

    public Cell getCellAt(int row, int column) {
        if (row < 0 || column < 0 || row >= environment.rows() || column >= environment.columns()) {
            return defaultCell;
        }
        return environment.getAt(row, column);
    }
}

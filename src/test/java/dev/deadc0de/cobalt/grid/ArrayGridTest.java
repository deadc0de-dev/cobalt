package dev.deadc0de.cobalt.grid;

import org.junit.Assert;
import org.junit.Test;

public class ArrayGridTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotConstructGridWithNegativeRows() {
        final ArrayGrid<?> unused = new ArrayGrid<>(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canotConstructGridWithNegativeColumns() {
        final ArrayGrid<?> unused = new ArrayGrid<>(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetValueAtNegativeRow() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.setAt(-1, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetValueAtNegativeColumn() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.setAt(0, -1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetValueAtRowNotLessThanRowsCount() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.setAt(1, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetValueAtColumnNotLessThanColumnsCount() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.setAt(0, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotGetValueAtNegativeRow() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.getAt(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotGetValueAtNegativeColumn() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.getAt(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotGetValueAtRowNotLessThanRowsCount() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.getAt(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotGetValueAtColumnNotLessThanColumnsCount() {
        final ArrayGrid<?> grid = new ArrayGrid<>(1, 1);
        grid.getAt(0, 1);
    }

    @Test
    public void getYieldsTheElementSet() {
        final Object element = new Object();
        final ArrayGrid<Object> grid = new ArrayGrid<>(1, 1);
        grid.setAt(0, 0, element);
        Assert.assertEquals(element, grid.getAt(0, 0));
    }

    @Test
    public void rowsYieldsTheGridRowsCount() {
        final ArrayGrid<Object> grid = new ArrayGrid<>(1, 0);
        Assert.assertEquals(1, grid.rows());
    }

    @Test
    public void columnsYieldsTheGridColumnsCount() {
        final ArrayGrid<Object> grid = new ArrayGrid<>(0, 1);
        Assert.assertEquals(1, grid.columns());
    }
}

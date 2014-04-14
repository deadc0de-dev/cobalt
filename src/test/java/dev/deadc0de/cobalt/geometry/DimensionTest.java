package dev.deadc0de.cobalt.geometry;

import org.junit.Test;

public class DimensionTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotConstructDimensionWithNegativeWidth() {
        final Dimension unised = new Dimension(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotConstructDimensionWithNegativeHeight() {
        final Dimension unised = new Dimension(0, -1);
    }
}

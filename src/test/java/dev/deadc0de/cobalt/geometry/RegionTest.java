package dev.deadc0de.cobalt.geometry;

import org.junit.Assert;
import org.junit.Test;

public class RegionTest {

    @Test(expected = IllegalArgumentException.class)
    public void cannotConstructRegionWithOverflowingEndX() {
        final Point position = new Point(1, 0);
        final Dimension size = new Dimension(Integer.MAX_VALUE, 0);
        final Region unused = new Region(position, size);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotConstructRegionWithOverflowingEndY() {
        final Point position = new Point(0, 1);
        final Dimension size = new Dimension(0, Integer.MAX_VALUE);
        final Region unused = new Region(position, size);
    }

    @Test
    public void endPositionXIsTheSumOfPositionXAndWidth() {
        final Point position = new Point(1, 0);
        final Dimension size = new Dimension(2, 0);
        final Region region = new Region(position, size);
        Assert.assertEquals(3, region.endPosition.x);
    }

    @Test
    public void endPositionYIsTheSumOfPositionYAndHeight() {
        final Point position = new Point(0, 1);
        final Dimension size = new Dimension(0, 2);
        final Region region = new Region(position, size);
        Assert.assertEquals(3, region.endPosition.y);
    }
}

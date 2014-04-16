package dev.deadc0de.cobalt.geometry;

import org.junit.Assert;
import org.junit.Test;

public class PointTest {

    @Test
    public void theXValueOfTheSumOfTwoPointsIsTheSumOfTheXValues() {
        final Point point = new Point(1, 0);
        final Point other = new Point(2, 0);
        Assert.assertEquals(3, point.add(other).x);
    }

    @Test
    public void theYValueOfTheSumOfTwoPointsIsTheSumOfTheYValues() {
        final Point point = new Point(0, 1);
        final Point other = new Point(0, 2);
        Assert.assertEquals(3, point.add(other).y);
    }
}

package dev.deadc0de.cobalt.world;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CyclicStateElementTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyListThrows() {
        final CyclicStateElement unused = new CyclicStateElement(Collections.emptyList());
    }

    @Test
    public void spriteIsInitializedWithTheFirstState() {
        final String firstState = "state";
        final CyclicStateElement sprite = new CyclicStateElement(Arrays.asList(firstState));
        Assert.assertEquals(firstState, sprite.state());
    }

    @Test
    public void spriteWithOneStateCyclesOverIt() {
        final String state = "state";
        final CyclicStateElement sprite = new CyclicStateElement(Arrays.asList(state));
        sprite.update();
        Assert.assertEquals(state, sprite.state());
    }

    @Test
    public void spriteStateIsTheNextOneAfterAnUpdate() {
        final String firstState = "first-state";
        final String secondState = "second-state";
        final CyclicStateElement sprite = new CyclicStateElement(Arrays.asList(firstState, secondState));
        sprite.update();
        Assert.assertEquals(secondState, sprite.state());
    }

    @Test
    public void spriteStateIsTheFirstOneAfterAnEntireCycle() {
        final String firstState = "first-state";
        final String secondState = "second-state";
        final List<String> states = Arrays.asList(firstState, secondState);
        final CyclicStateElement sprite = new CyclicStateElement(states);
        states.forEach(state -> sprite.update());
        Assert.assertEquals(firstState, sprite.state());
    }
}

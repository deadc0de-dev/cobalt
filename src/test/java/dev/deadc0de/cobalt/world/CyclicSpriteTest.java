package dev.deadc0de.cobalt.world;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class CyclicSpriteTest {

    @Test(expected = IllegalArgumentException.class)
    public void constructorWithEmptyListThrows() {
        final CyclicSprite<?> unused = new CyclicSprite<>(Collections.emptyList());
    }

    @Test
    public void spriteIsInitializedWithTheFirstState() {
        final Object firstState = new Object();
        final CyclicSprite<?> sprite = new CyclicSprite<>(Arrays.asList(firstState));
        Assert.assertEquals(firstState, sprite.state());
    }

    @Test
    public void spriteWithOneStateCyclesOverIt() {
        final Object state = new Object();
        final CyclicSprite<?> sprite = new CyclicSprite<>(Arrays.asList(state));
        sprite.update();
        Assert.assertEquals(state, sprite.state());
    }

    @Test
    public void spriteStateIsTheNextOneAfterAnUpdate() {
        final Object firstState = new Object();
        final Object secondState = new Object();
        final CyclicSprite<?> sprite = new CyclicSprite<>(Arrays.asList(firstState, secondState));
        sprite.update();
        Assert.assertEquals(secondState, sprite.state());
    }

    @Test
    public void spriteStateIsTheFirstOneAfterAnEntireCycle() {
        final Object firstState = new Object();
        final Object secondState = new Object();
        final List<Object> states = Arrays.asList(firstState, secondState);
        final CyclicSprite<?> sprite = new CyclicSprite<>(states);
        states.forEach(state -> sprite.update());
        Assert.assertEquals(firstState, sprite.state());
    }
}

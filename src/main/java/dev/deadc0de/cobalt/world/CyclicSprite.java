package dev.deadc0de.cobalt.world;

import java.util.Iterator;
import java.util.List;

public class CyclicSprite<S> implements Sprite<S> {

    private final List<S> states;
    private Iterator<S> statesIterator;
    private S state;

    public CyclicSprite(List<S> states) {
        if (states.isEmpty()) {
            throw new IllegalArgumentException("cannot cycle over an empty list of states");
        }
        this.states = states;
        this.statesIterator = states.iterator();
        this.state = statesIterator.next();
    }

    @Override
    public S state() {
        return state;
    }

    @Override
    public void update() {
        if (!statesIterator.hasNext()) {
            statesIterator = states.iterator();
        }
        state = statesIterator.next();
    }
}

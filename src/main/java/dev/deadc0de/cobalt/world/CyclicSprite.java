package dev.deadc0de.cobalt.world;

import java.util.Iterator;

public class CyclicSprite<S> implements Sprite<S> {

    private final Iterable<S> states;
    private Iterator<S> statesIterator;
    private S state;

    public CyclicSprite(Iterable<S> states) {
        if (!states.iterator().hasNext()) {
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

package dev.deadc0de.cobalt.world;

import java.util.Iterator;

public class CyclicStateElement {

    private final Iterable<String> states;
    private Iterator<String> statesIterator;
    private String state;

    public CyclicStateElement(Iterable<String> states) {
        if (!states.iterator().hasNext()) {
            throw new IllegalArgumentException("cannot cycle over an empty list of states");
        }
        this.states = states;
        this.statesIterator = states.iterator();
        this.state = statesIterator.next();
    }

    public String state() {
        return state;
    }

    public void update() {
        if (!statesIterator.hasNext()) {
            statesIterator = states.iterator();
        }
        state = statesIterator.next();
    }
}

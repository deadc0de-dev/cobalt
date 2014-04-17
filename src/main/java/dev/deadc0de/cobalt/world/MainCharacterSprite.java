package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Input;
import dev.deadc0de.cobalt.geometry.Point;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainCharacterSprite implements Sprite<String> {

    private static final List<Map.Entry<String, Point>> UP = Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("character-up-right", new Point(0, -4)),
            new AbstractMap.SimpleImmutableEntry<>("character-up", new Point(0, -4)),
            new AbstractMap.SimpleImmutableEntry<>("character-up-left", new Point(0, -4)),
            new AbstractMap.SimpleImmutableEntry<>("character-up", new Point(0, -4))
    );
    private static final List<Map.Entry<String, Point>> DOWN = Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("character-down-right", new Point(0, 4)),
            new AbstractMap.SimpleImmutableEntry<>("character-down", new Point(0, 4)),
            new AbstractMap.SimpleImmutableEntry<>("character-down-left", new Point(0, 4)),
            new AbstractMap.SimpleImmutableEntry<>("character-down", new Point(0, 4))
    );
    private static final List<Map.Entry<String, Point>> LEFT = Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("character-left-right", new Point(-4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-left", new Point(-4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-left-left", new Point(-4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-left", new Point(-4, 0))
    );
    private static final List<Map.Entry<String, Point>> RIGHT = Arrays.asList(
            new AbstractMap.SimpleImmutableEntry<>("character-right-right", new Point(4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-right", new Point(4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-right-left", new Point(4, 0)),
            new AbstractMap.SimpleImmutableEntry<>("character-right", new Point(4, 0))
    );

    private final Input input;
    private Iterator<Map.Entry<String, Point>> currentStatesSequence;
    private String currentState;
    private Point currentDirection;

    public MainCharacterSprite(Input input) {
        this.input = input;
        this.currentStatesSequence = Collections.emptyIterator();
        this.currentState = "character-down";
        this.currentDirection = STAND_STILL;
    }

    @Override
    public String state() {
        return currentState;
    }

    @Override
    public Point direction() {
        return currentDirection;
    }

    @Override
    public void update() {
        if (currentStatesSequence.hasNext()) {
            final Map.Entry<String, Point> next = currentStatesSequence.next();
            currentState = next.getKey();
            currentDirection = next.getValue();
            return;
        }
        if (input.up()) {
            currentStatesSequence = UP.iterator();
            update();
            return;
        }
        if (input.down()) {
            currentStatesSequence = DOWN.iterator();
            update();
            return;
        }
        if (input.left()) {
            currentStatesSequence = LEFT.iterator();
            update();
            return;
        }
        if (input.right()) {
            currentStatesSequence = RIGHT.iterator();
            update();
            return;
        }
        currentDirection = STAND_STILL;
    }
}
package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.AnimationBuilder;
import dev.deadc0de.cobalt.geometry.Point;
import java.util.Collections;
import java.util.Iterator;

public class MainCharacterElement {

    private static final Point STAND_STILL = new Point(0, 0);
    private static final int SKIP = 3;

    private static final Iterable<Frame> UP = new AnimationBuilder<Frame>()
            .add(new Frame("character-up-right", new Point(0, -4)))
            .add(SKIP, new Frame("character-up-right", STAND_STILL))
            .add(new Frame("character-up", new Point(0, -4)))
            .add(SKIP, new Frame("character-up", STAND_STILL))
            .add(new Frame("character-up-left", new Point(0, -4)))
            .add(SKIP, new Frame("character-up-left", STAND_STILL))
            .add(new Frame("character-up", new Point(0, -4)))
            .add(SKIP, new Frame("character-up", STAND_STILL))
            .animation();

    private static final Iterable<Frame> DOWN = new AnimationBuilder<Frame>()
            .add(new Frame("character-down-right", new Point(0, 4)))
            .add(SKIP, new Frame("character-down-right", STAND_STILL))
            .add(new Frame("character-down", new Point(0, 4)))
            .add(SKIP, new Frame("character-down", STAND_STILL))
            .add(new Frame("character-down-left", new Point(0, 4)))
            .add(SKIP, new Frame("character-down-left", STAND_STILL))
            .add(new Frame("character-down", new Point(0, 4)))
            .add(SKIP, new Frame("character-down", STAND_STILL))
            .animation();

    private static final Iterable<Frame> LEFT = new AnimationBuilder<Frame>()
            .add(new Frame("character-left-right", new Point(-4, 0)))
            .add(SKIP, new Frame("character-left-right", STAND_STILL))
            .add(new Frame("character-left", new Point(-4, 0)))
            .add(SKIP, new Frame("character-left", STAND_STILL))
            .add(new Frame("character-left-left", new Point(-4, 0)))
            .add(SKIP, new Frame("character-left-left", STAND_STILL))
            .add(new Frame("character-left", new Point(-4, 0)))
            .add(SKIP, new Frame("character-left", STAND_STILL))
            .animation();

    private static final Iterable<Frame> RIGHT = new AnimationBuilder<Frame>()
            .add(new Frame("character-right-right", new Point(4, 0)))
            .add(SKIP, new Frame("character-right-right", STAND_STILL))
            .add(new Frame("character-right", new Point(4, 0)))
            .add(SKIP, new Frame("character-right", STAND_STILL))
            .add(new Frame("character-right-left", new Point(4, 0)))
            .add(SKIP, new Frame("character-right-left", STAND_STILL))
            .add(new Frame("character-right", new Point(4, 0)))
            .add(SKIP, new Frame("character-right", STAND_STILL))
            .animation();

    private Iterator<Frame> currentAnimation;
    private String currentState;
    private Point currentDirection;

    public MainCharacterElement() {
        this.currentAnimation = Collections.emptyIterator();
        this.currentState = "character-down";
        this.currentDirection = STAND_STILL;
    }

    public String state() {
        return currentState;
    }

    public Point direction() {
        return currentDirection;
    }

    public void update() {
        if (currentAnimation.hasNext()) {
            final Frame next = currentAnimation.next();
            currentState = next.state;
            currentDirection = next.direction;
        } else {
            currentDirection = STAND_STILL;
        }
    }

    public boolean isIdle() {
        return !currentAnimation.hasNext();
    }

    public void moveUp() {
        if (!currentAnimation.hasNext()) {
            currentAnimation = UP.iterator();
        }
    }

    public void moveDown() {
        if (!currentAnimation.hasNext()) {
            currentAnimation = DOWN.iterator();
        }
    }

    public void moveLeft() {
        if (!currentAnimation.hasNext()) {
            currentAnimation = LEFT.iterator();
        }
    }

    public void moveRight() {
        if (!currentAnimation.hasNext()) {
            currentAnimation = RIGHT.iterator();
        }
    }

    private static class Frame {

        public final String state;
        public final Point direction;

        public Frame(String state, Point direction) {
            this.state = state;
            this.direction = direction;
        }
    }
}

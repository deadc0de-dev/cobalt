package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.AnimationBuilder;
import dev.deadc0de.cobalt.geometry.Point;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class MainCharacterElement {

    private static final Point STAND_STILL = new Point(0, 0);
    private static final int SKIP = 3;
    private static final int HIT = 10;

    private static final Map<Direction, Iterable<Frame>> moveAnimations = new EnumMap<>(Direction.class);

    static {
        moveAnimations.put(Direction.UP, new AnimationBuilder<Frame>()
                .add(new Frame("character-up-right", new Point(0, -4)))
                .add(SKIP, new Frame("character-up-right", STAND_STILL))
                .add(new Frame("character-up", new Point(0, -4)))
                .add(SKIP, new Frame("character-up", STAND_STILL))
                .add(new Frame("character-up-left", new Point(0, -4)))
                .add(SKIP, new Frame("character-up-left", STAND_STILL))
                .add(new Frame("character-up", new Point(0, -4)))
                .add(SKIP, new Frame("character-up", STAND_STILL))
                .animation());
        moveAnimations.put(Direction.DOWN, new AnimationBuilder<Frame>()
                .add(new Frame("character-down-right", new Point(0, 4)))
                .add(SKIP, new Frame("character-down-right", STAND_STILL))
                .add(new Frame("character-down", new Point(0, 4)))
                .add(SKIP, new Frame("character-down", STAND_STILL))
                .add(new Frame("character-down-left", new Point(0, 4)))
                .add(SKIP, new Frame("character-down-left", STAND_STILL))
                .add(new Frame("character-down", new Point(0, 4)))
                .add(SKIP, new Frame("character-down", STAND_STILL))
                .animation());
        moveAnimations.put(Direction.LEFT, new AnimationBuilder<Frame>()
                .add(new Frame("character-left-right", new Point(-4, 0)))
                .add(SKIP, new Frame("character-left-right", STAND_STILL))
                .add(new Frame("character-left", new Point(-4, 0)))
                .add(SKIP, new Frame("character-left", STAND_STILL))
                .add(new Frame("character-left-left", new Point(-4, 0)))
                .add(SKIP, new Frame("character-left-left", STAND_STILL))
                .add(new Frame("character-left", new Point(-4, 0)))
                .add(SKIP, new Frame("character-left", STAND_STILL))
                .animation());
        moveAnimations.put(Direction.RIGHT, new AnimationBuilder<Frame>()
                .add(new Frame("character-right-right", new Point(4, 0)))
                .add(SKIP, new Frame("character-right-right", STAND_STILL))
                .add(new Frame("character-right", new Point(4, 0)))
                .add(SKIP, new Frame("character-right", STAND_STILL))
                .add(new Frame("character-right-left", new Point(4, 0)))
                .add(SKIP, new Frame("character-right-left", STAND_STILL))
                .add(new Frame("character-right", new Point(4, 0)))
                .add(SKIP, new Frame("character-right", STAND_STILL))
                .animation());
    }

    private static final Map<Direction, Iterable<Frame>> hitAnimations = new EnumMap<>(Direction.class);

    static {
        hitAnimations.put(Direction.UP, new AnimationBuilder<Frame>()
                .add(HIT, new Frame("character-up-right", STAND_STILL))
                .add(HIT, new Frame("character-up", STAND_STILL))
                .animation());
        hitAnimations.put(Direction.DOWN, new AnimationBuilder<Frame>()
                .add(HIT, new Frame("character-down-right", STAND_STILL))
                .add(HIT, new Frame("character-down", STAND_STILL))
                .animation());
        hitAnimations.put(Direction.LEFT, new AnimationBuilder<Frame>()
                .add(HIT, new Frame("character-left-right", STAND_STILL))
                .add(HIT, new Frame("character-left", STAND_STILL))
                .animation());
        hitAnimations.put(Direction.RIGHT, new AnimationBuilder<Frame>()
                .add(HIT, new Frame("character-right-right", STAND_STILL))
                .add(HIT, new Frame("character-right", STAND_STILL))
                .animation());
    }

    private final Consumer<String> stateTracker;
    private final PositionTracker positionTracker;
    private Iterator<Frame> currentAnimation;
    private Direction currentDirection;

    public MainCharacterElement(Consumer<String> stateTracker, PositionTracker positionTracker) {
        this.stateTracker = stateTracker;
        this.positionTracker = positionTracker;
        this.currentAnimation = Collections.emptyIterator();
        this.currentDirection = Direction.DOWN;
        this.stateTracker.accept("character-down");
    }

    public void update() {
        if (currentAnimation.hasNext()) {
            final Frame next = currentAnimation.next();
            stateTracker.accept(next.state);
            positionTracker.move(next.direction.x, next.direction.y);
        }
    }

    public boolean isIdle() {
        return !currentAnimation.hasNext();
    }

    public void move(Direction direction) {
        if (!currentAnimation.hasNext()) {
            currentAnimation = moveAnimations.get(direction).iterator();
            currentDirection = direction;
        }
    }

    public void hit(Direction direction) {
        if (!currentAnimation.hasNext()) {
            currentAnimation = hitAnimations.get(direction).iterator();
            currentDirection = direction;
        }
    }

    public Direction currentDirection() {
        return currentDirection;
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

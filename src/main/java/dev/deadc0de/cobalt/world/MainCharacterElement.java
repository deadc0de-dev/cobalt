package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.animation.AnimationBuilder;
import dev.deadc0de.cobalt.geometry.Point;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class MainCharacterElement {

    private static final int SKIP = 3;
    private static final int HIT = 9;

    private final Map<Direction, Iterable<Runnable>> moveAnimations;
    private final Map<Direction, Iterable<Runnable>> hitAnimations;
    private final Consumer<String> stateTracker;
    private final PositionTracker positionTracker;
    private Iterator<Runnable> currentAnimation;
    private Direction currentDirection;

    public MainCharacterElement(Consumer<String> stateTracker, PositionTracker positionTracker) {
        moveAnimations = moveAnimations();
        hitAnimations = hitAnimations();
        this.stateTracker = stateTracker;
        this.positionTracker = positionTracker;
        this.currentAnimation = Collections.emptyIterator();
        this.currentDirection = Direction.DOWN;
        this.stateTracker.accept("character-down");
    }

    public void update() {
        if (currentAnimation.hasNext()) {
            currentAnimation.next().run();
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

    private void updateStateAndMove(String newState, int dx, int dy) {
        stateTracker.accept(newState);
        positionTracker.move(dx, dy);
    }

    private Map<Direction, Iterable<Runnable>> moveAnimations() {
        final Map<Direction, Iterable<Runnable>> animations = new EnumMap<>(Direction.class);
        animations.put(Direction.UP, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-up-right", 0, -4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-up", 0, -4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-up-left", 0, -4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-up", 0, -4))
                .sleep(SKIP)
                .end());
        animations.put(Direction.DOWN, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-down-right", 0, 4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-down", 0, 4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-down-left", 0, 4))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-down", 0, 4))
                .sleep(SKIP)
                .end());
        animations.put(Direction.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-left-right", -4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-left", -4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-left-left", -4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-left", -4, 0))
                .sleep(SKIP)
                .end());
        animations.put(Direction.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-right-right", 4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-right", 4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-right-left", 4, 0))
                .sleep(SKIP)
                .run(character -> character.updateStateAndMove("character-right", 4, 0))
                .sleep(SKIP)
                .end());
        return animations;
    }

    private Map<Direction, Iterable<Runnable>> hitAnimations() {
        final Map<Direction, Iterable<Runnable>> animations = new EnumMap<>(Direction.class);
        animations.put(Direction.UP, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-up-right"))
                .sleep(HIT)
                .run(character -> character.stateTracker.accept("character-up"))
                .sleep(HIT)
                .end()
        );
        animations.put(Direction.DOWN, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-down-right"))
                .sleep(HIT)
                .run(character -> character.stateTracker.accept("character-down"))
                .sleep(HIT)
                .end()
        );
        animations.put(Direction.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-left-right"))
                .sleep(HIT)
                .run(character -> character.stateTracker.accept("character-left"))
                .sleep(HIT)
                .end()
        );
        animations.put(Direction.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-right-right"))
                .sleep(HIT)
                .run(character -> character.stateTracker.accept("character-right"))
                .sleep(HIT)
                .end()
        );
        return animations;
    }
}

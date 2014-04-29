package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.animation.AnimationBuilder;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class MainCharacterElement {

    private static final int STEP_LENGTH = 4;
    private static final int MOVE_DELAY = 2;

    private final Map<Direction, Iterable<Runnable>> turnAnimations;
    private final Map<Direction, Map<Step, Iterable<Runnable>>> moveAnimations;
    private final Map<Direction, Map<Step, Iterable<Runnable>>> hitAnimations;
    private final Consumer<String> stateTracker;
    private final PositionTracker positionTracker;
    private Iterator<Runnable> currentAnimation;
    private Action currentAction;
    private Direction currentDirection;
    private Step currentStep;

    public MainCharacterElement(Consumer<String> stateTracker, PositionTracker positionTracker) {
        turnAnimations = turnAnimations();
        moveAnimations = moveAnimations();
        hitAnimations = hitAnimations();
        this.stateTracker = stateTracker;
        this.positionTracker = positionTracker;
        currentAnimation = Collections.emptyIterator();
        currentAction = Action.NONE;
        currentDirection = Direction.DOWN;
        currentStep = Step.NONE;
        stateTracker.accept("character-down");
    }

    public void update() {
        if (currentAnimation.hasNext()) {
            currentAnimation.next().run();
        } else {
            currentAction = Action.NONE;
            currentStep = Step.NONE;
        }
    }

    public boolean isIdle() {
        return isInterruptibleAction() || !currentAnimation.hasNext();
    }

    private boolean isInterruptibleAction() {
        return currentAction == Action.NONE || currentAction == Action.HIT;
    }

    public void turn(Direction direction) {
        if (isInterruptibleAction() || !currentAnimation.hasNext()) {
            currentAction = Action.TURN;
            currentStep = Step.NONE;
            currentAnimation = turnAnimations.get(direction).iterator();
            currentDirection = direction;
        }
    }

    public void move(Direction direction) {
        if (isInterruptibleAction() || !currentAnimation.hasNext()) {
            currentAction = Action.MOVE;
            currentStep = currentStep == Step.RIGHT ? Step.LEFT : Step.RIGHT;
            currentAnimation = moveAnimations.get(direction).get(currentStep).iterator();
            currentDirection = direction;
        }
    }

    public void hit(Direction direction) {
        if (currentAction == Action.NONE || !currentAnimation.hasNext()) {
            currentAction = Action.HIT;
            currentStep = currentStep == Step.RIGHT ? Step.LEFT : Step.RIGHT;
            currentAnimation = hitAnimations.get(direction).get(currentStep).iterator();
            currentDirection = direction;
        }
    }

    public Direction currentDirection() {
        return currentDirection;
    }

    private void updateStateAndMove(String newState, int dx, int dy) {
        stateTracker.accept(newState);
        positionTracker.move(dx, dy);
    }

    private Map<Direction, Iterable<Runnable>> turnAnimations() {
        final Map<Direction, Iterable<Runnable>> animations = new EnumMap<>(Direction.class);
        animations.put(Direction.UP, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-up"))
                .end());
        animations.put(Direction.DOWN, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-down"))
                .end());
        animations.put(Direction.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-left"))
                .end());
        animations.put(Direction.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-right"))
                .end());
        return animations;
    }

    private Map<Direction, Map<Step, Iterable<Runnable>>> moveAnimations() {
        final Map<Direction, Map<Step, Iterable<Runnable>>> animations = new EnumMap<>(Direction.class);
        animations.put(Direction.UP, new EnumMap<>(Step.class));
        animations.put(Direction.DOWN, new EnumMap<>(Step.class));
        animations.put(Direction.LEFT, new EnumMap<>(Step.class));
        animations.put(Direction.RIGHT, new EnumMap<>(Step.class));
        animations.get(Direction.UP).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-up", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up-right", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up-right", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.UP).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-up", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up-left", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up-left", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-up", 0, -STEP_LENGTH)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.DOWN).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-down", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down-right", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down-right", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.DOWN).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-down", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down-left", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down-left", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-down", 0, STEP_LENGTH)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.LEFT).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left-right", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left-right", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.LEFT).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-left", -STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.RIGHT).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.RIGHT).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.updateStateAndMove("character-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right-left", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right-left", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .run(character -> character.updateStateAndMove("character-right", STEP_LENGTH, 0)).sleep(MOVE_DELAY)
                .end());
        return animations;
    }

    private Map<Direction, Map<Step, Iterable<Runnable>>> hitAnimations() {
        final Map<Direction, Map<Step, Iterable<Runnable>>> animations = new EnumMap<>(Direction.class);
        animations.put(Direction.UP, new EnumMap<>(Step.class));
        animations.put(Direction.DOWN, new EnumMap<>(Step.class));
        animations.put(Direction.LEFT, new EnumMap<>(Step.class));
        animations.put(Direction.RIGHT, new EnumMap<>(Step.class));
        animations.get(Direction.UP).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-up")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.UP).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-up")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-up")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.DOWN).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-down")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.DOWN).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-down")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-down")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.LEFT).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.LEFT).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-left")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.RIGHT).put(Step.RIGHT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right")).sleep(MOVE_DELAY)
                .end());
        animations.get(Direction.RIGHT).put(Step.LEFT, AnimationBuilder.startWith(this)
                .run(character -> character.stateTracker.accept("character-right")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right-left")).sleep(MOVE_DELAY)
                .run(character -> character.stateTracker.accept("character-right")).sleep(MOVE_DELAY)
                .end());
        return animations;
    }

    private static enum Step {

        LEFT,
        RIGHT,
        NONE;
    }

    private static enum Action {

        TURN,
        MOVE,
        HIT,
        NONE;
    }
}

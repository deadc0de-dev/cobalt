package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Updatable;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MainCharacterController implements Updatable {

    private final MainCharacterElement mainCharacter;
    private final Consumer<Runnable> onPaused;
    private Supplier<Set<ZoneInput>> input;
    private ZoneEnvironment environment;
    private int row;
    private int column;
    private Direction lastDirection;
    private boolean goingToPause;
    private boolean paused;

    public MainCharacterController(MainCharacterElement mainCharacter, Consumer<Runnable> onPaused) {
        this.mainCharacter = mainCharacter;
        this.onPaused = onPaused;
    }

    public void changeEnvironment(Supplier<Set<ZoneInput>> input, ZoneEnvironment environment, int row, int column) {
        this.input = input;
        this.environment = environment;
        this.row = row;
        this.column = column;
        goingToPause = false;
        paused = false;
    }

    @Override
    public void update() {
        if (paused) {
            return;
        }
        if (goingToPause && mainCharacter.isIdle()) {
            goingToPause = false;
            paused = true;
            onPaused.accept(this::resume);
            return;
        }
        final Set<ZoneInput> activeInput = input.get();
        if (activeInput.contains(ZoneInput.PAUSE)) {
            goingToPause = true;
        }
        if (mainCharacter.isIdle()) {
            applyInput(activeInput);
            mainCharacter.update();
            return;
        } else {
            mainCharacter.update();
        }
        final boolean mainCharacterBecameIdle = mainCharacter.isIdle();
        if (mainCharacterBecameIdle) {
            getNearCell(lastDirection.opposite()).onLeave(lastDirection);
            environment.getCellAt(row, column).onEnter(lastDirection);
        }
    }

    private void resume() {
        paused = false;
    }

    public void applyInput(Set<ZoneInput> activeInput) {
        if (activeInput.contains(ZoneInput.ACTION)) {
            if (!getNearCell(mainCharacter.currentDirection()).onSelected(mainCharacter.currentDirection())) {
                lastDirection = null;
                return;
            }
        }
        if (activeInput.contains(ZoneInput.UP)) {
            tryMove(Direction.UP, -1, 0);
        } else if (activeInput.contains(ZoneInput.DOWN)) {
            tryMove(Direction.DOWN, 1, 0);
        } else if (activeInput.contains(ZoneInput.LEFT)) {
            tryMove(Direction.LEFT, 0, -1);
        } else if (activeInput.contains(ZoneInput.RIGHT)) {
            tryMove(Direction.RIGHT, 0, 1);
        } else {
            lastDirection = null;
        }
    }

    private void tryMove(Direction direction, int rowDelta, int columnDelta) {
        if (lastDirection == null) {
            mainCharacter.turn(direction);
            lastDirection = direction;
            return;
        }
        if (!environment.getCellAt(row, column).beforeLeave(direction)) {
            lastDirection = null;
            return;
        }
        if (!getNearCell(direction).beforeEnter(direction)) {
            lastDirection = null;
            return;
        }
        if (getNearCell(direction).isTraversable()) {
            mainCharacter.move(direction);
            row += rowDelta;
            column += columnDelta;
        } else {
            mainCharacter.hit(direction);
        }
    }

    private Cell getNearCell(Direction direction) {
        switch (direction) {
            case UP:
                return environment.getCellAt(row - 1, column);
            case DOWN:
                return environment.getCellAt(row + 1, column);
            case LEFT:
                return environment.getCellAt(row, column - 1);
            case RIGHT:
                return environment.getCellAt(row, column + 1);
            default:
                throw new IllegalStateException("unknown direction");
        }
    }
}

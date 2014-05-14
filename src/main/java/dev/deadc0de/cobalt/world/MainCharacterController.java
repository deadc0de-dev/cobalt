package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Updatable;
import java.util.Set;
import java.util.function.Supplier;

public class MainCharacterController implements Updatable {

    private final MainCharacterElement mainCharacter;
    private final Supplier<Set<ZoneInput>> input;
    private final ZoneEnvironment environment;
    private int row;
    private int column;
    private Direction lastDirection;

    public MainCharacterController(MainCharacterElement mainCharacter, Supplier<Set<ZoneInput>> input, ZoneEnvironment environment, int initialRow, int initialColumn) {
        this.mainCharacter = mainCharacter;
        this.input = input;
        this.environment = environment;
        this.row = initialRow;
        this.column = initialColumn;
    }

    @Override
    public void update() {
        if (mainCharacter.isIdle()) {
            applyInput(input.get());
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

    public void applyInput(Set<ZoneInput> activeInput) {
        if (activeInput.contains(ZoneInput.ACTION)) {
            if (!getNearCell(mainCharacter.currentDirection()).onSelected(mainCharacter.currentDirection())) {
                lastDirection = null;
                return;
            }
        }
        if (activeInput.contains(ZoneInput.UP)) {
            tryMove(Direction.UP, row - 1, column);
        } else if (activeInput.contains(ZoneInput.DOWN)) {
            tryMove(Direction.DOWN, row + 1, column);
        } else if (activeInput.contains(ZoneInput.LEFT)) {
            tryMove(Direction.LEFT, row, column - 1);
        } else if (activeInput.contains(ZoneInput.RIGHT)) {
            tryMove(Direction.RIGHT, row, column + 1);
        } else {
            lastDirection = null;
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

    private void tryMove(Direction direction, int targetRow, int targetColumn) {
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
        if (environment.getCellAt(targetRow, targetColumn).isTraversable()) {
            mainCharacter.move(direction);
            row = targetRow;
            column = targetColumn;
        } else {
            mainCharacter.hit(direction);
        }
    }
}

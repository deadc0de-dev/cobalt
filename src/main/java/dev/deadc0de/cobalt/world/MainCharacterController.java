package dev.deadc0de.cobalt.world;

import java.util.Set;
import java.util.function.Supplier;

public class MainCharacterController {

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

    public void update() {
        if (mainCharacter.isIdle()) {
            final Set<ZoneInput> activeInput = input.get();
            if (activeInput.contains(ZoneInput.ACTION)) {
                getNearCell(mainCharacter.currentDirection()).action.run();
                lastDirection = null;
            } else if (activeInput.contains(ZoneInput.UP)) {
                tryMove(Direction.UP, row - 1, column);
                lastDirection = Direction.UP;
            } else if (activeInput.contains(ZoneInput.DOWN)) {
                tryMove(Direction.DOWN, row + 1, column);
                lastDirection = Direction.DOWN;
            } else if (activeInput.contains(ZoneInput.LEFT)) {
                tryMove(Direction.LEFT, row, column - 1);
                lastDirection = Direction.LEFT;
            } else if (activeInput.contains(ZoneInput.RIGHT)) {
                tryMove(Direction.RIGHT, row, column + 1);
                lastDirection = Direction.RIGHT;
            } else {
                lastDirection = null;
            }
        }
        mainCharacter.update();
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
            return;
        }
        if (environment.getCellAt(targetRow, targetColumn).type.equals("ground")) {
            mainCharacter.move(direction);
            row = targetRow;
            column = targetColumn;
        } else {
            mainCharacter.hit(direction);
        }
    }
}

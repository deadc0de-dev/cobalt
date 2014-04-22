package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Input;

public class MainCharacterController {

    private final MainCharacterElement mainCharacter;
    private final Input input;
    private final ZoneEnvironment environment;
    private int row;
    private int column;

    public MainCharacterController(MainCharacterElement mainCharacter, Input input, ZoneEnvironment environment, int initialRow, int initialColumn) {
        this.mainCharacter = mainCharacter;
        this.input = input;
        this.environment = environment;
        this.row = initialRow;
        this.column = initialColumn;
    }

    public void update() {
        if (mainCharacter.isIdle()) {
            if (input.action()) {
                getNearCell(mainCharacter.currentDirection()).action.run();
            } else if (input.up()) {
                tryMove(Direction.UP, row - 1, column);
            } else if (input.down()) {
                tryMove(Direction.DOWN, row + 1, column);
            } else if (input.left()) {
                tryMove(Direction.LEFT, row, column - 1);
            } else if (input.right()) {
                tryMove(Direction.RIGHT, row, column + 1);
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
        if (environment.getCellAt(targetRow, targetColumn).type.equals("ground")) {
            mainCharacter.move(direction);
            row = targetRow;
            column = targetColumn;
        } else {
            mainCharacter.hit(direction);
        }
    }
}

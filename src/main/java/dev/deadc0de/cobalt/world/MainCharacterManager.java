package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Input;

public class MainCharacterManager {

    private final MainCharacterElement mainCharacter;
    private final Input input;
    private final ZoneEnvironment environment;
    private int row;
    private int column;

    public MainCharacterManager(MainCharacterElement mainCharacter, Input input, ZoneEnvironment environment, int initialRow, int initialColumn) {
        this.mainCharacter = mainCharacter;
        this.input = input;
        this.environment = environment;
        this.row = initialRow;
        this.column = initialColumn;
    }

    public void update() {
        if (mainCharacter.isIdle()) {
            if (input.up()) {
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

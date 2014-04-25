package dev.deadc0de.cobalt.world;

import java.util.Set;
import java.util.function.Supplier;

public class MainCharacterController {

    private final MainCharacterElement mainCharacter;
    private final Supplier<Set<ZoneInput>> input;
    private final ZoneEnvironment environment;
    private int row;
    private int column;

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
            } else if (activeInput.contains(ZoneInput.UP)) {
                tryMove(Direction.UP, row - 1, column);
            } else if (activeInput.contains(ZoneInput.DOWN)) {
                tryMove(Direction.DOWN, row + 1, column);
            } else if (activeInput.contains(ZoneInput.LEFT)) {
                tryMove(Direction.LEFT, row, column - 1);
            } else if (activeInput.contains(ZoneInput.RIGHT)) {
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

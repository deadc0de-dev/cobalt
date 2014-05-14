package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Updatable;
import java.util.Set;
import java.util.function.Supplier;

public class MainCharacterController implements Updatable {

    private final MainCharacterElement mainCharacter;
    private Supplier<Set<ZoneInput>> input;
    private ZoneEnvironment environment;
    private int row;
    private int column;
    private Direction lastDirection;

    public MainCharacterController(MainCharacterElement mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void changeEnvironment(Supplier<Set<ZoneInput>> input, ZoneEnvironment environment, int row, int column) {
        this.input = input;
        this.environment = environment;
        this.row = row;
        this.column = column;
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

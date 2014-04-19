package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Input;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.rendering.View;

public class MainCharacterManager {

    private final MainCharacterElement mainCharacter;
    private final Input input;
    private final View followingView;
    private final Point viewRelativePosition;
    private final ZoneEnvironment environment;
    private int column;
    private int row;
    private Point absolutePosition;

    public MainCharacterManager(MainCharacterElement mainCharacter, ZoneEnvironment environment, Point initialCell, Point initialPosition, Input input, View followingView, Point viewRelativePosition) {
        this.mainCharacter = mainCharacter;
        this.environment = environment;
        this.input = input;
        this.followingView = followingView;
        this.viewRelativePosition = viewRelativePosition;
        this.column = initialCell.x;
        this.row = initialCell.y;
        this.absolutePosition = initialPosition;
        alignView();
    }

    private void alignView() {
        followingView.x = absolutePosition.x + viewRelativePosition.x;
        followingView.y = absolutePosition.y + viewRelativePosition.y;
    }

    public void update() {
        if (mainCharacter.isIdle()) {
            if (input.up()) {
                if (mainCharacter.moveUp(environment.getCellAt(row - 1, column))) {
                    row--;
                }
            } else if (input.down()) {
                if (mainCharacter.moveDown(environment.getCellAt(row + 1, column))) {
                    row++;
                }
            } else if (input.left()) {
                if (mainCharacter.moveLeft(environment.getCellAt(row, column - 1))) {
                    column--;
                }
            } else if (input.right()) {
                if (mainCharacter.moveRight(environment.getCellAt(row, column + 1))) {
                    column++;
                }
            }
        }
        mainCharacter.update();
        absolutePosition = absolutePosition.add(mainCharacter.direction());
        alignView();
    }

    public String state() {
        return mainCharacter.state();
    }

    public Point position() {
        return absolutePosition;
    }
}

package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.Input;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.rendering.View;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

public class MainCharacterEnviroment {

    private final MainCharacterSprite mainCharacter;
    private final Input input;
    private final View followingView;
    private final Point viewRelativePosition;
    private Point position;

    public MainCharacterEnviroment(MainCharacterSprite mainCharacter, Point initialPosition, Input input, View followingView, Point viewRelativePosition) {
        this.mainCharacter = mainCharacter;
        this.input = input;
        this.followingView = followingView;
        this.viewRelativePosition = viewRelativePosition;
        this.position = initialPosition;
        alignView();
    }

    private void alignView() {
        followingView.x = position.x + viewRelativePosition.x;
        followingView.y = position.y + viewRelativePosition.y;
    }

    public void update() {
        if (mainCharacter.isIdle()) {
            if (input.up()) {
                mainCharacter.moveUp();
            } else if (input.down()) {
                mainCharacter.moveDown();
            } else if (input.left()) {
                mainCharacter.moveLeft();
            } else if (input.right()) {
                mainCharacter.moveRight();
            }
        }
        mainCharacter.update();
        position = position.add(mainCharacter.direction());
        alignView();
    }

    public Stream<Map.Entry<String, Point>> getStateAndPosition() {
        return Stream.of(new AbstractMap.SimpleImmutableEntry<>(mainCharacter.state(), position));
    }
}

package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Point;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

public class SpritesEnvironment<S> {

    private final Map<Sprite<S>, Point> sprites;

    public SpritesEnvironment(Map<Sprite<S>, Point> sprites) {
        this.sprites = sprites;
    }

    public void update() {
        sprites.replaceAll((sprite, position) -> {
            sprite.update();
            return position.add(sprite.direction());
        });
    }

    public Stream<Map.Entry<S, Point>> getStatesAndPositions() {
        return sprites.entrySet().stream().map(SpritesEnvironment::mapSpriteState);
    }

    private static <S> Map.Entry<S, Point> mapSpriteState(Map.Entry<Sprite<S>, Point> sprite) {
        return new AbstractMap.SimpleImmutableEntry<>(sprite.getKey().state(), sprite.getValue());
    }
}

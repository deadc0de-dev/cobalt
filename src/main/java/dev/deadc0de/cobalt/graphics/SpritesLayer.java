package dev.deadc0de.cobalt.graphics;

import java.util.Arrays;
import java.util.Iterator;

public interface SpritesLayer {

    default void addSprite(Sprite sprite) {
        addSprites(sprite);
    }

    default void addSprites(Sprite... sprites) {
        addSprites(Arrays.asList(sprites));
    }

    default void addSprites(Iterable<Sprite> sprites) {
        addSprites(sprites.iterator());
    }

    void addSprites(Iterator<Sprite> sprites);

    void removeSprite(Sprite sprite);

    void removeAll();
}

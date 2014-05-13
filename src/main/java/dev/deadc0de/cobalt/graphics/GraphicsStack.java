package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Point;

public interface GraphicsStack {

    Frame pushFrame(View view);

    void popFrame();

    static interface Frame {

        void pushSingleSourceLayer(String sourceName, Point position);

        default void pushSingleSourceLayer(String sourceName) {
            pushSingleSourceLayer(sourceName, Point.atOrigin());
        }

        SpritesLayer pushSpritesLayer();

        void popLayer();
    }
}

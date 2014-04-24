package dev.deadc0de.cobalt.graphics;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface GraphicsFacade {

    Runnable pushImageLayer(String name, View view);

    Runnable pushSpritesLayer(String groupName, Supplier<Stream<Sprite>> spritesSource, View view);

    void pop();
}

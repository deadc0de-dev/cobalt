package dev.deadc0de.cobalt.graphics;

import java.util.stream.Stream;

public interface RenderingLayer {

    String background();

    Stream<Sprite> sprites();
}

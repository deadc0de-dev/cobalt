package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Region;

public interface SpritesRepository<S> {

    S getSource(String name);

    Region getRegion(String spriteName);

    void addSource(String sourceName, String sourceDescription);

    void addSprite(String spriteName, String sourceName, Region region);
}

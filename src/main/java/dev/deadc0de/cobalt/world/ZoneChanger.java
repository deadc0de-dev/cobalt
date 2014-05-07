package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Point;

public interface ZoneChanger {

    void changeZone(String name, int row, int column, Point mainCharacterPosition);
}

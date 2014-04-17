package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;

public class View {

    public int x;
    public int y;
    public final Dimension size;

    public View(Dimension size) {
        this.size = size;
    }

    public Point position() {
        return new Point(x, y);
    }

    public Region region() {
        return new Region(position(), size);
    }
}

package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;

public interface View {

    int x();

    int y();

    int width();

    int height();

    default Point position() {
        return new Point(x(), y());
    }

    default Dimension size() {
        return new Dimension(width(), height());
    }

    default Region region() {
        return new Region(position(), size());
    }
}

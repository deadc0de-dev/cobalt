package dev.deadc0de.cobalt.graphics;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;

public class ImmutableView implements View {

    private final Region region;

    public ImmutableView(Region region) {
        this.region = region;
    }

    @Override
    public int x() {
        return region.position.x;
    }

    @Override
    public int y() {
        return region.position.y;
    }

    @Override
    public int width() {
        return region.size.width;
    }

    @Override
    public int height() {
        return region.size.height;
    }

    @Override
    public Dimension size() {
        return region.size;
    }

    @Override
    public Point position() {
        return region.position;
    }

    @Override
    public Region region() {
        return region;
    }
}

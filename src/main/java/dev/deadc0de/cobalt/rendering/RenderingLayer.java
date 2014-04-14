package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Region;
import javafx.scene.canvas.GraphicsContext;

public interface RenderingLayer {

    void render(GraphicsContext graphics, Region region);
}

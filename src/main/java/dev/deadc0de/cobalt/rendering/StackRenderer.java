package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Region;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;

public class StackRenderer {

    private final List<RenderingLayer> layerStack;
    private final GraphicsContext graphics;
    private final View view;

    public StackRenderer(List<RenderingLayer> layerStack, GraphicsContext graphics, View view) {
        this.layerStack = layerStack;
        this.graphics = graphics;
        this.view = view;
    }

    public void render() {
        final Region region = new Region(view.position(), view.size);
        layerStack.forEach(layer -> layer.render(graphics, region));
    }
}

package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Region;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageRenderingLayer implements RenderingLayer {

    private final Image background;

    public ImageRenderingLayer(Image background) {
        this.background = background;
    }

    @Override
    public void render(GraphicsContext graphics, Region region) {
        graphics.drawImage(background, region.position.x, region.position.y, region.size.width, region.size.height, 0, 0, region.size.width, region.size.height);
    }
}

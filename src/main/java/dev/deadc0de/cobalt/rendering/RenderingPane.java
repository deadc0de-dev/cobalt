package dev.deadc0de.cobalt.rendering;

import dev.deadc0de.cobalt.geometry.Region;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class RenderingPane extends StackPane {

    private final ImageView background;
    private final Supplier<Stream<RenderingLayer>> renderingStack;
    private final View view;
    private final GraphicsContext graphics;

    public RenderingPane(Image background, Supplier<Stream<RenderingLayer>> renderingStack, View view) {
        this.background = new ImageView(background);
        this.renderingStack = renderingStack;
        this.view = view;
        this.background.setViewport(viewport());
        final Canvas foreground = new Canvas(view.size.width, view.size.height);
        this.graphics = foreground.getGraphicsContext2D();
        this.getChildren().addAll(this.background, foreground);
    }

    private Rectangle2D viewport() {
        return new Rectangle2D(view.x, view.y, view.size.width, view.size.height);
    }

    public void update() {
        background.setViewport(viewport());
        graphics.clearRect(0, 0, view.size.width, view.size.height);
        final Region region = view.region();
        renderingStack.get().forEach(layer -> layer.render(graphics, region));
    }
}

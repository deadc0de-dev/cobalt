package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.rendering.ImageRenderingLayer;
import dev.deadc0de.cobalt.rendering.RenderingLayer;
import dev.deadc0de.cobalt.rendering.StackRenderer;
import dev.deadc0de.cobalt.rendering.View;
import java.util.Arrays;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 9;
    private static final int TILE_SIZE = 16;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root());
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        stage.show();
    }

    private Parent root() {
        final Dimension renderingArea = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        final Canvas canvas = new Canvas(renderingArea.width, renderingArea.height);
        final View view = new View(renderingArea);
        final StackRenderer stackRenderer = new StackRenderer(layers(), canvas.getGraphicsContext2D(), view);
        startRendering(stackRenderer::render);
        return new StackPane(canvas);
    }

    private List<RenderingLayer> layers() {
        final Image background = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/background.png"));
        return Arrays.asList(new ImageRenderingLayer(background));
    }

    private void startRendering(Runnable renderer) {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> renderer.run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

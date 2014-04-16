package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.rendering.ImageRenderingLayer;
import dev.deadc0de.cobalt.rendering.RenderingLayer;
import dev.deadc0de.cobalt.rendering.SpritesRenderingLayer;
import dev.deadc0de.cobalt.rendering.StackRenderer;
import dev.deadc0de.cobalt.rendering.View;
import dev.deadc0de.cobalt.world.Sprite;
import dev.deadc0de.cobalt.world.SpritesEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private final List<Runnable> updateHandlers = new ArrayList<>();

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root());
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        startRendering();
        stage.show();
    }

    private Parent root() {
        final Dimension renderingArea = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        final Canvas canvas = new Canvas(renderingArea.width, renderingArea.height);
        final View view = new View(renderingArea);
        final StackRenderer stackRenderer = new StackRenderer(layers(), canvas.getGraphicsContext2D(), view);
        updateHandlers.add(stackRenderer::render);
        return new StackPane(canvas);
    }

    private List<RenderingLayer> layers() {
        final RenderingLayer background = new ImageRenderingLayer(new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/background.png")));
        final SpritesEnvironment<String> environment = environment();
        final RenderingLayer spritesLayer = new SpritesRenderingLayer<>(Sprites.SPRITES, Sprites.spritesRegions(), environment::getStatesAndPositions);
        return Arrays.asList(background, spritesLayer);
    }

    private SpritesEnvironment<String> environment() {
        final SpritesEnvironment<String> environment = new SpritesEnvironment<>(sprites());
        updateHandlers.add(environment::update);
        return environment;
    }

    private Map<Sprite<String>, Point> sprites() {
        final Map<Sprite<String>, Point> sprites = new HashMap<>();
        sprites.put(Sprites.signboard(), new Point(2 * TILE_SIZE, 4 * TILE_SIZE));
        sprites.put(Sprites.flowers(), new Point(1 * TILE_SIZE, 6 * TILE_SIZE));
        return sprites;
    }

    private void startRendering() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), event -> updateHandlers.forEach(Runnable::run)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.rendering.RenderingLayer;
import dev.deadc0de.cobalt.rendering.RenderingPane;
import dev.deadc0de.cobalt.rendering.SpritesRenderingLayer;
import dev.deadc0de.cobalt.rendering.View;
import dev.deadc0de.cobalt.world.MainCharacterEnviroment;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 9;
    private static final int TILE_SIZE = 16;
    private static final Dimension RENDERING_AREA = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

    private final List<Runnable> updateHandlers;
    private final KeyboardInput input;
    private final View view;

    public Main() {
        updateHandlers = new ArrayList<>();
        input = new KeyboardInput();
        view = new View(RENDERING_AREA);
        updateHandlers.add(this.input::update);
    }

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root());
        scene.setOnKeyPressed(input::keyDown);
        scene.setOnKeyReleased(input::keyUp);
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        startRendering();
        stage.show();
    }

    private Parent root() {
        final Image background = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/pallet_town.png"));
        final RenderingPane renderingPane = new RenderingPane(background, layers()::stream, view);
        updateHandlers.add(renderingPane::update);
        return renderingPane;
    }

    private List<RenderingLayer> layers() {
        final SpritesEnvironment<String> environment = environment();
        final RenderingLayer spritesLayer = new SpritesRenderingLayer<>(Sprites.SPRITES, Sprites.spritesRegions(), environment::getStatesAndPositions);
        final MainCharacterEnviroment mainCharacterEnvironment = mainCharacterEnvironment();
        final RenderingLayer mainCharacterLayer = new SpritesRenderingLayer<>(MainCharacter.SPRITES, MainCharacter.spritesRegions(), mainCharacterEnvironment::getStateAndPosition);
        return Arrays.asList(spritesLayer, mainCharacterLayer);
    }

    private SpritesEnvironment<String> environment() {
        final SpritesEnvironment<String> environment = new SpritesEnvironment<>(sprites());
        updateHandlers.add(environment::update);
        return environment;
    }

    private Map<Sprite<String>, Point> sprites() {
        final Map<Sprite<String>, Point> sprites = new HashMap<>();
        sprites.put(Sprites.flower(), new Point(7 * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(7 * TILE_SIZE, 14 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(8 * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(8 * TILE_SIZE, 14 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(9 * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(9 * TILE_SIZE, 14 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(10 * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(10 * TILE_SIZE, 14 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(13 * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(13 * TILE_SIZE, 18 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(14 * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(14 * TILE_SIZE, 18 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(15 * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(15 * TILE_SIZE, 18 * TILE_SIZE));
        sprites.put(Sprites.flower(), new Point(16 * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
        sprites.put(Sprites.flower(), new Point(16 * TILE_SIZE, 18 * TILE_SIZE));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 18 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 18 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 18 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 19 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 19 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 19 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 20 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 20 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 20 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 21 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 21 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 21 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 22 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 22 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 22 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 23 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 23 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 23 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(8 * TILE_SIZE - 8, 24 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(9 * TILE_SIZE - 8, 24 * TILE_SIZE - 8));
        sprites.put(Sprites.sea(), new Point(10 * TILE_SIZE - 8, 24 * TILE_SIZE - 8));
        return sprites;
    }

    private MainCharacterEnviroment mainCharacterEnvironment() {
        final Point initialPosition = new Point(8 * TILE_SIZE, 9 * TILE_SIZE - 4);
        final Point viewRelativePosition = new Point(-4 * TILE_SIZE, -4 * TILE_SIZE + 4);
        final MainCharacterEnviroment environment = new MainCharacterEnviroment(MainCharacter.mainCharacter(), initialPosition, input, view, viewRelativePosition);
        updateHandlers.add(environment::update);
        return environment;
    }

    private void startRendering() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> updateHandlers.forEach(Runnable::run)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

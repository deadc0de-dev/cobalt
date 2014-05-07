package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.ImmutableView;
import dev.deadc0de.cobalt.graphics.MovableView;
import dev.deadc0de.cobalt.graphics.javafx.JavaFXRenderingStack;
import dev.deadc0de.cobalt.input.KeyboardInputFocusStack;
import dev.deadc0de.cobalt.text.SpriteTextFacade;
import dev.deadc0de.cobalt.text.TextInput;
import dev.deadc0de.cobalt.world.World;
import dev.deadc0de.cobalt.world.ZoneInput;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 9;
    private static final int TILE_SIZE = 16;
    private static final Dimension RENDERING_AREA = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
    private static final Point VIEW_RELATIVE_POSITION = new Point(- 4 * TILE_SIZE, - 4 * TILE_SIZE + 4);

    private final List<Updatable> updateHandlers = new ArrayList<>();
    private final MovableView view = new MovableView(0, 0, RENDERING_AREA);
    private final Map<String, Image> imagesRepository = imagesRepository();
    private final Map<String, Region> spritesRegionsRepository = spritesRegionsRepository();
    private final KeyboardInputFocusStack input;
    private final StackPane root = new StackPane();
    private final JavaFXRenderingStack graphics;
    private final SpriteTextFacade textFacade;
    private final World world;

    public Main() {
        input = inputFacade();
        updateHandlers.add(input);
        graphics = new JavaFXRenderingStack(root.getChildren(), imagesRepository::get, spritesRegionsRepository::get);
        textFacade = new SpriteTextFacade(graphics, new ImmutableView(new Region(new Point(0, 0), RENDERING_AREA)), input);
        updateHandlers.add(textFacade);
        world = new World(textFacade, imagesRepository::put, spritesRegionsRepository::put, graphics, input, VIEW_RELATIVE_POSITION);
        final int initialRow = 9;
        final int initialColumn = 8;
        final Point initialPosition = new Point(initialColumn * TILE_SIZE, initialRow * TILE_SIZE - 4);
        updateHandlers.add(world.pushZone("pallet-town", 9, 8, initialPosition, view));
        updateHandlers.add(graphics);
    }

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root);
        scene.setOnKeyPressed(input::keyDown);
        scene.setOnKeyReleased(input::keyUp);
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        startRendering();
        stage.show();
    }

    private KeyboardInputFocusStack inputFacade() {
        final Map<Class<?>, Map<KeyCode, ?>> inputBindings = new HashMap<>();
        inputBindings.put(ZoneInput.class, zoneInput());
        inputBindings.put(TextInput.class, textInput());
        return new KeyboardInputFocusStack(inputBindings);
    }

    private Map<KeyCode, ZoneInput> zoneInput() {
        final Map<KeyCode, ZoneInput> bindings = new EnumMap<>(KeyCode.class);
        bindings.put(KeyCode.W, ZoneInput.UP);
        bindings.put(KeyCode.S, ZoneInput.DOWN);
        bindings.put(KeyCode.A, ZoneInput.LEFT);
        bindings.put(KeyCode.D, ZoneInput.RIGHT);
        bindings.put(KeyCode.K, ZoneInput.ACTION);
        bindings.put(KeyCode.M, ZoneInput.CANCEL);
        bindings.put(KeyCode.SPACE, ZoneInput.PAUSE);
        bindings.put(KeyCode.C, ZoneInput.OPTIONAL);
        return bindings;
    }

    private Map<KeyCode, TextInput> textInput() {
        final Map<KeyCode, TextInput> bindings = new EnumMap<>(KeyCode.class);
        bindings.put(KeyCode.K, TextInput.FORWARD);
        bindings.put(KeyCode.M, TextInput.FORWARD);
        return bindings;
    }

    private Map<String, Image> imagesRepository() {
        final Map<String, Image> repository = new HashMap();
        repository.put(Text.BACKGROUND_NAME, Text.BACKGROUND);
        repository.put(Text.GROUP_NAME, Text.SPRITES);
        repository.putAll(Text.spritesImages());
        repository.put(Sprites.GROUP_NAME, Sprites.SPRITES);
        repository.putAll(Sprites.spritesImages());
        repository.put(MainCharacter.GROUP_NAME, MainCharacter.SPRITES);
        repository.putAll(MainCharacter.spritesImages());
        return repository;
    }

    private Map<String, Region> spritesRegionsRepository() {
        final Map<String, Region> repository = new HashMap();
        repository.putAll(Text.spritesRegions());
        repository.putAll(Sprites.spritesRegions());
        repository.putAll(MainCharacter.spritesRegions());
        return repository;
    }

    private void startRendering() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> updateHandlers.forEach(Updatable::update)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

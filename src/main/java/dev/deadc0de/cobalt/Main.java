package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.FixedSizeView;
import dev.deadc0de.cobalt.graphics.ImmutableView;
import dev.deadc0de.cobalt.graphics.MovableView;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.graphics.javafx.JavaFXRenderingStack;
import dev.deadc0de.cobalt.graphics.javafx.ImageSpritesRepository;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 9;
    private static final int TILE_SIZE = 16;
    private static final Dimension RENDERING_AREA = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
    private static final Point VIEW_RELATIVE_POSITION = new Point(- 4 * TILE_SIZE, - 4 * TILE_SIZE + 4);

    private final List<Updatable> updateHandlers = new ArrayList<>();
    private final MovableView view = new FixedSizeView(RENDERING_AREA);
    private final SpritesRepository<Image> spritesRepository = new ImageSpritesRepository();
    private final KeyboardInputFocusStack input;
    private final StackPane root = new StackPane();
    private final JavaFXRenderingStack graphics;
    private final SpriteTextFacade textFacade;
    private final World world;

    public Main() {
        input = inputFacade();
        updateHandlers.add(input);
        graphics = new JavaFXRenderingStack(root.getChildren(), spritesRepository);
        textFacade = new SpriteTextFacade(graphics, new ImmutableView(new Region(new Point(0, 0), RENDERING_AREA)), input);
        updateHandlers.add(textFacade);
        world = new World(textFacade, spritesRepository, graphics, input, VIEW_RELATIVE_POSITION, view);
        final int initialRow = 9;
        final int initialColumn = 8;
        final Point initialPosition = new Point(initialColumn * TILE_SIZE, initialRow * TILE_SIZE - 4);
        world.changeZone("pallet-town", 9, 8, initialPosition);
        updateHandlers.add(world);
        updateHandlers.add(graphics);
    }

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root);
        scene.setFill(Color.rgb(0x00, 0x47, 0xAB, 0.5d));
        scene.setOnKeyPressed(input::keyDown);
        scene.setOnKeyReleased(input::keyUp);
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        addGlobalSprites();
        startTicks();
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

    private void addGlobalSprites() {
        Sprites.addSprites(spritesRepository);
        Text.addSprites(spritesRepository);
        MainCharacter.addSprites(spritesRepository);
    }

    private void startTicks() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> updateHandlers.forEach(Updatable::update)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

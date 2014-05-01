package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.GraphicsFacade;
import dev.deadc0de.cobalt.graphics.MutableSprite;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.View;
import dev.deadc0de.cobalt.graphics.javafx.JavaFXGraphicsFacade;
import dev.deadc0de.cobalt.input.KeyboardInputFacade;
import dev.deadc0de.cobalt.text.SpriteTextFacade;
import dev.deadc0de.cobalt.text.TextInput;
import dev.deadc0de.cobalt.text.TextFacade;
import dev.deadc0de.cobalt.world.MainCharacterController;
import dev.deadc0de.cobalt.world.MainCharacterElement;
import dev.deadc0de.cobalt.world.PositionTracker;
import dev.deadc0de.cobalt.world.World;
import dev.deadc0de.cobalt.world.ZoneEnvironment;
import dev.deadc0de.cobalt.world.ZoneInput;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
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

    private final List<Runnable> updateHandlers = new ArrayList<>();
    private final View view = new View(RENDERING_AREA);
    private final Map<String, Image> imagesRepository = imagesRepository();
    private final Map<String, Function<String, Region>> spritesRegionsRepository = spritesRegionsRepository();
    private final KeyboardInputFacade input;
    private final StackPane root = new StackPane();
    private final GraphicsFacade graphics;
    private final TextFacade textFacade;
    private final World world;

    public Main() {
        input = inputFacade();
        updateHandlers.add(input);
        graphics = new JavaFXGraphicsFacade(root.getChildren(), imagesRepository::get, spritesRegionsRepository::get);
        textFacade = new SpriteTextFacade(input, graphics, new View(RENDERING_AREA));
        updateHandlers.add(textFacade);
        world = new World(textFacade, imagesRepository::put, spritesRegionsRepository::put, graphics);
        setup();
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

    private KeyboardInputFacade inputFacade() {
        final Map<Class<?>, Map<KeyCode, ?>> inputBindings = new HashMap<>();
        inputBindings.put(ZoneInput.class, zoneInput());
        inputBindings.put(TextInput.class, textInput());
        return new KeyboardInputFacade(inputBindings);
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
        repository.put(Sprites.GROUP_NAME, Sprites.SPRITES);
        repository.put(MainCharacter.GROUP_NAME, MainCharacter.SPRITES);
        return repository;
    }

    private Map<String, Function<String, Region>> spritesRegionsRepository() {
        final Map<String, Function<String, Region>> repository = new HashMap();
        repository.put(Text.GROUP_NAME, Text.spritesRegions()::get);
        repository.put(Sprites.GROUP_NAME, Sprites.spritesRegions()::get);
        repository.put(MainCharacter.GROUP_NAME, MainCharacter.spritesRegions()::get);
        return repository;
    }

    private void setup() {
        updateHandlers.add(world.pushZone("pallet-town", view));
        final Sprite mainCharacterSprite = mainCharacterEnvironment(world.currentZone().environment);
        updateHandlers.add(graphics.pushSpritesLayer(MainCharacter.GROUP_NAME, () -> Stream.of(mainCharacterSprite), view));
    }

    private Sprite mainCharacterEnvironment(ZoneEnvironment environment) {
        final Point initialPosition = new Point(8 * TILE_SIZE, 9 * TILE_SIZE - 4);
        final MutableSprite mainCharacterSprite = new MutableSprite(null, initialPosition);
        view.x = initialPosition.x - 4 * TILE_SIZE;
        view.y = initialPosition.y - 4 * TILE_SIZE + 4;
        final PositionTracker onCharacterMoved = (x, y) -> {
            mainCharacterSprite.setPosition(mainCharacterSprite.position().add(new Point(x, y)));
            view.x += x;
            view.y += y;
        };
        final MainCharacterElement mainCharacterElement = new MainCharacterElement(mainCharacterSprite::setState, onCharacterMoved);
        final Supplier<Set<ZoneInput>> activeInputProvider = input.push(ZoneInput.class, () -> EnumSet.noneOf(ZoneInput.class));
        final MainCharacterController manager = new MainCharacterController(mainCharacterElement, activeInputProvider, environment, 9, 8);
        updateHandlers.add(manager::update);
        return mainCharacterSprite;
    }

    private void startRendering() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> updateHandlers.forEach(Runnable::run)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

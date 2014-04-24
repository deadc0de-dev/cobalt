package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.rendering.GraphicsFacade;
import dev.deadc0de.cobalt.rendering.javafx.JavaFXGraphicsFacade;
import dev.deadc0de.cobalt.grid.ArrayGrid;
import dev.deadc0de.cobalt.grid.Grid;
import dev.deadc0de.cobalt.rendering.MutableSprite;
import dev.deadc0de.cobalt.rendering.Sprite;
import dev.deadc0de.cobalt.rendering.StationarySprite;
import dev.deadc0de.cobalt.rendering.View;
import dev.deadc0de.cobalt.text.SpriteTextOutput;
import dev.deadc0de.cobalt.world.Cell;
import dev.deadc0de.cobalt.world.MainCharacterController;
import dev.deadc0de.cobalt.world.MainCharacterElement;
import dev.deadc0de.cobalt.world.MutableElement;
import dev.deadc0de.cobalt.world.PositionTracker;
import dev.deadc0de.cobalt.world.Zone;
import dev.deadc0de.cobalt.world.ZoneEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 9;
    private static final int TILE_SIZE = 16;
    private static final Dimension RENDERING_AREA = new Dimension(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

    private final List<Runnable> updateHandlers = new ArrayList<>();
    private final KeyboardInput input = new KeyboardInput();
    private final View view = new View(RENDERING_AREA);
    private final SpriteTextOutput prompt;
    private final Function<String, Image> imagesRepository = imagesRepository();
    private final Function<String, Function<String, Region>> spritesRegionsRepository = spritesRegionsRepository();
    private final StackPane root = new StackPane();
    private final GraphicsFacade graphics;

    public Main() {
        prompt = new SpriteTextOutput(input::action);
        updateHandlers.add(input::update);
        updateHandlers.add(prompt::update);
        graphics = new JavaFXGraphicsFacade(root.getChildren(), imagesRepository, spritesRegionsRepository);
        setupGraphics();
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

    private Function<String, Image> imagesRepository() {
        final Map<String, Image> repository = new HashMap();
        repository.put("pallet-town", new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/pallet_town.png")));
        repository.put("text-background", new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/message.png")));
        repository.put("text", Text.SPRITES);
        repository.put("sprites", Sprites.SPRITES);
        repository.put("main-character", MainCharacter.SPRITES);
        return repository::get;
    }

    private Function<String, Function<String, Region>> spritesRegionsRepository() {
        final Map<String, Function<String, Region>> repository = new HashMap();
        repository.put("text", Text.spritesRegions()::get);
        repository.put("sprites", Sprites.spritesRegions()::get);
        repository.put("main-character", MainCharacter.spritesRegions()::get);
        return repository::get;
    }

    private void setupGraphics() {
        final Zone zone = palletTown();
        final Sprite mainCharacterSprite = mainCharacterEnvironment(zone.environment);
        updateHandlers.add(graphics.pushImageLayer("pallet-town", view));
        updateHandlers.add(graphics.pushSpritesLayer("sprites", zone.sprites, view));
        updateHandlers.add(graphics.pushSpritesLayer("main-character", () -> Stream.of(mainCharacterSprite), view));
        final View textView = new View(RENDERING_AREA);
        graphics.pushImageLayer("text-background", textView);
        updateHandlers.add(graphics.pushSpritesLayer("text", prompt::sprites, textView));
    }

    private Zone palletTown() {
        final MutableElement flower = new MutableElement();
        final Iterator<Runnable> flowerAnimation = Sprites.flower(flower::setState).iterator();
        final List<Point> flowersPositions = new ArrayList<>();
        for (int c = 7; c <= 10; c++) {
            flowersPositions.add(new Point(c * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
            flowersPositions.add(new Point(c * TILE_SIZE, 14 * TILE_SIZE));
        }
        for (int c = 13; c <= 16; c++) {
            flowersPositions.add(new Point(c * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
            flowersPositions.add(new Point(c * TILE_SIZE, 18 * TILE_SIZE));
        }
        final MutableElement sea = new MutableElement();
        final Iterator<Runnable> seaAnimation = Sprites.sea(sea::setState).iterator();
        final List<Point> seaPositions = new ArrayList<>();
        for (int r = 18; r <= 24; r++) {
            for (int c = 8; c <= 10; c++) {
                seaPositions.add(new Point(c * TILE_SIZE - 8, r * TILE_SIZE - 8));
            }
        }
        final List<Sprite> sprites = Stream.of(
                flowersPositions.stream().map(position -> new StationarySprite(flower::state, position)),
                seaPositions.stream().map(position -> new StationarySprite(sea::state, position)))
                .reduce(Stream.empty(), Stream::concat).collect(Collectors.toList());
        final List<Runnable> updatables = Arrays.asList(() -> flowerAnimation.next().run(), () -> seaAnimation.next().run());
        final Zone palletTown = new Zone("pallet-town", sprites::stream, updatables::stream, environment());
        updateHandlers.add(() -> palletTown.updatables.get().forEach(Runnable::run));
        return palletTown;
    }

    private ZoneEnvironment environment() {
        final Grid<Cell> enviroment = new ArrayGrid<>(24, 27);
        final Cell ground = new Cell("ground");
        final Cell obstacle = new Cell("solid");
        final Cell water = new Cell("water");
        final Cell signboard = new Cell("signboard", () -> prompt.print("Under development:please retry      later!"));
        fillRegion(enviroment, ground, new Region(new Point(0, 0), new Dimension(27, 24)));
        fillRegion(enviroment, obstacle, new Region(new Point(6, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(12, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(21, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 4), new Dimension(10, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 4), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 4), new Dimension(8, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 5), new Dimension(1, 15)));
        fillRegion(enviroment, obstacle, new Region(new Point(22, 5), new Dimension(1, 15)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 20), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(5, 20), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(11, 20), new Dimension(12, 1)));
        fillRegion(enviroment, water, new Region(new Point(7, 17), new Dimension(4, 7)));
        fillRegion(enviroment, obstacle, new Region(new Point(7, 6), new Dimension(4, 3)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 6), new Dimension(4, 3)));
        fillRegion(enviroment, obstacle, new Region(new Point(7, 12), new Dimension(4, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 11), new Dimension(6, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 16), new Dimension(6, 1)));
        fillRegion(enviroment, signboard, new Region(new Point(6, 8), new Dimension(1, 1)));
        fillRegion(enviroment, signboard, new Region(new Point(14, 8), new Dimension(1, 1)));
        fillRegion(enviroment, signboard, new Region(new Point(10, 12), new Dimension(1, 1)));
        fillRegion(enviroment, signboard, new Region(new Point(16, 16), new Dimension(1, 1)));
        return new ZoneEnvironment(enviroment, obstacle);
    }

    private void fillRegion(Grid<Cell> grid, Cell cell, Region region) {
        for (int r = region.position.y; r < region.endPosition.y; r++) {
            for (int c = region.position.x; c < region.endPosition.x; c++) {
                grid.setAt(r, c, cell);
            }
        }
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
        final MainCharacterController manager = new MainCharacterController(mainCharacterElement, input, environment, 9, 8);
        updateHandlers.add(manager::update);
        return mainCharacterSprite;
    }

    private void startRendering() {
        final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(40), event -> updateHandlers.forEach(Runnable::run)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}

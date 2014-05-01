package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.animation.AnimationBuilder;
import dev.deadc0de.cobalt.animation.LoopAnimation;
import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javafx.scene.image.Image;

public class Sprites {

    public static final String GROUP_NAME = "sprites";
    public static final Image SPRITES = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/sprites.png"));
    private static final int ANIMATION_DELAY = 8;

    public static Map<String, Region> spritesRegions() {
        final Map<String, Region> sprites = new HashMap<>();
        sprites.put("flower-1", new Region(new Point(64, 72), new Dimension(8, 8)));
        sprites.put("flower-2", new Region(new Point(64, 88), new Dimension(8, 8)));
        sprites.put("flower-3", new Region(new Point(64, 104), new Dimension(8, 8)));
        sprites.put("flower-4", new Region(new Point(64, 120), new Dimension(8, 8)));
        sprites.put("sea-1", new Region(new Point(16, 0), new Dimension(16, 16)));
        sprites.put("sea-2", new Region(new Point(32, 0), new Dimension(16, 16)));
        sprites.put("sea-3", new Region(new Point(48, 0), new Dimension(16, 16)));
        sprites.put("sea-4", new Region(new Point(64, 0), new Dimension(16, 16)));
        sprites.put("sea-5", new Region(new Point(80, 0), new Dimension(16, 16)));
        sprites.put("rock", new Region(new Point(256, 64), new Dimension(16, 16)));
        return sprites;
    }

    public static Iterable<Runnable> flower(Consumer<String> stateTracker) {
        return LoopAnimation.indefinite(AnimationBuilder.startWith(stateTracker)
                .run(state -> state.accept("flower-1"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("flower-2"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("flower-3"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("flower-4"))
                .sleep(ANIMATION_DELAY)
                .end());
    }

    public static Iterable<Runnable> sea(Consumer<String> stateTracker) {
        return LoopAnimation.indefinite(AnimationBuilder.startWith(stateTracker)
                .run(state -> state.accept("sea-1"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-2"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-3"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-4"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-5"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-4"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-3"))
                .sleep(ANIMATION_DELAY)
                .run(state -> state.accept("sea-2"))
                .sleep(ANIMATION_DELAY)
                .end());
    }
}

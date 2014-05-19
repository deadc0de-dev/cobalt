package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.animation.AnimationBuilder;
import dev.deadc0de.cobalt.animation.LoopAnimation;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import java.util.function.Consumer;

public class Sprites {

    private static final String SOURCE_NAME = "sprites";
    private static final String SPRITES_SOURCE = "/dev/deadc0de/cobalt/images/sprites.png";
    private static final int ANIMATION_DELAY = 8;

    public static void addSprites(SpritesRepository<?> spritesRepository) {
        spritesRepository.addSource(SOURCE_NAME, SPRITES_SOURCE);
        spritesRepository.addSprite("flower-1", SOURCE_NAME, new Region(64, 72, 8, 8));
        spritesRepository.addSprite("flower-2", SOURCE_NAME, new Region(64, 88, 8, 8));
        spritesRepository.addSprite("flower-3", SOURCE_NAME, new Region(64, 104, 8, 8));
        spritesRepository.addSprite("flower-4", SOURCE_NAME, new Region(64, 120, 8, 8));
        spritesRepository.addSprite("sea-1", SOURCE_NAME, new Region(16, 0, 16, 16));
        spritesRepository.addSprite("sea-2", SOURCE_NAME, new Region(32, 0, 16, 16));
        spritesRepository.addSprite("sea-3", SOURCE_NAME, new Region(48, 0, 16, 16));
        spritesRepository.addSprite("sea-4", SOURCE_NAME, new Region(64, 0, 16, 16));
        spritesRepository.addSprite("sea-5", SOURCE_NAME, new Region(80, 0, 16, 16));
        spritesRepository.addSprite("rock", SOURCE_NAME, new Region(256, 64, 16, 16));
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

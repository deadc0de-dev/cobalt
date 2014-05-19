package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.SpritesRepository;

public class MainCharacter {

    private static final String SOURCE_NAME = "main-character";
    private static final String SPRITES_SOURCE = "/dev/deadc0de/cobalt/images/main_character.png";

    public static void addSprites(SpritesRepository<?> spritesRepository) {
        spritesRepository.addSource(SOURCE_NAME, SPRITES_SOURCE);
        spritesRepository.addSprite("character-down", SOURCE_NAME, new Region(16, 0, 16, 16));
        spritesRepository.addSprite("character-down-left", SOURCE_NAME, new Region(0, 0, 16, 16));
        spritesRepository.addSprite("character-down-right", SOURCE_NAME, new Region(32, 0, 16, 16));
        spritesRepository.addSprite("character-up", SOURCE_NAME, new Region(16, 48, 16, 16));
        spritesRepository.addSprite("character-up-left", SOURCE_NAME, new Region(0, 48, 16, 16));
        spritesRepository.addSprite("character-up-right", SOURCE_NAME, new Region(32, 48, 16, 16));
        spritesRepository.addSprite("character-left", SOURCE_NAME, new Region(16, 16, 16, 16));
        spritesRepository.addSprite("character-left-left", SOURCE_NAME, new Region(0, 16, 16, 16));
        spritesRepository.addSprite("character-left-right", SOURCE_NAME, new Region(32, 16, 16, 16));
        spritesRepository.addSprite("character-right", SOURCE_NAME, new Region(16, 32, 16, 16));
        spritesRepository.addSprite("character-right-left", SOURCE_NAME, new Region(0, 32, 16, 16));
        spritesRepository.addSprite("character-right-right", SOURCE_NAME, new Region(32, 32, 16, 16));
    }
}

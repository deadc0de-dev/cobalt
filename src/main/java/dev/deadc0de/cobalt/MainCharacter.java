package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class MainCharacter {

    public static final String GROUP_NAME = "main-character";
    public static final Image SPRITES = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/main_character.png"));

    public static Map<String, Region> spritesRegions() {
        final Map<String, Region> sprites = new HashMap<>();
        sprites.put("character-down", new Region(new Point(16, 0), new Dimension(16, 16)));
        sprites.put("character-down-left", new Region(new Point(0, 0), new Dimension(16, 16)));
        sprites.put("character-down-right", new Region(new Point(32, 0), new Dimension(16, 16)));
        sprites.put("character-up", new Region(new Point(16, 48), new Dimension(16, 16)));
        sprites.put("character-up-left", new Region(new Point(0, 48), new Dimension(16, 16)));
        sprites.put("character-up-right", new Region(new Point(32, 48), new Dimension(16, 16)));
        sprites.put("character-left", new Region(new Point(16, 16), new Dimension(16, 16)));
        sprites.put("character-left-left", new Region(new Point(0, 16), new Dimension(16, 16)));
        sprites.put("character-left-right", new Region(new Point(32, 16), new Dimension(16, 16)));
        sprites.put("character-right", new Region(new Point(16, 32), new Dimension(16, 16)));
        sprites.put("character-right-left", new Region(new Point(0, 32), new Dimension(16, 16)));
        sprites.put("character-right-right", new Region(new Point(32, 32), new Dimension(16, 16)));
        return sprites;
    }

    public static Map<String, Image> spritesImages() {
        final Map<String, Image> images = new HashMap<>();
        spritesRegions().keySet().forEach(name -> images.put(name, SPRITES));
        return images;
    }
}

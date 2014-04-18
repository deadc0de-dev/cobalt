package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.world.CyclicSprite;
import dev.deadc0de.cobalt.world.Sprite;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Sprites {

    public static final Image SPRITES = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/world.png"));

    public static Map<String, Region> spritesRegions() {
        final Map<String, Region> sprites = new HashMap<>();
        sprites.put("flower-1", new Region(new Point(64, 72), new Dimension(8, 8)));
        sprites.put("flower-2", new Region(new Point(64, 88), new Dimension(8, 8)));
        sprites.put("flower-3", new Region(new Point(64, 104), new Dimension(8, 8)));
        sprites.put("flower-4", new Region(new Point(64, 120), new Dimension(8, 8)));
        sprites.put("sea-1", new Region(new Point(0, 0), new Dimension(16, 16)));
        sprites.put("sea-2", new Region(new Point(16, 0), new Dimension(16, 16)));
        sprites.put("sea-3", new Region(new Point(32, 0), new Dimension(16, 16)));
        sprites.put("sea-4", new Region(new Point(48, 0), new Dimension(16, 16)));
        sprites.put("sea-5", new Region(new Point(64, 0), new Dimension(16, 16)));
        sprites.put("sea-6", new Region(new Point(80, 0), new Dimension(16, 16)));
        return sprites;
    }

    public static Sprite<String> flower() {
        return new CyclicSprite<>(new AnimationBuilder<String>()
                .add(10, "flower-1")
                .add(10, "flower-2")
                .add(10, "flower-3")
                .add(10, "flower-4")
                .animation());
    }

    public static Sprite<String> sea() {
        return new CyclicSprite<>(new AnimationBuilder<String>()
                .add(10, "sea-1")
                .add(10, "sea-2")
                .add(10, "sea-3")
                .add(10, "sea-4")
                .add(10, "sea-5")
                .add(10, "sea-6")
                .animation());
    }
}

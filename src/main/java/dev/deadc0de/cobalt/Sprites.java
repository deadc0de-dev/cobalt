package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.world.ImmutableSprite;
import dev.deadc0de.cobalt.world.Sprite;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Sprites {

    public static final Image SPRITES = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/world.png"));

    public static Map<String, Region> spritesRegions() {
        final Map<String, Region> sprites = new HashMap<>();
        sprites.put("signboard", new Region(new Point(208, 64), new Dimension(16, 16)));
        return sprites;
    }

    public static Sprite<String> signboard() {
        return new ImmutableSprite<>("signboard");
    }
}

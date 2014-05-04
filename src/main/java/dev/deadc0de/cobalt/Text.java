package dev.deadc0de.cobalt;

import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Text {

    public static final String GROUP_NAME = "text";
    public static final Image SPRITES = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/text.png"));
    public static final String BACKGROUND_NAME = "text-background";
    public static final Image BACKGROUND = new Image(Main.class.getResourceAsStream("/dev/deadc0de/cobalt/images/text_background.png"));

    public static Map<String, Region> spritesRegions() {
        final Map<String, Region> sprites = new HashMap<>();
        sprites.put("A", new Region(new Point(0, 0), new Dimension(8, 8)));
        sprites.put("B", new Region(new Point(8, 0), new Dimension(8, 8)));
        sprites.put("C", new Region(new Point(16, 0), new Dimension(8, 8)));
        sprites.put("D", new Region(new Point(24, 0), new Dimension(8, 8)));
        sprites.put("E", new Region(new Point(32, 0), new Dimension(8, 8)));
        sprites.put("F", new Region(new Point(40, 0), new Dimension(8, 8)));
        sprites.put("G", new Region(new Point(48, 0), new Dimension(8, 8)));
        sprites.put("H", new Region(new Point(56, 0), new Dimension(8, 8)));
        sprites.put("I", new Region(new Point(64, 0), new Dimension(8, 8)));
        sprites.put("J", new Region(new Point(72, 0), new Dimension(8, 8)));
        sprites.put("K", new Region(new Point(80, 0), new Dimension(8, 8)));
        sprites.put("L", new Region(new Point(88, 0), new Dimension(8, 8)));
        sprites.put("M", new Region(new Point(96, 0), new Dimension(8, 8)));
        sprites.put("N", new Region(new Point(104, 0), new Dimension(8, 8)));
        sprites.put("O", new Region(new Point(112, 0), new Dimension(8, 8)));
        sprites.put("P", new Region(new Point(120, 0), new Dimension(8, 8)));
        sprites.put("Q", new Region(new Point(128, 0), new Dimension(8, 8)));
        sprites.put("R", new Region(new Point(136, 0), new Dimension(8, 8)));
        sprites.put("S", new Region(new Point(144, 0), new Dimension(8, 8)));
        sprites.put("T", new Region(new Point(152, 0), new Dimension(8, 8)));
        sprites.put("U", new Region(new Point(160, 0), new Dimension(8, 8)));
        sprites.put("V", new Region(new Point(168, 0), new Dimension(8, 8)));
        sprites.put("W", new Region(new Point(176, 0), new Dimension(8, 8)));
        sprites.put("X", new Region(new Point(184, 0), new Dimension(8, 8)));
        sprites.put("Y", new Region(new Point(192, 0), new Dimension(8, 8)));
        sprites.put("Z", new Region(new Point(200, 0), new Dimension(8, 8)));
        sprites.put("(", new Region(new Point(208, 0), new Dimension(8, 8)));
        sprites.put(")", new Region(new Point(216, 0), new Dimension(8, 8)));
        sprites.put(":", new Region(new Point(224, 0), new Dimension(8, 8)));
        sprites.put(";", new Region(new Point(232, 0), new Dimension(8, 8)));
        sprites.put("[", new Region(new Point(240, 0), new Dimension(8, 8)));
        sprites.put("]", new Region(new Point(248, 0), new Dimension(8, 8)));
        sprites.put("a", new Region(new Point(0, 8), new Dimension(8, 8)));
        sprites.put("b", new Region(new Point(8, 8), new Dimension(8, 8)));
        sprites.put("c", new Region(new Point(16, 8), new Dimension(8, 8)));
        sprites.put("d", new Region(new Point(24, 8), new Dimension(8, 8)));
        sprites.put("e", new Region(new Point(32, 8), new Dimension(8, 8)));
        sprites.put("f", new Region(new Point(40, 8), new Dimension(8, 8)));
        sprites.put("g", new Region(new Point(48, 8), new Dimension(8, 8)));
        sprites.put("h", new Region(new Point(56, 8), new Dimension(8, 8)));
        sprites.put("i", new Region(new Point(64, 8), new Dimension(8, 8)));
        sprites.put("j", new Region(new Point(72, 8), new Dimension(8, 8)));
        sprites.put("k", new Region(new Point(80, 8), new Dimension(8, 8)));
        sprites.put("l", new Region(new Point(88, 8), new Dimension(8, 8)));
        sprites.put("m", new Region(new Point(96, 8), new Dimension(8, 8)));
        sprites.put("n", new Region(new Point(104, 8), new Dimension(8, 8)));
        sprites.put("o", new Region(new Point(112, 8), new Dimension(8, 8)));
        sprites.put("p", new Region(new Point(120, 8), new Dimension(8, 8)));
        sprites.put("q", new Region(new Point(128, 8), new Dimension(8, 8)));
        sprites.put("r", new Region(new Point(136, 8), new Dimension(8, 8)));
        sprites.put("s", new Region(new Point(144, 8), new Dimension(8, 8)));
        sprites.put("t", new Region(new Point(152, 8), new Dimension(8, 8)));
        sprites.put("u", new Region(new Point(160, 8), new Dimension(8, 8)));
        sprites.put("v", new Region(new Point(168, 8), new Dimension(8, 8)));
        sprites.put("w", new Region(new Point(176, 8), new Dimension(8, 8)));
        sprites.put("x", new Region(new Point(184, 8), new Dimension(8, 8)));
        sprites.put("y", new Region(new Point(192, 8), new Dimension(8, 8)));
        sprites.put("z", new Region(new Point(200, 8), new Dimension(8, 8)));
        sprites.put("\'", new Region(new Point(208, 8), new Dimension(8, 8)));
        sprites.put("PK", new Region(new Point(216, 8), new Dimension(8, 8)));
        sprites.put("MN", new Region(new Point(224, 8), new Dimension(8, 8)));
        sprites.put("-", new Region(new Point(232, 8), new Dimension(8, 8)));
        sprites.put("?", new Region(new Point(240, 8), new Dimension(8, 8)));
        sprites.put("!", new Region(new Point(248, 8), new Dimension(8, 8)));
        sprites.put("0", new Region(new Point(0, 16), new Dimension(8, 8)));
        sprites.put("1", new Region(new Point(8, 16), new Dimension(8, 8)));
        sprites.put("2", new Region(new Point(16, 16), new Dimension(8, 8)));
        sprites.put("3", new Region(new Point(24, 16), new Dimension(8, 8)));
        sprites.put("4", new Region(new Point(32, 16), new Dimension(8, 8)));
        sprites.put("5", new Region(new Point(40, 16), new Dimension(8, 8)));
        sprites.put("6", new Region(new Point(48, 16), new Dimension(8, 8)));
        sprites.put("7", new Region(new Point(56, 16), new Dimension(8, 8)));
        sprites.put("8", new Region(new Point(64, 16), new Dimension(8, 8)));
        sprites.put("9", new Region(new Point(72, 16), new Dimension(8, 8)));
        sprites.put(".", new Region(new Point(80, 16), new Dimension(8, 8)));
        sprites.put("/", new Region(new Point(88, 16), new Dimension(8, 8)));
        sprites.put(",", new Region(new Point(96, 16), new Dimension(8, 8)));
        sprites.put("»", new Region(new Point(104, 16), new Dimension(8, 8)));
        sprites.put("→", new Region(new Point(112, 16), new Dimension(8, 8)));
        sprites.put("↓", new Region(new Point(120, 16), new Dimension(8, 8)));
        sprites.put("$", new Region(new Point(144, 16), new Dimension(8, 8)));
        sprites.put("×", new Region(new Point(152, 16), new Dimension(8, 8)));
        sprites.put("°", new Region(new Point(160, 16), new Dimension(8, 8)));
        sprites.put("&", new Region(new Point(168, 16), new Dimension(8, 8)));
        sprites.put(" ", new Region(new Point(248, 24), new Dimension(8, 8)));
        return sprites;
    }

    public static Map<String, Image> spritesImages() {
        final Map<String, Image> images = new HashMap<>();
        spritesRegions().keySet().forEach(name -> images.put(name, SPRITES));
        return images;
    }
}

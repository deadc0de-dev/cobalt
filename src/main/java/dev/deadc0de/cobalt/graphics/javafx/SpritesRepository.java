package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.geometry.Region;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.util.Pair;

public class SpritesRepository {

    private final Map<String, Image> sourceImages;
    private final Map<String, Region> spritesRegions;

    public SpritesRepository() {
        sourceImages = new HashMap<>();
        spritesRegions = new HashMap<>();
    }

    public Image getImage(String name) {
        return sourceImages.get(name);
    }

    public Pair<Image, Region> getSpriteSource(String spriteName) {
        return new Pair<>(sourceImages.get(spriteName), spritesRegions.get(spriteName));
    }

    public void addImage(String sourceName, Image sourceImage) {
        sourceImages.put(sourceName, sourceImage);
    }

    public void addSprite(String spriteName, String sourceName, Region region) {
        sourceImages.put(spriteName, sourceImages.get(sourceName));
        spritesRegions.put(spriteName, region);
    }
}

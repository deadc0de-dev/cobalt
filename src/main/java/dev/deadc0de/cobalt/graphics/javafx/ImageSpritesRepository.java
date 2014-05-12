package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class ImageSpritesRepository implements SpritesRepository<Image> {

    private final Map<String, Image> sourceImages;
    private final Map<String, Region> spritesRegions;

    public ImageSpritesRepository() {
        sourceImages = new HashMap<>();
        spritesRegions = new HashMap<>();
    }

    @Override
    public Image getSource(String name) {
        return sourceImages.get(name);
    }

    @Override
    public Region getRegion(String spriteName) {
        return spritesRegions.get(spriteName);
    }

    @Override
    public void addSource(String sourceName, String sourcePath) {
        sourceImages.put(sourceName, new Image(ImageSpritesRepository.class.getResourceAsStream(sourcePath)));
    }

    @Override
    public void addSprite(String spriteName, String sourceName, Region region) {
        sourceImages.put(spriteName, sourceImages.get(sourceName));
        spritesRegions.put(spriteName, region);
    }
}

package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.text.TextFacade;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javafx.scene.image.Image;

public interface ZoneFactory {

    Zone createZone(TextFacade textFacade, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Function<String, Region>> spritesRegionsRepository);
}

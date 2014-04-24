package dev.deadc0de.cobalt.rendering.javafx;

import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.rendering.GraphicsFacade;
import dev.deadc0de.cobalt.rendering.Sprite;
import dev.deadc0de.cobalt.rendering.View;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JavaFXGraphicsFacade implements GraphicsFacade {

    private final List<Node> componentsStack;
    private final Function<String, Image> imagesRepository;
    private final Function<String, Function<String, Region>> spritesRegionsRepository;

    public JavaFXGraphicsFacade(List<Node> componentsStack, Function<String, Image> imagesRepository, Function<String, Function<String, Region>> spritesRegionsRepository) {
        this.componentsStack = componentsStack;
        this.imagesRepository = imagesRepository;
        this.spritesRegionsRepository = spritesRegionsRepository;
    }

    @Override
    public Runnable pushImageLayer(String name, View view) {
        final Image image = imagesRepository.apply(name);
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(view.size.width);
        imageView.setFitHeight(view.size.height);
        componentsStack.add(imageView);
        return new ImageViewAligner(imageView, view);
    }

    @Override
    public Runnable pushSpritesLayer(String groupName, Supplier<Stream<Sprite>> spritesSource, View view) {
        final Image spritesImage = imagesRepository.apply(groupName);
        final Function<String, Region> spritesRegions = spritesRegionsRepository.apply(groupName);
        final Canvas canvas = new Canvas(view.size.width, view.size.height);
        componentsStack.add(canvas);
        return new SpritesRenderer(spritesImage, spritesRegions, spritesSource, view, canvas.getGraphicsContext2D());
    }

    @Override
    public void pop() {
        componentsStack.remove(componentsStack.size() - 1);
    }
}

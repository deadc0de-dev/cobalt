package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.RenderingLayer;
import dev.deadc0de.cobalt.graphics.RenderingStack;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.graphics.View;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JavaFXRenderingStack implements RenderingStack, Updatable {

    private final List<Node> componentsStack;
    private final Deque<Updatable> updateStack;
    private final SpritesRepository<Image> spritesRepository;

    public JavaFXRenderingStack(List<Node> componentsStack, SpritesRepository<Image> spritesRepository) {
        this.componentsStack = componentsStack;
        updateStack = new LinkedList<>();
        this.spritesRepository = spritesRepository;
    }

    @Override
    public void pushLayer(RenderingLayer layer, View view) {
        updateStack.push(imageLayer(layer.background(), view));
        updateStack.push(spritesLayer(layer::sprites, view));
    }

    @Override
    public void popLayer() {
        pop();
        pop();
    }

    @Override
    public void update() {
        updateStack.forEach(Updatable::update);
    }

    private Updatable imageLayer(String name, View view) {
        final Image image = spritesRepository.getSource(name);
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(view.width());
        imageView.setFitHeight(view.height());
        componentsStack.add(imageView);
        return new ImageViewAligner(imageView, view);
    }

    private Updatable spritesLayer(Supplier<Stream<Sprite>> spritesSource, View view) {
        final Canvas canvas = new Canvas(view.width(), view.height());
        componentsStack.add(canvas);
        return new SpritesRenderer(spritesRepository, spritesSource, view, canvas.getGraphicsContext2D());
    }

    private void pop() {
        componentsStack.remove(componentsStack.size() - 1);
        updateStack.pop();
    }
}

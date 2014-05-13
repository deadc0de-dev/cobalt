package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.GraphicsStack;
import dev.deadc0de.cobalt.graphics.SpritesLayer;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.graphics.View;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JavaFXRenderingStackFrame implements GraphicsStack.Frame, Updatable {

    private final List<Node> componentsFrame;
    private final SpritesRepository<Image> spritesRepository;
    private final View view;
    private final Deque<Updatable> updateStack;

    public JavaFXRenderingStackFrame(List<Node> componentsFrame, SpritesRepository<Image> spritesRepository, View view) {
        this.componentsFrame = componentsFrame;
        this.spritesRepository = spritesRepository;
        this.view = view;
        updateStack = new LinkedList<>();
    }

    @Override
    public void pushSingleSourceLayer(String sourceName, Point position) {
        final Image image = spritesRepository.getSource(sourceName);
        final ImageView imageView = new ImageView(image);
        updateStack.push(new ImageViewAligner(imageView, view));
        updateStack.peek().update();
        componentsFrame.add(imageView);
    }

    @Override
    public SpritesLayer pushSpritesLayer() {
        final Canvas canvas = new Canvas(view.width(), view.height());
        final JavaFXSpritesLayer spritesLayer = new JavaFXSpritesLayer(canvas, spritesRepository, view);
        updateStack.push(spritesLayer);
        componentsFrame.add(canvas);
        return spritesLayer;
    }

    @Override
    public void popLayer() {
        componentsFrame.remove(componentsFrame.size() - 1);
        updateStack.pop();
    }

    @Override
    public void update() {
        updateStack.forEach(Updatable::update);
    }
}

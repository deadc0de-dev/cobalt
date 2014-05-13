package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.GraphicsStack;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.graphics.View;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class JavaFXGraphicsStack implements GraphicsStack, Updatable {

    private final List<Node> componentsStack;
    private final SpritesRepository<Image> spritesRepository;
    private final Deque<Updatable> updateStack;

    public JavaFXGraphicsStack(List<Node> componentsStack, SpritesRepository<Image> spritesRepository) {
        this.componentsStack = componentsStack;
        this.spritesRepository = spritesRepository;
        updateStack = new LinkedList<>();
    }

    @Override
    public Frame pushFrame(View view) {
        final StackPane componentsFrame = new StackPane();
        final JavaFXRenderingStackFrame stackFrame = new JavaFXRenderingStackFrame(componentsFrame.getChildren(), spritesRepository, view);
        updateStack.push(stackFrame);
        componentsStack.add(componentsFrame);
        return stackFrame;
    }

    @Override
    public void popFrame() {
        componentsStack.remove(componentsStack.size() - 1);
        updateStack.pop();
    }

    @Override
    public void update() {
        updateStack.forEach(Updatable::update);
    }
}

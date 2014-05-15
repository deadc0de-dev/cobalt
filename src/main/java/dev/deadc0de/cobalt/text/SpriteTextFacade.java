package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.GraphicsStack;
import dev.deadc0de.cobalt.graphics.SpritesLayer;
import dev.deadc0de.cobalt.input.InputFocusStack;
import java.util.Deque;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;

public class SpriteTextFacade implements TextFacade, Updatable {

    private final GraphicsStack.Frame graphics;
    private final InputFocusStack input;
    private final Deque<Updatable> updateStack;

    public SpriteTextFacade(GraphicsStack.Frame graphics, InputFocusStack input) {
        this.graphics = graphics;
        this.input = input;
        updateStack = new LinkedList<>();
    }

    @Override
    public void print(Iterator<String> messages) {
        final SpriteTextController spriteTextController = new SpriteTextController(input.pushFocus(TextInput.class, () -> EnumSet.noneOf(TextInput.class)));
        pushTextLayer(spriteTextController);
        updateStack.push(spriteTextController);
        spriteTextController.print(messages, this::popTextLayer);
    }

    private void pushTextLayer(SpriteTextController spriteTextController) {
        graphics.pushSingleSourceLayer("text-background");
        final SpritesLayer spritesLayer = graphics.pushSpritesLayer();
        spritesLayer.addSprites(spriteTextController.sprites().iterator());
    }

    private void popTextLayer() {
        input.popFocus();
        graphics.popLayer();
        graphics.popLayer();
    }

    @Override
    public void update() {
        updateStack.forEach(Updatable::update);
    }
}

package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.GraphicsStack;
import dev.deadc0de.cobalt.graphics.SpritesLayer;
import dev.deadc0de.cobalt.input.InputFocusStack;
import java.util.Iterator;

public class SpriteTextFacade implements TextFacade, Updatable {

    private final GraphicsStack.Frame graphics;
    private final SpriteTextController textLayer;
    private boolean pushed;

    public SpriteTextFacade(GraphicsStack.Frame graphics, InputFocusStack input) {
        this.graphics = graphics;
        textLayer = new SpriteTextController(input);
        pushed = false;
    }

    @Override
    public void print(Iterator<String> messages) {
        if (textLayer.isDismissed()) {
            pushTextLayer();
            textLayer.print(messages);
        }
    }

    private void pushTextLayer() {
        graphics.pushSingleSourceLayer("text-background");
        final SpritesLayer spritesLayer = graphics.pushSpritesLayer();
        spritesLayer.addSprites(textLayer.sprites().iterator());
        pushed = true;
    }

    @Override
    public void update() {
        textLayer.update();
        if (pushed && textLayer.isDismissed()) {
            graphics.popLayer();
            graphics.popLayer();
            pushed = false;
        }
    }
}

package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.RenderingStack;
import dev.deadc0de.cobalt.graphics.View;
import dev.deadc0de.cobalt.input.InputFacade;
import java.util.Iterator;

public class SpriteTextFacade implements TextFacade, Updatable {

    private final RenderingStack graphics;
    private final View view;
    private final SpriteTextRenderingLayer textLayer;
    private boolean pushed;

    public SpriteTextFacade(RenderingStack graphics, View view, InputFacade input) {
        this.graphics = graphics;
        this.view = view;
        textLayer = new SpriteTextRenderingLayer(input);
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
        graphics.pushLayer(textLayer, view);
        pushed = true;
    }

    @Override
    public void update() {
        textLayer.update();
        if (pushed && textLayer.isDismissed()) {
            graphics.popLayer();
            pushed = false;
        }
    }
}

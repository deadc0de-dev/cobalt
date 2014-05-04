package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class ImageViewAligner implements Updatable {

    private final ImageView imageView;
    private final View view;

    public ImageViewAligner(ImageView imageView, View view) {
        this.imageView = imageView;
        this.view = view;
    }

    @Override
    public void update() {
        final Rectangle2D viewport = new Rectangle2D(view.x(), view.y(), view.width(), view.height());
        imageView.setViewport(viewport);
    }
}

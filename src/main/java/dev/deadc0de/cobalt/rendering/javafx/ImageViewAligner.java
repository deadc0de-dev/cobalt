package dev.deadc0de.cobalt.rendering.javafx;

import dev.deadc0de.cobalt.rendering.View;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;

public class ImageViewAligner implements Runnable {

    private final ImageView imageView;
    private final View view;

    public ImageViewAligner(ImageView imageView, View view) {
        this.imageView = imageView;
        this.view = view;
    }

    @Override
    public void run() {
        final Rectangle2D viewport = new Rectangle2D(view.x, view.y, view.size.width, view.size.height);
        imageView.setViewport(viewport);
    }
}

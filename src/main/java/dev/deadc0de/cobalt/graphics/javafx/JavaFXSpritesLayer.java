package dev.deadc0de.cobalt.graphics.javafx;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.SpritesLayer;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.graphics.View;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class JavaFXSpritesLayer implements SpritesLayer, Updatable {

    private final Set<Sprite> sprites;
    private final Updatable renderer;

    public JavaFXSpritesLayer(Canvas canvas, SpritesRepository<Image> spritesRepository, View view) {
        sprites = new HashSet<>();
        renderer = new SpritesRenderer(spritesRepository, sprites::stream, view, canvas.getGraphicsContext2D());
    }

    @Override
    public void addSprites(Iterator<Sprite> sprites) {
        sprites.forEachRemaining(sprite -> this.sprites.add(sprite));
    }

    @Override
    public void removeSprite(Sprite sprite) {
        sprites.remove(sprite);
    }

    @Override
    public void removeAll() {
        sprites.clear();
    }

    @Override
    public void update() {
        renderer.update();
    }
}

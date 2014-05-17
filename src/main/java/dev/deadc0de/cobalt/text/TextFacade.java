package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.geometry.Point;
import java.util.Iterator;
import java.util.stream.Stream;

public interface TextFacade {

    default void print(String... messages) {
        print(Stream.of(messages).iterator());
    }

    void print(Iterator<String> messages);

    default void showMenu(Runnable onMenuClose, int textWidth, MenuEntry... menuEntries) {
        showMenu(onMenuClose, textWidth, new Point(0, 0), menuEntries);
    }

    default void showMenu(Runnable onMenuClose, int textWidth, Point position, MenuEntry... menuEntries) {
        showMenu(onMenuClose, textWidth, position, Stream.of(menuEntries).iterator());
    }

    void showMenu(Runnable onMenuClose, int textWidth, Point position, Iterator<MenuEntry> menuEntries);
}

package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.MutableSprite;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.StationarySprite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SpriteMenuController implements MenuFacade, Updatable {

    private static final String BLANK_GLYPH = " ";
    private static final String POINTER_GLYPH = "→";
    private static final String SELECTION_GLYPH = "»";

    private final Supplier<Set<MenuInput>> input;
    private final int textWidth;
    private final Runnable onDismissed;
    private final List<MenuEntry> menu;
    private final String[] menuPointers;
    private final List<Sprite[]> sprites;
    private int currentLine;
    private boolean onBackground;
    private boolean dismissed;

    public SpriteMenuController(Supplier<Set<MenuInput>> input, int textWidth, Point position, Iterator<MenuEntry> menuEntries, Runnable onDismissed) {
        this.input = input;
        this.textWidth = textWidth;
        this.onDismissed = onDismissed;
        menu = new ArrayList<>();
        menuEntries.forEachRemaining(menu::add);
        menuPointers = new String[menu.size()];
        Arrays.fill(menuPointers, BLANK_GLYPH);
        menuPointers[0] = POINTER_GLYPH;
        sprites = new ArrayList<>();
        currentLine = 0;
        onBackground = false;
        dismissed = false;
        final List<String[]> lines = menu.stream().map(MenuEntry::label).map(this::splitToGlyphs).collect(Collectors.toList());
        initializeSprites(lines, position);
    }

    private String[] splitToGlyphs(String string) {
        final String[] glyphs = new String[textWidth];
        for (int i = 0; i < string.length() && i < textWidth; i++) {
            glyphs[i] = string.substring(i, i + 1);
        }
        for (int i = string.length(); i < textWidth; i++) {
            glyphs[i] = BLANK_GLYPH;
        }
        return glyphs;
    }

    private void initializeSprites(List<String[]> lines, Point position) {
        sprites.add(buildRow("frame-top-left", "frame-top", "frame-top-right", position));
        sprites.add(buildRow("frame-left", BLANK_GLYPH, "frame-right", new Point(0, 8).add(position)));
        int index = 0;
        for (String[] line : lines) {
            sprites.add(buildTextBackedRow(index, "frame-left", line, "frame-right", new Point(0, (index + 1) * 16).add(position)));
            sprites.add(buildRow("frame-left", BLANK_GLYPH, "frame-right", new Point(0, (index + 1) * 16 + 8).add(position)));
            index++;
        }
        sprites.add(buildRow("frame-bottom-left", "frame-bottom", "frame-bottom-right", new Point(0, (index + 1) * 16).add(position)));
    }

    private Sprite[] buildRow(String left, String center, String right, Point offsets) {
        final Sprite[] row = new Sprite[columns()];
        int i = 0;
        row[i++] = new MutableSprite(left, offsets);
        while (i < row.length - 1) {
            row[i] = new MutableSprite(center, new Point(i * 8, 0).add(offsets));
            i++;
        }
        row[i] = new MutableSprite(right, new Point(i * 8, 0).add(offsets));
        return row;
    }

    private Sprite[] buildTextBackedRow(int entry, String left, String[] glyphs, String right, Point offsets) {
        final Sprite[] row = new Sprite[columns()];
        int i = 0;
        row[i++] = new MutableSprite(left, offsets);
        row[i++] = new MutableSprite(BLANK_GLYPH, new Point(8, 0).add(offsets));
        row[i++] = new StationarySprite(() -> menuPointers[entry], new Point(16, 0).add(offsets));
        row[i++] = new MutableSprite(BLANK_GLYPH, new Point(24, 0).add(offsets));
        for (int j = 0; j < textWidth; j++) {
            row[i] = new MutableSprite(glyphs[j], new Point(i * 8, 0).add(offsets));
            i++;
        }
        row[i] = new MutableSprite(BLANK_GLYPH, new Point(i * 8, 0).add(offsets));
        i++;
        row[i] = new MutableSprite(right, new Point(i * 8, 0).add(offsets));
        return row;
    }

    private int columns() {
        return textWidth + 6;
    }

    public Stream<Sprite> sprites() {
        return sprites.stream().flatMap(Stream::of);
    }

    @Override
    public void focus() {
        menuPointers[currentLine] = POINTER_GLYPH;
        onBackground = false;
    }

    @Override
    public void dismiss() {
        dismissed = true;
        onDismissed.run();
    }

    @Override
    public void update() {
        if (dismissed || onBackground) {
            return;
        }
        final Set<MenuInput> currentInput = input.get();
        if (currentInput.contains(MenuInput.CANCEL)) {
            dismiss();
        } else if (currentInput.contains(MenuInput.SELECT)) {
            menuPointers[currentLine] = SELECTION_GLYPH;
            onBackground = true;
            menu.get(currentLine).onSelected(this);
        } else if (currentInput.contains(MenuInput.UP) && currentLine != 0) {
            menuPointers[currentLine--] = BLANK_GLYPH;
            menuPointers[currentLine] = POINTER_GLYPH;
        } else if (currentInput.contains(MenuInput.DOWN) && currentLine != menu.size() - 1) {
            menuPointers[currentLine++] = BLANK_GLYPH;
            menuPointers[currentLine] = POINTER_GLYPH;
        }
    }
}

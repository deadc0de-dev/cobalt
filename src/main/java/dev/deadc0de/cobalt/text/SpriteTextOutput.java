package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.GraphicsFacade;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.StationarySprite;
import dev.deadc0de.cobalt.graphics.View;
import dev.deadc0de.cobalt.input.InputFacade;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpriteTextOutput implements TextOutput {

    private static final Consumer<TextOutput> NOOP = textOutput -> {
    };

    private final InputFacade input;
    private final GraphicsFacade graphics;
    private final View view;
    private final String[][] lines;
    private final Sprite[][] sprites;
    private final StringBuilder buffer;
    private Supplier<Set<TextInput>> activeInput;
    private Runnable backgroundLayer;
    private Runnable textLayer;
    private int currentLine;
    private int currentIndex;
    private int currentDelay;
    private State state;
    private Consumer<TextOutput> onEnd;

    public SpriteTextOutput(InputFacade input, GraphicsFacade graphics, View view) {
        this.input = input;
        this.graphics = graphics;
        this.view = view;
        this.lines = new String[2][18];
        this.sprites = new Sprite[lines.length][];
        this.buffer = new StringBuilder();
        this.state = State.DISMISSED;
        initializeSprites();
        clear();
    }

    private void initializeSprites() {
        for (int i = 0; i < lines.length; i++) {
            sprites[i] = new Sprite[lines[i].length];
            final int line = i;
            for (int j = 0; j < lines[i].length; j++) {
                final int index = j;
                sprites[i][j] = new StationarySprite(() -> lines[line][index], new Point(j * 8 + 8, i * 16 + 112));
            }
        }
    }

    private void clear() {
        for (String[] line : lines) {
            Arrays.fill(line, " ");
        }
    }

    @Override
    public void print(String text) {
        print(text, NOOP);
    }

    @Override
    public void print(String text, Consumer<TextOutput> onEnd) {
        if (state == State.DISMISSED) {
            state = State.PRINTING;
            activeInput = input.push(TextInput.class, () -> EnumSet.noneOf(TextInput.class));
            backgroundLayer = graphics.pushImageLayer("text-background", view);
            textLayer = graphics.pushSpritesLayer("text", this::sprites, view);
            currentLine = 0;
            currentIndex = 0;
            currentDelay = 0;
            buffer.append(text);
            this.onEnd = onEnd;
        }
    }

    private void dismiss() {
        graphics.pop();
        graphics.pop();
        input.pop();
        state = State.DISMISSED;
    }

    @Override
    public void run() {
        if (state != State.DISMISSED) {
            update();
            backgroundLayer.run();
            textLayer.run();
        }
    }

    private void update() {
        switch (state) {
            case READY_TO_END:
                if (!activeInput.get().isEmpty()) {
                    clear();
                    dismiss();
                    state = State.DISMISSED;
                    onEnd.accept(this);
                }
                return;
            case WAITING_TO_END:
                if (activeInput.get().isEmpty()) {
                    state = State.READY_TO_END;
                }
                return;
            case WAITING_TO_SCROLL:
                if (activeInput.get().isEmpty()) {
                    return;
                }
                scroll();
            case FAST_PRINTING:
                currentDelay--;
            case PRINTING:
                currentDelay--;
                if (currentDelay <= 0) {
                    printNextGlyph();
                    currentDelay = 4;
                }
        }
    }

    private void printNextGlyph() {
        if (buffer.length() == 0) {
            state = State.WAITING_TO_END;
            return;
        }
        if (currentLine == lines.length) {
            state = State.WAITING_TO_SCROLL;
            return;
        }
        final String letter = buffer.substring(0, 1);
        buffer.deleteCharAt(0);
        lines[currentLine][currentIndex++] = letter;
        if (currentIndex == lines[currentLine].length) {
            currentIndex = 0;
            currentLine++;
        }
        state = activeInput.get().isEmpty() ? State.PRINTING : State.FAST_PRINTING;
    }

    private void scroll() {
        final String[] firstLine = lines[0];
        for (int i = 0; i < lines.length - 1; i++) {
            lines[i] = lines[i + 1];
        }
        currentLine--;
        lines[currentLine] = firstLine;
        Arrays.fill(lines[currentLine], " ");
        state = State.PRINTING;
    }

    private Stream<Sprite> sprites() {
        return Stream.of(sprites).flatMap(Stream::of);
    }

    private static enum State {

        PRINTING,
        FAST_PRINTING,
        WAITING_TO_SCROLL,
        WAITING_TO_END,
        READY_TO_END,
        DISMISSED;
    }
}

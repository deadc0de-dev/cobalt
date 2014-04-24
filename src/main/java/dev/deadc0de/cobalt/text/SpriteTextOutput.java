package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.StationarySprite;
import java.util.Arrays;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SpriteTextOutput implements TextOutput {

    private final BooleanSupplier forward;
    private final String[][] lines;
    private final Sprite[][] sprites;
    private final StringBuilder buffer;
    private int currentLine;
    private int currentIndex;
    private int currentDelay;
    private State state;
    private Consumer<TextOutput> onEnd;

    public SpriteTextOutput(BooleanSupplier forward) {
        this.forward = forward;
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
        print(text, TextOutput::dismiss);
    }

    @Override
    public void print(String text, Consumer<TextOutput> onEnd) {
        if (state == State.DISMISSED) {
            state = State.PRINTING;
            currentLine = 0;
            currentIndex = 0;
            currentDelay = 0;
            buffer.append(text);
            this.onEnd = onEnd;
        }
    }

    @Override
    public void dismiss() {
        state = State.DISMISSED;
    }

    public void update() {
        switch (state) {
            case DISMISSED:
                return;
            case READY_TO_END:
                if (forward.getAsBoolean()) {
                    clear();
                    state = State.DISMISSED;
                    onEnd.accept(this);
                }
                return;
            case WAITING_TO_END:
                if (!forward.getAsBoolean()) {
                    state = State.READY_TO_END;
                }
                return;
            case WAITING_TO_SCROLL:
                if (forward.getAsBoolean()) {
                    scroll();
                }
                return;
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
        state = forward.getAsBoolean() ? State.FAST_PRINTING : State.PRINTING;
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

    public Stream<Sprite> sprites() {
        return Stream.of(sprites).flatMap(Stream::of);
    }

    private static enum State {

        PRINTING,
        FAST_PRINTING,
        WAITING_TO_SCROLL,
        WAITING_TO_END,
        READY_TO_END,
        DISMISSED
    }
}

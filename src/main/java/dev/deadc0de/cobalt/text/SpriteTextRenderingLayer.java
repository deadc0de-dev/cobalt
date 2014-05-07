package dev.deadc0de.cobalt.text;

import dev.deadc0de.cobalt.Updatable;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.graphics.RenderingLayer;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.StationarySprite;
import dev.deadc0de.cobalt.input.InputFacade;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpriteTextRenderingLayer implements TextFacade, RenderingLayer, Updatable {

    private static final int PRINTING_DELAY = 2;
    private static final String SCROLL_GLYPH = "↓";
    private static final int SCROLL_BLINK_DURATION = 16;

    private final InputFacade input;
    private final String[][] lines;
    private final Sprite[][] sprites;
    private final StringBuilder buffer;
    private Supplier<Set<TextInput>> activeInput;
    private int currentLine;
    private int currentIndex;
    private int printingDelay;
    private int blinkDelay;
    private State state;
    private Runnable onEnd;

    public SpriteTextRenderingLayer(InputFacade input) {
        this.input = input;
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
    public void print(Iterator<String> messages) {
        chainPrintCalls(messages, this::dismiss);
    }

    private void chainPrintCalls(Iterator<String> messages, Runnable onEnd) {
        if (messages.hasNext()) {
            final String message = messages.next();
            if (messages.hasNext()) {
                print(message, () -> this.chainPrintCalls(messages, onEnd));
            } else {
                print(message, onEnd);
            }
        }
    }

    private void print(String message, Runnable onEnd) {
        if (state == State.DISMISSED) {
            activeInput = input.push(TextInput.class, () -> EnumSet.noneOf(TextInput.class));
        }
        if (state == State.DISMISSED || state == State.ENDED) {
            state = State.PRINTING;
            clear();
            currentLine = 0;
            currentIndex = 0;
            printingDelay = 0;
            blinkDelay = 0;
            buffer.append(message);
            this.onEnd = onEnd;
        }
    }

    private void dismiss() {
        input.pop();
        state = State.DISMISSED;
    }

    public boolean isDismissed() {
        return state == State.DISMISSED;
    }

    @Override
    public void update() {
        switch (state) {
            case DISMISSED:
                return;
            case ENDED:
                onEnd.run();
                return;
            case READY_TO_ADVANCE:
                if (activeInput.get().contains(TextInput.FORWARD)) {
                    if (buffer.length() == 0) {
                        state = State.ENDED;
                        return;
                    }
                    scroll();
                }
            case WAITING_TO_ADVANCE:
                updateBlinkingScroll();
                if (activeInput.get().isEmpty()) {
                    state = State.READY_TO_ADVANCE;
                }
                return;
            case FAST_PRINTING:
                printingDelay--;
            case PRINTING:
                printingDelay--;
                if (printingDelay <= 0) {
                    printNextGlyph();
                    printingDelay = PRINTING_DELAY;
                }
        }
    }

    private void updateBlinkingScroll() {
        blinkDelay--;
        if (blinkDelay <= 0) {
            lines[1][17] = lines[1][17] == SCROLL_GLYPH ? " " : SCROLL_GLYPH;
            blinkDelay = SCROLL_BLINK_DURATION;
        }
    }

    private void printNextGlyph() {
        if (buffer.length() == 0 || currentLine == lines.length) {
            state = State.WAITING_TO_ADVANCE;
            return;
        }
        String letter = buffer.substring(0, 1);
        if (letter.equals("\n")) {
            buffer.deleteCharAt(0);
            lineFeed();
            printNextGlyph();
            return;
        } else if (currentIndex == lines[currentLine].length - 1) {
            lineFeed();
            printNextGlyph();
            return;
        }
        buffer.deleteCharAt(0);
        lines[currentLine][currentIndex++] = letter;
        state = activeInput.get().isEmpty() ? State.PRINTING : State.FAST_PRINTING;
    }

    private void lineFeed() {
        currentIndex = 0;
        currentLine++;
    }

    private void scroll() {
        lines[1][17] = " ";
        final String[] firstLine = lines[0];
        for (int i = 0; i < lines.length - 1; i++) {
            lines[i] = lines[i + 1];
        }
        currentLine--;
        lines[currentLine] = firstLine;
        Arrays.fill(lines[currentLine], " ");
        state = State.PRINTING;
    }

    @Override
    public Stream<Sprite> sprites() {
        return Stream.of(sprites).flatMap(Stream::of);
    }

    @Override
    public String background() {
        return "text-background";
    }

    private static enum State {

        PRINTING,
        FAST_PRINTING,
        WAITING_TO_ADVANCE,
        READY_TO_ADVANCE,
        ENDED,
        DISMISSED;
    }
}

package dev.deadc0de.cobalt.text;

import java.util.function.Consumer;

public interface TextOutput extends Runnable {

    void print(String... messages);

    void print(String message, Consumer<TextOutput> onEnd);
}

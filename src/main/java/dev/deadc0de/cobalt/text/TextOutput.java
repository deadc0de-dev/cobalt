package dev.deadc0de.cobalt.text;

import java.util.function.Consumer;

public interface TextOutput {

    void print(String text);

    void print(String text, Consumer<TextOutput> onEnd);

    void dismiss();
}

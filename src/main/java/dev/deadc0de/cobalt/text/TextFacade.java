package dev.deadc0de.cobalt.text;

import java.util.Arrays;
import java.util.Iterator;

public interface TextFacade {

    default void print(String... messages) {
        print(Arrays.asList(messages).iterator());
    }

    void print(Iterator<String> messages);
}

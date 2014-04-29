package dev.deadc0de.cobalt.text;

import java.util.Iterator;

public interface TextFacade extends Runnable {

    void print(String... messages);

    void print(Iterator<String> messages);

    void printAndThen(Runnable onEnd, String... messages);

    void printAndThen(Runnable onEnd, Iterator<String> messages);
}

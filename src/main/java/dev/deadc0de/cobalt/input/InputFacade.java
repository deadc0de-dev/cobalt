package dev.deadc0de.cobalt.input;

import java.util.Set;
import java.util.function.Supplier;

public interface InputFacade extends Runnable {

    <I> Supplier<Set<I>> push(Class<I> inputType, Supplier<Set<I>> inputsSetFactory);

    void pop();
}

package dev.deadc0de.cobalt.input;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public interface InputFocusStack {

    default <I> Supplier<Set<I>> pushFocus(Class<I> inputType) {
        return pushFocus(inputType, HashSet::new);
    }

    <I> Supplier<Set<I>> pushFocus(Class<I> inputType, Supplier<Set<I>> inputsSetFactory);

    void popFocus();
}

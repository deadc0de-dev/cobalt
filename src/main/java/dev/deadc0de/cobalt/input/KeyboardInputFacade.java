package dev.deadc0de.cobalt.input;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInputFacade implements InputFacade {

    private final Map<Class<?>, Map<KeyCode, ?>> inputBindings;
    private final Deque<KeyboardInputMapper<?>> inputMappers;

    public KeyboardInputFacade(Map<Class<?>, Map<KeyCode, ?>> inputBindings) {
        this.inputBindings = inputBindings;
        inputMappers = new ArrayDeque<>();
    }

    @Override
    public <I> Supplier<Set<I>> push(Class<I> inputType, Supplier<Set<I>> inputsSetFactory) {
        final KeyboardInputMapper<I> mapper = new KeyboardInputMapper<>((Map<KeyCode, I>) inputBindings.get(inputType), inputsSetFactory);
        Optional.ofNullable(inputMappers.peek()).ifPresent(KeyboardInputMapper::clear);
        inputMappers.push(mapper);
        return mapper;
    }

    @Override
    public void pop() {
        inputMappers.pop();
    }

    @Override
    public void run() {
        inputMappers.peek().run();
    }

    public void keyUp(KeyEvent keyEvent) {
        inputMappers.forEach(mapper -> mapper.keyUp(keyEvent));
    }

    public void keyDown(KeyEvent keyEvent) {
        inputMappers.peek().keyDown(keyEvent);
    }
}

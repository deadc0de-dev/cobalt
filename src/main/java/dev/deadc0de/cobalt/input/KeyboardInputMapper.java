package dev.deadc0de.cobalt.input;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInputMapper<I> implements Supplier<Set<I>>, Runnable {

    private final Map<KeyCode, I> keyBindings;
    private final Set<KeyCode> pressedKeys;
    private final Set<KeyCode> activatedKeys;
    private final Set<I> activeInputs;

    public KeyboardInputMapper(Map<KeyCode, I> keyBindings, Supplier<Set<I>> inputsSetFactory) {
        this.keyBindings = keyBindings;
        pressedKeys = EnumSet.noneOf(KeyCode.class);
        activatedKeys = EnumSet.noneOf(KeyCode.class);
        activeInputs = inputsSetFactory.get();
    }

    @Override
    public Set<I> get() {
        return activeInputs;
    }

    @Override
    public void run() {
        activeInputs.clear();
        activatedKeys.stream().map(keyBindings::get).forEach(activeInputs::add);
        activatedKeys.clear();
        activatedKeys.addAll(pressedKeys);
    }

    public void clear() {
        activeInputs.clear();
        activatedKeys.clear();
    }

    public void keyUp(KeyEvent keyEvent) {
        final KeyCode keyCode = keyEvent.getCode();
        if (keyBindings.containsKey(keyCode)) {
            pressedKeys.remove(keyCode);
        }
    }

    public void keyDown(KeyEvent keyEvent) {
        final KeyCode keyCode = keyEvent.getCode();
        if (keyBindings.containsKey(keyCode)) {
            pressedKeys.add(keyCode);
            activatedKeys.add(keyCode);
        }
    }
}

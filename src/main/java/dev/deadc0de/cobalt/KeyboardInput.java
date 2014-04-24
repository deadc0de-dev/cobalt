package dev.deadc0de.cobalt;

import java.util.EnumMap;
import java.util.Map;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInput implements Input {

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int ACTION = 4;
    private static final int CANCEL = 5;
    private static final int PAUSE = 6;
    private static final int OPTIONAL = 7;
    private static final int INPUT_TYPES = 8;

    private static final Map<KeyCode, Integer> inputMappings = new EnumMap<>(KeyCode.class);

    static {
        inputMappings.put(KeyCode.W, UP);
        inputMappings.put(KeyCode.S, DOWN);
        inputMappings.put(KeyCode.A, LEFT);
        inputMappings.put(KeyCode.D, RIGHT);
        inputMappings.put(KeyCode.ENTER, ACTION);
        inputMappings.put(KeyCode.BACK_SPACE, CANCEL);
        inputMappings.put(KeyCode.SPACE, PAUSE);
        inputMappings.put(KeyCode.Z, OPTIONAL);
    }

    private final boolean[] keyStates = new boolean[INPUT_TYPES];
    private final boolean[] keyCache = new boolean[INPUT_TYPES];
    private final boolean[] activeKeys = new boolean[INPUT_TYPES];

    public void update() {
        System.arraycopy(keyCache, 0, activeKeys, 0, INPUT_TYPES);
        System.arraycopy(keyStates, 0, keyCache, 0, INPUT_TYPES);
    }

    public void keyUp(KeyEvent keyEvent) {
        if (inputMappings.containsKey(keyEvent.getCode())) {
            keyStates[inputMappings.get(keyEvent.getCode())] = false;
        }
    }

    public void keyDown(KeyEvent keyEvent) {
        if (inputMappings.containsKey(keyEvent.getCode())) {
            final int keyType = inputMappings.get(keyEvent.getCode());
            keyCache[keyType] = keyStates[keyType] = true;
        }
    }

    @Override
    public boolean up() {
        return activeKeys[UP];
    }

    @Override
    public boolean down() {
        return activeKeys[DOWN];
    }

    @Override
    public boolean left() {
        return activeKeys[LEFT];
    }

    @Override
    public boolean right() {
        return activeKeys[RIGHT];
    }

    @Override
    public boolean action() {
        return activeKeys[ACTION];
    }

    @Override
    public boolean cancel() {
        return activeKeys[CANCEL];
    }

    @Override
    public boolean pause() {
        return activeKeys[PAUSE];
    }

    @Override
    public boolean optional() {
        return activeKeys[OPTIONAL];
    }
}

package dev.deadc0de.cobalt;

import javafx.scene.input.KeyEvent;

public class KeyboardInput implements Input {

    private boolean w;
    private boolean s;
    private boolean a;
    private boolean d;
    private boolean enter;
    private boolean backsapce;
    private boolean space;
    private boolean z;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean action;
    private boolean cancel;
    private boolean pause;
    private boolean optional;

    public void update() {
        up = w;
        down = s;
        left = a;
        right = d;
        action = enter;
        cancel = backsapce;
        pause = space;
        optional = z;
    }

    public void keyUp(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
                w = false;
                break;
            case S:
                s = false;
                break;
            case A:
                a = false;
                break;
            case D:
                d = false;
                break;
            case ENTER:
                enter = false;
                break;
            case BACK_SPACE:
                backsapce = false;
                break;
            case SPACE:
                space = false;
                break;
            case Z:
                z = false;
                break;
        }
    }

    public void keyDown(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
                w = true;
                break;
            case S:
                s = true;
                break;
            case A:
                a = true;
                break;
            case D:
                d = true;
                break;
            case ENTER:
                enter = true;
                break;
            case BACK_SPACE:
                backsapce = true;
                break;
            case SPACE:
                space = true;
                break;
            case Z:
                z = true;
                break;
        }
    }

    @Override
    public boolean up() {
        return up;
    }

    @Override
    public boolean down() {
        return down;
    }

    @Override
    public boolean left() {
        return left;
    }

    @Override
    public boolean right() {
        return right;
    }

    @Override
    public boolean action() {
        return action;
    }

    @Override
    public boolean cancel() {
        return cancel;
    }

    @Override
    public boolean pause() {
        return pause;
    }

    @Override
    public boolean optional() {
        return optional;
    }
}

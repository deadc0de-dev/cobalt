package dev.deadc0de.cobalt;

import java.util.ArrayList;
import java.util.List;

public class AnimationBuilder<F> {

    private static final int ONE_TIME = 1;

    private final List<Animation.KeyFrame<F>> keyFrames;

    public AnimationBuilder() {
        keyFrames = new ArrayList<>();
    }

    public AnimationBuilder<F> add(F frame) {
        keyFrames.add(new Animation.KeyFrame<>(frame, ONE_TIME));
        return this;
    }

    public AnimationBuilder<F> add(int duration, F frame) {
        keyFrames.add(new Animation.KeyFrame<>(frame, duration));
        return this;
    }

    public Animation<F> animation() {
        return new Animation<>(keyFrames);
    }
}

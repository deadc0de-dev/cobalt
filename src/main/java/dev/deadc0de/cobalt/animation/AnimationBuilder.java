package dev.deadc0de.cobalt.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class AnimationBuilder<C> {

    private static final Runnable NOOP = () -> {
    };

    private final C context;
    private final List<Runnable> animation;

    private AnimationBuilder(C context) {
        this.context = context;
        this.animation = new ArrayList<>();
    }

    public static <C> AnimationBuilder<C> startWith(C context) {
        return new AnimationBuilder<>(context);
    }

    public static AnimationBuilder<Void> start() {
        return new AnimationBuilder<>(null);
    }

    public AnimationBuilder<C> run(Consumer<? super C> action) {
        animation.add(() -> action.accept(context));
        return this;
    }

    public AnimationBuilder<C> run(Consumer<? super C>... actions) {
        animation.add(() -> Stream.of(actions).forEach(action -> action.accept(context)));
        return this;
    }

    public AnimationBuilder<C> run(int times, Consumer<? super C> action) {
        for (int i = 0; i < times; i++) {
            animation.add(() -> action.accept(context));
        }
        return this;
    }

    public AnimationBuilder<C> run(Runnable action) {
        animation.add(action);
        return this;
    }

    public AnimationBuilder<C> run(Runnable... actions) {
        animation.add(() -> Stream.of(actions).forEach(Runnable::run));
        return this;
    }

    public AnimationBuilder<C> run(int times, Runnable action) {
        for (int i = 0; i < times; i++) {
            animation.add(action);
        }
        return this;
    }

    public AnimationBuilder<C> sleep(int time) {
        for (int i = 0; i < time; i++) {
            animation.add(NOOP);
        }
        return this;
    }

    public Iterable<Runnable> end() {
        return animation;
    }
}

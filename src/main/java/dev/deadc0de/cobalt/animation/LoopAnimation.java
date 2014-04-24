package dev.deadc0de.cobalt.animation;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LoopAnimation implements Iterable<Runnable> {

    private static final int INDEFINITE = -1;

    private final int times;
    private final Iterable<Runnable> animation;

    public LoopAnimation(int times, Iterable<Runnable> animation) {
        if (times < 0) {
            throw new IllegalArgumentException("cannot loop a negative number of times");
        }
        this.times = times;
        this.animation = animation;
    }

    private LoopAnimation(Iterable<Runnable> animation) {
        times = INDEFINITE;
        this.animation = animation;
    }

    public static LoopAnimation indefinite(Iterable<Runnable> animation) {
        return new LoopAnimation(animation);
    }

    @Override
    public Iterator<Runnable> iterator() {
        return new LoopIterator();
    }

    private class LoopIterator implements Iterator<Runnable> {

        private int times;
        private Iterator<Runnable> currentAnimation;

        public LoopIterator() {
            times = LoopAnimation.this.times;
            currentAnimation = LoopAnimation.this.animation.iterator();
        }

        @Override
        public boolean hasNext() {
            return times != 0;
        }

        @Override
        public Runnable next() {
            if (times == 0) {
                throw new NoSuchElementException("iterator is consumed");
            }
            if (!currentAnimation.hasNext()) {
                if (times > 0) {
                    times--;
                }
                currentAnimation = LoopAnimation.this.animation.iterator();
            }
            return currentAnimation.next();
        }
    }
}

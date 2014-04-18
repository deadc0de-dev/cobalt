package dev.deadc0de.cobalt;

import java.util.Iterator;

public class Animation<F> implements Iterable<F> {

    private final Iterable<KeyFrame<F>> keyFrames;

    public Animation(Iterable<KeyFrame<F>> keyFrames) {
        this.keyFrames = keyFrames;
    }

    @Override
    public Iterator<F> iterator() {
        return new FramesIterator<>(keyFrames.iterator());
    }

    public static class KeyFrame<F> {

        public final F frame;
        public final int duration;

        public KeyFrame(F frame, int duration) {
            if (duration < 0) {
                throw new IllegalArgumentException("key frame duration cannot be negative");
            }
            this.frame = frame;
            this.duration = duration;
        }
    }

    private static class FramesIterator<F> implements Iterator<F> {

        private final Iterator<KeyFrame<F>> keyFrames;
        private F currentFrame;
        private int duration;

        public FramesIterator(Iterator<KeyFrame<F>> keyFrames) {
            this.keyFrames = keyFrames;
            fetchNextKeyFrame();
        }

        private void fetchNextKeyFrame() {
            final KeyFrame<F> next = keyFrames.next();
            currentFrame = next.frame;
            duration = next.duration;
        }

        @Override
        public boolean hasNext() {
            return keyFrames.hasNext() || duration > 0;
        }

        @Override
        public F next() {
            while (duration == 0) {
                fetchNextKeyFrame();
            }
            duration--;
            return currentFrame;
        }
    }
}

package dev.deadc0de.cobalt.world;

public class ImmutableSprite<S> implements Sprite<S> {

    private final S state;

    public ImmutableSprite(S state) {
        this.state = state;
    }

    @Override
    public S state() {
        return state;
    }
}

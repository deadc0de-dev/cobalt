package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.graphics.Sprite;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Zone {

    public final String name;
    public final Supplier<Stream<Sprite>> sprites;
    public final Supplier<Stream<Runnable>> updatables;
    public final ZoneEnvironment environment;

    public Zone(String name, Supplier<Stream<Sprite>> sprites, Supplier<Stream<Runnable>> updatables, ZoneEnvironment environment) {
        this.name = name;
        this.sprites = sprites;
        this.updatables = updatables;
        this.environment = environment;
    }
}

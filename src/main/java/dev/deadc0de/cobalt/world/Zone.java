package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.graphics.Sprite;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Zone {

    public final String name;
    public final String backgroundName;
    public final Supplier<Stream<Sprite>> spritesSource;
    public final Supplier<Stream<Runnable>> updatables;
    public final ZoneEnvironment environment;

    public Zone(String name, String backgroundName, Supplier<Stream<Sprite>> spritesSource, Supplier<Stream<Runnable>> updatables, ZoneEnvironment environment) {
        this.name = name;
        this.backgroundName = backgroundName;
        this.spritesSource = spritesSource;
        this.updatables = updatables;
        this.environment = environment;
    }
}

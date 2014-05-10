package dev.deadc0de.cobalt.world;

import dev.deadc0de.cobalt.graphics.javafx.SpritesRepository;
import dev.deadc0de.cobalt.text.TextFacade;

public interface ZoneFactory {

    Zone createZone(TextFacade textFacade, ZoneChanger zoneChanger, SpritesRepository spritesRepository);
}

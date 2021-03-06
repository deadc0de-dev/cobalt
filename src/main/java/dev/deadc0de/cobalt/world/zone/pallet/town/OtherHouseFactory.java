package dev.deadc0de.cobalt.world.zone.pallet.town;

import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.SpritesRepository;
import dev.deadc0de.cobalt.grid.ArrayGrid;
import dev.deadc0de.cobalt.grid.Grid;
import dev.deadc0de.cobalt.text.TextFacade;
import dev.deadc0de.cobalt.world.Cell;
import dev.deadc0de.cobalt.world.Direction;
import dev.deadc0de.cobalt.world.Zone;
import dev.deadc0de.cobalt.world.ZoneChanger;
import dev.deadc0de.cobalt.world.ZoneEnvironment;
import dev.deadc0de.cobalt.world.ZoneFactory;
import java.util.stream.Stream;

public class OtherHouseFactory implements ZoneFactory {

    private static final int TILE_SIZE = 16;
    private static final String NAME = "pallet-town-other-house";
    private static final String BACKGROUND_NAME = "pallet-town-other-house-background";
    private static final int PALLET_TOWN_ROW = 8;
    private static final int PALLET_TOWN_COLUMN = 16;
    private static final Point PALLET_TOWN_POSITION = new Point(PALLET_TOWN_COLUMN * TILE_SIZE, PALLET_TOWN_ROW * TILE_SIZE - 4);

    @Override
    public Zone createZone(TextFacade textFacade, ZoneChanger zoneChanger, SpritesRepository<?> spritesRepository) {
        addResources(spritesRepository);
        return new Zone(NAME, BACKGROUND_NAME, Stream::empty, Stream::empty, environment(textFacade, zoneChanger));
    }

    private void addResources(SpritesRepository<?> spritesRepository) {
        spritesRepository.addSource(BACKGROUND_NAME, "/dev/deadc0de/cobalt/world/zone/pallet/town/other_house.png");
    }

    private ZoneEnvironment environment(TextFacade textFacade, ZoneChanger zoneChanger) {
        final Grid<Cell> enviroment = new ArrayGrid<>(9, 8);
        final Cell ground = Cell.traversable(true);
        final Cell obstacle = Cell.traversable(false);
        final Cell exit = new Exit(zoneChanger);
        fillRegion(enviroment, obstacle, new Region(0, 0, 8, 1));
        fillRegion(enviroment, ground, new Region(0, 1, 8, 7));
        fillRegion(enviroment, obstacle, new Region(0, 8, 8, 1));
        fillRegion(enviroment, obstacle, new Region(0, 1, 2, 1));
        fillRegion(enviroment, obstacle, new Region(7, 1, 1, 1));
        fillRegion(enviroment, obstacle, new Region(3, 3, 2, 2));
        fillRegion(enviroment, obstacle, new Region(0, 6, 1, 2));
        fillRegion(enviroment, obstacle, new Region(7, 6, 1, 2));
        fillRegion(enviroment, exit, new Region(2, 8, 2, 1));
        return new ZoneEnvironment(enviroment, obstacle);
    }

    private void fillRegion(Grid<Cell> grid, Cell cell, Region region) {
        for (int r = region.position.y; r < region.endPosition.y; r++) {
            for (int c = region.position.x; c < region.endPosition.x; c++) {
                grid.setAt(r, c, cell);
            }
        }
    }

    private static class Exit implements Cell {

        private final ZoneChanger zoneChanger;

        public Exit(ZoneChanger zoneChanger) {
            this.zoneChanger = zoneChanger;
        }

        @Override
        public boolean isTraversable() {
            return false;
        }

        @Override
        public boolean beforeEnter(Direction toward) {
            zoneChanger.changeZone("pallet-town", PALLET_TOWN_ROW, PALLET_TOWN_COLUMN, PALLET_TOWN_POSITION);
            return CONTINUE_ACTION;
        }
    }
}

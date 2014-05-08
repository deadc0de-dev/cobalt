package dev.deadc0de.cobalt.world.zone.pallet.town;

import dev.deadc0de.cobalt.Sprites;
import dev.deadc0de.cobalt.geometry.Dimension;
import dev.deadc0de.cobalt.geometry.Point;
import dev.deadc0de.cobalt.geometry.Region;
import dev.deadc0de.cobalt.graphics.Sprite;
import dev.deadc0de.cobalt.graphics.StationarySprite;
import dev.deadc0de.cobalt.grid.ArrayGrid;
import dev.deadc0de.cobalt.grid.Grid;
import dev.deadc0de.cobalt.text.TextFacade;
import dev.deadc0de.cobalt.world.Cell;
import dev.deadc0de.cobalt.world.Direction;
import dev.deadc0de.cobalt.world.MutableElement;
import dev.deadc0de.cobalt.world.Zone;
import dev.deadc0de.cobalt.world.ZoneChanger;
import dev.deadc0de.cobalt.world.ZoneEnvironment;
import dev.deadc0de.cobalt.world.ZoneFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.image.Image;

public class PalletTownFactory implements ZoneFactory {

    private static final int TILE_SIZE = 16;
    private static final String NAME = "pallet-town";
    private static final String BACKGROUND_NAME = "pallet-town-background";
    private static final int OTHER_HOUSE_ROW = 7;
    private static final int OTHER_HOUSE_COLUMN = 2;
    private static final Point OTHER_HOUSE_POSITION = new Point(OTHER_HOUSE_COLUMN * TILE_SIZE, OTHER_HOUSE_ROW * TILE_SIZE - 4);

    @Override
    public Zone createZone(TextFacade textFacade, ZoneChanger zoneChanger, BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository) {
        addResources(imagesRepository, spritesRegionsRepository);
        final MutableElement flower = new MutableElement();
        final Iterator<Runnable> flowerAnimation = Sprites.flower(flower::setState).iterator();
        final List<Point> flowersPositions = new ArrayList<>();
        for (int c = 7; c <= 10; c++) {
            flowersPositions.add(new Point(c * TILE_SIZE + 8, 13 * TILE_SIZE + 8));
            flowersPositions.add(new Point(c * TILE_SIZE, 14 * TILE_SIZE));
        }
        for (int c = 13; c <= 16; c++) {
            flowersPositions.add(new Point(c * TILE_SIZE + 8, 17 * TILE_SIZE + 8));
            flowersPositions.add(new Point(c * TILE_SIZE, 18 * TILE_SIZE));
        }
        final MutableElement sea = new MutableElement();
        final Iterator<Runnable> seaAnimation = Sprites.sea(sea::setState).iterator();
        final List<Point> seaPositions = new ArrayList<>();
        for (int r = 18; r <= 24; r++) {
            for (int c = 8; c <= 10; c++) {
                seaPositions.add(new Point(c * TILE_SIZE - 8, r * TILE_SIZE - 8));
            }
        }
        final List<Point> rockPositions = Arrays.asList(
                new Point(13 * TILE_SIZE, 4 * TILE_SIZE),
                new Point(14 * TILE_SIZE, 4 * TILE_SIZE),
                new Point(5 * TILE_SIZE, 20 * TILE_SIZE),
                new Point(6 * TILE_SIZE, 20 * TILE_SIZE)
        );
        final List<Sprite> sprites = Stream.of(
                flowersPositions.stream().map(position -> new StationarySprite(flower::state, position)),
                seaPositions.stream().map(position -> new StationarySprite(sea::state, position)),
                rockPositions.stream().map(position -> new StationarySprite(() -> "rock", position))
        ).reduce(Stream.empty(), Stream::concat).collect(Collectors.toList());
        final List<Runnable> updatables = Arrays.asList(() -> flowerAnimation.next().run(), () -> seaAnimation.next().run());
        final Zone palletTown = new Zone(NAME, BACKGROUND_NAME, sprites::stream, updatables::stream, environment(textFacade, zoneChanger));
        return palletTown;
    }

    private void addResources(BiConsumer<String, Image> imagesRepository, BiConsumer<String, Region> spritesRegionsRepository) {
        imagesRepository.accept(BACKGROUND_NAME, new Image(PalletTownFactory.class.getResourceAsStream("/dev/deadc0de/cobalt/world/zone/pallet/town/pallet_town.png")));
    }

    private ZoneEnvironment environment(TextFacade textFacade, ZoneChanger zoneChanger) {
        final Grid<Cell> enviroment = new ArrayGrid<>(24, 27);
        final Cell ground = Cell.traversable(true);
        final Cell obstacle = Cell.traversable(false);
        final Cell water = Cell.traversable(false);
        final Cell signboard = new MessageCell(textFacade, "Under\ndevelopment:\nplease retry\nlater.");
        final Cell otherHouseDoor = new ActionCell(() -> zoneChanger.changeZone("pallet-town-other-house", OTHER_HOUSE_ROW, OTHER_HOUSE_COLUMN, OTHER_HOUSE_POSITION));
        final Cell labDoor = new MessageCell(textFacade, "It's locked.", "There's a note on\nthe door:\n32.5°N...", "The rest is torn\naway.");
        final Cell homeDoor = new MessageCell(textFacade, "It's locked.", "Mom...");
        fillRegion(enviroment, ground, new Region(new Point(0, 0), new Dimension(27, 24)));
        fillRegion(enviroment, obstacle, new Region(new Point(6, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(12, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(21, 0), new Dimension(1, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 4), new Dimension(10, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 4), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 4), new Dimension(8, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 5), new Dimension(1, 15)));
        fillRegion(enviroment, obstacle, new Region(new Point(22, 5), new Dimension(1, 15)));
        fillRegion(enviroment, obstacle, new Region(new Point(3, 20), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(5, 20), new Dimension(2, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(11, 20), new Dimension(12, 1)));
        fillRegion(enviroment, water, new Region(new Point(7, 17), new Dimension(4, 7)));
        fillRegion(enviroment, obstacle, new Region(new Point(7, 6), new Dimension(4, 3)));
        fillRegion(enviroment, obstacle, new Region(new Point(15, 6), new Dimension(4, 3)));
        fillRegion(enviroment, obstacle, new Region(new Point(7, 12), new Dimension(4, 1)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 11), new Dimension(6, 4)));
        fillRegion(enviroment, obstacle, new Region(new Point(13, 16), new Dimension(6, 1)));
        enviroment.setAt(8, 6, signboard);
        enviroment.setAt(8, 14, signboard);
        enviroment.setAt(12, 10, signboard);
        enviroment.setAt(16, 16, signboard);
        enviroment.setAt(8, 16, otherHouseDoor);
        enviroment.setAt(14, 15, labDoor);
        enviroment.setAt(8, 8, homeDoor);
        return new ZoneEnvironment(enviroment, obstacle);
    }

    private void fillRegion(Grid<Cell> grid, Cell cell, Region region) {
        for (int r = region.position.y; r < region.endPosition.y; r++) {
            for (int c = region.position.x; c < region.endPosition.x; c++) {
                grid.setAt(r, c, cell);
            }
        }
    }

    private static class MessageCell implements Cell {

        private final TextFacade textFacade;
        private final String[] messages;

        public MessageCell(TextFacade textFacade, String... messages) {
            this.textFacade = textFacade;
            this.messages = messages;
        }

        @Override
        public boolean isTraversable() {
            return false;
        }

        @Override
        public void onSelected(Direction toward) {
            textFacade.print(messages);
        }
    }

    private static class ActionCell implements Cell {

        private final Runnable action;

        public ActionCell(Runnable action) {
            this.action = action;
        }

        @Override
        public boolean isTraversable() {
            return false;
        }

        @Override
        public void onSelected(Direction toward) {
            action.run();
        }
    }
}

/**
 * @author Micky Snadden 300569572
 * Originally used Maps of coordinates to cells but Coords were added to Cells for convenience of
 * serializing and deserializing as jackson deals with simpler data structures better.
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chip;
import nz.ac.vuw.ecs.swen225.gp22.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp22.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.LockedDoor;
import nz.ac.vuw.ecs.swen225.gp22.domain.MoveableBlock;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.DefaultCustomMovingEntityServiceProvider;

/**
 * Singleton class that holds source code versions of level 1 and level 2.
 * Acts as source of truth to check deserialized xml against.
 */
public class LevelMap {

    private static LevelMap instance = null;

    private Map<Coord, Cell> level1Map;
    private Map<Coord, Cell> level2Map;

    private final List<Cell> level1CellList;
    private final List<Cell> level2CellList;

    public List<Cell> getLevel1CellList() {
        return level1CellList;
    }

    public List<Cell> getLevel2CellList() {
        return level2CellList;
    }

    private LevelMap() {
        initLevel1Map();
        initLevel2Map();
        level1CellList = new ArrayList<>();
        level2CellList = new ArrayList<>();
        level1Map.keySet().forEach(key-> level1CellList.add(level1Map.get(key)));
        level2Map.keySet().forEach(key-> level2CellList.add(level2Map.get(key)));
    }
    private void initLevel1Map() {
        this.level1Map = new HashMap<>();
        // --------------------------------------------
        // Row 0
        // --------------------------------------------
        {
            level1Map.put(new Coord(0, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20, 0), new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 1
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 2
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 3
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(6,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,3),new Cell(
                new Exit(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(12,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 4
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,4),new Cell(
                new LockedDoor("Green"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,4),new Cell(
                new ExitLock(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,4),new Cell(
                new LockedDoor("Green"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 5
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Yellow")))
            ));
            level1Map.put(new Coord(4,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,5),new Cell(
                new LockedDoor("Blue"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,5),new Cell(
                new LockedDoor("Red"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Yellow")))
            ));
            level1Map.put(new Coord(14,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 6
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(4,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Blue")))
            ));
            level1Map.put(new Coord(7,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,6),new Cell(
                new Info("Use the keys to unlock the doors, collect all the treasure to exit."),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Red")))
            ));
            level1Map.put(new Coord(11,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(14,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 7
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(7,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Chip(Direction.None,new Coord(8,7))))
            ));
            level1Map.put(new Coord(9,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(11,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 8
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(4,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Blue")))
            ));
            level1Map.put(new Coord(7,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Red")))
            ));
            level1Map.put(new Coord(11,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(14,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 9
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,9),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,9),new Cell(
                new LockedDoor("Red"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(9,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,9),new Cell(
                new LockedDoor("Blue"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,9),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 10
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,10),new Cell(
                new LockedDoor("Yellow"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,10),new Cell(
                new LockedDoor("Yellow"),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 11
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 12
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,12),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(8,12),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level1Map.put(new Coord(10,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,12),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 13
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,13),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,13),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Key("Green")))
            ));
            level1Map.put(new Coord(10,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,13),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 14
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,14),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 15
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 16
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 17
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 18
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 19
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 20
        // --------------------------------------------
        {
            level1Map.put(new Coord(0,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(1,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(2,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(3,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(4,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(5,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(6,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(7,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(8,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(9,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(10,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(11,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(12,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(13,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(14,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(15,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(16,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(17,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(18,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(19,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level1Map.put(new Coord(20,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        level1Map.forEach((coord, cell) -> cell.setCoord(coord));
    }
    private void initLevel2Map() {
        this.level2Map = new HashMap<>();
        // --------------------------------------------
        // Row 0
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,0),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 1
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,1),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,1),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 2
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level2Map.put(new Coord(7,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,2),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,2),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 3
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,3),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,3),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 4
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,4),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,4),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 5
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,5),new Cell(
                new FreeTile(),
                List.of(
                    new DefaultCustomMovingEntityServiceProvider()
                )
            ));
            level2Map.put(new Coord(8,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,5),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,5),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level2Map.put(new Coord(18,5),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,5),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 6
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,6),new Cell(
                new Exit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,6),new Cell(
                new ExitLock(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,6),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,6),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new MoveableBlock()))
            ));
            level2Map.put(new Coord(14,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new MoveableBlock()))
            ));
            level2Map.put(new Coord(15,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Chip(Direction.None,new Coord(15,6))))
            ));
            level2Map.put(new Coord(16,6),new Cell(
                new Info("Move the blocks to get across pit, and avoid monsters."),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,6),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,6),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 7
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,7),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,7),new Cell(
                new Pit(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level2Map.put(new Coord(19,7),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,7),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 8
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,8),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,8),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 9
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,9),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,9),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,9),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 10
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of(new Treasure()))
            ));
            level2Map.put(new Coord(7,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,10),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,10),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 11
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,11),new Cell(
                new Wall(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,11),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 12
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,12),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 13
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,13),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));

        }
        // --------------------------------------------
        // Row 14
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,14),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 15
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,15),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 16
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,16),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 17
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,17),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 18
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,18),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 19
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,19),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        // --------------------------------------------
        // Row 20
        // --------------------------------------------
        {
            level2Map.put(new Coord(0,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(1,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(2,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(3,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(4,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(5,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(6,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(7,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(8,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(9,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(10,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(11,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(12,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(13,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(14,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(15,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(16,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(17,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(18,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(19,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
            level2Map.put(new Coord(20,20),new Cell(
                new FreeTile(),
                new ArrayList<>(List.of())
            ));
        }
        level2Map.forEach((coord, cell) -> cell.setCoord(coord));
    }

    /**
     *
     * @return Unmodifiable map of coords to cells for level 1.
     */
    public Map<Coord, Cell> getLevel1Map() {
        return Collections.unmodifiableMap(level1Map);
    }

    /**
     *
     * @return Unmodifiable map of coords to cells for level 2.
     */
    public Map<Coord, Cell> getLevel2Map() {
        return Collections.unmodifiableMap(level2Map);
    }

    /**
     * Used to get singleton instance of LevelMap.
     * @return singleton instance of LevelMap
     */
    public static LevelMap get() {
        if (instance == null) {
            instance = new LevelMap();
        }
        return instance;
    }

}

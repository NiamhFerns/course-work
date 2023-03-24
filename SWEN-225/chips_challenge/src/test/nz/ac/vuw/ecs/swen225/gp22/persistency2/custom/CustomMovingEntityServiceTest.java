/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.custom;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.io.File;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.MovingEntity;
import org.junit.jupiter.api.Test;

/**
 * Check a simple implementation of the abstract class works as expected.
 */
class CustomMovingEntityServiceTest {

    @Test
    void directionList() {
        CustomMovingEntityService entity = createEntity();
        assertEquals(entity.getDirectionList(),List.of(Direction.Up,Direction.Up,Direction.Down,Direction.Down));
    }

    @Test
    void getNextDirection() {
        CustomMovingEntityService entity = createEntity();
        assertEquals(entity.getNextDirection(),Direction.Up);
        assertEquals(entity.getNextDirection(),Direction.Up);
        assertEquals(entity.getNextDirection(),Direction.Down);
        assertEquals(entity.getNextDirection(),Direction.Down);
        assertEquals(entity.getNextDirection(),Direction.Up);
        assertEquals(entity.getNextDirection(),Direction.Up);
        assertEquals(entity.getNextDirection(),Direction.Down);
        assertEquals(entity.getNextDirection(),Direction.Down);
    }
    public CustomMovingEntityService createEntity() {
        return new CustomMovingEntityService(
            Direction.None, new Coord(-1, -1)) {

            @Override
            public File getAssociatedLevelFile() {
                return new File("level2.xml");
            }

            @Override
            public List<Direction> getDirectionList() {
                return List.of(Direction.Up,Direction.Up,Direction.Down,Direction.Down);
            }

            @Override
            public boolean interactBefore(MovingEntity e, Direction d, Cell myCell) {
                return false;
            }

            @Override
            public boolean interactAfter(MovingEntity e, Direction d, Cell myCell) {
                return false;
            }

            @Override
            public int drawHierarchy() {
                return 0;
            }

            @Override
            public Image getImage() {
                return null;
            }
        };
    }
}
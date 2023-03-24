/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.custom;

import gameImages.Img;
import java.awt.Image;
import java.io.File;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.MovingEntity;

/**
 * Default implementation of CustomMovingEntityServiceProvider if external service provider is not
 * detected.
 */
public class DefaultCustomMovingEntityServiceProvider extends CustomMovingEntityService {

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
        return Img.Monster.image;
    }

    @Override
    public File getAssociatedLevelFile() {
        return new File("level2.xml");
    }

    @Override
    public List<Direction> getDirectionList() {
        return List.of(Direction.Up,Direction.Up,Direction.Up,Direction.Up,Direction.Down,
            Direction.Down, Direction.Down,Direction.Down);
    }
}

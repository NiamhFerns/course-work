package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * The exit is Chip's goal. When Chip stands on the exit's cell the level is won.
 *
 * @author James Gordon - 300577473
 */
public class Exit extends FreeTile {
    @Override
    public String toString() {
        return "E";
    }
    @Override
    public Image getImage() {
        return Img.Exittile.image;
    }

    @Override
    public boolean afterMoveInto(MovingEntity e, Direction d, Cell myCell) {
        if (e instanceof Chip) e.level.onWin.run();
        return true;
    }
}

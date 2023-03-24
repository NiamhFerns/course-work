package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * The ExitLock prevents Chip moving past until all Treasures have been collected.
 *
 * @author James Gordon - 300577473
 */
public class ExitLock extends FreeTile {
	@Override
	public boolean onMoveInto(MovingEntity e, Direction d, Cell myCell) {
		if (e.level.getRemainingTreasures() == 0 && e instanceof Chip) return true;

		return false;
	}

	@Override
	public boolean afterMoveInto(MovingEntity e, Direction d, Cell myCell) {
		if (!(e instanceof Chip) || e.level.getRemainingTreasures() != 0){
			throw new IllegalStateException("Only Chip can move onto exit locks when no treasures are left!");
		}
		e.level.addSoundToPlay(this);
		myCell.setStoredTile(new FreeTile());
		return true;
	}

	@Override
	public String toString() {
		return "L";
	}

	@Override
	public Image getImage() {
		return Img.Exitlock.image;
	}
}

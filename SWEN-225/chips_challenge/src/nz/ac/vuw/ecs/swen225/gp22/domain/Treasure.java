package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;
import java.util.Arrays;

/**
 * When Chip steps onto the Treasure's cell, it is picked up and the number of remaining treasures decreases, unlocking
 * the ExitLocks once it reaches 0.
 *
 * @author James Gordon - 300577473
 */
public class Treasure implements Entity {
	@Override
	public boolean interactBefore(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}

	@Override
	public boolean interactAfter(MovingEntity e, Direction d, Cell myCell) {
		if (e instanceof Chip) {
			myCell.removeEntity(this);
			e.level.setRemainingTreasures(e.level.getRemainingTreasures() - 1);
			e.level.addSoundToPlay(this);
		}

		return true;
	}
	@Override
	public int drawHierarchy() {
		return 4;
	}

	@Override
	public String toString() {
		return "t";
	}

	@Override
	public Image getImage() {
		return Img.Treasure.image;
	}
	@Override
	public int hashCode() {
		return this.getClass().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Treasure && obj.hashCode() == this.hashCode();
	}
}

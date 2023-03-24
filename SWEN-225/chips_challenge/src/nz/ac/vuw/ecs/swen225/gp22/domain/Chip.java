package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;

import java.awt.*;

/**
 * Chip is the player's character, it can move and interact with entities and tiles.
 *
 * @author James Gordon - 300577473
 */
public class Chip extends MovingEntity {

	/**
	 * No-arguments constructor for Chip allows creating Chip without setting a proper coordinate immediately.
	 */
	public Chip(){
		super(Direction.Down, new Coord(-1,-1));
	}

	/**
	 * Constructor for Chip that allows setting the direction chip is facing and the coordinates of Chip on creation.
	 *
	 * @param facingDir - the direction Chip is facing
	 * @param c - the coordinate of the cell Chip is on
	 */
	public Chip(Direction facingDir, Coord c){
		super(facingDir, c);
	}

	@Override
	public boolean interactBefore(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}

	@Override
	public boolean interactAfter(MovingEntity e, Direction d, Cell myCell) {
		if (e instanceof CustomMovingEntityService) e.level.onDeath.run();

		return false;
	}

	/**
	 * Sets the current level chip is part of
	 *
	 * @param l - the level chip is part of
	 */
	public void setLevel(Level l) {
		this.level = l;
	}

	@Override
	public int drawHierarchy() {
		return 1;
	}

	@Override
	public String toString() {
		return "c";
	}

	@Override
	public Image getImage() {
		return Img.Chip.image;
	}
}

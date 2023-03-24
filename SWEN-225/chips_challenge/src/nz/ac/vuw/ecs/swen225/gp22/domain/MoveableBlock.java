package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * Can be pushed around by moving into it - it blocks movement if it cannot be pushed forwards.
 *
 * @author James Gordon - 300577473
 */
public class MoveableBlock extends MovingEntity {

	/**
	 * No-argument Constructor for MoveableBlock, creates the block without setting a proper coordinate
	 */
	public MoveableBlock() {
		super(Direction.None, new Coord());
	}

	/**
	 * Constructor for MoveableBlock allows setting coordinate upon creation of the block.
	 *
	 * @param c	- the coordinate of the cell the block is on
	 */
	public MoveableBlock(Coord c) {
		super(Direction.None,c);
	}
	@Override
	public boolean interactBefore(MovingEntity e, Direction d, Cell myCell) {
		if (e instanceof MoveableBlock) return false;	// cannot push two blocks at once

		return move(d);
	}
	@Override
	public boolean interactAfter(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}
	@Override
	public int drawHierarchy() {
		return 0;
	}
	@Override
	public String toString() {
		return "b";
	}
	@Override
	public Image getImage() {
		return Img.Movingblock.image;
	}
}

package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Teleporters teleport the target to the other teleporter of the same colour when they are stepped on.
 *
 * @author James Gordon - 300577473
 */
public class Teleporter extends FreeTile {
	public String teleColour;

	/**
	 * Constructor for Teleporter
	 *
	 * @param teleColour - the colour of this teleporter.
	 *                      Teleporters teleport between two teleporters of the same colour
	 */
	public Teleporter(String teleColour) {
		this.teleColour = teleColour;
	}

	/**
	 * No-arguments constructor allows creation of the teleporter without immediately setting the colour
	 */
	public Teleporter() {
		this.teleColour = "";
	}
	@Override
	public boolean onMoveInto(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}

	@Override
	public boolean afterMoveInto(MovingEntity e, Direction d, Cell myCell) {
		if (!(e instanceof Chip)) return true;

		Cell[][] cells = e.level.cells;
		List<Coord> otherTeleporter = new ArrayList<Coord>();

		IntStream.range(0, cells.length).forEach(row -> {
			IntStream.range(0, cells[row].length).filter(col -> {
				return cells[row][col].getStoredTile() instanceof Teleporter t
						&& t.teleColour.equals(this.teleColour) && t != this;})
					.forEach(col -> otherTeleporter.add(new Coord(row, col)));
		});

		if (otherTeleporter.size() == 0) throw new IllegalStateException("Teleporter missing destination!");
		if (otherTeleporter.size() > 1) throw new IllegalStateException("Too many teleporter destinations! Max 1.");

		Cell newCell = cells[otherTeleporter.get(0).x()][otherTeleporter.get(0).y()];

		myCell.removeEntity(e);
		newCell.getEntities().add(e);
		e.coords = otherTeleporter.get(0);

		return true;
	}

	@Override
	public String toString() {
		return "T";
	}

	@Override
	public Image getImage() {	// No image since we never ended up using Teleporter
		throw new Error("No Teleporter image yet!");

		//return Img.Freetile.image;
	}
}

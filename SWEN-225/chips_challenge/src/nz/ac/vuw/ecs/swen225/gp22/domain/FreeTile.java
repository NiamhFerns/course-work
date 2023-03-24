package nz.ac.vuw.ecs.swen225.gp22.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gameImages.Img;

import java.awt.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.DefaultCustomMovingEntityServiceProvider;

/**
 * The FreeTile is an empty tile that doesn't prevent movement.
 *
 * @author James Gordon - 300577473
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.CLASS,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Exit.class, name = "exit"),
	@JsonSubTypes.Type(value = ExitLock.class, name = "exitlock"),
	@JsonSubTypes.Type(value = Hazard.class, name = "hazard"),
	@JsonSubTypes.Type(value = IceHazard.class, name = "icehazard"),
	@JsonSubTypes.Type(value = Info.class, name = "info"),
	@JsonSubTypes.Type(value = LockedDoor.class, name = "lockeddoor"),
	@JsonSubTypes.Type(value = Pit.class, name = "pit"),
	@JsonSubTypes.Type(value = Teleporter.class, name = "teleporter"),
	@JsonSubTypes.Type(value = Wall.class, name = "wall")
})
public class FreeTile {
	/**
	 * Performs an action based on this tile and the entity attempting to move onto this tile's cell,
	 * returns a boolean whether the entity is allowed to move onto the same cell as this tile
	 *
	 * @param e - the entity attempting to move onto the cell this tile is on
	 * @param d - the direction the entity is attempting to move
	 * @param myCell - the cell this tile is part of
	 *
	 * @return - returns true if the entity can move onto the same cell as this tile, false if not
	 */
	public boolean onMoveInto(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}

	/**
	 * Performs an action based on this tile and the entity that has moved onto this tile's cell,
	 * returns a boolean whether moving onto this tile's cell has killed chip
	 *
	 * @param e - the entity that has moved onto this tile's cell
	 * @param d - the direction the entity moved
	 * @param myCell - the cell this tile is part of
	 *
	 * @return - returns true if moving onto this tile's cell doesn't kill Chip
	 */
	public boolean afterMoveInto(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}

	/**
	 * @return - the tile's corresponding image
	 */
	@JsonIgnore
	public Image getImage() {
		return Img.Freetile.image;
	}

	@Override
	public String toString() {
		return "_";
	}
	@Override
	public int hashCode() {
		return this.toString().hashCode()+this.getClass().toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof FreeTile && obj.hashCode() == this.hashCode();
	}
}

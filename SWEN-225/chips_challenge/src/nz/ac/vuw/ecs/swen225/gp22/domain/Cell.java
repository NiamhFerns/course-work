package nz.ac.vuw.ecs.swen225.gp22.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Each Cell keeps track of the tile it is made up of and the entities on it.
 *
 * @author James Gordon - 300577473
 */
public class Cell {
	private FreeTile storedTile;
	private List<Entity> entities;

	private Coord coord;
	/**
	 * No-argument Constructor for the cell leaves the tile as a blank FreeTile
	 */
	public Cell() {
		this.storedTile = new FreeTile();
		entities = new ArrayList<Entity>();
	}

	/**
	 * Constructor for cell that allows setting the stored tile on creation
	 *
	 * @param storedTile - the tile to set as the stored tile
	 */
	public Cell(FreeTile storedTile) {
		this.storedTile = storedTile;
		entities = new ArrayList<Entity>();
	}

	/**
	 * Constructor for cell that allows setting the stored tile and entity list on creatio
	 *
	 * @param freeTile - the tile to set as the stored tile
	 * @param entities - the list of entities to put on the tile
	 */
	public Cell(FreeTile freeTile, List<Entity> entities) {
		this.storedTile = freeTile;
		this.entities = entities;
	}

	/**
	 * Constructor for cell that allows setting the stored tile, entity list, and coords on creation
	 *
	 * @param freeTile - the tile to set as the stored tile
	 * @param entities - the list of entities to put on the tile
	 * @param coord - coordinates used by persistency to save the location of the tile
	 */
	public Cell(FreeTile freeTile, List<Entity> entities, Coord coord) {
		this.storedTile = freeTile;
		this.entities = entities;
		this.coord = coord;
	}

	/**
	 * Sets the list of entities on this cell - used by persistency for creating cells
	 *
	 * @param entities - the entities to go on the cell
	 */
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	/**
	 * Returns the coordinates of this cell - used by gamesave for saving the location of cells
	 *
	 * @return - the coordinates of the cell
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * Sets the coordinates of this cell - used by gamesave for saving the location of cells
	 *
	 * @param coord - the location of this cell
	 */
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	/**
	 * Activates the pre-move effects of the tile and entities on this cell (mostly just checking if the entity can move
	 * onto the tile) and disallows movement if any of the entities or tiles don't allow it
	 *
	 * @param e - the entity moving onto the cell
	 * @param d - the direction the entity is moving
	 * @return - returns true if the entity can move onto this cell, false if not
	 */
    public boolean beforeMoveInto(MovingEntity e, Direction d) {
		return storedTile.onMoveInto(e, d, this) &&
				entities.stream().allMatch(a -> a.interactBefore(e, d, this));
	}

	/**
	 * Activates the post-move effects of the tile and entities on this cell (such as picking up entities),
	 * returns if chip has died or not
	 *
	 * @param e - the entity moving onto the cell
	 * @param d - the direction the entity is moving
	 * @return - returns false if chip dies, true otherwise
	 */
	public boolean afterMoveInto(MovingEntity e, Direction d) {
		return storedTile.afterMoveInto(e, d, this) &&
				entities.stream().allMatch(a -> a.interactAfter(e, d, this));
	}

	/**
	 * Returns the tile stored in this cell
	 *
	 * @return - the tile in this cell
	 */
	public FreeTile getStoredTile() {
		return storedTile;
	}

	/**
	 * Sets the new tile to be stored in this cell. Throws IllegalArgumentException if the new tile is null.
	 *
	 * @param newTile - the new tile to be stored
	 */
	public void setStoredTile(FreeTile newTile) {
		if (newTile == null) throw new IllegalArgumentException("Stored tile cannot be null!");

		storedTile = newTile;
	}

	/**
	 * Returns the list of entities stored in this cell
	 *
	 * @return - the list of entities in this cell
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Returns the image of the topmost entity stored on this cell as an optional
	 *
	 * @return - the image of the topmost entity on the cell, returns an empty optional if there is none
	 */
	@JsonIgnore
	public Optional<Image> getImage() {
		Optional<Entity> entity = entities.stream().reduce((a, b)->{
			return a.drawHierarchy() < b.drawHierarchy() ? a : b;
		});

		if (entity.isEmpty()) return Optional.empty();
		return Optional.of(entity.get().getImage());
	}

	/**
	 * Returns the image of the tile on this cell
	 *
	 * @return - the image of this cell's tile
	 */
	@JsonIgnore
	public Image getTileImage() {
		return storedTile.getImage();
	}

	/**
	 * Removes the given entity from the list of entities on this cell. If this cell doesn't contain the entity,
	 * throws an IllegalArgumentException.
	 *
	 * @param e - the entity to remove from the cell
	 */
	public void removeEntity(Entity e) {
		if (!entities.contains(e))
			throw new IllegalArgumentException("Cannot remove an entity from a tile it is not in!");

		entities = new ArrayList<Entity>(entities.stream().filter(entity -> entity != e).toList());
	}
	@Override
	public int hashCode() {
		return storedTile.hashCode()+entities.hashCode()+coord.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Cell && obj.hashCode() == this.hashCode();
	}
}

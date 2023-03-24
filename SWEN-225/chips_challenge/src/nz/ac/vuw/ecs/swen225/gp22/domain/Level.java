package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;

import java.util.ArrayList;
import java.util.List;

/**
 * The Level keeps track of the board, Chip, Chip's inventory, the monsters, what to do on win, what to do on death,
 * and the remaining treasures to be picked up. Performs no actions on its own.
 *
 * @author James Gordon - 300577473
 */
public class Level {
	private long remainingTreasures;
	public final Cell[][] cells;
	private ArrayList<Entity> inventory;
	public final Chip player;

	private ArrayList<CustomMovingEntityService> mon;

	public final Runnable onWin;
	public final Runnable onDeath;

	private List<Object> objSoundsToPlay;	// renderer checks this in order to play sounds such as picking up keys

	/**
	 * A constructor for the Level
	 *
	 * @param remainingTreasures - the remaining treasures in this level to be picked up
	 * @param cells - the board for this level
	 * @param player - Chip/the player character
	 * @param inventory - a list of up to 8 entities
	 * @param mon - a list of monsters that are told to move every few updates
	 * @param onWin - a Runnable that runs when Chip reaches this level's exit
	 * @param onDeath - a Runnable that runs when Chip is killed
	 */
	public Level(long remainingTreasures, Cell[][] cells, Chip player, ArrayList<Entity> inventory,
				 ArrayList<CustomMovingEntityService> mon, Runnable onWin, Runnable onDeath) {
		this.remainingTreasures = remainingTreasures;
		this.cells = cells;
		this.player = player;
		this.inventory = inventory;
		if (inventory.size() > 7) throw new IllegalArgumentException("Max inventory size is 8!");
		this.mon = mon;
		this.onWin = onWin;
		this.onDeath = onDeath;
		this.objSoundsToPlay = new ArrayList<Object>();
	}

	/**
	 * @return - how many treasures are left to collect in the level
	 */
	public long getRemainingTreasures() {
		return remainingTreasures;
	}

	/**
	 * Replaces the count of how many treasures there are to collect with the new value
	 *
	 * @param newTreasures - the new amount of treasures remaining in the level
	 */
	public void setRemainingTreasures(long newTreasures) {
		remainingTreasures = newTreasures;
	}

	/**
	 * Returns the list of up to 8 entities stored in this level's inventory
	 * (for checking what keys chip has picked up, etc)
	 *
	 * @return - the list of entities
	 */
	public ArrayList<Entity> getInventory() {
		return inventory;
	}

	/**
	 * Adds an object to the list Renderer should play sounds for
	 *
	 * @param o - the object being added
	 */
	public void addSoundToPlay(Object o) {
		objSoundsToPlay.add(o);
	}

	/**
	 * @return - the list of objects to play sounds for. Assumes the objects will be removed from the list once their
	 * sound has been played
	 */
	public List<Object> getSoundsToPlay() {
		return objSoundsToPlay;
	}

	/**
	 * @return - returns the list of monsters this level keeps track of
	 */
	public ArrayList<CustomMovingEntityService> getMon() {
		return mon;
	}

	/**
	 * Adds an entity to this level's inventory if the inventory has room, returning false if it does not.
	 * Max 8 entities in the inventory at any time.
	 *
	 * @param e - The Entity to add to the inventory.
	 * @return boolean - Whether or not the entity was successfully added.
	 */
	public boolean addToInventory(Entity e) {
		if (inventory.size() > 7) return false;

		inventory.add(e);
		return true;
	}
}

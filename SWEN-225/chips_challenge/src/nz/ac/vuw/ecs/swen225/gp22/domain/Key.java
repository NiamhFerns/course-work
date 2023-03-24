package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * The Key entity allows Chip to unlock LockedDoors of the same colour when the key is in the player's inventory.
 * Disappears after use unless it is a green key.
 *
 * @author James Gordon - 300577473
 */
public class Key implements Entity {
	public String keyColour;

	/**
	 * No-arguments constructor for key allows creating a key without immediately setting its colour
	 */
	public Key() {
		this.keyColour = "";
	}

	/**
	 * Constructor for key
	 *
	 * @param keyColour - The colour of this key, unlocks locked doors of the corresponding colour.
	 */
	public Key(String keyColour) {
		this.keyColour = keyColour;
	}

	@Override
	public boolean interactBefore(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}
	@Override
	public boolean interactAfter(MovingEntity e, Direction d, Cell myCell) {
		if (!(e instanceof Chip p)) return true;
		if (p.level.addToInventory(this)) {
			myCell.removeEntity(this);
			e.level.addSoundToPlay(this);
		}
		return true;
	}

	@Override
	public int drawHierarchy() {
		return 3;
	}
	@Override
	public String toString() {
		return "k";
	}

	@Override
	public Image getImage() {
		return switch (keyColour) {
			case "Red" -> Img.Redkey.image;
			case "Green" -> Img.Greenkey.image;
			case "Blue" -> Img.Bluekey.image;
			case "Yellow" -> Img.Yellowkey.image;
			default -> throw new IllegalArgumentException("Key does not have an image for the colour " + keyColour + "!");
		};
	}
	@Override
	public int hashCode() {
		return keyColour.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Key && obj.hashCode() == this.hashCode();
	}
}

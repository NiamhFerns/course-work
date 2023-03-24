package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * Hazards kill Chip when he moves onto the Hazard's cell unless Chip has an AntiHazard with the same hazardType.
 *
 * @author James Gordon - 300577473
 */
public class Hazard extends FreeTile {
	public String hazardType;

	/**
	 * No-arguments constructor for HazardTile that allows creating a HazardTile without immediately
	 * setting the hazardType
	 */
	public Hazard(){
		this.hazardType="";
	}

	/**
	 * Constructor for Hazard
	 *
	 * @param hazardType - the type of hazard this hazard is, an antihazard of the same type stops chip from dying
	 */
	public Hazard(String hazardType) {
		this.hazardType = hazardType;
	}
	@Override
	public boolean onMoveInto(MovingEntity e, Direction d, Cell myCell) {
		return true;
	}
	@Override
	public boolean afterMoveInto(MovingEntity e, Direction d, Cell myCell) {
		if (e instanceof Chip &&
				!e.level.getInventory().stream()	// checks to make sure no antihazard of the same hazardtype
						.filter(ent->{return ent instanceof AntiHazard;})
						.anyMatch(ent->{return ((AntiHazard)ent).hazardType.equals(this.hazardType);})) {

			e.level.onDeath.run();
			return false;
		}

		return true;
	}
	@Override
	public String toString() {
		return "H";
	}
	@Override
	public Image getImage() {	// No image since we never ended up using HazardTile
		throw new Error("No Hazard image yet!");
		//return Img.Freetile.image;
	}
}

package nz.ac.vuw.ecs.swen225.gp22.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import gameImages.Img;

import java.awt.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.DefaultCustomMovingEntityServiceProvider;

/**
 * The Entity interface is for the various things that the player could interact with.
 * Different from tiles, these can stack on top of each other
 * (for example, a monster standing on the key, or a box pushed onto treasure).
 *
 * @author James Gordon - 300577473
 */
@JsonTypeInfo(
	use = JsonTypeInfo.Id.CLASS,
	include = JsonTypeInfo.As.PROPERTY,
	property = "type"
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = Key.class, name = "key"),
	@JsonSubTypes.Type(value = Treasure.class, name = "treasure"),
	@JsonSubTypes.Type(value = Chip.class, name = "chip"),
	@JsonSubTypes.Type(value = DefaultCustomMovingEntityServiceProvider.class, name = "defaultcustomentity"),
	@JsonSubTypes.Type(value = CustomMovingEntityService.class, name = "customentity")
})
public interface Entity {
	/**
	 * The interactBefore method causes the entity to perform an action based on its class, before an entity moves
	 * onto this entity's tile.
	 * 
	 * @param e - The Entity interacting with this Entity.
	 * @param d - The direction the interacting Entity is moving.
	 * @param c - The current coordinates of this entity.
	 * 
	 * @return Returns true if the interaction is successful, false if there is a failure
	 * (such as the interaction causing a box to attempt to move into a wall).
	 */
	boolean interactBefore(MovingEntity e, Direction d, Cell myCell);
	/**
	 * The interactAfter method causes the entity to perform an action based on its class, after the player moves
	 * onto this entity's tile.
	 *
	 * @param e - The Entity interacting with this Entity.
	 * @param d - The direction the interacting Entity is moving.
	 * @param c - The current coordinates of this entity.
	 *
	 * @return Returns true if the interaction is successful, false if there is a failure
	 * (such as the interaction causing a box to attempt to move into a wall).
	 */
	boolean interactAfter(MovingEntity e, Direction d, Cell myCell);
	
	/**
	 * @return Returns an integer with the class' position in the draw hierarchy,
	 * for when multiple entities occupy the same tile.
	 */
	int drawHierarchy();

	/**
	 * @return Returns the entity's image
	 */
	@JsonIgnore
	public Image getImage();

	@Override
	public String toString();
}

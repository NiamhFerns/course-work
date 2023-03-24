package nz.ac.vuw.ecs.swen225.gp22.domain;

import gameImages.Img;

import java.awt.*;

/**
 * The Info tile displays text when Chip is standing on it.
 *
 * @author James Gordon - 300577473
 */
public class Info extends FreeTile {
	public String infoText;

	/**
	 * No-arguments constructor for info allows creating an info tile without immediately setting text for it
	 */
	public Info() {
		this.infoText = "";
	}

	/**
	 * Constructor for the info tile
	 *
	 * @param infoText - the text to display when Chip stands on the tile
	 */
	public Info(String infoText) {
		this.infoText = infoText;
	}

	@Override
	public String toString() {
		return "I";
	}
	@Override
	public Image getImage() {
		return Img.Infotile.image;
	}
}

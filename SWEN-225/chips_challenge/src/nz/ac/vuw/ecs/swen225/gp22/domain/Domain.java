package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.persistency2.GameSave;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers.GameSaveHelper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * The domain class is the part of the domain package the other packages interact with.
 * It keeps track of the level and tells the other classes in this package what to do.
 *
 * @author James Gordon - 300577473
 */
public class Domain {
	Level currentLevel = null;
	int wait = 10;
	/**
	 * Updates objects in the level that move independently of Chip (ie monster)
	 *
	 * Throws IllegalStateException if there is no level to update.
	 */
	public void update() {
		//System.out.println("Domain recieved update!");
		if (!ok()) throw new IllegalStateException("No current level to update.");

		if (wait-- > 0) return;

		wait = 10;

		currentLevel.getMon().stream().forEach((m)->{
			m.move(m.getNextDirection());
		});
	}

	/**
	 * startLevel takes a file path and what to do on win/death, asks Persistency for the corresponding level,
	 * and gives the relevant info to createLevel to make a level out of
	 *
	 * @param levelname - the path to the level
	 * @param onWin - what to do on the player reaching the exit
	 * @param onDeath - what to do on the player dying
	 */
	public void startLevel(String levelname, Runnable onWin, Runnable onDeath) {
		try {
			GameSave save;
			if (levelname.contains("save")) {
				save = GameSaveHelper.loadGameSave(Path.of("./saves/" + levelname + ".xml"));
			}
			else save = GameSaveHelper.loadGameSave(Path.of("./levels/" + levelname + ".xml"));

			Cell[][] cells = new Cell[save.CELLS_HEIGHT][save.CELLS_WIDTH];

			save.getCellList().forEach((c) -> {
				cells[c.getCoord().y()][c.getCoord().x()] = c;
			});
			createLevel(cells, new ArrayList<Entity>(save.getInventory()), onWin, onDeath);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * createLevel takes a 2d array of cells, an array of entities,
	 * and what to do on win/death and turns it into a level stored in Domain.
	 *
	 * @param cells - a 2d array
	 * @param onWin - what to do on the player reaching the exit
	 * @param onDeath - what to do on the player dying
	 */
	public void createLevel(Cell[][] cells, ArrayList<Entity> inventory, Runnable onWin, Runnable onDeath) {
		long remainingTreasures = Arrays.stream(cells)
				.flatMap(cArray-> Arrays.stream(cArray))
				.flatMap(c->c.getEntities().stream())
				.filter(e -> e instanceof Treasure)
				.count();

		Optional<Chip> player = Arrays.stream(cells)
				.flatMap(cArray -> Arrays.stream(cArray))
				.flatMap(c->c.getEntities().stream())
				.filter(e -> e instanceof Chip)
				.map(e -> (Chip) e)
				.reduce((c1, c2)->{throw new IllegalArgumentException("Too many Chips in level!");});


		List<MovingEntity> movingEntityList = new ArrayList<>();
		ArrayList<CustomMovingEntityService> monsters = new ArrayList<>();

		IntStream.range(0, cells.length)
				.forEach((y)->{
					IntStream.range(0, cells[y].length)
							.forEach((x)->{
								cells[y][x].getEntities().stream()
										.filter(e -> e instanceof MovingEntity)
										.map(e -> (MovingEntity) e)
										.forEach(m -> {
											m.coords = new Coord(y, x);
											movingEntityList.add(m);
											if (m instanceof CustomMovingEntityService mon) monsters.add(mon);
											});
							});
				});

		if (player.isEmpty()) { throw new IllegalArgumentException("No Chip in level!"); }

		currentLevel = new Level(remainingTreasures, cells, player.get(), inventory, monsters, onWin, onDeath);

		player.get().setLevel(currentLevel);

		movingEntityList.stream().forEach(m -> m.level = currentLevel);
	}

	/**
	 * Moves the player in the specified direction. Throws IllegalStateException if Domain doesn't have a level stored
	 *
	 * @param dir - the direction to move the player
	 */
	public void movePlayer(Direction dir) {
		if (!ok()) throw new IllegalStateException("No current level for moving player.");

		currentLevel.player.move(dir);
	}

	/**
	 * Returns whether domain currently has a level stored
	 *
	 * @return - boolean that returns true if a level is stored
	 */
	public boolean ok() {
		return currentLevel != null;
	}

	/**
	 * Returns the stored level as an Optional - if there is no stored level returns an empty optional
	 *
	 * @return - Optional that stores the level
	 */
	public Optional<Level> getLevel() {
		if (!ok()) return Optional.empty();

		return Optional.of(currentLevel);
	}

	/**
	 * Sets the stored level in the domain to be the given level
	 *
	 * @param newLevel - the new level for domain
	 */
	public void setLevel(Level newLevel) {
		currentLevel = newLevel;
	}
}
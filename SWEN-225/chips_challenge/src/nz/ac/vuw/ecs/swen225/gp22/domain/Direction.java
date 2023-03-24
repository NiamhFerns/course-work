package nz.ac.vuw.ecs.swen225.gp22.domain;

/**
 * The four directions Up, Down, Left, Right, as well as None - used for movement.
 *
 * @author James Gordon - 300577473
 */
public enum Direction {
	Up(0,-1), Down(0,1), Left(-1,0), Right(1,0), None(0,0);
	final int x, y;
	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Convenience method to convert direction string to enum value.
	 * @param directionString string representing direction.
	 * @return corresponding direction enum value.
	 */
	public static Direction fromString(String directionString) {
			switch (directionString) {
				case "Up" -> {
					return Direction.Up;
				}
				case "Down" -> {
					return Direction.Down;
				}
				case "Left" -> {
					return Direction.Left;
				}
				case "Right" -> {
					return Direction.Right;
				}
				case "None" -> {
					return Direction.None;
				}
				default -> throw new IllegalArgumentException("Not a valid direction string");
			}
	}
}

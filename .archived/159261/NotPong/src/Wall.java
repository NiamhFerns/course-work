public class Wall {
    public Vector2D location, dimensions;

    Wall (double x, double y, double width, double height) {
        location = new Vector2D(x, y);
        dimensions = new Vector2D(width, height);
    }
}

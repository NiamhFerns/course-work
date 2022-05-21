public class Ball {
    public Vector2D location;
    public double radius;

    Ball (double x, double y, double radius) {
        location = new Vector2D(x, y);
        this.radius = radius;
    }
}

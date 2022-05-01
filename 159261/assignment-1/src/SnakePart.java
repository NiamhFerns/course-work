public class SnakePart {
    public boolean head;
    private Vector2D location;
    private static int size;

    public static int getSize() { return size; }
    public Vector2D getLocation() { return location; }

    public static void setSize(int size) { SnakePart.size = size; }
    public void setLocation(int x, int y) {
        location.x = x;
        location.y = y;
    }
    public void setLocation(Vector2D location) {
        this.location.x = location.x;
        this.location.y = location.y;
    }

    public void addLocation(int x, int y) {
        location.x += x;
        location.y += y;
    }

    public boolean collidesWith(SnakePart part) {
        return getLocation().x == part.getLocation().x && getLocation().y == part.getLocation().y;
    }
    public boolean collidesWith(Vector2D v) {
        return getLocation().x == v.x && getLocation().y == v.y;
    }

    // SnakePart() { }
    SnakePart(int x, int y) {
        location = new Vector2D(x, y);
    }
}

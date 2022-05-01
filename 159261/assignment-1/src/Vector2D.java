public class Vector2D { // No structs in this hell language.
    public int x, y;

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void set(Vector2D v) {
        x = v.x;
        y = v.y;
    }
    public Vector2D inverted() {
        return new Vector2D(x * -1, y * -1);
    }
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    Vector2D() {
        x = 0;
        y = 0;
    }
    Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Vector2D v) {
        return v.x == x && v.y == y;
    }
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

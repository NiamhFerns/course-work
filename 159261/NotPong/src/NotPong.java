import java.awt.event.KeyEvent;

public class NotPong extends GameEngine {
    private enum State {
        PLAYING,
        DEAD
    }
    private enum Action {
        UP,
        DOWN,
        STILL
    }
    private Vector2D windowSize = new Vector2D(500, 500);
    State s; Action m;
    // A and B are walls.
    private Ball ball;
    private Vector2D v, d;
    private Integer score;

    private Wall A, B;

    public static void main(String[] args) {
        NotPong game = new NotPong(300.0);
        createGame(game, 60);
    }

    public void init() {
        setWindowSize((int)windowSize.x, (int)windowSize.y);
        ball = new Ball(windowSize.x / 2, windowSize.y / 2, 10);
        A = new Wall(windowSize.x / 2 - ball.radius, -windowSize.y / 2 - ball.radius * 5, 20, windowSize.y);
        B = new Wall(windowSize.x / 2 - ball.radius,  windowSize.y / 2 + ball.radius * 5, 20, windowSize.y);
    }

    public boolean hasCollision(Ball b, Wall w) {

        if(b.location.x > w.location.x && b.location.x < w.location.x + w.dimensions.x) {
            return b.location.y > w.location.y && b.location.y < w.location.y + w.dimensions.y;
        }
        return false;
    }

    public void reset() {
        score = 0;
        ball.location.x = windowSize.x / 2;
        ball.location.y = windowSize.y / 2;
        A.location.x = windowSize.x / 2 - ball.radius;
        A.location.y = -windowSize.y / 2 - ball.radius * 5;
        B.location.x = windowSize.x / 2 - ball.radius;
        B.location.y = windowSize.y / 2 + ball.radius * 5;
    }

    @Override
    public void update(double dt) {
        if(s == State.PLAYING) {
            if(ball.location.x + ball.radius > windowSize.x || ball.location.x - 10 < 0) { d.x = d.x * -1; score++; }
            if(ball.location.y + ball.radius > windowSize.y || ball.location.y - 10 < 0) { d.y = d.y * -1; }
            ball.location.x += v.x * dt * d.x;
            ball.location.y += v.y * dt * d.y;

            switch(m) {
                case UP:
                    if (A.location.y != 0) {
                        A.location.y -= 500 * dt;
                        B.location.y -= 500 * dt;
                    }
                    break;

                case DOWN:
                    if (B.location.y != 0) {
                        A.location.y += 500 * dt;
                        B.location.y += 500 * dt;
                    }
                    break;

                default:
                    System.out.println("Still...");
            }
        }

        if(hasCollision(ball, A)) { s = State.DEAD; reset(); }
        if(hasCollision(ball, B)) { s = State.DEAD; reset(); }
    }

    private void drawGame() {
        changeBackgroundColor(white);
        clearBackground((int) windowSize.x, (int) windowSize.y);

        changeColor(0, 0, 0);
        drawSolidCircle(ball.location.x, ball.location.y, ball.radius);

        drawSolidRectangle(A.location.x, A.location.y, A.dimensions.x, A.dimensions.y);
        drawSolidRectangle(B.location.x, B.location.y, B.dimensions.x, B.dimensions.y);

        drawText(50, windowSize.y - 50, score.toString());
    }
    private void drawUI() {
        changeColor(255, 0, 0);
        drawText( 130, 250, "GAME OVER");
        drawText( 100, 300, "Press Space to Restart", 30);
    }
    @Override
    public void paintComponent() {
        switch(s) {
            case PLAYING:
                drawGame();
                break;
            case DEAD:
                drawUI();
                break;
            default:
        }
    }

    NotPong(double speed) {
        s = State.PLAYING;
        v = new Vector2D(speed, speed / 2);
        d = new Vector2D(1.0, -1.0);
        m = Action.STILL;
        score = 0;
    }

    public void keyPressed(KeyEvent e) {
        // Controls
        if (e.getKeyCode() == KeyEvent.VK_SPACE) { reset(); s = State.PLAYING; } // Left
        if (e.getKeyCode() == KeyEvent.VK_UP) { m = Action.UP; } // Left
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { m = Action.DOWN; } // Left
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) { m = Action.STILL; } // Left

    }
}

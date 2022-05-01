import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class SnakeGame extends GameEngine {
    private final int boardSize = 32, boarderSize = 16, segmentSize = 24;
    private int gameState = 0, frameCounter;
    private final Vector2D windowSize = new Vector2D(boardSize * segmentSize + boarderSize * 2, boardSize * segmentSize + segmentSize + boardSize * 2);
    private ArrayList<SnakePart> snake = new ArrayList<>();
    private final SnakePart apple = new SnakePart(24 * 4, 24 * 4), bomb = new SnakePart(16 * 4, 16 * 4);
    private static SnakePart snakeHead;
    private static int healthPoints = 4, highestScore, currentScore;
    private final Vector2D direction = new Vector2D(1, 0);
    private final Queue<Vector2D> inputQueue = new LinkedList<>();
    Image head[] = new Image[4], body, tail[] = new Image[4], heartRed, heartGrey, appleImage, bombImage, grass;

    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
        createGame(game, 5);
    }

    public void reset() {
        snake = new ArrayList<>();
        snake.add(new SnakePart(16, 16));
        snake.add(new SnakePart(16, 17));
        snake.add(new SnakePart(16, 18));
        snake.get(0).head = true;
        snakeHead = snake.get(0);
        healthPoints = 4;
        currentScore = 0;
        gameState = 0;
        inputQueue.clear();
    }
    public void init() {
        setWindowSize(windowSize.x, windowSize.y);
        SnakePart.setSize(segmentSize);

        snake.add(new SnakePart(16, 16));
        snake.add(new SnakePart(16, 17));
        snake.add(new SnakePart(16, 18));
        snake.get(0).head = true;
        snakeHead = snake.get(0);
        gameState = 1;

        head[0] = loadImage("textures/headup.png");
        head[1] = loadImage("textures/headright.png");
        head[2] = loadImage("textures/headdown.png");
        head[3] = loadImage("textures/headleft.png");
        tail[0] = loadImage("textures/tailup.png");
        tail[1] = loadImage("textures/tailright.png");
        tail[2] = loadImage("textures/taildown.png");
        tail[3] = loadImage("textures/tailleft.png");
        body = loadImage("textures/skin.png");
        heartRed = loadImage("textures/heart.png");
        heartGrey = loadImage("textures/missingheart.png");
        appleImage = loadImage("textures/apple.png");
        bombImage = loadImage("textures/bomb.png");
        grass = loadImage("textures/grass.png");
        apple.setLocation(getNewAppleLocation());
        bomb.setLocation(getNewAppleLocation());
    }

    private Vector2D getNewAppleLocation() {
        Vector2D location = new Vector2D();
        boolean unsafe = true;
        Random rand = new Random();

        while(unsafe) {
            unsafe = false;
            location.x = rand.nextInt(32);
            location.y = rand.nextInt(32);
            for (SnakePart part : snake) {
                if (part.collidesWith(location)) {
                    unsafe = true;
                    break;
                }
            }
            if (apple.collidesWith(location)) { unsafe = true; }
        }
        return location;
    }

    private void snakeMove() {
        if(!inputQueue.isEmpty() && !inputQueue.peek().equals(direction.inverted())) {
            direction.set(inputQueue.remove());
        }
        else if(!inputQueue.isEmpty()) inputQueue.remove();

        if (snake.size() > 1) {
            for (int i = snake.size() - 1; i > 0; --i) {
                snake.get(i).setLocation(snake.get(i - 1).getLocation());
            }
        }

        int newX = snakeHead.getLocation().x + (direction.x);
        int newY = snakeHead.getLocation().y + (direction.y);
        if (newX >= boardSize || newX < 0 || newY >= boardSize || newY < 0 ) { gameState = -1; }
        snakeHead.setLocation(newX, newY);

        for (SnakePart part : snake.subList(1, snake.size())) {
            if (snakeHead.collidesWith(part)) {
                healthPoints--;
            }
        }
    }

    private void collisionCheck(SnakePart previousTail) {
        if (snakeHead.collidesWith(apple)) {
            apple.setLocation(getNewAppleLocation());
            currentScore++;
            if (currentScore > highestScore) highestScore = currentScore;
            if (snake.size() <= 20) snake.add(new SnakePart(previousTail.getLocation().x, previousTail.getLocation().y));
        }
        if (snakeHead.collidesWith(bomb)) {
            bomb.setLocation(getNewAppleLocation());
            currentScore = 0;
            healthPoints--;
        }
    }

    @Override
    public void update(double dt) {
        switch (gameState) {
            case 0:
                SnakePart previousTail = snake.get(snake.size() - 1);
                snakeMove();
                collisionCheck(previousTail);
                if (frameCounter % 100 == 0) bomb.setLocation(getNewAppleLocation());

                if (healthPoints == 0) gameState = -1;
                break;
            case -1:
                break;
        }
        frameCounter++;
    }
    public void drawUI() {
        changeBackgroundColor(Color.darkGray);
        clearBackground(windowSize.x, windowSize.y);

        changeColor(black);
        for (int i = 0; i < 3; ++i) {
            if (i >= healthPoints - 1) drawImage(heartGrey, (windowSize.x / 2 - segmentSize - boarderSize - segmentSize / 2) + (segmentSize + boarderSize) * i, windowSize.y -  segmentSize * 2);
            else drawImage(heartRed, (windowSize.x / 2 - segmentSize - boarderSize - segmentSize / 2) + (segmentSize + boarderSize) * i, windowSize.y -  segmentSize * 2);
        }

        drawBoldText(segmentSize, windowSize.y -  segmentSize,"" + currentScore);
        drawBoldText(windowSize.x - segmentSize - (16 * String.valueOf(highestScore).length()),windowSize.y -  segmentSize,"" + highestScore);
    }
    public void drawSnake() {
        saveCurrentTransform();
        translate(boarderSize,boarderSize);
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                drawImage(grass, j * segmentSize, i * segmentSize);
            }
        }

        drawImage(appleImage, apple.getLocation().x * segmentSize, apple.getLocation().y * segmentSize);
        drawImage(bombImage, bomb.getLocation().x * segmentSize, bomb.getLocation().y * segmentSize);
        if (snake.size() > 1) {
            for (SnakePart part : snake.subList(1, snake.size() - 1)) {
                drawImage(body, part.getLocation().x * segmentSize, part.getLocation().y * segmentSize);
            }
            switch(snake.get(snake.size() - 1).getLocation().subtract(snake.get(snake.size() - 2).getLocation()).toString()) {
                case "(1, 0)":
                    drawImage(tail[1], snake.get(snake.size() - 1).getLocation().x * segmentSize, snake.get(snake.size() - 1).getLocation().y * segmentSize);
                    break;
                case "(-1, 0)":
                    drawImage(tail[3], snake.get(snake.size() - 1).getLocation().x * segmentSize, snake.get(snake.size() - 1).getLocation().y * segmentSize);
                    break;
                case "(0, 1)":
                    drawImage(tail[2], snake.get(snake.size() - 1).getLocation().x * segmentSize, snake.get(snake.size() - 1).getLocation().y * segmentSize);
                    break;
                case "(0, -1)":
                    drawImage(tail[0], snake.get(snake.size() - 1).getLocation().x * segmentSize, snake.get(snake.size() - 1).getLocation().y * segmentSize);
                    break;
            }
        }
        switch(direction.toString()) {
            case "(1, 0)":
                drawImage(head[1], snakeHead.getLocation().x * segmentSize, snakeHead.getLocation().y * segmentSize);
                break;
            case "(-1, 0)":
                drawImage(head[3], snakeHead.getLocation().x * segmentSize, snakeHead.getLocation().y * segmentSize);
                break;
            case "(0, 1)":
                drawImage(head[2], snakeHead.getLocation().x * segmentSize, snakeHead.getLocation().y * segmentSize);
                break;
            case "(0, -1)":
                drawImage(head[0], snakeHead.getLocation().x * segmentSize, snakeHead.getLocation().y * segmentSize);
                break;
        }
        restoreLastTransform();
    }

    public void drawEndScreen() {
        changeColor(red);
        drawBoldText(300, 300, "YOU DIED");
        changeColor(white);
        drawText(200, 400, "[ENTER] - Play Again");
        drawText(250, 480, "[M] - MAIN MENU");
        drawText(300, 560, "[Q] - QUIT");

    }
    public void drawMenuScreen() {
        changeColor(orange);
        drawBoldText(340, 300, "Snake");
        changeColor(white);
        drawText(200, 400, "[ENTER] - Play game");
        drawText(310, 480, "[H] - Help");
        drawText(300, 560, "[Q] - QUIT");
    }
    public void drawHelpScreen() {
        changeColor(blue);
        drawBoldText(340, 300, "Help");
        changeColor(white);
        drawText(200, 400, "Control snake with arrow keys.", 28);
        drawText(150, 480, "Pick up apples to increase you score.", 28);
        drawText(50, 560, "Avoid bombs as they will take a life and reset your score.", 28);
        drawText(180, 640, "After 4 lost lives, the game ends.", 28);
        drawText(280, 720, "[ESC] - Main Menu", 28);
    }
    @Override
    public void paintComponent() {
        drawUI();
        switch (gameState) {
            case 0:
                drawSnake();
                break;
            case 1:
                drawMenuScreen();
                break;
            case 2:
                drawHelpScreen();
                break;
            case -1:
                drawEndScreen();
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        // Controls
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { inputQueue.add(new Vector2D(-1, 0)); } // Left
        if (e.getKeyCode() == KeyEvent.VK_DOWN) { inputQueue.add(new Vector2D(0, 1)); } // Down
        if (e.getKeyCode() == KeyEvent.VK_UP) { inputQueue.add(new Vector2D(0, -1)); } // Up
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) { inputQueue.add(new Vector2D(1, 0)); } // Right

        // Menu
        if (e.getKeyCode() == KeyEvent.VK_ENTER && (gameState == -1 || gameState == 1)) { reset(); }
        if (e.getKeyCode() == KeyEvent.VK_Q && (gameState == -1 || gameState == 1)) { System.exit(0); }
        if (e.getKeyCode() == KeyEvent.VK_M && (gameState == -1 || gameState == 1)) { gameState = 1; }

        // Menu
        if (e.getKeyCode() == KeyEvent.VK_ENTER && (gameState == -1 || gameState == 1)) { reset(); }
        if (e.getKeyCode() == KeyEvent.VK_Q && (gameState == -1 || gameState == 1)) { System.exit(0); }
        if (e.getKeyCode() == KeyEvent.VK_H && (gameState == -1 || gameState == 1)) { gameState = 2; }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && (gameState == 2)) { gameState = 1; }
    }
}

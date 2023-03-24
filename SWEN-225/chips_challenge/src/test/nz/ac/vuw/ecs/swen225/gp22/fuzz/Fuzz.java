package nz.ac.vuw.ecs.swen225.gp22.fuzz;

import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.app.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * This class is used to test the game by automatically generating random inputs.
 *
 * @author zhanghaos
 * zhanghaos and holycabbage are the same person.
 */
class FuzzTest {
    private final GameHandler game;
    static final Random random = new Random();
    // keys to be pressed
    private static final List
            <Integer> keys = List.of(
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_ESCAPE
    );
    /**
     * This the constructor of the Fuzz class.
     */
    public FuzzTest() {
        game = GameHandler.get();
        game.reset();
        game.start();
    }
    /**
     * This method is used Strategy Pattern to generate random inputs.
     * @throws AWTException if the Robot class is not supported.
     */
    public void testInputStrategy(Input input, int size, String level) throws AWTException {
        game.skipTo(level);
        Robot robot = new Robot();
        robot.delay(2000);
        for (int i = 0; i < size; i++) {
            int key = input.nextInput();
            if (input.isPressCtrl()) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                System.out.println("Pressing control");
            }
            testClock();
            robot.keyPress(key);
            System.out.println("Pressing " + KeyEvent.getKeyText(key));
            robot.keyRelease(key);
            if (input.isPressCtrl()) {
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }
        }
    }

    /**
     * this method generates a random sequence of inputs
     *             and then executes them.
     * @param size the number of random inputs to generate.
     * @param level the level to start the game on.
     * @throws AWTException if the Robot class is not supported.
     * @throws IllegalArgumentException if there is a precondition violation.
     */
    public void randomKeys(int size, String level) throws AWTException, IllegalArgumentException {
        Robot robot = new Robot();

        game.skipTo(level);
        robot.delay(2000 ) ;

         for (int i = 0; i < size; i++) {
            int key = keys.get(random.nextInt(keys.size()))  ;
            robot.keyPress(key);
            System.out.println("Key: " + KeyEvent.getKeyText(key));
            robot.keyRelease(key);
            robot.delay(10);
         }
    }
    /**
     * This method generates a mouse click at a location.
     * @throws AWTException if the Robot class is not supported.
     */
    public void mouseTest() throws AWTException {
        Robot robot = new Robot();

        robot.mouseMove(1050, 550);
        robot.delay(200);
        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }
    /**
     * this method generates a random sequence of actions functions from the
     *             list of actions and then executes them.
     * @param size the number of random inputs to generate.
     * @param level the level to start the game on.
     */
    public void actionTest(int size, String level) throws AWTException {
        game.skipTo(level);
        InputGenerator input = new InputGenerator();
        Robot robot = new Robot();

        List<Runnable> actions_without_up = List.of( input::down, input::down, input::left, input::right);
        List<Runnable> actions_without_down = List.of( input::up, input::up, input::left, input::right);
        List<Runnable> actions_without_left = List.of( input::up, input::down, input::right, input::right);
        List<Runnable> actions_without_right = List.of( input::up, input::down, input::left, input::left);

        robot.delay(2000);
        int index = 3;
        for (int i = 0; i < size; i++) {

            // prevent the player from making meaningless moves( like moving up and down at the same time)
            List<Runnable> from = switch(index){
                case 0 -> actions_without_down;
                case 1 -> actions_without_up;
                case 2 -> actions_without_right;
                default -> actions_without_left;
            };

            testClock();

            from.get(index).run();
            System.out.println("Action: " + printActionName(index));
            robot.delay(10);
            index = random.nextInt(from.size());
        }
    }
    /**
     * This method is used to print the name of the action which works for actionTest() method.
     * @param index the index of the action.
     * @return the name of the action.
     */
    public String printActionName(int index) {
        return switch (index) {
            case 0 -> "up";
            case 1 -> "down";
            case 2 -> "left";
            case 3 -> "right";
            default -> "unknown";
        };
    }
    /**
     * This method tests the clock.
     * @throws IllegalStateException if the precondition is violated.
     */
    public void testClock() throws IllegalStateException {
        if(GameClock.get().currentLevelTime() < 0) {
            throw new IllegalStateException("Time is negative");
        }
    }
    public static void main(String[] args){}
    /**
     * This method is used to test level 1.
     * using int method to determine the strategy of generating random inputs.
     * method 0: using Strategy Pattern to generate random inputs with ProbInput Strategy.
     * method 1: using Strategy Pattern to generate random inputs with RandomInput Strategy.
     * method 2: randomKeys() method, using Robot to generate random Key Events.
     * method 3: actionTest() method, calling the action functions from the list of actions.
     * @throws AWTException if the Robot class is not supported.
     */
    @Test
    public void test1() throws AWTException {
        FuzzTest f = new FuzzTest();
        Input ProbInput = new Input( new ProbInput());
        Input FollowedInput = new Input( new FollowedInput());

        //Switch 'method' to determine the strategy of generating random inputs(0-3)
        int method = 3;
        switch (method) {
            case 0 -> f.testInputStrategy(ProbInput, 100000, "level1");
            case 1 -> f.testInputStrategy(FollowedInput, 100000, "level1");
            case 2 -> f.randomKeys(100000, "level1");
            case 3 -> f.actionTest(100000, "level1");
            default -> {
            }
        }

    }
    /**
     * This method is used to test level 2.
     * using int method to determine the strategy of generating random inputs.
     * method 0: using Strategy Pattern to generate random inputs with ProbInput Strategy.
     * method 1: using Strategy Pattern to generate random inputs with RandomInput Strategy.
     * method 2: randomKeys() method, using Robot to generate random Key Events.
     * method 3: actionTest() method, calling the action functions from the list of actions.
     * @throws AWTException if the Robot class is not supported.
     */
    @Test
    public void test2() throws AWTException {
        FuzzTest f = new FuzzTest();
        Input ProbInput = new Input( new ProbInput());
        Input FollowedInput = new Input( new FollowedInput());

        //Switch 'method' to determine the strategy of generating random inputs(0-3)
        int method = 0;
        switch (method) {
            case 0 -> f.testInputStrategy(ProbInput, 100000, "level2");
            case 1 -> f.testInputStrategy(FollowedInput, 100000, "level2");
            case 2 -> f.randomKeys(100000, "level2");
            case 3 -> f.actionTest(100000, "level2");
            default -> {
            }
        }
    }
}
/**
 * This the interface of the Strategy Pattern.
 */
interface InputStrategy {
    int nextInput(Input input);
    boolean isPressCtrl(Input input);
}
/**
 * This class is used to generate random inputs from the previous inputs.
 */
class FollowedInput implements InputStrategy {
    Random random = new Random();
    private final int[] keys = {
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_ESCAPE,
//            KeyEvent.VK_S,
//            KeyEvent.VK_R
    };

    /**
     * This is the constructor of the FollowedInput class.
     */
    public FollowedInput() {
    }

    /**
     * This method is used to generate random inputs for the game by following the previous input.
     *
     * @param input the input.
     * @return the next input.
     */
    @Override
    public int nextInput(Input input) {
        int[] keysWithoutUp = {KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};
        int[] keysWithoutDown = {KeyEvent.VK_UP, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};
        int[] keysWithoutLeft = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};
        int[] keysWithoutRight = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};
//        int[] keyAfterS = { KeyEvent.VK_R };
//        int[] keyAfterR = { KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE, KeyEvent.VK_S, KeyEvent.VK_R };
        int[] keyAfterSpace = {KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};
        int[] keyAfterEscape = {KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};

        int[] from = switch (input.prevKey) {
            case KeyEvent.VK_UP -> keysWithoutDown;
            case KeyEvent.VK_DOWN -> keysWithoutUp;
            case KeyEvent.VK_LEFT -> keysWithoutRight;
            case KeyEvent.VK_RIGHT -> keysWithoutLeft;
//            case KeyEvent.VK_S -> keyAfterS;
//            case KeyEvent.VK_R -> keyAfterR;
            case KeyEvent.VK_SPACE -> keyAfterSpace;
            case KeyEvent.VK_ESCAPE -> keyAfterEscape;
            default -> keys;
        };

        return from[random.nextInt(from.length)];
    }

    /**
     * This method is used to check if the input is a control key.
     *
     * @param input the input.
     * @return true if the input is a control key.
     */
    @Override
    public boolean isPressCtrl(Input input) {
        return (input.key == KeyEvent.VK_S || input.key == KeyEvent.VK_R);
    }
}
/**
 * This class is used to handle the input.
 */
class Input {
    InputStrategy inputStrategy;
    int prevKey;
    int key;

    /**
     * Constructor for Input.
     * @param inputStrategy the input strategy to use.
     */
    public Input( InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    /**
     * This method is used to get the next input.
     * @return the next KeyEvent.
     */
    public int nextInput() {
        key = inputStrategy.nextInput(this);
        prevKey = key;
        return key;
    }

    /**
     * This method is used to check if the next input is a ctrl key.
     * @return true if the next input is a ctrl key.
     */
    public boolean isPressCtrl() {
        return inputStrategy.isPressCtrl(this);
    }
}

/**
 * This class is used to generate random inputs with a different probability.
 */
class ProbInput implements InputStrategy {
    Random random = new Random();
    private final int[] keys = {
            KeyEvent.VK_UP,
            KeyEvent.VK_DOWN,
            KeyEvent.VK_LEFT,
            KeyEvent.VK_RIGHT,
            KeyEvent.VK_SPACE,
            KeyEvent.VK_ESCAPE,
//            KeyEvent.VK_S,
//            KeyEvent.VK_R
    };
    /**
     * This is the constructor of the ProbInput class.
     */
    public ProbInput() {}
    /**
     * This method is used to generate random inputs for the game with different probabilities.
     * @param input the input.
     * @return the next input.
     */
    @Override
    public int nextInput(Input input) {
        double[] weightMost = {0.22, 0.22, 0.22, 0.22, 0.06, 0.06};
        double[] weightAfterUp = {0.3, 0, 0.3, 0.3, 0.05, 0.05};
        double[] weightAfterDown = {0, 0.3, 0.3, 0.3, 0.05, 0.05};
        double[] weightAfterLeft = {0.3, 0.3, 0.3, 0, 0.05, 0.05};
        double[] weightAfterRight = {0.3, 0.3, 0, 0.3, 0.05, 0.05};
//        double[] weightAfterS = {0, 0, 0, 0, 0, 0, 0, 1};
        double[] weightAfterSpace = {0.15, 0.15, 0.15, 0.15, 0.1, 0.3};

        double[] fromProb = switch (input.prevKey) {
            //case KeyEvent.VK_S -> weightAfterS;
            case KeyEvent.VK_UP -> weightAfterUp;
            case KeyEvent.VK_DOWN -> weightAfterDown;
            case KeyEvent.VK_LEFT -> weightAfterLeft;
            case KeyEvent.VK_RIGHT -> weightAfterRight;
            case KeyEvent.VK_SPACE -> weightAfterSpace;
            default -> weightMost;
        };

        int[] from = IntStream.range(0, fromProb.length)
                .filter(i -> fromProb[i] > 0)
                .mapToObj(i -> IntStream.range(0, (int) (fromProb[i] * 100)).map(j -> keys[i]).toArray())
                .flatMapToInt(IntStream::of)
                .toArray();

        return from[random.nextInt(from.length)];
    }
    /**
     * This method is used to check if the next input is a ctrl key.
     * @param input the input.
     * @return true if the next input is a ctrl key.
     */
    @Override
    public boolean isPressCtrl(Input input) {
        return (input.key == KeyEvent.VK_S || input.key == KeyEvent.VK_R);
    }
}
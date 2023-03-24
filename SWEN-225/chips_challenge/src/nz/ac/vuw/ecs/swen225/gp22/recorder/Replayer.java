package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.*;

import java.awt.event.KeyEvent;
import java.util.Stack;


/**
 * Replayer represents an object that can replay levels that have been recorded.
 * Implements Observer from App so the autoReplay is synced with the GameClock update()
 * Two modes of Replaying include AutoReplay, where each step is invoked at the LevelTime they
 * were executed in the original play-through, as the GameClock ticks. This ReplaySpeed can be altered by
 * the user to make the AutoReplay faster or slower.
 * And StepByStep allows User to move through each step made by chip in their own time,
 * one Step at a time either forwards or backwards.
 *
 * @author Santino Gaeta 300305101
 */
public class Replayer implements Observer {

    private static int replaySpeed = GameClock.get().DEFAULT_FRAMERATE;
    private static Stack<Step> history;
    private static Stack<Step> prevSteps;
    private String currentLevel;
    static boolean autoReplayState = true;
    private static InputGenerator inputGenerator;

    /**
     * Replayer initialised with a Stack of Chip's moves to go through and replay
     * Instantiates InputGenerator invoking moves on Chip
     * AutoReplay mode on default at default speed
     * @param gameHistory - Stack<Steps> Chip's movements to be replayed
     * @param level - A String of which level was recorded
     *
     * @author Santino Gaeta
     */
    public Replayer(Stack<Step> gameHistory, String level) {
        setHistory(gameHistory);
        currentLevel = level;
        setPrevStack();
        setInputGenerator();
        setReplayState(true);
        setReplaySpeed(GameClock.get().DEFAULT_FRAMERATE);
    }
        /**
     * Sets the history Stack with Chips's recorded Steps
     * @param steps - Stack of Chip's movements during recorded level
     *
     * @author Santino Gaeta
     */
    private void setHistory(Stack<Step> steps){
        history = steps;
    }

    /**
     * Initialises a Stack for holding the executed recorded Steps of Chips
     *
     * @author Santino Gaeta
     */
    private void setPrevStack(){
        prevSteps = new Stack<>();
    }

    /**
     * Instantiates a new InputGenerator for executing Chip's recorded movements
     *
     * @author Santino Gaeta
     */
    private void setInputGenerator(){
        inputGenerator = new InputGenerator();
    }

    /**
     * Sets the replay state of the Replayer to Automatic each time replay starts
     * @param auto - sets the ReplayState as automatic replay mode
     *
     * @author Santino Gaeta
     */
    private void setReplayState(boolean auto){
        autoReplayState = auto;
    }

    /**
     * Adds Keybindings to GameHandler to operate a Replayer altering replaySpeed and
     * StepByStep Replay forward and backwards movement - called in App module
     *
     * @author Santino Gaeta
     */
    public void setBindings(){
        GameHandler.get().addBindings(KeyEvent.VK_EQUALS, Replayer::speedUp, () -> {});
        GameHandler.get().addBindings(KeyEvent.VK_MINUS, Replayer::speedDown, () -> {});
        GameHandler.get().addBindings(KeyEvent.VK_PERIOD, Replayer::stepForward, () -> {});
        GameHandler.get().addBindings(KeyEvent.VK_COMMA, Replayer::stepBackward, () -> {});
    }

    /**
     * Implementing GameClock in App, Replayer will update each tick of GameClock
     * If the history Stack, empty time is speed up to return to Menu
     * If AutoReplay Mode and the next Step's time matches up with GameClock,
     * autoReplay() will replay the next movement of Chip
     *
     * @author Santino Gaeta
     */
    @Override
    public void update() {
        if(history.isEmpty()){GameHandler.get().skipTo("startmenu");}
        else if(autoReplayState && history.peek().getTime()==GameClock.get().currentLevelTime()){
            autoReplay();
        }
    }

    /**
     * Automatic Replay
     * If the next move occurs at the currentLevelTime of the GameClock - will replay move on update()
     * Also pushes each move into the prev Stack
     *
     * @author Santino Gaeta
     */
    public static void autoReplay(){
        Step currentStep = history.pop();
        prevSteps.push(currentStep);
        replayStep(currentStep);
    }

    /**
     * Step-by-Step Forward Replay
     * Sets Automatic replay off in case Automatic replay was currently on
     * Moves Step from history Stack to prevSteps Stack and replays that move direction
     *
     * @author Santino Gaeta
     */
    public static void stepForward(){
        if(history.isEmpty()){return;}
        autoReplayState = false;
        setReplaySpeed(15);
        Step currentMove = history.pop();
        prevSteps.push(currentMove);
        replayStep(currentMove);
    }

    /**
     * Step-by-Step Backwards Replay
     * Sets Automatic replay off in case Automatic replay was currently on
     * Moves Step from prevSteps Stack to back to history Stack and replays direction in reverse
     *
     * @author Santino Gaeta
     */

    public static void stepBackward(){
        if(prevSteps.isEmpty()){return;}
        setReplaySpeed(15);
        autoReplayState = false;
        Step currentMove = prevSteps.pop();
        history.push(currentMove);
        replayBackStep(currentMove);
    }

    /**
     * Invokes InputGenerator from App to move Chip forward a Step in Game during replay
     * Triggered by PLayer pressing "Period" / "." Key
     * @param step - Step popped from history Stack to be invoked on Chip
     *
     * @author Santino Gaeta
     */
    public static void replayStep(Step step){
        if(step.getMove().equals("Left")){inputGenerator.left();}
        else if (step.getMove().equals("Right")){inputGenerator.right();}
        else if (step.getMove().equals("Up")){inputGenerator.up();}
        else if (step.getMove().equals("Down")){inputGenerator.down();}
    }

    /**
     * Invokes InputGenerator from App to move Chip back a Step in Game during replay
     * Triggered by Player pressing "COMMA" / "," key
     * @param step - Step popped from history Stack to be invoked on Chip
     *
     * @author Santino Gaeta
     */
    public static void replayBackStep(Step step){
        if(step.getMove().equals("Left")){inputGenerator.right();}
        else if (step.getMove().equals("Right")){inputGenerator.left();}
        else if (step.getMove().equals("Up")){inputGenerator.down();}
        else if (step.getMove().equals("Down")){inputGenerator.up();}
    }

    /**
     * Changes the replaySpeed of the automatic replay
     * @param newSpeed - how many ticks the GameClock makes each second
     *
     * @author Santino Gaeta
     */
    public static void setReplaySpeed(int newSpeed){
        replaySpeed = newSpeed;
        GameClock.setTickRate(replaySpeed);
        restartClock();
    }

    /**
     * Triggered by the "EQUALS" / '=' Key on the keyboard, when pressed increases the tickrate
     * of Gameclock increments of 15ticks/sec  with each press, capped at 3x Speed
     *
     * @author Santino Gaeta
     */
    public static void speedUp(){
        if(replaySpeed > 180){return;}
        replaySpeed += 15;
        GameClock.setTickRate(replaySpeed);
        restartClock();
    }

    /**
     * Triggered by the "MINUS" / '-' Key on the keyboard, when pressed decrease the tickrate
     * of Gameclock increments of -15ticks/sec  with each press, capped at 0.25x Speed
     *
     * @author Santino Gaeta
     */
    public static void speedDown(){
        if(replaySpeed < 20){return;}
        replaySpeed -= 15;
        GameClock.setTickRate(replaySpeed);
        restartClock();
    }

    /**
     * Pauses the GameClock briefly in case Clock wasn't paused already
     * and UnPauses to start update() ticks again.
     *
     * @author Santino Gaeta
     */
    public static void restartClock(){
        GameClock.get().pause();
        GameClock.get().unpause();
    }

    /**
     * Returns the level that this Replayer was recorded from
     * @return String - containing which level this Replayer will replay
     *
     * @author Santino Gaeta
     */
    public String getReplayLevel(){return currentLevel;}

}

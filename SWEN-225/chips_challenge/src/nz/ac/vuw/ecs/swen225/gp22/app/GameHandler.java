package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Replayer;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Render;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

/**
 * The primary game management class for a game of chips challenge that is responsible for the lifetime and handling
 * of all major components.
 *
 * @author niamh
 */
public class GameHandler implements Observer {
    static GameHandler instance;
    private final ObserverAdapter domain;
    private final Recorder recorder;
    protected Replayer currentReplay;
    private Viewport viewport;
    private final InputHandler input;

    /**
     * Construct a new GameHandler and make sure one and only one instance exists.
     *
     * @author niamh
     */
    private GameHandler() {
        // Create fields.
        domain = new ObserverAdapter(new Domain());
        recorder = new Recorder();

        input = new InputHandler(domain.get(), recorder);
        setBindings(input);

        try {
            SwingUtilities.invokeAndWait(() -> { viewport = new Viewport(input); });
        }
        catch (InvocationTargetException | InterruptedException e) { e.printStackTrace(); }

        // Start the game and game clock.
        start();
        if (instance != null) throw new IllegalStateException("GameHandler has already been instantiated. Use GameHandler.get() to retrieve the instance.");
    }

    /**
     * Retrieve the GameHandler instance from other classes.
     * @return the current instance of GameHandler.
     *
     * @author niamh
     */
    public static GameHandler get() {
        if (instance == null) instance = new GameHandler();
        return instance;
    }

    /**
     * Retrieve the input handler currently assigned to the GameHandler instance.
     * @return an instance of input currently tied to GameHandler
     *
     * @author niamh
     */
    protected InputHandler getInput() {
        return input;
    }

    /**
     * Retrieve the domain currently assigned to the GameHandler instance.
     * @return an instance of domain tied to GameHandler
     *
     * @author niamh
     */
    protected Domain domain() {
        return domain.get();
    }

    /**
     * Retrieve the recorder instance currently assigned to the GameHandler instance.
     * @return an instance of recorder tied to GameHandler
     *
     * @author niamh
     */
    protected Recorder recorder() {
        return recorder;
    }

    /**
     * Check for fail conditions.
     *
     * @author niamh
     */
    @Override
    public void update() {
        if (GameClock.get().currentLevelTime() <= 0)
            onFail();
    }

    /**
     * Represents a simple adapter to translate domain into something registrable by Subject.
     *
     * @author niamh
     */
    class ObserverAdapter implements Observer {
        Domain d;
        protected Domain get() { return d; }
        protected ObserverAdapter(Domain d) { this.d = d; }
        @Override public void update() { domain.get().update(); }
    }

    /**
     * Set all the keybindings that will be used in this game of Chip's Challenge.
     * @param input the InputHandler currently tied to this GameHandler.
     *
     * @author niamh
     */
    private void setBindings(InputHandler input) {
        input.addBinding(KeyEvent.VK_UP,      input::mvUp,    () -> {});
        input.addBinding(KeyEvent.VK_DOWN,    input::mvDown,  () -> {});
        input.addBinding(KeyEvent.VK_LEFT,    input::mvLeft,  () -> {});
        input.addBinding(KeyEvent.VK_RIGHT,   input::mvRight, () -> {});
        input.addBinding(KeyEvent.VK_SPACE,   input::pause,   () -> {});
        input.addBinding(KeyEvent.VK_ESCAPE,  input::unpause, () -> {});

        input.addBinding(KeyEvent.VK_CONTROL, input::setAlternateControls, () -> {});
        input.addAlternateBinding(KeyEvent.VK_CONTROL, () -> {}, input::unsetAlternateControls);

        input.addAlternateBinding(KeyEvent.VK_R, input::resumeGame,   () -> {});
        input.addAlternateBinding(KeyEvent.VK_X, input::exitGame,     () -> {});
        input.addAlternateBinding(KeyEvent.VK_S, input::saveGame,     () -> {});
        input.addAlternateBinding(KeyEvent.VK_1, input::skipToLevel1, () -> {});
        input.addAlternateBinding(KeyEvent.VK_2, input::skipToLevel2, () -> {});
    }

    /**
     * Utility method that allows other modules and parts of the game to add their own bindings to the input handler
     * allowing them to handle their own needs.
     * @param keyCode the keyCode to detect.
     * @param onPress the action to be run when the key is pressed.
     * @param onRelease the action to be run when the key is released.
     *
     * @author niamh
     */
    public void addBindings(Integer keyCode, Runnable onPress, Runnable onRelease) {
        input.addBinding(keyCode, onPress, onRelease);
    }

    /**
     * Utility method that allows other modules and parts of the game to add their own alternate bindings to the input
     * handle allowing them to handle their own needs. (Alternate bindings are when Ctrl is held.)
     * @param keyCode the keyCode to detect.
     * @param onPress the action to be run when the key is pressed.
     * @param onRelease the action to be run when the key is released.
     *
     * @author niamh
     */
    public void addAltBindings(Integer keyCode, Runnable onPress, Runnable onRelease) {
        input.addAlternateBinding(keyCode, onPress, onRelease);
    }

    /**
     * Start the game.
     *
     * @author niamh
     */
    public void start() {
        GameClock.get().register(viewport);
        viewport.setState(new StartScreen());
        viewport.repack();
        GameClock.get().start();
    }

    /**
     * Resets game cleanly and leaves it in a "stopped" state.
     *
     * @author niamh
     */
    public void reset() {
        GameClock.get().stop();
        GameClock.reset();
        GameClock.get().unregister(this);
        GameClock.get().unregister(viewport);
    }

    /**
     * Sets the GameHandler to expect a replayer and make the appropriate preparations.
     * @param replayer the instance of replayer currently being used.
     *
     * @author niamh
     */
    public void setReplayer(Replayer replayer) {
        skipTo(replayer.getReplayLevel());
        input.clearBindings();
        replayer.setBindings();
        currentReplay = replayer;
        GameClock.get().register(replayer);
    }

    /**
     * Retrieves information for the current level.
     * @return a LevelTracker instance with the correct information.
     */
    public static LevelTracker getLevelInfo() {
        var gs = instance.viewport.getGameState();
        if(gs instanceof Level) return ((Level)gs).levelInfo();
        return LevelTracker.NONE;
    }

    /**
     * Skips to a level from anywhere in the game. Can be used by other classes to set level for testing purposes.
     * @param str a string containing the level name.
     *
     * @author niamh
     */
    public void skipTo(String str) {
        System.out.println(str);
        if (currentReplay != null) {
            GameClock.get().unregister(currentReplay);
            currentReplay = null;
        }
        switch (str.toLowerCase()) {
            case "level1" -> {
                System.out.println("You are now at level one.");
                setGameState(new Level(LevelTracker.LEVEL1, domain.get(), new Render()));
            }
            case "level2" -> {
                System.out.println("You are now at level two.");
                setGameState(new Level(LevelTracker.LEVEL2, domain.get(), new Render()));
            }
            case "startmenu" -> setGameState(new StartScreen());
            default -> {
                System.out.println(str + " is not a level that exists.");
            }
        }
    }

    /**
     * Internal functionality for changing the game to any arbitrary GameState.
     * @param state the state that the game will be switched to.
     *
     * @author niamh
     */
    protected void setGameState(GameState state) {
        input.clearBindings();
        if (currentReplay != null) {
            GameClock.get().unregister(currentReplay);
            currentReplay = null;
        }
        GameClock.get().unregister(viewport);
        GameClock.get().unregister(domain);
        viewport.setState(state);

        if (state instanceof Level) {
            setComponents((Level) state);
            setBindings(input);
            GameClock.get().setLevelTime(((Level) state).levelInfo().getTime());
        }

        GameClock.get().register(viewport);
        GameClock.get().register(this);

        viewport.validate();
        viewport.repack();
    }

    /**
     * Changes the level to the next level in order based on the current state.
     *
     * @author niamh
     */
    protected void onLevelChange() {
        input.clearBindings();
        if (currentReplay != null) {
            GameClock.get().unregister(currentReplay);
            currentReplay = null;
        }
        GameClock.get().unregister(this);
        GameClock.get().unregister(domain);
        GameClock.get().unregister(viewport);
        if (viewport.getGameState() instanceof Level) recorder.saveRecording();

        viewport.setState(viewport.getGameState().nextLevel());

        if (viewport.getGameState() instanceof Level) {
            setComponents((Level) viewport.getGameState());
            setBindings(input);
            GameClock.get().setLevelTime(((Level) viewport.getGameState()).levelInfo().getTime());
        }


        GameClock.get().register(this);
        GameClock.get().register(viewport);

        viewport.validate();
        viewport.repack();
    }

    /**
     * Responsible for handling what the game does on a death of the player.
     *
     * @author niamh
     */
    protected  void onFail() {
        input.clearBindings();
        GameClock.get().unregister(this);
        setGameState(new StartScreen());
    }

    /**
     * Sets domain to the correct level and resets the recorder for use on a new level.
     * @param level the level that the game will be changing to.
     *
     * @author niamh
     */
    private void setComponents(Level level) {
            // Start the next level in domain.
            domain.get().startLevel(level.levelInfo().currentPath(), this::onLevelChange, this::onFail);
            assert domain.get().ok();
            GameClock.get().register(domain);

            // Reset the recorder.
            recorder.reset();
            recorder.setLevel(level.levelInfo().currentName());
            recorder.start();

            // Register domain to the renderer instance.
            level.gameplayPanel().setUp(domain.get());
    }
}
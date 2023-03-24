package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.GameSave;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers.GameSaveHelper;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Render;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Responsible for handling user input for a game of chips challenge.
 * Captures events then translates them for an instance of domain and renderer based on a pair of maps of keybindings
 *
 * @author niamh
 */
class InputHandler implements KeyListener {
    private final Domain domain;
    private final Recorder recorder;
    private HashMap<Integer, Runnable> currentPressedMap;
    private HashMap<Integer, Runnable> currentReleasedMap;
    private HashMap<Integer, Runnable> pressed;
    private HashMap<Integer, Runnable> released;
    private HashMap<Integer, Runnable> alternatePressed;
    private HashMap<Integer, Runnable> alternateReleased;
    private HashMap<Integer, Boolean> currentlyLocked;

    /**
     * Sets up an InputHandler and ties it to an instance of domain and recorder.
     * @param domain the domain that will be receiving translated key events.
     * @param recorder the recorder that will be receiving translated key events.
     *
     * @author niamh
     */
    InputHandler(Domain domain, Recorder recorder) {
        this.domain = domain;
        this.recorder = recorder;
        pressed = new HashMap<>();
        released = new HashMap<>();
        alternatePressed= new HashMap<>();
        alternateReleased = new HashMap<>();

        currentPressedMap = pressed;
        currentReleasedMap = released;

        currentlyLocked = new HashMap<>();
    }

    /**
     * Command callable by other classes to add a binding and its pressed and released functionality to the relevant default map.
     * @param key an integer code representing a key both in the maps and on the keyboard.
     * @param pressed a Runnable lambda or class method passed as the functionality on pressing a key.
     * @param released a Runnable lambda or class method passed as the functionality on releasing a key.
     *
     * @author niamh
     */
    protected void addBinding(Integer key, Runnable pressed, Runnable released) {
        this.pressed.put(key, pressed);
        this.released.put(key, released);
    }

    /**
     * Command callable by other classes to add a binding and its pressed and released functionality to the relevant alternate map.
     * @param key an integer code representing a key both in the maps and on the keyboard.
     * @param pressed a Runnable lambda or class method passed as the functionality on pressing a key.
     * @param released a Runnable lambda or class method passed as the functionality on releasing a key.
     *
     * @author niamh
     */
    protected void addAlternateBinding(Integer key, Runnable pressed, Runnable released) {
        this.alternatePressed.put(key, pressed);
        this.alternateReleased.put(key, released);
    }

    /**
     * Resets the key HashMaps to get ready for a new stage with different bindings. Allows for locking of keys in
     * certain modes.
     *
     * @author niamh
     */
    protected void clearBindings() {
        pressed = new HashMap<>();
        released = new HashMap<>();
        alternatePressed= new HashMap<>();
        alternateReleased = new HashMap<>();

        currentPressedMap = pressed;
        currentReleasedMap = released;

        currentlyLocked = new HashMap<>();
    }

    /**
     * Move a player up/north and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void mvUp() {
        domain.movePlayer(Direction.Up);
        recorder.up();
    }

    /**
     * Move a player south/south and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void mvDown() {
        domain.movePlayer(Direction.Down);
        recorder.down();
    }

    /**
     * Move a player left/east and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void mvLeft() {
        domain.movePlayer(Direction.Left);
        recorder.left();
    }

    /**
     * Move a player right/east and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void mvRight() {
        domain.movePlayer(Direction.Right);
        recorder.right();
    }

    /**
     * Move a player right/east and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void pause() {
        GameClock.pause();
    }
    /**
     * Move a player right/east and then record the movement to a recorder.
     *
     * @author niamh
     */
    protected void unpause() {
        GameClock.unpause();
    }

    /**
     * Sets the InputHandler to expect an alternate control with the control key modifier.
     *
     * @author niamh
     */
    protected void setAlternateControls() {
        currentPressedMap = alternatePressed;
        currentReleasedMap = alternateReleased;
    }

    /**
     * Sets the InputHandler to expect standard controls without the control key modifier.
     *
     * @author niamh
     */
    protected void unsetAlternateControls() {
        currentPressedMap = pressed;
        currentReleasedMap = released;
    }

    /**
     * Sets the InputHandler to expect standard controls without the control key modifier.
     *
     * @author niamh
     */
    protected void saveGame() {
        GameClock.get().stop();
        GameSave save = new GameSave(
                Arrays.stream(GameHandler.get().domain().getLevel().orElseThrow(() -> new Error("Current Level not found")).cells).flatMap(Stream::of).toList(),
                (int)GameClock.get().currentLevelTime(),
                GameHandler.get().domain().getLevel().orElseThrow(() -> new Error("Current Level not found")).getInventory(),
                Integer.parseInt(GameHandler.getLevelInfo().currentPath().substring(5)));
        try {
            GameSaveHelper.saveGameSave(save);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * Sets the InputHandler to expect standard controls without the control key modifier.
     *
     * @author niamh
     */
    protected void exitGame() {
        System.exit(0);
    }

    protected void resumeGame() {
        var fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("./saves/"));
        int response = fileChooser.showOpenDialog(null);
        if (response == 0) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            try {
                var sv = GameSaveHelper.loadGameSave(Path.of(file.getAbsolutePath()));
                var level = LevelTracker.valueOf("LEVEL" + sv.getLevelNumber());
                level.setCustomPath(file.getName().substring(0, file.getName().indexOf(".")));
                GameHandler.get().setGameState(new Level(level, GameHandler.get().domain(), new Render()));
                GameClock.get().setLevelTime(sv.getTime());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the InputHandler to expect standard controls without the control key modifier.
     *
     * @author niamh
     */
    protected void skipToLevel1() {
        GameHandler.get().skipTo("level1");
    }

    /**
     * Sets the InputHandler to expect standard controls without the control key modifier.
     *
     * @author niamh
     */
    protected void skipToLevel2() {
        GameHandler.get().skipTo("level2");
    }

    /**
     * Unused keyTyped method inherited from KeyListener interface.
     *
     * @author niamh
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    /**
     * Retrieves a keyEvent on detecting a key push and then runs the relevant action in pressed.
     *
     * @author niamh
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (currentlyLocked.getOrDefault(keyEvent.getKeyCode(), false)) return;
        if (GameClock.isPaused() && keyEvent.getKeyCode() != KeyEvent.VK_ESCAPE) return;
        currentPressedMap.getOrDefault(keyEvent.getKeyCode(), () -> {}).run();
        currentlyLocked.put(keyEvent.getKeyCode(), true);
    }

    /**
     * Retrieves a keyEvent on detecting a key push and then runs the relevant action in released.
     *
     * @author niamh
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (GameClock.isPaused() && keyEvent.getKeyCode() != KeyEvent.VK_ESCAPE) return;
        currentReleasedMap.getOrDefault(keyEvent.getKeyCode(), () -> {}).run();
        currentlyLocked.remove(keyEvent.getKeyCode());
    }

    /**
     * Responsible for translating a call from inside the program into an actual action based on what's currently in
     * the key maps. Respects locking as these aren't hard coded.
     * @param keyCode the key event to call.
     *
     * @author niamh
     */
    public void call(Integer keyCode) {
        currentPressedMap.getOrDefault(keyCode, () -> {}).run();
        currentReleasedMap.getOrDefault(keyCode, () -> {}).run();
    }

}

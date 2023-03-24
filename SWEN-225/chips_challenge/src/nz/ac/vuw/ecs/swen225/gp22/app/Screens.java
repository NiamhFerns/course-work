package nz.ac.vuw.ecs.swen225.gp22.app;

import nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers.GameSaveHelper;
import nz.ac.vuw.ecs.swen225.gp22.recorder.Recorder;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Render;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Represents the games start screen and holds the various actions that can be performed before beginning a game.
 *
 * @author niamh
 */
class StartScreen implements GameState, ActionListener {
    private List<JButton> buttons;

    /**
     * Build and return the start screen as a single panel.
     * @return returns a list containing a single panel holding the menu options.
     *
     * @author niamh
     */
    @Override
    public List<JPanel> panels() {
        JPanel menuframe = new JPanel();

        // Menu frame settings.
        menuframe.setLayout(null);
        menuframe.setFocusable(false);
        menuframe.setPreferredSize(new Dimension(640, 480));

        // Button to start the game.
        JButton startGame = new JButton();
        startGame.setBounds(220, 100, 200, 50);
        startGame.setText("Start Game");
        startGame.addActionListener(l -> GameHandler.get().onLevelChange());

        // Button to load a save.
        JButton loadGame = new JButton();
        loadGame.setBounds(220, 175, 200, 50);
        loadGame.setText("Load Game");
        loadGame.addActionListener(l -> {
            var fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("./saves/"));
            int response = fileChooser.showOpenDialog(menuframe);
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
        });

        // Button to load a recording.
        JButton loadRecording = new JButton();
        loadRecording.setBounds(220, 250, 200, 50);
        loadRecording.setText("Load Recording");
        loadRecording.addActionListener(l -> {
            try {
                GameHandler.get().setReplayer(GameHandler.get().recorder().loadRecording());
            } catch (NullPointerException e) {
                System.out.println("Unfortunately that save is not a valid file. Please try again.");
            }
        });

        // Button to quit.
        JButton quitGame = new JButton();
        quitGame.setBounds(220, 325, 200, 50);
        quitGame.setText("Quit Game");
        quitGame.addActionListener(l -> exit(0));

        buttons = List.of(startGame, loadGame, loadRecording, quitGame);

        buttons.forEach(menuframe::add);

        return List.of(menuframe);
    }

    /**
     * Basic constructor to set field defaults.
     */
    public StartScreen() {
        buttons = new ArrayList<>();
    }

    /**
     * Holds the next level to be started.
     * @return the following level in order.
     *
     * @author niamh
     */
    @Override
    public GameState nextLevel() {
        return new Level(LevelTracker.NONE.nextLevel(), GameHandler.get().domain(), new Render());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {}
}

package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Represents the viewport through which we view the game. This is the actual window that holds all the relevant panels
 * and holds the KeyListener.
 *
 * @author niamh
 */
class Viewport extends JFrame implements Observer {
    private GameState state;

    private List<JPanel> panels;


    /**
     * Construct a viewport and its size, layout, and defaults.
     * @param input the input handler that will detect the various key events on this Viewport instance.
     *
     * @author niamh
     */
    public Viewport(InputHandler input) {
        assert SwingUtilities.isEventDispatchThread();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Chips Challenge");
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));

        addKeyListener(input);

        panels = List.of();
    }

    /**
     * Repacks the viewport to change dimensions.
     *
     * @author niamh
     */
    public void repack() {
        pack();
    }

    /**
     * Repaints all panels on a tick of the Subject this is registered to.
     *
     * @author niamh
     */
    @Override
    public void update() {
        panels.forEach(JPanel::repaint);

    }

    /**
     * Changes the game state and sets all the new panels accordingly.
     * @param state the GameState to change to.
     *
     * @author niamh
     */
    protected void setState(GameState state) {
        panels.forEach(this::remove);
        this.state = state;
        panels = state.panels();
        panels.forEach(this::add);
        pack();
    }

    /**
     * Retrieves the current state the game is in.
     * @return the current gameState assigned to this viewport instance.
     */
    protected GameState getGameState() {
        return state;
    }
}
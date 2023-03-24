package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;
import java.util.List;

/**
 * Represents a level or main screen in a game of chips challenge.
 *
 * @author niamh
 */
interface GameState {
    /**
     * Set up panels for this GameState
     *
     * @return list of JPanels for this GameState.
     *
     * @author niamh
     */
    List<JPanel> panels();
    /**
     * @return next GameState to change of on switching level. Conceptually a LinkedList of GameStates.
     *
     * @author niamh
     */
    GameState nextLevel();
}


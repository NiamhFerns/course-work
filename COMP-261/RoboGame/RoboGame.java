import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class RoboGame extends JFrame {

    private WorldComponent worldComp = new WorldComponent();
    private File code1, code2;

    public static final String ASSET_DIRECTORY ="./assets/";     // the folder containing the images for the robot
    public static final String CODE_DIRECTORY = "./programs/";   // the folder containing the robot programs 
    public static boolean debugDisplay = true;

    /**
     * Set up the interface for the game
     */
    public RoboGame() {
        super("Robots");   // initialise the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(worldComp, BorderLayout.CENTER);

        createMenus();
        pack();

        setLocationRelativeTo(null);

        setVisible(true);
    }


    /** Make the menus for the interface */
    private void createMenus() {
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        final JMenu loadMenu = new JMenu("Load Program");
        menu.add(loadMenu);

        final JMenu debugMenu = new JMenu("Debug ");
        menu.add(debugMenu);

        final JMenuItem load1 = makeMenuItem("Robot 1 (Red)",loadMenu, (ActionEvent e) -> {
                code1 = getCodeFile();
                if (code1 != null) {
                    worldComp.loadRobotProgram(1, code1);
                    worldComp.repaint();
                }
            });
        
        final JMenuItem load2 = makeMenuItem("Robot 2 (Blue)",loadMenu,(ActionEvent e) ->{
                code2 = getCodeFile();
                if (code2 != null) {
                    worldComp.loadRobotProgram(2, code2);
                    worldComp.repaint();
                }
            });
        final JMenuItem start = makeMenuItem ("Start",menu, null);
        start.addActionListener((ActionEvent e) -> {   // acts on the JMenuItem itself...
                load1.setEnabled(false);
                load2.setEnabled(false);
                start.setEnabled(false);
                worldComp.start();
            });

        final JMenuItem reset = makeMenuItem("Reset", menu,(ActionEvent e) ->{
                worldComp.reset();
                if (code1 != null) {
                    worldComp.loadRobotProgram(1, code1);
                }
                if (code2 != null) {
                    worldComp.loadRobotProgram(2, code2);
                }
                worldComp.repaint();
                load1.setEnabled(true);
                load2.setEnabled(true);
                start.setEnabled(true);
            });

        final JMenuItem debugOn = makeMenuItem("On", debugMenu,(ActionEvent e) -> {debugDisplay = true;});
        final JMenuItem debugOff = makeMenuItem("Off", debugMenu,(ActionEvent e) -> {debugDisplay = false;});
        JMenuItem quit = makeMenuItem("Quit", menu,(ActionEvent e) -> {System.exit(0);});

    }

    /** Utility method for making a JMenuItem on a JMenu */
    private JMenuItem makeMenuItem(String name, JMenu menu, ActionListener action){
        JMenuItem menuItem = new JMenuItem(name);
        menu.add(menuItem);
        if (action!=null) menuItem.addActionListener(action);
        return menuItem;
    }
    /** Utility method for making a JMenuItem on a JMenuBar */
    private JMenuItem makeMenuItem(String name, JMenuBar menu, ActionListener action){
        JMenuItem menuItem = new JMenuItem(name);
        menu.add(menuItem);
        if (action!=null) menuItem.addActionListener(action);
        return menuItem;
    }


    /*
     * Ask the user (using a file dialog) for a program to load.
     */
    public File getCodeFile() {
        JFileChooser chooser = new JFileChooser(CODE_DIRECTORY);// Make sure this is the directory where the programs are
        int res = chooser.showOpenDialog(this);
        if (res == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    /**
     * This is the entry point into the program.
     */
    public static void main(String[] args) {
        new RoboGame();
    }
}

package nz.ac.vuw.ecs.swen225.gp22.renderer;

import gameImages.Img;
import nz.ac.vuw.ecs.swen225.gp22.domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Render class is the main class of the Renderer Module.
 * This class draws to the Graphics pannel supplied by App and calls the Sound class to create sound effects.
 *
 * @author kittmcevoy 300522463
 * */
public class Render extends JPanel {

    private Domain domain;
    private final List<Object> soundToPlay = new ArrayList<Object>();
    /**
     * Default constructor for an instance of Render.
     */
    public Render() {

    }

    /**
     * Accepts the Domain instance for the game.
     *
     * @param d Domain instance used for the game
     */
    public void setUp(Domain d) {
        this.domain = d;
    }

    /**
     * Checks that the domain has been set.
     *
     * @return boolean, if domain is set on Render side
     */
    public boolean domainSet() {
        return (domain != null);
    }


    /**
     * A method to find the cell that Chip is currently standing on. This method helps with being able
     * to figure out the cells that need to be drawn and the cells that do not.
     *
     * @return Array of two integers, the first being the x co-ordinate and the second is the y co-ordinate
     * @param cells, A 2d array of cells
     */
    private ArrayList<Integer> findChip(Cell[][] cells) {
        ArrayList<Integer> chipsCells = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                for (Entity e : cells[i][j].getEntities()) {
                    if (e.getClass().equals(Chip.class)) {
                        chipsCells.add(i);
                        chipsCells.add(j);
                        return chipsCells;
                    }
                }
            }
        }
        return null;
    }


    /**
     * This method breaks a longer String into multiple 12 character strings.
     *
     * @param text A string to break up
     * @return an Array of Strings broken up into 12 character
     */
    public static ArrayList<String> splitString(String text) {
        ArrayList<String> results = new ArrayList<>();
        String[] strings = text.split(" ");
        for (int i = 0; i < strings.length; i++) {
            if(i < strings.length-2){
                if(strings[i].length() + strings[i+1].length() + strings[i+2].length() <= 25){
                    results.add(strings[i]+ " " + strings[i+1] + " " + strings[i+2]);
                    i++;
                    i++;
                }
                else if(strings[i].length() + strings[i+1].length() <= 25){
                    results.add(strings[i]+ " " + strings[i+1]);
                    i++;
                }
            }
            else if(i < strings.length -1){
                if(strings[i].length() + strings[i+1].length() <= 25){
                    results.add(strings[i]+ " " + strings[i+1]);
                    i++;
                }
            }
            else{
                results.add(strings[i]);
            }
        }
        return results;
    }

    /**
     * This is the method that draws all the tiles, entities and characters.
     * As well as playing sound effects.
     *
     * @param g Graphics pannel to draw to
     */
    public void paintComponent(Graphics g) {
        boolean cellsPresent = this.domain.getLevel().isPresent();
        Cell[][] cells;
        boolean drawInfo = false;
        String infoText = null;
        if (cellsPresent) {
            cells = this.domain.getLevel().get().cells;

            int width = 9;
            int tileSize = getWidth() / width;

            ArrayList<Integer> chipsCells = findChip(cells);
            assert chipsCells != null;

            int i = 0;
            int j = 0;
            for (int col = -4; col < 5; col++) {
                for (int row = -4; row < 5; row++) {
                    if (col + chipsCells.get(0) < 0 || col + chipsCells.get(0) >= cells.length
                            || row + chipsCells.get(1) < 0 || row + chipsCells.get(1) >= cells.length) {
                        g.drawImage(Img.Freetile.image, j * tileSize, i * tileSize, tileSize, tileSize, null);
                    }
                    else {
                        Cell c = cells[col + chipsCells.get(0)][row + chipsCells.get(1)];
                        g.drawImage(c.getTileImage(), j * tileSize, i * tileSize, tileSize, tileSize, null);

                        Optional<Image> entityOptionalImage = c.getImage();
                        boolean entityBoolean = entityOptionalImage.isPresent();
                        if(entityBoolean){
                            g.drawImage(entityOptionalImage.get(), j * tileSize, i * tileSize, tileSize, tileSize, null);
                        }
                        for(Entity e : c.getEntities()){
                            if(e.getClass().equals(Chip.class)) {
                                int soundArraySize = ((Chip) e).level.getSoundsToPlay().size();
                                List<Object> soundArray = ((Chip) e).level.getSoundsToPlay();
                                if(soundToPlay.size() != soundArraySize){
                                    Object sound = soundArray.get(soundArraySize-1);
                                    if(sound.getClass().equals(Key.class)){
                                        Sound s = new Sound();
                                        s.setFile(0);
                                        s.play();
                                    }
                                    else if(sound.getClass().equals(LockedDoor.class)){
                                        Sound s = new Sound();
                                        s.setFile(1);
                                        s.play();
                                    }
                                    else if(sound.getClass().equals(Treasure.class)){
                                        Sound s = new Sound();
                                        s.setFile(2);
                                        s.play();
                                    }
                                    soundToPlay.add(soundArray.get(soundArraySize-1));
                                }
                            }
                            if(c.getStoredTile().getClass().equals(Info.class)){
                                if(e.getClass().equals(Chip.class)){
                                    drawInfo = true;
                                    Info cellI = (Info) c.getStoredTile();
                                    infoText = cellI.infoText;
                                }
                                else{
                                    drawInfo = false;
                                }
                            }
                        }
                    }
                    j++;
                }
                j = 0;
                i++;
            }

            if(drawInfo){
                ArrayList<String> strings = splitString(infoText);
                int l = 0;
                g.setColor(Color.BLUE);
                g.setFont(new Font("SansSerif", Font.BOLD, 30));
                Graphics2D g2d = (Graphics2D) g.create();
                FontMetrics fm = g2d.getFontMetrics();
                for(String s : strings) {
                    g.drawString(s, getWidth() / 2 - fm.stringWidth(s) / 2, 180 + (l*30));
                    l++;
                }
            }
        }
    }
}
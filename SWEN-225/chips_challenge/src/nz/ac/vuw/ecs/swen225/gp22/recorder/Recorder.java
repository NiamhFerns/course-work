package nz.ac.vuw.ecs.swen225.gp22.recorder;

import nz.ac.vuw.ecs.swen225.gp22.app.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Represents a Recorder implemented in App, where Recorder stores Chip's movements into an Array
 * of Steps.
 * Recorder remains being able to record multiple levels and storing them in xml format into the "./src/levels/" folder.
 * Recorder can also load those xml files from levels folder and produce a Replayer object, who can
 * replay those levels and re-enact Chip's movements during the level that was recorded.
 *
 * @author Santino Gaeta 300305101
 */
public class Recorder {

    private ArrayList<Step> currentRecording;
    private String currentLevel = "zero";
    private boolean startRecording = false;

    /**
     * Initialises List of RecordedLevels and List of moves from currentRecording
     *
     * @author Santino Gaeta
     */
    public Recorder(){
        currentRecording = new ArrayList<>();
    }

    /**
     * Sets Recorder to start recording inputs from App's InputHandler
     *
     * @author Santino Gaeta
     */
    public void start() {startRecording = true;}

    /**
     * Triggers once currentRecording is saved at the end of Level
     * Clears the currentRecording of all moves from the level, ready for recording new level
     * Sets currentLevel to zero - will be set at beginning of each level
     * Sets Recorder to stop recording inputs - in case of a lull period before levels
     *
     * @author Santino Gaeta
     */
    public void reset(){
        currentRecording.clear();
        currentLevel = "zero";
        startRecording = false;
    }

    /**
     * From App, will let recorder know what level is currently being recorded
     * @param level - that Player is currently playing
     *
     * @author Santino Gaeta
     */
    public void setLevel(String level){
        this.currentLevel = level;
    }

    /**
     * Recording when Player moves Chip 'Up'
     *
     * @author Santino Gaeta
     */
    public void up(){
        if(startRecording){
            long time = GameClock.get().currentLevelTime();
            currentRecording.add(new Step("Up", time));
        }
    }

    /**
     * Recording when Player moves Chip 'Down'
     *
     * @author Santino Gaeta
     */
    public void down(){
        if(startRecording){
            long time = GameClock.get().currentLevelTime();
            currentRecording.add(new Step("Down", time));
        }
    }

    /**
     * Recording when Player moves Chip 'Left'
     *
     * @author Santino Gaeta
     */
    public void left(){
        if(startRecording){
            long time = GameClock.get().currentLevelTime();
            currentRecording.add(new Step("Left", time));
        }
    }

    /**
     * Recording when Player moves Chip 'Right'
     *
     * @author Santino Gaeta
     */
    public void right(){
        if(startRecording){
            long time = GameClock.get().currentLevelTime();
            currentRecording.add(new Step("Right", time));
        }
    }

    /**
     * Recording when game updates and Player has not moved
     *
     * @author Santino Gaeta
     */
    public void none(){
        if(startRecording){
            long time = GameClock.get().currentLevelTime();
            currentRecording.add(new Step("None", time));
        }
    }

    /**
     * Converts an Array of Steps into XmlFile and save in folder "./src/levels/"
     * Then resets the Recorder - ready for recording a new level
     *
     * @author Santino Gaeta
     */
    public void saveRecording(){
        if(currentRecording.size() == 0){return;}
        saveStepArrayListToXml(currentRecording, currentLevel);
        reset();
    }

    /**
     *  Called via App's Start Screen - clicking on "Load Recording" button
     *  loadRecording brings up a file picker for the user to choose which xmlFile Recording to laod
     *  The file's name is checked and sets the level - used to create Replayer with
     *  Converts the file into Stack using convertXmlToStack(File) and returns a Replayer
     *
     * @return Replayer  - initialised with Stack from converted xmlFile using convertXmlToStack(File)
     *
     * @author Santino Gaeta
     */
    public Replayer loadRecording(){
        JFileChooser jfc = new JFileChooser("./src/levels/");
        jfc.setDialogTitle("Select XmlFile of Recording to Replay");
        jfc.setFileFilter(new FileNameExtensionFilter("Extensible Markup Language", "xml"));
        int check = jfc.showOpenDialog(null);
        if(check == JFileChooser.CANCEL_OPTION) {
            return null;
        }
        File xmlFile = jfc.getSelectedFile();
        if(xmlFile.toString().contains("level1Recording")){currentLevel = "level1";}
        else if(xmlFile.toString().contains("level2Recording")){currentLevel = "level2";}
        else{return null;}
        return new Replayer(convertXmlToStack(xmlFile), currentLevel);
    }

    /**
     * Creates an XmlMapper to convert each Step within the ArrayList into an XmlFile
     * Then stores the XmlFile in "./src/levels/" either under level1Recording.xml or
     * level2Recording.xml, depending on what level was recorded
     *
     * @param chipMoves - ArrayList of Steps containing Chip's movement during the recorded level
     * @param level - name of the Level that was recorded
     *
     * @author Santino Gaeta
     */
    public void saveStepArrayListToXml(ArrayList<Step> chipMoves, String level){
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try{
            if(level.equals("Level One")) {
                mapper.writeValue(new File("./src/levels/level1Recording.xml"), chipMoves);
            }
            else{
                mapper.writeValue(new File("./src/levels/level2Recording.xml"), chipMoves);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an XmlMapper and grabs the chosen XmlFile via InputStream
     * Using the TypeReference for the Xml to be converted back into an ArrayList<Step>
     * Then in reverse order, creates a Stack of Steps for the Replayer to use so moves are in
     * correct order to .pop() and replay.
     * Checks if a move is created at time 9000 and removes it
     *
     * @param xmlFile - Chosen by User in loadRecording()
     * @return Stack<Step> - the history of Chip's moves with first move at the top
     *
     * @author Santino Gaeta
     */
    public static Stack<Step> convertXmlToStack(File xmlFile){
        XmlMapper mapper = new XmlMapper();
        try{
            InputStream input = new FileInputStream(xmlFile);
            TypeReference<ArrayList<Step>> step = new TypeReference<>(){};
            ArrayList<Step> chipHistory = mapper.readValue(input, step);
            Stack<Step> history = new Stack<>();
            for(int i = chipHistory.size()-1; i >= 0; i--){
                history.push(chipHistory.get(i));
            }
            if(history.peek().getTime()==90000){
                history.pop();
            }
            return history;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

}


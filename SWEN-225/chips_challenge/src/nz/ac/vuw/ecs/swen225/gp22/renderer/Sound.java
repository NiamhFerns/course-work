package nz.ac.vuw.ecs.swen225.gp22.renderer;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;


/**
 * Sound class to construct clips for sound effects
 *
 *  * @author kittmcevoy 300522463
 */
public class Sound {
    private Clip clip;
    private final URL[] soundURL = new URL[5];


    /**
     * Creates a list of the sounds that are used for the game
     * */
    public Sound() {
        soundURL[0] = getClass().getResource("/nz/ac/vuw/ecs/swen225/gp22/renderer/Sound/keys.wav");
        soundURL[1] = getClass().getResource("/nz/ac/vuw/ecs/swen225/gp22/renderer/Sound/door_opening.wav");
        soundURL[2] = getClass().getResource("/nz/ac/vuw/ecs/swen225/gp22/renderer/Sound/cat.wav");
    }

    /**
     * Sets the file to be played.
     *
     * @param i, the number of the file to be played
     * */
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the sound effect
     * */
    public void play(){
        clip.start();
    }

}
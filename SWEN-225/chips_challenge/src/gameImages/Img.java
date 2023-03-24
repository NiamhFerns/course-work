package gameImages;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;


/**
 * Enum for creating and loading images
 *
 *  * @author kittmcevoy 300522463
 * */
public enum Img{
    BlueLockeddoor,
    Chip,
    Movingblock,
    Monster,
    Pit,
    Exitlock,
    Exittile,
    Freetile,
    GreenLockeddoor,
    Infotile,
    RedLockeddoor,
    Walltile,
    YellowLockeddoor,
    Bluekey,
    Greenkey,
    Yellowkey,
    Redkey,
    Treasure;

    public final BufferedImage image;
    Img(){image=loadImage(this.name());}
    /**
     * Loads each image in and gives them a way to be called
     *
     * @param name, name of the image file
     * */
    static private BufferedImage loadImage(String name){
        URL imagePath = Img.class.getResource(name+".png");
        try{return ImageIO.read(imagePath);}
        catch(IOException e) { throw new Error(e); }
    }
}
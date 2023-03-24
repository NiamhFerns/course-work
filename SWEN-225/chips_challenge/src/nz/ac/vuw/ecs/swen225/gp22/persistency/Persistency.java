package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import org.dom4j.Document;
import org.dom4j.Element;

public class Persistency {

    /**
     * loads xml file at path into a gameSave object.
     * @param path
     * @return
     */
    public static GameSave loadGameSave(Path path) {
        Document document = FileHandler.getXML(path);
        GameSave gameSave = new GameSave();
        gameSave.fromXml(document.getRootElement());
        return gameSave;
    }

    /**
     * writes the xml of a gameSave object to path given.
     * @param gameSave
     * @param path
     */
    public static void saveGameSave(GameSave gameSave, Path path) {
        FileHandler.saveXML(gameSave.toXml(),path.toString());
    }
}

/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.GameSave;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;

/**
 * Helper class for dealing with GameSave objects.
 */
public class GameSaveHelper {

    /**
     * Path that GameSaveHelper will use to load GameSave objects that have an empty inventory,
     * time set to 100 and cells list representing a start of game state.
     */
    public static final Path LEVEL_PATH = Path.of("./levels/");
    public static Path SAVE_PATH = Path.of("./saves/");

    public static String getFileName(GameSave gameSave) {
        return "save"+gameSave.hashCode()+".xml";
    }
    /**
     * Convenience method so modules could get Level 1 to play around with.
     * @return gamesave object for level 1.
     */
    public static GameSave getLevel1GameSave() {
        return new GameSave(LevelMap.get().getLevel1CellList(), 100, List.of(),1);
    }
    /**
     * Convenience method so modules could get Level 2 to play around with.
     * @return gamesave for level 2.
     */
    public static GameSave getLevel2GameSave() throws IOException {
        GameSave gameSave = loadGameSave(Path.of(LEVEL_PATH+"/level2.xml"));
        ServiceLoader<CustomMovingEntityService> loader = ServiceLoader.load(CustomMovingEntityService.class);
        if (loader.findFirst().isEmpty()) {
            return gameSave;
        }
        replaceDefaultCustomEntities(gameSave,loader);
        return gameSave;
    }
    /**
     * Used to replace default custom entities with first implementation found in jar
     * (For convenience getLevelXGameSave methods).
     * @param gameSave gamesave object to operate on
     * @param loader the service loader holding the providers
     */
    private static void replaceDefaultCustomEntities(GameSave gameSave,ServiceLoader<CustomMovingEntityService> loader) {
        List<Cell> cellList = gameSave.getCellList();
        List<Cell> cellsWithCustomEntities = cellList.stream()
            .filter(cell -> cell.getEntities().stream()
                .anyMatch(e -> e instanceof CustomMovingEntityService))
            .toList();
        cellsWithCustomEntities.forEach(cell -> {
            List<Entity> origEntities = cell.getEntities();
            List<Entity> customEntities = cell.getEntities().stream()
                .filter(e -> e instanceof CustomMovingEntityService).toList();
            customEntities.forEach(customEntity -> {
                int indexOfCustomEntity = origEntities.indexOf(customEntity);
                if (loader.findFirst().isPresent()) {
                    origEntities.set(indexOfCustomEntity,loader.findFirst().get());
                }
            });
            cell.setEntities(origEntities);
        });
    }

    /**
     * Used to load a game save but look for a service provider and replace the default implementation
     * with the service providers implementation.
     * @param path path of serialized gamesave.
     * @return deserialized game save with provided custom entity.
     * @throws IOException if jackson lib encounters problem parsing xml or finding file at path.
     */
    public static GameSave loadLevel(Path path) throws IOException {
        GameSave gameSave = loadGameSave(path);
        ServiceLoader<CustomMovingEntityService> loader = ServiceLoader.load(CustomMovingEntityService.class);
        if (loader.findFirst().isEmpty()) {
            return loadGameSave(path);
        }
        replaceDefaultCustomEntities(gameSave,loader);
        return gameSave;
    }

    /**
     *
     * @param path path of gamesave file
     * @return loaded gamesave object
     * @throws IOException if jackson lib encounters errors trying to locate file or parsing xml.
     */
    public static GameSave loadGameSave(Path path) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(path.toFile(), GameSave.class);
    }

    /**
     * Uses jacksons XmlMapper to write the gamesave to designated save directory.
     * @param gameSave gamesave object to serialize in saves directory.
     * @throws IOException if jackson lib encounters error generating xml for gamesave or can't
     * write the file.
     */
    public static void saveGameSave(GameSave gameSave) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // create dir if non existent
        if (!new File(SAVE_PATH.toUri()).exists()) {
            boolean mkdir = new File(SAVE_PATH.toUri()).mkdir();
            if (!mkdir) {
                throw new IOException("Can't create save dir");
            }
        }
        File file = new File(SAVE_PATH + "/save" + gameSave.hashCode()+".xml");
        mapper.writeValue(file,gameSave);
    }

}

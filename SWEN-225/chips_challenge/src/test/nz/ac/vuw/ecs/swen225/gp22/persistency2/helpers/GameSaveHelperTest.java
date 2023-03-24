/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers;

import static nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers.GameSaveHelper.getFileName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.GameSave;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.CustomMovingEntityService;
import org.junit.jupiter.api.Test;

/**
 * Tests to check GameSaveHelper's correct operation.
 */
class GameSaveHelperTest {
    @Test
    void loadLevel() throws IOException {
        /*
         *  I'm guessing the way I'm testing may be suboptimal in that while testing load I'm also using save
         *  to save and then load the save, I'm not sure what the better approach is.
         */
        Path tmpDir = Files.createTempDirectory("tmpSaveDir");
        GameSave gameSave = new GameSave(LevelMap.get().getLevel2CellList(), 100, List.of(),-1);

        GameSaveHelper.SAVE_PATH=tmpDir;
        GameSaveHelper.saveGameSave(gameSave);

        GameSave gameSave1 = GameSaveHelper.loadLevel(
            Path.of(tmpDir + "/" + getFileName(gameSave)));
        assertEquals(gameSave1,gameSave); // loaded level and source of truth level are equal so successful load.
    }
    @Test
    void getLevel1GameSave() {
        List<Cell> level1CellList = LevelMap.get().getLevel1CellList();
        GameSave gameSave = new GameSave(level1CellList, 100, List.of(),1);
        GameSave level1GameSave = GameSaveHelper.getLevel1GameSave();
        assertEquals(gameSave,level1GameSave);
    }
    @Test
    void getLevel2GameSave() throws IOException {
        GameSave level2GameSave = GameSaveHelper.getLevel2GameSave();
        Optional<Cell> cellWithCustomMovingEntity = level2GameSave.getCellList().stream().filter(cell -> cell.getEntities().stream().filter(e -> e.getClass().getName().equals(
            CustomMovingEntityService.class.getName())).isParallel()).findAny();
        assertTrue(cellWithCustomMovingEntity.isEmpty());
        // TODO compare against source of truth source code levels
    }

    @Test
    void loadGameSave() throws IOException {
        // TODO find way to do with mockito
        List<Cell> level1CellList = LevelMap.get().getLevel1CellList();
        GameSave gameSave = new GameSave(level1CellList, 100, List.of(),1);
        // create a tmpDir and write the gamesave to it.
        Path tmpSaveDir = Files.createTempDirectory("tmpSaveDir");
        GameSaveHelper.SAVE_PATH=tmpSaveDir;
        GameSaveHelper.saveGameSave(gameSave);

        GameSave gameSave1 = GameSaveHelper.loadGameSave(
            Path.of(tmpSaveDir + "/save" + gameSave.hashCode() + ".xml"));

        assertEquals(gameSave1, gameSave);
    }

    @Test
    void saveGameSave() throws IOException {
        List<Cell> level1CellList = LevelMap.get().getLevel1CellList();
        GameSave gameSave = new GameSave(level1CellList, 100, List.of(),1);
        // TODO find way to do with mockito
        Path tmpSaveDir = Files.createTempDirectory("tmpSaveDir");
        GameSaveHelper.SAVE_PATH=tmpSaveDir;
        GameSaveHelper.saveGameSave(gameSave);

        GameSave gameSave1 = GameSaveHelper.loadGameSave(
            Path.of(tmpSaveDir + "/save" + gameSave.hashCode() + ".xml"));

        assertEquals(gameSave1, gameSave);

        File file = new File(tmpSaveDir + "/deez/");
        assertFalse(file.exists());

        GameSaveHelper.SAVE_PATH = file.toPath();
        GameSaveHelper.saveGameSave(gameSave);

        assertTrue(file.exists());

        File file1 = Path.of(file.toURI() + "/" + getFileName(gameSave)).toFile();
        assertTrue(file.exists());
    }

}
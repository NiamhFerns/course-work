/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class LevelHelperTest {

    @Test
    void getAssociatedPath() {
        LevelHelper levelHelper = new LevelHelper();
        assertEquals(levelHelper.getAssociatedPath(LevelEnum.LEVEL1), Path.of("./levels/level1.xml"));
        assertEquals(levelHelper.getAssociatedPath(LevelEnum.LEVEL2), Path.of("./levels/level2.xml"));
    }
}
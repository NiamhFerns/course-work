/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for dealing with Levels in persistency.
 */
public class LevelHelper {
    /**
     * Used to get the Path of Level xml file associated with given LevelEnum.
     */
    public final Map<LevelEnum,Path> levelEnumPathMap;

    public LevelHelper() {
        // Initialize levelEnumPathMap with the appropriate LevelEnum values and their paths.
        this.levelEnumPathMap = new HashMap<>();
        levelEnumPathMap.put(LevelEnum.LEVEL1,Path.of("./levels/level1.xml"));
        levelEnumPathMap.put(LevelEnum.LEVEL2,Path.of("./levels/level2.xml"));
    }

    /**
     *
     * @param levelEnum instance of LevelEnum eg LEVEL1 or LEVEL2
     * @return Path that is the value of given levelEnum key in map.
     */
    public Path getAssociatedPath(LevelEnum levelEnum) {
        return levelEnumPathMap.get(levelEnum);
    }
}

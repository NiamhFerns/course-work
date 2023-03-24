package nz.ac.vuw.ecs.swen225.gp22.app;

/**
 * Represents a tracker that is used to hold the various information of a level of chips challenge. This is used in
 * conjunction with the Level record to instantiate a level with the correct information.
 *
 * @author niamh
 */
public enum LevelTracker {
    NONE("Start Screen", "") {
        @Override
        public LevelTracker nextLevel() {
            return LevelTracker.LEVEL1;
        }
    },
    LEVEL1("Level One", "level1") {
        @Override
        public LevelTracker nextLevel() {
            return LevelTracker.LEVEL2;
        }
        @Override
        public long getTime() {
            return 60000;
        }
    },
    LEVEL2("Level Two", "level2") {
        @Override
        public LevelTracker nextLevel() {
            return LevelTracker.NONE;
        }
        @Override
        public long getTime() {
            return 90000;
        }
    };

    private final Pair<String, String> currentLevel;

    /**
     * When needed, you can change the name for a level.
     * @param name the name the level will show when played.
     *
     * @author niamh
     */
    protected void setCustomName(String name) {
        currentLevel.setKey(name);
    }

    /**
     * When needed, you can point a level to load a custom xml file. Mostly used for saves but can be used for modding a
     * level into chips challenge.
     * @param path the path of the xml file to load.
     *
     * @author niamh
     */
    protected void setCustomPath(String path) {
        currentLevel.setValue(path);
    }

    /**
     * Retrieve the time that this level starts with.
     * @return a long of the time in milliseconds.
     *
     * @author niamh
     */
    public long getTime() {
        return 0;
    }

    /**
     * Retrieve the name for the current level.
     * @return the name of the level as a String.
     *
     * @author niamh
     */
    public String currentName(){
        return currentLevel.key();
    }

    /**
     * Retrieve the path for the current level.
     * @return the path of the level as a String.
     *
     * @author niamh
     */
    public String currentPath(){
        return currentLevel.value();
    }

    /**
     * Retrieve the next LevelTracker value holding all the information for the next level.
     * @return a LevelTracker value with all the relevant information.
     *
     * @author niamh
     */
    public LevelTracker nextLevel() {
        return LevelTracker.NONE;
    }

    LevelTracker(String levelName, String levelPath) {
        currentLevel = new Pair<>(levelName, levelPath);
    }
}

package nz.ac.vuw.ecs.swen225.gp22.persistency;

import java.nio.file.Path;

public enum Level {

    // enum constants calling the enum constructors
    LEVEL1(Path.of("./src/levels/level1.xml")),
    LEVEL2(Path.of("./src/levels/level1.xml"));

    private final Path levelPath;

    // private enum constructor
    Level(Path levelPath) {
        this.levelPath = levelPath;
    }

    public Path getLevelPath() {
        return levelPath;
    }
}

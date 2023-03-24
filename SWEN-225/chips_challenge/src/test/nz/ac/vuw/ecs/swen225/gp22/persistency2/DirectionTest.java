/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void fromString() {
        assertThrows(IllegalArgumentException.class,()-> Direction.fromString(""));
        assertThrows(IllegalArgumentException.class,()-> Direction.fromString("NotADir"));
        Map<String, Direction> stringDirectionMap = Map.ofEntries(
            Map.entry("Up", Direction.Up),
            Map.entry("Down", Direction.Down),
            Map.entry("Left", Direction.Left),
            Map.entry("Right", Direction.Right),
            Map.entry("None", Direction.None)
        );
        stringDirectionMap.keySet().forEach(keyDirString->{
            Direction direction = stringDirectionMap.get(keyDirString);
            assertSame(direction, Direction.fromString(keyDirString));
        });
    }

    @Test
    void values() {
        assertEquals(Direction.values().length,5);
    }

}
/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.ArrayList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Exit;
import nz.ac.vuw.ecs.swen225.gp22.domain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp22.domain.FreeTile;
import nz.ac.vuw.ecs.swen225.gp22.domain.Hazard;
import nz.ac.vuw.ecs.swen225.gp22.domain.IceHazard;
import nz.ac.vuw.ecs.swen225.gp22.domain.Info;
import nz.ac.vuw.ecs.swen225.gp22.domain.LockedDoor;
import nz.ac.vuw.ecs.swen225.gp22.domain.Pit;
import nz.ac.vuw.ecs.swen225.gp22.domain.Teleporter;
import nz.ac.vuw.ecs.swen225.gp22.domain.Wall;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FreeTileTest {

    /**
     * All variations of free tile to check that I can serialize and deserialize.
     */
    final List<FreeTile> tiles = new ArrayList<>(List.of(
        new Exit(),
        new ExitLock(),
        new Hazard(),
        new IceHazard(),
        new Info("Info text"),
        new LockedDoor("Green"),
        new Pit(),
        new Teleporter(),
        new Wall()
    ));

    @Test
    @DisplayName("Go through freetiles, serialize, deserialize, check objects equal")
    public void testCorrectSerializationAndDeserialization() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        tiles.forEach(tile->{
            try {
                String tileString = mapper.writeValueAsString(tile);
                FreeTile freeTile = mapper.readValue(tileString, FreeTile.class);
                assertEquals(tile,freeTile);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @Test
    public void testIncorrectSerializationAndDeserialization() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        FreeTile freeTile = new FreeTile() {
        };
        Wall wall = new Wall();
        assertNotEquals(wall,freeTile);
    }

}
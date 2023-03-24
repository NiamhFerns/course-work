/**
 * @author Michealangelo Snaddoni 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.Map;
import nz.ac.vuw.ecs.swen225.gp22.domain.Cell;
import nz.ac.vuw.ecs.swen225.gp22.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.helpers.LevelMap;
import org.junit.jupiter.api.Test;

class CellTest {
    final XmlMapper mapper = new XmlMapper();
    public CellTest() {
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * Go through level 2 cells, checking cells against
     * {@link #validCellSerializeAndDeSerialize(Cell)}
     */
    @Test
    public void testLevel2Cells() {
        Map<Coord, Cell> level2Map = LevelMap.get().getLevel2Map();

        level2Map.keySet().forEach(coord -> {
            Cell cell = level2Map.get(coord);
            try {
                assertTrue(validCellSerializeAndDeSerialize(cell));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Go through level 1 cells, checking cells against
     * {@link #validCellSerializeAndDeSerialize(Cell)}
     */
    @Test
    public void testLevel1Cells() {
        Map<Coord, Cell> level2Map = LevelMap.get().getLevel1Map();

        level2Map.keySet().forEach(coord -> {
            Cell cell = level2Map.get(coord);
            try {
                assertTrue(validCellSerializeAndDeSerialize(cell));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Uses xmlmapper to serialize, then deserialize, checks deserialized object is equal to
     * original object, then re-serializes the cell created from xml along with original and
     * checks both have same xml.
     * @param cell cell to check.
     * @return whether cell serialization and deserialization was successful and correct.
     * @throws JsonProcessingException if jackson encounters parsing / generation problems.
     */
    public boolean validCellSerializeAndDeSerialize(Cell cell) throws JsonProcessingException {
        String string = mapper.writeValueAsString(cell);
        Cell cellFromXml = mapper.readValue(string, Cell.class);
        String cellFromXmlSerial = mapper.writeValueAsString(cellFromXml);
        String cellSerial = mapper.writeValueAsString(cell);

        boolean sameXml = cellSerial.equals(cellFromXmlSerial);
        return cell.equals(cellFromXml) && sameXml;
    }
}
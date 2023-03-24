/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.ArrayList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chip;
import nz.ac.vuw.ecs.swen225.gp22.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.Entity;
import nz.ac.vuw.ecs.swen225.gp22.domain.Key;
import nz.ac.vuw.ecs.swen225.gp22.domain.Treasure;
import nz.ac.vuw.ecs.swen225.gp22.persistency2.custom.DefaultCustomMovingEntityServiceProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntityTest {

    /**
     * List of all entities to test
     */
    final List<Entity> entityList;

    EntityTest() {
        entityList = new ArrayList<>(
            List.of(
                new Key("Green"),
                new Treasure(),
                new Chip(Direction.None, new Coord(-1, -1)),
                new DefaultCustomMovingEntityServiceProvider()
            )
        );
    }
    @Test
    @DisplayName("go through entity list serialize, deserialize and check entities are equal")
    public void testSerializationAndDeserialisation() {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        entityList.forEach(entity -> {
            try {
                String string = mapper.writeValueAsString(entity);
                Entity entityFromString = mapper.readValue(string, Entity.class);
                assertEquals(entityFromString,entity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
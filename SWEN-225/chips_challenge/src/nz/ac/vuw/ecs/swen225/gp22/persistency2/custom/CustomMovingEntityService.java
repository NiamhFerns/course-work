/**
 * @author Micky Snadden 300569572
 */
package nz.ac.vuw.ecs.swen225.gp22.persistency2.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp22.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp22.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp22.domain.MovingEntity;

/**
 * Abstract class Service Providers need to implement.
 */
public abstract class CustomMovingEntityService extends MovingEntity {

    @JsonIgnore
    private Iterator<Direction> iterator;

    /**
     *
     * @param facingDir direction entity is facing.
     * @param coords coordinates of the moving entity.
     */
    public CustomMovingEntityService(Direction facingDir, Coord coords) {
        super(facingDir, coords);
        this.iterator = getDirectionList().listIterator();
    }

    public CustomMovingEntityService() {
        super(Direction.None, new Coord(-1, -1));
    }

    @JsonIgnore
    public abstract File getAssociatedLevelFile();
    /**
     * Houses the directions the custom moving entity will go.
     *
     * @return the list of directions the entity will traverse.
     */
    @JsonSerialize(using = DirectionListSerializer.class)
    @JsonDeserialize(using = DirectionListDeserializer.class)
    public abstract List<Direction> getDirectionList();

    /**
     * Used to serialize the List of directions.
     */
    public static class DirectionListSerializer extends
        StdSerializer {

        public DirectionListSerializer() {
            this(null);
        }
        protected DirectionListSerializer(Class t) {
            super(t);
        }

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
            serializerProvider.defaultSerializeValue(o,jsonGenerator);
        }
    }

    /**
     * Used to deserialize the list of directions back into enums.
     */
    public static class DirectionListDeserializer extends
        StdDeserializer {

        public DirectionListDeserializer() {
            this(null);
        }
        protected DirectionListDeserializer(Class vc) {
            super(vc);
        }

        @Override
        public Object deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {
            JsonNode treeNode = jsonParser.readValueAsTree();
            JsonNode directionList = treeNode.get("directionList");
            Iterator<JsonNode> directionListIterator = directionList.iterator();
            ArrayList<Direction> directions = new ArrayList<>();
            directionListIterator.forEachRemaining(e-> directions.add(Direction.fromString(e.asText())));
            return directions;
        }
    }

    /**
     * Used to get the next direction for the custom moving entity from the direction list,
     * looping if at the end of list.
     * @return next direction the entity should go.
     */
    @JsonIgnore
    public Direction getNextDirection() {
        assert !getDirectionList().isEmpty();
        if (iterator == null) {
            iterator = getDirectionList().listIterator(0);
        }
        if (!iterator.hasNext()) {
            this.iterator = getDirectionList().listIterator(0);
        }
        return iterator.next();
    }
}

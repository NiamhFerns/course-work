import java.util.Scanner;
import java.util.function.Function;

/**
 * Node responsible for representing a single comparison in a relation between two values p and q.
 */
record CondNode(Relation relation, NumericNode p, NumericNode q) implements BooleanNode {
    /**
     * Internal enum responsible for holding the different relational operations that can be performed in a COND
     * node.
     */
    @SuppressWarnings("unused")
    enum Relation {
        LT("lt", (p, q, robot) -> p.retrieve(robot) < q.retrieve(robot)),
        GT("gt", (p, q, robot) -> p.retrieve(robot) > q.retrieve(robot)),
        EQ("eq", (p, q, robot) -> p.retrieve(robot) == q.retrieve(robot));

        private final String action;
        private final TriFunction<NumericNode, NumericNode, Robot, Boolean> strategy;

        /**
         * Returns the name of the current conditional operation this enum represents.
         */
        String current() {
            return action;
        }

        /**
         * Returns a strategy of the current operation that can be performed on a pair of values (p, q) and a robot
         * (robot).
         */
        TriFunction<NumericNode, NumericNode, Robot, Boolean> retrieve() {
            return strategy;
        }

        Relation(String action, TriFunction<NumericNode, NumericNode, Robot, Boolean> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    /**
     * Parse in and return a Node representing some arbitrary conditional expression.
     */
    public static CondNode parse(Scanner s) {
        var rel = CondNode.Relation.valueOf(Parser.require(Parser.RELATION_PAT, "Not a valid relation.", s).toUpperCase());
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var sens = SensNode.parse(s);
        Parser.require(Parser.COMMA_PAT, "Missing comma.", s);
        var value = NumNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        return new CondNode(rel, sens, value);
    }

    @Override
    public boolean evaluate(Robot robot) {
        return relation.retrieve().apply(p, q, robot);
    }

    @Override
    public String toString() {
        return relation.current() + "(" + p.toString() + ", " + q.toString() + ")";
    }
}

/**
 * Node responsible for representing a sensor on a robot.
 */
record SensNode(Sensor sensor) implements NumericNode {
    /**
     * Internal enum responsible for holding both the name and strategy for some arbitrary sensor.
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    enum Sensor {
        FUELLEFT("fuelLeft", Robot::getFuel),
        OPPLR("oppLR", Robot::getOpponentLR),
        OPPFB("oppFB", Robot::getOpponentFB),
        NUMBARRELS("numBarrel", Robot::numBarrels),
        BARRELLR("barrelLR", Robot::getClosestBarrelLR),
        BARRELFB("barrelFB", Robot::getClosestBarrelFB),
        WALLDIST("wallDist", Robot::getDistanceToWall);

        private final String action;
        private final Function<Robot, Integer> strategy;

        /**
         * Returns the name of the current sensor that this enum represents.
         */
        String current() {
            return action;
        }

        /**
         * Returns a strategy that accepts a robot and retrieves the current sensor value of that robot.
         */
        Function<Robot, Integer> retrieve() {
            return strategy;
        }

        Sensor(String action, Function<Robot, Integer> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    /**
     * Parses in and returns a node representing a sensor on a robot.
     */
    static SensNode parse(Scanner s) {
        var action = SensNode.Sensor.valueOf(Parser.require(Parser.SENSOR_PAT, "This is not a valid sensor.", s).toUpperCase());
        return new SensNode(action);
    }

    @Override
    public int retrieve(Robot robot) {
        return sensor.retrieve().apply(robot);
    }


    @Override
    public String toString() {
        return sensor.current();
    }
}

/**
 * Node responsible for holding some numerical literal value.
 */
record NumNode(int n) implements NumericNode {
    /**
     * Parse in and return a node containing a numerical literal.
     */
    public static NumNode parse(Scanner s) {
        return new NumNode(Parser.requireInt(Parser.NUM_PAT, "Not a valid number.", s));
    }
    @Override
    public int retrieve(Robot robot) {
        return n;
    }

    @Override
    public String toString() {
        return "" + n;
    }
}

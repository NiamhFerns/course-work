import java.util.Scanner;
import java.util.function.Function;

record CondNode(Relation relation, NumericNode p, NumericNode q) implements BooleanNode {
    @SuppressWarnings("unused")
    enum Relation {
        LT("lt", (p, q, robot) -> p.retrieve(robot) < q.retrieve(robot)),
        GT("gt", (p, q, robot) -> p.retrieve(robot) > q.retrieve(robot)),
        EQ("eq", (p, q, robot) -> p.retrieve(robot) == q.retrieve(robot));

        private final String action;
        private final TriFunction<NumericNode, NumericNode, Robot, Boolean> strategy;

        String current() {
            return action;
        }
        TriFunction<NumericNode, NumericNode, Robot, Boolean> retrieve() {
            return strategy;
        }

        Relation(String action, TriFunction<NumericNode, NumericNode, Robot, Boolean> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

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

record SensNode(Sensor sensor) implements NumericNode {
    @Override
    public int retrieve(Robot robot) {
        return sensor.retrieve().apply(robot);
    }

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

        String current() {
            return action;
        }
        Function<Robot, Integer> retrieve() {
            return strategy;
        }

        Sensor(String action, Function<Robot, Integer> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    static SensNode parse(Scanner s) {
        var action = SensNode.Sensor.valueOf(Parser.require(Parser.SENSOR_PAT, "This is not a valid sensor.", s).toUpperCase());
        return new SensNode(action);
    }

    @Override
    public String toString() {
        return sensor.current();
    }
}

record NumNode(int n) implements NumericNode {
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

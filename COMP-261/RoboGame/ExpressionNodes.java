import java.util.Scanner;
import java.util.function.Function;

/**
 * Node responsible for representing a single comparison in a relation between two values p and q.
 */
record CondNode(Relations relation, ExprNode p, ExprNode q) implements ExprNode {
    /**
     * Internal enum responsible for holding the different relational operations that can be performed in a COND
     * node.
     */
    @SuppressWarnings("unused")
    enum Relations {
        LT("lt", (p, q, robot) -> p.asInt(robot) < q.asInt(robot)),
        GT("gt", (p, q, robot) -> p.asInt(robot) > q.asInt(robot)),
        EQ("eq", (p, q, robot) -> p.asInt(robot) == q.asInt(robot)),
        AND("and", (p, q, robot) -> p.asBool(robot) && q.asBool(robot)),
        OR("or", (p, q, robot) -> p.asBool(robot) || q.asBool(robot)),
        NOT("not", (p, q, robot) -> !p.asBool(robot));

        private final String action;
        private final TriFunction<ExprNode, ExprNode, Robot, Boolean> strategy;

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
        TriFunction<ExprNode, ExprNode, Robot, Boolean> retrieve() {
            return strategy;
        }

        Relations(String action, TriFunction<ExprNode, ExprNode, Robot, Boolean> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    /**
     * Parse in and return a Node representing some arbitrary conditional expression.
     */
    public static CondNode parse(Scanner s) {
        // Grab our operator and the first argument.
        var rel = Relations.valueOf(Parser.require(Parser.RELATION_PAT, "Not a valid relation.", s).toUpperCase());
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);

        if (rel.current().matches("and|or|not") && s.hasNext(Parser.NUM_PAT))
            Parser.fail("Failed to parse " + rel.current() + "() expression: numerical arguments provided.", s);
        var p = ExprNode.parse(s);

        // Grab the second argument if it's not a "not" operator.
        // Yes, null is bad here.
        ExprNode q = null;
        if (!rel.current().equals("not")) {
            Parser.require(Parser.COMMA_PAT, "Missing comma.", s);
            if (rel.current().matches("and|or|not") && s.hasNext(Parser.NUM_PAT))
                Parser.fail("Failed to parse " + rel.current() + "() expression: numerical arguments provided.", s);
            q = ExprNode.parse(s);
        }

        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        return new CondNode(rel, p, q);
    }

    @Override
    public boolean asBool(Robot robot) {
        return relation.retrieve().apply(p, q, robot);
    }

    @Override
    public String toString() {
        return relation.current() + "(" + p.toString() + ((this.q != null) ? ", " + q : "") + ")";
    }
}

/**
 * Record responsible for holding an operation and two expression nodes to perform that operation on.
 */
record OperationNode(Operations op, ExprNode p, ExprNode q) implements ExprNode {
    /**
     * Internal enum responsible for holding the different arithmetic operations that can be performed in an OP
     * node.
     */
    @SuppressWarnings("unused")
    enum Operations {
        ADD("add", (p, q, robot) -> p.asInt(robot) + q.asInt(robot)),
        SUB("sub", (p, q, robot) -> p.asInt(robot) - q.asInt(robot)),
        MUL("mul", (p, q, robot) -> p.asInt(robot) * q.asInt(robot)),
        DIV("div", (p, q, robot) -> p.asInt(robot) / q.asInt(robot));

        private final String action;
        private final TriFunction<ExprNode, ExprNode, Robot, Integer> strategy;

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
        TriFunction<ExprNode, ExprNode, Robot, Integer> retrieve() {
            return strategy;
        }

        Operations(String action, TriFunction<ExprNode, ExprNode, Robot, Integer> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    public static OperationNode parse(Scanner s) {
        var op = Operations.valueOf(Parser.require(Parser.ARITHOP_PAT, "Not a valid arithmetic operation.", s).toUpperCase());
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        if (s.hasNext(Parser.RELATION_PAT))
            Parser.fail("Failed to parse " + op.current() + "() expression: numerical arguments provided.", s);
        var p = ExprNode.parse(s);
        Parser.require(Parser.COMMA_PAT, "Missing comma.", s);
        if (s.hasNext(Parser.RELATION_PAT))
            Parser.fail("Failed to parse " + op.current() + "() expression: numerical arguments provided.", s);
        var q = ExprNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        return new OperationNode(op, p, q);
    }

    @Override
    public int asInt(Robot robot) {
        return op.retrieve().apply(p, q, robot);
    }

    @Override
    public String toString() {
        return "";
    }
}

/**
 * Node responsible for representing a sensor on a robot.
 */
record SensNode(Sensor sensor) implements ExprNode {
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
    public int asInt(Robot robot) {
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
record NumNode(int n) implements ExprNode {
    /**
     * Parse in and return a node containing a numerical literal.
     */
    public static NumNode parse(Scanner s) {
        return new NumNode(Parser.requireInt(Parser.NUM_PAT, "Not a valid number.", s));
    }
    @Override
    public int asInt(Robot robot) {
        return n;
    }

    @Override
    public String toString() {
        return "" + n;
    }
}

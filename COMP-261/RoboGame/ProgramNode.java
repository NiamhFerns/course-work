import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface ProgramNode {
    void execute(Robot robot);
}

interface BooleanNode {
    boolean evaluate(Robot robot);
}
interface NumericNode {
    int retrieve(Robot robot);
}

interface TriFunction<T, U, V, R> {
    R apply(T t, U u, V v);
}

record Program(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        for (ProgramNode s : statementNodes) {
            s.execute(robot);
        }
    }

    public static Program parse(Scanner s) {
        var statements = new ArrayList<StatementNode>();
        while (s.hasNext()) {
            statements.add(StatementNode.parse(s));
        }
        return new Program(statements);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var statement : statementNodes) {
            s.append(statement.toString()).append("\n");
        }
        return s.toString();
    }
}
record StatementNode(ProgramNode statement) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        if (statement != null)
            statement.execute(robot);
    }

    public static StatementNode parse(Scanner s) {
        if (s.hasNext(Parser.ACT_PATTERN)) return ActNode.parse(s);
        if (Parser.checkFor(Parser.LOOP_PATTERN, s)) return LoopNode.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return StatementNode.of(null);
    }

    public static boolean check(Scanner s) {
        return s.hasNext(Parser.ACT_PATTERN) | s.hasNext(Parser.LOOP_PATTERN);
    }

    static StatementNode of(ProgramNode n) {
        return new StatementNode(n);
    }

    @Override
    public String toString() {
        return statement.toString();
    }
}

record ActNode(Acts action) implements ProgramNode {
    enum Acts {
        MOVE("move", Robot::move),
        TURNL("turnL", Robot::turnLeft),
        TURNR("turnR", Robot::turnRight),
        TURNAROUND("turnAround", Robot::turnAround),
        SHIELDON("shieldOn", (Robot robot) -> { robot.setShield(true); }),
        SHIELDOFF("shieldOff", (Robot robot) -> { robot.setShield(false); }),
        TAKEFUEL("takeFuel", Robot::takeFuel),
        WAIT("wait", Robot::idleWait);

        private final String action;
        private final Consumer<Robot> strategy;

        String current() {
            return action;
        }
        void go(Robot robot) {
            strategy.accept(robot);
        }

        Acts(String action, Consumer<Robot> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    static StatementNode parse(Scanner s) {
        var action = Acts.valueOf(Parser.require(Parser.ACT_PATTERN, "This is not a valid action", s).toUpperCase());
        Parser.require(Parser.SEMICOLON, "Syntax error: failed to find ';'.", s);
        return StatementNode.of(new ActNode(action));
    }

    @Override
    public void execute(Robot robot) {
        action.go(robot);
    }

    @Override
    public String toString() {
        return action.current() + ";";
    }
}

record LoopNode(BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) {
            blockNode.execute(robot);
        }
    }

    static StatementNode parse(Scanner s) {
        return StatementNode.of(new LoopNode(BlockNode.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

record WhileNode(BooleanNode condition, BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) {
            blockNode.execute(robot);
        }
    }

    static StatementNode parse(Scanner s) {
        return StatementNode.of(new LoopNode(BlockNode.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

record IfNode(BooleanNode condition, BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        if (condition.evaluate(robot))
            blockNode.execute(robot);
    }

    static CondNode parse(Scanner s) {
        var rel = CondNode.Relation.valueOf(Parser.require(Parser.RELATION_PATTERN, "Not a valid relation.", s).toUpperCase());
        Parser.require(Parser.OPENPAREN, "Missing open parenthesis.", s);
        var sens = SensNode.parse(s);
        Parser.require(Parser.COMMA_PATTERN, "Missing comma.", s);
        var value = NumNode.parse(s);
        Parser.require(Parser.CLOSEPAREN, "Missing close parenthesis.", s);
        return new CondNode(rel, sens, value);
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

record BlockNode(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        for (StatementNode s : statementNodes) {
            s.execute(robot);
        }
    }

    static BlockNode parse(Scanner s) {
        Parser.require(Parser.OPENBRACE, "Missing opening brace '{'.", s);
        var statements = new ArrayList<StatementNode>();
        while(StatementNode.check(s)) {
            statements.add(StatementNode.parse(s));
        }
        Parser.require(Parser.CLOSEBRACE, "Missing closing brace '}'.", s);
        return new BlockNode(statements);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var statement : statementNodes) {
            s.append(statement.toString()).append("\n");
        }
        return s.toString();
    }
}

record CondNode(Relation relation, NumericNode p, NumericNode q) implements BooleanNode {
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

    @Override
    public boolean evaluate(Robot robot) {
        return relation.retrieve().apply(p, q, robot);
    }
}

record SensNode(Sensor sensor) implements NumericNode {
    @Override
    public int retrieve(Robot robot) {
        return sensor.retrieve().apply(robot);
    }

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
        var action = SensNode.Sensor.valueOf(Parser.require(Parser.SENSOR_PATTERN, "This is not a valid sensor.", s).toUpperCase());
        return new SensNode(action);
    }
}

record NumNode(int n) implements NumericNode {
    public static NumNode parse(Scanner s) {
        return new NumNode(Parser.requireInt(Parser.NUMPAT, "Not a valid number.", s));
    }
    @Override
    public int retrieve(Robot robot) {
        return n;
    }
}
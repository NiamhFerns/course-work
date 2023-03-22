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
        if (s.hasNext(Parser.ACT_PAT)) return ActNode.parse(s);
        if (Parser.checkFor(Parser.LOOP_PAT, s)) return LoopNode.parse(s);
        if (Parser.checkFor(Parser.WHILE_PAT, s)) return WhileNode.parse(s);
        if (Parser.checkFor(Parser.IF_PAT, s)) return IfNode.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return StatementNode.of(null);
    }

    public static boolean check(Scanner s) {
        return s.hasNext(Parser.ACT_PAT)
                | s.hasNext(Parser.LOOP_PAT)
                | s.hasNext(Parser.IF_PAT)
                | s.hasNext(Parser.WHILE_PAT);
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
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    enum Acts {
        MOVE("move", Robot::move),
        TURNL("turnL", Robot::turnLeft),
        TURNR("turnR", Robot::turnRight),
        TURNAROUND("turnAround", Robot::turnAround),
        SHIELDON("shieldOn", robot -> robot.setShield(true)),
        SHIELDOFF("shieldOff", robot -> robot.setShield(false)),
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
        var action = Acts.valueOf(Parser.require(Parser.ACT_PAT, "This is not a valid action", s).toUpperCase());
        Parser.require(Parser.TERMINATION_PAT, "Syntax error: failed to find ';'.", s);
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

@SuppressWarnings("InfiniteLoopStatement")
record LoopNode(BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) blockNode.execute(robot);
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
        while(condition.evaluate(robot)) {
            blockNode().execute(robot);
        }
    }

    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing open parenthesis.", s);
        var block = BlockNode.parse(s);
        return StatementNode.of(new WhileNode(cond, block));
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ")" + blockNode.toString();
    }
}

record IfNode(BooleanNode condition, BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        if (condition.evaluate(robot))
            blockNode.execute(robot);
    }

    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        var block = BlockNode.parse(s);
        return StatementNode.of(new IfNode(cond, block));
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ")" + blockNode.toString();
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
        Parser.require(Parser.OPEN_BRACE_PAT, "Missing opening brace '{'.", s);
        var statements = new ArrayList<StatementNode>();
        while(StatementNode.check(s)) {
            statements.add(StatementNode.parse(s));
        }
        Parser.require(Parser.CLOSE_BRACE_PAT, "Missing closing brace '}'.", s);
        if (statements.isEmpty()) Parser.fail("Empty block statement.", s);
        return new BlockNode(statements);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        s.append("{\n");
        for (var statement : statementNodes) {
            s.append("\t").append(statement.toString()).append("\n");
        }
        s.append("}");
        return s.toString();
    }
}

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
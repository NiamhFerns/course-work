import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface ProgramNode {
    void execute(Robot robot);
}

record Program(ArrayList<Statement> statements) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        for (ProgramNode s : statements) {
            s.execute(robot);
        }
    }

    public static Program parse(Scanner s) {
        var statements = new ArrayList<Statement>();
        while (s.hasNext()) {
            statements.add(Statement.parse(s));
        }
        return new Program(statements);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var statement : statements) {
            s.append(statement.toString()).append("\n");
        }
        return s.toString();
    }
}
record Statement(ProgramNode statement) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        if (statement != null)
            statement.execute(robot);
    }

    public static Statement parse(Scanner s) {
        if (s.hasNext(Parser.ACT_PATTERN)) return Act.parse(s);
        if (Parser.checkFor(Parser.LOOP_PATTERN, s)) return Loop.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return Statement.of(null);
    }

    public static boolean check(Scanner s) {
        return s.hasNext(Parser.ACT_PATTERN) | s.hasNext(Parser.LOOP_PATTERN);
    }

    static Statement of(ProgramNode n) {
        return new Statement(n);
    }

    @Override
    public String toString() {
        return statement.toString();
    }
}

record Act(Acts action) implements ProgramNode {
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

    static Statement parse(Scanner s) {
        var action = Acts.valueOf(Parser.require(Parser.ACT_PATTERN, "This is not a valid action", s).toUpperCase());
        Parser.require(Parser.SEMICOLON, "Syntax error: failed to find ';'.", s);
        return Statement.of(new Act(action));
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

record Loop(Block block) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) {
            block.execute(robot);
        }
    }

    static Statement parse(Scanner s) {
        return Statement.of(new Loop(Block.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + block.toString();
    }
}

record While(Block block) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) {
            block.execute(robot);
        }
    }

    static Statement parse(Scanner s) {
        return Statement.of(new Loop(Block.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + block.toString();
    }
}

record Block(ArrayList<Statement> statements) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        for (Statement s : statements) {
            s.execute(robot);
        }
    }

    static Block parse(Scanner s) {
        Parser.require(Parser.OPENBRACE, "Missing opening brace '{'.", s);
        var statements = new ArrayList<Statement>();
        while(Statement.check(s)) {
            statements.add(Statement.parse(s));
        }
        Parser.require(Parser.CLOSEBRACE, "Missing closing brace '}'.", s);
        return new Block(statements);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        for (var statement : statements) {
            s.append(statement.toString()).append("\n");
        }
        return s.toString();
    }
}

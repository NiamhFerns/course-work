import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
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

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Top level node responsible for representing the root of our concrete syntax tree. Execution of a program
 * begins at this node.
 */
record Program(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    /**
     * Parse in a program and return that program as a root node of a concrete syntax tree.
     */
    public static Program parse(Scanner s) {
        var statements = new ArrayList<StatementNode>();
        while (s.hasNext()) {
            statements.add(StatementNode.parse(s));
        }
        return new Program(statements);
    }

    @Override
    public void execute(Robot robot) {
        for (ProgramNode s : statementNodes) {
            s.execute(robot);
        }
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

/**
 * Wrapper node responsible for containing a program node that can be read in as a statement.
 */
record StatementNode(ProgramNode statement) implements ProgramNode {
    /**
     * Parses in a StatementNode.
     */
    public static StatementNode parse(Scanner s) {
        if (s.hasNext(Parser.ACT_PAT)) return ActNode.parse(s);
        if (Parser.checkFor(Parser.LOOP_PAT, s)) return LoopNode.parse(s);
        if (Parser.checkFor(Parser.WHILE_PAT, s)) return WhileNode.parse(s);
        if (Parser.checkFor(Parser.IF_PAT, s)) return IfNode.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return StatementNode.of(null);
    }

    /**
     * Check to see if the next token in the scanner is allowed in a StatementNode.
     */
    public static boolean check(Scanner s) {
        return s.hasNext(Parser.ACT_PAT)
                | s.hasNext(Parser.LOOP_PAT)
                | s.hasNext(Parser.IF_PAT)
                | s.hasNext(Parser.WHILE_PAT);
    }

    /**
     * Returns a StatementNode consisting of some arbitrary program node.
     */
    static StatementNode of(ProgramNode n) {
        return new StatementNode(n);
    }

    @Override
    public void execute(Robot robot) {
        if (statement != null)
            statement.execute(robot);
    }

    @Override
    public String toString() {
        return statement.toString();
    }
}

/**
 * Node responsible for holding the state and functionality for an ACT.
 */
record ActNode(Acts action) implements ProgramNode {
    /**
     * A simple enumeration that holds both the name and functionality for each possible action a robot can take
     * at any one time.
     */
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

        /**
         * Returns the name of the current act held by this node as it is shown in the grammar.
         */
        String current() {
            return action;
        }

        /**
         * Performs the act currently held in this node by performing this nodes strategy on an accepted robot.
         */
        void go(Robot robot) {
            strategy.accept(robot);
        }

        Acts(String action, Consumer<Robot> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    /**
     * Parse in an ACT non-terminal and return a statement node containing it.
     */
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

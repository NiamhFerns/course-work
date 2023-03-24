import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Node responsible for holding the state and functionality for an ACT.
 */
record ActNode(Acts action, ExprNode callCount) implements ProgramNode {
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
     * Parse in an ACT non-terminal and return a statement node containing it. "move" and "wait" terminals can be called
     * multiple times with one ACT node.
     */
    static StatementNode parse(Scanner s) {
        // Parse in the ACT command itself and assign the correct functionality in action enum.
        var action = Acts.valueOf(Parser.require(Parser.ACT_PAT, "This is not a valid action", s).toUpperCase());
        ExprNode count = null;

        // Check for multiple calls and parse in the ExprNode if needed.
        if (action.current().matches("move|wait") && Parser.checkFor(Parser.OPEN_PAREN_PAT, s)) {
            count = ExprNode.parse(s);
            Parser.require(Parser.CLOSE_PAREN_PAT, "Missing closing parenthesis on repeated " + action.current() + " statement.", s);
        }

        Parser.require(Parser.TERMINATION_PAT, "Syntax error: failed to find ';'.", s);
        return StatementNode.of(new ActNode(action, count));
    }

    @Override
    public void execute(Robot robot) {
        // Call once if no callCount is found.
        if (callCount == null) {
            action.go(robot);
            return;
        }

        // Call multiple times if call count is found.
        var count = callCount.asInt(robot);
        for (int i = 0; i < count; ++i)
            action.go(robot);
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        s.append(action.current());
        if (callCount != null)
            s.append("(").append(callCount).append(")");
        s.append(";");
        return s.toString();
    }
}

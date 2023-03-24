import java.util.Scanner;

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

    /**
     * Parse in an OperationNode containing the operation to be performed and both values (p, q).
     */
    public static OperationNode parse(Scanner s) {
        // Parse in operation type.
        var op = Operations.valueOf(Parser.require(Parser.ARITHOP_PAT, "Not a valid arithmetic operation.", s).toUpperCase());
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);

        // Makes sure it's a number.
        if (s.hasNext(Parser.RELATION_PAT))
            Parser.fail("Failed to parse " + op.current() + "() expression: numerical arguments provided.", s);
        var p = ExprNode.parse(s);

        Parser.require(Parser.COMMA_PAT, "Missing comma.", s);

        // Makes sure it's a number.
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
        return op.current() + "(" + p + ", " + q + ")";
    }
}

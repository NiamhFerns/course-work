import java.util.Scanner;

/**
 * Node responsible for representing a single relation between two values p and q.
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

        // Check for boolean expression.
        if (rel.current().matches("and|or|not") && s.hasNext(Parser.NUM_PAT))
            Parser.fail("Failed to parse " + rel.current() + "() expression: numerical arguments provided.", s);
        var p = ExprNode.parse(s);

        // Grab the second argument if it's not a "not" operator.
        ExprNode q = null;
        if (!rel.current().equals("not")) {
            Parser.require(Parser.COMMA_PAT, "Missing comma.", s);
            // Check for boolean expression.
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

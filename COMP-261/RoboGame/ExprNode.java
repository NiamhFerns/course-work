import java.util.Scanner;

/**
 * Interface responsible for representing some value that can be treated as an expression or an expression of expressions.
 */
interface ExprNode {
    /**
     * Parse in an ExprNode containing some expression.
     */
    static ExprNode parse(Scanner s) {
        if (s.hasNext(Parser.NUM_PAT)) return NumNode.parse(s);
        if (s.hasNext(Parser.SENSOR_PAT)) return SensNode.parse(s);
        if (s.hasNext(Parser.RELATION_PAT)) return CondNode.parse(s);
        if (s.hasNext(Parser.ARITHOP_PAT)) return OperationNode.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return null;
    }

    /**
     * Return the current ExprNode's evaluated result as an integer. This is intended for expressions that naturally evaluate
     * to a numerical result and can be defined to coerce expressions resulting in a boolean type to result in some boolean
     * to determine what constitutes true or false for this expression.
     * Coercion is not done by default.
     */
    default int asInt(Robot robot) {
        throw new ArithmeticException("Tried to return expression as integer where no integer conversion was provided.");
    }

    /**
     * Return the current ExprNode's evaluated result as a boolean. This is intended for expressions that naturally result
     * in a boolean but can be defined to coerce expressions resulting in a numerical type to result in some integer value.
     * Coercion is not done by default.
     */
    default boolean asBool(Robot robot) {
        throw new ArithmeticException("Tried to return expression as boolean where no boolean conversion was provided.");
    }
}

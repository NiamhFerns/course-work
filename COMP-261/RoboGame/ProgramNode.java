import java.util.Scanner;

/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface ProgramNode {
    void execute(Robot robot);
}

interface ExprNode {
    static ExprNode parse(Scanner s) {
        if (s.hasNext(Parser.NUM_PAT)) return NumNode.parse(s);
        if (s.hasNext(Parser.SENSOR_PAT)) return SensNode.parse(s);
        if (s.hasNext(Parser.RELATION_PAT)) return CondNode.parse(s);
        if (s.hasNext(Parser.ARITHOP_PAT)) return OperationNode.parse(s);
        Parser.fail("Not a valid code piece of code.", s);
        return null;
    }

    default int asInt(Robot robot) {
        throw new ArithmeticException("Tried to return expression as integer where no integer conversion was provided.");
    }

    default boolean asBool(Robot robot) {
        throw new ArithmeticException("Tried to return expression as boolean where no boolean conversion was provided.");
    }
}
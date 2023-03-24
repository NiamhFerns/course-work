import java.util.Scanner;

/**
 * Record responsible for representing a WHILE node that will loop over a block of code while some condition is met.
 */
record WhileNode(CondNode condition, BlockNode blockNode) implements ProgramNode {
    /**
     * Parses in and returns a StatementNode containing a while loop.
     */
    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing open parenthesis.", s);
        var block = BlockNode.parse(s);
        return StatementNode.of(new WhileNode(cond, block));
    }

    @Override
    public void execute(Robot robot) {
        while (condition.asBool(robot)) {
            blockNode().execute(robot);
        }
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ")" + blockNode.toString();
    }
}

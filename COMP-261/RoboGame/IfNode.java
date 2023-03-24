import java.util.Scanner;

/**
 * Record responsible for representing an IF node that will execute a block of code once only if some conditional is met.
 */
record IfNode(CondNode condition, BlockNode blockIf, BlockNode blockElse) implements ProgramNode {
    /**
     * Parses in and returns a StatementNode containing an if conditional and some optional else clause if it is present.
     */
    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);

        // Read in the conditional and the if's block.
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        var blockIf = BlockNode.parse(s);

        // Check for an else block and parse it if needed.
        BlockNode blockElse = null;
        if (Parser.checkFor(Parser.ELSE_PAT, s)) {
            blockElse = BlockNode.parse(s);
        }

        return StatementNode.of(new IfNode(cond, blockIf, blockElse));
    }

    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        if (condition.asBool(robot))
            blockIf.execute(robot);
        else if (blockElse != null) {
            blockElse.execute(robot);
        }
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ")"
                + blockIf
                + ((blockElse != null) ? " else" + blockElse : "");
    }
}

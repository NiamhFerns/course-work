import java.util.Scanner;

/**
 * Wrapper node responsible for containing a program node that can be read in as a statement. (Redundant but needed to
 * satisfy the grammar requirements for the marks for this assignment).
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

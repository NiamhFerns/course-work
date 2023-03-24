import java.util.ArrayList;
import java.util.Scanner;

/**
 * Record responsible for denoting scope and holding a set of statements in a block that govern what is run by some other
 * control flow statement.
 */
record BlockNode(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    /**
     * Parses in and returns a BlockNode containing a list of all the statements it can execute.
     */
    static BlockNode parse(Scanner s) {
        Parser.require(Parser.OPEN_BRACE_PAT, "Missing opening brace '{'.", s);
        var statements = new ArrayList<StatementNode>();
        while(StatementNode.check(s)) {
            statements.add(StatementNode.parse(s));
        }
        Parser.require(Parser.CLOSE_BRACE_PAT, "Missing closing brace '}'.", s);
        if (statements.isEmpty()) Parser.fail("Empty block statement.", s);
        return new BlockNode(statements);
    }

    @Override
    public void execute(Robot robot) {
        for (StatementNode s : statementNodes) {
            s.execute(robot);
        }
    }

    @Override
    public String toString() {
        var s = new StringBuilder();
        s.append("{\n");
        for (var statement : statementNodes) {
            s.append(statement.toString()).append("\n");
        }
        s.append("}");
        return s.toString();
    }
}

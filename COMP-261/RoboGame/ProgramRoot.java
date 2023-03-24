import java.util.ArrayList;
import java.util.Scanner;

/**
 * Top level node responsible for representing the root of our concrete syntax tree. Execution of a program
 * begins at this node.
 */
record ProgramRoot(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    /**
     * Parse in a program and return that program as a root node of a concrete syntax tree.
     */
    public static ProgramRoot parse(Scanner s) {
        var statements = new ArrayList<StatementNode>();
        while (s.hasNext()) {
            statements.add(StatementNode.parse(s));
        }
        return new ProgramRoot(statements);
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


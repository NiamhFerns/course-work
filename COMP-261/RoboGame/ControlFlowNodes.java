import java.util.ArrayList;
import java.util.Scanner;

/**
 * Record responsible for representing a LOOP node that will infinitely loop a block of code when executed.
 */
@SuppressWarnings("InfiniteLoopStatement")
record LoopNode(BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        while(true) blockNode.execute(robot);
    }

    /**
     * Parses in and returns a StatementNode containing a Loop.
     */
    static StatementNode parse(Scanner s) {
        return StatementNode.of(new LoopNode(BlockNode.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

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
        while(condition.asBool(robot)) {
            blockNode().execute(robot);
        }
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ")" + blockNode.toString();
    }
}

/**
 * Record responsible for representing an IF node that will execute a block of code once only if some conditional is met.
 */
record IfNode(CondNode condition, BlockNode blockIf, BlockNode blockElse) implements ProgramNode {
    /**
     * Parses in and returns a StatementNode containing an if conditional.
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
                + ((blockElse != null) ? " else" + blockElse : "" );
    }
}

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
            s.append("\t").append(statement.toString()).append("\n");
        }
        s.append("}");
        return s.toString();
    }
}

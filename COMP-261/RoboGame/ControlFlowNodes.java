import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("InfiniteLoopStatement")
record LoopNode(BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        while(true) blockNode.execute(robot);
    }

    static StatementNode parse(Scanner s) {
        return StatementNode.of(new LoopNode(BlockNode.parse(s)));
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

record WhileNode(BooleanNode condition, BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        while(condition.evaluate(robot)) {
            blockNode().execute(robot);
        }
    }

    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing open parenthesis.", s);
        var block = BlockNode.parse(s);
        return StatementNode.of(new WhileNode(cond, block));
    }

    @Override
    public String toString() {
        return "while(" + condition.toString() + ")" + blockNode.toString();
    }
}

record IfNode(BooleanNode condition, BlockNode blockNode) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        // Infinitely loop all statements in the block.
        if (condition.evaluate(robot))
            blockNode.execute(robot);
    }

    static StatementNode parse(Scanner s) {
        Parser.require(Parser.OPEN_PAREN_PAT, "Missing open parenthesis.", s);
        var cond = CondNode.parse(s);
        Parser.require(Parser.CLOSE_PAREN_PAT, "Missing close parenthesis.", s);
        var block = BlockNode.parse(s);
        return StatementNode.of(new IfNode(cond, block));
    }

    @Override
    public String toString() {
        return "if(" + condition.toString() + ")" + blockNode.toString();
    }
}

record BlockNode(ArrayList<StatementNode> statementNodes) implements ProgramNode {
    @Override
    public void execute(Robot robot) {
        for (StatementNode s : statementNodes) {
            s.execute(robot);
        }
    }

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

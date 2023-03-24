import java.util.Scanner;

/**
 * Record responsible for representing a LOOP node that will infinitely loop a block of code when executed.
 */
@SuppressWarnings("InfiniteLoopStatement")
record LoopNode(BlockNode blockNode) implements ProgramNode {
    /**
     * Parses in and returns a StatementNode containing a Loop.
     */
    static StatementNode parse(Scanner s) {
        return StatementNode.of(new LoopNode(BlockNode.parse(s)));
    }

    @Override
    public void execute(Robot robot) {
        while (true) blockNode.execute(robot);
    }

    @Override
    public String toString() {
        return "loop" + blockNode.toString();
    }
}

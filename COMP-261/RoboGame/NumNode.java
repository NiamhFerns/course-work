import java.util.Scanner;

/**
 * Node responsible for holding some numerical literal value.
 */
record NumNode(int n) implements ExprNode {
    /**
     * Parse in and return a node containing a numerical literal.
     */
    public static NumNode parse(Scanner s) {
        return new NumNode(Parser.requireInt(Parser.NUM_PAT, "Not a valid number.", s));
    }

    @Override
    public int asInt(Robot robot) {
        return n;
    }

    @Override
    public String toString() {
        return "" + n;
    }
}

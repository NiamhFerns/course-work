/**
 * Interface for all nodes that can be executed,
 * including the top level program node
 */

interface ProgramNode {
    void execute(Robot robot);
}

interface BooleanNode {
    boolean evaluate(Robot robot);
}
interface NumericNode {
    int retrieve(Robot robot);
}
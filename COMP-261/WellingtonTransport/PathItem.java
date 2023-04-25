/**
 * AStar search (and Dijkstra search) uses a priority queue of partial paths
 * that the search is building.
 * Each partial path needs several pieces of information, to specify
 * the path to that point, its cost so far, and its estimated total cost
 */

public class PathItem implements Comparable<PathItem> {
    private final Stop stop;
    private final Edge edge;
    private double cost;
    private double heuristicValue;

    public PathItem(Stop stop, Edge edge, double cost, double heuristicValue) {
        this.stop = stop;
        this.edge = edge;
        this.cost = cost;
        this.heuristicValue = heuristicValue;
    }

    public Stop stop() {
        return stop;
    }
    public Edge edge() {
        return edge;
    }
    public double cost() {
        return cost;
    }
    public double heuristicValue() {
        return heuristicValue;
    }

    public void newHeuristic(double heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public void newCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(PathItem pathItem) {
        return this.cost - pathItem.cost > 0 ? 1
                : this.cost - pathItem.cost < 0 ? -1
                : 0;
    }
}

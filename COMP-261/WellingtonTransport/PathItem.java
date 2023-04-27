/**
 * AStar search (and Dijkstra search) uses a priority queue of partial paths
 * that the search is building.
 * Each partial path needs several pieces of information, to specify
 * the path to that point, its cost so far, and its estimated total cost
 */

public record PathItem(Stop stop, Edge edge, double cost, double heuristicValue) implements Comparable<PathItem> {
    @Override
    public int compareTo(PathItem pathItem) {
        return Double.compare(this.cost, pathItem.cost);
    }
}

/**
 * Implements the A* search algorithm to find the shortest path
 * in a graph between a start node and a goal node.
 * It returns a Path consisting of a list of Edges that will
 * connect the start node to the goal node.
 */

import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;



public class AStar {
    private static String timeOrDistance = "distance";    // way of calculating cost: "time" or "distance"


    // find the shortest path between two stops
    public static List<Edge> findShortestPath(Stop origin, Stop target, String timeOrDistance) {
        if (origin == null || target == null) return null;
        if (origin == target) return List.of();

        AStar.timeOrDistance = (timeOrDistance.equals("time")) ? "time" : "distance";

        PriorityQueue<PathItem> fringe = new PriorityQueue<>();
        HashMap<Stop, Edge> backPointers = new HashMap<>();

        fringe.add(new PathItem(origin, null, 0.0d, heuristic(origin, target)));


        while (!fringe.isEmpty()) {
            var pathItem = fringe.remove();
            var stop = pathItem.stop();
            var edge = pathItem.edge();
            var cost = pathItem.cost();



            if (!backPointers.containsKey(stop)) {
                backPointers.put(stop, edge);

                if (stop == target)
                    return reconstructPath(backPointers, origin, target);

                for (var nEdge : stop.getForwardEdges()) {
                    var nStop = nEdge.toStop();
                    if (backPointers.containsKey(nStop)) continue;

                    var nCost = cost + edgeCost(nEdge);
                    var nHeuristic = nCost + heuristic(nStop, target);
                    fringe.add(new PathItem(nStop, nEdge, nCost, nHeuristic));
                }
            }
        }

        return List.of();
    }

    /**
     * Returns a path of edges from a map of nodes to edges for all explored nodes of our graph.
     * @param backPointers map of nodes to edges to reconstruct from
     * @param origin the starting stop
     * @param target the target stop
     * @return a List of Edges of the shortest path.
     */
    private static List<Edge> reconstructPath(HashMap<Stop, Edge> backPointers, Stop origin, Stop target) {
        var path = new ArrayList<Edge>();
        path.add(backPointers.get(target));
        var stop = target;

        while (stop != origin) {
            stop = backPointers.get(stop).fromStop();
            if (stop != origin)
                path.add(0, backPointers.get(stop));
        }

        return path;
    }


    /**
     * Return the heuristic estimate of the cost to get from a stop to the goal
     */
    public static double heuristic(Stop current, Stop goal) {
        if (timeOrDistance.equals("distance"))
            return current.distanceTo(goal);
        if (timeOrDistance.equals("time"))
            return current.distanceTo(goal) / Transport.TRAIN_SPEED_MPS;
        return 0;
    }

    /**
     * Return the cost of traversing an edge in the graph
     */
    public static double edgeCost(Edge edge) {
        if (timeOrDistance.equals("distance"))
            return edge.distance();
        if (timeOrDistance.equals("time"))
            return edge.time();
        return 1;
    }


}

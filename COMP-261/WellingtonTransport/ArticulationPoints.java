import java.util.Collections;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


//=============================================================================
//   TODO   Finding Articulation Points
//   Finds and returns a collection of all the articulation points in the undirected
//   graph, without walking edges
//=============================================================================

public class ArticulationPoints{

    // Use the algorithm from the lectures, but you will need a loop to check through
    // all the Stops in the graph to find any Stops which were not connected to the
    // previous Stops, and apply the lecture slide algorithm starting at each such stop.

    private static HashSet<Stop> articulationPoints;
    private static HashSet<Stop> unexplored;
    private static HashMap<Stop, Integer> depths;

    public static Collection<Stop> findArticulationPoints(Graph graph) {
        System.out.println("calling findArticulationPoints");
        graph.computeNeighbours();   // To ensure that all stops have a set of (undirected) neighbour stops

        depths = new HashMap<>();
        articulationPoints = new HashSet<>();
        unexplored = new HashSet<>();

        for (var stop : graph.getStops()) {
            depths.put(stop, -1);
            unexplored.add(stop);
        }

        if (unexplored.isEmpty()) return List.of();

        // Do this until we run out of graphs to explore.
        while (!unexplored.isEmpty()) {
            Stop root = unexplored.stream().findFirst().get();
            depths.put(root, 0);
            unexplored.remove(root);
            int subTreeCount = 0;

            // Do the thing.
            for (var neighbour : root.getNeighbours()) {
                if (depths.get(neighbour) != -1) continue;
                recArticulationPoints(neighbour, 1, root);
                subTreeCount++;
            }

            if (subTreeCount > 1) articulationPoints.add(root);
        }

        return articulationPoints;
    }

    // The other recursive bit.
    private static int recArticulationPoints(Stop stop, int depth, Stop fromStop) {
        int reachBack = depth;
        depths.put(stop, depth);
        unexplored.remove(stop);

        for (var neighbour : stop.getNeighbours()) {
            if (neighbour == fromStop) continue;
            if (depths.get(neighbour) != -1) {
                reachBack = Math.min(depths.get(neighbour), reachBack);
                continue;
            }

            var childReach = recArticulationPoints(neighbour, depth + 1, stop);

            if (childReach >= depth)
                    articulationPoints.add(stop);
            reachBack = Math.min(childReach, reachBack);
        }

        return reachBack;
    }
}

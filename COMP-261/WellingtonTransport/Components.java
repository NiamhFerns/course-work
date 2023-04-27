import java.util.*;

//=============================================================================
//   TODO   Finding Components
//   Finds all the strongly connected subgraphs in the graph
//   Labels each stop with the number of the subgraph it is in and
//   sets the subGraphCount of the graph to the number of subgraphs.
//   Uses Kosaraju's_algorithm   (see lecture slides, based on
//   https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm)
//=============================================================================

public class Components{

    // Use Kosaraju's algorithm.
    // In the forward search, record which nodes are visited with a visited set.
    // In the backward search, use the setSubGraphId and getSubGraphID methods
    // on the stop to record the component (and whether the node has been visited
    // during the backward search).
    // Alternatively, during the backward pass, you could use a Map<Stop,Stop>
    // to record the "root" node of each component, following the original version
    // of Kosaraju's algorithm, but this is unnecessarily complex.

    
    public static void findComponents(Graph graph) {
        System.out.println("calling findComponents");
        graph.resetSubGraphIds();

        ArrayList<Stop> stopList = new ArrayList<>();
        HashSet<Stop> visited = new HashSet<>();

        // Add to list.
        for (var stop : graph.getStops()) {
            if (visited.contains(stop)) continue;
            Components.forwardVisit(stop, stopList, visited);
        }

        // Find components.
        Collections.reverse(stopList);
        for (var stop : stopList) {
            if (stop.getSubGraphId() != -1) continue;
            backwardVisit(stop, graph.getSubGraphCount());
            graph.incrementSubGraphCount();
        }
    }

    private static void forwardVisit(Stop stop, ArrayList<Stop> stopList, HashSet<Stop> visited) {
        if (visited.contains(stop)) return;
        visited.add(stop);
        for (var nEdge : stop.getForwardEdges())
            forwardVisit(nEdge.toStop(), stopList, visited);
        stopList.add(stop);
    }

    private static void backwardVisit(Stop stop, int componentNumber) {
        if (stop.getSubGraphId() != -1) return;
        stop.setSubGraphId(componentNumber);
        for (var nEdge : stop.getBackwardEdges())
            backwardVisit(nEdge.fromStop(), componentNumber);
    }
}

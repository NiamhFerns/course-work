import java.util.*;
import javafx.util.Pair;

public class EdmondKarp {
    // String IDs to Edges: Even ID = Forward Edge, Odd ID = Reverse Edge
    private static HashMap<String, Edge> edges;

    /**
     * Build a residual graph for use by Edmond Karp algorithm.
     *
     * @param graph the original network.
     */
    public static void computeResidualGraph(Graph graph) {
        edges = new HashMap<>();
        int id = 0;
        for (var edge : graph.getOriginalEdges()) {
            // Add the forward edge and then give that edge ID to the "from" city.
            edge.fromCity().addEdgeId(String.valueOf(id));
            edges.put(
                    String.valueOf(id++),
                    new Edge(
                            edge.fromCity(),
                            edge.toCity(),
                            edge.transpType(),
                            edge.capacity(),
                            0
                    ));

            // Add the reverse edge and then give that edge ID to the "to" city.
            edge.toCity().addEdgeId(String.valueOf(id));
            edges.put(
                    String.valueOf(id++),
                    new Edge(
                            edge.toCity(),
                            edge.fromCity(),
                            edge.transpType(),
                            0,
                            0
                    ));

        }

        // Makes sure the cities have the correct links available.
        GraphUtils.computeLinks(graph);

        printResidualGraphData(graph);
    }


    /**
     * Find the maximum flow in a graph.
     *
     * @param graph a graph containing come network to analyse.
     * @param from  the source node.
     * @param to    the sink node.
     * @return a list of augmentation paths to bottlenecks.
     */
    public static ArrayList<Pair<ArrayList<String>, Integer>> calcMaxflows(Graph graph, City from, City to) {
        // Augmentation path and the corresponding flow.
        ArrayList<Pair<ArrayList<String>, Integer>> augmentationPaths = new ArrayList<>();
        computeResidualGraph(graph);
        Optional<Pair<ArrayList<String>, Integer>> pair = bfs(from, to);
        while(pair.isPresent()) {
            var path =  pair.get().getKey();
            var flow = pair.get().getValue();
            for (var id : path) {
                var idNum = Integer.parseInt(id);
                var edge = edges.get(id);
                // "We use Strings because in reality you don't need to do much ID arithmetic."
                var mirrorEdge = edges.get(idNum % 2 == 0 ? String.valueOf(idNum + 1) : String.valueOf(idNum - 1));

                edge.setCapacity(edge.capacity() - flow);
                edge.setFlow(flow);
                mirrorEdge.setCapacity(mirrorEdge.capacity() - flow);
                mirrorEdge.setFlow(flow);
            }
            augmentationPaths.add(pair.get());
            pair = bfs(from, to);
        }
        return augmentationPaths;
    }

    /**
     * BFS search through a residual graph and return an augmentation path in that residual.
     *
     * @param s the source node.
     * @param t the target node.
     * @return a pair containing the augmentation path as an array of IDs for the edges and an integer for the bottleneck.
     */
    public static Optional<Pair<ArrayList<String>, Integer>> bfs(City s, City t) {
        ArrayList<String> augmentationPath = new ArrayList<>();
        HashMap<City, String> backPointers = new HashMap<>();
        Queue<City> fringe = new LinkedList<>();
        fringe.add(s);

        OUTER:
        while (!fringe.isEmpty()) {
            var city = fringe.remove();
            // Only care about out neighbours.
            for (var edgeID : city.getEdgeIds()) {
                // If the neighbour is our source, or it's already in the backpointers, or it has no capacity left, skip.
                var edge = edges.get(edgeID);
                if (
                        edge.toCity() == s
                                || backPointers.containsKey(edge.toCity())
                                || edge.capacity() == 0
                ) {
                    continue;
                }

                backPointers.put(edge.toCity(), edgeID);
                if (edge.toCity() == t) break OUTER; // We found the target so there's a path. Exit loops.

                fringe.add(edges.get(edgeID).toCity());
            }
        }

        // No augmentation path exists.
        if (!backPointers.containsKey(t)) return Optional.empty();

        // Build augmentation path.
        var city = t;
        while (backPointers.containsKey(city) && city != s) {
            augmentationPath.add(backPointers.get(city));
            city = edges.get(backPointers.get(city)).fromCity();
        }

        Collections.reverse(augmentationPath);

        return Optional.of(new Pair<>(augmentationPath, findBottleneck(augmentationPath)));
    }

    /**
     * Find and return the bottleneck of some arbitrary augmentation path.
     *
     * @param augmentationPath a list of string IDs for each edge in the path.
     * @return bottleneck value.
     */
    public static int findBottleneck(ArrayList<String> augmentationPath) {
        int lowestCapacity = Integer.MAX_VALUE;
        for (var edge : augmentationPath) {
            lowestCapacity = Math.min(edges.get(edge).capacity(), lowestCapacity);
        }
        return lowestCapacity;
    }

    /**
     * Get a residual edge from its ID.
     *
     * @param id the ID of the edge you want.
     * @return the edge object.
     */
    public static Edge getEdge(String id) {
        return edges.get(id);
    }

    //=============================================================================
    //  DEBUG UTILITIES
    //=============================================================================
    public static void printResidualGraphData(Graph graph) {
        System.out.println("\nResidual Graph");
        System.out.println("\n=============================\nCities:");
        for (City city : graph.getCities().values()) {
            System.out.print(city.toString());

            // for each city display the out edges
            for (String eId : city.getEdgeIds()) {
                System.out.print("[" + eId + "] ");
            }
            System.out.println();
        }
        System.out.println("\n=============================\nEdges(Original(with even Id) and Reverse(with odd Id):");
        edges.forEach((eId, edge) -> System.out.println("[" + eId + "] " + edge.toString()));

        System.out.println("===============");
    }
}


class GraphUtils {
    public static void printCityData(Graph graph) {
        System.out.println("\nPage Rank Graph");

        for (City city : graph.getCities().values()) {
            System.out.print("\nCity: " + city.toString());
            //for each city display the in edges
            System.out.print("\nIn links to cities:");
            for (City c : city.getFromLinks()) {

                System.out.print("[" + c.getId() + "] ");
            }

            System.out.print("\nOut links to cities:");
            //for each city display the out edges
            for (City c : city.getToLinks()) {
                System.out.print("[" + c.getId() + "] ");
            }
            System.out.println();
        }
        System.out.println("=================");
    }

    public static void computeLinks(Graph graph) {
        for (var edge : graph.getOriginalEdges()) {
            var from = edge.fromCity();
            var to = edge.toCity();

            from.addToLinks(to);
            to.addFromLinks(from);
        }
    }
}


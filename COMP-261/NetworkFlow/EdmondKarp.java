
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Map;

import javafx.util.Pair;

/**
 * Edmond karp algorithm to find augmentation paths and network flow.
 * <p>
 * This would include building the supporting data structures:
 * <p>
 * a) Building the residual graph(that includes original and backward (reverse) edges.)
 * - maintain a map of Edges where for every edge in the original graph we add a reverse edge in the residual graph.
 * - The map of edges are set to include original edges at even indices and reverse edges at odd indices (this helps accessing the corresponding backward edge easily)
 * <p>
 * <p>
 * b) Using this residual graph, for each city maintain a list of edges out of the city (this helps accessing the neighbours of a node (both original and reverse))
 * <p>
 * The class finds : augmentation paths, their corresponing flows and the total flow
 */

public class EdmondKarp {
    // class members

    //data structure to maintain a list of forward and reverse edges - forward edges stored at even indices and reverse edges stored at odd indices
    private static Map<String, Edge> edges;

    // Augmentation path and the corresponding flow
    private static ArrayList<Pair<ArrayList<String>, Integer>> augmentationPaths = null;


    //TODO:Build the residual graph that includes original and reverse edges 
    public static void computeResidualGraph(Graph graph) {
        // TODO


        //printResidualGraphData(graph);  //may help in debugging
        // END TODO
    }

    // Method to print Residual Graph 
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
        edges.forEach((eId, edge) ->
                System.out.println("[" + eId + "] " + edge.toString()));

        System.out.println("===============");
    }

    //=============================================================================
    //  Methods to access data from the graph. 
    //=============================================================================

    /**
     * Return the corresonding edge for a given key
     */

    public static Edge getEdge(String id) {
        return edges.get(id);
    }

    /**
     * find maximum flow
     */
    // TODO: Find augmentation paths and their corresponding flows
    public static ArrayList<Pair<ArrayList<String>, Integer>> calcMaxflows(Graph graph, City from, City to) {
        //TODO


        // END TODO
        return augmentationPaths;
    }

    // TODO:Use BFS to find a path from s to t along with the correponding bottleneck flow
    public static Pair<ArrayList<String>, Integer> bfs(Graph graph, City s, City t) {

        ArrayList<String> augmentationPath = new ArrayList<String>();
        HashMap<String, String> backPointer = new HashMap<String, String>();
        // TODO


        // END TODO
        return new Pair(null, 0);
    }

}



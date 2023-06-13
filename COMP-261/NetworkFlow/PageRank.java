import java.util.*;

/**
 * Write a description of class PageRank here.
 *
 * @author Niamh Ferns
 * @version 28 05 2023
 */
public class PageRank {
    private static final double DAMPING_FACTOR = .85;
    private static final int ITER_COUNT = 10;

    /**
     * Make sure the cities have the correct links.
     *
     * @param graph the original network.
     */
    public static void computeLinks(Graph graph) {
        GraphUtils.computeLinks(graph); // PageRank.java shouldn't be responsible for this...
        GraphUtils.printCityData(graph); // PageRank.java shouldn't be responsible for this either...
    }

    /**
     * Computes and prints the rank for each node in the network.
     *
     * @param graph the original network.
     */
    public static void computePageRank(Graph graph) {
        var nNodes = (long) graph.getCities().keySet().size();
        var pageRanks = new HashMap<City, Double>();

        for (var city : graph.getCities().values()) {

            pageRanks.put(city, 1.0 / nNodes);
        }

        var count = 1;

        while (count < ITER_COUNT) {
            for (var node : graph.getCities().values()) {
                var nRank = 0.0;

                for (var fromLink : node.getFromLinks()) {
                    nRank += pageRanks.get(fromLink) / fromLink.getToLinks().size();
                }

                nRank = ((1 - DAMPING_FACTOR) / nNodes) + DAMPING_FACTOR * nRank;
                pageRanks.put(node, nRank);
            }

            count++;
        }

        for (var city : pageRanks.keySet().stream().sorted().toList()) {
            System.out.println(city.getName() + "[" + city.getId() + "]: " + pageRanks.get(city));
        }
    }
}

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;


/**
 * Graph is the data structure that stores the collection of stops, lines and connections. 
 * The Graph constructor is passed a Map of the stops, indexed by stopId and
 *  a Map of the Lines, indexed by lineId.
 * The Stops in the map have their id, name and GIS location.
 * The Lines in the map have their id, and lists of the stopIDs and times (in order)
 *
 * To build the actual graph structure, it is necessary to
 *  build the list of Edges out of each stop and the list of Edges into each stop
 * Each pair of adjacent stops in a Line is an edge.
 * We also need to create walking edges between every pair of stops in the whole
 *  network that are closer than walkingDistance.
 */
public class Graph {

    private Collection<Stop> stops;
    private Collection<Line> lines;
    private Collection<Edge> edges = new HashSet<Edge>();      // edges between Stops

    private int numComponents = 0;     // number of connected sub-graphs (graph components)

    /**
     * Construct a new graph given a collection of stops and a collection of lines.
     */
    public Graph(Collection<Stop> stops, Collection<Line> lines) {
        this.stops = new TreeSet<Stop>(stops);
        this.lines = lines;

        // These are two of the key methods you must complete:
        createAndConnectEdges();
        computeNeighbours();

        // printGraphData();   // you could uncomment this to help in debugging your code
    }


    /** Print out the lines and stops in the graph to System.out */
    public void printGraphData(){
        System.out.println("============================\nLines:");
        for (Line line : lines){
            System.out.println(line.getId()+ "("+line.getStops().size()+" stops)");
        }
        System.out.println("\n=============================\nStops:");
        for (Stop stop : stops){
            System.out.println(stop+((stop.getSubGraphId()<0)?"":" subG:"+stop.getSubGraphId()));
            System.out.println("  "+stop.getForwardEdges().size()+" out edges; "+
                               stop.getBackwardEdges().size() +" in edges; " +
                               stop.getNeighbours().size() +" neighbours");
        }
        System.out.println("===============");
    }


    //============================================
    // Methods to build the graph structure. 
    //============================================

    /** 
     * From the loaded Line and Stop information,
     *  identify all the edges that connect stops along a Line.
     * - Construct the collection of all Edges in the graph  and
     * - Construct the forward and backward neighbour edges of each Stop.
     */
    private void createAndConnectEdges() {
        for (var line : lines) {
            var lineType = line.getType();

            var stops = line.getStops();
            var times = line.getTimes();

            // This is safe because we don't add backwards edges on i = 0.
            double time = 0;
            double dist = 0;
            Edge edge = null;

            // Add all the intermediary edges.
            for (int i = 0; i < stops.size(); ++i) {
                // Backward edges.
                if (i > 0) {
                    stops.get(i).addBackwardEdge(edge);
                    edges.add(edge);
                }

                // Forward edges.
                if (i < stops.size() - 1) {
                    time = times.get(i + 1) - times.get(i);
                    dist = stops.get(i).distanceTo(stops.get(i + 1));

                    // We reassign the next edge after it is included as a backward edge.
                    edge = new Edge(
                            stops.get(i),
                            stops.get(i + 1),
                            lineType,
                            line,
                            time,
                            dist
                    );

                    stops.get(i).addForwardEdge(edge);
                    edges.add(edge);
                }

            }
        }



    }

    /** 
     * Construct the undirected graph of neighbours for each Stop:
     * For each Stop, construct a set of the stops that are its neighbours
     * from the forward and backward neighbour edges.
     * It may assume that there are no walking edges at this point.
     */
    public void computeNeighbours(){
        for (var stop : stops) {
            for (var neighbour : stop.getForwardEdges())
                stop.addNeighbour(neighbour.toStop());
            for (var neighbour : stop.getBackwardEdges())
                stop.addNeighbour(neighbour.fromStop());
        }
    }

    //=============================================================================
    //    Recompute Walking edges and add to the graph
    //=============================================================================
    //
    /** 
     * Reconstruct all the current walking edges in the graph,
     * based on the specified walkingDistance:
     * identify all pairs of stops * that are at most walkingDistance apart,
     * and construct edges (both ways) between the stops
     * add the edges to the forward and backward neighbours of the Stops
     * add the edges to the walking edges of the graph.
     * Assume that all the previous walking edges have been removed
     */
    public void recomputeWalkingEdges(double walkingDistance) {
        // Reset Stuff
        int count = 0;
        removeWalkingEdges();

        for (var stop : stops) {
            for (var neighbour : stops) {
                var distance = stop.distanceTo(neighbour);
                if (Transport.MAX_WALKING_DISTANCE_M < distance || walkingDistance < distance) continue;
                if (stop == neighbour) continue;

                count++;
                var edge = new Edge(
                        stop,
                        neighbour,
                        Transport.WALKING,
                        null,
                        distance / Transport.WALKING_SPEED_MPS,
                        stop.distanceTo(neighbour)
                );
                stop.addForwardEdge(edge);
                edges.add(edge);
            }
        }




        System.out.println("Number of walking edges added: " + count);
    }

    /** 
     * Remove all the current walking edges in the graph
     * - from the edges field (the collection of all the edges in the graph)
     * - from the forward and backward neighbours of each Stop.
     * - Resets the number of components back to 0 by
     *   calling  resetSubGraphIds()
     */
    public void removeWalkingEdges() {
        resetSubGraphIds();
        for (Stop stop : stops) {
            stop.deleteEdgesOfType(Transport.WALKING);// remove all edges of type walking
        }
        edges.removeIf((Edge e)->Transport.WALKING.equals(e.transpType()));
        
    }

    //=============================================================================
    //  Methods to access data from the graph. 
    //=============================================================================
    /**
     * Return a collection of all the stops in the network
     */        
    public Collection<Stop> getStops() {
        return Collections.unmodifiableCollection(stops);
    }
    /**
     * Return a collection of all the edges in the network
     */        
    public Collection<Edge> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

    /**
     * Return the first stop that starts with the specified prefix
     * (first by alphabetic order of name)
     */
    public Stop getFirstMatchingStop(String prefix) {
        for (Stop stop : stops) {
            if (stop.getName().startsWith(prefix)) {
                return stop;
            }
        }
        return null;
    }

    /** 
     * Return all the stops that start with the specified prefix
     * in alphabetic order.
     */
    public List<Stop> getAllMatchingStops(String prefix) {
        List<Stop> ans = new ArrayList<Stop>();
        for (Stop stop : stops) {
            if (stop.getName().startsWith(prefix)) {
                ans.add(stop);
            }
        }
        return ans;
    }

    public int getSubGraphCount() {
        return numComponents;
    }
    public void setSubGraphCount(int num) {
        numComponents = num;
        if (num==0){ resetSubGraphIds(); }
    }

    /**
     * reset the subgraph ID of all stops
     */
    public void resetSubGraphIds() {
        for (Stop stop : stops) {
            stop.setSubGraphId(-1);
        }
        numComponents = 0;
    }





}

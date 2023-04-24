import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

/**
 * Structure for holding stop information
 */

public class Stop implements Comparable<Stop> {
    // location of the stop
    private GisPoint loc;
    private String name;
    private String id;

    // data structure for holding a link to the lines that stop is part of
    private Collection<Line> lines = new HashSet<Line>();

    // data structure for holding the (directed) edges connecting to the stop
    private Collection<Edge> forwardEdges = new HashSet<Edge>();
    private Collection<Edge> backwardEdges = new HashSet<Edge>();

    // data structure for holding the set of (undirected) neighbours (stops) connected to this stop
    private Set<Stop> neighbours = new HashSet<Stop>();

    //Field to record the different subgraphs
    private int subGraphId = -1; // used to denote which subgraph the stop belongs to. -1 to indicate no subgraphs yet.


    /**
     * Constructor for a stop
     * 
     * @param id   4 or 5 digit stop id
     * @param name Long name for the stop
     * @param lat
     * @param lon
     */
    public Stop(double lon, double lat, String name, String id) {
        this.loc = new GisPoint(lon, lat);
        this.name = name;
        this.id = id;
    }

    //--------------------------------------------
    //  Getters and basic properties of a Stop
    //--------------------------------------------

    /**
     * Get the location of the stop
     * @return GisPoint object of location on earth
     */
    public GisPoint getPoint() {
        return this.loc;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns distance in meters between this stop and a GisPoint
     */
    public double distanceTo(GisPoint loc) {
        return this.loc.distance(loc);
    }
    
    /**
     * Returns distance in meters between this stop and another stop
     */
    public double distanceTo(Stop toStop) {
        return this.loc.distance(toStop.loc);
    }

    /**
     * Compare by alphabetic order of name,
     * If two stops have the same name, then
     * compare their id's in case they are not the same stop.
     */
    public int compareTo(Stop other){
        int ans = this.name.compareTo(other.name);
        if (ans!=0) {return ans;}
        return this.id.compareTo(other.id);
    }


    /** 
     * Display a stop
     * 
     * @return string of the stop information in the format: XXXX: long name at (lon,lat)
     */
    public String toString() {
        return id + ": " + name + " at (" + loc.getLon() + ", " + loc.getLat() + ")";
    }

    /**
     * @param a GisPoint to check if the stop is in an **identical** location
     * @return is this stop in the same location as the given point
     */
    public boolean atLocation(GisPoint point) {
        return this.loc.equals(point);
    }


    //-------------------------
    // Setting and getting the lines through this stop
    //-------------------------

    /**
     * adding a line that goes through this stop
     * @param line
     */
    public void addLine(Line line) {
        this.lines.add(line);
    }

    // get lines
    public Collection<Line> getLines() {
        return Collections.unmodifiableCollection(this.lines);
    }

    //--------------------------------------------
    //  Setting and getting the neighbours of the stop
    //
    //  forwardEdges is a collection of the (directed) edges out of the stop, and
    //  backwardEdges is a collection of the (directed) edges into the stop.
    //  neighbours is a collection of the stops that are connected (forward or back) this stop.
    //     (ie, the undirected graph)
    //--------------------------------------------

    /** Get the collection of forwardEdges*/
    public Collection<Edge> getForwardEdges() {
        return Collections.unmodifiableCollection(forwardEdges);
    }
         
    /** Get the collection of backwardEdges*/
    public Collection<Edge> getBackwardEdges(){
        return Collections.unmodifiableCollection(backwardEdges);
    }

    /** Get the collection of neighbouring Stops*/
    public Collection<Stop> getNeighbours() {
        return Collections.unmodifiableSet(neighbours);
    }
         
    /** add a new forward edge  */
    public void addForwardEdge(Edge edge) {
        this.forwardEdges.add(edge);
    }

    /** add a new backward edge  */
    public void addBackwardEdge(Edge e) {
        this.backwardEdges.add(e);
    }
    /**
     * Delete forward and backward edges of the specified type.
     */
    public void deleteEdgesOfType(String type) {
        // remove edges that are of the specified type
        forwardEdges.removeIf((Edge e)->type.equals(e.transpType()));
        backwardEdges.removeIf((Edge e)->type.equals(e.transpType()));
    }

    /**
     * Compute the neighbouring Stops - all the Stops at the other end of
     * both the forward and backward edges.
     */
    public void addNeighbour(Stop stop){
        neighbours.add(stop);
    }

    //--------------------------------------------
    // fields and methods for finding connected components / subgraphs.
    //--------------------------------------------
    
    /** 
     * @param subGraphId the id of the graph so stops in the game subgraph can be drawn in the same colour or highlighted
     */
    public void setSubGraphId(int id) {
        this.subGraphId = id;
    }

    public int getSubGraphId() {
        return subGraphId;
    }



}

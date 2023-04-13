/**
 * A directed edge in the graph, with associated data.
 * fromStop and toStop are the stops at the beginning and end of the edge
 * transpType is a String (as defined in the Transport class) specifying the type of edge
 * line is the line that this edge is part of (null if it is a WALKING edge)
 * time is the time it takes to travel this edge
 * distance is the distance along the edge.
 *
 * This could almost be a record class, but making the toString() efficient requires
 *  caching the string in a field that is not a parameter of the constructor.
 */

public class Edge {

    private final Stop fromStop;
    private final Stop toStop;
    
    private final String transpType; 
    private final Line line; 

    private final double time; // in seconds between the two stops of the edge
    private final double distance; // distance between the two stops of the edge

    private final String toString;   // compute the string representation just once.
    
    public Edge(Stop fromStop, Stop toStop, String transpType, Line line, double time, double distance){
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.transpType = transpType;
        this.line = line;
        this.time = time;
        this.distance = distance;
        this.toString = "FROM " +
            fromStop.getName() + "(" + fromStop.getId()+")  TO "+
            toStop.getName() + "(" + toStop.getId()+")  BY "+transpType+
            ((line!=null)?("(" + line.getId()+")"):"")+
            "  " + ((int)time) + "s/" + ((int)distance)+"m";
    }

    // todo add getters and setters

    public Stop fromStop() {return fromStop;}
    public Stop toStop() {return toStop;}
    public String transpType() {return transpType;}
    public Line line() {return line;}
    public double time() {return time;}
    public double distance() {return distance;}

    public String toString() {return this.toString;}

}

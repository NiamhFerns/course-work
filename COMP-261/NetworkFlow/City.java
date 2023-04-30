import java.util.Collection;
import java.util.Collections;
import javafx.geometry.Point2D;
import java.util.HashSet;
import javafx.util.Pair;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;


/**
 * Structure for holding city information
 */

public class City implements Comparable<City> {
    // location of the city
    private Point2D loc;
    private String name;
    private String id;

    // data structure for holding routes between cities to support EdmondKarp 
    //for efficient storage and look-up instead of maintaining a set of forward and backward(reverse) edges we just maintain a list of edge IDs where even ID indicates a forward edge and odd ID represents a backward(reverse) edge
    private Collection<String> edgeIds = new HashSet<String>();
    
    //data structures  for maintaining the count and set of inlinks and outlinks for each node in the graph to support PageRank
    private  Set<City> fromLinks = new HashSet<City>();
    private  Set<City>toLinks = new HashSet<City>();

    /**
     * Constructor for a city
     * 
     * @param id   4  digit city id
     * @param name Long name for the city
     * @param lat
     * @param lon
     */
    public City(double x, double y, String name, String id) {
        this.loc = new Point2D(x, y);
        this.name = name;
        this.id = id;

    }

    //--------------------------------------------
    //  Getters and basic properties of a City
    //--------------------------------------------

    /**
     * Get the location of the City
     */
    public Point2D getPoint() {
        return this.loc;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    /**
     * Returns distance in meters between this City and a Location
     */
    public double distanceTo(Point2D loc) {
        return this.loc.distance(loc);
    }

    /**
     * Returns distance in meters between this City and another City
     */
    public double distanceTo(City toCity) {
        return this.loc.distance(toCity.loc);
    }

    /**
     * Compare by alphabetic order of name,
     */
    public int compareTo(City other){
        return this.name.compareTo(other.name);
    }

    /** 
     * Display a City
     * 
     * @return string of the city information in the format: XXXX: name at (x,y)
     */
    public String toString() {
        // TODO: decide how you want to print a City
        //return id + ": " + name + " at (" + getPoint().getX() + ", " + getPoint().getY() + ")";
        return id + ": " + name;
    }

    /**
     * @param a Point to check if the city is in an **identical** location
     * @return is this city is at the same location as the given point
     */
    public boolean atLocation(Point2D point) {
        return this.loc.equals(point);
    }

    //-------------------------
    // Setting and getting the routes(edges) from this city
    //-------------------------

    //set of edge indices out of the city  - these would include actual edges in the graph and the reverse edges that we add to support Edmond-Karp algorithm
    //  
    //--------------------------------------------
    /** Get the collection of edges out of the city - remember this also includes reverse edges added to support Edmond-karp algorithm*/
    public Collection<String> getEdgeIds() {
        return Collections.unmodifiableCollection(edgeIds);
    }
    /** Get the collection of to cities */
    public Collection<City> getToLinks() {
        return Collections.unmodifiableCollection(toLinks);
    }
    /** Get the collection of from cities */
    public Collection<City> getFromLinks() {
        return Collections.unmodifiableCollection(fromLinks);
    }


    /** add a new edge  */
    public void addEdgeId(String edgeId) {
        this.edgeIds.add(edgeId);
    }
    
    /**
     * add a city to the set of fromlinks 
     */
    public void addFromLinks(City c){
        this.fromLinks.add(c);
        
    }

    /**
     * add a city to the set of tolinks 
     */
    public void addToLinks(City c){
        this.toLinks.add(c);
        
    }
    
}

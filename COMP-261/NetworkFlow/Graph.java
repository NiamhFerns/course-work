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
 * Graph is the data structure that stores the collection of Cities and connections(edges). 
 * The Graph constructor is passed a Map of the Cities, indexed by CityId and
 *  a collection of the (origninal) Edges.
 * The Cities in the map have their id, name and location.
 * The Edges are defined by their ids (which are defaulted to 0 in the Edge class), City from which it originates (fromCity), city to which it ends (toCity)
 */
public class Graph {

    // Cities data picked from node file

    private Map<String,City> cities = new HashMap<String,City>();

    // Original edges between Cities - the ones that are passed over from the edge file

    private Collection<Edge> originalEdges = new HashSet<Edge>();

    /**
     * Construct a new graph given a collection of Cities and a collection of edges
     */
    public Graph(Map<String,City> cities, Collection<Edge> originalEdges) {

        this.cities = cities;

        this.originalEdges = originalEdges;

        //printGraphData();   // uncomment this to help in debugging your code
    }

    //=============================================================================
    //  Method to prith graph data - cities and edges. 
    //=============================================================================
    public void printGraphData(){
        System.out.println("\nOriginal Graph");
        System.out.println("\n=============================\nCities:");
        for (City city : cities.values()){
            System.out.println(city.toString());
        }
        System.out.println("\n=============================\nEdges:");
        for (Edge e : originalEdges){
            System.out.println(e.toString());
        }
        System.out.println("===============");
    }

    //=============================================================================
    //  Methods to access data from the graph. 
    //=============================================================================
    /**
     * Return a map of all the Cities in the network
     */        
    public Map<String,City> getCities() {
        return Collections.unmodifiableMap(cities);
    }

    /**
     * Return a collection of all the edges in the network
     */ 

    public Collection<Edge> getOriginalEdges(){

        return Collections.unmodifiableCollection(originalEdges);
    }

    /**
     * Return the city cityid 
     */ 
    public City getCity(String id){
        return cities.get(id);
    }

}

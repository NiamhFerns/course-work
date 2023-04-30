/**
 * A directed edge in the graph, with associated data.
 * fromCity and toCity are the Cityies at the beginning and end of the edge
 * transpType is a String (as defined in the Transport class) specifying the type of edge
 * capacity is the capacity it takes to travel this edge
 * flow is the flow along the edge.
 *
 */

public class Edge {
    //private final String id;
    private final  City fromCity;
    private final City toCity;

    private final String transpType; 

    private int capacity; // capacity of the edge
    private  int flow; // flow of the edge

    
    public Edge(City fromCity, City toCity, String transpType,  int capacity, int flow){
        //this.id= id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.transpType = transpType;

        this.capacity = capacity;
        this.flow = flow;

    }

    // todo add getters and setters
    //public String getId(){return id;} 
    public City fromCity() {return fromCity;}

    public City toCity() {return toCity;}

    public String transpType() {return transpType;}

    public int capacity() {return capacity;}

    public int flow() {return flow;}

    public void setFlow(int f){flow = f;}

    public void setCapacity(int c){capacity = c;}

    public String toString() {return  "FROM " + 
        fromCity.getName() + "(" + fromCity.getId()+")  TO "+
        toCity.getName() + "(" + toCity.getId()+")  BY "+transpType+
        "  " + (capacity) +" " + (flow);}

}

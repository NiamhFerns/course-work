import java.io.File;
import java.util.*;

public class TestGraph{

    public static void main(String[] args){
        // load the input files
        System.out.println("Loading Graph Data...");
        Map<String, City> stopMap = Controller.loadCities(new File("data/node.csv"));
        Map<String,Edge> lines = Controller.loadLines(new File("data/edge.csv"), stopMap);
                               
        System.out.println("Loaded Graph Data; constructing graph....");
        Graph graph = new Graph(stopMap, lines.values());

        graph.printGraphData();
        
    }
}

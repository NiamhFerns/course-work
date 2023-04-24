import java.util.*;
import java.util.stream.*;


/**
 * Program to test the Articulation Points algorithm.
 *
 * The program builds a graph and then searches for the articulation points.
 * It reports all the points that your method should find, followed by the
 *  actual path your method found. If they don't match, then there is something
 *  wrong with your code - either you haven't built the graph correctly, or
 *  your AStar method is not correct.
 *
 *  Note, this testing program is not a full test of your articulation points method
 *  and does not attempt to find all possible errors, but it may be helpful.
 *
 */

public class TestArticPts{


    private static String theGraph ="""
        | Constructing the following graph for findArticulationPoints
        |
        |      _B--C--D--E--F
        |     / |   \\_|
        |    A  G     H
        |    \\   \\_ _/
        |     \\    I    _N_
        |      \\       / | \\
        |       J-----K--L--M
        |       |
        |       O--P--Q  R--S--T
        |        \\___/
        |""";
    private static Stop A = makeStop("A", 0, 1);
    //long path with lots of short edges:
    private static Stop B = makeStop("B", 1, 0);    // 3.6 across, 2 down = 4units
    private static Stop C = makeStop("C", 2, 0);
    private static Stop D = makeStop("D", 3, 0);
    private static Stop E = makeStop("E", 4, 0);
    private static Stop F = makeStop("F", 5, 0);
    private static Stop G = makeStop("G", 1, 1);
    private static Stop H = makeStop("H", 2, 2);
    private static Stop I = makeStop("I", 3, 10);
    private static Stop J = makeStop("J", 1, 3);
    private static Stop K = makeStop("K", 3, 3);
    private static Stop L = makeStop("L", 4, 3);
    private static Stop M = makeStop("M", 5, 3);
    private static Stop N = makeStop("N", 4, 2);
    private static Stop O = makeStop("O", 1, 4);
    private static Stop P = makeStop("P", 2, 4);
    private static Stop Q = makeStop("Q", 3, 4);
    private static Stop R = makeStop("R", 4, 4);
    private static Stop S = makeStop("S", 5, 4);
    private static Stop T = makeStop("T", 6, 4);


    private static Line L1 = makeLine("bus1",100, O,Q,P,O,J,A,B,C,D,E,F,E,D,C,B,A,J,O,P,Q,O);
    private static Line L2 = makeLine("bus2",100, B,G,I,H,D,H,C,H,I,G,B);
    private static Line L3 = makeLine("bus3",100, J,K,L,M,N,K,N,M,L,K,J);
    private static Line L4 = makeLine("bus4",100, R,S,T,S,R);


    private static String prefix = "| ";

    public static void testArticPts(String pre){
        if (pre!=null) {prefix = pre;}
        System.out.println(theGraph);

        //------------------------------------------------------------
        report("Making new Graph, which should construct the edges and neighbours");
        report("(See details of edges and stops at the end)");
        Graph graph = new Graph(List.of(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T),
                                List.of(L1,L2,L3,L4));

        report("===============");
        report("");
        report("Testing Articulation Points: (compare the target stops to the stops your code found)");
        report("--------------");
        try{
            reportAPoints(ArticulationPoints.findArticulationPoints(graph),
                          List.of(A,B,D,E,J,K,O,S));
        }catch(Exception e){report("Exception in findArticulationPoints: "+e);}
        report("");
        report("================");
        report("Details of Graph:");
        reportGraph(graph);

    }


    //make a stop on a grid, based on a reference point in the wellington city region
    public static Stop makeStop(String id, double lonSteps, double latSteps){
        return makeStop(id, lonSteps, latSteps, false);
    }

    //make a stop on a grid, based on a reference point in the wellington city region
    //if walking is true, then add an "across the road" increment as well.
    public static Stop makeStop(String id, double lonSteps,  double latSteps, boolean walking){
        double INCR  = .01;    // Standen St to Reading St (roughly 2 typical bus stops)
        double INCR_W = .0003;  // across the road
        double LON = 174.77;
        double LAT = -41.3;
        return new Stop(LON+lonSteps*INCR+(walking?INCR_W:0), LAT+latSteps*INCR+(walking?INCR_W:0), id, id);
    }


    private static Line makeLine(String name, int increment, Stop... stops){
        Line ans = new Line(name);
        int time = 0;
        for (Stop stop : stops){
            ans.addStop(stop, time);
            time+=increment;
        }
        return ans;
    }


    private static void report(String str){
        System.out.println(prefix+str);
    }

    private static void reportAPoints(Collection<Stop> aPoints, List<Stop> target){
        report("Target: "+ target.stream().map(Stop::getName).sorted().collect(Collectors.joining (",")));
        if (aPoints==null){
            report("Null collection returned");
        }
        else if (aPoints.isEmpty()){
            report("No articulation points found");
        }
        else {
            report("Found:  " + aPoints.stream().map(Stop::getName).sorted().collect(Collectors.joining (",")));
        }
    }

    private static void reportGraph(Graph graph){
        report("------------");
        report("Stops and Neighbours:");
        for (Stop s : graph.getStops().stream().sorted().toList()){
            report(String.format("%2s:   %s",
                                 s.getName(),
                                 s.getNeighbours().stream().map(Stop::getName).sorted().toList()));
        }
    }
    public static void main(String[] args){
        testArticPts(null);
    }

}

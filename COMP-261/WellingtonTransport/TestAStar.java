import java.util.*;
import java.util.stream.*;


/**
 * Program to test the AStar path finding.
 *
 * The program builds a graph and then searches for several paths in the graph
 * It reports the target path (that your method should find) followed by the
 *  actual path your method found. If they don't match, then there is something
 *  wrong with your code - either you haven't built the graph correctly, or
 *  your AStar method is not correct.
 * 
 * Note that an error in the AStar.java template means that finding paths by
 *  time does not work.
 * The final test uses time. To make it work, you would need to replace
 *  the first three lines of findShortestPath(..) by the following:
 *
 *      public static List<Edge> findShortestPath(Stop start, Stop goal, String tOrD) {
 *      if (start == null || goal == null) {return null;}
 *      timeOrDistance= (tOrD.equals("time"))?"time":"distance";
 *
 *  Note, this testing program is not a full test of your findShortestPath and does not attempt
 *   to find all possible errors, but it may be helpful.
 *
 */

public class TestAStar{


    private static String theGraph ="""
        | Constructing the following graph for AStar 
        |      ___________________________
        |     /                           \\
        |    A--B--C--D--E--F--G--H--I--J--K_
        |    |                            ___L
        |    |                           M___
        |    v                            ___N
        |    S-----T-----U               O___
        |     \\_    \\_    \\_              ___P
        |       \\_    \\_    \\_           Q
        |         \\     \\     \\           \\
        |          V-----W-----X-----------R
        |
        |
        |    Y--------Z
        |""";
    private static Stop A = makeStop("A", 0, 0);
    //long path with lots of short edges:
    private static Stop B = makeStop("B", 0.5, 0);    // 3.6 across, 2 down = 4units
    private static Stop C = makeStop("C", 1.0, 0);
    private static Stop D = makeStop("D", 1.5, 0);
    private static Stop E = makeStop("E", 2.0, 0);
    private static Stop F = makeStop("F", 2.5, 0.1);
    private static Stop G = makeStop("G", 3.0, 0);
    private static Stop H = makeStop("H", 3.5, 0);
    private static Stop I = makeStop("I", 4.0, 0);
    private static Stop J = makeStop("J", 4.5, 0);
    private static Stop K = makeStop("K", 5.0, 0);

    private static Stop L = makeStop("L", 5.3, 0.3);
    private static Stop M = makeStop("M", 4.7, 0.6);
    private static Stop N = makeStop("N", 5.3, 0.9);
    private static Stop O = makeStop("O", 4.7, 1.2);
    private static Stop P = makeStop("P", 5.3, 1.5);
    private static Stop Q = makeStop("Q", 4.7, 1.8);

    private static Stop R = makeStop("R", 5.0, 2);

    private static Stop S = makeStop("S", 0, 1);
    private static Stop T = makeStop("T", 1, 1);
    private static Stop U = makeStop("U", 2, 0.9);
    private static Stop V = makeStop("V", 1, 2);
    private static Stop W = makeStop("W", 2, 1.9);
    private static Stop X = makeStop("X", 3, 2.1);

    private static Stop Y = makeStop("Y", 0, 3);
    private static Stop Z = makeStop("Z", 2, 3);


    private static Line L0 = makeLine("JVL_0",30, A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R);  //time = 510
    private static Line L1 = makeLine("JVL_1",30, R,Q,P,O,N,M,L,K,J,I,H,G,F,E,D,C,B,A);  //time = 510
    private static Line L2 = makeLine("bus2", 500, A,K,A);                                   
    private static Line L3 = makeLine("bus3", 500, A,S,T,U,X,R,X,U,T,S,A);                        // time = 2500
    private static Line L4 = makeLine("bus4", 150, S,V,W,X,W,V,S);
    private static Line L5 = makeLine("bus5", 150, T,W,T);
    private static Line L6 = makeLine("bus8", 140, Y, Z, Y);


    private static String prefix = "| ";

    public static void testAStar(String pre){
        if (pre!=null) {prefix = pre;}
        System.out.println(theGraph);

        //------------------------------------------------------------
        report("Making new Graph, which should construct the edges and neighbours");
        report("(See details of edges and stops at the end)");
        Graph graph = new Graph(List.of(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z),
                                List.of(L0,L1,L2,L3,L4,L5,L6));

        report("===============");
        report("");
        report("Testing AStar: (compare the target path to the path your code found)");
        report("--------------");
        report("Finding shortest path from A to R, measured by distance:");
        reportPath(AStar.findShortestPath(A, R, "distance"),
                   List.of(A,S,T,W,X,R));
        report("--------------");
        report("Finding shortest path from W to I, measured by distance:");
        reportPath(AStar.findShortestPath(W, I, "distance"),
                   List.of(W,T,S,A,B,C,D,E,F,G,H,I));
        report("--------------");
        report("Finding shortest path from W to J, measured by distance:");
        reportPath(AStar.findShortestPath(W, J, "distance"),
                   List.of(W,X,R,Q,P,O,N,M,L,K,J));
        report("--------------");
        report("Finding shortest path from A to Z, measured by distance:");
        reportPath(AStar.findShortestPath(A, Z, "distance"),
                   null);
        report("--------------");
        report("Finding shortest path from A to A, measured by distance:");
        reportPath(AStar.findShortestPath(A, A, "distance"),
                   List.of());
        report("--------------");


        /* Because of an error in the AStar template, this doesn't work.
         * Replace the timeOrDistance parameter by tOrD and the 2nd line by
         *  timeOrDistance= (tOrD.equals("time"))?"time":"distance";
         * to be able to run this test also
         */
        report("Finding shortest path from A to R, measured by time:");
        report("(won't work unless you have corrected the error in the AStar template)");
        reportPath(AStar.findShortestPath(A, R, "time"),
                   List.of(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R));

        report("================");
        report("Details of Graph:");
        report("--------------");
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

    private static void reportPath(List<Edge> path, List<Stop> target){
        if (target==null){report("There is no path to the goal");}
        else if (target.isEmpty()){report("Target: - (start=goal)");}
        else {report("Target: "+ target.stream().map(Stop::getName).collect(Collectors.joining ("->")));}
        if (path==null){report("Path was null");}
        else if (path.isEmpty()){report("Path was empty - no path found, or goal=start");}
        else {
            report("Found:  " + path.get(0).fromStop().getName()+"->"+
                   path.stream().map(Edge::toStop).map(Stop::getName).collect(Collectors.joining ("->")));
        }
    }

    private static void reportGraph(Graph graph){
        report("All Edges:");
        int lineCount = -1;
        for (Edge e :
                 graph.getEdges().stream()
                 .sorted(Comparator.comparing(Edge::fromStop).thenComparing(Edge::toStop))
                 .toList()){
            if (0==(lineCount+=1)%5){System.out.print("\n"+prefix);}
            System.out.printf("%2s->%s (%4.0f/%3.0f), ",
                              e.fromStop().getName(),
                              e.toStop().getName(),
                              e.distance(), e.time());
        }
        System.out.println();
        report("------------");
        report("All Stops:");
        report("    OUT edges to: IN edges from:  NEIGHBOURS:");
        for (Stop s : graph.getStops().stream().sorted().toList()){
            report(String.format("%2s:   %-13s %-14s  %s",
                                 s.getName(),
                                 s.getForwardEdges().stream().map(Edge::toStop).map(Stop::getName).sorted().toList(),
                                 s.getBackwardEdges().stream().map(Edge::fromStop).map(Stop::getName).sorted().toList(),
                                 s.getNeighbours().stream().map(Stop::getName).sorted().toList()));
        }
    }
    public static void main(String[] args){
        testAStar(null);
    }

}


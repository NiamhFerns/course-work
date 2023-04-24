import java.util.*;
import java.util.stream.*;


/**
 * Program to test the graph construction (edges and neighbours) and the
 * Strongly Connected Components algorithm for the WellingtonTransport assignment.
 *
 * Creates a small graph (23 stops,26 edges) and the computes the connected components.
 * It then adds walking edges (8 edges) and computes the connected components again.
 *
 * Expected output is listed as a comment at the end of the file.
 * 
 *  Stops are guaranteed to have unique names, therefore it doesn't matter whether 
 *   the Stops class has the original or the corrected version of compareTo(..)
 *  The listing of stops and edges is done in alphabetic order to make it easy to
 *   compare your results with the expected output.
 *
 *  Your program may number the components in a different order, but the reporting
 *   in this program canonicalises the numbering of the components 
 *   so that your results can be compared to the expected output.
 *
 *  Each output line of the test program starts with a | to help you distinguish
 *   the test output from any debugging output in your code.
 *
 *  Note, this testing program is not a full test of your program and does not attempt
 *   to find all possible errors, but it may be helpful in your testing
 *   
 */

public class TestGraphAndComponents{


    private static String theGraph ="""
        | Constructing the following graph:
        |
        |    A<-->B-->C-->D-->E-->F-->G-->H-->I-->J
        |             ^   |       ^       |      ^^
        |             |   |       |       |      ||
        |             |   v       |       v      ||
        |             K<--L       M<--N<--O-->P-->Q
        |                        wM<-wN<-wO<-wP
        |
        |    Y<------>Z
        |""";
    private static Stop A = makeStop("A", 0, 0);
    private static Stop B = makeStop("B", 1, 0);
    private static Stop C = makeStop("C", 2, 0);
    private static Stop D = makeStop("D", 3, 0);
    private static Stop E = makeStop("E", 4, 0);
    private static Stop F = makeStop("F", 5, 0);
    private static Stop G = makeStop("G", 6, 0);
    private static Stop H = makeStop("H", 7, 0);
    private static Stop I = makeStop("I", 8, 0);
    private static Stop J = makeStop("J", 9, 0);
    
    private static Stop K = makeStop("K", 2, 1);
    private static Stop L = makeStop("L", 3, 1);
    
    private static Stop M = makeStop("M", 5, 1);
    private static Stop N = makeStop("N", 6, 1);
    private static Stop O = makeStop("O", 7, 1);
    private static Stop P = makeStop("P", 8, 1);
    private static Stop Q = makeStop("Q", 9, 1);

    private static Stop wM = makeStop("wM", 5, 1, true);
    private static Stop wN = makeStop("wN", 6, 1, true);
    private static Stop wO = makeStop("wO", 7, 1, true);
    private static Stop wP = makeStop("wP", 8, 1, true);
    
    private static Stop Y = makeStop("Y", 0, 2);
    private static Stop Z = makeStop("Z", 2, 2);


    private static Line L1 = makeLine( "JVL_0",10, A,B,C,D,E,F,G,H,I,J);
    private static Line L2 = makeLine("bus2", 40, D,L,K,C);
    private static Line L3 = makeLine("bus3", 40, H,O,N,M,F);
    private static Line L4 = makeLine("bus4", 80, O,P,Q,J);
    private static Line L5 = makeLine("JVL_1", 10, Q,J);
    private static Line L6 = makeLine("bus6", 40, B,A);
    private static Line L7 = makeLine("bus7", 40, wP,wO,wN,wM);
    private static Line L8 = makeLine("bus8", 40, Y, Z, Y);


    private static String prefix = "|| "; 

    public static void testMakeGraphAndComponents(String pre){
        prefix = pre;
        System.out.println(theGraph);                        

        //------------------------------------------------------------
        report("Make new Graph, which should construct the edges and neighbours");
        Graph graph = new Graph(List.of(A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,Y,Z,wM,wN,wO,wP),
                                List.of(L1,L2,L3,L4,L5,L6,L7,L8));


        report("All Edges: (should match the graph above)");
        report("  distances are 835 (East-West) or 1112 (North-South) except Y-Z (1671)");
        report("  times are 40 (B-A, D-L-K-C, H-O-N-M-F, Y-Z and wP-wO-wN-wM), 80 (O-P-Q-J), and 10 otherwise");
        for (Edge e :
                 graph.getEdges().stream()
                 .sorted(Comparator.comparing(Edge::fromStop).thenComparing(Edge::toStop))
                 .toList()){
            report(String.format("%2s->%s (%.0f/%.0f)",
                                 e.fromStop().getName(),
                                 e.toStop().getName(),
                                 e.distance(), e.time()));
        }

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
        // remember the neighbours, to compare with after adding walking edges
        Map<Stop,Collection<Stop>> oldNeighbours = new HashMap<Stop,Collection<Stop>>();
        for (Stop s : graph.getStops()){
            oldNeighbours.put(s, new HashSet<Stop>(s.getNeighbours()));
        }
        

        //------------------------------------------------------------
        report("===============");
        report("Number of initial components (should be 0): "+ graph.getSubGraphCount());
        report("Calling findComponents");
        Components.findComponents(graph);
        reportComponents(graph, 13);

        report("===============");
        report("Adding Walking edges: ");
        report(" Should be 8 edges: M->wM, N->wN, O->wO, P->wP, wM->M, wN->N, wO->O, wP->P");
        report(" with a dist/time of 42/30");
                           graph.resetSubGraphIds();
                           graph.removeWalkingEdges();
        graph.recomputeWalkingEdges(200);
        List<Edge> walkingEdges =
            graph.getEdges().stream()
            .filter((Edge edge)-> edge.transpType()==Transport.WALKING)
            .sorted(Comparator.comparing(Edge::fromStop) .thenComparing(Edge::toStop))
            .toList();
        for (Edge e : walkingEdges){
            report(String.format("%s -> %s (%.0f/%.0f)",
                                 e.fromStop().getName(),
                                 e.toStop().getName(),
                                 e.distance(),e.time()));
        }
        List<Stop> walkingStops =
            Stream.concat(walkingEdges.stream().map(Edge::fromStop),
                          walkingEdges.stream().map(Edge::toStop))
            .sorted().distinct().toList();

        report("------------");
        report("Stops with walking edges in or out");
        report(" should be only M,N,O,P,wM,wN,wO,wP)");
        report(" no stops should have new neighbours");
        report("    OUT edges to: IN edges from:  NEW NEIGHBOURS:");
        for (Stop s : walkingStops){
            report(String.format("%2s:   %-13s %-14s  %s",
                                 s.getName(),
                                 s.getForwardEdges().stream()
                                 .filter((Edge edge)-> edge.transpType()==Transport.WALKING)
                                 .map(Edge::toStop).map(Stop::getName).sorted().toList(),
                                 s.getBackwardEdges().stream()
                                 .filter((Edge edge)-> edge.transpType()==Transport.WALKING)
                                 .map(Edge::fromStop).map(Stop::getName).sorted().toList(),
                                 s.getNeighbours().stream()
                                 .filter((Stop n)->!oldNeighbours.get(s).contains(n)).toList()));
        }
        report("--------------");
        report("Calling findComponents with walking edges");
        Components.findComponents(graph);
        reportComponents(graph, 8);

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

    // replace component numbers by a canonical numbering to ensure result is independent of
    // which node the findComponents method started with.
    private static void reportComponents(Graph graph, int target){
        Map<Integer,Integer> canonical = new HashMap<Integer,Integer>();
        int gid = 0;
        for (Stop s : graph.getStops().stream().sorted().toList()){
            int actual = s.getSubGraphId();
            if (!canonical.containsKey(actual)){canonical.put(actual, gid++);}
        }
        Map<Integer,List<String>> components = new TreeMap<Integer,List<String>>();
        for (Stop s : graph.getStops()){
            int can = canonical.get(s.getSubGraphId());
            if (!components.containsKey(can)){components.put(can,new ArrayList<String>());}
            components.get(can).add(s.getName());
        }
        report("--------------");
        report(String.format("Number of Components %d (should be %d)",
                             graph.getSubGraphCount(), target));
        report("Stops in the (canonicalised) components:");
        for (int can : components.keySet()){
            report(String.format("%2d: %s", can, components.get(can).stream().sorted().toList()));
        }
    }

    public static void main(String[] args){
        testMakeGraphAndComponents("| ");  
    }

}

/* Expected output:

|Constructing the following graph:
|
|    A<-->B-->C-->D-->E-->F-->G-->H-->I-->J
|             ^   |       ^       |      ^^
|             |   |       |       |      ||
|             |   v       |       v      ||
|             K<--L       M<--N<--O-->P-->Q
|                        wM<-wN<-wO<-wP
|
|    Y<------>Z
|
| Make new Graph, which should construct the edges and neighbours
| All Edges: (should match the graph above)
|   distances are 835 (East-West) or 1112 (North-South) except Y-Z (1671)
|   times are 40 (B-A, D-L-K-C, H-O-N-M-F, Y-Z and wP-wO-wN-wM), 80 (O-P-Q-J), and 10 otherwise
|  A->B (835/10)
|  B->A (835/40)
|  B->C (835/10)
|  C->D (835/10)
|  D->E (835/10)
|  D->L (1112/40)
|  E->F (835/10)
|  F->G (835/10)
|  G->H (835/10)
|  H->I (835/10)
|  H->O (1112/40)
|  I->J (835/10)
|  K->C (1112/40)
|  L->K (835/40)
|  M->F (1112/40)
|  N->M (835/40)
|  O->N (835/40)
|  O->P (835/80)
|  P->Q (835/80)
|  Q->J (1112/80)
|  Q->J (1112/10)
|  Y->Z (1671/40)
|  Z->Y (1671/40)
| wN->wM (835/40)
| wO->wN (835/40)
| wP->wO (835/40)
| ------------
| All Stops:
|     OUT edges to: IN edges from:  NEIGHBOURS:
|  A:   [B]           [B]             [B]
|  B:   [A, C]        [A]             [A, C]
|  C:   [D]           [B, K]          [B, D, K]
|  D:   [E, L]        [C]             [C, E, L]
|  E:   [F]           [D]             [D, F]
|  F:   [G]           [E, M]          [E, G, M]
|  G:   [H]           [F]             [F, H]
|  H:   [I, O]        [G]             [G, I, O]
|  I:   [J]           [H]             [H, J]
|  J:   []            [I, Q, Q]       [I, Q]
|  K:   [C]           [L]             [C, L]
|  L:   [K]           [D]             [D, K]
|  M:   [F]           [N]             [F, N]
|  N:   [M]           [O]             [M, O]
|  O:   [N, P]        [H]             [H, N, P]
|  P:   [Q]           [O]             [O, Q]
|  Q:   [J, J]        [P]             [J, P]
|  Y:   [Z]           [Z]             [Z]
|  Z:   [Y]           [Y]             [Y]
| wM:   []            [wN]            [wN]
| wN:   [wM]          [wO]            [wM, wO]
| wO:   [wN]          [wP]            [wN, wP]
| wP:   [wO]          []              [wO]
| ===============
| Number of initial components (should be 0): 0
| Calling findComponents
| --------------
| Number of Components 13 (should be 13)
| Stops in the (canonicalised) components:
|  0: [A, B]
|  1: [C, D, K, L]
|  2: [E]
|  3: [F, G, H, M, N, O]
|  4: [I]
|  5: [J]
|  6: [P]
|  7: [Q]
|  8: [Y, Z]
|  9: [wM]
| 10: [wN]
| 11: [wO]
| 12: [wP]
| ===============
| Adding Walking edges: 
|  Should be 8 edges: M->wM, N->wN, O->wO, P->wP, wM->M, wN->N, wO->O, wP->P
|  with a dist/time of 42/30
| M -> wM (42/30)
| N -> wN (42/30)
| O -> wO (42/30)
| P -> wP (42/30)
| wM -> M (42/30)
| wN -> N (42/30)
| wO -> O (42/30)
| wP -> P (42/30)
| ------------
| Stops with walking edges in or out
|  should be only M,N,O,P,wM,wN,wO,wP)
|  no stops should have new neighbours
|     OUT edges to: IN edges from:  NEW NEIGHBOURS:
|  M:   [wM]          [wM]            []
|  N:   [wN]          [wN]            []
|  O:   [wO]          [wO]            []
|  P:   [wP]          [wP]            []
| wM:   [M]           [M]             []
| wN:   [N]           [N]             []
| wO:   [O]           [O]             []
| wP:   [P]           [P]             []
| --------------
| Calling findComponents with walking edges
| --------------
| Number of Components 8 (should be 8)
| Stops in the (canonicalised) components:
|  0: [A, B]
|  1: [C, D, K, L]
|  2: [E]
|  3: [F, G, H, M, N, O, P, wM, wN, wO, wP]
|  4: [I]
|  5: [J]
|  6: [Q]
|  7: [Y, Z]

*/

/*
 * Niamk Kirsty Ferns
 * 21007069
 * Shortest Path Finder - Weighted Graphs
 */

#include<iostream>
#include<cstdlib>
#include<fstream>
#include<string>
#include<unordered_map>
#include<map>

// You need a struct or a vertex or something. Idk man, fuck this shit honestly.

class Graph
{
    private:
        struct Edge
        {
            char destination;
            int weight;
            Edge * next;
        };

        // This is much much easier than using a vector and is just as fast.
        std::unordered_map<char, Edge *> Vertices;


    public:
        void add_vertex(char vertex);
        void add_edge(char origin, char destination, int weight);

        void run_dijkstra(char c);
};

void read_file(const std::string & s, Graph & g);
std::string lower_case(std::string s);

int main(int argc, char ** argv)
{
    Graph graph;

    if (argc != 2) { std::cout << "Please pass in 1 and only 1 file name." << std::endl; exit(0); }
    read_file(argv[1], graph);
    graph.run_dijkstra('A');


    return 0;

}

void Graph::add_vertex(char vertex)
{
    if (Vertices.find(vertex) != Vertices.end()) return;
    Vertices[vertex] = nullptr;
}

void Graph::add_edge(char origin, char destination, int weight)
{
    if (Vertices.find(origin) == Vertices.end()) return;
    if (!Vertices[origin])
    {
        Vertices[origin] = new Edge{destination, weight, nullptr};
        return;
    }

    Edge * current = Vertices[origin];
    while (current->next)
    {
        current = current->next;
    }
    current->next = new Edge{destination, weight, nullptr};
}

void Graph::run_dijkstra(char origin)
{
    /*
    * -------------------------------------------
    * PSEUDOCODE FOR ALGORITHM (WITH MAPS NOT VECTORS)
    * -------------------------------------------
    * Takes in a Graph & and Source Vertex
    *
    * declare map of state structs (distance, state) dest[N]
    * declare a graph node current = SourceNode
    *
    * dest[SourceNode] = {0, p};
    * for (each vertex v of G) do
    *     if (v != SourceNode then)
    *         dest[v] = (∞, t)
    *     end if
    * end for
    *
    * while (there is a vertex v with state dest[v].state == t) do
    *     for (each neighbour v of current) do
    *         dest[v].distance = min (dest[v].distance, dest[current].distance + cost(current, v));
    *     end for
    *     find minimum dest[v].distance with state dest[v].state == t, make current = v;
    *     dest[current].state = p;
    * end while
    *
    */

    // Initialising everything.
    struct Destination
    {
        int cost;
        bool permenant;
    };

    std::map<char, Destination> costs;
    for (auto & [vertex, adjacencies] : Vertices)
    {
        // -1: infinity
        // 0 : temp
        costs[vertex] = {999999999, 0};
    }
    costs[origin] = {0, 1};
    char current = origin;

    // Run Dijkstra's
    bool running = true;
    while(running)
    {
        Edge * v;
        v = Vertices[current];
        while (v)
        {
            costs[v->destination].cost = std::min(costs[v->destination].cost, costs[current].cost + v->weight);
            v = v->next;
        }

        // Find our next current
        bool switch_node = true;
        for(auto & [vertex, desination] : costs)
        {
            if(switch_node && !costs[vertex].permenant)
            {
                current = vertex;
                switch_node = false;
                continue;
            }

            current = (desination.cost < costs[current].cost && !desination.permenant ? vertex : current);
        }
        costs[current].permenant = true;

        for (auto & [vertex, destination] : costs)
        {
            if (!destination.permenant) { running = true; break; }
            running = false;
        }
    }


    // Print all our values (easy because in an ordered map. c:)
    for (auto & [vertex, destination] : costs)
    {
        if (vertex == origin) continue;
        std::cout << "From " << origin << " to " << vertex << ":" << destination.cost << std::endl;
    }
}

// Utility functions.


void read_file(const std::string & s, Graph & g)
{
    std::ifstream input;
    input.open(s);
    if(input.is_open() == false) { std::cout << "Could not read file: " << std::endl << s << std::endl; exit(0); }

    std::string e;

    while(std::getline(input, e))
    {
        // Skip commented lines.
        if (e[0] == '#') continue;

        // Node
        if (lower_case(e.substr(0, 4)) == "node")
        {
            g.add_vertex(e[e.size() - 2]);
            continue;
        }

        // Edge between nodes.
        g.add_edge(e[0], e[2], std::stoi(e.substr(3, e.size())));
    }
}

std::string lower_case(std::string s)
{
    for (int i = 0; i < s.size(); ++i) {
        s[i] = std::tolower(s[i]);
    }
    return s;
}

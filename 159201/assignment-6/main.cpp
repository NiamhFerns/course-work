/*
 * Niamk Kirsty Ferns
 * 21007069
 * Shortest Path Finder - Weighted Graphs
 */

// NOTE: THIS USES C++17. YOU WILL NEED IT FOR THE STRUCTURERD BINDINGS.

#include<iostream>
#include<cstdlib>
#include<fstream>
#include<string>
#include<unordered_map>
#include<map>
#include<limits>
#include<vector>

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

// Ideally, we'd want to be able to return a map of our graph, but with this algorithm, that would mean we need to convert the map to bet a <char, int>
// and not a <char, struct>.
void Graph::run_dijkstra(char origin)
{
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
        costs[vertex] = {std::numeric_limits<int>::max(), 0};
    }
    costs[origin] = {0, 1};
    char current = origin;

    auto edge_check = [](std::map<char, Destination> costs) -> bool {
        for (auto & [vertex, destination] : costs)
            if (!destination.permenant) { return true; }
        return false;
    };

    // Run Dijkstra's
    while(edge_check(costs))
    {
        // Run through each possible destination of our current vertex.
        // Find the minimum cost for each one.
        Edge * v = Vertices[current];
        while (v)
        {
            costs[v->destination].cost = std::min(costs[v->destination].cost, costs[current].cost + v->weight);
            v = v->next;
        }

        // Find valid options that are not permenant.
        std::vector<char> options;
        for(auto & [vertex, desination] : costs)
            if(!desination.permenant) options.emplace_back(vertex);

        // Cycle through said options to find the minimum and make that our next location.
        current = options[0];
        for(auto v : options)
            if(costs[current].cost > costs[v].cost) current = v;

        // Set that location to permenant.
        costs[current].permenant = true;
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

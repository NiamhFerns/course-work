// Niamh Kirsty Ferns - 21007069 - Router Traffic Simulator
// This was completed with a mix of the starter code from stream that I heavily editted as well as my own code.
// I changed a lot of the variable names and recommented everything just so that the program is clearer and easier
// to read.
//
// I removed and refactored a significant amount of the starter code and removed the unneeded fluff.

#include <iostream>
#include <ostream>
#include <string>
#include <fstream>
#include <sstream>
#include <cstdlib>

// This is the delay for the output clock.
#define TIME_DELAY 3
// This is the maximum amount of ports our simulation can handle.
#define MAX_PORTS 128

template<class T>
class Queue {
    private:
        struct Element {
            T content;
            Element * next_element;
        };
        Element * l_head, * l_tail;
        int queue_len;

    public:
        Queue();
        ~Queue() {}

        // Add a new element to the tail of the queue.
        void join(T element);
        // Intent is the clear the head but we return the value at the same time as it's convienient.
        T leave();
        // Intent is to return the value from the head with no editting of the queue.
        T front();

        // QoL functions to prevent bugs.
        bool is_empty();
        int length();
        void print();
};

// Resets all queues to null.
void reset_input_queues();

// Resets the OutQueues_current and Congestion_Size arrays to starting values.
void init_simulation();

// Monitor packet traffic in simulation.
int Congestion_Size[MAX_PORTS];

// We use pointers so that we can create queues as we need them.
Queue<int> * Input_Queues[MAX_PORTS];
Queue<int> * Output_Queues[MAX_PORTS];

int main(int argc, char **argv) {
    int current_port = 0, total_ports = 0, packet_content = 0, destination_port = 0;
    std::string expression, token;
    std::ifstream input_file;
    reset_input_queues();

    // ---------------- //
    // SETUP SIMULATION //
    // ---------------- //

    // Open our file for IO.
    if(argc != 2) { std::cout<< "Type a file name. " << std::endl << argv[1] << std::endl; exit(0); }
    input_file.open(argv[1]);
    if(input_file.is_open() == false) { std::cout << "Could not read file: " << std::endl << argv[1] << std::endl; exit(0); }

    // Read contents of file into memory.
    while(!input_file.eof()){
        getline(input_file, expression);
        std::stringstream line(expression);

        if(input_file.eof()) break;

        // Read in our meta data.
        //
        // # - this means that the line is a comment and should be ignored.
        // P - this means that the line contains our port numebr.
        // Anything else will count as relevant data for each queue.
        if(expression[0] =='#') continue;
        if(expression[0] =='P') {
            getline(line, token, ' ');
            getline(line, token, ' ');
            // Note: atoi() is used for compatibility with < C++11;
            total_ports = atoi(token.c_str());
            continue;
        }

        Input_Queues[current_port] = new Queue<int>;
        Output_Queues[current_port] = new Queue<int>;

        // Read in our input queues seperating by spaces.
        while(getline(line, token, ' ')) {
            packet_content = atoi(token.c_str());
            // This simulation assumes the only content a packet contains is its destination.
            if (packet_content <= 0 || packet_content > total_ports || current_port > total_ports - 1) { std::cout << "ERROR in the format of the text file" << std::endl; exit(0); }
            Input_Queues[current_port]->join(packet_content);
        }

        ++current_port;
    }

    // -------------- //
    // RUN SIMULATION //
    // -------------- //

    init_simulation();
    unsigned long int clock = 0;
    unsigned long int current_conjestion = 0, max_conjestion = 0;
    current_port = 0;

    do {
        // Move a packet from input to the correct output. Again, this assumes the only data each packet holds is its destination.
        if (!Input_Queues[current_port]->is_empty()) {
            destination_port = Input_Queues[current_port]->leave();
            Output_Queues[destination_port - 1]->join(destination_port);
        }


        // We have a delay to represent packets leaving the output queues every N cycles.
        // Let t = our delay, p = the number of ports. Then, N = t * p.
        if(clock % (TIME_DELAY * total_ports) == 0 && clock != 0) {
            // Send packets from output queues.
            for(int i = 0; i < total_ports; ++i){
                Output_Queues[i]->leave();
            }
        }

        // Calculate the current and max conjestion level AFTER packets have left the output queues0
        current_conjestion = 0;
        for (int i = 0; i < total_ports; ++i) { current_conjestion += Output_Queues[i]->length(); }

        if (current_conjestion > max_conjestion) {
            for (int i = 0; i < total_ports; ++i) {
                Congestion_Size[i] = Output_Queues[i]->length();
                max_conjestion = current_conjestion;
            }
        }
        current_port = (current_port + 1) % total_ports;
        clock++;
    } while(current_conjestion > 0);

    // Final print out.
    for(int i = 0; i < total_ports; ++i){
        std::cout << "output port " << i + 1 << ": " << Congestion_Size[i] << " packets" << std::endl;
    }

    return 0;
}

void reset_input_queues() {
    for (auto * item : Input_Queues) {
        delete item; // Avoid memory leaks. c:
        item = nullptr;
    }
}

void init_simulation(){
  for(int i = 0; i < MAX_PORTS; ++i){
    Congestion_Size[i] = 0;
  }
}

template<class T>
Queue<T>::Queue() {
    l_head = l_tail = nullptr;
    queue_len = 0;
}

template<class T>
void Queue<T>::join(T element) {
    Element * e = new Element({element, nullptr});
    if (is_empty()) l_head = e;
    else l_tail->next_element = e;
    l_tail = e;
    ++queue_len;
}

template<class T>
T Queue<T>::leave() {
    if (is_empty()) return 0;

    T data = l_head->content;
    Element * to_delete = l_head;

    l_head = l_head->next_element;
    --queue_len;
    delete to_delete;
    return data;
}

template<class T>
T Queue<T>::front() {
    if (is_empty()) return 0;
    return l_head->content;
}

template<class T>
bool Queue<T>::is_empty() {
    // We can also return whether the length == 0 but I take "empty" to mean "there is no queue"
    // in which case !l_head makes more sense as there literally is no pointer to a queue.
    return !l_head;
}

template<class T>
int Queue<T>::length() {
    return queue_len;
}

template<class T>
void Queue<T>::print() {
    if (is_empty()) { std::cout << "Queue is empty..." << std::endl; return; }
    Element * e = l_head;
    while (e) {
        std::cout << e->content << " ";
        e = e->next_element;
    }
    std::cout << std::endl;
}

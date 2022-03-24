#include <cstdlib>
#include <iostream>
#include <fstream>
#include <string>

template<class T>
class Stack {
    private:
        struct Stack_Node {
            T data;
            Stack_Node *next_node;
        };
        Stack_Node *current, *l_pointer;

    public:
        // Empty
        Stack();
        ~Stack();

        // NOTE: These functions aren't required for the assignment, but they're here as the idea behind this is
        //       class is to provide functionality you'd want from a stack class, not just the functionality
        //       needed in this assingment.

        Stack(T item); // Add a single item.
        Stack(T items[]); // Add multiple items via an array.

        void push();
        // These give back a copy of the data rather than a pointer to the node because otherwise we run into memory
        // leak issues with pop(), and Top() would allow the private data to be editted outside of the class.
        T pop();
        T top();

        bool is_empty();

        // NOTE: Again, this is unneeded for this assignment but is here because this is supposed to be an
        //       implementation of a stack, not just the functionality for this assignment.
        int length();
};

// For this example we will assume it is an int. This can be easily changed to include floats and other types.
void run_operation(char c, Stack<int> & s);

int main(int argc, char** argv) {

    if (argc == 1) {
        std::cout << "Please provide a file argument..." << std::endl;
        exit(0);
    }

    std::ifstream file;
    file.open(argv[1]);
    if (!file.good()) {
        std::cout << "Unfortunately, your file failed to open." << std::endl;
        exit(0);
    }

    std::string s;
    while(!file.eof()) {
        getline(file, s);
        if (isdigit(s[0])) {
            // expression.push(s) you'll need a try catch here for if it's not actually an int...
            continue;
        }
        // run_operation(s[0], expression);
    }
    return 0;
}

template <class T>
Stack<T>::Stack() {
}

template <class T>
Stack<T>::~Stack() {}

template <class T>
Stack<T>::Stack(T item) {} // Add a single item.

template <class T>
Stack<T>::Stack(T items[]) {} // Add multiple items via an array.

template <class T>
void Stack<T>::push() {}

template <class T>
T Stack<T>::pop() {}

template <class T>
T Stack<T>::top() {}

template <class T>
bool Stack<T>::is_empty() {}

template <class T>
int Stack<T>::length() {}

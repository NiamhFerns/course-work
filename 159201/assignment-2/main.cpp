// Niamh Kirsty Ferns - Assignment 2 - Reverse Polish Notation Calculator
//
// NOTE:
// I went way over the top with unnecesary stuff that wasn't needed for this assignment in particular. This was
// to practice using templates as well as to practice trying to make my programs/implementations as extensible
// as possible. If a stack is being implemented, it should be implemented for all types, is my thought pattern.
//
// Any feedback on my implementation here that isn't strickly related to the assignment is welcome and desired.

#include <cstddef>
#include <cstdlib>
#include <iostream>
#include <fstream>
#include <iterator>
#include <string>

template<class T>
class Stack {
    private:
        struct Stack_Node {
            T data;
            Stack_Node *next;
        };
        Stack_Node *current, *l_pointer;
        int len;

    public:
        // Empty Constructor
        Stack();

        // NOTE: These functions aren't required for the assignment, but they're here as the idea behind this is
        //       class is to provide functionality you'd want from a stack class, not just the functionality
        //       needed in this assingment.
        Stack(T item); // Add a single item.
        Stack(T items[], int n); // Add multiple items via an array.

        ~Stack();

        void push(T data);

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

    Stack<int> expression;
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
            expression.push(stoi(s));
            continue;
        }
        run_operation(s[0], expression);
    }
    return 0;
}

template <class T>
Stack<T>::Stack() {
    l_pointer = nullptr;
    len = 0;
}

template <class T>
Stack<T>::Stack(T item) {
    l_pointer = new Stack_Node(item, nullptr);
    len = 1;
}

template <class T>
Stack<T>::Stack(T items[], int n) {
    l_pointer = new Stack_Node(items[0], nullptr);
    for (int i = 1; i < n; ++i) {
        l_pointer = new Stack_Node(items[i], l_pointer);
    }
    len = n;
}

template <class T>
Stack<T>::~Stack() {
    // if (l_pointer == nullptr) return;

    // Stack_Node * to_delete;
    // while (l_pointer) {
    //     to_delete = l_pointer;
    //     if(l_pointer->next) l_pointer = l_pointer->next;
    //     delete to_delete;
    // }
}

template <class T>
void Stack<T>::push(T data) {
    Stack_Node *item = new Stack_Node({data, l_pointer});
    l_pointer = item;
    ++len;
}

template <class T>
T Stack<T>::pop() {
    // This guard statement is gross... I wanted to use std::optional but not sure if this is marked in c++ 11 or 17.
    // empty return doesn't work for a templated function with an explicit return type.
    if (is_empty()) return 0;

    T data = l_pointer->data;
    Stack_Node *to_delete = l_pointer;
    l_pointer = l_pointer->next;

    --len;
    delete to_delete;
    return data;
}

template <class T>
T Stack<T>::top() {
    return l_pointer->data;
}

template <class T>
bool Stack<T>::is_empty() {
    return l_pointer == nullptr;
}

template <class T>
int Stack<T>::length() {
    return len;
}

void run_operation(char c, Stack<int> & s) { return; }

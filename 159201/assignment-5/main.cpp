#include <cctype>
#include <iostream>
#include <cstdlib>
#include <string>
#include <fstream>

template<class T>
class Stack
{
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
        int length();
};

struct Tree
{
    std::string data;
    Tree * left, * right;
};

void print_pre();           // Depth first: pre-order printing
void print_in();            // Depth first: in-order printing
void print_post();          // Depth first: post-order printing.
void print_breadth_left();  // Breadth first: left to right printing;
void print_breadth_right(); // Breadth first: right to left printing;


int main(int argc, char**  argv)
{
    if (argc != 2) { std::cout << "Please pass in 1 and only 1 file name." << std::endl; exit(0); }
    Stack<Tree> s;
    Tree expression, left, right;

    std::ifstream input;
    input.open(argv[1]);
    if(input.is_open() == false) { std::cout << "Could not read file: " << std::endl << argv[1] << std::endl; exit(0); }

    int i = 0;
    std::string e;
    while(getline(input, e))
    {
        if (std::isdigit(std::stoi(e)))
        if (e == "+" || e == "-" || e == "*" || e == "/")
        {

        }
        s.push({e, nullptr, nullptr});
    }
}

template <class T>
Stack<T>::Stack()
{
    l_pointer = nullptr;
    len = 0;
}

template <class T>
Stack<T>::Stack(T item)
{
    l_pointer = new Stack_Node(item, nullptr);
    len = 1;
}

template <class T>
Stack<T>::Stack(T items[], int n)
{
    l_pointer = new Stack_Node(items[0], nullptr);
    for (int i = 1; i < n; ++i) {
        l_pointer = new Stack_Node(items[i], l_pointer);
    }
    len = n;
}

template <class T>
Stack<T>::~Stack() {}

template <class T>
void Stack<T>::push(T data)
{
    Stack_Node *item = new Stack_Node({data, l_pointer});
    l_pointer = item;
    ++len;
}

template <class T>
T Stack<T>::pop()
{
    // This guard statement is gross... I wanted to use std::optional but not sure if this is marked in c++ 11 or 17.
    // empty return doesn't work for a templated function with an explicit return type.
    // Making this function pass by reference would eliminate this issue but wouldn't be as intuitive to use in my opinion.
    // It would also neccesitate having the function defined twice with different signatures.
    if (is_empty()) return 0;

    T data = l_pointer->data;
    Stack_Node *to_delete = l_pointer;
    l_pointer = l_pointer->next;

    --len;
    delete to_delete;
    return data;
}

template <class T>
T Stack<T>::top()
{
    return l_pointer->data;
}

template <class T>
bool Stack<T>::is_empty()
{
    return l_pointer == nullptr;
}

template <class T>
int Stack<T>::length()
{
    return len;
}

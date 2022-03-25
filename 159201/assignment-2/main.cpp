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
#include <exception>
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
        int length();
};

// For this assignment we will assume it is an int. This can be easily changed to include floats and other types as well.
// We use invalid to determine whether there are too many operands.
void run_operation(char c, Stack<int> & s, bool & invalid);

int main(int argc, char** argv) {

    // Our expression stack and a variable to monitor whether whether something goes wrong mid operation.
    Stack<int> expression;
    bool incorrect_input = 0;

    // Check for file name and open our file.
    if (argc == 1) {
        std::cout << "Please provide a file as input..." << std::endl;
        exit(0);
    }

    std::ifstream file;
    file.open(argv[1]);
    if (!file.good()) {
        std::cout << "An error occured while opening the specified file." << std::endl;
        exit(0);
    }

    // Loop through the file, run operation if relevant or push number to the stack.
    std::string s;
    while(!file.eof() == !incorrect_input) {
        getline(file, s);
        if (isdigit(s[0])) {
            std::cout << "reading number " << s << std::endl;
            // stoi() assumes integer input from user.
            expression.push(stoi(s));
            continue;
        }
        if (s[0] == '+' || s[0] == '-' || s[0] == '*' || s[0] == '/') {
            std::cout << "reading operator " << s << std::endl;
            run_operation(s[0], expression, incorrect_input);
        }
    }

    // Output our result or the relevant error message.
    if (expression.length() == 1) std::cout << "The result is " << expression.top() << std::endl;
    else std::cout << "too many " << (expression.length() > 1 ? "numbers" : "operators") << std::endl;
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
Stack<T>::~Stack() {}

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

void run_operation(char c, Stack<int> & s, bool & invalid) {
    int n1, n2;
    n1 = s.pop();
    if (s.is_empty()) {
        invalid = 1;
        return;
    }
    n2 = s.pop();
    switch (c) {
        case '+':
            s.push(n2 + n1);
            break;
        case '-':
            s.push(n2 - n1);
            break;
        case '*':
            s.push(n2 * n1);
            break;
        case '/':
            s.push(n2 / n1);
            break;
        default:
            break;
    }
}

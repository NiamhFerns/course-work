// Niamh Kirsty Ferns - Assignment 5 - 21007069

#include <iostream>
#include <cstdlib>
#include <string>
#include <fstream>

// This stack class is copypasted from my second assignment. It is all my code.
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

template <class T>
class Tree
{
    private:
        T data;
        Tree<T> * left, * right;

    public:
        // Constructors
        Tree(T data) : data(data), left(nullptr), right(nullptr) {}
        Tree(T data, Tree<T> * left, Tree<T> * right) : data(data), left(left), right(right) {}

        // Getters and Setters
        void set_data(T data);
        void set_left(Tree<T> * ptr);
        void set_right(Tree<T> * ptr);

        T get_contents();
        Tree<T> * get_left();
        Tree<T> * get_right();
};


bool is_number(const std::string & s);
void print_in(Tree<std::string> * tree);
void print_post(Tree<std::string> * tree);

int main(int argc, char**  argv)
{
    if (argc != 2) { std::cout << "Please pass in 1 and only 1 file name." << std::endl; exit(0); }
    Stack<Tree<std::string> *> s;
    Tree<std::string> * expression, * left, * right;

    std::ifstream input;
    input.open(argv[1]);
    if(input.is_open() == false) { std::cout << "Could not read file: " << std::endl << argv[1] << std::endl; exit(0); }

    int i = 0;
    std::string e;

    // while(std::getline(input, e)) - This is how I wanted to do it originally but there is a very strange bug I ran into that couldn't fix.
    // on linux it seems that it reads each line in as a string with a random character on the end (it's not a new line character and it doesn't show up when I print).
    // this character doesn't get read in when I run the code on the massey automarker. This means that if I trim the string, it fails all the auto-marker tests with no output,
    // but if I don't trim the string, I get get no output on my end.
    while(input >> e)
    {
        // e = e.substring(0, e.size() - 1);
        if (is_number(e))
        {
            s.push(new Tree<std::string>(e));
            continue;
        }
        if (e == "+" || e == "-" || e == "*" || e == "/")
        {
            right = s.pop();
            left = s.pop();
            s.push(new Tree<std::string>(e, left, right));
        }
        if(!s.is_empty()) expression = s.top();
    }

    std::cout << "In-fix:\n";
    print_in(expression);
    std::cout << std::endl;

    std::cout << "Post-fix:\n";
    print_post(expression);
    std::cout << std::endl;

    return 0;
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

template <class T>
void Tree<T>::set_data(T data) { this->data = data; }

template <class T>
void Tree<T>::set_left(Tree<T> * ptr) { left = ptr; }

template <class T>
void Tree<T>::set_right(Tree<T> * ptr) { left = ptr; }

template <class T>
T Tree<T>::get_contents() { return data; }

template <class T>
Tree<T> * Tree<T>::get_left() { return left; }

template <class T>
Tree<T> * Tree<T>::get_right() { return right; }

bool is_number(const std::string & s)
{
    if (s.empty()) return false;
    for (char c : s) { if (!std::isdigit(c)) return false; }
    return true;
}

void print_in(Tree<std::string> * tree)
{
    if(!tree) return;
    if(!is_number(tree->get_contents())) std::cout << "(";
    print_in(tree->get_left());
    std::cout << tree->get_contents();
    print_in(tree->get_right());
    if(!is_number(tree->get_contents())) std::cout << ")";
}

void print_post(Tree<std::string> * tree)
{
    if(!tree) return;
    print_post(tree->get_left());
    print_post(tree->get_right());
    std::cout << tree->get_contents() << " ";
}

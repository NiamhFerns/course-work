#include <cstddef>
#include <exception>
#include <iostream>
#include <vector>
#include <initializer_list>

template <class T>
class List {
    private:
        struct Node {
            T data;
            Node *next;
        };
        int size;
        Node * l_pointer, * current;

    public:
        List();
        List(const std::initializer_list<T>& elements);
        ~List() {}

        void push(T element);
        void push(const std::initializer_list<T>& elements);

        void insert(T element, int i);
        void insert(const std::initializer_list<T>& elements, int i);

        T& operator [] (unsigned int i);

        int length();
        bool is_empty();
        void print();
};

class Big_Number {

};

int main(int argc, char **argv) {
    List<int> foo = {1, 2, 3, 4, 5};
    // foo.insert(10, 2);
    // foo[1] = 15;
    foo.insert({9, 8, 7, 6}, 4);
    foo.print();
}

template <class T>
List<T>::List() {
    l_pointer = current = nullptr;
    size = 0;
}

template <class T>
List<T>::List(const std::initializer_list<T>& elements) {
    int i = 0; size = 0;
    l_pointer = current = nullptr;
    push(elements);
}

template <class T>
void List<T>::push(T element) {
    l_pointer = new Node({element, l_pointer});
    ++size;
}

template <class T>
void List<T>::push(const std::initializer_list<T>& elements) {
    // We could also store a temp variable and then use that to track the last element in the list but that also means extra
    // conditionals and is just... gross.
    for (auto e = std::rbegin(elements); e != std::rend(elements); ++e) { l_pointer = new Node({*e, l_pointer}); }
    size += elements.size();
}

template <class T>
void List<T>::insert(T element, int i) {
    current = l_pointer;
    // We need to preserve the link to the desired index so we need to stop at the index before our intended index and change that
    // node pointer to be a new node with the node pointer to it's current next node.
    for (int k = 0; current && k < i - 1; ++k) { current = current->next; }
    current->next = new Node({element, current->next});
    ++size;
}

template <class T>
void List<T>::insert(const std::initializer_list<T>& elements, int i) {
    current = l_pointer;
    for (int k = 0; current && k < i - 1; ++k) { current = current->next; }
    for (auto e = std::rbegin(elements); e != std::rend(elements); ++e) { current->next = new Node({*e, current->next}); }
    size += elements.size();
}

template <class T>
T& List<T>::operator [] (unsigned int i) {
    current = l_pointer;
    for (int k = 0; current && k != i; ++k) { current = current->next; }
    return current->data;
}

template <class T>
int List<T>::length() {
    return size;
}

template <class T>
bool List<T>::is_empty() {
    return l_pointer;
}

template <class T>
void List<T>::print() {
    current = l_pointer;
    while (current) {
        std::cout << current->data << "\n";
        current = current->next;
    }
}

#include <iostream>
#include <string>
#include <vector>
#include <initializer_list>
#include <fstream>
#include <sstream>
#include <cstdlib>

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
        List(T element);
        List(const std::initializer_list<T>& elements);
        ~List() {}

        void push(T element);
        void push(const std::initializer_list<T>& elements);

        void insert(T element);
        void insert(T element, int i);
        void insert(const std::initializer_list<T>& elements, int i);

        void set_position();
        void set_position(int i);
        bool next();

        T get();
        T& get(int i);
        void set(T element);
        T& operator [] (unsigned int i);

        void remove();
        void remove(int i);
        void remove(int i, int j);
        void remove_all();

        int length();
        bool is_empty();
        void print();
};

class Big_Number {
    private:
        List<int> * digits;
        bool carry = 0;

    public:
        void add(Big_Number n);
        void set(std::string n);

        int& operator [] (unsigned int i);
        void operator + (Big_Number n);

        std::string to_string();
        int length();

        // Empty big number.
        Big_Number();
        // Empty big number.
        Big_Number(std::string digits);
        // If we have a list of elements.
        Big_Number(const std::initializer_list<int>& digits);
};

// Empty big number.
Big_Number::Big_Number() {
    digits = new List<int>(0);
}
// if we have a string.
Big_Number::Big_Number(std::string n) {
    digits[0] = n[0] - '0';
    for (int i = 1; i < n.size(); ++i) {
        digits->push(n[i] - '0');
    }
}
// If we have a list of elements.
Big_Number::Big_Number(const std::initializer_list<int>& digits) {
    this->digits = new List<int>(digits);
}

void Big_Number::add(Big_Number n) {

}

void Big_Number::set(std::string n) {
    std::cout << "this is n: " << n;
    // digits->remove_all();
    // for (int i = 0; i < n.size(); ++i) {
    //     digits->push(std::atoi(&n[i]));
    // }
}

int& Big_Number::operator [] (unsigned int i) {
    return digits->get(i);
}
void Big_Number::operator + (Big_Number n) {
    add(n);
}

std::string Big_Number::to_string() {
    digits->set_position();
    std::string s = "";
    do {
        s = std::to_string(digits->get()) + s;
    } while(digits->next());
    return s;
}
int Big_Number::length() { return 0; }

int main(int argc, char **argv) {
    if (argc != 2) { std::cout << "Please pass in 1 and only 1 file name." << std::endl; exit(0); }
    Big_Number numbers[2];

    std::ifstream input;
    input.open(argv[1]);
    if(input.is_open() == false) { std::cout << "Could not read file: " << std::endl << argv[1] << std::endl; exit(0); }

    int i = 0;
    std::string n;
    while(i < 2) {
        getline(input, n);
        // std::cout << "reading a big number from file:" << n << std::endl;//Comment this out for your final version
        // if (!input.eof()) {
            numbers[i] = Big_Number(n);
            std::cout << numbers[i].to_string() << std::endl;
            ++i;
        // }
  // while(!input_file.eof()){
  //   getline(input_file,numberstring);
  //   if(!input_file.eof()){
  //     cout << "reading a big number from file:" << numberstring << endl;//Comment this out for your final version
  //     if(stringseq==0){
  //       B1.ReadFromString(numberstring);
  //       stringseq=1;
  //     }
  //     else B2.ReadFromString(numberstring);
  //   }
  // }
    }

    // std::cout << A.to_string() << " and " << B.to_string() << std::endl;
}

template <class T>
List<T>::List() {
    l_pointer = current = nullptr;
    size = 0;
}

template <class T>
List<T>::List(T element) {
    l_pointer = new Node({element, nullptr});
    current = l_pointer;
    size = 1;
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
void List<T>::insert(T element) {
    if (is_empty() || !current) return;
    current->next = new Node({element, current->next});
    ++size;
}

template <class T>
void List<T>::insert(T element, int i) {
    current = l_pointer;
    // We need to preserve the link to the desired index so we need to stop at the index before our intended index and change that
    // node pointer to be a new node with the node pointer to it's current next node.
    for (int k = 0; current && k < i - 1; ++k, current = current->next) {}
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
void List<T>::set_position() { current = l_pointer; }
template <class T>
void List<T>::set_position(int i) {
    current = l_pointer;
    if(i < size) {
        while (i > 0) {
            current = current->next;
            --i;
        }
    }
}
template <class T>
bool List<T>::next() {
    if (current->next) { current = current->next; return 1; }
    else return 0 ;
}

template <class T>
T List<T>::get() { return is_empty() ? 0: current->data; }
template <class T>
T& List<T>::get(int i) {
    current = l_pointer;
    for (int k = 0; current && k != i; ++k) { current = current->next; }
    return current->data;
}
// NOTE: THIS NEEDS TO BE REDONE! There is a major bug that doesn't account for arrays and most objects when assigning data.
template <class T>
void List<T>::set(T element) { if(!is_empty()) current->data = element; }

template <class T>
T& List<T>::operator [] (unsigned int i) {
    return get(i);
}

// I wanted to add these but ran out of time. ;-;
template <class T>
void List<T>::remove() {}
template <class T>
void List<T>::remove(int i) {}
template <class T>
void List<T>::remove(int i, int j) {}
template <class T>
void List<T>::remove_all() {
    current = l_pointer;
    Node * to_remove = current;
    while (current) {
        current = current->next;
        delete to_remove;
        --size;
        to_remove = current;
    }
    l_pointer = nullptr;
}

template <class T>
int List<T>::length() {
    return size;
}

template <class T>
bool List<T>::is_empty() {
    return !l_pointer;
}

template <class T>
void List<T>::print() {
    current = l_pointer;
    while (current) {
        std::cout << current->data << "\n";
        current = current->next;
    }
}

// Niamh Kirsty Ferns - 21007069 - Big_Number adder.

#include <iostream>
#include <string>
#include <fstream>
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
        // Build an empty list.
        List();
        ~List() {}

        // This adds a new element to the head of the list.
        void push(const T & element);
        // This inserts a new element into the list after the current node.
        void insert(const T & element);
        // Resets the current node to point to the beginning of the list.
        bool begin();
        // If possible, move the current node pointer forward one node in the list.
        bool next();

        // If possible, move the current node pointer forward one node in the list.
        T get();

        // Removes the items immedietly after the current pointer.
        bool remove();
        // Removes the item at the head of the list. Needed because this is a Single-LL.
        bool remove_front();
        // Removes all items in the list.
        bool remove_all();

        // Get the length of the list.
        int length();
        // Return whether the list is empty or not.
        bool is_empty();
        // Print the list with each value on its own line.
        void print();
};

template <class T>
List<T>::List() {
    l_pointer = current = nullptr;
    size = 0;
}

template <class T>
void List<T>::push(const T & element) {
    l_pointer = new Node({element, l_pointer});
    size++;
}

template <class T>
void List<T>::insert(const T & element) {
    if (is_empty()) {
        push(element);
        begin();
        return;
    }
    current->next = new Node({element, current->next});
    size++;
}

template <class T>
bool List<T>::begin() {
    current = l_pointer;
    return is_empty() ? false : true;
}

template <class T>
bool List<T>::next() {
    if (is_empty()) return false;
    if (!current->next) return false;
    current = current->next;
    return true;
}

template <class T>
T List<T>::get() {
    if (is_empty()) return 0;
    return current->data;
}

template <class T>
bool List<T>::remove() {
    if (is_empty() && !current->next) return false;
    Node * to_remove = current->next;
    // We need the pointers here [ C ]->[ N ]->[ N -> [ N ] ].
    //                    delete this one ^
    current->next = current->next->next;
    --size;
    delete to_remove;
    return true;
}

template <class T>
bool List<T>::remove_front() {
    if (is_empty()) return false;
    Node * to_remove = l_pointer;
    if (current == l_pointer) current = l_pointer->next;
    l_pointer = l_pointer->next;
    --size;
    delete to_remove;
    return true;
}

template <class T>
bool List<T>::remove_all() {
    if (is_empty()) return false;

    // Remove all the elements normally then set the l_pointer and current to nullptr.
    if (size > 1) {
        begin();
        Node * to_remove = current;
        while (next()) {
            delete to_remove;
            to_remove = current;
        }

    }

    size = 0;
    l_pointer = current = nullptr;
    return true;
}

template <class T>
int List<T>::length() {
    return size;
}

template <class T>
bool List<T>::is_empty() {
    return size == 0;
}

template <class T>
void List<T>::print() {
    if (is_empty()) {
        std::cout << "Empty list." << std::endl;
        return;
    }

    do {
        std::cout << get() << std::endl;
    } while(next());
}

class Big_Number {
    private:
        List<char> * digits;
        bool carry = 0;

    public:
        // Sets Obj A to the sum of Obj B + Obj C.
        void add(Big_Number n1, Big_Number n2);
        // Sets the big number via a string input.
        void set(std::string n);
        // Returns the least significant digit of the number.
        char get_front();
        // Delete the least significant digits (same as n /= 10).
        void remove_front();


        // Returns a string of the current number.
        std::string to_string();
        // Returns the length of the number based on how many digits there are.
        int length();
        // Returns whether the whole number is == 0;
        bool is_zero();

        // Empty big number.
        Big_Number();
        // Empty big number.
        Big_Number(std::string s);
};

Big_Number::Big_Number() {
    digits = new List<char>();
    digits->push('0' - '0');
}

Big_Number::Big_Number(std::string s) {
    digits = new List<char>();
    set(s);
}

void Big_Number::add(Big_Number n1, Big_Number n2) {
    digits->remove_all();

    // If one of the numbers is zero there's no point in running the entire algorithm.
    if(n1.is_zero()) { set(n2.to_string()); return; }
    if(n2.is_zero()) { set(n1.to_string()); return; }

    // Make a backup of our numbers.
    std::string n1_bak = n1.to_string(), n2_bak = n2.to_string();

    // Iterate through our numbers by the length of the longest number.
    // Remove the least significant digit from each number and add them to make a new digit in our output.
    // If there is a carry, add that too.
    // We then reset the original numbers to their starting values. (Not a safe method of doing this. See note below.)
    int added;
    for (int i = n1.length() > n2.length() ? n1.length() : n2.length(); i > 0; --i) {
        added = n1.get_front() + n2.get_front() + carry;
        carry = 0; // We reset it for the next iteration.

        if (added > 9) {
            added = added % 10;
            carry = 1;
        }

        digits->insert(added);
        digits->next();

        n1.remove_front();
        n2.remove_front();
    }
    if(carry == 1) digits->insert(1);

    // NOTE: The alternative to just storing backups would be to take in copies of each number. I couldn't figure out how to do this though.
    // How would I do this?
    n1.set(n1_bak);
    n2.set(n2_bak);
}

void Big_Number::set(std::string n) {
    if (n.size() == 0) return;
    if (digits->length() > 0) digits->remove_all();
    for (char c : n) {
        if(isdigit(c)) digits->push(c - '0');
    }
}

char Big_Number::get_front() {
    digits->begin();
    return digits->is_empty() ? 0 : digits->get();
}

void Big_Number::remove_front() {
    digits->remove_front();
}

std::string Big_Number::to_string() {
    std::string s = "";
    digits->begin();
    do {
        s = std::to_string(digits->get()) + s;
    } while(digits->next());
    return s;
}

int Big_Number::length() { return digits->length(); }

bool Big_Number::is_zero() {
    // Our number is zero if and only if all digits in it are zero;
    digits->begin();
    do {
        if(digits->get() > 0) return false;
    } while(digits->next());
    return true;
}

int main(int argc, char **argv) {
    if (argc != 2) { std::cout << "Please pass in 1 and only 1 file name." << std::endl; exit(0); }
    Big_Number numbers[3];

    std::ifstream input;
    input.open(argv[1]);
    if(input.is_open() == false) { std::cout << "Could not read file: " << std::endl << argv[1] << std::endl; exit(0); }

    int i = 0;
    std::string n;
    while(i < 2) {
        getline(input, n);
        numbers[i] = Big_Number(n);
        ++i;
    }

    numbers[2] = Big_Number();
    numbers[2].add(numbers[0], numbers[1]);

    std::cout << numbers[0].to_string() << "\n+\n" << numbers[1].to_string() << "\n=\n";
    std::cout << numbers[2].to_string() << std::endl;
}

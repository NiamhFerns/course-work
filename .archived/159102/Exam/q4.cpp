#include <iostream>
#include <cstdio>  // to use getchar
#include <string>
using namespace std;

class Node {
private:
  string name;
  int phone;
  Node *next;
public:
  void loaddata(string n, int pn, Node* p);
  string getname() { return name; }
  int getnumber() { return phone; }
  Node *getnext() { return next; }
  void setnext(Node* p) { next = p; }
};

void enterdata();
void searchlist(string str);

Node* listpointer;

int main() {
  string search;
  listpointer = NULL;
  enterdata();
  cout << "\nEnter the name to search for ";
  getline(cin, search);
  searchlist(search);
}

void enterdata() {
    // This could be significantly cleaned up but no time to refactor in an exam setting.
    string name = "", num = "";

    if (listpointer == nullptr) {
        std::cout << "To create a new list please enter the name for the first person: ";
        getline(cin, name);

        if (name == "??") return;

        std::cout << "Please enter a number for " << name << ": ";
        getline(cin, num);
        listpointer = new Node;
        listpointer->loaddata(name, stoi(num), nullptr);
    }

    std::cout << "\nWould you like to add items to the end of the list (y = end, N = front): ";
    getline(cin, name);

    // If user is adding stuff to the front of the list.
    if (tolower(name[0]) != 'y') {
        std::cout << "\nPlease enter a name (?? to stop): ";
        getline(cin, name);
        while (name != "??") {
            std::cout << "Please enter number for " << name << ": ";
            getline(cin, num);

            // Create a new Node*
            Node* temp = new Node;
            // Add the data.
            temp->loaddata(name, stoi(num), listpointer);
            listpointer = temp;

            // Next Node.
            std::cout << "\nPlease enter a name (?? to stop): ";
            getline(cin, name);
        }
        return;
    }

    // If user is adding stuff at the end.
    std::cout << "\nPlease enter a name (?? to stop): ";
    getline(cin, name);
    while (name != "??") {
        std::cout << "Please enter number for " << name << ": ";
        getline(cin, num);

        // Create a pointer to track current node.
        Node* current = listpointer;
        // Iterate over the nodes till we find the last one.
        while (current->getnext() != nullptr) {
            current = current->getnext();
        }

        // Use the last node to create a new node and fill it out with data.
        current->setnext(new Node);
        current->getnext()->loaddata(name, stoi(num), nullptr);

        // Next Node.
        std::cout << "\nPlease enter a name (?? to stop): ";
        getline(cin, name);
    }
}

void searchlist(string str) {
    Node* current;

    current = listpointer;
    while (current != NULL) {
        if (current->getname() == str) {
            cout << "Phone number for " << current->getname();
            cout << " is " << current->getnumber() << endl;
            return;
        }
        current = current->getnext();
    }
    cout << str << " is not in the list.\n";
}

//---------- methods for the Node ---------
void Node::loaddata(string n, int pn, Node* p) {
    name = n;
    phone = pn;
    next = p;
}

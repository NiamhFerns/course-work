//159201 assignment 1 skeleton
//You need to add your own AddNode and PrintLL functions, as well as an AddMatrices function
//
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>

using namespace std;

struct Node {  //declaration
    int row;
    int column;
    int value;
    Node *next;
};
Node *A = NULL, *B = NULL, *Added = NULL;  //declaration

void add_node(Node *&l_pointer, int i, int j, int x) {
    Node *next = new Node({i, j, x, l_pointer});
    l_pointer = next;
}

int search(Node *l_pointer, int row, int column) {
    while( l_pointer != NULL ) {
        if( l_pointer->row == row && l_pointer->column == column) return l_pointer->value;
        l_pointer = l_pointer->next;
    }
    return 0;
}

void add_matracies(Node *a, Node *b, Node *&added, int col, int row) {
    int value;
    for (int i = row - 1; i >= 0; --i) {
        for (int j = col - 1; j >= 0; --j) {
            value = search(a, i, j) + search (b, i, j);
            if (value == 0) continue;
            add_node(added, i, j, value);
        }
    }
}

void reverse_matrix(Node *&l_pointer) {
    if (l_pointer == NULL) return;

    Node *current_head, *tail, *next_head;
    tail = l_pointer;
    current_head = l_pointer->next;

    while(current_head != NULL) {
        // Mark the cut off point and then change head to point to the node behind it.
        next_head = current_head->next;
        current_head->next = l_pointer;

        // Move the listpointer and current head forward node.
        l_pointer = current_head;
        current_head = next_head;
    }
    // Last node points to NULL;
    tail->next=NULL;
}

void print_ll(Node* l_pointer, int rows, int columns, string name) {
    // Print our matrix label.
    cout << name << ": ";

    // Print our Linked-List.
    Node *current = l_pointer;
    while (current != NULL) {
        cout << current->value << " ";
        current = current->next;
    }
    cout << endl;

    // Print our matrix from the Linked-List.
    current = l_pointer;
    for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < columns; ++j) {
            if ( current != NULL && current->row == i && current->column == j ) {
                cout << current->value << " ";
                current = current->next;
                continue;
            }
            cout << "0 ";
        }
        cout << endl;
    }
}

void read_matrix(Node *&l_pointer, char *file_name, int & row, int & col){
    // Reads a matrix from a file.
    int value = 0;
    row = 0; col = 0;

    ifstream input;
    input.open(file_name);

    if(!input.good()){
        cout << "Cannot open file " << file_name << endl;
        exit(0);
    }

    int c;
    string line;

    // Reads the first line to get dimensions.
    if(input.good()){
        getline(input,line);
        stringstream sline(line);
        sline >> row >> col;
    }

    // Read matrix in as a Linked-List.
    for(int i = 0; i < row; ++i){
        if(input.good()) {
            getline(input,line);
            stringstream sline(line);
            for(int j = 0; j < col; ++j){
                sline >> value;
                if(value == 0) continue;
                add_node(l_pointer, i, j, value);
            }
        }
    }
    input.close();
}

int main( int argc, char** argv ) {
    int rows, columns;

    if(argc!=3) {printf("needs two matrices \n"); exit(0);}

    // Store our matracies as Linked-Lists.
    read_matrix(A, argv[1], rows, columns);
    reverse_matrix(A);
    read_matrix(B, argv[2], rows, columns);
    reverse_matrix(B);

    // Add our Matracies.
    add_matracies(A, B, Added, rows, columns);

    // Print our Linked-Lists.
    print_ll(A, rows, columns, "Matrix 1");
    print_ll(B, rows, columns, "Matrix 2");
    print_ll(Added, rows, columns, "Matrix Result");
}

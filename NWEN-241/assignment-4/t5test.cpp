/**
 * t5test.cpp
 * Sample test program for Task 5
 * This test assumes that you have completed implementation of VectorDbTable in Tasks 1, 2 and 3.
 *
 * To compile with your implementation:
 * g++ t5test.cpp abstractdb.cpp vectordb.cpp -o t5test
 *
 * If successful, executable file t5test should have been created.
 * 
 * Hint: An error message saying "undefined reference to nwen::VectorDbTable::xxx()" 
 * is a linking error which means that the compiler is expecting an implementation
 * of the member function xxx() in the VectorDbTable class.
 * 
 * Hint: If you encounter a linking error, provide an empty implementation of that
 * member function first so that you can proceed with your testing. An empty 
 * implementation is essentially a function that is empty or returns a fixed value.
 */

#include <iostream>
#include "vectordb.hpp"


using namespace std;
using namespace nwen;


struct movie t5movies[] = {
    { 13, "The Shawshank Redemption", 1994, "Frank Darabont" },
    { 25, "The Godfather", 1972, "Francis Ford Coppola" },
    { 31, "The Dark Knight", 2008, "Christopher Nolan" },
};


int main(void)
{
    VectorDbTable *db;
    bool r;
    
    cout << "Instantiating VectorDbTable..." << endl;
    db = new VectorDbTable();
       
    cout << "Invoking loadCSV(\"dummy\")..." << endl;
    r = db->loadCSV("dummy");
    cout << "Expected return value: 0" << endl;
    cout << "Actual return value  : " << r << endl;   
    
    cout << "Invoking loadCSV(\"t5.csv\")..." << endl;
    r = db->loadCSV("t5.csv");
    cout << "Expected return value: 1" << endl;
    cout << "Actual return value  : " << r << endl;       
    cout << "Expected : rows = 3" << endl;
    cout << "Actual   : rows = " << db->rows() << endl;  
    
    for(int i=0; i<3; i++) {
        cout << "Invoking get(" << i << ")..." << endl;
        const movie *m = db->get(i);
        const movie *e = t5movies + i;
        cout << "Expected return value: (" << e->id << ", " << e->title << ", " << e->year << ", " << e->director << ")" << endl;
        cout << "Actual return value  : (" << m->id << ", " << m->title << ", " << m->year << ", " << m->director << ")" << endl;
    }
    
    cout << "Freeing VectorDbTable..." << endl;
    delete db;
    
    return 0;
}

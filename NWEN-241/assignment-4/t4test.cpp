/**
 * t4test.cpp
 * Sample test program for Task 4
 * This test assumes that you have completed implementation of VectorDbTable in Tasks 1, 2 and 3.
 *
 * To compile with your implementation:
 * g++ t4test.cpp abstractdb.cpp vectordb.cpp -o t4test
 *
 * If successful, executable file t4test should have been created.
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
#include <fstream>
#include "vectordb.hpp"


using namespace std;
using namespace nwen;

struct movie movies[] = {
    { 13, "The Shawshank Redemption", 1994, "Frank Darabont" },
    { 25, "The Godfather", 1972, "Francis Ford Coppola" },
    { 31, "The Dark Knight", 2008, "Christopher Nolan" },
    { 40, "The Godfather: Part II", 1974, "Francis Ford Coppola" },
    { 55, "The Lord of the Rings: The Return of the King", 2003, "Peter Jackson" },
    { 72, "Pulp Fiction", 1994, "Quentin Tarantino" }
};


int main(void)
{
    VectorDbTable *db;
    bool r;
    
    cout << "Instantiating VectorDbTable..." << endl;
    db = new VectorDbTable();
    
    // Testing for add() and rows()
    cout << "Adding 6 entries..." << endl;
    for(unsigned long i=0; i<sizeof(movies)/sizeof(movie); i++) {
        cout << "Invoking add()..." << endl;
        r = db->add(movies[i]);
        cout << "Expected return value: 1" << endl;
        cout << "Actual return value  : " << r << endl;        
    }
    cout << "Invoking add()..." << endl;
    r = db->add(movies[0]);
    cout << "Expected return value: 0" << endl;
    cout << "Actual return value  : " << r << endl;   
        
    cout << "Expected : rows = 6" << endl;
    cout << "Actual   : rows = " << db->rows() << endl;
    
       
    cout << "Invoking saveCSV(\"t4.csv\")..." << endl;
    r = db->saveCSV("t4.csv");
    cout << "Expected return value: 1" << endl;
    cout << "Actual return value  : " << r << endl;   
    
    cout << "Expected contents of t4.csv:" << endl;
    for(unsigned long i=0; i<sizeof(movies)/sizeof(movie); i++) {
        const movie *e = movies + i;
        cout << e->id << ",\"" << e->title << "\"," << e->year << ",\"" << e->director << "\"" << endl;    
    }  
    cout << endl;
    
    cout << "Actual contents of t4.csv:" << endl;
    ifstream f;
    f.open("t4.csv");
    if (f.is_open()) {
        cout << f.rdbuf();
        f.close();
    } else
        cout << "Error: Failed to open t4.csv" << endl;
    cout << endl;
    
    cout << "Freeing VectorDbTable..." << endl;
    delete db;
    
    return 0;
}

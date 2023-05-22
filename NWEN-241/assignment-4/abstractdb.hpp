/**
 * abstractdb.hpp
 * C++ header file that should contain declaration for
 * - struct movie (given)
 * - AbstractDbTable abstract class
 *
 * You need to modify this file to declare AbstractDbTable abstract class
 * as specified in the hand-out (Task 1)
 */

#ifndef __ABSTRACT_DB_HPP__
#define __ABSTRACT_DB_HPP__

#include <string>
#include <vector>

using namespace std;

namespace nwen {
struct movie {
    unsigned long id;
    char title[50];
    unsigned short year;
    char director[50];
};

class AbstractDbTable {
public:
    /*
     * Returns the count of rows in the database.
     */
    virtual int rows() const = 0;
    /*
     * Return a pointer to a specific row number from the database.
     */
    virtual movie* get(int row_num) const = 0;
    /*
     * Add a new row to the database.
     */
    virtual bool add(movie new_movie) const = 0;
    /*
     * Update a row in the database with new information.
     */
    virtual bool update(unsigned long id, movie to_update) = 0;
    /*
     * Remove a row from the database.
     */
    virtual bool remove(unsigned long id) = 0;
    /*
     * From a CSV file, load our database into memeory.
     */
    bool loadCSV(std::string path);
    /*
     * To a CSV file, save our database from memory.
     */
    bool saveCSV(std::string path);
};
}

#endif /* __ABSTRACT_DB_HPP__ */

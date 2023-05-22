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

namespace nwen {
struct movie {
    unsigned long id;
    char title[50];
    unsigned short year;
    char director[50];
};

class AbstractDbTable {
public:
    virtual int rows() const;
    virtual movie* get(int row_num) const;
    virtual void add(movie new_movie) const;
    virtual bool update(unsigned long id, movie to_update) const;
    virtual bool remove(unsigned long id) const;
    bool loadCSV(std::string path);
    bool saveCSV(std::string path);
};
}

#endif /* __ABSTRACT_DB_HPP__ */

/**
 * vectordb.hpp
 * C++ header file that should contain declaration for
 * - VectorDbTable class
 * 
 * You need to modify this file to declare VectorDbTable class 
 * as specified in the hand-out (Task 2)
 */ 

#ifndef __VECTOR_DB_HPP__
#define __VECTOR_DB_HPP__

#include <string>
#include <vector>
#include "abstractdb.hpp"

namespace nwen {
class VectorDbTable : public AbstractDbTable {
private:
    std::vector<movie> *entries;

public:
    VectorDbTable();

    virtual int rows() const override;
    virtual movie* get(int row_num) const override;
    virtual bool add(movie new_movie) const override;
    virtual bool update(unsigned long id, movie to_update) override;
    virtual bool remove(unsigned long id) override;
};
}

#endif /* __ABSTRACT_DB_HPP__ */

/**
 * vectordb.cpp
 * C++ source file that should contain implementation for member functions
 * - rows()
 * - get()
 * - add()
 * - update()
 * - remove()
 * 
 * You need to modify this file to implement the above-mentioned member functions
 * as specified in the hand-out (Task 3)
 */ 

#include "vectordb.hpp"

#include "abstractdb.hpp"
#include <vector>

nwen::VectorDbTable::VectorDbTable()
{
    entries = new std::vector<movie>;
}

int nwen::VectorDbTable::rows() const { return entries->size(); }

nwen::movie* nwen::VectorDbTable::get(int row_num) const
{
    if (row_num >= entries->size())
        return nullptr;
    return &(*entries)[row_num];
}

bool nwen::VectorDbTable::add(nwen::movie new_movie) const
{
    for (auto entry : *entries)
        if (new_movie.id == entry.id) return false;
    entries->push_back(new_movie);
    return true;
}

bool nwen::VectorDbTable::update(unsigned long id, movie to_update)
{
    for (int i = 0; i < entries->size(); ++i) {
        if ((*entries)[i].id == id) {
            (*entries)[i] = to_update;
            return true;
        }
    }
    return false;
}

bool nwen::VectorDbTable::remove(unsigned long id)
{
    for (int i = 0; i < entries->size(); ++i) {
        if ((*entries)[i].id == id) {
            entries->erase(entries->begin() + i);
            return true;
        }
    }
    return false;
};

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

nwen::VectorDbTable::~VectorDbTable()
{
    delete entries;
}

int nwen::VectorDbTable::rows() const
{
    return entries->size();
}

nwen::movie* nwen::VectorDbTable::get(int row_num) const
{
    // Invalid row guard.
    if (row_num >= entries->size() || row_num < 0)
        return nullptr;

    return &(*entries)[row_num];
}

bool nwen::VectorDbTable::add(const nwen::movie & new_movie) const
{
    // Avoid duplicate entries.
    for (auto entry : *entries)
        if (new_movie.id == entry.id)
            return false;

    // Add entry.
    entries->push_back(new_movie);
    return true;
}

bool nwen::VectorDbTable::update(unsigned long id, const movie & to_update)
{
    // Update entry if it exists.
    for (int i = 0; i < entries->size(); ++i) {
        if ((*entries)[i].id == id) {
            (*entries)[i] = to_update;
            return true;
        }
    }

    // Return false if it doesn't.
    return false;
}

bool nwen::VectorDbTable::remove(unsigned long id)
{
    // Remove entry if it exists.
    for (int i = 0; i < entries->size(); ++i) {
        if ((*entries)[i].id == id) {
            entries->erase(entries->begin() + i);
            return true;
        }
    }

    // Return false if it doesn't.
    return false;
};

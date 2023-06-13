/**
 * abstractdb.cpp
 * C++ source file that should contain implementation for member functions
 * - loadCSV()
 * - saveCSV()
 *
 * You need to modify this file to implement the above-mentioned member functions
 * as specified in the hand-out (Tasks 4 and 5)
 */

#include "abstractdb.hpp"
#include <fstream>
#include <iostream>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <string>

// Used for creating an entry.
#define ID 0
#define TITLE 1
#define YEAR 2
#define DIRECTOR 3

bool nwen::AbstractDbTable::loadCSV(const std::string & path)
{
    // Open file.
    std::fstream input;
    input.open(path, std::ios::in);
    if (!input || !input.is_open())
        return false;

    // Needed for splitting.
    std::string line;
    std::string token;
    char delimiter = ',';

    // Grab each line as a C.S.L then split into 4 individual table entries.
    while (std::getline(input, line)) {
        std::stringstream stream;
        stream << line;
        std::string tokens[4];

        // Get our entry details.
        for (int i = 0; std::getline(stream, token, delimiter); i++) {
            tokens[i] = token;
        }

        // Create our entry.
        movie m;
        m.id = std::strtoul(tokens[ID].c_str(), NULL, 0);
        strncpy(m.title, tokens[TITLE].c_str(), 50);
        m.year = (unsigned short)std::strtoul(tokens[YEAR].c_str(), NULL, 0);
        strncpy(m.director, tokens[DIRECTOR].c_str(), 50);

        this->add(m);
    }

    input.close();
    return true;
}

bool nwen::AbstractDbTable::saveCSV(const std::string & path)
{
    // Open a file steam.
    std::fstream output;
    output.open(path, std::ios::out);
    if (!output || !output.is_open())
        return false;

    // Read in movies and out stream them in format id,"title",year,"director".
    int i = 0;
    movie* m;

    while ((m = this->get(i++))) {
        output << m->id << "," << m->title << "," << m->year << "," << m->director << "\n";
    }

    output.close();
    return true;
}

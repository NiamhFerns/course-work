package com.company;

public class Modes {
    Lecturer[] lecturers = new Lecturer[3];

    private String[] locations = {"Auckland", "PN      ", "Distance"};

    // Return a list of the lecturer for each mode.
    public Lecturer[] getLecturers() {
        return lecturers;
    }

    // Return the name and location of a specific lecturer.
    public String getLecturer(int n) {
         return locations[n] + "    " + lecturers[n].toString();
    }

    public Modes(Lecturer[] lecturers) {
        for (int i = 0; i < 3; ++i) {
            this.lecturers[i] = lecturers[i];
        }
    }
}

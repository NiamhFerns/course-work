package com.company;

public class Modes {
    private Lecturer[] lecturers = new Lecturer[3];

    public Modes(Lecturer[] lecturers) {
        for (int i = 0; i < 3; ++i) {
            this.lecturers[i] = lecturers[i];
        }
    }

    // Return a list of the lecturer for each mode.
    public Lecturer[] getLecturers() {
        return lecturers;
    }

    // Return the name and location of a specific lecturer.
    public String getLecturer(int n) {
         return Main.getLocations()[n] + "    " + lecturers[n].toString();
    }
}

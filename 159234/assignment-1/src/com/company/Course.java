package com.company;

import java.util.ArrayList;
import java.util.Arrays;


public class Course extends Main {
    private int code;
    private String name;
    private ArrayList<String> relevantMajors;
    private Double[] assessments = new Double[3];
    private Modes modes;

    // Print all the offerings for this course alongside the relevant lecturer.
    public void printOfferings() {
        for (int i = 0; i < 3; ++i) {
            System.out.println(code + " " + modes.getLecturer(i));
        }
    }

    // Return the specific lecturers for the modes of this course.
    public Lecturer[] getLecturers() {
        return modes.getLecturers();
    }

    // Return the specific lecturer for a specific mode for this course.
    public Lecturer getLecturer(int n) {
        return modes.getLecturers()[n];
    }

    public Double getWeighting(int n) { return  n >= 0 && n < 3 ? assessments[n] : 0; }
    public String getMajors() { return relevantMajors.toString(); }
    public String getName() { return name; }
    public String getCode() { return String.valueOf(code); }

    @Override
    public String toString() { return code + " - " + name + " " + relevantMajors.toString(); }

    public Course(String data, Lecturer [] staff) {
        String[] s = data.split(";");
        code = Integer.parseInt(s[0]);
        name = s[1];
        relevantMajors = new ArrayList<String>(Arrays.asList(s[2].split(",")));
        for (int i = 0; i < assessments.length; ++i) {
            assessments[i] = Double.parseDouble(s[i + 3]);
        }

        modes = new Modes(Lecturer.assignLecturers(staff));
    }
}

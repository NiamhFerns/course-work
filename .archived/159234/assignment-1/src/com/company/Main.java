package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Major> majors;
    private static ArrayList<Course> courses;
    private static ArrayList<Lecturer> lecturers;
    private static String[] locations = {"Auckland", "PN", "Distance"};
    private static String schoolName = "School of Mathematical and Computational Sciences";

    public static String[] getLocations() { return locations; }
    private static void printFullName() { System.out.println("School Full Name: " + schoolName); }

    private static void displayInfo() {
        System.out.println("**************************************");
        System.out.println("Assignment 1, 159.234 Semester 1 2022");
        System.out.println("Submitted by: Niamh, Ferns 21007069");
        System.out.println("My Major at Massey: Computer Science");
        System.out.println("Assignment 1, 159.234 Semester 1 2022");
        System.out.println("**************************************");
        System.out.println();
    }

    // Read in the values of our semicolon separated strings into the relevant ArrayLists.
    // This isn't the best way to do this and relies on school_data.txt being perfectly formatted.
    // It will do fine for this for now.
    private static void readInData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("school_data.txt"));

            String expression = "";
            majors = new ArrayList<Major>();
            lecturers = new ArrayList<Lecturer>();
            courses = new ArrayList<Course>();

            while((expression = br.readLine()) != null) {
                switch (expression.charAt(0)) {
                    case 'M':
                        majors.add(new Major(expression.substring(2)));
                        break;
                    case 'L':
                        lecturers.add(new Lecturer(expression.substring(2)));
                        break;
                    case 'C':
                        courses.add(new Course(expression.substring(2), lecturers, majors));
                        break;
                }
            }
        }
        catch(IOException e) {
            System.out.println("Please make sure the \"school_data.txt\" file is present in your program directory.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        readInData();
        displayInfo();

        System.out.println("--------------------- Task 1 ---------------------");
        Main.printFullName();
        System.out.println();

        System.out.println("--------------------- Task 2 ---------------------");
        System.out.println("All paper details:");
        Course.printAllDetails(courses);
        System.out.println();

        System.out.println("--------------------- Task 3 ---------------------");
        try {
            Course[] temp = Major.getByID("CS", majors).getCourses(courses);

            System.out.println("Papers that belong to the Major CS:");
            Course.simplePrint(temp);
            System.out.println("Total matching papers in the specified Major - CS: " + temp.length);
        } catch (NullPointerException e) {
            System.out.println("No courses found. Error: " + e);
        }
        System.out.println();

        System.out.println("--------------------- Task 4 ---------------------");
        System.out.println("Papers that have an exam:");
        int i = 0;
        for (Course c : courses) {
            if (c.getExamWeighting() > 0.0) { c.simplePrint(); ++i; }
        }
        System.out.println("Total number of papers with an exam: " + i);
        System.out.println();

        System.out.println("--------------------- Task 5 ---------------------");
        System.out.println("All papers whose assignments weigh more than 50% in total:");
        i = 0;
        for (Course c : courses) {
            if (c.getAssignmentWeighting() > 50.0) { c.simplePrint(); ++i; }
        }
        System.out.println("Total number of papers that have assignment weighted more than 50%: " + i);
        System.out.println();

        System.out.println("--------------------- Task 6 ---------------------");
        System.out.println("Paper offering details:");
        for (Course c : courses) {
            c.printOfferings();
        }
        System.out.println();

        System.out.println("--------------------- Task 7 ---------------------");
        System.out.println("Paper offering details:");
        Lecturer x = Lecturer.getByID(21007069, lecturers);
        try {
            x.printOfferings(courses);
            System.out.println("I am teaching " + x.getTeaching() + " paper(s)");
        } catch (NullPointerException e) {
            System.out.println("This lecturer is not teaching any courses currently");
        }
        System.out.println();

        System.out.println("--------------------- Task 8 ---------------------");
        System.out.println("Paper offering details:");
        x = Course.getByCode(159234, courses).getLecturer(0);
        System.out.println("Lecturer's Name: " + x.toString());
        System.out.println("This lecturer is teaching " + x.getTeaching() + " paper(s).");
        System.out.println();
    }
}

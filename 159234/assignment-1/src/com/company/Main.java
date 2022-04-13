package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    protected static ArrayList<Major> majors;
    protected static ArrayList<Course> courses;
    protected static ArrayList<Lecturer> lecturers;

    private static void displayInfo() {
        System.out.println("**************************************");
        System.out.println("Assignment 1, 159.234 Semester 1 2022");
        System.out.println("Submitted by: Niamh, Ferns 21007069");
        System.out.println("My Major at Massey: Computer Science");
        System.out.println("Assignment 1, 159.234 Semester 1 2022");
        System.out.println("**************************************");
        System.out.println();
    }

    // Read in the values of our semi-colon separated strings into the relevant ArrayLists.
    private static void readINData() {
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
                        courses.add(new Course(expression.substring(2), lecturers.toArray(new Lecturer[lecturers.size()])));
                        break;
                }
            }
        }
        catch(IOException e) {
            System.out.println("Please make sure the \"school_data.txt\" file is present in your program directory.");
        }
    }

    public static void main(String[] args) {
        readINData();
        displayInfo();

        System.out.println("--------------------- Task 1 ---------------------");
        System.out.println("School Full Name: School of Mathematical and Computational Sciences");
        System.out.println();

        System.out.println("--------------------- Task 2 ---------------------");
        System.out.println("All paper details:");
        for (Course c : courses) {
            System.out.println(c.toString());
        }
        System.out.println();

        System.out.println("--------------------- Task 3 ---------------------");
        System.out.println("Papers that belong to the Major CS:");
        String[] s = majors.get(0).getCourses();
        for (String z : s) { System.out.println("Paper -  " + z); }
        System.out.println("Total matching papers in the specified Major - CS: " + s.length);
        System.out.println();

        System.out.println("--------------------- Task 4 ---------------------");
        System.out.println("Papers that have an exam:");
        int i = 0;
        for (Course c : courses) {
            if (c.getWeighting(2) > 0.0) { System.out.println("Paper -  " + c.getCode() + " " + c.getName()); ++i; }
        }
        System.out.println("Total number of papers with an exam: " + i);
        System.out.println();

        System.out.println("--------------------- Task 5 ---------------------");
        System.out.println("All papers whose assignments weigh more than 50% in total:");
        i = 0;
        for (Course c : courses) {
            if (c.getWeighting(0) > 50.0) { System.out.println("Paper -  " + c.getCode() + " " + c.getName()); ++i; }
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
        Lecturer.printOfferings(21007069, courses);
        for (Lecturer l : lecturers) {
            if (l.getStaffID() == 21007069) {
                System.out.println("I am teaching " + l.getTeaching() + " paper(s)");
            }
        }
        System.out.println();

        System.out.println("--------------------- Task 8 ---------------------");
        System.out.println("Paper offering details:");
        for (Course c : courses) {
            if (c.getCode().equals("159234")) {
                Lecturer x = c.getLecturer(0);
                System.out.println("Lecturer's Name: " + x.toString());
                System.out.println("This lecturer is teaching " + x.getTeaching() + " paper(s).");
            }
        }
        System.out.println();
    }
}

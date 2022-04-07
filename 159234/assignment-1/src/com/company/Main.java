package com.company;

import java.util.ArrayList;

public class Main {
    static final String[] majorsData = {
            "Computer Science;CS",
            "Information Technology;IT",
            "Information Systems;IS",
            "Software Engineering;SE",
            "Data Science;DS"
    };

    static final String[] courseData = {
            "158100;Information Technology Principles;IT,IS;70;30;0",
            "158120;Web-based IT Fundamentals;IT,IS;60;40;0",
            "159101;Applied Programming;IT,IS,CS,DS,SE;50;50;0",
            "159201;Algorithms and Data Structures;CS,DS,SE,IS;40;20;40",
            "159234;Object-Oriented Programming;CS,SE;50;10;40",
            "158337;Database Development;IT,SE,DS;60;0;40",
            "158345;Professionalism in the Information Sciences;IT,IS,CS,DS,SE;100;0;0",
    };

    static final String[] lecturersData = {
            "1562347;Thomas;Becker;Auckland",
            "21007069;Niamh;Ferns;Auckland",
            "5664789;Steven;Hobbs;Auckland",
            "3658947;Andrew;Jackson;Auckland",
            "6332698;Jonathon;Wood;Auckland",
            "12345678;Mickey;Mouse;Auckland",
            "1105236;Amy;Sheffield;PN",
            "1235894;Victoria;Jensen;PN",
            "7225669;James;Lee;PN",
            "1328991;Colin;Delmont;PN"
    };

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
        // Majors
        majors = new ArrayList<Major>();
        for (String s : majorsData) {
            majors.add(new Major(s));
        }

        // Lecturers
        lecturers = new ArrayList<Lecturer>();
        for (String s : lecturersData) {
            lecturers.add(new Lecturer(s));
        }

        // Courses
        courses = new ArrayList<Course>();
        for (String s : courseData) {
            courses.add(new Course(s, lecturers.toArray(new Lecturer[lecturers.size()])));
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

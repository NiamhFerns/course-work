package com.company;

public class Major extends Main{
    private final String name;
    private final String code;

    // Return a string array of courses where each course is added if it contains the relevant code for this major.
    public String[] getCourses() {
        String s = "";
        for (Course c : courses) {
            if (c.getMajors().contains(code)) {
                s = s.concat(";" + c.getCode() + " " + c.getName());
            }
        }
        return s.substring(1).split(";");
    }

    // Assign data to attributes.
    public Major(String data) {
        String[] s = data.split(";");
        name = s[0];
        code = s[1];
    }
}

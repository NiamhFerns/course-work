import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Lecturer {
    private int staffID;
    private String firstName;
    private String lastName;
    private String location;
    private int currentlyTeaching = 0;
    private boolean distanceTeacher = false;

    public Lecturer(String data) {
        String[] s = data.split(";");

        staffID = Integer.parseInt(s[0]);
        firstName = s[1];
        lastName = s[2];
        location = s[3];
    }

    // Print a list of all the papers that a specific teacher teaches by ID.
    // Loop through each mode of each course to check if they're the correct teacher, and if yes, print them.
    public int getTeaching() { return currentlyTeaching; }
    public int getStaffID() { return staffID; }

    public static Lecturer getByID(int id, ArrayList<Lecturer> lecturers) {
        for (Lecturer l : lecturers) {
            if (l.getStaffID() == id) return l;
        }
        return null;
    }
    // Randomly select a lecturer for a mode from a sublist of the total lecturing staff.
    // Keep picking a random lecturer till we find one who has courses < 4 and/or isn't already teaching a distance
    // course.
    private static Lecturer findLecturerFor(String campus, Lecturer[] staff) {
        // Logic for finding for distance.
        if (campus.equals("Distance")) {
            for (Lecturer l : staff) {
                if (l.distanceTeacher) continue;
                l.distanceTeacher = true;
                l.currentlyTeaching++;
                return l;
            }
        }

        // Logic for finding for Auckland and PN
        int x = 0;
        boolean found = false;
        Random index = new Random();
        while (x < staff.length) {
            x = index.nextInt(staff.length);
            if (staff[x].currentlyTeaching < 4) {
                staff[x].currentlyTeaching++;
                break;
            }
            ++x;
        }
        return staff[x];
    }

    // Returns an array of 3 lecturers, 1 for each mode, that can then be given to a course for its modes.
    public static Lecturer[] assignLecturers(Lecturer[] staff) {
        int split = 0;

        for (int i = 0; i < staff.length && split == 0; ++i) {
            if (staff[i].location.equals("PN")) split = i;
        }

        // We split the strings based on where the Auckland and PN are. Requires the list to be sorted by location.
        return new Lecturer[] {
                findLecturerFor("Auckland", Arrays.copyOfRange(staff, 0, split)),
                findLecturerFor("PN", Arrays.copyOfRange(staff, split, staff.length)),
                findLecturerFor("Distance", staff)
        };
    }

    public void printOfferings(ArrayList<Course> courses) {
        for (Course c : courses) {
            for (int i = 0; i < 3; ++i) {
                if (c.getLecturers()[i] == this) {
                    System.out.println("Paper Offering -  " + c.getCode() + "    " + Main.getLocations()[i] +
                            "   Lecturer: " + c.getLecturers()[i].toString());
                }
            }
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}

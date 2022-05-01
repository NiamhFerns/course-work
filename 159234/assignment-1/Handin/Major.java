import java.util.ArrayList;

public class Major {
    private final String name;
    private final String code;

    // Assign data to attributes.
    public Major(String data) {
        String[] s = data.split(";");
        name = s[0];
        code = s[1];
    }

    public String getCode() { return code; }

    public static Major getByID(String code, ArrayList<Major> majors) {
        for (Major m : majors) {
            if (m.getCode().equals(code)) {
                return m;
            }
        }
        return null;
    }

    // Return a string array of courses where each course is added if it contains the relevant code for this major.
    public Course[] getCourses(ArrayList<Course> courses) {
        ArrayList<Course> found = new ArrayList<>();
        for (Course c : courses) {
            if (c.getMajors().contains(code)) {
                found.add(c);
            }
        }
        return found.toArray(new Course[0]);
    }

    @Override
    public String toString() {
        return code;
    }
}

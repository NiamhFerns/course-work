import java.util.ArrayList;

public class Course {
    private int code;
    private String name;
    private ArrayList<Major> majors;
    private Double[] assessments = new Double[3];
    private Modes modes;

    // Constructors for Course
    public Course(String data, ArrayList<Lecturer> staff, ArrayList<Major> majors) {
        String[] s = data.split(";");
        code = Integer.parseInt(s[0]);
        name = s[1];

        this.majors = new ArrayList<>();
        String[] m = s[2].split(",");
        for (String item : m) {
            this.majors.add(Major.getByID(item, majors));
        }

        for (int i = 0; i < assessments.length; ++i) {
            assessments[i] = Double.parseDouble(s[i + 3]);
        }

        modes = new Modes(Lecturer.assignLecturers(staff.toArray(new Lecturer[0])));
    }

    // Getters
    public Double getAssignmentWeighting() { return assessments[0]; }
    public Double getTestWeighting() { return assessments[1]; }
    public Double getExamWeighting() { return assessments[2]; }
    public String getMajors() { return majors.toString(); }
    public String getName() { return name; }
    public int getCode() { return code; }

    public static Course getByCode(int code, ArrayList<Course> courses) {
        for (Course c : courses) {
            if (c.getCode() == code) { return c; }
        }
        return null;
    }

    // Return the specific lecturer for a specific mode for this course.
    public Lecturer getLecturer(int n) {
        return modes.getLecturers()[n];
    }
    // Return the specific lecturers for the modes of this course.
    public Lecturer[] getLecturers() {
        return modes.getLecturers();
    }

    // Printers
    // Print code and name for this course.
    public void simplePrint() {
        System.out.println(getCode() + " - " + getName());
    }

    // Print all the offerings for this course alongside the relevant lecturer.
    public void printOfferings() {
        for (int i = 0; i < 3; ++i) {
            System.out.println(code + " " + modes.getLecturer(i));
        }
    }

    // Print all details for every course in an ArrayList
    public static void printAllDetails(ArrayList<Course> courses) {
        for (Course c : courses) {
            System.out.println(c.toString());
        }
    }

    // Print code and name for every course in an ArrayList
    public static void simplePrint(Course[] courses) {
        for (Course c : courses) {
            System.out.println(c.getCode() + " - " + c.getName());
        }
    }

    // Overrides
    @Override
    public String toString() {
        // Having a string in a loop is super inefficient but for this, the performance loss is tiny.
        String s =  code + " - " + name + " (";
        for (Major m : majors) { s += m.getCode() + ", "; }
        s = s.substring(0, s.length() - 2) + ")";
        return s;
    }
}

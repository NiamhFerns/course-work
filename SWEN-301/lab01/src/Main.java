// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        var fs = new FetchStudents();
        fs.fetchAll();
        fs.fetchById(3);
        fs.fetchByDegreeAndMajor("BSc", "CS");
    }
}
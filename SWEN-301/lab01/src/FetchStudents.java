import org.relique.jdbc.csv.CsvDriver;
import java.sql.*;
import java.sql.DriverManager;

public class FetchStudents {
    String url = "jdbc:relique:csv:db?" + "separator=," + "&" + "fileExtension=.csv";
    public FetchStudents() {}
    public void fetchAll() throws Exception {
        // OPEN SESAmE
        Connection conn = DriverManager.getConnection(url);

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM students");

        boolean append = true;
        CsvDriver.writeToCsv(resultSet, System.out, append);
    }

    public void fetchById(int id) throws Exception {
        // OPEN SESAmE
        Connection conn = DriverManager.getConnection(url);

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM students WHERE ID='" + id + "'");

        boolean append = true;
        CsvDriver.writeToCsv(resultSet, System.out, append);
    }

    public void fetchByDegreeAndMajor(String degree, String major) throws Exception {
        // OPEN SESAmE
        Connection conn = DriverManager.getConnection(url);

        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM students WHERE (DEGREE='" + degree + "' AND MAJOR='" + major + "')");

        boolean append = true;
        CsvDriver.writeToCsv(resultSet, System.out, append);
    }
}

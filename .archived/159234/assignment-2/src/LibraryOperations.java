import java.util.ArrayList;
public interface LibraryOperations {
    ArrayList<Record> search(String phrase);
    Record search(int id);
    void sortRecords();
}

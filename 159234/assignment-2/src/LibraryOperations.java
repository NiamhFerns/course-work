public interface LibraryOperations {
    Record search(String phrase);
    Record search(int id);
    void sortRecords();
}

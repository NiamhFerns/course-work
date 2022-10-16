import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibrarySystem implements LibraryOperations {

    private ArrayList<Record> Records = new ArrayList<>();

    public void addRecord(String s) {
        String[] data = s.split(",");
        switch(data[0]) {
            // Simple switch case to add the correct subclass to our Records list for this LibrarySystem.
            case "Book":
                Records.add(new Book(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                break;
            case "Journal":
                Records.add(new Journal(Integer.parseInt(data[1]), data[2], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                break;
            case "Movie":
                Records.add(new Movie(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[3])));
                break;
            default:
                break;
        }

        sortRecords();
    }

    public List<Record> getRecords() {
        return List.copyOf(Records);
    }

    // We create a library system and a handler to handle the records in our library system.
    public static void main(String[] args) {
        LibrarySystem MasseyLibrary = new LibrarySystem("library.txt");
        LibraryHandler e = new LibraryHandler();

        // Preliminary display of information and entries.
        LibrarySystem.displayInfo();
        MasseyLibrary.printRecords();

        // This acts as the entry into our main menu. This system functions as a set of menus controlled by state enum.
        e.action(MasseyLibrary);
    }
    LibrarySystem() {}

    LibrarySystem(String records) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(records));
            String expression;
            while((expression = br.readLine()) != null) {
                String[] data = expression.split(",");
                switch(data[0]) {
                    // Simple switch case to add the correct subclass to our Records list for this LibrarySystem.
                    case "Book":
                        Records.add(new Book(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                        break;
                    case "Journal":
                        Records.add(new Journal(Integer.parseInt(data[1]), data[2], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[3])));
                        break;
                    case "Movie":
                        Records.add(new Movie(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[3])));
                        break;
                    default:
                        break;
                }
            }
        }
        catch(IOException e) {
            System.out.println("The file \"" + records + "\" could not be found.");
            System.exit(0);
        }

        sortRecords();
    }

    // 2 short functions to print all records currently in the system.
    public void printRecords() { Records.forEach(Record::shortPrint); }
    public void printFullRecords() { Records.forEach(Record::longPrint); }

    private static void displayInfo() {
        System.out.println("-----------------------------------");
        System.out.println("   Assignment 2  Semester 1 2022   ");
        System.out.println("Submitted by: Niamh, Ferns 21007069");
        System.out.println("-----------------------------------");
        System.out.println();
    }

    // Implementations for our library operations interface. This allows us to search for the correct records and
    // sort them as needed.
    @Override
    public ArrayList<Record> search(String phrase) {
        // For each Record in our list, if it contains the phrase, add it to a returned ArrayList of records.
        ArrayList<Record> found = new ArrayList<>();
        Records.forEach( (r) -> { if(r.getTitle().toLowerCase().contains(phrase.toLowerCase())) found.add(r); });
        return found;
    }
    @Override
    public Record search(int id) {
        // We filter through each element in the Records array and return either the first one or null if the filter ends.
        return Records.stream()
                .filter(r -> r.getID() == id)
                .findFirst()
                .orElse(null);

    }
    @Override
    public void sortRecords() {
        Collections.sort(Records, new RecordComparator());
    }
}

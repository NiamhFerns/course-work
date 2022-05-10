import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LibrarySystem {

    ArrayList<Book> Books = new ArrayList<>();
    ArrayList<Movie> Movies = new ArrayList<>();
    ArrayList<Journal> Journals = new ArrayList<>();

    public static void main(String[] args) {
        LibrarySystem MasseyLibrary = new LibrarySystem("library.txt");
        MasseyLibrary.printRecords();
    }

    LibrarySystem(String records) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(records));
            String expression;
            while((expression = br.readLine()) != null) {
                String[] data = expression.split(",");
                switch(data[0]) {
                    // This is all gross. I should not be allowed to code.
                    case "Movie":
                        Movies.add(new Movie(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[3]), 5.0));
                        break;
                    case "Book":
                        Books.add(new Book(Integer.parseInt(data[1]), data[2], data[4], Integer.parseInt(data[5]), Integer.parseInt(data[3]), 5.0));
                        break;
                    case "Journal":
                        Journals.add(new Journal(Integer.parseInt(data[1]), data[2], Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[3]), 5.0));
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
    }

    public final void printRecords() {
        Books.forEach(Record::shortPrint);
        Movies.forEach(Record::shortPrint);
        Journals.forEach(Record::shortPrint);
    }
}

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public abstract class Record implements Comparable<Record> {
    protected boolean available;
    protected int ID, year;
    protected final int maxBorrowTime;
    protected String title;
    protected ArrayList<Double> reviews;
    protected LocalDate due;

    // Getters
    public String getTitle() { return title; }
    public int getID() { return ID; }
    public Double getReviewAverage() { return reviews.size() > 0 ? reviews.stream().reduce(0.0, Double::sum) / reviews.size() : 0.0; }
    public boolean getStatus() { return available; }

    // Print Functions
    public final void shortPrint() {
        System.out.println("ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }
    public final void longPrint() {
        System.out.println("Average rating: " + getReviewAverage() + " Number of reviewers: " + reviews.size() + " ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }

    // Borrow or return the current record based on availability.
    public final void borrow() {
        available = !available;
        if (!available) {
            setDate();
            System.out.println("This item's due date is " + due);
        }
    }

    // We add a rating to the list of ratings and if it's an invalid rating, we throw an exception.
    public final void rate() {
        System.out.println("Please enter your rating (0-10)");
        Scanner sc = new Scanner(System.in);
        try {
            double rating = sc.nextDouble();
            if (rating > 10 || rating < 0) throw new IllegalArgumentException("That is not a valid rating.");
            reviews.add(rating);
            System.out.println("This item's new average rating is " + getReviewAverage());
        }
        catch(Exception e) {
            System.out.println("This was not a valid rating, please enter a number between 0 and 10.");
        }
    }

    // We just store whatever the current date is plus the maximum borrowing time.
    public final void setDate() {
        due = LocalDate.now().plusDays(maxBorrowTime);
    }

    Record(int ID, String title, int year) {
        this.ID = ID; this.title = title; this.year = year;
        maxBorrowTime = setMaxBorrowTime();
        reviews = new ArrayList<>();
        available = true;
    }

    // We need to have this as an abstract method so that we can force the subclasses to set the relevant maximum borrow time.
    protected abstract int setMaxBorrowTime();
    // full prints differ per record.
    public abstract void fullPrint();

    // For our comparator and sorting.
    public int compareTo(Record r) {
       return this.getID() - r.getID();
    }
}

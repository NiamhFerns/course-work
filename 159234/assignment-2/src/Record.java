import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public abstract class Record {
    protected boolean available;
    protected int ID, year;
    protected final int maxBorrowTime;
    protected String title;
    protected ArrayList<Double> reviews;
    protected LocalDate due;

    public String getTitle() { return title; }
    public int getID() { return ID; }
    public double getReviewAverage() { return reviews.size() > 0 ? reviews.stream().reduce(0.0, Double::sum) / reviews.size() : 0.0; }
    public boolean getStatus() { return available; }

    public final void shortPrint() {
        System.out.println("ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }
    public final void longPrint() {
        System.out.println("Average rating: " + getReviewAverage() + " Number of reviewers: " + reviews.size() + " ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }


    public final void borrow() {
        available = !available;
        if (!available) {
            setDate();
            System.out.println("This item's due date is " + due);
        }
    }
    public final void rate() {
        System.out.println("Please enter your rating (0-10)");
        Scanner sc = new Scanner(System.in);
        try {
            double rating = sc.nextDouble();
            if (rating > 10 || rating < 0) throw new IllegalArgumentException("That is not a valid rating.");
            reviews.add(rating);
        }
        catch(Exception e) {
            System.out.println("This was not a valid rating, please enter a number between 0 and 10.");
        }
        System.out.println("This item's new average rating is " + getReviewAverage());
    }
    public final void setDate() {
        due = LocalDate.now().plusDays(maxBorrowTime);
    }

    Record() {
        ID = 0;
        year = 0;
        maxBorrowTime = setMaxBorrowTime();
        reviews = new ArrayList<>();
        available = true;
    }

    Record(int ID, String title, int year) {
        this.ID = ID; this.title = title; this.year = year;
        maxBorrowTime = setMaxBorrowTime();
        reviews = new ArrayList<>();
        available = true;
    }

    protected abstract int setMaxBorrowTime();
    public abstract void fullPrint();
}

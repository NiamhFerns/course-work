import java.util.ArrayList;

public abstract class Record {
    protected boolean available;
    protected int ID, year;
    protected final int maxBorrowTime;
    protected String title;
    protected ArrayList<Double> reviews;

    public String getTitle() { return title; }
    public int getID() { return ID; }
    public int getMaxBorrowTime() { return maxBorrowTime; }
    public double getReviewAverage() { return reviews.size() > 0 ? reviews.stream().reduce(0.0, Double::sum) / reviews.size() : 0.0; }
    public boolean getStatus() { return available; }

    public final void shortPrint() {
        System.out.println("ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }
    public final void longPrint() {
        System.out.println("Average rating: " + getReviewAverage() + " Number of reviewers: " + reviews.size() + " ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }


    public final void borrow() {

    }
    public final void rate() {

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

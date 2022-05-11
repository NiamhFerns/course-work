import java.util.ArrayList;

public abstract class Record {
    protected int ID, year;
    protected final int maxBorrowTime;
    protected String title;
    protected ArrayList<Double> reviews;

    public String getTitle() { return title; }
    public int getID() { return ID; }

    public final void shortPrint() {
        System.out.println("ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }
    public final void longPrint() {
        double averageRating = reviews.stream().reduce(0.0, Double::sum) / reviews.size();

        System.out.println("Average rating: " + averageRating + " Number of reviewers: " + reviews.size() + " ID: " + ID + " Type: " + this.getClass().getName() + " Title: " + title);
    }

    public final void fullPrint() {

    }

    Record() {
        ID = 0;
        year = 0;
        maxBorrowTime = setMaxBorrowTime();
        reviews = new ArrayList<>();
    }

    Record(int ID, String title, int year, double review) {
        this.ID = ID; this.title = title; this.year = year;
        maxBorrowTime = setMaxBorrowTime();
        reviews = new ArrayList<>(); reviews.add(review);
    }

    protected abstract int setMaxBorrowTime();
}

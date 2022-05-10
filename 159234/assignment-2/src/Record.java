import java.util.ArrayList;

public abstract class Record {
    protected int ID, year;
    protected final int maxBorrowTime;
    protected String title;
    protected ArrayList<Double> reviews;

    protected final void shortPrint() {
        System.out.println(ID + ", " + this.getClass().getName() + ", " + title);
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

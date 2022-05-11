public class Movie extends Record {
    private String director;
    Movie() {
        director = "";
    }
    Movie(int ID, String title, String director, int year) {
        super(ID, title, year);
        this.director = director;
    }

    @Override
    protected int setMaxBorrowTime() { return 7; }
    public void fullPrint() {
        System.out.println("Type: " + this.getClass().getName() + "\nTitle: " + title + "\nYear: " + year);
        System.out.println("Average rating: " + getReviewAverage() + "\nNumber of Reviewers: " + reviews.size());
        System.out.println("Status: " + (available ? "available" : "on loan"));
        System.out.println("Director: " + director);
        System.out.println("Max number of days for borrowing: " + maxBorrowTime);
    }
    @Override
    public String toString() {
        return ID + ", " +  title + ", " + director + ", " + year;
    }
}

public class Book extends Record {
    private int pages;
    private String author;
    Book() {
        author = "";
        pages = 0;
    }
    Book(int ID, String title, String author, int pages, int year) {
        super(ID, title, year);
        this.author = author;
        this.pages = pages;
    }

    @Override
    protected int setMaxBorrowTime() { return 28; }
    @Override
    public void fullPrint() {
        System.out.println("Type: " + this.getClass().getName() + "\nTitle: " + title + "\nYear: " + year);
        System.out.println("Average rating: " + getReviewAverage() + "\nNumber of Reviewers: " + reviews.size());
        System.out.println("Status: " + (available ? "available" : "on loan"));
        System.out.println("Author: " + author + "\nNumber of pages: " + pages);
        System.out.println("Max number of days for borrowing: " + maxBorrowTime);
    }
    @Override
    public String toString() {
        return ID + ", " +  title + ", " + author + ", " + pages + ", " + year;
    }
}

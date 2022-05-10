public class Book extends Record {
    private int pages;
    private String author;
    Book() {
        author = "";
        pages = 0;
    }
    Book(int ID, String title, String author, int pages, int year, double review) {
        super(ID, title, year, review);
        this.author = author;
        this.pages = pages;
    }

    @Override
    protected int setMaxBorrowTime() { return 28; }

    @Override
    public String toString() {
        return ID + ", " +  title + ", " + author + ", " + pages + ", " + year;
    }
}

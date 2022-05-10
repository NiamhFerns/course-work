public class Movie extends Record {
    private String director;
    Movie() {
        director = "";
    }
    Movie(int ID, String title, String director, int year, double review) {
        super(ID, title, year, review);
        this.director = director;
    }

    @Override
    protected int setMaxBorrowTime() { return 7; }

    @Override
    public String toString() {
        return ID + ", " +  title + ", " + director + ", " + year;
    }
}

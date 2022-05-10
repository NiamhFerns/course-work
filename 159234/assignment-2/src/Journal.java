public class Journal extends Record {
    private int volume, number;
    Journal() {
        this.volume = 0;
        this.number = 0;
    }
    Journal(int ID, String title, int volume, int number, int year, double review) {
        super(ID, title, year, review);
        this.volume = volume;
        this.number = number;
    }

    @Override
    protected int setMaxBorrowTime() { return 14; }

    @Override
    public String toString() {
        return ID + ", " +  title + ", " + volume + ", " + number + ", " + year;
    }
}

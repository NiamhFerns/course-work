public class Journal extends Record {
    private int volume, number;
    Journal() {
        this.volume = 0;
        this.number = 0;
    }
    Journal(int ID, String title, int volume, int number, int year) {
        super(ID, title, year);
        this.volume = volume;
        this.number = number;
    }

    @Override
    protected int setMaxBorrowTime() { return 14; }
    @Override
    public void fullPrint() {
        System.out.println("Type: " + this.getClass().getName() + "\nTitle: " + title + "\nYear: " + year);
        System.out.println("Average rating: " + getReviewAverage() + "\nNumber of Reviewers: " + reviews.size());
        System.out.println("Status: " + (available ? "available" : "on loan"));
        System.out.println("Volume: " + volume + "\nNumber: " + number);
        System.out.println("Max number of days for borrowing: " + maxBorrowTime);
    }
    @Override
    public String toString() {
        return ID + ", " +  title + ", " + volume + ", " + number + ", " + year;
    }
}

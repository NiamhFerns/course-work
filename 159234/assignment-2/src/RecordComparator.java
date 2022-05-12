import java.util.Comparator;

public class RecordComparator implements Comparator<Record> {
    public int compare(Record a, Record b) {
        int ratingComparison = a.getReviewAverage().compareTo(b.getReviewAverage());
        return ratingComparison == 0 ? a.compareTo(b) : ratingComparison;
    }
}

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Structure for holding information about a bus/train/ferry/cablecar line
 */

public class Line {
    
    private final String lineId;
    private final String transpType;   // one of "bus", "train", "cablecar", "ferry"

    // paired lists with stop id and stop times.  need to make sure they remain in order
    private List<Stop> stops;
    private List<Integer> times;



    // constructor used to create and then add stops to the line
    public Line(String lineId) {
        this.lineId = lineId;
        this.transpType = Transport.transpType(lineId);
        this.stops = new ArrayList<Stop>();
        this.times = new ArrayList<Integer>();
        
    }

    /**
     * Add a stop to the end of the current line
     * @param stopId the 4/5 digit stop id
     * @param time  the time from the start of the line to the current stop
     */
    public void addStop(Stop stop, int time) {
        this.stops.add(stop);
        this.times.add(time);
    }

    public String getId() {
        return lineId;
    }

    public String getType() {
        return transpType;
    }

    // to string
    public String toString() {
        String s = "";
        s += "Line: " + lineId + " ("+transpType+")\t stops: " + stops.toString() + "\t times: " + times.toString();
        return s;
    }

    /**
     * Return the stops for each stop in the line.
     */
    public List<Stop> getStops() {
        return Collections.unmodifiableList(stops);
    }
    /**
     * Return the times for each stop in the line.
     * @return the list of times in seconds
     */
    public List<Integer> getTimes() {
        return  Collections.unmodifiableList(times);
    }

}

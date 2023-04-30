/*
 * Simple data structure to represent a Geographical Information System (GIS) point. 
*/

public class GisPoint {
    public final static double EARTH_CIRCUMFANCE = 40075016.68557849; // meters
    public final static double SCALE = EARTH_CIRCUMFANCE / 360.0; // meters per degree

    private double lon; // longitude in degrees is X
    private double lat; // latitude in degrees is Y

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * From google search and answer:
     * https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * 
     * @returns Distance in meters between two GPS points with elevation
     * 
     */
    public static double distance(double lat1, double lat2, double lon1,
            double lon2, double el1, double el2) {

        final int R = 6371000; // Radius of the earth in Meters

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2.0) * Math.sin(latDistance / 2.0)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDistance / 2.0) * Math.sin(lonDistance / 2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
        double distance = R * c; // convert to meters
        double height = el1 - el2;

        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0);
        return Math.sqrt(distance);
    }

    public GisPoint(double lon, double lat) {
        this.lat = lat;
        this.lon = lon;
    }

    // add and return a new Gis Point
    public GisPoint add(GisPoint point) {
        return new GisPoint(this.lon + point.lon, this.lat + point.lat);
    }

    public GisPoint add(double lon, double lat) {
        return new GisPoint(this.lon + lon, this.lat + lat);
    }

    // move the location of the Gis Point
    public void move(GisPoint point) {
        this.lat += point.lat;
        this.lon += point.lon;
    }

    public void move(double lon, double lat) {
        this.lat += lat;
        this.lon += lon;
    }

    // return a new Gis Point with the subtracted values    
    public GisPoint subtract(GisPoint point) {
        return new GisPoint(this.lon - point.lon, this.lat - point.lat);
    }

    public GisPoint subtract(double lon, double lat) {
        return new GisPoint(this.lon - lon, this.lat - lat);
    }


    // to string
    public String toString() {
        return "(" + lon + ", " + lat + ")";
    }

    /**
     * 
     * @returns Distance in Meters
     */
    public double distance(double lon2, double lat2) {
        return GisPoint.distance(this.lat, lat2, this.lon, lon2, 0.0, 0.0); // operating without elevation
    }

    /**
     * .
     * 
     * @returns Distance in Meters
     */
    public double distance(GisPoint loc) {
        return GisPoint.distance(this.lat, loc.lat, this.lon, loc.lon, 0.0, 0.0);
    }

    // get lat
    public double getLat() {
        return lat;
    }

    // get lon
    public double getLon() {
        return lon;
    }

}

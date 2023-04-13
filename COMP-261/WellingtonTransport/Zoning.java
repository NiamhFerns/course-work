import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

// class to store the GeoJson data for wellington transport fare boundaries
// https://www.rfc-editor.org/info/rfc7946

// "type": "FeatureCollection",
// "name": "Greater_Wellington_-_Public_Transport",
// "crs": { "type": "name", "properties": { "name": "urn:ogc:def:crs:OGC:1.3:CRS84" } }, "features": [
// { "type": "Feature", "properties": { "OBJECTID": 1, "ZONE": 1, "Shape_Length": 17970.978544639267, "Shape_Area": 6391753.5773680275 }, "geometry": { "type": "MultiPolygon", "coordinates":

public class Zoning {

    // the type of the GeoJson object
    private String type;
    private String name;
    private double length;
    private double area;

    private Map<String, Shape> shapes = new HashMap<String, Shape>();


    // Construct Zoning object from a geojson file
    // ID, Zone, Shape_Length, Shape_Area, coordinates
    public  Zoning(File geoJsonFile) {
        this.type = "shape file";
        this.name = "Wellington";
        try {
            // make a reader
            List<String> dataLines = Files.readAllLines(geoJsonFile.toPath());
            //throw away the two header lines of the file.
            dataLines.remove(1);
            dataLines.remove(0); 
            for (String dataLine : dataLines){
                // tokenise the line by splitting it at ",".
                String[] tokens = dataLine.split("[,]");
                if (tokens.length >= 5) {
                    // process the tokens
                    String id = tokens[0];
                    String zone_str = tokens[1];

                    double shapeLength = Double.parseDouble(tokens[2]);
                    double shapeArea = Double.parseDouble(tokens[3]);
                    Shape zone = new Shape(Integer.valueOf(zone_str), shapeLength, shapeArea);
                    // for loop through the coordinates
                    GeoPoly poly = new GeoPoly();
                    for (int i = 4; i < tokens.length; i = i + 2) {
                        double lon = Double.parseDouble(tokens[i]);
                        double lat = Double.parseDouble(tokens[i + 1]);
                        poly.add(new GisPoint(lon, lat));
                    }
                    if (shapes.get(zone_str) == null) {
                        shapes.put(zone_str, zone);
                    }
                    shapes.get(zone_str).addShape(poly);

                }
            }
        } catch (IOException e) {
            throw new RuntimeException("file reading failed.");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // add polygon to the list of zones
    public void addZone(String id, Shape zone) {
        this.shapes.put(id, zone);
    }

    // get the collection of zones
    public Map<String, Shape> getZones() {
        return shapes;
    }

}

// Zone properties
class Shape {
    private int shapeID;
    private double Shape_Length;
    private double Shape_Area;
    private List<GeoPoly> shapes;

    // constructor
    public Shape(int shapeID, double Shape_Length, double Shape_Area) {
        this.shapeID = shapeID;
        this.Shape_Length = Shape_Length;
        this.Shape_Area = Shape_Area;
        this.shapes = new ArrayList<GeoPoly>();
    }

    public int getZONE() {
        return shapeID;
    }

    public void setZONE(int shapeID) {
        this.shapeID = shapeID;
    }

    public double getShape_Length() {
        return Shape_Length;
    }

    public void setShape_Length(double Shape_Length) {
        this.Shape_Length = Shape_Length;
    }

    public double getShape_Area() {
        return Shape_Area;
    }

    public void setShape_Area(double Shape_Area) {
        this.Shape_Area = Shape_Area;
    }

    // add a polygon to the list of shapes
    public void addShape(GeoPoly shape) {
        this.shapes.add(shape);
    }

    // get the list of shapes
    public List<GeoPoly> getShapes() {
        return shapes;
    }

}

// geoJson polygon
class GeoPoly {
    private ArrayList<GisPoint> points;

    public GeoPoly() {
        this.points = new ArrayList<GisPoint>();
    }

    public GeoPoly(ArrayList<GisPoint> points) {
        this.points = points;
    }

    public ArrayList<GisPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<GisPoint> points) {
        this.points = points;
    }

    public void add(GisPoint point) {
        points.add(point);
    }
}

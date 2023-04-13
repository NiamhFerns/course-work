/** Top level program controlling the interface */ 

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Locale;
import java.util.ResourceBundle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.*;

public class Controller {

    public Graph graph;
    public Zoning zoneData;


    // names from the items defined in the FXML file
    @FXML
    private TextField startText;
    @FXML
    private TextField goalText;
    @FXML
    private Label Start;
    @FXML
    private Label Goal;
    @FXML
    private Label Walking;
    @FXML
    private Button Quit;
    @FXML
    private Button english_bt;
    @FXML
    private Button maori_bt;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private Label nodeDisplay;
    @FXML
    private TextArea lineText;

    @FXML
    private CheckBox walking_ch;
    @FXML
    private Button connectedComponents_bt;
    @FXML
    private Button articulationPoints_bt;
    @FXML
    private Slider walkingDistance_sl;
    @FXML
    private TextField walkingDistance_tf;

    // These are used to map the nodes to the location on screen
    private double scale = 5000.0; // 5000 gives 1 pixel ~ 20 meters
    private static final double ratioLatLon = 0.73; // in Wellington ratio of latitude to longitude
    private GisPoint mapOrigin = new GisPoint(174.77, -41.3); // Lon Lat for Wellington


    private static final int STOP_SIZE = 5; // drawing size of stops

    // used for A*
    private Stop startLocation;
    private Stop goalLocation;

    // used to prevent drag from creating a click
    private Boolean dragActive = false;

    // Collection of stops that should be highlighted (for whatever reason)
    private Collection<Stop> highlightNodes = new ArrayList<Stop>();

    // List of edges forming a path to be displayed 
    private List<Edge> pathEdges = null;

    // set up connections between the buttons and the methods
    public void initialize() {

        // load the input files
        Map<String, Stop> stopMap = loadStops(new File("data/stops.txt"));
        Collection<Line> lines = loadLines(new File("data/lines.txt"), stopMap);
                               
        this.graph = new Graph(stopMap.values(), lines);
        System.out.println("Loaded Graph Data");
        
        this.zoneData = new Zoning(new File("data/WellingtonZones.csv"));
        System.out.println("Loaded Zone Data");

        drawGraph(graph);
    }

    // get scale
    public double getScale() {
        return scale;
    }

    // get origin
    public GisPoint getOrigin() {
        return mapOrigin;
    }

    // get mapCanvas
    public Canvas getMapCanvas() {
        return mapCanvas;
    }

    // get ratLatLon
    public double getRatioLatLon() {
        return ratioLatLon;
    }

    /** handle the quit button being pressed connected using FXML */
    public void handleQuit(ActionEvent event) {
        System.out.println("Quitting with event " + event.getEventType());
        event.consume();
        System.exit(0); // system exit with status 0 - normal
    }


    // handle the english button being pressed connected using FXML
    public void handleEnglish(ActionEvent event)throws IOException  {
        //switch to english using setting resource bundle
        Main.setLocale(new Locale("en", "NZ")); // change to english
        Main.stage.close();
        Main reload = new Main();
        reload.reload();
        event.consume();
    }

    // handle the maori button being pressed connected using FXML
    public void handleMaori(ActionEvent event) throws IOException  {
        Main.setLocale(new Locale("mi", "NZ")); // change to english
        Main.stage.close();
        Main reload = new Main();
        reload.reload();
        event.consume();
    }
    
    // Key typing event for the Start bus stop
    public void handleStartKey(KeyEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String searchString = ((TextField) event.getSource()).getText();
        // Display the stops that match in the lineText area to help typing
        lineText.setText(stopNames(graph.getAllMatchingStops(searchString)));
        event.consume();
    }

    // Key typing event for the Goal bus stop
    public void handleGoalKey(KeyEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String searchString = ((TextField) event.getSource()).getText();
        // Display the stops that match in the lineText area to help typing
        lineText.setText(stopNames(graph.getAllMatchingStops(searchString)));
        event.consume();
    }
    /** Build a string containing the names of the stops in the given list */
    public String stopNames(List<Stop> listOfStops) {
        StringBuilder names = new StringBuilder("");
        for (Stop stop : listOfStops) {names.append(stop.getName()).append("\n");}
        return names.toString();
    }

    // When enter is pressed in text field perform an A* search
    public void handleStartAction(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String search = ((TextField) event.getSource()).getText();
        // set the start search location
        startLocation = graph.getFirstMatchingStop(search);

        // perform A* search and get the path edges
        pathEdges = AStar.findShortestPath(startLocation, goalLocation, "distance");

        drawGraph(graph); //update the graph
        event.consume();
    }

    // When enter is pressed perform an A* search using the Goal as the destination
    public void handleGoalAction(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String search = ((TextField) event.getSource()).getText();
        // set the goal search location
        goalLocation = graph.getFirstMatchingStop(search);
        // perform A* search and get the path edges
        pathEdges = AStar.findShortestPath( startLocation, goalLocation, "distance");

        drawGraph(graph);// update the graph
        event.consume();
    }

    // handleShowConnectedComponents
    public void handleShowConnectedComponents(ActionEvent event) {
        System.out.println("Show connected components event " + event.getEventType());
        //INFO : This is where your find component code is called
        highlightNodes.clear();
        pathEdges = null;
        Components.findComponents(graph);
        System.out.println("findComponents -> SubGraphCount: "+graph.getSubGraphCount());
        drawGraph(graph);
        //display to line text
        lineText.setText(Integer.toString(graph.getSubGraphCount()));
    }

    // handleShowArticulationPoints
    public void handleShowArticulationPoints(ActionEvent event) {
        System.out.println("Show articulation points event " + event.getEventType());
        //INFO : This is where your articulation points code is called
        pathEdges = null;
        graph.resetSubGraphIds();
        walkingDistance_sl.setValue(0.0);
        walkingDistance_tf.setText("0.0");
        graph.removeWalkingEdges();  // Walking edges shuld not be included.
        highlightNodes = ArticulationPoints.findArticulationPoints(graph);
        drawGraph(graph);
    }

    // handleAddWalking calls the code to add Walking
    public void handleAddWalking(ActionEvent event) {
        System.out.println("Add walking event " + walking_ch.isSelected());
        graph.removeWalkingEdges();
        if (walking_ch.isSelected()) {
            graph.recomputeWalkingEdges(Double.parseDouble(walkingDistance_tf.getText()));
        }
        drawGraph(graph);
    }

    // This handles entering distance in the walking text field and sets the slider
    public void handleWalkingDistance(ActionEvent event) {
        // divide the text value by four so the slider is 0 - 400
        String distStr = walkingDistance_tf.getText();
        double dist = 0;        
        try {dist = Math.round((Double.parseDouble(distStr)));}
        catch (Exception e){walkingDistance_tf.setText("0");}
        System.out.println("Setting walking distance (in tf) to " + dist);
        walkingDistance_sl.setValue(dist/4.0);
        graph.resetSubGraphIds();
        graph.removeWalkingEdges();
        if (dist>0){
            graph.recomputeWalkingEdges(dist);
        } 
        drawGraph(graph);
    }

    // This handles entering distance on the walking slider and sets the text field
    public void handleWalkingDistanceSlider(ObservableValue<Double> ovn, Double before, Double after) {
        // multiply the slider value by four so the text field is 0 - 400
        double dist = Math.round(ovn.getValue()*4.0);
        walkingDistance_tf.setText(Double.toString(dist));
        System.out.println("Setting walking distance (on slider) to " + dist);
        graph.resetSubGraphIds();
        graph.removeWalkingEdges();
        if (dist>0){
            graph.recomputeWalkingEdges(dist);
        } 
        drawGraph(graph);
    }

    // Mouse scroll for zoom
    public void mouseScroll(ScrollEvent event) {
        // change the zoom level
        double changefactor = 1 + (event.getDeltaY() / 400);
        scale *= changefactor;
        // update the graph
        drawGraph(graph);
        event.consume();
    }

    public double dragStartX = 0;
    public double dragStartY = 0;
    // handle starting drag on canvas
    public void handleMousePressed(MouseEvent event) {
        dragStartX = event.getX();
        dragStartY = event.getY();
        event.consume();
    }

    // handleMouse Drag
    public void handleMouseDrag(MouseEvent event) {
        // pan the map
        double dx = event.getX() - dragStartX;
        double dy = event.getY() - dragStartY;
        dragStartX = event.getX();
        dragStartY = event.getY();
        mapOrigin.move(-dx / (scale * ratioLatLon), (dy / scale));

        drawGraph(graph);
        // set drag active true to avoid clicks highlighting nodes
        dragActive = true;
        event.consume();
    }


    private Stop goalStop = null;
    private Stop prevStartStop = null;

    /*
     * handle mouse clicks on the canvas
     * select the node closest to the click
     */
    public void handleMouseClick(MouseEvent event) {
        if (dragActive) {
            dragActive = false;
            return;
        }

        System.out.println("Mouse click event " + event.getEventType());
        // find node closed to mouse click
        Point2D screenPoint = new Point2D(event.getX(), event.getY());
        GisPoint location = Projection.screen2Model(screenPoint, this);

        Stop closestStop = findClosestStop(location, graph);
        highlightClosestStop(closestStop);

        if (event.isShiftDown()) {
            // if shift is pressed, add the node to the list of nodes to be searched
            highlightNodes.add(closestStop);
            goalLocation = closestStop;
            drawGraph(graph);
        } else {
            highlightNodes.clear();
            highlightNodes.add(closestStop);
            // drawGraph(graph);
            startLocation = goalLocation;
            goalLocation = closestStop;
            // shortest path planning
        }
        if (startLocation != null && closestStop != startLocation) {
            // INFO: This is where your find path code is called during clicking
            pathEdges = AStar.findShortestPath(startLocation, goalLocation,"distance");
        }
        drawGraph(graph);
        event.consume();
    }

    /**
     * Find the closest stop to the given Gis Point location
     * @param x
     * @param y
     * @param graph
     * @return
     */
    public Stop findClosestStop(GisPoint loc, Graph graph) {
        double minDist = Double.MAX_VALUE;
        Stop closestStop = null;
        // This is slow but could be faster if you use a quadtree or KD tree
        for (Stop stop : graph.getStops()) {
            double dist = stop.distanceTo(loc);
            if (dist < minDist) {
                minDist = dist;
                closestStop = stop;
            };
        }
        if (closestStop != null) {
            return closestStop;
        }
        return null;
    }

    // 
    public void highlightClosestStop(Stop closestStop) {
        if (closestStop != null) {
            highlightNodes.clear();
            highlightNodes.add(closestStop);
            drawGraph(graph);
        }
    }

    /**
     * Draw the current graph, along with the current path, if there is one.
     * If there is a path, it also updates the lineText text area with a
     * text description of the path.
     */
    public void drawGraph(Graph graph) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        drawFareZones(gc);

        if (graph == null) {return;}

        // draw all the edges
        for (Edge edge : graph.getEdges()){
            Color color = switch (edge.transpType()) {
                case Transport.BUS -> Color.ROSYBROWN;
                case Transport.TRAIN -> Color.ORANGE;
                case Transport.WALKING -> Color.PURPLE;
                default -> Color.GREEN; };

            drawEdge(edge, 0.5, color);
        }

        drawPath();

        // Draw the stops,
        // Hilighted stops are red and double the normal size
        // For the other nodes, if the subgraphs /connected components have been identified,
        //  then colour the nodes according to their component number

        int numSubGraphs = graph.getSubGraphCount();
        Color[] subGraphColors = new Color[numSubGraphs];
        for (int i=0; i<numSubGraphs; i++){
            subGraphColors[i]= Color.hsb((180.0 + (i*360.0/numSubGraphs)) % 360, 1, 1);
        }

        for(Stop stop : graph.getStops()) {
            int size = STOP_SIZE;
            Color color;
            if (highlightNodes.contains(stop)) {
                drawStop(stop, STOP_SIZE*2, Color.RED);
            } else {
                drawStop(stop, STOP_SIZE, (numSubGraphs==0? Color.BLUE : subGraphColors[stop.getSubGraphId()]));
            }
        }

        // display the start and goal stops and report the path (if there is one)
        if (startLocation != null) {
            startText.setText(startLocation.getName());
        }
        if (goalLocation != null) {
            goalText.setText(goalLocation.getName());
        }
        reportPath();
    }

    private void drawEdge(Edge edge, double width, Color color){
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setLineWidth(width);
        gc.setStroke(color);
        Point2D from = Projection.model2Screen(edge.fromStop().getPoint(), this);
        Point2D to = Projection.model2Screen(edge.toStop().getPoint(), this);
        gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
    }


    /*
     * Draw a stop with the given size and color.
     */
    public void drawStop(Stop stop, int size, Color color) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setFill(color);
        Point2D point = Projection.model2Screen(stop.getPoint(), this);
        gc.fillOval(point.getX() - size / 2, point.getY() - size / 2, size, size);
    }


    /**
     * Draw the list of Path Edges (eg, returned from A* search)
     */
    public void drawPath() {
        if (pathEdges!=null){
            for (Edge edge : pathEdges) {
                Color color = switch (edge.transpType()) {
                case Transport.WALKING -> Color.BLACK;
                case Transport.BUS -> Color.RED;
                default -> Color.GREEN; };
                drawEdge(edge, 3, color);
            }
        }
    }

    /**
     * Draw the fare zones
     * This show the outline of Wellington.
     */
    public void drawFareZones(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(1);
        // for loop over values in the Hashmap of shapes
        for (Shape zone : zoneData.getZones().values()) {
            for (GeoPoly poly : zone.getShapes()) {
                for (int k = 0; k < poly.getPoints().size() - 1; k++) {
                    Point2D start = Projection.model2Screen(poly.getPoints().get(k), this);
                    Point2D end = Projection.model2Screen(poly.getPoints().get(k + 1), this);
                    gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());
                }
            }
        }
    }


    /**
     * Constructs a String description of the current path (if there is one)
     * and puts it in the lineText text area.
     * Assumes that the edges in the current path are in order from the start node to the goal node
     */
    public void reportPath(){
        if (pathEdges != null) {
        int totalTime = 0;
        double totalDistance = 0;
            StringBuilder path = new StringBuilder("START ").append(startLocation.getName()).append("\n");
            for (Edge edge : pathEdges){
                totalTime += edge.time();
                totalDistance += edge.distance();
                path.append(edge.toString()).append("\n");
            }
            int secs = (int)(totalTime)%60;
            int mins = totalTime/60%60;
            int hours = totalTime/3600;
            path.append(String.format("GOAL %s\nTotal path time = %d:%02d:%02d\nTotal path distance = %.3fkm",
                goalLocation.getName(),hours,mins, secs,totalDistance/1000));
            lineText.setText(path.toString());
        }
    }




    //-------------------------------------------------------
    //  methodes to load Stop and Line data from files
    //-------------------------------------------------------

    /** Load the stop data from the stop file
     * file contains:
     *     stop_id, stop_code, stop_name, stop_desc,
     *     stop_lat, stop_lon, zone_id, location_type,
     *     parent_station, stop_url, stop_timezone
     */
    public static Map<String, Stop> loadStops(File stopsFile) {
        Map<String, Stop> stops = new HashMap<String, Stop>();

        try {
            List<String> dataLines = Files.readAllLines(stopsFile.toPath());
            dataLines.remove(0);// throw away the top line of the file
            for (String dataLine : dataLines){
                // tokenise the dataLine by splitting it on tabs
                String[] tokens = dataLine.split("\t");
                if (tokens.length >= 6) {
                    // process the tokens
                    String stopId = tokens[0];
                    String stopName = tokens[2];
                    double lat = Double.valueOf(tokens[4]);
                    double lon = Double.valueOf(tokens[5]);
                    stops.put(stopId, new Stop(lon, lat, stopName, stopId));
                }
            }
            System.out.println("Loaded "+ stops.size()+" stops");
        } catch (IOException e) {
            throw new RuntimeException("Reading the stops file failed.");
        }
        return stops;
    }

    /** Load the line data from the lines file
     * File contains: line_id, stop_id, timepoint
     * Uses the stopMap to turn the stop_id's into Stops
     */
    public static Collection<Line> loadLines(File lineFile, Map<String,Stop> stopMap) {
        if (stopMap.isEmpty()){
            throw new RuntimeException("loadLines given an empty stopMap.");
        }
        Map<String, Line> lineMap = new HashMap<String, Line>();
        try {
            System.out.println("Reading data from: "+lineFile);
            List<String> dataLines = Files.readAllLines(lineFile.toPath());
            dataLines.remove(0); //throw away the top line of the file.
            for (String dataLine : dataLines){// read in each line of the file
                // tokenise the line by splitting it on tabs".
                String[] tokens = dataLine.split("\t");
                if (tokens.length >= 3) {
                    // process the tokens
                    String lineId = tokens[0];
                    Line line = lineMap.get(lineId);
                    if (line == null) {
                        System.out.println("Loading line: "+lineId);
                        line = new Line(lineId);
                        lineMap.put(lineId, line);
                    }
                    int time = Integer.parseInt(tokens[2]);
                    String stopId = tokens[1];
                    Stop stop = stopMap.get(stopId);
                    if (stop==null){
                        System.out.println("Line "+lineId+" has unknown stop "+stopId+" at "+time);
                    }
                    else {
                        line.addStop(stop, time);
                        stop.addLine(line);
                    }
                }
                else {
                    System.out.println("Line file has broken entry: "+dataLine);
                }
                    
            }
            System.out.println("Loaded "+ lineMap.size()+" lines");
        } catch (IOException e) {throw new RuntimeException("Loading the lines file failed.");}
        return lineMap.values();
    }

}

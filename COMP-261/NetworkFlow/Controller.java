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
import javafx.scene.text.Font;
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
import javafx.util.Pair;

public class Controller {

    public Graph graph;

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
    private Button Quit;
    @FXML
    private Button english_bt;
    @FXML
    private Button maori_bt;
    @FXML
    private Button networkflow_bt;
    @FXML
    private Button pagerank_bt;
    @FXML
    private Canvas mapCanvas;
    @FXML
    private Label nodeDisplay;
    @FXML
    private TextArea lineText;

    // These are used to map the nodes to the location on screen
    private double scale = 3.5; 
    private Point2D origin = new Point2D(100, 80);
    private static final double ratioxy = .5;
    private static int nodeSize = 10;

    private static final int City_SIZE = 5; // drawing size of Cities

    //Used for flow network
    private City startLocation;
    private City goalLocation;
    //stores the augmentation paths
    ArrayList<Pair<ArrayList<String>, Integer>>augmentationPaths;
    // used to prevent drag from creating a click
    private Boolean dragActive = false;

    // Collection of Cities that should be highlighted (for whatever reason)
    private Collection<City> highlightNodes = new ArrayList<City>();
    private Collection<Edge> highlightEdges = new ArrayList<Edge>();

    // set up connections between the buttons and the methods
    public void initialize() {

        // load the input files
        Map<String, City> CityMap = loadCities(new File("data/node.csv"));
        Map<String, Edge> lines = loadLines(new File("data/edge.csv"), CityMap);

        this.graph = new Graph(CityMap, lines.values());
        System.out.println("Loaded Graph Data");

        drawGraph(graph);
    }

    // get scale
    public double getScale() {
        return scale;
    }

    // get mapCanvas
    public Canvas getMapCanvas() {
        return mapCanvas;
    }

    //get Origin
    public Point2D getOrigin(){
        return origin;
    }

    // load button pressed - load Node file and edge file in that order
    public void handleLoad(ActionEvent event) {
        Stage stage = (Stage) mapCanvas.getScene().getWindow();
        System.out.println("Handling event " + event.getEventType());
        FileChooser fileChooser = new FileChooser();
        // Set to user directory or go to default
        File defaultNodePath = new File("data/");
        if (!defaultNodePath.canRead()) {
            defaultNodePath = new File("C:/");
        }
        fileChooser.setInitialDirectory(defaultNodePath);
        FileChooser.ExtensionFilter extentionFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extentionFilter);

        fileChooser.setTitle("Open Node File");
        File nodeFile = fileChooser.showOpenDialog(stage);

        fileChooser.setTitle("Open Edge File");
        File edgeFile = fileChooser.showOpenDialog(stage);
        Map<String, City> stopMap = loadCities(nodeFile);
        Map<String,Edge> lines = loadLines(edgeFile, stopMap);
        System.out.println("Loaded Graph Data; constructing graph....");
        graph = new Graph(stopMap, lines.values());
        drawGraph(graph);
        event.consume();
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

     
    // When enter is pressed in text field set the startLocation
    public void handleStartAction(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String search = ((TextField) event.getSource()).getText();
        // set the start search location
        startLocation = graph.getCity(search);;

        drawGraph(graph); //update the graph
        event.consume();
    }

    // When enter is pressed in the text field set the goalLocation
    public void handleGoalAction(ActionEvent event) {
        System.out.println("Look up event " + event.getEventType() + "  " + ((TextField) event.getSource()).getText());
        String search = ((TextField) event.getSource()).getText();
        // set the goal search location
        goalLocation = graph.getCity(search);;

        drawGraph(graph);// update the graph
        event.consume();
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
        origin= new Point2D(origin.getY()-dx / (scale * ratioxy), origin.getY()+(dy / scale));

        drawGraph(graph);
        // set drag active true to avoid clicks highlighting nodes
        dragActive = true;
        event.consume();
    }

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
        // find node close to mouse click
        Point2D screenPoint = new Point2D(event.getX(), event.getY());
        Point2D modelLoc = Projection.screen2Model(screenPoint,this);
        City closestCity = findClosestCity(modelLoc,graph);
        highlightClosestCity(closestCity);

        highlightNodes.clear();
        highlightNodes.add(closestCity);

        startLocation = goalLocation;
        goalLocation = closestCity;

        drawGraph(graph);
        event.consume();
    }

    /**
     * Find the closest City to the given Gis Point location
     * @param x
     * @param y
     * @param graph
     * @return
     */
    public City findClosestCity(Point2D loc, Graph graph) {
        double minDist = Double.MAX_VALUE;
        City closestCity = null;
        // This is slow but could be faster if you use a quadtree or KD tree
        for (City City : graph.getCities().values()) {
            double dist = City.distanceTo(loc);
            if (dist < minDist) {
                minDist = dist;
                closestCity = City;
            };
        }
        if (closestCity != null) {
            return closestCity;
        }
        return null;
    }

    // 
    public void highlightClosestCity(City closestCity) {
        if (closestCity != null) {
            highlightNodes.clear();
            highlightNodes.add(closestCity);
            drawGraph(graph);
        }
    }

    /*
     * Drawing the graph on the canvas
     */
    public void drawCircle(double x, double y, double radius) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.fillOval(x - (radius / 2), (y - radius / 2), radius, radius);
    }

    public void drawCircle(double x, double y, double radius, Color color) {
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.setFill(color);
        gc.fillOval(x - (radius / 2), (y - radius / 2), radius, radius);
    }

    public void drawLine(double x1, double y1, double x2, double y2) {
        mapCanvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
        //get the slope and find its angle
        double slope;
        if(x1==x2){
            slope=Integer.MAX_VALUE;
        }
        else{
            slope = (y1 - y2) / (x1 - x2);}
        double lineAngle = Math.atan(slope);
        double intercept = y1-slope*x1;
        double arrowAngle = x1 > x2 ? Math.toRadians(45) : -Math.toRadians(215);

        double arrowLength = 10;
        mapCanvas.getGraphicsContext2D().setStroke(Color.RED);
        double arrowX, arrowY;

        if(x1<x2){
            arrowX= 10+x1+(x2-x1)/2;
            arrowY=slope*arrowX+intercept;
        }
        else{
            if(x1>x2){
                arrowX= 10+x2+(x1-x2)/2;
                arrowY=slope*arrowX+intercept;
            }
            else{
                arrowX=x2;
                arrowY=y1>y2?y1+(y2-y1)/2:y2+(y1-y2)/2;
            }}

        mapCanvas.getGraphicsContext2D().strokeLine(arrowX, arrowY, arrowX+arrowLength*Math.cos(lineAngle - arrowAngle),arrowY+arrowLength * Math.sin(lineAngle - arrowAngle));
        mapCanvas.getGraphicsContext2D().strokeLine(arrowX, arrowY, arrowX+arrowLength*Math.cos(lineAngle + arrowAngle),arrowY+arrowLength * Math.sin(lineAngle + arrowAngle));

    }

    public void drawGraph(Graph graph) {
        if (graph == null) {
            return;
        }
        // upDateGraph(graph);
        GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, mapCanvas.getWidth(), mapCanvas.getHeight());
        // store node list so we can use nodes to find edge end points.
        Collection<City> nodeList = graph.getCities().values();

        // draw nodes

        nodeList.forEach(node -> {
                    double size = nodeSize;
                    if (highlightNodes != null && highlightNodes.contains(node)) {
                        gc.setFill(Color.RED); // on path
                        size = nodeSize * 1.5;
                    } else {

                        gc.setFill(Color.ROYALBLUE); // not visited
                    }

                    Point2D screenPoint = Projection.model2Screen(node.getPoint(),this);
                    drawCircle(screenPoint.getX(), screenPoint.getY(), size);
                    // if (nodeNames_ch.isSelected()) {
                    gc.setFont(new Font("Arial", 12));
                    gc.fillText("[" + node.getId() + "]" + node.getName(), screenPoint.getX() + 15,
                        screenPoint.getY() - 10);
                    //}
            });

        //display the start and goal locations

        if(startLocation!=null){
            startText.setText(startLocation.getId());

        }
        if(goalLocation!=null){
            goalText.setText(goalLocation.getId());
        }
        // draw edges
        graph.getOriginalEdges().forEach(edge -> {
                    if(highlightEdges!=null && highlightEdges.contains(edge)){
                        gc.setStroke(Color.RED);
                    }
                    else{
                        gc.setStroke(Color.BLACK);
                    }

                    Point2D startPoint = Projection.model2Screen(edge.fromCity().getPoint(), this);
                    Point2D endPoint = Projection.model2Screen(edge.toCity().getPoint(),this);
                    drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());

                    double textPosX, textPosY, slope, intercept;
                    if(startPoint.getX()==endPoint.getX()){
                        slope=Integer.MAX_VALUE;
                    }
                    //   else
                    {
                        slope = (endPoint.getY() - startPoint.getY()) / (endPoint.getX() - startPoint.getX());}
                    intercept = startPoint.getY()-slope*startPoint.getX();
                    if(startPoint.getX()<endPoint.getX()){
                        textPosX= 10+startPoint.getX()+(endPoint.getX()-startPoint.getX())/2;
                        textPosY=slope*textPosX+intercept;
                    }
                    else{
                        if(startPoint.getX()>endPoint.getX()){
                            textPosX= 10+endPoint.getX()+(startPoint.getX()-endPoint.getX())/2;
                            textPosY=slope*textPosX+intercept;
                        }
                        else{
                            textPosX=10+startPoint.getX();
                            textPosY=startPoint.getY()>endPoint.getY()?startPoint.getY()+(endPoint.getY()-startPoint.getY())/2:endPoint.getY()+(startPoint.getY()-endPoint.getY())/2;

                        }}

                    if (Math.abs(startPoint.getY() - endPoint.getY()) < 20) {   
                        textPosY-=2;
                    }             

                    //comment these lines for Pagerank 
                    //uncomment these lines for networkflow
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font("Arial", 12));
                    gc.fillText(Double.toString(edge.capacity()), textPosX, textPosY);

            });
    }

    //-------------------------------------------------------
    //  methodes to load City and Line data from files
    //-------------------------------------------------------

    /** Load the city data from the node file
     * file contains:
     *     city_id, city_name, location_x,
     *     location_y
     */
    public static Map<String, City> loadCities(File CitiesFile) {
        Map<String, City> cityMap= new HashMap<String, City>();

        try {
            List<String> dataLines = Files.readAllLines(CitiesFile.toPath());
            dataLines.remove(0);// throw away the top line of the file
            for (String dataLine : dataLines){
                // tokenise the dataLine by splitting it on tabs
                String[] tokens = dataLine.split(",");
                if (tokens.length >= 4) {
                    // process the tokens
                    String CityId = tokens[0];
                    String CityName = tokens[1];
                    double x = Double.valueOf(tokens[2]);
                    double y = Double.valueOf(tokens[3]);
                    cityMap.put(CityId, new City(x, y, CityName, CityId));
                }
            }
            System.out.println("Loaded "+ cityMap.size()+" Cities");
        } catch (IOException e) {
            throw new RuntimeException("Reading the Cities file failed.");
        }
        return cityMap;
    }

    /** Load the edge data from the edge file
     * File contains: edge_id, fromCity_Id, toCity_Id, capacity
     * Uses the CityMap to turn the City_id's into Citys
     */
    public static Map<String,Edge> loadLines(File lineFile, Map<String,City> CityMap) {
        if (CityMap.isEmpty()){
            throw new RuntimeException("loadLines given an empty CityMap.");
        }
        Map<String, Edge> edgeMap = new HashMap<String, Edge>();
        try {
            System.out.println("Reading data from: "+lineFile);
            List<String> dataLines = Files.readAllLines(lineFile.toPath());
            dataLines.remove(0); //throw away the top line of the file.

            for (String dataLine : dataLines){// read in each line of the file
                // tokenise the line by splitting it on tabs".
                String[] tokens = dataLine.split(",");
                if (tokens.length >= 4) {
                    // process the tokens
                    String edgeId = tokens[0];
                    String cityId1 = tokens[1];
                    String cityId2 = tokens[2];
                    int capacity = Integer.parseInt(tokens[3]);
                    City city1 = CityMap.get(cityId1);
                    City city2 = CityMap.get(cityId2);
                    if (city1==null || city2 == null){
                        System.out.println("Edge "+edgeId+" has unknown city "+cityId1+" at "+capacity);
                    }
                    else {
                        Edge e = new Edge(city1, city2,"Bus",capacity,0);
                        edgeMap.put(edgeId,e);
                    }
                }
                else {
                    System.out.println("Line file has broken entry: "+dataLine);
                }

            }
            System.out.println("Loaded "+ edgeMap.size()+" edges");
        } catch (IOException e) {throw new RuntimeException("Loading the lines file failed.");}
        return edgeMap;
    }
    // handleShowNetwork flow
    public void handleNetworkFlow(ActionEvent event) {
        System.out.print("Start location "+startLocation);
        System.out.print("Goal location "+goalLocation);
        augmentationPaths = new ArrayList<Pair<ArrayList<String>, Integer>>();
        augmentationPaths = EdmondKarp.calcMaxflows(graph,startLocation,goalLocation);
        reportaugmentationPaths(augmentationPaths);
    }

    public void handlePageRank(ActionEvent event){
        PageRank.computeLinks(graph);
        PageRank.computePageRank(graph);
    }

    /**
     * Constructs a String description of the augmentation path (if there is one)
     * and put it in the lineText text area.

     */
    public void reportaugmentationPaths(ArrayList<Pair<ArrayList<String>, Integer>> aPaths){
        if(aPaths!=null){
            //this is required to find max flow from the flows in each augmentation path
            int sumOfFlows = 0;
            StringBuilder path = new StringBuilder();
            //display each augmentation path
            for (Pair<ArrayList<String>,Integer> aPath : aPaths){
                path.append("AP: ").append(startLocation.getName()). append("[").append(startLocation.getId()).append("]");;

                for (String eID : aPath.getKey()){
                    path.append("->");
                    String nextCity = EdmondKarp.getEdge(eID).toCity().getName();
                    path.append(" ").append(nextCity).
                    append("[");
                    String nextCityID= EdmondKarp.getEdge(eID).toCity().getId();
                    path.append(nextCityID).
                    append("]");
                }
                path.append(" Flow: ").append(aPath.getValue()).append("\n");
                sumOfFlows = sumOfFlows + aPath.getValue();
            }
            //display the max flow
            path.append("Max Flow: ").append(sumOfFlows);
            lineText.setText(path.toString());

        }
        else{
            lineText.setText("No Augmenatation Paths found between "+startLocation.getName() + "[" + startLocation.getId() + "] and " + goalLocation.getName() + "[" + goalLocation.getId() + "]"); 
        }

    }
}

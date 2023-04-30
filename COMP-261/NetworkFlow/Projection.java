import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

// A helper class for projection of the graph to the canvas

public class Projection {


     
    /**
     * Project a node(vertex) to the pixels of the canvas
     * @param model
     * @param controller // this has the canvas scale and origin
     * @return a 2D point on the canvas
     */
    
    // map model to screen using scale and origin
    public static Point2D model2Screen (Point2D model, Controller gc) {
            double x = model2ScreenX(model.getX(), gc.getMapCanvas().getWidth()/2.0, gc.getScale(), gc.getOrigin().getX());
            double y = model2ScreenY(model.getY(), gc.getMapCanvas().getHeight()/2.0, gc.getScale(), gc.getOrigin().getY());
            return new Point2D(x,y);
        }

    public static double model2ScreenX (double modelX, double drawingCenter, double scale, double originX) {
            return (modelX - originX) * scale + drawingCenter; 
        }
        public static double model2ScreenY (double modelY, double drawingCenter, double scale, double originY) {
            return (modelY - originY)* scale + drawingCenter;
        }


        // map screen to model using scale and origin
        public static Point2D screen2Model (Point2D screen, Controller gc) {
            double x = screen2ModelX(screen.getX(), gc.getMapCanvas().getWidth()/2.0, gc.getScale(), gc.getOrigin().getX());
            double y = screen2ModelY(screen.getY(), gc.getMapCanvas().getHeight()/2.0, gc.getScale(), gc.getOrigin().getY());
            return new Point2D(x,y);
        }
        public static double screen2ModelX(double screenPointX, double drawingCenter, double scale, double originX) {
            return (((screenPointX-drawingCenter)/scale) + originX);
        }
        public static double screen2ModelY(double screenPointY, double drawingCenter, double scale, double originY) {
            return (((screenPointY-drawingCenter)/scale) + originY);
        }
  
}

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;

// A helper class for projection of the graph to the canvas

public class Projection {


    /**
     * Project a point from the gis map to the pixels of the canvas
     * @param model
     * @param controller // this has the canvas scale and origin
     * @return a 2D point on the canvas for the Gis point
     */
    public static Point2D model2Screen(GisPoint modelPoint, Controller controller) {
        return new Point2D(
                model2ScreenX(modelPoint.getLon(), controller.getMapCanvas(), controller.getScale(),
                        controller.getRatioLatLon(), controller.getOrigin().getLon()),
                model2ScreenY(modelPoint.getLat(), controller.getMapCanvas(), controller.getScale(),
                        controller.getOrigin().getLat()));
    }

    /**
     * Project a point from the screen pixels to the Gis position
     * @param screenPoint canvas x and y
     * @param controller contains the canvas, scale, and origin
     * @return a GisPoint corresponding to the displayed point
     */
    public static GisPoint screen2Model(Point2D screenPoint, Controller controller) {
        return new GisPoint(
                getScreen2ModelX(screenPoint, controller.getMapCanvas(), controller.getScale(),
                        controller.getRatioLatLon(), controller.getOrigin().getLon()),
                getScreen2ModelY(screenPoint, controller.getMapCanvas(), controller.getScale(),
                        controller.getOrigin().getLat()));
    }





    private static Point2D model2Screen(GisPoint model, Canvas mapCanvas, double scale, double ratioLatLon,
            GisPoint origin) {
        return new Point2D(model2ScreenX(model.getLon(), mapCanvas, scale, ratioLatLon, origin.getLon()),
                model2ScreenY(model.getLat(), mapCanvas, scale, origin.getLat()));
    }

    private static double model2ScreenX(double modelX, Canvas mapCanvas, double scale, double ratioLatLon,
            double originX) {
        return (modelX - originX) * (scale * ratioLatLon) + mapCanvas.getWidth() / 2;
    }

    private static double model2ScreenY(double modelY, Canvas mapCanvas, double scale, double originY) {
        // remember that y is flipped
        return mapCanvas.getHeight() - ((modelY - originY) * scale + mapCanvas.getHeight() / 2);
    }

    // map screen to model using scale and origin
    private static GisPoint Screen2Model(Point2D screenPoint, Canvas mapcanvas, double scale, double ratioLatLon,
            GisPoint origin) {
        return new GisPoint(getScreen2ModelX(screenPoint, mapcanvas, scale, ratioLatLon, origin.getLon()),
                getScreen2ModelY(screenPoint, mapcanvas, scale, origin.getLat()));
    }

    private static double getScreen2ModelX(Point2D screenPoint, Canvas mapcanvas, double scale, double ratioLatLon,
            double originX) {
        return ((screenPoint.getX() - mapcanvas.getWidth() / 2) / (scale * ratioLatLon)) + originX;
    }

    
    private static double getScreen2ModelY(Point2D screenPoint, Canvas mapCanvas, double scale, double originY) {
        // remember that y is flipped
        return (((mapCanvas.getHeight() - screenPoint.getY()) - mapCanvas.getHeight() / 2) / scale) + originY;
    }

}

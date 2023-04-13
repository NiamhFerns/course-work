import java.util.Set; 

/**
 * Constants and methods to represent Transportation data
 */
public class Transport {
    public static final double BUS_SPEED_KPH = 30;
    public static final double BUS_SPEED_MPS = BUS_SPEED_KPH / 3.6;
    public static final double TRAIN_SPEED_KPH = 50;
    public static final double TRAIN_SPEED_MPS = TRAIN_SPEED_KPH / 3.6;
    public static final double WALKING_SPEED_KPH = 5;
    public static final double WALKING_SPEED_MPS = WALKING_SPEED_KPH / 3.6;
    public static final double CABLECAR_SPEED_KPH = 20;
    public static final double CABLECAR_SPEED_MPS = CABLECAR_SPEED_KPH / 3.6;
    public static final double FERRY_SPEED_KPH = 40;
    public static final double FERRY_SPEED_MPS = CABLECAR_SPEED_KPH / 3.6;
    public static final double MAX_WALKING_DISTANCE_M = 100; // 100 meters

    public static final String WALKING = "walking";
    public static final String TRAIN = "train";
    public static final String FERRY = "ferry";
    public static final String BUS = "bus";
    public static final String CABLECAR = "cablecar";
    public static final String TRANSFER = "transfer";

    public static final double TRANSFER_TIME = 600;          // assume 10 minutes to switch between lines at a stop

    private static final Set<String> TRAINS = Set.of("JVL_0", "JVL_1", "KPL_0", "KPL_1", "MEL_0", "MEL_1", "HVL_0", "HVL_1", "WRL_0", "WRL_1");
    private static final Set<String> CABLECARS = Set.of("CCL_0","CCL_1");
    private static final Set<String> FERRIES= Set.of("WHF_0", "WHF_1");


    public static String transpType(String lineID) {
        if (isTrain(lineID)) {return TRAIN;}
        else if (isCableCar(lineID)) {return CABLECAR;}
        else if (isFerry(lineID)) { return FERRY;}
        else if (isWalking(lineID)) { return WALKING;}
        else if (isTransfer(lineID)) { return TRANSFER;}
        else { return BUS;}
    }

    /**
     * Returns if the given lineId is a train line.
     */
    public static boolean isTrain(String lineId) {
        return TRAINS.contains(lineId);
    }
    
    /**
     * Returns if the given lineId is a cable car line.
     */
    public static boolean isCableCar(String lineId) {
        return CABLECARS.contains(lineId);
    }

    /**
     * Returns if the given lineId is a ferry line.
     */
    public static boolean isFerry(String lineID) {
        return FERRIES.contains(lineID);
    }

    /**
     * Returns if the given lineId is a walking connection.
     */
    public static boolean isWalking(String lineID) {
        return (WALKING.equals(lineID));
    }

    /**
     * Returns if the given lineId is a transfer connection.
     */
    public static boolean isTransfer(String lineID) {
        return (TRANSFER.equals(lineID));
    }

    
    /**
     * Return get speed (in meters per second) based on transportation type
     */
    public static double getSpeedMPS(String lineID) {
        return switch (transpType(lineID)) {
            case TRAIN -> TRAIN_SPEED_MPS;
            case CABLECAR -> CABLECAR_SPEED_MPS;
            case FERRY ->  FERRY_SPEED_MPS;
            case WALKING ->  WALKING_SPEED_MPS;
            case BUS ->  BUS_SPEED_MPS;
            default ->  BUS_SPEED_MPS;
        };
    }

}

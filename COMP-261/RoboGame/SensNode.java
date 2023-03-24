import java.util.Scanner;
import java.util.function.Function;

/**
 * Node responsible for representing a sensor on a robot.
 */
record SensNode(Sensor sensor) implements ExprNode {
    /**
     * Internal enum responsible for holding both the name and strategy for some arbitrary sensor.
     */
    @SuppressWarnings({"unused", "SpellCheckingInspection"})
    enum Sensor {
        FUELLEFT("fuelLeft", Robot::getFuel),
        OPPLR("oppLR", Robot::getOpponentLR),
        OPPFB("oppFB", Robot::getOpponentFB),
        NUMBARRELS("numBarrel", Robot::numBarrels),
        BARRELLR("barrelLR", Robot::getClosestBarrelLR),
        BARRELFB("barrelFB", Robot::getClosestBarrelFB),
        WALLDIST("wallDist", Robot::getDistanceToWall);

        private final String action;
        private final Function<Robot, Integer> strategy;

        /**
         * Returns the name of the current sensor that this enum represents.
         */
        String current() {
            return action;
        }

        /**
         * Returns a strategy that accepts a robot and retrieves the current sensor value of that robot.
         */
        Function<Robot, Integer> retrieve() {
            return strategy;
        }

        Sensor(String action, Function<Robot, Integer> strategy) {
            this.action = action;
            this.strategy = strategy;
        }
    }

    /**
     * Parses in and returns a node representing a sensor on a robot.
     */
    static SensNode parse(Scanner s) {
        var action = SensNode.Sensor.valueOf(Parser.require(Parser.SENSOR_PAT, "This is not a valid sensor.", s).toUpperCase());
        return new SensNode(action);
    }

    @Override
    public int asInt(Robot robot) {
        return sensor.retrieve().apply(robot);
    }


    @Override
    public String toString() {
        return sensor.current();
    }
}


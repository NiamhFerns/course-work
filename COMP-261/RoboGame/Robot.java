import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

public class Robot {

    // =======================================================
    // Public methods that can be called by the interpreter:
    // =======================================================

    // Robot Actions
    // -------------

    /**
     * Move forward one step.
     */
    public void move() {
        debug("move");
        currentAction = ACTION_MOVE;
        switch (dir) {
        case NORTH -> { targetY -= 1; }
        case WEST -> { targetX -= 1; }
        case SOUTH -> { targetY += 1; }
        case EAST -> { targetX += 1; }
        }
        targetX = Math.max(0, targetX);
        targetY = Math.max(0, targetY);
        targetX = Math.min(11, targetX);
        targetY = Math.min(11, targetY);
        Robot other = world.getOtherRobot(this);
        if (other != null) {
            if (other.targetX == targetX && other.targetY == targetY) {
                // not allowed to move
                targetX = x;
                targetY = y;
            }
        }
        processFuelAndBlock();
        readState();
    }

    /**
     * Turn 90 degrees to the left
     */
    public void turnLeft() {
        debug("turnL");
        currentAction = ACTION_TURN_LEFT;
        switch (dir) {
        case NORTH -> { targetDir = DIRECTION.WEST;}			
        case WEST -> { targetDir = DIRECTION.SOUTH; }
        case SOUTH -> { targetDir = DIRECTION.EAST; }
        case EAST -> { targetDir = DIRECTION.NORTH; }
        }
        processFuelAndBlock();
        readState();
    }

    /**
     * Turn 90 degrees to the right
     */
    public void turnRight() {
        debug("turnR");
        currentAction = ACTION_TURN_RIGHT;
        switch (dir) {
        case NORTH -> {targetDir = DIRECTION.EAST; }
        case WEST -> {targetDir = DIRECTION.NORTH; }
        case SOUTH -> {targetDir = DIRECTION.WEST; }
        case EAST -> {targetDir = DIRECTION.SOUTH; }
        }
        processFuelAndBlock();
        readState();
    }

    /**
     * Turn 180 degrees.
     */
    public void turnAround() {
        debug("turnAround");
        currentAction = ACTION_TURN_AROUND;
        switch (dir) {
        case NORTH -> {targetDir = DIRECTION.SOUTH; }
        case WEST -> {targetDir = DIRECTION.EAST; }
        case SOUTH -> {targetDir = DIRECTION.NORTH; }
        case EAST -> {targetDir = DIRECTION.WEST; }
        }
        processFuelAndBlock();
        readState();
    }

    /**
     * Set the shield to true or false. When the shield is on, the other robot
     * cannot steal fuel, but this robot will use up fuel faster.
     */
    public void setShield(boolean shield) {
        debug("shield " + (shield ? "on" : "off"));
        this.shield = shield;
        readState();
    }

    /**
     * Take fuel barrel or siphon fuel from other robot.
     */
    public void takeFuel() {
        debug("takeFuel");
        currentAction = ACTION_TAKE_FUEL;
        processFuelAndBlock();
        readState();
    }

    /**
     * Do nothing and wait.
     */
    public void idleWait() {
        debug("wait");
        currentAction = ACTION_WAIT;
        processFuelAndBlock();
        readState();
    }

    // -------------
    // Robot Sensors
    // -------------

    /**
     * Distance to the wall directly in front of the robot relative to its
     * current orientation.
     * 
     * @return
     */
    public int getDistanceToWall() {
        if (cancelled)
            throw new RobotInterruptedException();
        switch (dir) {
        case NORTH -> {return y;}
        case SOUTH -> {return 11 - y;}
        case WEST -> {return x;}
        case EAST -> {return 11 - x;}
        }
        return 0;
    }

    /**
     * Gets the left-right-location of the other robot relative to the current
     * position and orientation.
     * 
     * @return INFINITY if there isn't a second robot, -ve if to the left, +ve
     *         if to the right and 0 if directly in front or behind
     */
    public int getOpponentLR() {
        if (cancelled)
            throw new RobotInterruptedException();
        if (otherRobotPos == null) {
            debug("oppLR=INFINTY");
            return INFINITY;
        } else {
            debug("oppLR=" + otherRobotPos.x);
            return otherRobotPos.x;
        }
    }

    /**
     * Gets the front-back-location of the other robot relative to the current
     * position and orientation.
     * 
     * @return INFINITY if there isn't a second robot, +ve if in front, -ve if
     *         behind and 0 if directly to the left or right.
     */
    public int getOpponentFB() {
        if (cancelled)
            throw new RobotInterruptedException();
        if (otherRobotPos == null) {
            debug("oppFB=INFINTY");
            return INFINITY;
        } else {
            debug("oppFB=" + otherRobotPos.y);
            return otherRobotPos.y;
        }
    }

    /**
     * @return The number of barrels currently in the world
     */
    public int numBarrels() {
        if (cancelled)
            throw new RobotInterruptedException();
        debug("numBarrels = " + barrels.size());
        return barrels.size();
    }

    /**
     * Left-right-location of the closest fuel barrel relative to the current
     * position and orientation.
     * 
     * @return INFINTY if there are no barrels, -ve if to the left, +ve if to
     *         the right and 0 if directly in front or behind
     */
    public int getClosestBarrelLR() {
        if (cancelled)
            throw new RobotInterruptedException();
        return getBarrelLR(0);
    }

    /**
     * Front-back-location of the closest fuel barrel relative to the current
     * position and orientation.
     * 
     * @return INFINTY if there are no barrels, +ve if in front, -ve if behind
     *         and 0 if directly to the left or right.
     */
    public int getClosestBarrelFB() {
        if (cancelled)
            throw new RobotInterruptedException();
        return getBarrelFB(0);
    }

    /**
     * Left-right-location of the nth fuel barrel relative to the current
     * position and orientation.
     * 
     * @return INFINTY if there are less than n barrels, -ve if to the left, +ve
     *         if to the right and 0 if directly in front or behind
     */
    public int getBarrelLR(int n) {
        if (cancelled)
            throw new RobotInterruptedException();
        int val = (n >= barrels.size()) ? INFINITY : barrels.get(n).x;
        debug(((n == 0) ? "" : (n + "th")) + "closestBarrelLR = " + ((val == INFINITY) ? "INFINITY" : "" + val));
        return val;
    }

    /**
     * Front-back-location of the nth fuel barrel relative to the current
     * position and orientation.
     * 
     * @return INFINITY if there are less than n barrels, +ve if in front, -ve
     *         if behind and 0 if directly to the left or right.
     */
    public int getBarrelFB(int n) {
        if (cancelled)
            throw new RobotInterruptedException();
        int val = (n >= barrels.size()) ? INFINITY : barrels.get(n).y;
        debug(((n == 0) ? "" : (n + "th")) + "closestBarrelFB = " + ((val == INFINITY) ? "INFINITY" : "" + val));
        return val;
    }

    /**
     * Gets the amount of fuel this robot has remaining.
     * 
     * @return
     */
    public int getFuel() {
        if (cancelled)
            throw new RobotInterruptedException();
        debug("fuel=" + fuel);
        return fuel;
    }

    /**
     * @return boolean stating whether the shield is currently on.
     */
    public boolean isShieldOn() {
        if (cancelled)
            throw new RobotInterruptedException();
        debug(shield ? "shield is on" : "shield is off");
        return shield;
    }

    // =======================================================
    // The internal workings of the robot, not needed by the parser or
    // interpreter.
    // =======================================================

    public static final int
        ACTION_MOVE = 1, ACTION_TURN_LEFT = 2, ACTION_TURN_RIGHT = 3,
        ACTION_TURN_AROUND = 4, ACTION_TAKE_FUEL = 5, ACTION_WAIT = 6,
        INFINITY = Integer.MAX_VALUE;  

    private static final int
        FUEL_IDLE = 3, FUEL_MOVE = 6, FUEL_TURN = 5, FUEL_SHIELD = 15;

    private enum DIRECTION { NORTH, WEST, SOUTH, EAST }

    private World world;
    private Point otherRobotPos;
    private List<Point> barrels = new ArrayList<Point>();
    private ProgramNode program;

    private boolean shield, dead, finished, cancelled;
    private int fuel = 100, targetFuel;
    private int x, y, targetX, targetY;
    private DIRECTION dir, targetDir;
    //	private int x, y, dir, targetX, targetY, targetDir;
    private int currentAction;
    private String colour;
    private BufferedImage robotImage1, robotImage2, shieldImage;
    private boolean noWait = false;

    public Robot(World world, int x, int y, String colour, boolean noWait) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.noWait = noWait;
        targetX = x;
        targetY = y;
        dir = (y < World.SIZE / 2) ? DIRECTION.SOUTH : DIRECTION.NORTH;
        targetDir = dir;
        targetFuel = fuel;
        try {
            robotImage1 = ImageIO.read(new File(RoboGame.ASSET_DIRECTORY+"robot_" + colour + "_1.png"));
            robotImage2 = ImageIO.read(new File(RoboGame.ASSET_DIRECTORY+"robot_" + colour + "_2.png"));
            shieldImage = ImageIO.read(new File(RoboGame.ASSET_DIRECTORY+ "shield.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> history = new ArrayList<>();

    /**
     * Set the program of this robot, if the controller represents is a valid
     * program
     */
    public void setProgram(ProgramNode prog) {
        program = prog;
    }

    /**
     * Start this robot running. If it has a valid program,, then executes the
     * program, passing in the robot. Otherwise, calls the default program.
     */
    public void run() {
        if (program != null) {
            program.execute(this);
        } else {
            defaultProgram();
        }
    }

    /**
     * Unblocks execution of this robot and hopefully completes its execution.
     * Don't call this yourself.
     */
    public void cancel() {
        cancelled = true;
        updatePending();
        fuel = 0;
        targetFuel = 0;
    }

    /**
     * Called when execution of a robot's code completes. You do not need to do
     * this yourself.
     * 
     * @param finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
        if (finished)
            updatePending();
    }

    /**
     * Called by the GUI only.
     * 
     * @param g2d
     * @param time
     */
    public void draw(Graphics2D g2d, double time) {
        double x = this.x * (1 - time) + targetX * time;
        double y = this.y * (1 - time) + targetY * time;
        x = x * WorldComponent.GRID_SIZE + WorldComponent.GRID_SIZE / 2d;
        y = y * WorldComponent.GRID_SIZE + WorldComponent.GRID_SIZE / 2d;

        double angle = getAngle(dir);
        switch (currentAction) {
        case ACTION_TURN_LEFT -> { angle -= Math.toRadians(90) * time; }
        case ACTION_TURN_RIGHT -> { angle += Math.toRadians(90) * time; }
        case ACTION_TURN_AROUND -> { angle += Math.toRadians(180) * time; }
        }

        AffineTransform trans = new AffineTransform();
        trans.translate(x, y);
        trans.rotate(angle);
        trans.translate(-robotImage1.getWidth() / 2d, -robotImage1.getHeight() / 2d);

        if (((int) (time * 10)) % 2 == 0 && currentAction < ACTION_TAKE_FUEL && currentAction > 0) {
            g2d.drawImage(robotImage2, trans, null);
        } else {
            g2d.drawImage(robotImage1, trans, null);
        }

        if (shield) {
            trans = new AffineTransform();
            trans.translate(x - 25, y - 25);
            g2d.drawImage(shieldImage, trans, null);
        }

        double fuelCurrent = targetFuel * time + fuel * (1 - time);
        if (fuelCurrent <= 0)
            dead = true;

        // draw fuel indicator
        Arc2D fuelArc = new Arc2D.Double(x - 10, y - 10, 20, 20, -90, 360d * fuelCurrent / 100d, Arc2D.OPEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.GREEN);
        g2d.draw(fuelArc);
    }

    public boolean isDead() {
        return dead;
    }

    private Point getOtherRobotPosition() {
        Robot other = world.getOtherRobot(this);
        return (other != null) ? toRelative(new Point(other.x, other.y)) : null;
    }

    private Point toRelative(Point p) {
        int rx = p.x;
        int ry = p.y;
        switch (dir) {
        case NORTH -> { return new Point(rx - x, y - ry); }
        case SOUTH -> { return new Point(x - rx, ry - y);}
        case WEST -> { return new Point(y - ry, x - rx); }
        case EAST -> { return new Point(ry - y, rx - x); }
        }
        return null;
    }

    private double getAngle(DIRECTION direction) {
        switch (direction) {
        case NORTH -> {return 0; }
        case SOUTH -> {return Math.toRadians(180);}
        case EAST  -> {return Math.toRadians(90);}
        case WEST  -> {return Math.toRadians(270);}
        }
        return 0;
    }

    private void processFuel() {
        // use fuel
        targetFuel = fuel;
        switch (currentAction) {
        case ACTION_MOVE ->        { targetFuel -= FUEL_MOVE; }
        case ACTION_TURN_LEFT->    { targetFuel -= FUEL_TURN; }
        case ACTION_TURN_RIGHT ->  { targetFuel -= FUEL_TURN; }
        case ACTION_TURN_AROUND -> { targetFuel -= FUEL_TURN; }
        case ACTION_WAIT ->        { targetFuel -= FUEL_IDLE; }
        case ACTION_TAKE_FUEL ->   {
            if (world.getAvailableFuel().contains(new Point(x, y))) {
                world.getAvailableFuel().remove(new Point(x, y));
                targetFuel = 100;
                return;
            } else {// try to siphon fuel
                Robot other = world.getOtherRobot(this);
                if (other != null && !other.shield) {
                    Point otherP = getOtherRobotPosition(); // relative position
                    if (otherP.x == 0 && otherP.y == 1) {
                        System.out.println("Taking fuel");
                        int takeFuel = Math.min(25, other.targetFuel / 2);
                        takeFuel = Math.min(other.targetFuel, takeFuel);
                        targetFuel = Math.min(100, targetFuel + takeFuel);
                        other.targetFuel -= takeFuel;
                        return;
                    }
                }
            }
            targetFuel -= FUEL_IDLE; }
        }
        if (shield) targetFuel -= FUEL_SHIELD;
    }

    private void processFuelAndBlock() {
        if (cancelled)
            throw new RobotInterruptedException();
        // use fuel
        processFuel();
        if (this.noWait) {
            updatePending();
        } else {
            while (currentAction != 0) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
     * Called by the "World" only.
     */
    public void updatePending() {
        // reset turn
        x = targetX;
        y = targetY;
        dir = targetDir;
        fuel = targetFuel;
        if (finished) {
            currentAction = ACTION_WAIT;
            processFuel();
        }
        otherRobotPos = getOtherRobotPosition();
        barrels = new ArrayList<Point>(world.getAvailableFuel());
        for (int i = 0; i < barrels.size(); i++) {
            Point p = toRelative(barrels.get(i));
            barrels.set(i, p);
        }
        Collections.sort(barrels, new Comparator<Point>() {

                @Override
                public int compare(Point p1, Point p2) {
                    int d1 = Math.abs(p1.x) + Math.abs(p1.y);
                    int d2 = Math.abs(p2.x) + Math.abs(p2.y);
                    return d1 - d2;
                }
            });
        currentAction = 0;
    }

    public String toString() {
        return "the " + colour + " robot @(" + x + "," + y + ")";
    }

    /**
     * print a message about the action to System.out if debugging is currently
     * turned on.
     */
    private void debug(String action) {
        //		if (RoboGame.debugDisplay && program != null) {
        System.out.println(colour + " robot: " + action);
        //}
    }

    /**
     * What the robot will do if it has no valid controller
     */
    private void defaultProgram() {
        // dummy program
        while (getFuel() > 0) {
            if (numBarrels() == 0) {
                idleWait();
            } else {
                int x = getClosestBarrelLR();
                int y = getClosestBarrelFB();
                if (x == 0 && y == 0) {
                    takeFuel();
                } else {
                    if (y == 0) {
                        if (x < 0)
                            turnLeft();
                        else
                            turnRight();
                    } else if (y > 0) {
                        move();
                    } else {
                        turnAround();
                    }
                }
            }
        }
    }

    /**
     * Encodes the state of the robot, used for automarking.
     */
    public void readState() {
        String state = String.format("%-4s @(%2d,%2d) dir:%-5s fuel:%3d %s",
                                     colour, x, y, dir, fuel, shield?"shielded":"unshielded");
        history.add(state);
        //System.out.println(state);
    }



}

import java.util.*;

public class TesterRobot extends Robot {

    // =======================================================
    // Public methods that can be called by the interpreter:
    // =======================================================

    // Robot Actions
    // -------------

    private List<String> output = new ArrayList<String>();
    private int wallDist = 0;
    private int fuel = 5;
    private boolean shield = false;
    private int count = 20;  // maximum number of actions to perform

    //Actions
    public void move() {check(); output.add("move");}
    public void turnLeft() {check(); output.add("turnL");}
    public void turnRight() {check(); output.add("turnR");}
    public void turnAround() {check(); output.add("turnAround");}
    public void setShield(boolean shield) {check(); output.add("shield" + (shield ? "On" : "Off"));}
    public void takeFuel() {check(); output.add("takeFuel");}
    public void idleWait() {check(); output.add("wait");}

    public int getFuel() {output.add("getFuelLeft");  return fuel--;}
    public int getDistanceToWall() { output.add("getWallDist"); return wallDist++;}

    public int getOpponentLR() { output.add("getOppLR");  return 3;}
    public int getOpponentFB() { output.add("getOppFB");  return 3;}

    public int numBarrels() { output.add("getNumBarrels");  return 4;}
    public int getClosestBarrelLR() {output.add("getBarrelLR");  return 4;}
    public int getClosestBarrelFB() {output.add("getBarrelFB");  return 4;}
    public int getBarrelLR(int n) {output.add("getBarrelLR("+n+")"); return 4+n;}
    public int getBarrelFB(int n) {output.add("getBarrelFB("+n+")"); return 4+n;}

    public boolean isShieldOn() {output.add("getSheildOn"); shield = !shield; return !shield;}


    public TesterRobot(){
        super(null, 0, 0, "red", false);
    }
    
    public void check(){
        if (count-- <= 0){
            throw new RobotInterruptedException();
        }
    }

    public List<String> getOutput() {return output;}

}

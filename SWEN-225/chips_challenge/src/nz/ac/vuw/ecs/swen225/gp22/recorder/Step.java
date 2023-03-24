package nz.ac.vuw.ecs.swen225.gp22.recorder;


/**
 * When Recording, 'Steps' are created to contain the direction Chip moved in and Time the move occurred.
 *
 * @author Santino Gaeta 300305101
 */
public class Step{

    private String move;
    private long time;

    /**
     * Default Constructor used for XmlConversion using Jackson
     *
     * @author Santino Gaeta
     */
    public Step(){}

    /**
     * Constructor used when Chip moves during a Level being recorded
     * @param move - Direction Chip moved
     * @param time - Time remaining in level when Chip executed this move
     *
     * @author Santino Gaeta
     */
    public Step(String move, long time){
        this.move = move;
        this.time = time;
    }

    /**
     * Returns move of the Step
     * @return move Chip made contained in this Step
     *
     * @author Santino Gaeta
     */
    public String getMove(){
        return move;
    }

    /**
     * Returns time of the Step
     * @return time Chip made move contained in this Step
     *
     * @author Santino Gaeta
     */
    public long getTime(){
        return time;
    }

    /**
     * Sets the move of the Step
     *
     * @author Santino Gaeta
     */
    public void setMove(String move) {
        this.move = move;
    }

    /**
     * Sets the time of the Step
     *
     * @author Santino Gaeta
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Returns String representation of the contents in Step
     * @return String - String will display move and time move occurred by Chips of this Step
     *
     * @author Santino Gaeta
     */
    public String toString(){
        return getMove()+" "+getTime();
    }
}

package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.*;

/**
 * Represents a clock singleton that puts out ticks at an arbitrary time interval. You can register observers to this
 * clock to be updated every tick cycle.
 * Intended to work with chips challenge so also holds the current time played for the game and level.
 *
 * @author niamh
 */
public class GameClock extends Subject {
    private static GameClock clock;
    private Timer timer;
    public static final int DEFAULT_FRAMERATE = 60;
    private static int FRAMERATE;
    private static int TICKRATE;

    private long tickCount;
    private long levelTickCount;
    private long timePlayed;
    private long timeRemaining;
    private boolean paused;

    /**
     * Retrieve the instance of GameClock or instantiate it if there isn't one.
     * @return the instance of GameClock
     *
     * @author niamh
     */
    public static GameClock get() {
        if (clock == null) {
            clock = new GameClock();
        }
        return clock;
    }

    /**
     * Responsible for resetting and validating the timer on a change of tickrate. This will refresh it to begin ticking
     * at the correct rate.
     *
     * @author niamh
     */
    private void validateClock() {
        timer.stop();
        timer = new Timer(TICKRATE, _e -> {
                tickIncrement();
                update();
            });
    }


    /**
     * Resets the tickrate based on the users desired framerate.
     * @param frameRate this is the desired frameRate that the user has set.
     *
     * @author niamh
     */
    public static void setTickRate(int frameRate) {
        FRAMERATE = frameRate;
        TICKRATE = 1000 / FRAMERATE;
        if (clock != null) get().validateClock();
    }

    /**
     * Returns the framerate that the clock is currently running at.
     * @return the current framerate of the clock.
     *
     * @author niamh
     */
    public static int getFrameRate() {
        return FRAMERATE;
    }

    /**
     * Retrieves the current total tick count since the clock has started.
     * @return a long representing the tick count.
     *
     * @author niamh
     */
    public long currentTick()      { return tickCount; }

    /**
     * Retrieves the current total time elapsed since the clock has started.
     * @return a long representing the elapsed time in milliseconds.
     *
     * @author niamh
     */
    public long currentTime()      { return timePlayed; }

    /**
     * Retrieves the tick count since the current level has started.
     * @return a long representing the tick count.
     *
     * @author niamh
     */
    public long currentLevelTick() { return levelTickCount; }

    /**
     * Retrieves the current total time remaining in a level.
     * @return a long representing the remaining time in milliseconds.
     *
     * @author niamh
     */
    public long currentLevelTime() { return timeRemaining; }

    /**
     * Sets the time that the user has to complete a level.
     * @param time a long representing the time in milliseconds.
     *
     * @author niamh
     */
    protected void setLevelTime(long time) {
        this.timeRemaining = time;
    }

    /**
     * Resets the level tick counter to zero on starting a new level.
     *
     * @author niamh
     */
    protected  void resetLevelTick() { levelTickCount = 0L; }

    /**
     * Increments both tick count variables by one and updates the time fields to match based on the length of a tick
     * on an update of the timer.
     *
     * @author niamh
     */
    private  void tickIncrement() {
        levelTickCount++;
        tickCount++;
        timeRemaining -= Math.min(16, timeRemaining);
    }

    /**
     * Starts the clock.
     *
     * @author niamh
     */
    protected void start() {
            timer = new Timer(TICKRATE, _e -> {
                tickIncrement();
                update();
            });
            timer.start();
    }

    /**
     * Pauses the clock and any tick updates that are sent out to observers.
     *
     * @author niamh
     */
    public static void pause() {
        if (clock.paused) return;
        clock.paused = true;
        clock.timer.stop();
    }

    /**
     * Unpauses the clock and resumes ticking to update the observers.
     *
     * @author niamh
     */
    public static void unpause() {
        if (!clock.paused) return;
        clock.paused = false;
        clock.timer.start();
    }

    /**
     * Check what state the clock currently is.
     * @return true if the clock is paused, false otherwise.
     */
    public static boolean isPaused() { return clock.paused; }


    /**
     * Stops the clock.
     *
     * @author niamh
     */
    protected void stop() {
        timer.stop();
    }

    /**
     * Resets the GameClock to it state to simulate restarting the program.
     * THIS IS A DANGEROUS OPERATION. USE WITH CAUTION.
     *
     * @author niamh
     */
    protected static void reset() {
        clock = new GameClock();
    }

    /**
     * Returns whether the clock is running.
     *
     * @return true if running, false if not running.
     *
     * @author niamh
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * Private constructor to instantiate a GameClock if the get() method determines that one does not yet exist.
     *
     * @author niamh
     */
    private GameClock() {
        tickCount = 0;
        levelTickCount = 0;
        timePlayed = 0;
        timeRemaining = 0;
        GameClock.setTickRate(DEFAULT_FRAMERATE);
        paused = false;
    }
}

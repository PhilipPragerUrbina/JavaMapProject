package MegaMaps.Utils;

/**
 * Simple benchmark timer
 */
public class Timer {
    private long start_time;

    /**
     * Create and start the timer
     */
    public Timer(){
        start_time = System.currentTimeMillis();
    }

    /**
     * Stop the timer
     * @return The duration of the timer in milliseconds
     */
    public long end(){
        long end_time = System.currentTimeMillis();
        return end_time-start_time;
    }

}

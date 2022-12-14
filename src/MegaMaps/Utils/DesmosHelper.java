package MegaMaps.Utils;

/**
 * A helper for getting Desmos data
 */
public class DesmosHelper {
    /**
     * Lambda expression for plotting values from some source
     */
    public interface plotFunction{
        /**
         * Get the Y value from an X value
         * @return
         */
        double getNextY(double x);
    }

    private plotFunction function;

    /**
     * Create a Desmos helper to plot values
     * @param to_plot What to plot
     */
    public DesmosHelper(plotFunction to_plot){
        function = to_plot;
    }

    /**
     * Compute the values and plot
     * @param min x value to start at(inclusive)
     * @param max x value to end at(exclusive)
     * @param step step in between x values
     * @return The desmos csv string
     * Make sure to Reset your stuff between plots!
     */
    public String plot(double min, double max, double step){
        String out = "";
        for (double x = min; x < max; x+=step) {
            out += x +","+ function.getNextY(x) + "\n";
        }
        return out;
    }

}

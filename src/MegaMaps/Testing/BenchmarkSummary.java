package MegaMaps.Testing;


import MegaMaps.Testing.Benchmarks.Benchmark;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;

//get statistics from scores, such as standard deviation and such. Run test multiple times(Student T test)
//get comparisons from two statistics

/**
 * Get statistics from benchmark scores. Runs a benchmark multiple times to get results.
 */
public class BenchmarkSummary {
    private IntSummaryStatistics statistics = new IntSummaryStatistics(); //Utility for calculating statistics
    private ArrayList<Integer> raw_values = new ArrayList<>();
    private Benchmark benchmark;

    /**
     * Run a benchmark n times and gather data. Resetting in between.
     * @param benchmark The benchmark to run
     * @param count # of times to run benchmark
     */
    public BenchmarkSummary(Benchmark benchmark, int count){
        this.benchmark = benchmark;
        for (int i = 0; i < count; i++) { //run the trials
            benchmark.reset(); //reset the map beforehand
            int result = benchmark.run();  //run the benchmark, and record the result
            statistics.accept(result);
            raw_values.add(result);
        }
    }


    /**
     * Get a summary of the stats
     * @return
     */
    @Override
    public String toString() {
        String out = benchmark.getDescription()+"\n";
        out += "Trials: " + statistics.getCount() + "\n";
        out += "Average: " + statistics.getAverage() + "\n";
        out += "Max: " + statistics.getMax() + "\n";
        out += "Min: " + statistics.getMin() + "\n";
        out += "SD: " + getStandardDeviation() + "\n";
        return out;
    }

    /**
     * Get a String summary comparing two statistics
     * @param other
     */
    public String comparison(BenchmarkSummary other){
        String out = benchmark.getDescription() + " compared to " + other.benchmark.getDescription() + "\n";
        out += "Percent difference of A(new value) compared to B(old value) \n";
        out += "Trials: " + percentImprovement(statistics.getCount(),other.statistics.getCount()) + "\n";
        out += "Average: " + percentImprovement(statistics.getAverage(),other.statistics.getAverage()) + "\n";
        out += "Max: " + percentImprovement(statistics.getMax(),other.statistics.getMax()) + "\n";
        out += "Min: " + percentImprovement(statistics.getMin(),other.statistics.getMin())+ "\n";
        out += "SD: " + percentImprovement(getStandardDeviation(),other.getStandardDeviation()) + "\n";
        return out;
    }

    /**
     * Get the percent increase or decrease
     * @param final_value New value
     * @param starting_value Old value
     * @return Percent change: eg: -50%
     */
    private double percentImprovement(double final_value, double starting_value){
        return (final_value-starting_value) / Math.abs(starting_value) * 100.0;
    }

    /**
     * Not included in IntSummaryStatistics
     * @return SD
     */
    private double getStandardDeviation(){
        double standard_deviation = 0;
        double mean = statistics.getAverage();
        for (int i = 0; i < raw_values.size(); i++) {
            standard_deviation +=  Math.pow(((double)raw_values.get(i) - mean), 2);

        }
        return Math.sqrt(standard_deviation / (double) raw_values.size());
    }
}

package MegaMaps.Testing.Benchmarks;

import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;

/**
 * A benchmark for testing the speed of different operations
 * Works on any set of data, with any map
 * @param <KeyType>
 * @param <ValueType>
 */
public abstract class Benchmark<KeyType,ValueType> {
    //The test case
    protected Dataset<KeyType, ValueType> data;
    protected Map<KeyType, ValueType> map;

    /**
     * Create a Benchmark
     * @param dataset The data to use
     * @param map The map to test on
     */
   public Benchmark(Dataset<KeyType, ValueType> dataset, Map<KeyType, ValueType> map){
        this.map = map;
        this.data = dataset;
    };

    /**
     * Run the benchmark
     * @return Execution time in milliseconds
     */
    public abstract int run();

    /**
     * Reset the map in the benchmark
     */
    public void reset(){
        map.reset();
    }

    /**
     * Get a simple description of what the benchmark is
     * @return A one line name with what the benchmark, and it's inputs are
     */
    public String getDescription() {
        return this.getClass().toString() + " with " + data.getClass().toString() + " on " + map.getClass().toString();
    }
}







//make sure to run benchmarks that test empty things, such as looking for something that doesn't exist

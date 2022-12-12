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
    private Dataset<KeyType, ValueType> data;
    private Map<KeyType, ValueType> map;

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
    public abstract double run();

}







//make sure to run benchmarks that test empty things, such as looking for something that doesn't exist

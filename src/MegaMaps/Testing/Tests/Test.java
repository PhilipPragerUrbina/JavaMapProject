package MegaMaps.Testing.Tests;

import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;

/**
 * A unit test for maps
 * Works on any set of data, with any map
 * @param <KeyType>
 * @param <ValueType>
 */
public abstract class Test<KeyType,ValueType> {
    //The test case
    private Dataset<KeyType, ValueType> data;
    private Map<KeyType, ValueType> map;

    /**
     * Create a test
     * @param dataset The data to use
     * @param map The map to test on
     */
    public Test(Dataset<KeyType, ValueType> dataset, Map<KeyType, ValueType> map){
        this.map = map;
        this.data = dataset;
    };

    /**
     * Run the test
     * @return Null if test pass, description if test fail
     */
    abstract public String run();
}




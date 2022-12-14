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
    protected Dataset<KeyType, ValueType> data;
    protected Map<KeyType, ValueType> map;

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
     * @return Null if test pass, error message if test fail
     */
    abstract public String run();

    /**
     * Reset the map in the test
     */
    public void reset(){
        map.reset();
    }

    /**
     * Get a formatted test failed message to return
     * @param what The assertion that failed
     * @return The formatted message
     */
    protected String getErrorMessage(String what){
        return this.getClass().toString() + " with " + data.getClass().toString() + " on " + map.getClass().toString() + "\n" + "TEST FAILED: " + what + "\n";
    }
}







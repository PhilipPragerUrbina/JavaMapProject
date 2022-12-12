package MegaMaps.Testing;

import MegaMaps.Map;
import MegaMaps.Utils.Timer;

import java.util.Random;

/**
 * Simple benchmark and integration test
 *  Pairs random string ints with random ints
 */
public class SimpleBenchMark {
    private Map<String ,Integer> map;
    private Random random = new Random(0);

    /**
     * Create a new benchmark for a map
     * @param map
     */
    public SimpleBenchMark(Map<String ,Integer> map){
        this.map = map;
    }

    /**
     * Run the benchmark
     * @param n How many entries
     * @throws Exception Problem with hash map
     * @return Execution Time in milliseconds
     */
    public long run(int n) throws Exception {
        Timer timer = new Timer();
        for (int i = 0; i < n; i++) {
            //gen values
            String key = String.valueOf(random.nextInt());
            Integer value = random.nextInt();
            //fill map with data
            map.put(key, value);
            //check
            //todo separate testing and benchmarking. Beware of the compiler
            Integer value_get = map.get(key);
            if(!value_get.equals(value)){
                throw new Exception("Test failed: " + value_get + " actual: " + value);
            }
        }
        return timer.end();
    }
}

package MegaMaps.Testing.Benchmarks;

import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;
import MegaMaps.Utils.Timer;

/**
 * Simple read benchmark. Write all dataset pairs into map, and record time it takes to read
 */
public class AccessBenchmark<KeyType,ValueType> extends Benchmark<KeyType,ValueType> {

    public AccessBenchmark(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }


    @Override
    public int run() {

        for (int i = 0; i < data.size(); i++) { //go through dataset
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert
        }
        Timer timer = new Timer();
        for (int i = 0; i < data.size(); i++) { //go through dataset
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            ValueType get = map.get(entry.key); //read
            if(!get.equals(entry.value)){
                System.out.println("Not equal"); //run a check to make sure it is not optimized away
            }
        }
        return (int)timer.end();
    }
}

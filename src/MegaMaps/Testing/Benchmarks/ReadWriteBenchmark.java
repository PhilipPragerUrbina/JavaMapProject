package MegaMaps.Testing.Benchmarks;

import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;
import MegaMaps.Utils.Timer;

/**
 * Combination of access and insert benchmarks.
 */
public class ReadWriteBenchmark<KeyType,ValueType> extends Benchmark<KeyType,ValueType> {

    public ReadWriteBenchmark(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }


    @Override
    public int run() {
        Timer timer = new Timer();
        for (int i = 0; i < data.size(); i++) { //go through dataset
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert
        }
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

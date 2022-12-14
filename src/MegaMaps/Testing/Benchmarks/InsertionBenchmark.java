package MegaMaps.Testing.Benchmarks;

import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;
import MegaMaps.Utils.Timer;

/**
 * Simple write benchmark. Write all dataset pairs into map
 */
public class InsertionBenchmark<KeyType,ValueType> extends Benchmark<KeyType,ValueType> {

    public InsertionBenchmark(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }


    @Override
    public int run() {
        Timer timer = new Timer();
        for (int i = 0; i < data.size(); i++) { //go through dataset
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert
        }
        return (int)timer.end();
    }
}

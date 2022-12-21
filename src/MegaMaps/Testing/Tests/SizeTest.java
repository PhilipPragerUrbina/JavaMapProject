package MegaMaps.Testing.Tests;


import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;

import java.util.HashMap;

/**
 * Test the size method
 */
public class SizeTest<KeyType,ValueType> extends Test<KeyType,ValueType> {

    public SizeTest(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }

    @Override
    public String run() {

        //insert data
        for (int i = 0; i < data.size(); i++) {
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert

        }

        final int removed = 10;
        //test removal
        for (int i = 0; i < removed; i++) {
            map.remove(data.getEntry(i).key);
        }

        if(map.size() != data.size()-removed){ //correct for removed elements
            return "Did not get correct size";
        }

        return null;
    }
}

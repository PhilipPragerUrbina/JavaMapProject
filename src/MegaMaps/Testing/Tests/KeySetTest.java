package MegaMaps.Testing.Tests;


import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;

import java.util.HashMap;

/**
 * A test that checks if the map can be iterated
 */
public class KeySetTest<KeyType,ValueType> extends Test<KeyType,ValueType> {

    public KeySetTest(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }

    @Override
    public String run() {
        HashMap<KeyType,ValueType> reference = new HashMap<>(); //reference

        //insert data
        for (int i = 0; i < data.size(); i++) {
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert

        }

        final int removed = 10; //num to remove

        //test removal
        for (int i = 0; i < removed; i++) {
            map.remove(data.getEntry(i).key);
        }

        int count = 0;

        //iterate data
        for (Pair<KeyType,ValueType> pair: map) {
            count++;
            if(reference.containsKey(pair.key)){
                return "Duplicate iteration";
            }
            reference.put(pair.key,pair.value);
        }
        if(count != data.size()-removed){
            return "Did not iterate over all data";
        }

        return null;
    }
}

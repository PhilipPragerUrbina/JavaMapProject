package MegaMaps.Testing.Tests;


import MegaMaps.Map;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Utils.Pair;
import MegaMaps.Utils.Timer;

/**
 * A test that write and then reads values to a map, and checks for correctness
 */
public class InsertReadTest<KeyType,ValueType> extends Test<KeyType,ValueType> {

    public InsertReadTest(Dataset<KeyType,ValueType> dataset, Map<KeyType,ValueType> map) {
        super(dataset, map);
    }

    @Override
    public String run() {
        //insert data
        for (int i = 0; i < data.size(); i++) {
            Pair<KeyType,ValueType> entry = data.getEntry(i); //get data
            map.put(entry.key,entry.value); //insert
        }

        //read data
        for (int i = 0; i < data.size(); i++) {
            Pair<KeyType,ValueType> correct_entry = data.getEntry(i);  //get expected data
            ValueType value = map.get(correct_entry.key);
            //check
            if(value == null || !value.equals(correct_entry.value)){
                return getErrorMessage("Write does not match read, expected:" + correct_entry + " got " + value);
            }
        }
        return null;
    }
}

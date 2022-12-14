package MegaMaps.Maps;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A naive loop based dictionary. Very slow O(n)
 * @param <KeyType>
 * @param <ValueType>
 */
public class LoopMap<KeyType, ValueType> implements Map<KeyType,ValueType> {
    //entries
    private ArrayList<KeyType> keys = new ArrayList<KeyType>();
    private ArrayList<ValueType>values  = new ArrayList<ValueType>();

    @Override
    public ValueType get(KeyType key) {
        for (int i = 0; i < keys.size(); i++) { //search
            if(key.equals(keys.get(i))){
                return values.get(i); //value found
            }
        }
        return  null; //none found
    }

    @Override
    public void put(KeyType key, ValueType value) {
        for (int i = 0; i < keys.size(); i++) { //search
            if(key.equals(keys.get(i))){
               values.set(i,value); //overwrite
                return;
            }
        }
        //not found, add new entry
        keys.add(key);
        values.add(value);
    }

    @Override
    public void remove(KeyType key) {
        for (int i = 0; i < keys.size(); i++) { //search
            if(key.equals(keys.get(i))){
                //remove
                keys.remove(i);
                values.remove(i);
                return;
            }
        }
    }

    @Override
    public void reset() {
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    //Iterate over key-value pairs
    @Override
    public Iterator<Pair<KeyType, ValueType>> iterator() {
        return new Iterator<Pair<KeyType, ValueType>>() {
            int idx = 0;
            @Override
            public boolean hasNext() {
                return idx < size();
            }

            @Override
            public Pair<KeyType, ValueType> next() {
                idx++;
                return new Pair<>(keys.get(idx-1), values.get(idx-1)); //convert to pair
            }
        };
    }
}

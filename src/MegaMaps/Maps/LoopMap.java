package MegaMaps.Maps;

import MegaMaps.Map;

import java.util.ArrayList;

/**
 * A naive loop based dictionary. Very slow O(n)
 * @param <KeyType>
 * @param <ValueType>
 */
public class LoopMap<KeyType, ValueType> implements Map<KeyType,ValueType> {
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


}

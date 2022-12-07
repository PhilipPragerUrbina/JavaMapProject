package MegaMaps.Maps;

import MegaMaps.Map;

import java.util.ArrayList;

/**
 * A Hash Map
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashMap<KeyType, ValueType> implements Map<KeyType,ValueType> {
    private ValueType[] values;
    int size= 0;

    public HashMap(int start_size){
        values = (ValueType[]) new Object[start_size]; //todo why
    }

    @Override
    public ValueType get(KeyType key) {

        int hash = key.hashCode();
        long idx = hash % n;
        return  null; //none found
    }

    @Override
    public void put(KeyType key, ValueType value) {

    }


}

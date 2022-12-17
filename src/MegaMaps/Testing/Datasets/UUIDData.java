package MegaMaps.Testing.Datasets;

import MegaMaps.Utils.Pair;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * UUID's paired with increasing Integer values
 */
public class UUIDData implements Dataset<UUID,Integer>{
    private ArrayList<Pair<UUID,Integer>> data; //the data

    /**
     * Generate UUIDs
     * @param size
     */
    public UUIDData(int size){
        data = new ArrayList<>(size); //preallocate
        for (int i = 0; i < size; i++) {
            //Random UUID repeats 1 in a billion(probably fine)
            data.add(new Pair<>(UUID.randomUUID(),i)); //gen data

        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Pair<UUID, Integer> getEntry(int index) {
        return data.get(index);
    }
}

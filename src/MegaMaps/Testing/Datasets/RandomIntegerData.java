package MegaMaps.Testing.Datasets;

import MegaMaps.Utils.Pair;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data consisting of random integers that have UNIQUE keys
 */
public class RandomIntegerData implements Dataset<Integer,Integer>{
    private ArrayList<Pair<Integer,Integer>> data; //the data
    private SecureRandom random = new SecureRandom(); //use secure random for actual randomness

    /**
     * Generate n random integer pairs
     * @param size
     */
    public RandomIntegerData(int size){
        data = new ArrayList<>(size); //preallocate
        Iterator<Integer> unique_keys =  random.ints().distinct().iterator(); //Make sure keys are UNIQUE!
        for (int i = 0; i < size; i++) {
            data.add(new Pair<>(unique_keys.next(),random.nextInt())); //gen data
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Pair<Integer, Integer> getEntry(int index) {
        return data.get(index);
    }
}

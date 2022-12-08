package MegaMaps.Maps;

import MegaMaps.Map;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A hash map implementation
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashMap<KeyType, ValueType> implements Map<KeyType,ValueType> {

    /**
     * The Bucket class for putting together similar keys
     */
    private class Bucket{
        public ArrayList<Pair> pairs = new ArrayList<>(); //the contents fo the bucket
        public Bucket(Pair first_pair){ //Create the bucket with a pair
            addPair(first_pair); //must have one pair
        }
        public void addPair(Pair pair){ //add a pair to the bucket
          pairs.add(pair); //todo nested buckets
        }
    }

    /**
     * Represents a key value pair
     */
    private class Pair{
        public Pair( KeyType key,ValueType value) { //simple constructor
            this.value = value;
            this.key = key;
        }
        public ValueType value;
        public KeyType key;
    }

    //todo add auto resize and re-hash. Multi-thread?
    private Bucket[] buffer; //actual hash map array
    private int entry_size = 0; //the # of entries

    /**
     * Create a new hash map with a certain size
     * @param start_size Should be similar to the amount of data you expect to contain in it
     */
    public HashMap(int start_size){
        buffer = (Bucket[]) Array.newInstance(Bucket.class, start_size); //todo Find out a better way to do this
    }

    @Override
    public ValueType get(KeyType key) {

        int hash = key.hashCode(); //create the hash code
        int idx =getIdx(hash); //get the index

        Bucket bucket = buffer[idx]; //access the bucket
        if(bucket != null){ //exists
                for (Pair candidate: bucket.pairs) {
                    if(candidate.key.equals(key)){
                        return candidate.value; //find corresponding value
                    }
                }
        }

        return null; //none found
    }

    @Override
    public void put(KeyType key, ValueType value) {

        int hash = key.hashCode(); //get the hash code
        int idx =getIdx(hash); //get the index

        Pair pair = new Pair(key, value); //Create the pair to insert
        entry_size++; //update size

        if(buffer[idx] != null){    //there is an entry here
            for (Pair other : buffer[idx].pairs) {
                if(other.key.equals(key)){  //assignment
                    other.value = value;
                    return;
                }
            }
            //else collision
            buffer[idx].addPair(pair);

        }else{ //first entry
            buffer[idx] = new Bucket(pair);
        }

    }

    /**
     * Get the size of the map
     * @return # of entries
     */
    public int size(){
        return entry_size;
    }

    //todo the key set

    /**
     * Utility to get the index in the buffer array
     * @param hash The hashed key
     * @return The index
     */
    private int getIdx(int hash){
        //should not be negative
        return Math.abs(hash % buffer.length); //todo make sure to minimize collisions
    }


}

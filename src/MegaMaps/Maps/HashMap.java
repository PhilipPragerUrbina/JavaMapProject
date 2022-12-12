package MegaMaps.Maps;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A hash map implementation
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashMap<KeyType, ValueType> implements Map<KeyType,ValueType> , Iterable<Pair<KeyType,ValueType>> {
    /**
     * The Bucket class for putting together similar keys
     */
    class Bucket{
        public ArrayList<Pair<KeyType,ValueType>> pairs = new ArrayList<>(); //the contents fo the bucket
        public Bucket(Pair<KeyType,ValueType> first_pair){ //Create the bucket with a pair
            addPair(first_pair); //must have at least one pair
        }
        public void addPair(Pair<KeyType,ValueType> pair){ //add a pair to the bucket
            pairs.add(pair); //todo nested buckets with fixed split size and no arraylists
        }
    }

    private Bucket[] buffer; //actual hash table
    private int entry_size = 0; //the # of entries
    private double resize_percentage;


    //todo add auto resize and re-hash. Multi-thread?
    //todo defualt constuctor
    //todo figure out when to resize based on count
    //todo re-hash by setting new buffer and re-putting existing pairs

    /**
     * Default constructor with a start size of 1000 and a resize percentage of 2.0
     */
    public HashMap(){
        this(1000,2.0);
    }

    /**
     * Create a new hash map with a certain size
     * @param start_size Should be similar to the amount of data you expect to contain in it
     * Has a resize percentage of 2.0
     */
    public HashMap(int start_size){
       this(start_size,2.0);
    }

    /**
     * Create a new hash map with a certain size
     * @param start_size Should be similar to the amount of data you expect to contain in it
     * @param resize_percentage What percentage full (0.1=10%) to resize the hashmap to be bigger. For example 2.0 means resizing when number of entries is twice the table size.
     */
    public HashMap(int start_size, double resize_percentage){
        buffer = (Bucket[]) Array.newInstance(Bucket.class, start_size); //todo Find out a better way to do this
        this.resize_percentage = resize_percentage;
    }

    /**
     * Change the size of the hash table
     * @param size The new size. Can be smaller or larger than current size
     */
    public void resize(int size){
        Bucket[] new_table = (Bucket[]) Array.newInstance(Bucket.class, size); //Create new array
        for (Pair<KeyType,ValueType> pair: this ) { //iterate over pairs
            //put the existing key in new table, we know it is not an assignment
            int idx = hash(pair.key, size); //re-hash with new length
            if(new_table[idx] != null){
                new_table[idx].addPair(pair); //There is already a bucket here
            }else{
                new_table[idx] = new Bucket(pair); //first entry in bucket, create new
            }
        }
        buffer = new_table; //swap
    }

    @Override
    public ValueType get(KeyType key) {
        int idx = hash(key, buffer.length);

        Bucket bucket = buffer[idx]; //access the bucket
        if(bucket != null){ //exists
                for (Pair<KeyType,ValueType> candidate: bucket.pairs) {
                    if(candidate.key.equals(key)){
                        return candidate.value; //find corresponding value
                    }
                }
        }
        return null; //none found
    }

    @Override
    public void put(KeyType key, ValueType value) {
        //auto resize if needed
        if((double)entry_size/(double)buffer.length > resize_percentage){
            resize(buffer.length + (int)(buffer.length * resize_percentage)); //resize proportional to current size and the resize percentage
        }

        int idx = hash(key, buffer.length);

        Pair pair = new Pair(key, value); //Create the pair to insert

        if(buffer[idx] != null){    //there is an entry here
            for (Pair other : buffer[idx].pairs) {
                if(other.key.equals(key)){  //assignment don't update size
                    other.value = value;
                    return;
                }
            }
            entry_size++; //update size
            //else collision
            buffer[idx].addPair(pair);

        }else{ //first entry
            entry_size++; //update size
            buffer[idx] = new Bucket(pair);
        }
    }

    @Override
    public void remove(KeyType key) {
        int idx = hash(key, buffer.length);

        Bucket bucket = buffer[idx]; //access the bucket
        if(bucket != null){ //exists
            for (Pair<KeyType,ValueType> candidate: bucket.pairs) {
                if(candidate.key.equals(key)){
                    bucket.pairs.remove(candidate);
                    return;
                }
            }
        }
    }

    /**
     * Utility to hash and compress a key
     * @param key The key
     * @param table_size The size of the range to compress to
     * @return The index in the table
     */
    private int hash(KeyType key, int table_size){
        int hash = key.hashCode(); //get the hash
        //compress to size
        return Math.abs(hash % table_size);  //should not be negative
        //todo make sure to minimize collisions
    }

    @Override
    public int size(){
        return entry_size;
    }

    /**
     * Instead of key list, you can iterate over the map
     * Order is random
     * @return The iterator for use in things like for loops
     */
    @Override
    public Iterator<Pair<KeyType,ValueType>> iterator() {
        return new Iterator<>() {
            int index = 0; //The current index in the hash table
            int bucket_index = 0; //The index within a bucket
            int entries_given = 0; //# Entries that have been outputted
            @Override
            public boolean hasNext() {
                return entries_given < entry_size; //check if entries left
            }
            @Override
            public Pair<KeyType, ValueType> next() {
                while (index < buffer.length) { //loop through table
                    if (buffer[index] != null) {
                        while (bucket_index < buffer[index].pairs.size()) { //loop through bucket
                            bucket_index++;  entries_given++; //update indexes
                            return buffer[index].pairs.get(bucket_index - 1); //get the pair
                        }
                        bucket_index = 0; //Next bucket reset index
                    }
                    index++;
                }
                return null;
            }
        };
    }
}

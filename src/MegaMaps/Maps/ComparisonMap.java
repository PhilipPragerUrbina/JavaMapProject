//package MegaMaps.Maps;
//
//import MegaMaps.Map;
//import MegaMaps.Utils.Pair;
//
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Iterator;
//
///**
// * Simple unbalanced tree map
// * @param <KeyType>
// * @param <ValueType>
// */
//public class ComparisonMap<KeyType extends Comparable, ValueType> implements Map<KeyType,ValueType>, Iterable<Pair<KeyType,ValueType>> {
//    /**
//     * A node in the tree
//     */
//    class Node{
//        public Node less_than,greater_than; //binary children
//        public Pair<KeyType,ValueType> entry = null; //The leaf data
//
//        /**
//         * Create a new inner node
//         */
//        public Node(){
//
//        }
//
//        /**
//         * Create a new leaf node
//         * @param leaf_value The entry that the leaf contains
//         */
//        public Node(Pair<KeyType,ValueType> leaf_value){
//            entry = leaf_value;
//        }
//        /**
//         * Check if the node is a leaf node
//         * @return True if leaf
//         */
//        public boolean isLeaf(){
//            return entry != null;
//        }
//    }
//
//    private int entry_size; //the # of entries
//    private Node root_node;
//
//    /**
//     * Create a new empty comparison map
//     */
//    public ComparisonMap(){
//        root_node  = new Node();
//    }
//
//
//    @Override
//    public ValueType get(KeyType key) {
//        Node current_node = root_node;
//        while (true){
//            if(current_node.isLeaf()){
//                if(current_node.entry.key.equals(key)){
//                    return current_node.entry.value;
//                }
//                return null;
//            }
//            if(key.)
//
//        }
//    }
//
//    @Override
//    public void put(KeyType key, ValueType value) {
//        //auto resize if needed
//        if((double)entry_size/(double)buffer.length > resize_percentage){
//            resize(buffer.length + (int)(buffer.length * resize_percentage)); //resize proportional to current size and the resize percentage
//        }
//
//        int idx = hash(key, buffer.length);
//
//        Pair pair = new Pair(key, value); //Create the pair to insert
//
//        if(buffer[idx] != null){    //there is an entry here
//            for (Pair other : buffer[idx].pairs) {
//                if(other.key.equals(key)){  //assignment don't update size
//                    other.value = value;
//                    return;
//                }
//            }
//            entry_size++; //update size
//            //else collision
//            buffer[idx].addPair(pair);
//
//        }else{ //first entry
//            entry_size++; //update size
//            buffer[idx] = new Bucket(pair);
//        }
//    }
//
//    @Override
//    public void remove(KeyType key) {
//        //todo test this as well as size
//        //todo make map resize to be smaller if enough removed to save space
//        int idx = hash(key, buffer.length);
//        Bucket bucket = buffer[idx]; //access the bucket
//        if(bucket != null){ //exists
//            for (Pair<KeyType,ValueType> candidate: bucket.pairs) {
//                if(candidate.key.equals(key)){
//                    bucket.pairs.remove(candidate); //remove it
//                    entry_size--; //Decrement size
//                    return;
//                }
//            }
//        }
//    }
//
//    @Override
//    public void reset() {
//        entry_size = 0; //reset count
//        buffer = (Bucket[]) Array.newInstance(Bucket.class, start_size); //reset buffer
//    }
//
//    /**
//     * Utility to hash and compress a key
//     * @param key The key
//     * @param table_size The size of the range to compress to
//     * @return The index in the table
//     */
//    private int hash(KeyType key, int table_size){
//        int hash = key.hashCode(); //get the hash
//        //compress to size
//        return Math.abs(hash % table_size);  //should not be negative
//    }
//
//    @Override
//    public int size(){
//        return entry_size;
//    }
//
//    /**
//     * Instead of key list, you can iterate over the map
//     * Order is random
//     * @return The iterator for use in things like for loops
//     */
//    @Override
//    public Iterator<Pair<KeyType,ValueType>> iterator() {
//        return new Iterator<>() {
//            int index = 0; //The current index in the hash table
//            int bucket_index = 0; //The index within a bucket
//            int entries_given = 0; //# Entries that have been outputted
//            @Override
//            public boolean hasNext() {
//                return entries_given < entry_size; //check if entries left
//            }
//            @Override
//            public Pair<KeyType, ValueType> next() {
//                while (index < buffer.length) { //loop through table
//                    if (buffer[index] != null) {
//                        while (bucket_index < buffer[index].pairs.size()) { //loop through bucket
//                            bucket_index++;  entries_given++; //update indexes
//                            return buffer[index].pairs.get(bucket_index - 1); //get the pair
//                        }
//                        bucket_index = 0; //Next bucket reset index
//                    }
//                    index++;
//                }
//                return null;
//            }
//        };
//    }
//}

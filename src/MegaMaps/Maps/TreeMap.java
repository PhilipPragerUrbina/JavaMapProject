package MegaMaps.Maps;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * An experimental hash map with nested buckets
 * @param <KeyType>
 * @param <ValueType>
 */
public class TreeMap <KeyType, ValueType> implements Map<KeyType,ValueType> {

    private int node_size; //The size that each node is

    /**
     * The Node class that represents a hash table that can be nested
     */
    class Node{
        public Node[] nodes = null; //The children
        public Pair<KeyType,ValueType> entry = null; //The leaf data

        /**
         * Create a new inner node
         */
        public Node(){
            nodes = (Node[])Array.newInstance(TreeMap.Node.class, node_size); //allocate children
        }

        /**
         * Create a new leaf node
         * @param leaf_value The entry that the leaf contains
         */
        public Node(Pair<KeyType,ValueType> leaf_value){
            entry = leaf_value;
        }

        /**
         * Check if the node is a leaf node
         * @return True if leaf
         */
        public boolean isLeaf(){
            return entry != null;
        }
    }

    private Node root_node; //The starting hash table
    private int entry_size; //the # of entries


    /**
     * Default constructor with a node size of 10
     */
    public TreeMap(){
        this(10);
    }


    /**
     * Create a new tree map
     * @param node_size How large each node's hash table should be
     */
    public TreeMap(int node_size){
        this.node_size = node_size;
        entry_size = 0;
        root_node = new Node();
    }

    @Override
    public ValueType get(KeyType key) {
        int level = 1; //Hash differently based on tree depth
        Node current_node = root_node; //start at root
        while (true){ //traverse tree
            int idx = hash(key, level); //hash based on current depth in tree
            Node child = current_node.nodes[idx]; //get the child node
            if(child == null){
                return null; //Not found
            }
            if(child.isLeaf()){
                return child.entry.value; //Entry is found
            }
            //need to traverse further
            root_node = child;
            level++;
        }
    }

    @Override
    public void put(KeyType key, ValueType value) {
        Pair pair = new Pair(key, value); //Create the pair to insert

        int level = 1; //Hash differently based on tree depth
        Node current_node = root_node; //start at root
        while (true){ //traverse tree
            int idx = hash(key, level); //hash based on current depth in tree
            Node child = current_node.nodes[idx]; //get the child node
            if(child == null){
                //create leaf node
                current_node.nodes[idx] = new Node(pair);
                return;
            }
            if(child.isLeaf()){
                //Collision
                Node old_leaf = child; //Get old leaf node
                current_node.nodes[idx] = new Node(); //Create new inner node
                child =  current_node.nodes[idx];
                //insert back in old node
                int other_idx = hash(key,level+1);
                child.nodes[other_idx] = old_leaf;
                //keep traversing to add new pair to this new inner node
            }
            //need to traverse further
            root_node = child;
            level++;
        }
    }

    @Override
    public void remove(KeyType key) {
        int level = 1; //Hash differently based on tree depth
        Node current_node = root_node; //start at root
        while (true){ //traverse tree
            int idx = hash(key, level); //hash based on current depth in tree
            Node child = current_node.nodes[idx]; //get the child node
            if(child == null){
                return; //Not found
            }
            if(child.isLeaf()){
                //found
                current_node.nodes[idx] = null; //todo collapse tree if parent node is empty
                entry_size--;
                return;
            }
            //need to traverse further
            root_node = child;
            level++;
        }
    }

    @Override
    public void reset() {
        entry_size = 0; //reset count
        root_node = new Node();
    }

    /**
     * Utility to hash and compress a key
     * @param key The key
     * @param offset A number to change the hash
     * @return The index in the node
     */
    private int hash(KeyType key, int offset ){
        int hash = key.hashCode(); //get the hash
        //compress to size and change based on offset. Todo fin something better than multiplication
        return Math.abs((hash*offset) % node_size);  //should not be negative
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

            int entries_given = 0; //# Entries that have been outputted
            @Override
            public boolean hasNext() {
                return entries_given < entry_size; //check if entries left
            }
            @Override
            public Pair<KeyType, ValueType> next() {
                //todo
                return null;
            }
        };
    }
}

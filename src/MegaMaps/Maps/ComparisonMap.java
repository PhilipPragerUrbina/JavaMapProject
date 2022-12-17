package MegaMaps.Maps;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Simple unbalanced tree map
 * @param <KeyType>
 * @param <ValueType>
 */
public class ComparisonMap<KeyType extends Comparable, ValueType> implements Map<KeyType,ValueType>, Iterable<Pair<KeyType,ValueType>> {
    /**
     * A node in the tree
     */
    class Node{
        public Node less_node, greater_node = null; //binary children
        public Pair<KeyType,ValueType> entry; //The value
        private int hash; //the hash code computed on creation

        /**
         * Create a new node
         * @param value The entry that the node contains
         */
        public Node(Pair<KeyType,ValueType> value){
            entry = value;
            hash = value.key.hashCode();
        }

        /**
         * Compare a key to this node. Check if less than this node.
         */
        public boolean lessThan(KeyType key){
            return key.hashCode() < hash;
        }
        /**
         * Compare a key to this node. Check if greater than this node.
         */
        public boolean greaterThan(KeyType key){
            return key.hashCode() > hash;
        }

    }

    private int entry_size; //the # of entries
    private Node root_node = null;

    /**
     * Create a new empty comparison map
     */
    public ComparisonMap(){
        entry_size = 0;
    }


    @Override
    public ValueType get(KeyType key) {
        Node current_node = root_node;
        while (true){
            if(current_node == null){ //Not found, reached end of tree
                return null;
            }
            if(current_node.lessThan(key)){ //keep traversing tree
                current_node = current_node.less_node;
                continue;
            }
            if(current_node.greaterThan(key)){
                current_node = current_node.greater_node;
                continue;
            }
            //Is neither greater nor less, must be equal
            return current_node.entry.value;
        }
    }

    @Override
    public void put(KeyType key, ValueType value) {
        Node current_node = root_node;
        Node parent = null; //last_node
        while (true){
            if(current_node == null){ //Not found, reached end of tree. Create new node
                entry_size++;
                Node new_node = new Node(new Pair<>(key,value));
                if(parent == null){ //create root node
                    root_node = new_node;
                }else { //add to parent
                    if(parent.greaterThan(key)){
                        parent.greater_node = new_node;
                    }else {
                        parent.less_node = new_node; //less node
                    }
                }
                return;
            }
            if(current_node.lessThan(key)){ //keep traversing tree
                parent = current_node;
                current_node = current_node.less_node;
                continue;
            }
            if(current_node.greaterThan(key)){
                parent = current_node;
                current_node = current_node.greater_node;
                continue;
            }
            //Is neither greater nor less, must be equal. Assign
            current_node.entry.value = value;
            System.out.println(key.hashCode() + " " + current_node.entry.key.hashCode());
            //todo fix. Sometimes hashes are the same for some reason
            return;
        }

    }

    @Override
    public void remove(KeyType key) {
        return;
    }

    @Override
    public void reset() {
        entry_size = 0; //reset count
        root_node = null;
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

                return null;
            }
        };
    }
}

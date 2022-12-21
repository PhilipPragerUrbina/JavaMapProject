package MegaMaps.Maps;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Stack;

/**
 * Simple unbalanced tree map
 * Known problem: Can not handle duplicate hashes(Will fail tests on high counts,since random hashes will collide). Solution is to replace single entries with arraylists and check for equal.
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
            if(current_node.entry == null){
                return null;//has been deleted
            }
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
            if(current_node.entry == null){
                //has been deleted
                entry_size++;
                current_node.entry = new Pair<>(key, value);
            }
            current_node.entry.value = value;
            //todo fix. Sometimes hashes are the same, but the keys are different. Make arraylist of keys and check for equals.
            return;
        }

    }

    @Override
    public void remove(KeyType key) {
        Node current_node = root_node;
        while (true){
            if(current_node == null){ //Not found, reached end of tree
                return;
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
            entry_size--;
            current_node.entry = null; //remove the entry (refactoring tree is too expensive)
            return;
        }
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

    @Override
    public Iterator<Pair<KeyType,ValueType>> iterator() {
        return new Iterator<>() {
            int entries_given = 0; //# Entries that have been outputted
            Stack<Node> stack = new Stack<>(); //stack for traversal of tree
            boolean first = true;

            @Override
            public boolean hasNext() {
                return entries_given < entry_size; //check if entries left
            }
            @Override
            public Pair<KeyType, ValueType> next() { //traverse tree
                if(first){
                    stack.push(root_node); //add root node
                    first = false;
                }
                Node current_node = stack.pop(); //get current node
                if(current_node.less_node != null){ //add children
                    stack.push(current_node.less_node);
                }
                if(current_node.greater_node != null){ //add children
                    stack.push(current_node.greater_node);
                }
                //check if deleted
                if(current_node.entry  == null && hasNext()){
                    return next();
                }
                entries_given++;
                return current_node.entry;
            }
        };
    }
}

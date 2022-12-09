package MegaMaps.Utils;

/**
 * Represents a key value pair
 */
public class Pair<KeyType, ValueType>{
    /**
     * Create a new key value pair
     * @param key
     * @param value
     */
    public Pair( KeyType key,ValueType value) {
        this.value = value;
        this.key = key;
    }
    public ValueType value;
    public KeyType key;

    @Override
    public String toString() {
        return "Pair{" +
                "value=" + value +
                ", key=" + key +
                '}';
    }
}
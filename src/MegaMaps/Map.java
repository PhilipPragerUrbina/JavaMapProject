package MegaMaps;

/**
 * Interface for using MegaMap maps.
 * @param <KeyType> The key type
 * @param <ValueType> The value type
 */
public interface Map<KeyType, ValueType> {
    /**
     * Get a value in the map
     * @param key The key that corresponds to the value
     * @return The value or null if not found
     */
    ValueType get(KeyType key);

    /**
     * Put a key value set in the map. Creates new if not exists, updates if exists
     * @param key The key
     * @param value The value
     */
    void put(KeyType key, ValueType value);
}

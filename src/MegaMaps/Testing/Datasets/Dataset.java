package MegaMaps.Testing.Datasets;

import MegaMaps.Utils.Pair;

/**
 * A dataset for benchmarking and testing
 *  Key's MUST BE UNIQUE
 * @param <KeyType>
 * @param <ValueType>
 */
public interface Dataset<KeyType, ValueType> {
    /**
     * Get the # of entries in dataset
     * @return Size of dataset
     */
    int size();

    /**
     * Access the dataset
     * @param index The nth entry to access
     * @return The key-value pair, or null if non-existent
     */
    Pair<KeyType,ValueType> getEntry(int index);

}



//for random datasets use java secure random. Also try uuid's
package MegaMaps.Testing.Datasets;

import MegaMaps.Map;
import MegaMaps.Utils.Pair;

//for random datasets use java secure random. Also try uuid's
public interface Dataset<KeyType, ValueType> {
    int size();
    Pair<KeyType,ValueType> getEntry(int index);
}

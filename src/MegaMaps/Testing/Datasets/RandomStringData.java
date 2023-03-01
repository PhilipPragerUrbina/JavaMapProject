package MegaMaps.Testing.Datasets;

import MegaMaps.Utils.Pair;

/**
 * Generates UUID strings paired with Integer values
 */
public class RandomStringData implements Dataset<String,Integer>{
    private UUIDData generator;

    /**
     * Generate random strings using UUIDS
     * @param count
     */
    public RandomStringData(int count){
        generator = new UUIDData(count);
    }

    @Override
    public int size() {
        return generator.size();
    }

    @Override
    public Pair<String, Integer> getEntry(int index) {
        return new Pair<>(generator.getEntry(index).key.toString()  ,index);
    }


}

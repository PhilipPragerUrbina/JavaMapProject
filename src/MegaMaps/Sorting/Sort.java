package MegaMaps.Sorting;

/**
 * An array sort algorithm
 */
public interface Sort {
    /**
     * Sort the array
     * @param array Array to sort in-place
     * @param <T> Type of thing to sort
     */
    public <T extends Comparable<T>>  void sort(T[] array);

}

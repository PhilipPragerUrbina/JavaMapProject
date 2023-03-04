package MegaMaps.Sorting;

/**
 * Selection sort
 */
public class SelectionSort implements Sort{

    /**
     * Sort array using selection sort
     * @param array Array to sort in-place
     */
    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        for (int index = 0; index < array.length; index++) {
            int smallest = getSmallestIndex(index, array);
            swap(index, smallest, array);
        }
        
    }

    /**
     * Get the smallest element in array
     * @param start First element to start looking(before will be ignored)
     * @return Index of the smallest element
     */
    public  static <T extends Comparable<T>> int getSmallestIndex(int start, T[] array){
        T smallest = array[start]; //Start with first
        int index = start;
        for (int i = start+1; i < array.length; i++) {
               if(smallest.compareTo(array[i]) > 0){
                   smallest = array[i];
                   index = i;
               }
        }
        return index;
    }

   
    /**
     * Swap two indexes in an array
     */
    public  static <T> void swap(int a, int b, T[] array){
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}

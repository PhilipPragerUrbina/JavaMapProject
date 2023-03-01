package MegaMaps.Sorting;

/**
 * Recursively split array
 */
public class SplitSort implements Sort{

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        sortSection(array,0,array.length);
    }

    /**
     * Recursive sort a section of the array
     * @param array Array to sort
     * @param start Start of section range inclusive
     * @param end End of range exclusive
     */
    public static <T extends Comparable<T>> void sortSection(T[] array, int start, int end){
        int middle =(end-start)/2;
        T middle_val = array[middle];
        for (int i = start; i < end; i++) {
            if(array[i].compareTo(array[middle]) > 0){
                middle = circularInsert(i,middle,array);
            }
        }
        if((end-start) < 3){
            return;
        }
        sortSection(array,start,start+middle);
        sortSection(array,start+middle,end);

    }

    /**
     * Swap two indexes in an array
     */
    public  static <T> void swap(int a, int b, T[] array){
        T temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    /**
     * Move value in array to another location without losing any data
     * Moves stuff left or right todo
     * @return The new index of what was previously in the destination index.
     */
    public  static <T> int circularInsert(int start_index, int destination_index, T[] array){
        T out = array[array.length-1]; //Value that would be gone
        for (int i = array.length-1; i > index; i-- ){
            array[i] = array[i-1]; //Shift
        }
        array[index] = value; //Set new value
        return out;
    }


    /**
     * Insert value into array and return the value that was forced out at the end of the array
     */
    public  static <T> T insert(T value, int index, T[] array){
        T out = array[array.length-1]; //Value that would be gone
        for (int i = array.length-1; i > index; i-- ){
            array[i] = array[i-1]; //Shift
        }
        array[index] = value; //Set new value
        return out;
    }
}

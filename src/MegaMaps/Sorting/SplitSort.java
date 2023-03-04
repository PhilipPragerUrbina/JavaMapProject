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
            if(array[i].compareTo(middle_val) > 0){
                middle = circularInsert(i,middle,array);
            }
        }
        if((end-start) < 3){
            return;
        }
        sortSection(array,start,start+middle);
        sortSection(array,start+middle+1,end);

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
     * Move value in array to another location shifting other data
     * Moves stuff left or right depending on direction
     * @return The new index of what was previously in the destination index.
     */
    public  static <T> int circularInsert(int start_index, int destination_index, T[] array){
        T start_value = array[start_index];
        if(start_index == destination_index){
            return destination_index;
        }
        if(start_index > destination_index){
            for (int i = start_index-1; i >= destination_index; i-- ){
                array[i+1] = array[i]; //Shift
            }
            array[destination_index] = start_value;
            return destination_index+1;
        }
        //start_index < destination_index)
            for (int i = start_index+1; i <= destination_index; i++ ){
                array[i-1] = array[i]; //Shift
            }
        array[destination_index] = start_value;
            return destination_index-1;

    }



}

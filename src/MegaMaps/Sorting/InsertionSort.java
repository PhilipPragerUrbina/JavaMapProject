package MegaMaps.Sorting;

/**
 * Insertion sort
 */
public class InsertionSort implements Sort{

    /**
     * Sort array using Insertion sort
     * @param array Array to sort in-place
     */
    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        for (int current = 0; current < array.length; current++) { //For each element
            T insertion_value = array[current]; //Get current value
            int correct_location = current-1; //Start after the value
            while(correct_location > -1 && insertion_value.compareTo(array[correct_location]) < 0){ //Go left until correct spot or no spot
                array[correct_location+1] = array[correct_location]; //Shift right
                correct_location--;
            }
            array[correct_location+1] = insertion_value; //Add at correct spot. Right in front of the one that is less than.

        }
    }


}

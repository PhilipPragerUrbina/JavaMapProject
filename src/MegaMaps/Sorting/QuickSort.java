package MegaMaps.Sorting;

/**
 * The fast sort
 */
public class QuickSort implements Sort{

    /**
     * Use quick sort to sort the array
     * @param array Array to sort in-place
     */
    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        quickSort(array,0,array.length);
    }

    /**
     * The recursive algorithm
     */
    private  <T extends Comparable<T>> void quickSort(T[] array,int start, int end) {
        if(end-start < 2) return; //If less than 2 elements return
        int pivot = getPivotPoint(array,start,end); //Get pivot
        pivot = split(array,start,end,pivot); //Partition and KEEP TRACK OF PIVOT
        //Exclude pivot and recurse
        quickSort(array,start,pivot);
        quickSort(array,pivot+1,end);
    }

    /**
     * Get the pivot point. I am using the middle element.
     */
    private  <T extends Comparable<T>> int getPivotPoint(T[] array, int start, int end){
        return (start+end)/2;
    }


    /**
     * The Hoare partition scheme
     * @return New pivot point
     */
    private  <T extends Comparable<T>> int split(T[] array, int start, int end, int pivot){
        T value = array[pivot];
        int left = start;
        int right = end-1; //End is exclusive
        while(true){
            //Move toward center
            while(array[left].compareTo(value) < 0 ){
                left++;
            }
            while(array[right].compareTo(value) > 0 ){
                right--;
            }
            if(left >= right){ //The two indices have passed each other
                return right; //new pivot
            }
            //Swap if no longer moving
            T temp = array[right];
            array[right] = array[left];
            array[left]=temp;
            //Keep moving so it doesn't get stuck
            right--;
            left++;
        }
    }
}

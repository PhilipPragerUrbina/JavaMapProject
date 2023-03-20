package MegaMaps.Sorting;

/**
 * Recursive merge sort
 */
public class MergeSort implements Sort {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {
        mergeSort(array,0,array.length);
    }

    private static  <T extends Comparable<T>> void mergeSort(T[] array,int start, int end){
        if(start >= end-1) return;
        int mid = (end+start)/2;
        mergeSort(array, start, mid);
        mergeSort(array, mid, end);
        merge(array, start,mid, mid, end);
    }

    private static  <T extends Comparable<T>> void merge(T[] array, int a_start, int b_start, int a_end, int b_end) {
        T[] array_original = array.clone();
        int i1 = 0;
        int i2 = 0;

        for (int i = a_start; i < b_end; i++) {
            if(array_original[i1].compareTo(array_original[i2]) < 0){
                array[i] =array_original[i1];
                i1++;
            }else{
                array[i] =array_original[i2];
                i2++;
            }
        }

    }
}

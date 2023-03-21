package MegaMaps.Sorting;

/**
 * Recursive merge sort
 */
public class MergeSort implements Sort {

    @Override
    public <T extends Comparable<T>> void sort(T[] array) {

        T[] array_copy = array.clone();
        mergeSort(array,0,array.length,array_copy);
    }

    private static  <T extends Comparable<T>> void mergeSort(T[] array,int start, int end,T[] temp){

        if(start >= end-1) return;
        int mid = (end+start)/2;
        mergeSort(array, start, mid,temp);
        mergeSort(array, mid, end,temp);
        merge(array, start,mid, mid, end,temp);
    }

    private static  <T extends Comparable<T>> void merge(T[] array, int a_start, int b_start, int a_end, int b_end, T[] temp) {
            int index_a = a_start;
            int index_b = b_start;
            int i = a_start;
            while(index_a < a_end && index_b < b_end) {
                if(array[index_a].compareTo( array[index_b]) < 0){
                    temp[i] = array[index_a];
                    index_a++;
                }else {
                    temp[i] = array[index_b];
                    index_b++;
                }
                i++;
            }

            for (int j = index_a; j < a_end; j++) {
                temp[i + j - index_a] = array[j];
            }
            for (int j = index_b; j < b_end; j++) {
                temp[i + j - index_b] = array[j];
            }


            for (int j = a_start; j < b_end; j++) {
                array[j] = temp[j];
            }

    }
}

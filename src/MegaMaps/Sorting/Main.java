package MegaMaps.Sorting;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Sort sort = new MergeSort();
        Integer[][] test_cases = new Integer[][]{
                {7, 1, 4, 3, 6, 5, 2,8},
                {7, 1, 4, 3, 6, 5, 2},
                {2,8,9,9,2,1,1,-4,2,5,2}
        };
        for (Integer[] test : test_cases) {
            System.out.println("Unsorted: " + Arrays.toString(test));
            sort.sort(test);
            System.out.println("Sorted: " + Arrays.toString(test));
            if(!verifyIncreasing(test)){
                System.err.println("Sort failed");
                return;
            }
        }

        while(true) {
            //Test search
            Integer[] search_arr = genRandomArray(1000);
            sort.sort(search_arr);
            System.out.println("Sorted random array: " + Arrays.toString(search_arr));
            Random random = new Random();
            for (int i = 0; i < 10000; i++) {
                int target = random.nextInt();
                int result = binarySearchRecursionLess(search_arr, target);
                System.out.println("Search for " + target + " found " + result);
                if (result != -1) {
                    System.out.println("The unlikely happened");
                    return;
                }
            }
        }

    }

    static void mergeSort(int[] arr){
        int[] temp = new int[arr.length];

        mergeSort(arr,0,arr.length-1,temp);
    }
    static void mergeSort(int[] arr, int start, int end, int[] temp){
        if(end <= start) return;
        int mid = (start+end)/2;
        mergeSort(arr,0,mid,temp);
        mergeSort(arr,mid+1,end,temp);
        merge(arr,0,mid,mid+1,end,temp);
    }

    static void merge(int[] arr, int s1, int e1, int s2, int e2, int[] temp){
        int index_a = s1;
        int index_b = s2;
        int i = s1;
        while(index_a <= e1 && index_b <= e2) {
            if(arr[index_a] < arr[index_b]){
                temp[i] = arr[index_a];
                index_a++;
            }else {
                temp[i] = arr[index_b];
                index_b++;
            }
            i++;
        }

        for (int j = index_a; j <= e1; j++) {
            temp[i + j - index_a] = arr[j];
        }
        for (int j = index_b; j <= e2; j++) {
            temp[i + j - index_b] = arr[j];
        }


        for (int j = s1; j < e2+1; j++) {
            arr[j] = temp[j];
        }
    }


    /**
     * Check if array is in increasing sorted order
     */
    private static <T extends Comparable<T>>boolean verifyIncreasing(T[] array){
        for (int i = 1; i < array.length; i++) {
            if(array[i-1].compareTo(array[i]) > 0){
                return false;
            }
        }
        return true;
    }

    //Internal recursion
    private static <T extends Comparable<T>>int binarySearch(T[] array, T target, int l, int r){
            int mid = (l+r)/2;
            if(array[mid].compareTo( target) == 0 ) return mid;
            if(l >= r ) return -1;
            return array[mid].compareTo(target)  > 0 ?  binarySearch(array,target,l , mid) : binarySearch(array,target,mid+1 , r);
    }

    /**
     * Perform a binary search
     * @param array Increasing SORTED array
     * @param <T> Array type
     * @param target Search query
     * @return index of found element otherwise -1
     * If multiple of the target are present, guaranteed to return one of them
     */
    private static <T extends Comparable<T>>int binarySearch(T[] array, T target){
       return binarySearch(array,target, 0,array.length);
    }

    /**
     * Perform a binary search without recursion
     * @param array Increasing SORTED array
     * @param <T> Array type
     * @param target Search query
     * @return index of found element otherwise -1
     * If multiple of the target are present, guaranteed to return one of them
     */
    private static <T extends Comparable<T>>int binarySearchRecursionLess(T[] array, T target){
        int r = array.length;
        int l = 0;
        while(l < r){
            int mid = (l+r)/2;
            if(array[mid].compareTo(target) == 0 ) return mid;
            if(array[mid].compareTo(target) > 0) {
                r = mid;
            } else{
                l = mid + 1;
            }
        }
        return -1;
    }

    /**
     * Generate a random int array with a length
     */
    private static Integer[] genRandomArray(int length){
        Random random = new Random();
        Integer[] arr = new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i]  = random.nextInt();
        }
        return arr;
    }

}

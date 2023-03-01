package MegaMaps.Sorting;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Sort sort = new SplitSort();
        Integer[][] test_cases = new Integer[][]{
                {7, 1, 4, 3, 6, 5, 2}
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
}

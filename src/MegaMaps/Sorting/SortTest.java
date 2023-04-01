package MegaMaps.Sorting;

import java.util.Arrays;
import java.util.Random;

public class SortTest {
    private static final int NUM_ITERATIONS = 10;
    private static final int[] SIZES_TO_TEST = {10,50, 100,300, 500, 700,1000,2000,5000,7000, 10000,20000,50000,70000, 100000};

    private static final Sort[] SORTS_TO_TEST = {new InsertionSort(),new SelectionSort(), new MergeSort(), new QuickSort()};

    public static void main(String[] args) {




        System.out.print("Array size");
        for (Sort sort: SORTS_TO_TEST) {
            System.out.print(", "+ sort.getClass().getCanonicalName() + " (ms)");
        }
        System.out.println();

        for (int arrSize : SIZES_TO_TEST) {
            long[] times = new long[SORTS_TO_TEST.length];


            for (int i = 0; i < NUM_ITERATIONS; i++) {

                Integer[] base_arr = createRandomArray(arrSize);

                for (int j = 0; j < times.length; j++) {
                    Integer[] arr_copy = Arrays.copyOf(base_arr, base_arr.length);
                    long start_time = System.currentTimeMillis();
                    SORTS_TO_TEST[j].sort(arr_copy);
                    long end_time = System.currentTimeMillis();
                    times[j] += (end_time - start_time);
                }
            }

            System.out.print(arrSize);
            for (long time: times) {
                System.out.print(", "+ (double)time/NUM_ITERATIONS);
            }
            System.out.println();
        }
    }

    /**
     * Generate a random int array with a length
     */
    private static Integer[] createRandomArray(int length){
        Random random = new Random();
        Integer[] arr = new Integer[length];
        for (int i = 0; i < length; i++) {
            arr[i]  = random.nextInt();
        }
        return arr;
    }

}

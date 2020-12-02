package lab4;

import java.util.Arrays;
import java.util.Random;


public class question1 {

    public static void main(String[] args) {
        int[] array16 = generateRandom(16);
        int[] array64 = generateRandom(64);
        int[] array256 = generateRandom(256);
        int[] array1024 = generateRandom(1024);
        int[] array4096 = generateRandom(4096);

        insertionSort(array16);
        insertionSort(array64);
        insertionSort(array256);
        insertionSort(array1024);
        insertionSort(array4096);

        System.out.println("---------------------------------------------------------------------------------");

        arraySort(array16);
        arraySort(array64);
        arraySort(array256);
        arraySort(array1024);
        arraySort(array4096);
    }

    public static int[] generateRandom(int size) {
        Random random = new Random();
        int[] array = new int[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }

        return array;
    }

    public static void insertionSort(int[] array) {
        int size = array.length;
        long startTime = System.nanoTime( );

        for (int i = 1; i < size; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }

        long endTime = System.nanoTime( );
        long elapsed = endTime - startTime;
        System.out.printf("StartTime: %d ns, End Time: %d ns, Elapsed Time: %d ns \n", startTime, endTime, elapsed);
    }

    public static void arraySort(int[] array) {
        long startTime = System.nanoTime( );
        Arrays.sort(array);
        long endTime = System.nanoTime( );
        long elapsed = endTime - startTime;
        System.out.printf("StartTime: %d ns, End Time: %d ns, Elapsed Time: %d ns \n", startTime, endTime, elapsed);
    }
}
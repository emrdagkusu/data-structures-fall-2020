package P2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

class Map {
    public int counter;
    public int index;

    Map(int counter, int index) {
        this.counter = counter;
        this.index = index;
    }
}

public class P2 {
    private static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    public static int binSearchFirst(int[] array, int N, int key) {
// Input: int array array[] with N elements in ascending order.
//        int key to find.
// Output: Returns subscript of the first array element >= key.
//         Returns N if key>array[N-1].
// Processing: Binary search.
        int low, high, mid;
        low = 0;
        high = N - 1;
// Subscripts between low and high are in search range.
// Size of range halves in each iteration.
// When low>high, low==high+1 and array[high]<key and array[low]>=key.
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] < key)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return low;
    }

    public static int binSearchLast(int[] array, int N, int key) {
// Input: int array array[] with N elements in ascending order.
//        int key to find.
// Output: Returns subscript of the last array element <= key.
//         Returns -1 if key<array[0].
// Processing: Binary search.
        int low, high, mid;
        low = 0;
        high = N - 1;
// subscripts between low and high are in search range.
// size of range halves in each iteration.
// When low>high, low==high+1 and array[high]<=key and array[low]>key.
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] <= key)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return high;
    }

    public static void operation(int action, int j) {
        if (action == 3) {

        }
    }

    public static void main(String[] args) throws IOException {
        URL url = P2.class.getResource("input2.txt");
        File input = new File(url.getPath());

        try {
            Scanner scan = new Scanner(readFile(input));
            int N = Integer.parseInt(scan.nextLine());
            int u = 0, v = 0;
            int[] count = new int[N];
            int[] countCopy = new int[N];
            Map[] mapArray = new Map[N];

            for (int i = 0; i < N; i++) {
                count[i] = 0;
                mapArray[i] = new Map(0, i);
            }

            while (scan.hasNextLine()) {
                String lines = scan.nextLine();
                String[] line = lines.split(" ");
                String action = line[0];

                if (action.equals("0")) {
                    break;
                }

                if (action.equals("1")) {
                    System.out.println("print by index");
                    for (int i = 0; i < N; i++) {
                        System.out.printf("%d %d\n", i, count[i]);
                    }
                    System.out.println("-------");
                }

                if (action.equals("2")) {
                    System.out.println("print by count");

                    for (int i = 0; i < N; i++) {
                        mapArray[i].counter = count[i];
                        mapArray[i].index = i;
                    }

                    Map temp;
                    for (int i = 1; i < N; i++) {
                        for (int j = 0; j < N - i; j++) {
                            if (mapArray[j].counter > mapArray[j + 1].counter) {
                                temp = mapArray[j];
                                mapArray[j] = mapArray[j + 1];
                                mapArray[j + 1] = temp;
                            }
                        }
                    }

                    for (int i = 0; i < N; i++) {
                        System.out.printf("%d %d\n", mapArray[i].index, mapArray[i].counter);
                    }

                    System.out.println("-------");
                }

                if (action.equals("3")) {
                    u = Integer.parseInt(line[1]);
                    count[u]++;
                }

                if (action.equals("4")) {
                    u = Integer.parseInt(line[1]);
                    count[u]--;
                }

                if (action.equals(("5"))) {
                    u = Integer.parseInt(line[1]);
                    v = Integer.parseInt(line[2]);

                    for (int i = 0; i < N; i++) {
                        countCopy[i] = count[i];
                    }

                    Arrays.sort(countCopy);
                    int val1 = binSearchFirst(countCopy, N, u);
                    int val2 = binSearchLast(countCopy, N, v);
                    int value = (val2 - val1) + 1;

                    System.out.printf("%d counters valued between %d and %d\n", value, u, v);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
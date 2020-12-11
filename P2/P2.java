package P2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

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

    public static int binarySearchFirstPlace(int[] array, int N, int key) {
        // Input: int array a[] with n elements in ascending order.
//        int key to find.
// Output: Returns subscript of the first a element >= key.
//         Returns n if key>a[n-1].
// Processing: Binary search.
        int low, high, mid;
        low = 0;
        high = N - 1;
// Subscripts between low and high are in search range.
// Size of range halves in each iteration.
// When low>high, low==high+1 and a[high]<key and a[low]>=key.
        while (low <= high) {
            mid = (low + high) / 2;
            if (array[mid] < key)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return low;
    }

    public static int binarySearchLastPlace(int[] array, int N, int key) {
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

    public static void main(String[] args) throws IOException {
        URL url = P2.class.getResource("input2.txt");
        File input = new File(url.getPath());

        try {
            Scanner scan = new Scanner(readFile(input));
            int N = Integer.parseInt(scan.nextLine());
            int u = 0, v = 0, temp, val1, val2, result;
            int[] count = new int[N];
            int[] map = new int[N];
            int[] index = new int[N];

            for (int i = 0; i < N; i++) {
                map[i] = i;
                index[i] = i;
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
                        System.out.printf("%d %d\n", i, count[map[i]]);
                    }
                    System.out.println("-------");
                }

                if (action.equals("2")) {
                    System.out.println("print by count");
                    for (int i = 0; i < N; i++) {
                        System.out.printf("%d %d\n", index[i], count[i]);
                    }
                    System.out.println("-------");
                }

                if (action.equals("3")) {
                    u = Integer.parseInt(line[1]);
                    u = map[u];
                    int value = count[u];
                    int rightPlace = binarySearchLastPlace(count, N, value);

                    temp = map[index[u]];
                    map[index[u]] = map[index[rightPlace]];
                    map[index[rightPlace]] = temp;

                    temp = index[u];
                    index[u] = index[rightPlace];
                    index[rightPlace] = temp;

                    count[rightPlace]++;
                }

                if (action.equals("4")) {
                    u = Integer.parseInt(line[1]);
                    u = map[u];
                    int value = count[u];
                    int leftPlace = binarySearchFirstPlace(count, N, value);

                    temp = map[index[u]];
                    map[index[u]] = map[index[leftPlace]];
                    map[index[leftPlace]] = temp;

                    temp = index[u];
                    index[u] = index[leftPlace];
                    index[leftPlace] = temp;

                    count[leftPlace]--;
                }

                if (action.equals(("5"))) {
                    u = Integer.parseInt(line[1]);
                    v = Integer.parseInt(line[2]);

                    val1 = binarySearchFirstPlace(count, N, u);
                    val2 = binarySearchLastPlace(count, N, v);
                    if (val1 == -1 && val2 == -1) {
                        result = 0;
                    } else {
                        result = (val2 - val1) + 1;
                    }

                    System.out.printf("%d counters valued between %d and %d\n", result, u, v);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
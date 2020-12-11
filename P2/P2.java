package P2;

import java.io.*;
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
//        To be able to make binary search we need leftmost and rightmost element
        int low, high, mid;
        low = 0;
        high = N - 1;
//        If low is equal to high or right of the right iteration can stop
        while (low <= high) {
//            Finding the middle index
            mid = (low + high) / 2;
//            If mid index of given array is smaller than the key value that wanted to be found
//            Lowest index will be one right index from mid index
            if (array[mid] < key) {
                low = mid + 1;
            }
//            Other ways highest index will be one left index from mid index
            else
                high = mid - 1;
        }
//        Finally if the key value is not in the array low will be zero
//        Or it will return the leftmost key value's index in the array
        return low;
    }

    public static int binarySearchLastPlace(int[] array, int N, int key) {
//        To be able to make binary search we need leftmost and rightmost element
        int low, high, mid;
        low = 0;
        high = N - 1;
//        If low is equal to high or right of the right iteration can stop
        while (low <= high) {
//            Finding the middle index
            mid = (low + high) / 2;
//            If mid index of given array is equal to or smaller than the key value that wanted to be found
//            Lowest index will be one right index from mid index
            if (array[mid] <= key) {
                low = mid + 1;
            }
//            Other ways highest index will be one left index from mid index
            else {
                high = mid - 1;
            }
        }
//        Finally if the key value is not in the array high will be zero
//        Or it will return the rightmost key value's index in the array
        return high;
    }

    public static void main(String[] args) throws IOException {
//        Inserting input files / To give the other input, input name can be changed
        URL inurl = P2.class.getResource("input1.txt");
        File input = new File(inurl.getPath());
//        Creating and writing output file / Output file will be directly created
        BufferedWriter writer = new BufferedWriter(new FileWriter("out1.txt"));

        try {
            Scanner scan = new Scanner(readFile(input));
            int N = Integer.parseInt(scan.nextLine());
            int u = 0, v = 0, temp, val1, val2, result;
            int[] count = new int[N];
            int[] map = new int[N];
            int[] index = new int[N];

//            Initializing the map and index arrays
            for (int i = 0; i < N; i++) {
                map[i] = i;
                index[i] = i;
            }

            while (scan.hasNextLine()) {
                String lines = scan.nextLine();
                String[] line = lines.split(" ");
                String action = line[0];

                if (action.equals("0")) {
                    writer.flush();
                    writer.close();
                    break;
                }

                if (action.equals("1")) {
//                    System.out.println("print by index");
                    writer.append("print by index\n");
                    for (int i = 0; i < N; i++) {
//                        System.out.printf("%d %d\n", i, count[map[i]]);
                        writer.write( i + " " + count[map[i]]);
                        writer.newLine();
                    }
//                    System.out.println("-------");
                    writer.append("-------\n");

                }

                if (action.equals("2")) {
//                    System.out.println("print by count");
                    writer.append("print by count\n");
                    for (int i = 0; i < N; i++) {
//                        System.out.printf("%d %d\n", index[i], count[i]);
                        writer.write(index[i] + " " + count[i]);
                        writer.newLine();
                    }
//                    System.out.println("-------");
                    writer.append("-------\n");
                }

                if (action.equals("3")) {
                    u = Integer.parseInt(line[1]);
//                    Finding where place u in the count array via using map array
                    u = map[u];
                    int value = count[u];
//                    Finding rightmost place with that value in the count array in O(logn) (Binary Search)
                    int rightPlace = binarySearchLastPlace(count, N, value);
//                    Before increasing the value of the index, we should prepare the map and index arrays via swapping in O(1)
                    temp = map[index[u]];
                    map[index[u]] = map[index[rightPlace]];
                    map[index[rightPlace]] = temp;

                    temp = index[u];
                    index[u] = index[rightPlace];
                    index[rightPlace] = temp;
//                    Increasing the value of given index
                    count[rightPlace]++;
                }

                if (action.equals("4")) {
                    u = Integer.parseInt(line[1]);
//                    Finding where place u in the count array via using map array
                    u = map[u];
                    int value = count[u];
                    int leftPlace = binarySearchFirstPlace(count, N, value);
//                    Before decreasing the value of the index, we should prepare the map and index arrays via swapping in O(1)
                    temp = map[index[u]];
                    map[index[u]] = map[index[leftPlace]];
                    map[index[leftPlace]] = temp;

                    temp = index[u];
                    index[u] = index[leftPlace];
                    index[leftPlace] = temp;
//                    Decreasing the value of given index
                    count[leftPlace]--;
                }

                if (action.equals(("5"))) {
                    u = Integer.parseInt(line[1]);
                    v = Integer.parseInt(line[2]);
//                    Finding the leftmost index in the array with given interval's smallest side
                    val1 = binarySearchFirstPlace(count, N, u);
//                    Finding the rightmost index in the array with given interval's biggest side
                    val2 = binarySearchLastPlace(count, N, v);
//                    If both are zero that means there is no element inside that interval
                    if (val1 == -1 && val2 == -1) {
                        result = 0;
                    }
//                    To find the exact number we should add 1
                    else {
                        result = (val2 - val1) + 1;
                    }

//                    System.out.printf("%d counters valued between %d and %d\n", result, u, v);
                    writer.write(result + " counters valued between " + u + " and " + v);
                    writer.newLine();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
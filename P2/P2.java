package P2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

public class P2 {
    private static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    public static int binarySearch(int arr[], int l, int r, int x) {
        if (r>=l)
        {
            int mid = l + (r - l)/2;
            if (arr[mid] == x)
                return mid;

            if (arr[mid] > x)
                return binarySearch(arr, l, mid-1, x);

            return binarySearch(arr, mid+1, r, x);
        }
        return -1;
    }


    public static void main(String[] args) throws IOException {
        URL url = P2.class.getResource("input1.txt");
        File input = new File(url.getPath());

        try {
            Scanner scan = new Scanner(readFile(input));
            int N = Integer.parseInt(scan.nextLine());
            int u = 0, v = 0;
            int[] counter = new int[N];
            int[] index = new int[N];

            for (int i = 0; i < N; i++) {
                counter[i] = 0;
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
                        System.out.printf("%d %d\n", i, counter[i]);

                    }
                    System.out.println("-------");
                }

                if (action.equals("2")) {
                    System.out.println("print by count");
                    for (int i = 1; i < N; i++) {
                        for (int j = 0; j < N-i; j++) {
                            if (counter[j] > counter[j+1]) {
                                int temp = counter[j];
                                counter[j] = counter[j+1];
                                counter[j+1] = temp;
                                temp = index[j];
                                index[j] = index[j+1];
                                index[j+1] = temp;
                            }
                        }
                    }
                    for(int i = 0; i < N; i++) {
                        System.out.printf("%d %d\n",index[i] ,counter[i]);
                    }
                    System.out.println("-------");
                }

                if (action.equals("3")) {
                    u = Integer.parseInt(line[1]);
                    counter[u]++;
                }

                if (action.equals("4")) {
                    u = Integer.parseInt(line[1]);
                    counter[u]--;
                }

                if (action.equals(("5"))) {
                    u = Integer.parseInt(line[1]);
                    v = Integer.parseInt(line[2]);
                    for (int i = 1; i < N; i++) {
                        for (int j = 0; j < N-i; j++) {
                            if (counter[j] > counter[j+1]) {
                                int temp = counter[j];
                                counter[j] = counter[j+1];
                                counter[j+1] = temp;
                            }
                        }
                    }
                    System.out.println(Arrays.toString(counter));
//                    int val = binarySearch(counter, 0, N-1, u);
//                    int val1 = binarySearch(counter, 0, N-1, v);
//                    int value = (val1-val);
//
//                    System.out.printf("%d counters valued between %d and %d\n", value, u, v);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
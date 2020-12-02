package lab4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class question2 {
    public static void main(String args[]) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the n: ");
        int n = scanner.nextInt();
        System.out.print("Enter the x: ");
        double x = scanner.nextInt();

        double[] array = new double[n];
        for (int i = 0; i < n; i++) {
            array[i] = random.nextInt(20);
        }
        System.out.println(Arrays.toString(array));

        double total = polynomial(array, x);
        System.out.printf("Result: %f\n", total);

    }

    public static double polynomial(double a[], double x){
        int size = a.length;
        double result;
        double total = 0;
            for (int i = 0; i < size; i++) {
                result = a[i] * power(x, i);
                total += result;
            }

        return total;
    }

    public static double power(double x, int exp){
        double result = 1;

        for (int i = 0; i < exp; i++) {
            result = result * x;
        }
//        System.out.println(result);
        return result;
    }
}

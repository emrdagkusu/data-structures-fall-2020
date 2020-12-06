package lab6_1;

import java.util.Scanner;
import java.util.Stack;

public class Q1 {
    public static void implementPermutation(int N) {
//        size is the number of element of what we have got inside the finished stack (finishStack)
        int size = 1;
        for (int i = 1; i <= N; i++) {
            size *= i;
        }

        Stack<String> beginStack = new Stack();
        Stack<String> finishStack = new Stack();

//        Starting with empty string
        String temp = "";
        beginStack.push(temp);

//        After finishStack has size of number of permutations, we are done
        while (finishStack.size() != size) {
            temp = beginStack.pop();
//            If the temp has N many element inside the string that means this permutation is done
//            So we can send it to the finishStack
            if (temp.length() == N) {
                finishStack.push(temp);
            } else {
//                Control of the current temp has which elements inside it
                for (int i = 1; i < N+1; i++) {
//                    If the temp does not contain the current element we will add that element
//                    And we will push that temp the beginStack again
                    if (!temp.contains(""+i)) {
                        beginStack.push(temp+i);
                    }
                }
            }
        }
//       To obtain the same order of your output, pop elements to an array
        String[] stringArray = new String[size];
        for (int i = 0; i < size; i++) {
            stringArray[i] = finishStack.pop();
        }
//        Printing the elements as seen in your output order
        for (int i = size-1; i > -1; i--) {
            System.out.println(stringArray[i]);
        }
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
//        Getting input value
        int N = scan.nextInt();
        implementPermutation(N);
    }
}

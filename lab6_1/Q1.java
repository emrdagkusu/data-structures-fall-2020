package lab6_1;

import java.util.Scanner;
import java.util.Stack;

public class Q1 {
    public static void implementPermutation(int N) {
        Stack<String> beginStack = new Stack();

//        Starting with empty string
        String curr = "";
        beginStack.push(curr);

//        After stack has no element inside it, we are done
        while (beginStack.size() != 0) {
            curr = beginStack.pop();
//            If the current string has N many element inside it, that means this permutation is done
//            So we can print it
            if (curr.length() == N) {
                System.out.println(curr);
            } else {
//                Control of the curr string for which elements includes inside it
//                For example if we have curr = "32", we will try to find which one is missing
//                Then we will push the curr+i which means "321" to the stack again, and so on
                for (int i = 1; i < N+1; i++) {
//                    If the curr does not contain the current i we will add that element
//                    And we will push curr the stack again
                    if (!curr.contains(""+i)) {
                        beginStack.push(curr+i);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
//        Getting input value
        System.out.print("Enter integer value for N: ");
        int N = scan.nextInt();
        implementPermutation(N);
    }
}

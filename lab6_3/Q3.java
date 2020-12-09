package lab6_3;

public class Q3 {
    public static void main(String[] args) {
//	 From original ArrayStack code that provided
        ArrayStack<Integer> S = new ArrayStack<>();
        ArrayStack<Integer> T = new ArrayStack<>();

        S.push(70);
        S.push(71);
        S.push(72);
        S.push(73);
        S.push(74);
        S.push(75);
        T.push(30);
        T.push(31);
        T.push(32);
        T.push(33);
        T.push(34);
        T.push(35);
        System.out.println("Stack S: " + S.toString());
        System.out.println("Stack T: " + T.toString());

        stackTransformation(S, T);

        System.out.println("Last version of Stack S: " + S.toString());
        System.out.println("Last version of Stack T: " + T.toString());
    }

    public static void stackTransformation(Stack S, Stack T) {
        int sizeS = S.size();
        Object current;

        for (int i = 0; i < sizeS; i++) {
            current = S.pop();
            System.out.printf("%d popped from stack S\n", current);
            System.out.println("Stack S: " + S.toString());
            T.push(current);
            System.out.printf("%d pushed to stack T\n", current);
            System.out.println("Stack T: " + T.toString());
        }
    }
}
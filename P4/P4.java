package P4;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

class Lines {
    String line;
    int count;
}

public class P4 {
    //    readFile function that provided the course object oriented programming last year
    static String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
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
    // Recursive heap sort function which takes given word array ant its length
    public static void sortOfHeap(Lines[] linesArray, int N) {
        // Temporary variables for swapping information
        String foo;
        int buff;

        // heapify the array
        for (int i = (N / 2) - 1; i >= 0; i--) {
            ascendingHeapify(linesArray, N, i);
        }

        // swapping necessary variables then again heapify
        for (int j = N - 1; j >= 0; j--) {
            foo = linesArray[0].line;
            linesArray[0].line = linesArray[j].line;
            linesArray[j].line = foo;

            buff = linesArray[0].count;
            linesArray[0].count = linesArray[j].count;
            linesArray[j].count = buff;

            ascendingHeapify(linesArray, j, 0);
        }
    }

    // heapify the given array with ascending order
    public static void ascendingHeapify(Lines[] linesArray, int N, int current) {
        // Temporary variables for swapping information
        String foo;
        int buff;

        // Current root is the given index(current)
        int root = current;
        // Since array starting from 0, left child of a parent node is 2*i + 1
        int leftChild = (2 * current) + 1;
        // Since array starting from 0, right child of a parent node is 2*i + 2
        int rightChild = (2 * current) + 2;

        // It is needed that
        String leftWord = linesArray[leftChild].line;
        String rightWord = linesArray[rightChild].line;
        String largestWord = linesArray[root].line;

        // compareTo method gives -1 if first variable smaller than second one, gives 0 if they are equal
        // give 1 if first variable bigger than second one
        // if key and current.key is equal to each other that means we found the node with given key
        if (leftChild < N && leftWord.compareTo(largestWord) > 0) {
            root = leftChild;
        }

        // compareTo method gives -1 if first variable smaller than second one, gives 0 if they are equal
        // give 1 if first variable bigger than second one
        // if key and current.key is equal to each other that means we found the node with given key
        if (rightChild < N && rightWord.compareTo(largestWord) > 0) {
            root = rightChild;
        }

        // if our current root is not equal to int current we should swap the variables
        if (root != current) {
            foo = linesArray[current].line;
            linesArray[current].line = linesArray[root].line;
            linesArray[root].line = foo;

            buff = linesArray[current].count;
            linesArray[current].count = linesArray[root].count;
            linesArray[root].count = buff;
            ascendingHeapify(linesArray, N, root);
        }
    }




    public static void main(String[] args) throws IOException {
        // Getting input file information
        URL in = P4.class.getResource("in.dat");
        File input = new File(in.getPath());
        // Buffered write to append into out file
        BufferedWriter writer = new BufferedWriter(new FileWriter("out.dat"));

        // Creating and initializing with empty variables of lines
        Lines[] lines = new Lines[50];
        int size = lines.length;
        for (int i = 0; i < size; i++) {
            lines[i] = new Lines();
        }

        try {
            // Getting the information from input file
            Scanner scanInput = new Scanner(readFile(input));
            Scanner scanner;
            // To determine how many input files are there
            int N = Integer.parseInt(scanInput.nextLine());

            // Declaring the current position
            int position = 0;

            // For loop will go over N (amount of input files) times
            for (int i = 0; i < N; i++) {
                // name of the input file
                String name = scanInput.nextLine();
                // getting path of input files
                URL inOut = P4.class.getResource(name);
                File inputOut = new File(inOut.getPath());
                // reading the input file with using scanner and readFile function
                scanner = new Scanner(readFile(inputOut));

                // Declaring the variables that will use many times
                String currentWord;
                int isFound;

                // we will go over the input file while it has data inside it
                while (scanner.hasNextLine()) {
                    // if it is found we will make it one so we can increment its count
                    isFound = 0;

                    // getting current word from current input file
                    currentWord = scanner.nextLine();

                    // we will look if there is such a word same as current word
                    // if there is, we will increment its count
                    for (int j = 0; j < position; j++) {
                        if (lines[j].line.compareTo(currentWord) == 0) {
                            // it is found so isFound should be 1
                            isFound = 1;
                            // Incrementing its count
                            lines[j].count = lines[j].count + 1;
                        }
                    }

                    // if isFound is 0, that means there is no such a word inside our heap
                    // we should add it into ende
                    if (isFound == 0) {
                        // adding the current word into heap with count equal to 1
                        lines[position].line = currentWord;
                        lines[position].count = 1;
                        // if we add an element into our heap we can increment our position too
                        position++;
                    }

                }
            }

            // sorting the array that contains words
            sortOfHeap(lines, position);

            // we will go over every element inside the heap
            for (int i = 0; i < position; i++) {
//                System.out.println(lines[i].line + " " + lines[i].count);
                // adding into out file from our heap heap
                writer.append(lines[i].line + " " + lines[i].count);
                writer.newLine();
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

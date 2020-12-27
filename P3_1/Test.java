package P3_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class Test {
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

//    scanFile function to scan given txt file and returning and ArrayList
    static ArrayList<String> scanFile(File file) throws IOException {
        ArrayList<String> names = new ArrayList<>();

        try {
            Scanner scan = new Scanner(readFile(file));

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
//                If the line is empty it will not add into ArrayList
                if (line.compareTo("") == 0) continue;
                names.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter file name includes names: ");
//        Getting the name of input file (starwars.txt)
        String fileNames = scan.nextLine();
        URL inSW = Test.class.getResource(fileNames);
        File inputSW = new File(inSW.getPath());
//        names keeps all names in the starwars.txt
        ArrayList<String> names = scanFile(inputSW);

        System.out.print("Enter file name includes test objects: ");
//        Getting the name of the file includes testObjects (test.txt)
        String fileSearch = scan.nextLine();
        URL inTest = Test.class.getResource(fileSearch);
        File inputTest = new File(inTest.getPath());
//        testObjects keeps all test names in the test.txt
        ArrayList<String> testObjects = scanFile(inputTest);

        BinarySearchTree BST = new BinarySearchTree();

//        timer to find the average time
        int timer = 0;
//        Inserting all names in the names ArrayList into BST
        for (String item : names) {
//            Start time
            Instant start = Instant.now();
//            Inserting
            BST.insert(item);
//            End time
            Instant end = Instant.now();
//            Duration between start and end
            Duration timeElapsed = Duration.between(start, end);
//            Adding durations onto time to find average time taken
            timer += timeElapsed.toNanos();
        }
//        Average time taken for insertion into BST
        System.out.println("\n***AVERAGE TIME***\n");
        System.out.println("Average time taken for insertion into BST: " + timer / names.size() + " nanoseconds");
//        Printing the all names in inorder traversal
        System.out.println("\n***INORDER TRAVERSAL***\n");
        BST.inorder();
        System.out.println();
//        Printing minimum node and time taken to finding it
        BST.searchMin();
        System.out.println();
//        Printing maximum node and time taken to finding it
        BST.searchMax();

//        Searching testObjects in the BST
        System.out.println("\n***SEARCHING FROM SECOND ARRAY***\n");
//        to determine whether it is leaf
        boolean isLeafNode;
        BSTNode current;
        for (String item : testObjects) {
//            Start time
            Instant start = Instant.now();
//            search function return the node with given key
            current = BST.search(item);
//            End time
            Instant end = Instant.now();
//            Duration between start and end
            Duration timeElapsed = Duration.between(start, end);

//            if we have the item in the BST we can print it's features as
//            depth, is it leaf node, height of subtree, time elapsed to find it
            if (current != null) {
//                If it has no left or right children, it is leaf node
                isLeafNode = current.left == null && current.right == null;
//                depth is equal to current.depth - 1 because depth is starting from 0 not 1
                System.out.println(item + " {depth: " + (current.depth - 1) + ", isLeafNode: "
                        + isLeafNode + ", heightOfSubtree: " + BST.findHeight(current) + ", timeElapsed: " + timeElapsed.toNanos() + " nanoseconds}");
            }
//            If we don't have the item in the BST, print not found
            else {
                System.out.println("\"" + item + "\"" + " is not found in BST!");
            }
        }
    }
}

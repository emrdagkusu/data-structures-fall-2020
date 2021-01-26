import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Pair class to not lose information about pairs
    static class Pair {
        int source;
        int destination;
        int numOfCommonFriends;
        boolean isPositive;

        public Pair(int source, int destination, int numOfCommonFriends, boolean isPositive) {
            this.source = source;
            this.destination = destination;
            this.numOfCommonFriends = numOfCommonFriends;
            this.isPositive = isPositive;
        }

        @Override
        public String toString() {
            return "" + source + " " +
                     + destination + " " +
                     + numOfCommonFriends + " " + isPositive;
        }
    }

    // To be able to find max id to determine graph's size
    static int max = 0;

    //    readFile function that provided by object oriented programming course last year
    static String readFile(File file) throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        }
    }

    //    scanFile function to scan given txt file and return an ArrayList
    static ArrayList<Edge> scanFile(File file) {
        ArrayList<Edge> edges = new ArrayList<>();

        try {
            Scanner scan = new Scanner(readFile(file));

            while (scan.hasNextLine()) {
                String lines = scan.nextLine();
                String[] line = lines.split(" ");
                Edge newEdge;

                if (line.length == 3) {
                    newEdge = new Edge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    if (Integer.parseInt(line[0]) > max) {
                        max = Integer.parseInt(line[0]);
                    }
                } else {
                    newEdge = new Edge(Integer.parseInt(line[0]), Integer.parseInt(line[1]), 1);
                }

                edges.add(newEdge);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return edges;
    }


    public static void main(String[] args) throws IOException {
        // Getting file path of FinalExamGraphFile.txt
        URL inGraph = Main.class.getResource("FinalExamGraphFile.txt");
        File inputGraph = new File(inGraph.getPath());
        // Scanning FinalExamGraphFile.txt and keeping them as a list
        ArrayList<Edge> graphFile = scanFile(inputGraph);

        // Main graph includes friendships inside the FinalExamGraphFile.txt
        Graph mainGraph = new Graph(max);

        // Another graph includes friendships inside both FinalExamGraphFile.txt and Positive.txt
        // Because we should create another list includes pairs which are not friends
        // And these pairs must be different than positive pairs
        // Therefore, I added positive pairs as a friend inside the new graph called graphWithFutureFriends
        Graph graphWithFutureFriends = new Graph(max);

        // Inserting every friendships inside the FinalExamGraphFile.txt into both mainGraph and graphWithFutureFriends
        for (Edge edge : graphFile) {
            mainGraph.insert(edge);
            graphWithFutureFriends.insert(edge);
        }

        Scanner scan = new Scanner(System.in);
        int ID;

        while (true) {
            System.out.println("Enter an ID to find it's adjacent nodes: (To exit write -1)");
            ID = scan.nextInt();
            if (ID == -1) break;
            else if (ID > 6251 || ID < -1) {
                System.out.println("Please, enter valid ID");
                continue;
            }
            mainGraph.findEdges(ID);
        }


        // Getting file path of Positive.txt
        URL inPositive = Main.class.getResource("Positive.txt");
        File inputPositive = new File(inPositive.getPath());
        // Scanning Positive.txt and keeping them as a list of edges
        ArrayList<Edge> positiveFile = scanFile(inputPositive);


        // Inserting every friendships inside the Positive.txt into graphWithFutureFriends to be able to detect negative pairs
        for (Edge edge : positiveFile) {
            graphWithFutureFriends.insert(edge);
        }


        // Initializing a list of edges to put negative pairs in it
        ArrayList<Edge> negativeList = new ArrayList<>();
        // Getting positive pairs size, which is "5592", to be able to keep that many element inside the negative pair list
        int positiveSize = positiveFile.size();

        // Random generator to produce ids to detect negative pairs
        Random random = new Random();

        // Counter to be able to produce size of positive pairs many element
        int counter = 0;
        // Initializing an edge to be able to keep pairs
        Edge negativeEdge;

        // It will stop when we create positive pairs many element
        // Since friendships are 2 sided, loop with positivePairs/2 many iteration will be enough
        while (counter < (positiveSize / 2)) {
            // Random source id
            int source = random.nextInt(max + 1);
            // Random destination id
            int destination = random.nextInt(max + 1);

            // We look into graph that includes both FinalExamGraphFile.txt and Positive.txt
            // because we must be sure about that these negative pairs must not be friend and must be different than positive pairs
            // So, if source and destination don't have an edge that means they are not friend
            // And because we insert positive pairs in this graph, they are different than positive pairs
            if (!graphWithFutureFriends.hasEdge(source, destination) && source != destination) {
                // Creating new edges with negative source and destination pairs
                // And weight part will be changed in the future with common friend number
                negativeEdge = new Edge(source, destination, 0);
                // Adding negative pairs into negative list
                negativeList.add(negativeEdge);
                // Adding this friendship into graph, to prevent get same pair in other iterations again
                graphWithFutureFriends.insert(negativeEdge);

                // Creating another edge includes destination and source with different place
                // Because friendships are two sided
                negativeEdge = new Edge(destination, source, 0);
                negativeList.add(negativeEdge);
                // Adding this friendship into graph, to prevent get same pair in other iterations again
                graphWithFutureFriends.insert(negativeEdge);


                // Incrementing counter by one when we found a negative pair
                counter++;
            }
        }


        // Creating new list called score to put both positive pairs and negative pairs into it
        // We will keep their number of common friends inside these list, and we will sort this list depending on number of common friend
        ArrayList<Pair> score = new ArrayList<>();
        // Initializing an pair node to be able to keep pairs with their source id, destination id, their number of common friends
        // and their isPositive value
        Pair totalPair;
        // Initializing an integer to keep number of common friends
        int commonFriends;

        // List for keeping positive and negative pairs separately with number of common friends of pairs
        ArrayList<Pair> positivePairs = new ArrayList<>();
        ArrayList<Pair> negativePairs = new ArrayList<>();


        for (Edge edge : positiveFile) {
            // Looking how many common friend given positive pair has inside the graph includes FinalExamGraphFile.txt
            commonFriends = mainGraph.numberOfCommonFriends(edge.source, edge.destination);

//            System.out.println(edge.source + " " + edge.destination + " " + commonFriends);

            // Creating a new pair with source id, destination id, number of common friends and isPositive value
            totalPair = new Pair(edge.source, edge.destination, commonFriends, true);
            positivePairs.add(totalPair);
            // Adding this new pair into score list
            score.add(totalPair);
        }
        System.out.println("#### Positive Pairs Loaded");


        for (Edge edge : negativeList) {
            // Looking how many common friend given negative pair has inside the graph includes FinalExamGraphFile.txt
            commonFriends = mainGraph.numberOfCommonFriends(edge.source, edge.destination);
//            System.out.println(edge.source + " " + edge.destination + " " + commonFriends);

            // Creating a new pair with source id, destination id, number of common friends and isPositive value
            totalPair = new Pair(edge.source, edge.destination, commonFriends, false);
            negativePairs.add(totalPair);
            // Adding this new pair into score list
            score.add(totalPair);
        }
        System.out.println("#### Negative Pairs Created");


        ArrayList<Pair> copy1 = new ArrayList<>();
        ArrayList<Pair> copy2 = new ArrayList<>();
        ArrayList<Pair> copy3 = new ArrayList<>();
        ArrayList<Pair> copy4 = new ArrayList<>();
        // Copies of score list which includes positive and negative pairs with their number of common friends
        // If we don't take copy of score list first sorting algorithm will sort the score list
        // and other algorithms will work their best case, therefore created 4 copies for 4 sorting algorithms
        for (Pair pair : score) {
            copy1.add(pair);
            copy2.add(pair);
            copy3.add(pair);
            copy4.add(pair);

        }

        Instant start;
        Instant end;
        Duration timeElapsed;

        start = Instant.now();
        ArrayList<Pair> quickSortedList = quickSort(copy1, 0, copy1.size() - 1);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken of Quick Sort    : " + timeElapsed.toMillis() + " milliseconds");

        start = Instant.now();
        ArrayList<Pair> heapSortedList = heapSort(copy2);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken of Heap Sort     : " + timeElapsed.toMillis() + " milliseconds");

        start = Instant.now();
        ArrayList<Pair> mergeSortedList = mergeSort(copy3, 0, copy3.size() - 1);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken of Merge Sort    : " + timeElapsed.toMillis() + " milliseconds");

        start = Instant.now();
        ArrayList<Pair> insertionSortedList = insertionSort(copy4);
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken of Insertion Sort: " + timeElapsed.toMillis() + " milliseconds");

        // Finding accuracy
        int correctCounter = 0;
        // Level above pairs as positive
        for (int i = 0; i < positiveSize; i++) {
            if (!quickSortedList.get(i).isPositive) {
                correctCounter++;
            }
        }
        // Level below pairs as negative
        for (int i = positiveSize; i < 2 * positiveSize; i++) {
            if (quickSortedList.get(i).isPositive) {
                correctCounter++;
            }
        }

        // Since we added first positive pairs into score list, positive pairs with 0 common friends stays in the first half
        // And since we added negative pairs into score list, negative pairs mostly stays in the second half
        // There are only 258 positive pairs and there are at most 40-60 negative pairs with nonzero common friend
        // Therefore, accuracy is quite low
        // if we were load negative pairs first then load positive pairs, there would be 95% accuracy
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        double percent = ((double)correctCounter/(2*positiveSize))*100;
        System.out.println("Accuracy: " + correctCounter + "/" + 2*positiveSize + " --- " + df.format(percent) + "%");



        BufferedWriter positiveWriter = new BufferedWriter(new FileWriter("PositiveWithCommonFriends.txt"));
        BufferedWriter negativeWriter = new BufferedWriter(new FileWriter("NegativeWithCommonFriends.txt"));
        BufferedWriter sortedWriter = new BufferedWriter(new FileWriter("SortedBothPositiveNegative.txt"));

        for (Pair pair : positivePairs) {
            positiveWriter.append(pair.toString());
            positiveWriter.newLine();
        }
        System.out.println("Positive Pairs with Common Friends added into \"PositiveWithCommonFriends.txt\"");

        for (Pair pair : negativePairs) {
            negativeWriter.append(pair.toString());
            negativeWriter.newLine();
        }
        System.out.println("Negative Pairs with Common Friends added into \"NegativeWithCommonFriends.txt\"");

        for (Pair pair : quickSortedList) {
            sortedWriter.append(pair.toString());
            sortedWriter.newLine();
        }
        System.out.println("Sorted List includes Both Positive and Negative Pairs added into \"SortedBothPositiveNegative.txt\"");

        System.out.println("These files created for control purposes, you can see them in the menu at the left part");

        scan.close();
        positiveWriter.close();
        negativeWriter.close();
        sortedWriter.close();
    }

    // QUICK SORT
    static ArrayList<Pair> quickSort(ArrayList<Pair> scoreList, int low, int high) {
        if (low < high) {
            // partitionIndex is partitioning index, for ensuring current partition index is in the right place
            int partitionIndex = partition(scoreList, low, high);

            // After ensuring about that partition index of scoreList is in the right place and sorted
            // Being sorted means every element before element of scoreList in partition index are smaller than or equal to it
            // And every element after element of scoreList in partition index are bigger than it
            // Because we are sorting in ascending order
            // For example, if value of partition index in scoreList is 4, every element before it must be smaller than or equal to4
            // and every element after it must be bigger than 4
            // We will do same operations to every element before and after partition index
            quickSort(scoreList, low, partitionIndex - 1);
            quickSort(scoreList, partitionIndex + 1, high);
        }
        // if high is smaller than low that means we finished the sorting
        // And we can return the sorted list
        return scoreList;
    }

    static int partition(ArrayList<Pair> scoreList, int low, int high) {
        // Choosing pivot element
        Pair pivot = scoreList.get(high);
        // Index of smaller element
        int i = low;

        for (int j = low; j < high; j++) {
            // if we found smaller element than pivot we should put it into available leftmost index of scoreList
            // To be able to sort in ascending order
            if (scoreList.get(j).numOfCommonFriends < pivot.numOfCommonFriends) {

                // swap ith element of scoreList and jth element of scoreList
                Pair temp = scoreList.get(i);
                scoreList.set(i, scoreList.get(j));
                scoreList.set(j, temp);

                // Increment i by one to detect next right place for putting next bigger element
                i++;
            }
        }

        // Then we should swap the pivot with i th element of the list
        // Because current i th index is the right place of pivot to be sorted
        // Which means if we put pivot into ith index we can be sure about that
        // value of every element before index i is smaller than or equal to pivot's value
        // and value of every element after index i is bigger than pivot's value
        Pair temp = scoreList.get(i);
        scoreList.set(i, scoreList.get(high));
        scoreList.set(high, temp);

        return i;
    }

    // HEAP SORT
    static ArrayList<Pair> heapSort(ArrayList<Pair> scoreList) {
        int size = scoreList.size();

        // Build heap (max heap because we want largest elements at the rightmost)
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(scoreList, size, i);
        }

        // One by one extract an element from heap
        for (int i = size - 1; i > 0; i--) {
            // Change places of first and last node
            Pair temp = scoreList.get(0);
            scoreList.set(0, scoreList.get(i));
            scoreList.set(i, temp);

            // Build max heap with reduced list again after swapping
            heapify(scoreList, i, 0);
        }

        // After everything is done, return the sorted array
        return scoreList;
    }

    static void heapify(ArrayList<Pair> scoreList, int size, int current) {
        // Current root is the given index(current)
        int largest = current;
        // Since array starting from 0, left child of a parent node is 2*current + 1
        int left = 2 * current + 1;
        // Since array starting from 0, right child of a parent node is 2*current + 2
        int right = 2 * current + 2;

        // If left child is bigger than root
        if (left < size && scoreList.get(left).numOfCommonFriends > scoreList.get(largest).numOfCommonFriends)
            largest = left;

        // If right child is bigger than largest so far
        if (right < size && scoreList.get(right).numOfCommonFriends > scoreList.get(largest).numOfCommonFriends)
            largest = right;

        // if our current is not equal to root, we should swap the variables
        if (largest != current) {
            Pair swap = scoreList.get(current);
            scoreList.set(current, scoreList.get(largest));
            scoreList.set(largest, swap);

            // Build max heap with reduced list again after swapping
            heapify(scoreList, size, largest);
        }
    }

    // MERGE SORT
    static ArrayList<Pair> mergeSort(ArrayList<Pair> scoreList, int left, int right) {
        if (left < right) {
            // Find the middle point
            int mid = (left + right) / 2;

            // Sort first and second half
            mergeSort(scoreList, left, mid);
            mergeSort(scoreList, mid + 1, right);

            // Merge the sorted first half and sorted second half
            merge(scoreList, left, mid, right);
        }

        // After everything is done, return the sorted array
        return scoreList;
    }

    static void merge(ArrayList<Pair> scoreList, int left, int mid, int right) {
        // Finding size of left half and right half
        int firstSize = mid - left + 1;
        int secondSize = right - mid;

        // Creating temporary left and right lists
        ArrayList<Pair> leftArray = new ArrayList<>();
        ArrayList<Pair> rightArray = new ArrayList<>();

        // Copying first and second half of the original array into new temporary lists
        for (int i = 0; i < firstSize; ++i) {
            leftArray.add(scoreList.get(left + i));
        }
        for (int j = 0; j < secondSize; ++j) {
            rightArray.add(scoreList.get(mid + 1 + j));
        }

        // Indexes for left and right half lists
        int i = 0, j = 0;
        // Index of list for merging
        int k = left;

        while (i < firstSize && j < secondSize) {
            // if left is smaller than or equal to right part we should put leftArray's element into merge list first
            if (leftArray.get(i).numOfCommonFriends <= rightArray.get(j).numOfCommonFriends) {
                scoreList.set(k, leftArray.get(i));
                i++;
            }
            // Otherwise, we should put rightArray's element first
            else {
                scoreList.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // If there are any element left inside the leftArray, copy rest of the elements into the main list
        while (i < firstSize) {
            scoreList.set(k, leftArray.get(i));
            i++;
            k++;
        }
        // If there are any element left inside the rightArray, copy rest of the elements into the main list
        while (j < secondSize) {
            scoreList.set(k, rightArray.get(j));
            j++;
            k++;
        }

    }

    //INSERTION SORT
    static ArrayList<Pair> insertionSort(ArrayList<Pair> scoreList) {
        // Size of scoreList
        int size = scoreList.size();
        // Initializing a pair node to use again and again
        Pair pivot;

        for (int i = 1; i < size; ++i) {
            // Pivot is the current i th index of scoreList
            pivot = scoreList.get(i);
            // j is the index just before the current pivot's index which is i
            int j = i - 1;

            // if j is bigger than or equal to 0 which means we have place to control
            // Now, we are trying to find the proper place to put our pivot
            // Looking before part of the list, if there is any bigger value than our pivot's value
            // Pivot goes left when there is no element bigger than it's value
            while (j >= 0 && scoreList.get(j).numOfCommonFriends > pivot.numOfCommonFriends) {
                // Changing place of j+1 th element with jth element
                scoreList.set(j + 1, scoreList.get(j));
                j--;
            }
            // (j+1)th index is the right place to put our pivot
            // because, there are no any bigger value than it's value at it's left
            scoreList.set(j + 1, pivot);
        }

        // After all finished we can return the scoreList
        return scoreList;
    }
}

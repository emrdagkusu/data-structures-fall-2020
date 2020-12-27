package P3_2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HDTestData {
    String serialNumber;
    String model;
    long capacity;
    int power;

    public HDTestData(String serialNumber, String model, long capacity, int power) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.capacity = capacity;
        this.power = power;
    }

    @Override
    public String toString() {
        return "HDTestData {" +
                "serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", power=" + power +
                '}';
    }
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
    static ArrayList<HDTestData> scanFile(File file) throws IOException {
        ArrayList<HDTestData> objects = new ArrayList<>();

        try {
            Scanner scan = new Scanner(readFile(file));
            scan.nextLine();

            while (scan.hasNextLine()) {
                String lines = scan.nextLine();
                String[] line = lines.split(",");
//                Creating new HDTestData object
                HDTestData newObject = new HDTestData(line[0], line[1], Long.parseLong(line[2]), Integer.parseInt(line[3]));
//                Adding into objects ArrayList
                objects.add(newObject);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return objects;
    }

    public static void main(String[] args) throws IOException {
//        IMPLEMENTING
        System.out.println("IMPLEMENTING");
//        Getting the object in the "data_tiny.csv" file
        URL inTiny = HDTestData.class.getResource("data_tiny.csv");
        File inputTiny = new File(inTiny.getPath());
//        tinyObjects ArrayList includes all elements from "data_tiny.csv"
        ArrayList<HDTestData> tinyObjects = scanFile(inputTiny);

//        Creating AVLTrees for each feature in "data_tiny.csv"
//        serial_number, model, capacity_bytes, and power_on_hours
        AVLTree<String> tiny_serial_number = new AVLTree<>();
        AVLTree<String> tiny_model = new AVLTree<>();
        AVLTree<Long> tiny_capacity_bytes = new AVLTree<>();
        AVLTree<Integer> tiny_power_on_hours = new AVLTree<>();

//        Inserting serialNumbers from data_tiny.csv into serial number tree
        for (HDTestData item : tinyObjects) {
            tiny_serial_number.insert(item.serialNumber);
        }
//        Inserting models from data_tiny.csv into model tree
        for (HDTestData item : tinyObjects) {
            tiny_model.insert(item.model);
        }
//        Inserting capacities from data_tiny.csv into capacity tree
        for (HDTestData item : tinyObjects) {
            tiny_capacity_bytes.insert(item.capacity);
        }
//        Inserting powers from data_tiny.csv into power tree
        for (HDTestData item : tinyObjects) {
            tiny_power_on_hours.insert(item.power);
        }
//        Printing elements in DFS with inorder traversal
        System.out.println("\n***INORDER DFS PRINTING***\n");
        System.out.println("SERIAL NUMBER TREE");
        tiny_serial_number.printInOrderDFS();
        System.out.println("\nMODEL TREE");
        tiny_model.printInOrderDFS();
        System.out.println("\nCAPACITY TREE");
        tiny_capacity_bytes.printInOrderDFS();
        System.out.println("\nPOWER TREE");
        tiny_power_on_hours.printInOrderDFS();

        System.out.println("\n----------------------------------\n");

//        Printing elements in BFS traversal
        System.out.println("BFS PRINTING\n");
        System.out.println("SERIAL NUMBER TREE");
        tiny_serial_number.printBFS(tiny_serial_number.root);
        System.out.println("\nMODEL TREE");
        tiny_model.printBFS(tiny_model.root);
        System.out.println("\nCAPACITY TREE");
        tiny_capacity_bytes.printBFS(tiny_capacity_bytes.root);
        System.out.println("\nPOWER TREE");
        tiny_power_on_hours.printBFS(tiny_power_on_hours.root);


//        TESTING
        System.out.println("\n***TESTING***\n");
//        Getting the object in the "data_main.csv" file
        URL inMain = HDTestData.class.getResource("data_main.csv");
        File inputMain = new File(inMain.getPath());
//        mainObjects ArrayList includes all elements from "data_main.csv"
        ArrayList<HDTestData> mainObjects = scanFile(inputMain);

//        New ArrayList for inserting n random objects
        ArrayList<HDTestData> randomObjects = new ArrayList<>();
//        New ArrayList for inserting m random objects to test later
        ArrayList<HDTestData> testObjects = new ArrayList<>();

        int size = mainObjects.size();
//        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        n and m can be changed from here
        int n = 10000, m = 10, r;
        Random random = new Random();

        for (int i = 0; i < n; i++) {
//            Get a random integer between 0 and size of the mainObjects
            r = random.nextInt(size);
//            Add that random indexed element into randomObject ArrayList
            randomObjects.add(mainObjects.get(r));
        }

        for (int i = 0; i < m; i++) {
//            Get a random integer between 0 and size of the mainObjects
            r = random.nextInt(size);
//            Add that random indexed element into testObject ArrayList to test later
            testObjects.add(mainObjects.get(r));
        }

//        Creating AVLTrees for each feature in "data_main.csv"
//        serial_number, model, capacity_bytes, and power_on_hours
        AVLTree<String> main_serial_number = new AVLTree<>();
        AVLTree<String> main_model = new AVLTree<>();
        AVLTree<Long> main_capacity_bytes = new AVLTree<>();
        AVLTree<Integer> main_power_on_hours = new AVLTree<>();

//        timer for calculating average time taken for insertions
        int timer = 0;
//        Start time
        Instant start = Instant.now();
//        Inserting serial numbers into serial number AVL tree
        for (HDTestData item : randomObjects) {
            main_serial_number.insert(item.serialNumber);
        }
//        End time
        Instant end = Instant.now();
//        Duration between start and end
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken insertion to serialNumberTree: " + timeElapsed.toMillis() + " milliseconds");
//        Add current duration onto time to calculate average time taken
        timer += timeElapsed.toMillis();

        start = Instant.now();
//        Inserting models into model AVL tree
        for (HDTestData item : randomObjects) {
            main_model.insert(item.model);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken insertion to modelTree       : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();

        start = Instant.now();
//        Inserting capacities into capacity AVl tree
        for (HDTestData item : randomObjects) {
            main_capacity_bytes.insert(item.capacity);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken insertion to capacityTree    : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();

        start = Instant.now();
//        Inserting powers into power AVL tree
        for (HDTestData item : randomObjects) {
            main_power_on_hours.insert(item.power);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken insertion to powerTree       : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();
        System.out.println("Average time taken for insertions       : " + timer / 4 + " milliseconds\n");

//        Timers for search in 4 different trees
        int serialNumberTimer = 0, modelTimer = 0, capacityTimer = 0, powerTimer = 0;
//            If it is not in the tree search function will return null and that means height of that feature is -1
        AVLNode current;
        for (HDTestData item : testObjects) {
            System.out.println(item.toString());
            start = Instant.now();
            System.out.println("Height of");
            current = main_serial_number.search(item.serialNumber);
            end = Instant.now();
            System.out.println("Serial Number = " + (current == null ? -1 : main_serial_number.height(current)));
            timeElapsed = Duration.between(start, end);
//            Adding duration onto serialNumberTimer to calculate average time taken for search in serialNumber tree
            serialNumberTimer += timeElapsed.toNanos();

            start = Instant.now();
            current = main_model.search(item.model);
            end = Instant.now();
            System.out.println("Model         = " + (current == null ? -1 : main_model.height(current)));
            timeElapsed = Duration.between(start, end);
//            Adding duration onto modelTimer to calculate average time taken for search in model tree
            modelTimer += timeElapsed.toNanos();

            start = Instant.now();
            current = main_capacity_bytes.search(item.capacity);
            end = Instant.now();
            System.out.println("Capacity      = " + (current == null ? -1 : main_capacity_bytes.height(current)));
            timeElapsed = Duration.between(start, end);
//            Adding duration onto capacityTimer to calculate average time taken for search in capacity tree
            capacityTimer += timeElapsed.toNanos();

            start = Instant.now();
            current = main_power_on_hours.search(item.power);
            end = Instant.now();
            System.out.println("Power         = " + (current == null ? -1 : main_power_on_hours.height(current)));
            timeElapsed = Duration.between(start, end);
//            Adding duration onto powerTimer to calculate average time taken for search in power tree
            powerTimer += timeElapsed.toNanos();
            System.out.println();
        }

//        Average times of search in each corresponding AVL tree for m many objects
        System.out.println("Average time taken for search in serialNumberTree   : " + serialNumberTimer / m + " nanoseconds");
        System.out.println("Average time taken for search in modelTree          : " + modelTimer / m + " nanoseconds");
        System.out.println("Average time taken for search in capacityTree       : " + capacityTimer / m + " nanoseconds");
        System.out.println("Average time taken for search in powerTree          : " + powerTimer / m + " nanoseconds\n");

//        Timers for deletion in 4 different trees
        int deleteSerialNumberTimer = 0, deleteModelTimer = 0, deleteCapacityTimer = 0, deletePowerTimer = 0;
//        Deleting from serialNumber tree
        for (HDTestData item : randomObjects) {
            start = Instant.now();
            main_serial_number.delete(item.serialNumber);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteSerialNumberTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in serialNumberTree : " + deleteSerialNumberTimer / m + " nanoseconds");

//        Deleting from model tree
        for (HDTestData item : randomObjects) {
            start = Instant.now();
            main_model.delete(item.model);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteModelTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in modelTree        : " + deleteModelTimer / m + " nanoseconds");

//        Deleting from capacity tree
        for (HDTestData item : randomObjects) {
            start = Instant.now();
            main_capacity_bytes.delete(item.capacity);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteCapacityTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in capacityTree     : " + deleteCapacityTimer / m + " nanoseconds");

//        Deleting from power tree
        for (HDTestData item : randomObjects) {
            start = Instant.now();
            main_power_on_hours.delete(item.power);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deletePowerTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in powerTree        : " + deletePowerTimer / m + " nanoseconds");
    }
}

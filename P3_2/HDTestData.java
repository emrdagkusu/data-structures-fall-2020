package P3_2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
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
        return "HDTestData{" +
                "serialNumber='" + serialNumber + '\'' +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", power=" + power +
                '}';
    }

    static String readFile(File file) throws IOException {
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

    static ArrayList<HDTestData> scanFile(File file) throws IOException {
        ArrayList<HDTestData> objects = new ArrayList<>();

        try {
            Scanner scan = new Scanner(readFile(file));
            scan.nextLine();
            int i = -1;

            while (scan.hasNextLine()) {
                String lines = scan.nextLine();
                String[] line = lines.split(",");
//                System.out.println(line[0].toString());
                HDTestData newObject = new HDTestData(line[0], line[1], Long.parseLong(line[2]), Integer.parseInt(line[3]));
                newObject.toString();

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
        URL inTiny = HDTestData.class.getResource("data_tiny.csv");
        File inputTiny = new File(inTiny.getPath());
        ArrayList<HDTestData> tinyObjects = scanFile(inputTiny);

//      serial_number, model, capacity_bytes, and power_on_hours
        AVLTree<String> tiny_serial_number = new AVLTree<>();
        AVLTree<String> tiny_model = new AVLTree<>();
        AVLTree<Long> tiny_capacity_bytes = new AVLTree<>();
        AVLTree<Integer> tiny_power_on_hours = new AVLTree<>();

        for (HDTestData item :
                tinyObjects) {
            tiny_serial_number.insert(item.serialNumber);
        }
        for (HDTestData item :
                tinyObjects) {
            tiny_model.insert(item.model);
        }
        for (HDTestData item :
                tinyObjects) {
            tiny_capacity_bytes.insert(item.capacity);
        }
        for (HDTestData item :
                tinyObjects) {
            tiny_power_on_hours.insert(item.power);
        }
        System.out.println("\nINORDER DFS PRINTING\n");
        System.out.println("SERIAL NUMBER TREE");
        tiny_serial_number.inOrderDFS(tiny_serial_number.getRoot());
        System.out.println("\nMODEL TREE");
        tiny_model.inOrderDFS(tiny_model.getRoot());
        System.out.println("\nCAPACITY TREE");
        tiny_capacity_bytes.inOrderDFS(tiny_capacity_bytes.getRoot());
        System.out.println("\nPOWER TREE");
        tiny_power_on_hours.inOrderDFS(tiny_power_on_hours.getRoot());
        System.out.println("\n\n----------------------------------\n");
        System.out.println("\nBFS PRINTING\n");
        System.out.println("SERIAL NUMBER TREE");
        tiny_serial_number.BFS(tiny_serial_number.getRoot());
        System.out.println("\nMODEL TREE");
        tiny_model.BFS(tiny_model.getRoot());
        System.out.println("\nCAPACITY TREE");
        tiny_capacity_bytes.BFS(tiny_capacity_bytes.getRoot());
        System.out.println("\nPOWER TREE");
        tiny_power_on_hours.BFS(tiny_power_on_hours.getRoot());


//        TESTING
        System.out.println("\nTESTING\n");
        URL inMain = HDTestData.class.getResource("data_main.csv");
        File inputMain = new File(inMain.getPath());
        ArrayList<HDTestData> mainObjects = scanFile(inputMain);
        ArrayList<HDTestData> randomObjects = new ArrayList<>();
        ArrayList<HDTestData> testObjects = new ArrayList<>();

        int size = mainObjects.size();
        int n = 10000, m = 10, r;
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            r = random.nextInt(size);
            randomObjects.add(mainObjects.get(r));
        }

        for (int i = 0; i < m; i++) {
            r = random.nextInt(size);
            testObjects.add(mainObjects.get(r));
        }

//      serial_number, model, capacity_bytes, and power_on_hours
        AVLTree<String> main_serial_number = new AVLTree<>();
        AVLTree<String> main_model = new AVLTree<>();
        AVLTree<Long>  main_capacity_bytes = new AVLTree<>();
        AVLTree<Integer> main_power_on_hours = new AVLTree<>();

        int timer = 0;
        Instant start = Instant.now();
        for (HDTestData item :
                randomObjects) {
            main_serial_number.insert(item.serialNumber);
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for serialNumberTree: " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();
        start = Instant.now();
        for (HDTestData item :
                randomObjects) {
            main_model.insert(item.model);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for modelTree       : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();
        start = Instant.now();
        for (HDTestData item :
                randomObjects) {
            main_capacity_bytes.insert(item.capacity);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for capacityTree    : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();
        start = Instant.now();
        for (HDTestData item :
                randomObjects) {
            main_power_on_hours.insert(item.power);
        }
        end = Instant.now();
        timeElapsed = Duration.between(start, end);
        System.out.println("Time taken for powerTree       : " + timeElapsed.toMillis() + " milliseconds");
        timer += timeElapsed.toMillis();
        System.out.println("Average time taken             : " + timer / 4 + " milliseconds\n");

        int serialNumberTimer = 0, modelTimer = 0, capacityTimer = 0, powerTimer = 0;
        for (HDTestData item :
                testObjects) {
            System.out.println(item.toString());
            start = Instant.now();
            System.out.println("Serial Number: " + (main_serial_number.search(item.serialNumber) == null ? -1 : main_serial_number.search(item.serialNumber).height));
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            serialNumberTimer += timeElapsed.toNanos();
            start = Instant.now();
            System.out.println("Model        : " + (main_model.search(item.model) == null ? -1 : main_model.search(item.model).height));
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            modelTimer += timeElapsed.toNanos();
            start = Instant.now();
            System.out.println("Capacity     : " + (main_capacity_bytes.search(item.capacity) == null ? -1 : main_capacity_bytes.search(item.capacity).height));
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            capacityTimer += timeElapsed.toNanos();
            start = Instant.now();
            System.out.println("Power        : " + (main_power_on_hours.search(item.power) == null ? -1 : main_power_on_hours.search(item.power).height));
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            powerTimer += timeElapsed.toNanos();
            start = Instant.now();
            System.out.println();
        }

        System.out.println("Average time taken for search in serialNumberTree   : " + serialNumberTimer/m + " nanoseconds");
        System.out.println("Average time taken for search in modelTree          : " + modelTimer/m + " nanoseconds");
        System.out.println("Average time taken for search in capacityTree       : " + capacityTimer/m + " nanoseconds");
        System.out.println("Average time taken for search in powerTree          : " + powerTimer/m + " nanoseconds\n");

        timer = 0;
        int deleteSerialNumberTimer = 0, deleteModelTimer = 0, deleteCapacityTimer = 0, deletePowerTimer = 0;
        for (HDTestData item :
                randomObjects) {
            start = Instant.now();
            main_serial_number.insert(item.serialNumber);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteSerialNumberTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in serialNumberTree : " + deleteSerialNumberTimer/m + " nanoseconds");

        for (HDTestData item :
                randomObjects) {
            start = Instant.now();
            main_model.insert(item.model);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteModelTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in modelTree        : " + deleteModelTimer/m + " nanoseconds");

        for (HDTestData item :
                randomObjects) {
            start = Instant.now();
            main_capacity_bytes.insert(item.capacity);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deleteCapacityTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in capacityTree     : " + deleteCapacityTimer/m + " nanoseconds");

        for (HDTestData item :
                randomObjects) {
            start = Instant.now();
            main_power_on_hours.insert(item.power);
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            deletePowerTimer += timeElapsed.toNanos();
        }
        System.out.println("Average time taken for deletion in powerTree        : " + deletePowerTimer/m + " nanoseconds");



    }
}

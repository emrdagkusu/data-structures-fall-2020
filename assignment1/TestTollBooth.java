package assignment1;

import java.util.Scanner;

public class TestTollBooth {
    public static void main(String [] args){
        TollBooth booth = new AlleghenyTollBooth();

        Truck bmc = new BMCTruck(5,12500 );
        Truck nissan = new NissanTruck(6, 15000); // 2 axles and 5000kg
        Truck ford = new FordTruck(5, 12500); // 5 axles and 12000 kilograms

        Truck[] array = new Truck[3];
        array[0] = bmc;
        array[1] = nissan;
        array[2] = ford;

//        booth.calculateToll(ford);
//        booth.calculateToll(nissan);
//        booth.totalData();
//        booth.calculateToll(nissan);
//        booth.calculateToll(ford);
//        booth.displayData();
//        booth.calculateToll(ford);
//        booth.calculateToll(nissan);
//        booth.displayData();
//        booth.totalData();

        String actions = "1: Scan Barcode\n" +
                         "2: Display Data\n" +
                         "3: Restartmeter";

        int index = 0;
        while (true) {
            System.out.println(actions);
            Scanner scan = new Scanner(System.in);
            int option = scan.nextInt();

            if (option == 1) {
                if (index < 3) {
                    booth.calculateToll(array[index]);
                } else {
                    System.out.println("No car name!");
                }
                index++;
            } else if (option == 2) {
                booth.totalData();
            } else if (option == 3) {
                booth.displayData();
            } else {
                System.out.println("Quitting!");
                break;
            }
        }


    }
}

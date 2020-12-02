package assignment1;

public class AlleghenyTollBooth implements TollBooth {
    public int total = 0;
    public int tollDue;
    public static int numOfTrucks = 0;


    @Override
    public void calculateToll(Truck truck) {
        if (truck.getWeight() != 0 || truck.getNumOfAxles() != 0) {
            numOfTrucks++;
            tollDue = (truck.getNumOfAxles() * 5) + ((truck.getWeight() / 1000) * 10);
            total += tollDue;
            String text = "Truck arrival - Axles: " + truck.getNumOfAxles() + " Total Weight: " + truck.getWeight() + " Toll due: $" + tollDue;
            System.out.println(text);

        }
    }

    @Override
    public void totalData() {
        String text2 = "Total since last collection - Receipt: $" + total + " Trucks: " + numOfTrucks;
        System.out.println(text2);
    }

    @Override
    public void displayData() {
        System.out.println("***  Collecting receipts  ***");
        String text2 = "Total since last collection - Receipt: $" + total + " Trucks: " + numOfTrucks;
        System.out.println(text2);
        total = 0;
        numOfTrucks = 0;
    }

}

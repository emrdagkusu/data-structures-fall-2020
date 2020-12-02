package assignment1;

public class BMCTruck implements Truck{
    private int numOfAxles;
    private int weight;

    public BMCTruck(int numOfAxles, int weight) {
        this.numOfAxles = numOfAxles;
        this.weight = weight;
    }

    public BMCTruck(int numOfAxles) {
        this.numOfAxles = 0;
        weight = 0;
        System.out.println("There must be information of weight!");
    }

    public BMCTruck() {
        numOfAxles = 0;
        weight = 0;
        System.out.println("There must be information of weight and number of axles!");
    }

    public int getNumOfAxles() {
        return numOfAxles;
    }

    public void setNumOfAxles(int numOfAxles) {
        this.numOfAxles = numOfAxles;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

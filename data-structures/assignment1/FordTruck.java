package assignment1;

public class FordTruck implements Truck {
    private int numOfAxles;
    private int weight;

    public FordTruck(int numOfAxles, int weight) {
        this.numOfAxles = numOfAxles;
        this.weight = weight;
    }

    public FordTruck(int numOfAxles) {
        this.numOfAxles = 0;
        weight = 0;
        System.out.println("There must be information of weight!");
    }

    public FordTruck() {
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

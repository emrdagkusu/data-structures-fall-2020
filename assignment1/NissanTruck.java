package assignment1;

public class NissanTruck implements Truck {
    private int numOfAxles;
    private int weight;

    public NissanTruck(int numOfAxles, int weight) {
        this.numOfAxles = numOfAxles;
        this.weight = weight;
    }

    public NissanTruck(int numOfAxles) {
        this.numOfAxles = 0;
        weight = 0;
        System.out.println("There must be information of weight!");
    }

    public NissanTruck() {
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

import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<List<Node>> adjacencyList = new ArrayList<>();

    Graph(int size) {
        // Adjacency list initializing
        for (int i = 0; i <= size; i++) {
            adjacencyList.add(i, new ArrayList<>());
        }
    }

    // Insertion method for friendships
    public void insert(Edge edge) {
        adjacencyList.get(edge.source).add(new Node(edge.destination, edge.weight));
    }

    // Look if there is any edge between given pair
    public boolean hasEdge(int source, int destination) {
        int size = adjacencyList.get(source).size();
        for (int i = 0; i < size; i++) {
            if (adjacencyList.get(source).get(i).data == destination) {
                return true;
            }
        }

        return false;
    }

    // Finding and printing adjacent nodes of a given id
    public void findEdges(int source) {
        for (Node edge : adjacencyList.get(source)) {
            System.out.print("(" + source + "," + edge.data +
                    ")\t" + edge.weight + "\n");
        }
    }

    // Finding number of common friends of a given pair
    // Takes first id's all friends and then second id's all friend
    // And when it got the same, increment the counter by one, at the end returns the value of it
    public int numberOfCommonFriends(int first, int second) {
        int friendNumber = 0;
        int sizeOfSource = adjacencyList.get(first).size();
        int sizeOfDestination = adjacencyList.get(second).size();

        for (int i = 0; i < sizeOfSource; i++) {
            for (int j = 0; j < sizeOfDestination; j++) {
                if (adjacencyList.get(first).get(i).data == adjacencyList.get(second).get(j).data) {
                    friendNumber++;
                }
            }
        }

        return friendNumber;
    }


    // print adjacency list for the graph
    public void printGraph() {
        int counter = 0;
        int size = adjacencyList.size();

        System.out.println("***Print the Graph***");
        while (counter < size) {
            // Starting from first id it goes all the way around in the graph and print them
            for (Node edge : adjacencyList.get(counter)) {
                System.out.print("(" + counter + "," + edge.data + ") " +
                         + edge.weight + "\t");
            }
            // If the current ID has not any edge, don't go next line
            // Otherwise, after printing current ID's every edge, go next line
            if (adjacencyList.get(counter).size() > 0) {
                System.out.println();
            }

            // Increment the counter by one to get next ID
            counter++;
        }
    }
}

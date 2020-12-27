package P3_1;


public class BSTNode {
    BSTNode left;
    BSTNode right;
    String key;
    int depth;

    public BSTNode(String key) {
        this.key = key;
        right = left = null;
    }
}

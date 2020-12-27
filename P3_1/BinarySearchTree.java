package P3_1;

import java.time.Duration;
import java.time.Instant;

public class BinarySearchTree {
    public BSTNode root;
//    To be able to find depth
    private int currentDepth;

    BinarySearchTree() {
        root = null;
    }

    public BSTNode search(String key) {
        return searchItem(root, key);
    }

    //      To find the item that we look there is a recursive function with parameters node and given key
    private BSTNode searchItem(BSTNode node, String key) {
//        If the node is null that means node with given key is not in the BST
        if (node == null) {
            return null;
        }
//        compareTo method gives -1 if first variable smaller than second one, gives 0 if they are equal,
//        give 1 if first variable bigger than second one
//        if key and current.key is equal to each other that means we found the node with given key
        else if (node.key.compareTo(key) == 0) {
            return node;
        }

//        If the result of comparison is equal to or smaller than 0,
//        it should go left because my BST place duplicates in the left side
        if (key.compareTo(node.key) <= 0) {
            return searchItem(node.left, key);
        }
//        If the result of comparison is bigger than 0, it should go right side
        else {
            return searchItem(node.right, key);
        }
    }

//    findHeight function to find the height of subtree
//    recursive function with parameter current node
    public int findHeight(BSTNode node) {
        if (node == null) {
            return 0;
        } else {
            return findHeight(node.left) + findHeight(node.right) + 1;
        }
    }

//    Finding the minimum node and the time taken to find the node
    public void searchMin() {
        Instant start = Instant.now();
        BSTNode min = searchMinNode(root);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: " + timeElapsed.toNanos() + " nanoseconds | Minimum value: " + min.key);
    }

//    Finding minimum node which means leftmost node of the tree
//    If the left child is not null we should go left and then return the minimum node
    private BSTNode searchMinNode(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

//    Finding the maximum node and the time taken to find the node
    public void searchMax() {
        Instant start = Instant.now();
        BSTNode max = searchMaxNode(root);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: " + timeElapsed.toNanos() + " nanoseconds | Maximum value: " + max.key);

    }

//    Finding maximum node which means rightmost node of the tree
//    If the right child is not null we should go right and then return the maximum node
    private BSTNode searchMaxNode(BSTNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public void insert(String key) {
        root = insertItem(root, key);
    }

//    insertItem function to insert and element to tree
//    Recursive function with parameters node and given key
    private BSTNode insertItem(BSTNode node, String key) {
//        If node is null that means we found the right place to put our new node
        if (node == null) {
            node = new BSTNode(key);
//            depth of the new node will be parent node depth + 1
            node.depth = currentDepth + 1;
            return node;
        }
//        To find the depth of parent node
        currentDepth = node.depth;

//        If result of comparison is equal to or smaller than 0, it should go left
//        which means we will put duplicate nodes to the left
        if (key.compareTo(node.key) <= 0) {
            node.left = insertItem(node.left, key);
        }
//        If result of comparison is bigger than 0, it should go right
        else {
            node.right = insertItem(node.right, key);
        }
//        Finally return the current node
        return node;
    }

    public void delete(String key) {
        root = deleteItem(root, key);
    }

//    deleteItem function to delete and element from tree
//    Recursive function with parameters node and given key
    private BSTNode deleteItem(BSTNode node, String key) {
//        If node is null that means given key is not in the BST
        if (node == null) {
            return null;
        }

//        If result of comparison is smaller than 0, which means given key is left side of the current node
        if (key.compareTo(node.key) < 0) {
            node.left = deleteItem(node.left, key);
        }
//        If result of comparison is bigger than 0, which means given key is right side of the current node
        else if (key.compareTo(node.key) > 0) {
            node.right = deleteItem(node.right, key);

        }
//        If result of comparison is equal to 0, which means we found the node with given key
        else {
//            If the current node has only one child
//            If left child is null, that means right child will take current node's place
            if (node.left == null) {
                return node.right;
            }
//            If right child is null, that means left child will take current node's place
            else if (node.right == null) {
                return node.left;
            } else {

//           Or our node can have both right and left child
//           Leftmost child of right child of the current node's key will be equal to current node's key
                node.key = searchMinNode(node.right).key;

//            After replacing leftmost child and current it should make leftmost child null
                node.right = deleteItem(node.right, node.key);
            }
        }

//        Finally return the current node
        return node;
    }

    public void printInorder() {
        inorderDFS(root);
    }

//    Recursive function to print our nodes in DFS inorder way
    private void inorderDFS(BSTNode root) {
        if (root != null) {
            inorderDFS(root.left);
            System.out.println(root.key);
            inorderDFS(root.right);
        }
    }
}

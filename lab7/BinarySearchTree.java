package lab7;

public class BinarySearchTree {
    Node root;

    BinarySearchTree(Node root) {
        this.root = root;
        System.out.printf("Root node is equal to %d\n", root.data);
    }

//    find function takes number as a parameter
    public boolean find(int number) {
//        There is another function to determine if the given number is in the BST
//        This function gives boolean value
        boolean result = findItem(root, number);

//        If the given boolean value is true that means given number is in the BST
        if (result) {
            System.out.printf("(Find) The given number %d is in the Binary Search Tree\n", number);
        }
//        If the given boolean value is false that means given number is not in the BST
        else {
            System.out.printf("(Find) The given number %d is not in the Binary Search Tree\n", number);
        }

//        Returning the boolean result
        return result;
    }

//    Recursive findItem function that takes node and given number as parameters
    private boolean findItem(Node node, int number)
    {
//        If the current node is null that means we searched whole BST but we couldn't find the given number
        if (node == null) {
            return false;
        }
//        If the current node is equal to given number that means we searched the BST and found the given number
        else if (node.data == number) {
            return true;
        }

//        Recursive / That looks whether the given number is bigger or smaller than the current node's data
        if (number > node.data) {
            return findItem(node.right, number);
        } else {
            return findItem(node.left, number);
        }
    }

//    insert function takes number as a parameter
    public void insert(int number) {
//        Looks if the given number is already inserted into the BST
//        if not insert the given number
        if (!findItem(root, number)) {
            insertItem(root, number);
            System.out.printf("(Insert) %d\n", number);
        }
//        else display the message
        else {
            System.out.printf("%d is already in the Binary Search Tree\n", number);
        }
    }

//    Recursive insertItem function that takes node and given number as parameters
    private Node insertItem(Node node, int number) {
//        If we found a null place after recursive we can add given number in that place
        if (node == null)
        {
            node = new Node(number);
            return node;
        }

//        Recursive / That looks whether the given number is bigger or smaller than the current node's data
//        if number is smaller than current node's data we should go to the left node
        if (number < node.data) {
            node.left = insertItem(node.left, number);
        }
//        if number is bigger than current node's data we should go to the right node
        else if (number > root.data) {
            node.right = insertItem(node.right, number);
        }

//        Then finally return the node
        return node;
    }

//    Recursive display function that takes root node as parameter
    public void display(Node root) {
//        Display the BST with Inorder Traversal
//        if give node is null that means we are done with that direction
        if (root != null) {
//            Looking left node
            display(root.left);
//            Printing current node
            System.out.println(root.data);
//            Looking right node
            display(root.right);
        }
    }
}

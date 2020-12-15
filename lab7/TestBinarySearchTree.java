package lab7;

public class TestBinarySearchTree {
    public static void main(String[] args) {
//        Since display function needs root Node as a parameter we should create a root Node in here
//        To be able to send root Node to display function
        Node root = new Node(20);
//        Then we can initialize a Binary Search Tree with the created root Node
        BinarySearchTree binarySearchTree = new BinarySearchTree(root);

//        Inserting few elements into the BST
        binarySearchTree.insert(25);
        binarySearchTree.insert(17);
        binarySearchTree.insert(15);
        binarySearchTree.insert(30);
        binarySearchTree.insert(35);
        binarySearchTree.insert(13);
        System.out.println("Display the Binary Search Tree with Inorder Traversal");
        binarySearchTree.display(root);
        System.out.println("-----------------------------------------------------");
//        Finding whether elements inside the BST
        System.out.println(binarySearchTree.find(25));
        System.out.println(binarySearchTree.find(47));
//        If we try an element that is already inside the BST
        binarySearchTree.insert(13);

    }
}

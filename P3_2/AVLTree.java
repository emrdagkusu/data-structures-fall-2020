package P3_2;

import java.util.LinkedList;
import java.util.Queue;

//  E used to make the AVL tree generic
public class AVLTree<E extends Comparable<E>> {

    public AVLNode<E> root;

//    Non recursive way to find the given key's node
    public AVLNode<E> search(E key) {
//        We have a temporary node to go all the way without changing the root node
        AVLNode<E> current = root;
//        To be able to search without recursion while loop is necessary
        while (current != null) {
//            compareTo method gives -1 if first variable smaller than second one, gives 0 if they are equal
//            give 1 if first variable bigger than second one
//            if key and current.key is equal to each other that means we found the node with given key
            if (key.compareTo(current.key) == 0) {
                return current;
            }

//            If the result of comparison is equal to or smaller than 0,
//            it should go left because my AVL tree place duplicates in the left side
            if (key.compareTo(current.key) <= 0) {
                current = current.left;
            }
//            If the result of comparison is bigger than 0, it should go right side
            else {
                current = current.right;
            }
        }
//        Finally return null that means node with given key is not in the AVL tree
        return null;
    }

    public void insert(E key) {
        root = insertItem(root, key);
    }

//    Recursive insertItem method with parameters node and key
    private AVLNode<E> insertItem(AVLNode<E> node, E key) {
//        If the current node is null, that means node with given key is not in the AVL tree
        if (node == null) {
            return new AVLNode<E>(key);
        }

//        If result of comparison is equal to or smaller than 0, it should go left side
//        In my  AVL tree it puts duplicates to the left side because of that equal to or smaller than
        if (key.compareTo(node.key) <= 0) {
            node.left = insertItem(node.left, key);
        }
//        If result of comparison is bigger than 0, it should go right side
        else {
            node.right = insertItem(node.right, key);
        }

//        After finding the right place to put we should determine if the AVL tree needs balancing
        return balanceAgain(node);
    }

    public void delete(E key) {
        root = deleteItem(root, key);
    }

//    Recursive deleteItem method with parameters node and key
    private AVLNode<E> deleteItem(AVLNode<E> node, E key) {
//        If the current node is null, that means node with given key is not in the AVL tree
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
                node = node.right;
            }
//            If right child is null, that means left child will take current node's place
            else if (node.right == null) {
                node = node.left;
            } else {

//           Or our node can have both right and left child
//           Leftmost child of right child of the current node's key will be equal to current node's key
                node.key = searchMinNode(node.right).key;

//            After replacing leftmost child and current it should make leftmost child null
                node.right = deleteItem(node.right, node.key);
            }
        }

//        After all if node is not null we should balance the AVL tree again
        if (node != null) {
            node = balanceAgain(node);
        }

//        Finally return the current node
        return node;
    }

    private AVLNode<E> searchMinNode(AVLNode<E> node) {
//        Go left till it is null
        while (node.left != null) {
            node = node.left;
        }
//        Return the current node which is the leftmost node
        return node;
    }

//    After insertion and deletion we should look whether the AVL tree is balanced
    private AVLNode<E> balanceAgain(AVLNode<E> node) {
//        Update the height of the new node
        changeHeight(node);

//        If the height of right subtree is bigger than height of left subtree we should rotate left (Left Rotation)
        if (balanceResult(node) > 1) {
//            However, we should look do we need to do right rotation before left rotation (Right Left Rotation)
            if (height(node.right.right) <= height(node.right.left)) {
                node.right = rightRotation(node.right);
            }
            node = leftRotation(node);
        }
//        If the height of left subtree is bigger than height of right subtree we should rotate right (Right Rotation)
        else if (balanceResult(node) < -1) {
//            However, we should look do we need to do left rotation before right rotation (Left Right Rotation)
            if (height(node.left.left) <= height(node.left.right)) {
                node.left = leftRotation(node.left);
            }
            node = rightRotation(node);
        }
//        Finally return the current node
        return node;
    }

    private AVLNode<E> rightRotation(AVLNode<E> node) {
        AVLNode<E> currentLeft = node.left;
        AVLNode<E> rightOfCurrentLeft = currentLeft.right;

//        Place the given node as right child of the currentLeft
        currentLeft.right = node;
//        Place rightOfCurrentLeft as left child of given node
        node.left = rightOfCurrentLeft;

//        Update heights of replaced nodes
        changeHeight(node);
        changeHeight(currentLeft);

//        Finally returning the currentLeft node to be able to use in balanceAgain function
        return currentLeft;
    }

    private AVLNode<E> leftRotation(AVLNode<E> node) {
        AVLNode<E> currentRight = node.right;
        AVLNode<E> leftOfCurrentRight = currentRight.left;

//        Place the given node as left child of the currentRight
        currentRight.left = node;
//        Place leftOfCurrentRight as right child of given node
        node.right = leftOfCurrentRight;

//        Update heights of replaced nodes
        changeHeight(node);
        changeHeight(currentRight);

//        Finally returning the currentRight node to be able to use in balanceAgain function
        return currentRight;
    }

//    Height of the given node will be biggest one of heights of left and right children + 1
    private void changeHeight(AVLNode<E> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

//    If given node is null height is -1 else return the node's height
    public int height(AVLNode<E> node) {
        return node == null ? -1 : node.height;
    }

//    Looking whether the AVL tree is balanced, if it is balanced it will give 0
//    if the result is bigger than 1, if the height of right subtree is bigger than the height of left subtree
//    if the result is smaller than -1, if the height of left subtree is bigger than the height of right subtree
    public int balanceResult(AVLNode<E> node) {
        if (node == null) {
           return 0;
        } else {
            return height(node.right) - height(node.left);
        }
    }

    public void printInOrderDFS() {
        inorderDFS(root);
    }
//    Recursive function to print the AVL tree with DFS inorder traversal
    void inorderDFS(AVLNode<E> node) {
        if (node != null) {
            inorderDFS(node.left);
            System.out.println(node.key + " " + node.height);
            inorderDFS(node.right);
        }
    }

//    printBFS function to print the AVL tree with BFS traversal
    void printBFS(AVLNode<E> node) {
//        Queue used for printing in breadth first search traversal
        Queue<AVLNode> queue = new LinkedList<AVLNode>();

//        if the root is null which means there is no node in the AVL tree, and we can finish without printing
        if (root == null) {
            System.exit(0);
        }

//        If root is not null we add it into queue
        queue.add(root);
//        We continue for looking inside the queue until it is being empty
        while (!queue.isEmpty()) {

//            poll() is used for removing the current head of the queue
            AVLNode current = queue.poll();
//            After removing the current head we print it's key and height information
            System.out.println(current.key + " " + height(current));

//            If current node (ex head) has left child, we add it into the queue
            if (current.left != null) {
                queue.add(current.left);
            }

//            If current node (ex head) has right child, we add it into the queue
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }
}

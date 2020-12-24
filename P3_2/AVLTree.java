package P3_2;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<E extends Comparable<E>> {

    private AVLNode<E> root;

    public AVLNode<E> search(E key) {
        AVLNode<E> current = root;

        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) {
                return current;
            } else if (comparison < 0) {
                current = current.left;
            } else { //comparison > 0
                current = current.right;
            }
        }

        return current;
    }

    public void insert(E key) {
        root = insert(root, key);
    }

    public void delete(E key) {
        root = delete(root, key);
    }

    public AVLNode getRoot() {
        return root;
    }

    public int height() {
        return root == null ? - 1 : root.height;
    }

    private AVLNode<E> insert(AVLNode<E> node, E key) {
        if (node == null) {
            return new AVLNode<E>(key);
        } else if (key.compareTo(node.key) < 0) {
            node.left = insert(node.left, key);
        } else if (key.compareTo(node.key) >= 0) {
            node.right = insert(node.right, key);
        }
//        else {
//            throw new RuntimeException("duplicate Key!");
//        }
        return rebalance(node);
    }

    private AVLNode<E> delete(AVLNode<E> node, E key) {
        if (node == null) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            node.left = delete(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                AVLNode<E> mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    private AVLNode<E> mostLeftChild(AVLNode<E> node) {
        AVLNode<E> current = node;
        /* loop down to find the leftmost leaf */
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private AVLNode<E> rebalance(AVLNode<E> z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right)) {
                z = rotateRight(z);
            } else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    private AVLNode<E> rotateRight(AVLNode<E> y) {
        AVLNode<E> x = y.left;
        AVLNode<E> z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode<E> rotateLeft(AVLNode<E> y) {
        AVLNode<E> x = y.right;
        AVLNode<E> z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private void updateHeight(AVLNode<E> n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    private int height(AVLNode<E> n) {
        return n == null ? -1 : n.height;
    }

    public int getBalance(AVLNode<E> n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    void inOrderDFS(AVLNode<E> node) {
        if (node != null) {
            inOrderDFS(node.left);
            System.out.println(node.key + " " + node.height);
            inOrderDFS(node.right);
        }
    }

    void BFS(AVLNode<E> node) {
        Queue<AVLNode> queue = new LinkedList<AVLNode>();
        if (root == null) System.exit(0);
        queue.add(root);
        while (!queue.isEmpty())
        {

            // poll() removes the present head.
            AVLNode<E> tempNode = queue.poll();
            System.out.println(tempNode.key + " " + tempNode.height );

            /*Enqueue left child */
            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }

            /*Enqueue right child */
            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
    }

}

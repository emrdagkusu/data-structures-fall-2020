package P3_2;

public class AVLNode<E> {
    public AVLNode<E> left;
    public AVLNode<E> right;
    public E key;
    public int height;

    public AVLNode(E key) {
        this.key = key;
    }
}

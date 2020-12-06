package lab6_2;

public class ArrayStack<E> implements Stack<E> {
  /** Default array capacity. */
//  To see the if the stack is working properly, CAPACITY is decreased to 5
  public static final int CAPACITY=5;   // default array capacity

  /** Generic array used for storage of stack elements. */
  private E[] data;                        // generic array used for storage

  /** Index of the top element of the stack in the array. */
  private int t = -1;                      // index of the top element in stack

//  Added new element to point oldest element in the stack
  private int l = 0;

  /** Constructs an empty stack using the default array capacity. */
  public ArrayStack() { this(CAPACITY); }  // constructs stack with default capacity

  /**
   * Constructs and empty stack with the given array capacity.
   * @param capacity length of the underlying array
   */
  @SuppressWarnings({"unchecked"})
  public ArrayStack(int capacity) {        // constructs stack with given capacity
    data = (E[]) new Object[capacity];     // safe cast; compiler may give warning
  }

  /**
   * Returns the number of elements in the stack.
   * @return number of elements in the stack
   */
  @Override
  public int size() {
//    size equals to index of last element minus index of oldest element
//    And then +1 because at the beginning, the difference between t and l is 1
//    % CAPACITY is to staying in the bound of the stack
    int size = ((t - l + 1) % CAPACITY);
//    size can be equal to 0 in 2 situation, either size is really 0 or size is equal to 5
//    To prevent this there is a if block whether size is 0 or 5
    if (size == 0 && t > l) {
      size = 5;
    }
    return size;
  }

  /**
   * Tests whether the stack is empty.
   * @return true if the stack is empty, false otherwise
   */
  @Override
  public boolean isEmpty() { return (t - l == -1); }

  /**
   * Inserts an element at the top of the stack.
   * @param e   the element to be inserted
   * @throws IllegalStateException if the array storing the elements is full
   */
  @Override
  public void push(E e) throws IllegalStateException {
//    If index of the new element's position's % CAPACITY is equal to oldest element's position's % CAPACITY
//    We should shift 1 the oldest element and then we can let the ex oldest element to leak to put the new
//      element to that position
//    The second part condition of the if block prevent the oldest element's position if array is not full yet
    if ((t + 1) % CAPACITY == l % CAPACITY && (t + 1) != 0) l++;
    data[++t % CAPACITY] = e;
  }

  /**
   * Returns, but does not remove, the element at the top of the stack.
   * @return top element in the stack (or null if empty)
   */
  @Override
  public E top() {
    if (isEmpty()) return null;
    return data[t];
  }

  /**
   * Removes and returns the top element from the stack.
   * @return element removed (or null if empty)
   */
  @Override
  public E pop() {
//    If an element is popped and the element in the 1 position below is the oldest element
//    oldest element should be the element just before
    if ((t - 1) % CAPACITY == l) l--;
    if (isEmpty() || data[l % CAPACITY] == null) return null;
    E answer = data[t % CAPACITY];
    data[t % CAPACITY] = null;                        // dereference to help garbage collection
    t--;
    return answer;
  }

  /**
   * Produces a string representation of the contents of the stack.
   * (ordered from top to bottom). This exists for debugging purposes only.
   *
   * @return textual representation of the stack
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    for (int j = t; j >= l; j--) {
      sb.append(data[j % CAPACITY]);
      if (j > l) sb.append(" ,");
    }
    sb.append(")");
    return sb.toString();
  }

  /** Demonstrates sample usage of a stack. */
  public static void main(String[] args) {
    Stack<Integer> S = new ArrayStack<>();  // contents: ()
    S.push(5);                              // contents: (5)
    S.push(3);                              // contents: (5, 3)
    S.push(7);                              // contents: (7)
    S.push(9);                              // contents: (7, 9)
    S.push(4);                              // contents: (7, 9, 4)
    System.out.println(S.toString());
    S.push(6);                              // contents: (7, 9, 6)
    S.push(8);                              // contents: (7, 9, 6, 8)
    S.push(5);                              // contents: (7, 9, 6, 8)
    System.out.println(S.toString());
    System.out.println(S.size());           // contents: (7, 9, 4)  outputs 3
    System.out.println(S.pop());            // contents: (7, 9, 6)  outputs 8
    System.out.println(S.toString());
    System.out.println(S.size());           // contents: (7, 9, 4)  outputs 3
    S.push(6);                              // contents: (7, 9, 6, 8)
    S.push(7);                              // contents: (7, 9, 6, 8)
    S.push(8);                              // contents: (7, 9, 6, 8)
    S.push(9);                              // contents: (7, 9, 6, 8)
    System.out.println(S.toString());
    System.out.println(S.pop());            // contents: (7, 9)     outputs 4
    System.out.println(S.pop());            // contents: (7, 9)     outputs 4
    System.out.println(S.pop());            // contents: (7, 9)     outputs 4
    System.out.println(S.toString());
    System.out.println(S.pop());            // contents: (7, 9)     outputs 4
    System.out.println(S.pop());            // contents: (7, 9)     outputs 4
    System.out.println(S.toString());
    System.out.println(S.size());           // contents: (7, 9, 4)  outputs 3


  }
}

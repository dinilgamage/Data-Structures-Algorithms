/**
 * <p>My implementation uses JavaDoc comments to document the purpose of the class,
 * field variables, and the constructor. The JavaDoc documentation can be processed
 * by tools like JavaDoc to generate API documentation if needed.</p>
 *
 * @param <T> the type of data stored in the node
 *
 * <p>The use of the generic type parameter allows this class to be
 * instantiated with various types of objects by allowing me to specify the
 * type of data I want to store in the node.</p>
 */
public class Node<T> {
    /**
     * The data stored in the node.
     */
    T data;

    /**
     * Reference to the next node in the linked structure.
     */
    Node<T> next;

    /**
     * Constructs a node with the specified data and a null reference to the next node.
     *
     * @param data the data to be stored in the node
     */
    public Node(T data) {
        this.data = data;
        this.next = null;
    }
}

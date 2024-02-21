/**
 * <p>My implementation uses JavaDoc comments to document the purpose of the class,
 * method functionalities, parameters, and potential exceptions. The JavaDoc
 * documentation can be processed by tools like JavaDoc to generate API documentation if needed.</p>
 *
 * @param <T> the type of elements stored in the linked list
 *
 * <p>The use of the generic type parameter allows this class to be
 * instantiated with various types of objects by allowing me to specify the
 * type of elements I want to store in the linked list.</p>
 */
public class LinkedList<T> {
    private Node<T> head;
    private int size;

    /**
     * Constructs an empty linked list.
     *
     * <p>This constructor initializes an empty linked list with a null head
     * and a size of 0.</p>
     */
    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Adds an element to the end of the linked list.
     *
     * <p>This method adds an element to the end of the linked list. If the list is
     * empty, the new element becomes the head. Otherwise, it traverses the list
     * to the last node and appends the new element there.</p>
     *
     * @param element the element to be added
     */
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    /**
     * Retrieves the element at the specified index in the linked list.
     *
     * <p>This method retrieves the element at the specified index by traversing
     * the list from the head. It performs bounds checking to ensure the index
     * is within valid bounds.</p>
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public T get(int index) {
        validateIndex(index);
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the number of elements in the linked list.
     *
     * @return the number of elements in the linked list
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified index from the linked list.
     *
     * <p>This method removes the element at the specified index by adjusting
     * the links between nodes. If the index is 0, it updates the head; otherwise,
     * it traverses the list to the node before the specified index and adjusts
     * the links accordingly.</p>
     *
     * @param index the index of the element to be removed
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public void remove(int index) {
        validateIndex(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }


    /**
     * Validates whether the provided index is within the valid bounds of the linked list.
     *
     * @param index the index to be validated
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * Returns a string representation of the linked list for debugging and testing purposes
     *
     * <p>The string representation consists of a list of the linked list's elements
     * enclosed in square brackets and separated by commas. The order of elements is
     * the order in which they were added to the list.</p>
     *
     * @return a string representation of the linked list
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            result.append(current.data);
            if (current.next != null) {
                result.append(", ");
            }
            current = current.next;
        }
        result.append("]");
        return result.toString();
    }

}

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double-ended queue or deque is a generalisation of a stack and a queue that supports adding and removing items
 * from either the front or the end of the data structure. Deque is implemented using a linked list.
 *
 * Each operation is constant worst-case time. Non-iterator memory use is linear w.r.t current number of items
 * whereas memory usage per iterator is constant worst-case time.
 *
 * @author Aydin
 */
public class Deque<Item> implements Iterable<Item> {
    private int size;  // Number of nodes in the linked list
    private Node first;  // Reference to the first leftmost node in the linked list
    private Node last;  // Reference to the last rightmost node in the linked list

    /**
     * Initialise deque.
     */
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    /**
     * Linked list node class.
     */
    private class Node {
        private final Item item;  // Information to be held
        private Node prev;  // Node to the left
        private Node next;  // Node to the right

        /**
         * Node must be instantiated with all member variables specified.
         *
         * @param item Information to be held by the node.
         * @param prev Reference to left node.
         * @param next Reference to right node.
         */
        public Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Front to end iterator.
     */
    private class DequeIterator implements Iterator<Item> {
        private Node current;  // Node we are currently on in the iteration process.

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to iterate over.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Are there zero items in deque?
     *
     * @return true if empty, false if one or more items.
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Number of items held in Deque.
     *
     * @return number of items.
     */
    public int size() {
        return size;
    }

    /**
     * Adds item to the front of the deque.
     *
     * @param item to add to front.
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot addFirst() a null item.");
        }

        Node node = new Node(item, null, first);
        if (isEmpty()) {
            first = node;
            last = node;
        }
        else {  // has one or more items
            first.prev = node;
            first = node;
        }
        size++;
    }

    /**
     * Adds item to the end of the deque.
     * @param item to add to end.
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot addLast() a null item.");
        }

        Node node = new Node(item, last, null);
        if (isEmpty()) {
            first = node;
            last = node;
        }
        else {  // has one or more items
            last.next = node;
            last = node;
        }
        size++;
    }

    /**
     * Remove and return the item from the front of the deque.
     * @return item at the front.
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot removeFirst() an item from an empty deque.");
        }

        Item item = first.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {  // size > 1
            first = first.next;
            first.prev = null;
        }
        size--;
        return item;
    }

    /**
     * Remove and return the item from the end.
     * @return item from the end.
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot removeLast() an item from an empty deque.");
        }

        Item item = last.item;
        if (size == 1) {
            first = null;
            last = null;
        }
        else {  // size > 1
            last = last.prev;
            last.next = null;
        }
        size--;
        return item;
    }

    /**
     * Return an iterator that iterates over item in order from front to end.
     *
     * @return front to end iterator object.
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
}

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A randomized queue is similar to a stack or queue, except that the item removed is chosen uniformly at random from
 * items in the data structure. This RandomizedQueue is implemented using a resizing array.
 *
 * Non-iterator operations are constant amortized time, iterator constructor is linear w.r.t the current number of items
 * and other iterator operations is constant worst-case time. Both non-iterator memory use and memory usage per iterator
 * is linear w.r.t the current number of items.
 *
 * @author Aydin
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;  // References to items.
    private int n;  // Indicates where to put a new item next.

    /**
     * Initialise randomized queue.
     */
    public RandomizedQueue() {
        items = (Item []) new Object[1];  // Start with array of size 1.
        n = 0;
    }

    /**
     * Iterator enabling uniformly random iteration. Every iterator is mutually independent.
     */
    private class RandomIterator implements Iterator<Item> {
        private final Item[] it;  // References to items.
        private int current;  // Item currently on in the iterating process.

        /**
         * Initialise iterator's items by copying RandomizedQueue's items then using Knuth Shuffle. Knuth Shuffle
         * ensures each new iterator is mutually independent from each other.
         */
        public RandomIterator() {
            it = (Item[]) new Object[n];  // 0 sized array is possible, which will mean hasNext()==false.
            for (int i = 0; i < it.length; i++) {
                it[i] = items[i];
            }
            StdRandom.shuffle(it);
            current = 0;
        }

        public boolean hasNext() {
            return (current < it.length);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return it[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Are there zero items in randomized queue?
     *
     * @return true if empty, false if one or more items.
     */
    public boolean isEmpty() {
        return (n == 0);
    }

    /**
     * Number of items in randomized queue.
     *
     * @return number of items.
     */
    public int size() {
        return n;
    }

    /**
     * Takes care of items array resizing.
     *
     * @param capacity to resize items array to.
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    /**
     * Add item to randomized queue.
     *
     * A single iteration of Knuth Shuffle is applied for every new item added to ensure uniform randomness.
     *
     * @param item to be added.
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot enqueue() a null item.");
        }

        // Resize (by doubling) the size of the items array if capacity is reached.
        if (n == items.length) {
            resize(2 * items.length);
        }

        // Applying single iteration of Knuth Shuffle on new item while inserting it.
        int r = StdRandom.uniform(++n);
        items[n - 1] = items[r];
        items[r] = item;  // note that item is in a sense items[n-1], even though we never set items[n-1] = item.
    }

    /**
     * Remove item from randomized queue.
     *
     * @return item removed uniformly at random.
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue() an item when empty.");
        }
        Item item = items[--n];
        items[n] = null;

        // Resize (by halving the capacity) if only a quarter of the capacity is utilised.
        if (n > 0 && n == items.length/4) {
            resize(items.length/2);
        }

        return item;
    }

    /**
     * Return (but do not remove) a random item.
     *
     * @return item sampled uniformly at random .
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample() an item when empty.");
        }
        return items[StdRandom.uniform(n)];
    }

    /**
     * Each iterator is uniformly random and mutually independent of each other.
     *
     * @return uniformly random iterator object.
     */
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
}

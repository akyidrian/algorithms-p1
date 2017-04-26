import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Aydin on 21/04/2017.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;  // Indicates where to put a new item next.

    private class RandomIterator implements Iterator<Item> {
        private final Item[] it;
        private int current;

        public RandomIterator() {
            it = (Item[]) new Object[n];  // 0 sized array is possible, which will mean there is !hasNext()==true.
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

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item []) new Object[1];
        n = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return (n == 0);
    }

    // return the number of items on the queue
    public int size() {
        return n;
    }

    // resize the array.
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Cannot enqueue() a null item.");
        }
        if (n == items.length) {
            resize(2 * items.length);
        }

        // Applying single iteration of Knuth Shuffle on new item.
        int r = StdRandom.uniform(++n);
        items[n - 1] = items[r];
        items[r] = item;  // item is assumed to begin in items[n - 1]
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue() an item when empty.");
        }
        Item item = items[--n];
        items[n] = null;

        if (n > 0 && n == items.length/4) {
            resize(items.length/2);
        }

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot sample() an item when empty.");
        }
        return items[StdRandom.uniform(n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }
}

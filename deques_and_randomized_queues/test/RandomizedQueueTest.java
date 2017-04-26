import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test client for RandomizedQueue class. Does basic checking for correctness and proper usage of exceptions.
 */
class RandomizedQueueTest {
    private RandomizedQueue randQueue;
    private String seq = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";  // input sequence of items.
    private String[] seqItems;    // items array where each element represent a character or word from "seq".
    private static final int NUM_OF_RANDOMNESS_CHECKS = 1000;  // number of trials to be done to check randomness.

    @BeforeEach
    void setUp() {
        randQueue = new RandomizedQueue<String>();
        seqItems = seq.trim().split("\\s+");  // whitespace acts to divide items from seq string
    }

    /**
     * In the beginning, RandomizedQueue should be empty.
     */
    @Test
    void emptyOnInit() {
        assertTrue(randQueue.isEmpty());
        assertEquals(0, randQueue.size());
    }

    /**
     * Is every ordering different? In other words, does the ordering appear random?
     *
     * Note, for small seqItems.length or a large NUM_OF_RANDOMNESS_CHECKS the likely hood of collisions becomes higher.
     *
     * @param hashes constructed from strings representing order of items.
     * @return
     */
    boolean isRandom(int[] hashes) {
        for (int i = 0; i < hashes.length; i++) {
            for (int j = i + 1; j < hashes.length; j++) {
                if (hashes[i] == hashes[j]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check the correctness of size() and isEmpty() output while filling and emptying randomized queue.
     */
    @Test
    void checkForCorrectSizeAndIsEmptyResults () {
        for (int i = 0; i < seqItems.length; i++) {
            randQueue.enqueue(seqItems[i]);
            assertEquals(randQueue.size(), i + 1);
            assertFalse(randQueue.isEmpty()); // Want check this is false while it has items. Don't move!
        }

        for (int i = 0; i < seqItems.length; i++) {
            int currSize = seqItems.length - (i + 1);
            assertFalse(randQueue.isEmpty());  // Want to check this is false while it has items. Don't move!
            randQueue.dequeue();
            assertEquals(randQueue.size(), currSize);
        }
    }

    /**
     * Checking for uniform randomness of dequeue() operation.
     */
    @Test
    void dequeueRandomnessCheck() {
        int[] itemsHash = new int[NUM_OF_RANDOMNESS_CHECKS];
        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            for (String item: seqItems) {
                randQueue.enqueue(item);
            }
            for (int j = 0; j < seqItems.length; j++) {
                strBuilder.append(randQueue.dequeue());
            }
            itemsHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(itemsHash));
    }

    /**
     * Checking for uniform randomness of sample() operation.
     */
    @Test
    void sampleRandomnessCheck() {
        int[] itemsHash = new int[NUM_OF_RANDOMNESS_CHECKS];
        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            for (String item: seqItems) {
                randQueue.enqueue(item);
            }
            for (int j = 0; j < seqItems.length; j++) {
                strBuilder.append(randQueue.sample());
            }
            itemsHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(itemsHash));
    }

    /**
     * Check every iterator is uniformly random and mutually independent.
     */
    @Test
    void iteratorRandomnessCheck() {
        int[] itemsHash = new int[NUM_OF_RANDOMNESS_CHECKS];

        for (String item: seqItems) {
            randQueue.enqueue(item);
        }

        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            Iterator<String> iter = randQueue.iterator();
            while (iter.hasNext()) {
                strBuilder.append(iter.next());
            }
            itemsHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(itemsHash));
    }

    /**
     * Ensure we can't add a null item.
     */
    @Test
    void addingNullItemException() {
        try {
            randQueue.enqueue(null);
            fail("NullPointerException not raised on addFirst()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    /**
     * Ensure we can't try to remove items from empty randomized queue.
     */
    @Test
    void removingItemFromEmptyDequeException() {
        try {
            randQueue.dequeue();
            fail("NoSuchElementException not raised on dequeue()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }

        try {
            randQueue.sample();
            fail("NoSuchElementException not raised on sample()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }
    }

    /**
     * Ensure we can't use remove() iterator method.
     */
    @Test
    void iteratorRemoveCallException() {
        Iterator<String> i = randQueue.iterator();
        try {
            i.remove();
            fail("UnsupportedOperationException not raised!");
        } catch (Exception ex) {
            assertTrue(ex instanceof UnsupportedOperationException);
        }
    }

    /**
     * Ensure we can't iterate (using the iterator) through an empty randomized queue.
     */
    @Test
    void iteratorNoNextInEmptyDequeException() {
        Iterator<String> i = randQueue.iterator();
        assertFalse(i.hasNext());
        try {
            i.next();
            fail("NoSuchElementException not raised!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }
    }

    /**
     * Ensures iterator cannot be used when randomized queue is modified.
     *
     * Currently disabled. Reasonable amount of work necessary in RandomizedQueue to check for modification while using
     * an iterator.
     */
    @Disabled
    @Test
    void iteratorDequeModifiedException() {
        for (String item: seqItems) {
            randQueue.enqueue(item);  // It's not important what we add.
        }
        Iterator<String> i = randQueue.iterator();
        randQueue.dequeue();  // It's not important what we remove. We just want to modify the queue in someway.

        try {
            i.next();
            fail("ConcurrentModificationException not raised!");
        }
        catch (Exception ex) {
            assertTrue(ex instanceof ConcurrentModificationException);
        }
    }
}
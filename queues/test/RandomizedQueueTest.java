import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RandomizedQueueTest {
    private RandomizedQueue randQueue;
    private String seq = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
    private String[] seqItems;
    private static final int NUM_OF_RANDOMNESS_CHECKS = 1000;

    @BeforeEach
    void setUpRandQueue() {
        randQueue = new RandomizedQueue<String>();
        seqItems = seq.trim().split("\\s+");
    }

    @Test
    void emptyOnInit() {
        assertTrue(randQueue.isEmpty());
        assertEquals(0, randQueue.size());
    }

    boolean isRandom(int[] randStrHash) {
        for (int i = 0; i < randStrHash.length; i++) {
            for (int j = i + 1; j < randStrHash.length; j++) {
                if (randStrHash[i] == randStrHash[j]){
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    void checkForCorrectSizeAndIsEmptyResults () {
        for (int i = 0; i < seqItems.length; i++) {
            randQueue.enqueue(seqItems[i]);
            assertEquals(randQueue.size(), i + 1);
            assertFalse(randQueue.isEmpty()); // Want to just check this is false while deque has items.
        }

        for (int i = 0; i < seqItems.length; i++) {
            int currSize = seqItems.length - (i + 1);
            assertFalse(randQueue.isEmpty());  // Want to just check this is false while deque has items.
            randQueue.dequeue();
            assertEquals(randQueue.size(), currSize);
        }
    }

    @Test
    void dequeueRandomnessCheck() {
        int[] randStrHash = new int[NUM_OF_RANDOMNESS_CHECKS];
        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            for (String item: seqItems) {
                randQueue.enqueue(item);
            }
            for (int j = 0; j < seqItems.length; j++) {
                strBuilder.append(randQueue.dequeue());
            }
            randStrHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(randStrHash));
    }

    @Test
    void sampleRandomnessCheck() {
        int[] randStrHash = new int[NUM_OF_RANDOMNESS_CHECKS];
        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            for (String item: seqItems) {
                randQueue.enqueue(item);
            }
            for (int j = 0; j < seqItems.length; j++) {
                strBuilder.append(randQueue.sample());
            }
            randStrHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(randStrHash));
    }

    @Test
    void iteratorRandomnessCheck() {
        int[] randStrHash = new int[NUM_OF_RANDOMNESS_CHECKS];

        for (String item: seqItems) {
            randQueue.enqueue(item);
        }

        for (int i = 0; i < NUM_OF_RANDOMNESS_CHECKS; i++) {
            StringBuilder strBuilder = new StringBuilder();
            Iterator<String> iter = randQueue.iterator();
            while (iter.hasNext()) {
                strBuilder.append(iter.next());
            }
            randStrHash[i] = strBuilder.toString().hashCode();
        }
        assertTrue(isRandom(randStrHash));
    }

    @Test
    void addingNullItemException() {
        try {
            randQueue.enqueue(null);
            fail("NullPointerException not raised on addFirst()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

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

    @Disabled
    @Test
    void iteratorDequeModifiedException() {
        for (String item: seqItems) {
            randQueue.enqueue(item);  // It's not important where we add.
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
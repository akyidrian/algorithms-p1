import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test client for Deque class. Does basic checking for correctness and proper usage of exceptions.
 */
class DequeTest {
    private Deque deque;
    private String seq = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";  // input sequence of items.
    private String[] seqItems;  // items array where each element represent a character or word from "seq".

    @BeforeEach
    void setUp() {
        deque = new Deque<String>();
        seqItems = seq.trim().split("\\s+");  // whitespace acts to divide items from seq string
    }

    /**
     * In the beginning, deque should be empty.
     */
    @Test
    void emptyOnInit() {
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    /**
     * Fill from start and empty from end. Acts like a queue.
     */
    @Test
    void addFirstRemoveLastQueue() {
        for (int i = 0; i < seqItems.length; i++) {
            deque.addFirst(seqItems[i]);
            assertEquals(deque.size(), i + 1);
            assertFalse(deque.isEmpty()); // Want to just check this is false while deque has items.
        }

        for (int i = 0; i < seqItems.length; i++) {
            assertFalse(deque.isEmpty());  // Want to just check this is false while deque has items.
            String item = (String) deque.removeLast();
            assertEquals(deque.size(), seqItems.length - (i + 1));
            assertEquals(item, seqItems[i]);
        }
    }

    /**
     * Fill from end and empty from start. Acts like a queue.
     */
    @Test
    void addLastRemoveFirstQueue() {
        for (int i = 0; i < seqItems.length; i++) {
            deque.addLast(seqItems[i]);
            assertEquals(deque.size(), i + 1);
            assertFalse(deque.isEmpty()); // Want to just check this is false while deque has items.
        }

        for (int i = 0; i < seqItems.length; i++) {
            assertFalse(deque.isEmpty());  // Want to just check this is false while deque has items.
            String item = (String) deque.removeFirst();
            assertEquals(deque.size(), seqItems.length - (i + 1));
            assertEquals(item, seqItems[i]);
        }
    }

    /**
     * Fill from start and empty from start. Acts like a stack.
     */
    @Test
    void addRemoveFirstStack() {
        for (int i = 0; i < seqItems.length; i++) {
            deque.addFirst(seqItems[i]);
            assertEquals(deque.size(), i + 1);
            assertFalse(deque.isEmpty()); // Want to just check this is false while deque has items.
        }

        for (int i = 0; i < seqItems.length; i++) {
            int currSize = seqItems.length - (i + 1);

            assertFalse(deque.isEmpty());  // Want to just check this is false while deque has items.
            String item = (String) deque.removeFirst();
            assertEquals(deque.size(), currSize);
            assertEquals(item, seqItems[currSize]);
        }
    }

    /**
     * Fill from end and empty from end. Acts like a stack.
     */
    @Test
    void addRemoveLastStack() {
        for (int i = 0; i < seqItems.length; i++) {
            deque.addLast(seqItems[i]);
            assertEquals(deque.size(), i + 1);
            assertFalse(deque.isEmpty()); // Want to just check this is false while deque has items.
        }

        for (int i = 0; i < seqItems.length; i++) {
            int currSize = seqItems.length - (i + 1);

            assertFalse(deque.isEmpty());  // Want to just check this is false while deque has items.
            String item = (String) deque.removeLast();
            assertEquals(deque.size(), currSize);
            assertEquals(item, seqItems[currSize]);
        }
    }

    /**
     * Ensure we can't add a null item.
     */
    @Test
    void addingNullItemException() {
        try {
            deque.addFirst(null);
            fail("NullPointerException not raised on addFirst()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }

        try {
            deque.addLast(null);
            fail("NullPointerException not raised on addLast()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NullPointerException);
        }
    }

    /**
     * Ensure we can't try to remove items from empty deque.
     */
    @Test
    void removingItemFromEmptyDequeException() {
        try {
            deque.removeFirst();
            fail("NoSuchElementException not raised on removeFirst()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }

        try {
            deque.removeLast();
            fail("NoSuchElementException not raised on removeLast()!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }
    }

    /**
     * Ensure we can't use the remove() iterator method.
     */
    @Test
    void iteratorRemoveCallException() {
        Iterator<String> i = deque.iterator();
        try {
            i.remove();
            fail("UnsupportedOperationException not raised!");
        } catch (Exception ex) {
            assertTrue(ex instanceof UnsupportedOperationException);
        }
    }

    /**
     * Ensure we can't iterate (using the iterator) through an empty deque.
     */
    @Test
    void iteratorNoNextInEmptyDequeException() {
        Iterator<String> i = deque.iterator();
        assertFalse(i.hasNext());
        try {
            i.next();
            fail("NoSuchElementException not raised!");
        } catch (Exception ex) {
            assertTrue(ex instanceof NoSuchElementException);
        }
    }

    /**
     * Ensures iterator cannot be used when deque is modified.
     *
     * Currently disabled. Reasonable amount of work necessary in Deque to check for modification while using an
     * iterator.
     */
    @Disabled
    @Test
    void iteratorDequeModifiedException() {
        for (String item: seqItems) {
            deque.addFirst(item);  // It's not important where we add.
        }
        Iterator<String> i = deque.iterator();
        deque.removeFirst();  // It's not important what we remove. We just want to modify the queue in someway.

        try {
            i.next();
            fail("ConcurrentModificationException not raised!");
        }
        catch (Exception ex) {
            assertTrue(ex instanceof ConcurrentModificationException);
        }
    }

    /**
     * Ensure iterator iterates from the front to the end of the deque.
     */
    @Test
    void iteratorWorksFrontToEnd() {
        for (String item: seqItems) {
            deque.addLast(item);
        }

        Iterator<String> iter = deque.iterator();
        for (int i = 0; i < seqItems.length; i++) {
            assertEquals(seqItems[i], iter.next());
        }
    }
}

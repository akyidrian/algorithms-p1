import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    private Deque deque;
    private String seq = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
    private String[] seqItems;

    @BeforeEach
    void setUpDeque() {
        deque = new Deque<String>();
        seqItems = seq.trim().split("\\s+");
    }

    @Test
    void emptyOnInit() {
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

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

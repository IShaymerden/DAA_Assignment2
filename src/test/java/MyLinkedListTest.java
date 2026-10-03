import org.junit.jupiter.api.Test;
import structures.MyLinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void testAddByIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void testAddAtHead() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(0, 5);

        assertEquals(5, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void testRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        int removed = list.remove(1);

        assertEquals(20, removed);
        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void testRemoveHead() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        int removed = list.remove(0);

        assertEquals(10, removed);
        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void testContains() {
        MyLinkedList list = new MyLinkedList();

        list.add(5);
        list.add(10);
        list.add(15);

        assertTrue(list.contains(10));
        assertFalse(list.contains(100));
    }

    @Test
    void testDuplicateValues() {
        MyLinkedList list = new MyLinkedList();

        list.add(7);
        list.add(7);
        list.add(7);

        assertEquals(3, list.size());
        assertTrue(list.contains(7));
    }

    @Test
    void testFirstAndLastIndex() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(30, list.get(2));
    }

    @Test
    void testInvalidGetIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(0)
        );
    }

    @Test
    void testInvalidAddIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(1, 10)
        );
    }

    @Test
    void testInvalidRemoveIndex() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );
    }
}

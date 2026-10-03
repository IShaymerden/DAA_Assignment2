import org.junit.jupiter.api.Test;
import structures.DynamicArray;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void testAddAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
        assertEquals(3, array.size());
    }

    @Test
    void testAddByIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);

        array.add(1, 20);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
        assertEquals(3, array.size());
    }

    @Test
    void testRemove() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        int removed = array.remove(1);

        assertEquals(20, removed);
        assertEquals(2, array.size());
        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    void testContains() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(10);
        array.add(15);

        assertTrue(array.contains(10));
        assertFalse(array.contains(100));
    }

    @Test
    void testDuplicateValues() {
        DynamicArray array = new DynamicArray();

        array.add(7);
        array.add(7);
        array.add(7);

        assertEquals(3, array.size());
        assertTrue(array.contains(7));
    }

    @Test
    void testFirstAndLastIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(2));
    }

    @Test
    void testInvalidGetIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(0)
        );
    }

    @Test
    void testInvalidAddIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(1, 10)
        );
    }

    @Test
    void testInvalidRemoveIndex() {
        DynamicArray array = new DynamicArray();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(0)
        );
    }

    @Test
    void testResize() {
        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 100; i++) {
            array.add(i);
        }

        assertEquals(100, array.size());
        assertEquals(0, array.get(0));
        assertEquals(99, array.get(99));
    }
}
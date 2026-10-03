import org.junit.jupiter.api.Test;
import structures.MinHeap;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void testInsertAndPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.peekMin());
        assertEquals(3, heap.size());
    }

    @Test
    void testExtractMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.peekMin());
        assertEquals(2, heap.size());
    }

    @Test
    void testSortedOutput() {
        MinHeap heap = new MinHeap();

        heap.insert(40);
        heap.insert(10);
        heap.insert(30);
        heap.insert(20);
        heap.insert(50);

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
        assertEquals(50, heap.extractMin());
    }

    @Test
    void testDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testSingleElement() {
        MinHeap heap = new MinHeap();

        heap.insert(100);

        assertEquals(100, heap.peekMin());
        assertEquals(100, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void testEmptyPeek() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void testEmptyExtract() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void testHeapPropertyAfterInsert() {
        MinHeap heap = new MinHeap();

        heap.insert(40);
        assertTrue(heap.isValidHeap());

        heap.insert(10);
        assertTrue(heap.isValidHeap());

        heap.insert(30);
        assertTrue(heap.isValidHeap());

        heap.insert(5);
        assertTrue(heap.isValidHeap());
    }

    @Test
    void testHeapPropertyAfterExtract() {
        MinHeap heap = new MinHeap();

        heap.insert(40);
        heap.insert(10);
        heap.insert(30);
        heap.insert(5);
        heap.insert(50);

        heap.extractMin();
        assertTrue(heap.isValidHeap());

        heap.extractMin();
        assertTrue(heap.isValidHeap());
    }

    @Test
    void testResize() {
        MinHeap heap = new MinHeap();

        for (int i = 100; i >= 0; i--) {
            heap.insert(i);
        }

        assertEquals(101, heap.size());
        assertEquals(0, heap.peekMin());
        assertTrue(heap.isValidHeap());
    }
}
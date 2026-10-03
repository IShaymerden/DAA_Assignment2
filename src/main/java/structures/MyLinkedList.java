package structures;

import metrics.Metrics;

public class MyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    private final Metrics metrics = new Metrics();

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            tail = newNode;

            metrics.addMoves(2);
        } else {
            tail.next = newNode;
            metrics.addMove();

            tail = newNode;
            metrics.addMove();
        }

        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        if (index == size) {
            add(x);
            return;
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            metrics.addMove();

            head = newNode;
            metrics.addMove();

            if (size == 0) {
                tail = newNode;
                metrics.addMove();
            }
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.addStep();
            }

            newNode.next = current.next;
            metrics.addMove();

            current.next = newNode;
            metrics.addMove();
        }

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removedValue;

        if (index == 0) {
            metrics.addStep();

            removedValue = head.value;

            head = head.next;
            metrics.addMove();

            size--;

            if (size == 0) {
                tail = null;
                metrics.addMove();
            }

            return removedValue;
        }

        Node current = head;

        for (int i = 0; i < index - 1; i++) {
            current = current.next;
            metrics.addStep();
        }

        metrics.addStep();
        removedValue = current.next.value;

        if (current.next == tail) {
            tail = current;
            metrics.addMove();
        }

        current.next = current.next.next;
        metrics.addMove();

        size--;

        return removedValue;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.addStep();
        }

        metrics.addStep();

        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.addStep();
            metrics.addComparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}
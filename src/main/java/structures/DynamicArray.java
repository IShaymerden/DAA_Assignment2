package structures;

import metrics.Metrics;

public class DynamicArray {

    private int[] data;
    private int size;
    private final Metrics metrics = new Metrics();

    public DynamicArray() {
        data = new int[10];
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
        ensureCapacity();

        data[size] = x;
        metrics.addStep();

        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        ensureCapacity();

        for (int i = size; i > index; i--) {
            metrics.addStep();
            data[i] = data[i - 1];

            metrics.addMove();
        }

        data[index] = x;
        metrics.addStep();

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        metrics.addStep();
        int removedValue = data[index];

        for (int i = index; i < size - 1; i++) {
            metrics.addStep();
            data[i] = data[i + 1];

            metrics.addMove();
        }

        size--;

        return removedValue;
    }

    public int get(int index) {
        checkIndex(index);

        metrics.addStep();

        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            metrics.addStep();
            metrics.addComparison();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                metrics.addStep();

                newData[i] = data[i];

                metrics.addMove();
            }

            data = newData;
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}

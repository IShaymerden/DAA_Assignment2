package structures;

import metrics.Metrics;

public class MinHeap {

    private int[] data;
    private int size;
    private final Metrics metrics = new Metrics();

    public MinHeap() {
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

    public void insert(int x) {
        ensureCapacity();

        data[size] = x;
        metrics.addStep();

        int index = size;
        size++;

        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.addSteps(2);
            metrics.addComparison();

            if (data[parent] <= data[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.addStep();

        return data[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        metrics.addStep();
        int min = data[0];

        metrics.addStep();
        data[0] = data[size - 1];
        metrics.addMove();

        size--;

        int index = 0;

        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.addSteps(2);
                metrics.addComparison();

                if (data[left] < data[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.addSteps(2);
                metrics.addComparison();

                if (data[right] < data[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }

        return min;
    }

    public boolean isValidHeap() {
        for (int i = 0; i < size; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < size) {
                metrics.addSteps(2);
                metrics.addComparison();

                if (data[i] > data[left]) {
                    return false;
                }
            }

            if (right < size) {
                metrics.addSteps(2);
                metrics.addComparison();

                if (data[i] > data[right]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void swap(int i, int j) {
        metrics.addSteps(2);

        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;

        metrics.addMoves(3);
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
}

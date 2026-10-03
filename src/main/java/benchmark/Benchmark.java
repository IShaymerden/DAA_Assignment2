package benchmark;

import metrics.Metrics;
import structures.DynamicArray;
import structures.MinHeap;
import structures.MyLinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100,
            1_000,
            10_000,
            100_000
    };

    private static final int RUNS = 5;

    public static void main(String[] args) {
        File resultsDirectory = new File("results");

        if (!resultsDirectory.exists()) {
            resultsDirectory.mkdirs();
        }

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("results/results.csv"))) {

            writer.println(
                    "workload,variant,structure,n,time_ms,steps,moves,comparisons"
            );

            warmUp();

            for (int n : SIZES) {
                System.out.println("Running n = " + n);

                runW1(n, writer);
                runW2(n, writer);
                runW3Head(n, writer);
                runW3Middle(n, writer);
                runW4(n, writer);
            }

            System.out.println();
            System.out.println("Benchmark finished.");
            System.out.println("Results saved to results/results.csv");

        } catch (IOException e) {
            System.out.println("Error writing CSV:");
            e.printStackTrace();
        }
    }

    private static void warmUp() {
        System.out.println("Warm-up...");

        for (int run = 0; run < 3; run++) {
            DynamicArray array = new DynamicArray();

            for (int i = 0; i < 10_000; i++) {
                array.add(i);
            }

            for (int i = 0; i < 10_000; i++) {
                array.get(i % array.size());
            }
        }

        System.out.println("Warm-up finished.");
        System.out.println();
    }

    // =========================================================
    // W1 - RANDOM ACCESS
    // =========================================================

    private static void runW1(int n, PrintWriter writer) {
        int[] data = generateData(n);

        Result arrayResult = benchmarkW1DynamicArray(data);
        Result listResult = benchmarkW1LinkedList(data);

        writeResult(
                writer,
                "W1",
                "-",
                "DynamicArray",
                n,
                arrayResult
        );

        writeResult(
                writer,
                "W1",
                "-",
                "MyLinkedList",
                n,
                listResult
        );
    }

    private static Result benchmarkW1DynamicArray(int[] data) {
        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            DynamicArray array = new DynamicArray();

            for (int value : data) {
                array.add(value);
            }

            array.resetMetrics();

            Random random = new Random(42);

            long start = System.nanoTime();

            for (int i = 0; i < 10_000; i++) {
                int index = random.nextInt(data.length);
                array.get(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = array.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    private static Result benchmarkW1LinkedList(int[] data) {
        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MyLinkedList list = createLinkedList(data);

            list.resetMetrics();

            Random random = new Random(42);

            long start = System.nanoTime();

            for (int i = 0; i < 10_000; i++) {
                int index = random.nextInt(data.length);
                list.get(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = list.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W2 - SEARCH
    // =========================================================

    private static void runW2(int n, PrintWriter writer) {
        int[] data = generateData(n);
        int[] queries = generateSearchQueries(data);

        Result arrayResult =
                benchmarkW2DynamicArray(data, queries);

        Result listResult =
                benchmarkW2LinkedList(data, queries);

        writeResult(
                writer,
                "W2",
                "-",
                "DynamicArray",
                n,
                arrayResult
        );

        writeResult(
                writer,
                "W2",
                "-",
                "MyLinkedList",
                n,
                listResult
        );
    }

    private static Result benchmarkW2DynamicArray(
            int[] data,
            int[] queries) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            DynamicArray array = createDynamicArray(data);

            array.resetMetrics();

            long start = System.nanoTime();

            for (int query : queries) {
                array.contains(query);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = array.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    private static Result benchmarkW2LinkedList(
            int[] data,
            int[] queries) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MyLinkedList list = createLinkedList(data);

            list.resetMetrics();

            long start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = list.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W3 - HEAD
    // =========================================================

    private static void runW3Head(
            int n,
            PrintWriter writer) {

        int[] data = generateData(n);

        Result arrayResult =
                benchmarkW3HeadDynamicArray(data);

        Result listResult =
                benchmarkW3HeadLinkedList(data);

        writeResult(
                writer,
                "W3",
                "head",
                "DynamicArray",
                n,
                arrayResult
        );

        writeResult(
                writer,
                "W3",
                "head",
                "MyLinkedList",
                n,
                listResult
        );
    }

    private static Result benchmarkW3HeadDynamicArray(
            int[] data) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            DynamicArray array = createDynamicArray(data);

            array.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {
                array.add(0, i);
            }

            for (int i = 0; i < 1_000; i++) {
                array.remove(0);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = array.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    private static Result benchmarkW3HeadLinkedList(
            int[] data) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MyLinkedList list = createLinkedList(data);

            list.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {
                list.add(0, i);
            }

            for (int i = 0; i < 1_000; i++) {
                list.remove(0);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = list.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W3 - MIDDLE
    // =========================================================

    private static void runW3Middle(
            int n,
            PrintWriter writer) {

        int[] data = generateData(n);

        Result arrayResult =
                benchmarkW3MiddleDynamicArray(data);

        Result listResult =
                benchmarkW3MiddleLinkedList(data);

        writeResult(
                writer,
                "W3",
                "middle",
                "DynamicArray",
                n,
                arrayResult
        );

        writeResult(
                writer,
                "W3",
                "middle",
                "MyLinkedList",
                n,
                listResult
        );
    }

    private static Result benchmarkW3MiddleDynamicArray(
            int[] data) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            DynamicArray array = createDynamicArray(data);

            array.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {
                int index = array.size() / 2;
                array.add(index, i);
            }

            for (int i = 0; i < 1_000; i++) {
                int index = array.size() / 2;
                array.remove(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = array.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    private static Result benchmarkW3MiddleLinkedList(
            int[] data) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MyLinkedList list = createLinkedList(data);

            list.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1_000; i++) {
                int index = list.size() / 2;
                list.add(index, i);
            }

            for (int i = 0; i < 1_000; i++) {
                int index = list.size() / 2;
                list.remove(index);
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = list.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // W4 - PRIORITY PROCESSING
    // =========================================================

    private static void runW4(
            int n,
            PrintWriter writer) {

        int[] data = generateData(n);

        Result result = benchmarkW4MinHeap(data);

        writeResult(
                writer,
                "W4",
                "-",
                "MinHeap",
                n,
                result
        );
    }

    private static Result benchmarkW4MinHeap(
            int[] data) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run < RUNS; run++) {
            MinHeap heap = new MinHeap();

            heap.resetMetrics();

            long start = System.nanoTime();

            for (int value : data) {
                heap.insert(value);
            }

            int previous = Integer.MIN_VALUE;

            while (heap.size() > 0) {
                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "Heap output is not sorted"
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            times[run] = (end - start) / 1_000_000.0;

            Metrics metrics = heap.getMetrics();

            steps = metrics.getSteps();
            moves = metrics.getMoves();
            comparisons = metrics.getComparisons();
        }

        return new Result(
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================================================
    // DATA GENERATION
    // =========================================================

    private static int[] generateData(int n) {
        Random random = new Random(42);

        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static int[] generateSearchQueries(
            int[] data) {

        int[] queries = new int[1_000];

        Random random = new Random(42);

        for (int i = 0; i < 500; i++) {
            queries[i] =
                    data[random.nextInt(data.length)];
        }

        for (int i = 500; i < 1_000; i++) {
            queries[i] = -1 - i;
        }

        return queries;
    }

    // =========================================================
    // STRUCTURE CREATION
    // =========================================================

    private static DynamicArray createDynamicArray(
            int[] data) {

        DynamicArray array = new DynamicArray();

        for (int value : data) {
            array.add(value);
        }

        return array;
    }

    private static MyLinkedList createLinkedList(
            int[] data) {

        MyLinkedList list = new MyLinkedList();

        for (int value : data) {
            list.add(value);
        }

        return list;
    }

    // =========================================================
    // MEDIAN
    // =========================================================

    private static double median(
            double[] values) {

        double[] copy = Arrays.copyOf(
                values,
                values.length
        );

        Arrays.sort(copy);

        return copy[copy.length / 2];
    }

    // =========================================================
    // CSV OUTPUT
    // =========================================================

    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            Result result) {

        writer.printf(
                Locale.US,
                "%s,%s,%s,%d,%.6f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                result.timeMs,
                result.steps,
                result.moves,
                result.comparisons
        );

        writer.flush();
    }

    // =========================================================
    // RESULT CLASS
    // =========================================================

    private static class Result {

        double timeMs;
        long steps;
        long moves;
        long comparisons;

        Result(
                double timeMs,
                long steps,
                long moves,
                long comparisons) {

            this.timeMs = timeMs;
            this.steps = steps;
            this.moves = moves;
            this.comparisons = comparisons;
        }
    }
}
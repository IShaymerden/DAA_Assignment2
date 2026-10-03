# DAA Assignment 2 - Data Structures

This project was created for the Design and Analysis of Algorithms course.

The goal of the assignment is to implement custom data structures from scratch, benchmark them, measure physical operations, and compare their performance.

## Implemented Data Structures

The project contains three custom structures:

- `DynamicArray`
- `MyLinkedList`
- `MinHeap`

The implementations use primitive `int` values and do not use:

- `java.util.ArrayList`
- `java.util.LinkedList`
- `java.util.PriorityQueue`

## Features

The project includes:

- custom DynamicArray implementation;
- custom singly linked list implementation;
- custom array-based MinHeap;
- operation counters:
    - steps;
    - moves;
    - comparisons;
- JUnit 5 tests;
- benchmark workloads W1-W4;
- CSV result export;
- benchmark plots;
- asymptotic complexity analysis;
- loop invariant proofs.

## Project Structure

```text
DAA_Assignment2/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── benchmark/
│   │       │   └── Benchmark.java
│   │       ├── metrics/
│   │       │   └── Metrics.java
│   │       └── structures/
│   │           ├── DynamicArray.java
│   │           ├── MyLinkedList.java
│   │           └── MinHeap.java
│   └── test/
│       └── java/
│           ├── DynamicArrayTest.java
│           ├── MyLinkedListTest.java
│           └── MinHeapTest.java
├── results/
│   ├── results.csv
│   └── plots/
├── plot_results.py
├── README.md
├── REPORT.md
└── pom.xml
```

## Requirements

To run the Java project:

- Java 17 or newer
- Maven

To generate plots:

- Python 3
- pandas
- matplotlib

Install Python dependencies with:

```bash
py -m pip install pandas matplotlib
```

## How to Build the Project

From the project root directory run:

```bash
mvn clean compile
```

## How to Run Tests

Run all JUnit 5 tests with:

```bash
mvn test
```

The tests cover:

- add operations;
- remove operations;
- get operations;
- contains/search;
- duplicate values;
- empty structures;
- invalid indexes;
- resizing;
- heap property;
- sorted heap output.

## How to Run the Benchmark

Run the `Benchmark` class from IntelliJ IDEA:

```text
src/main/java/benchmark/Benchmark.java
```

or run it using the IDE Run button.

The benchmark uses the following input sizes:

```text
100
1,000
10,000
100,000
```

The random data is generated using:

```java
new Random(42)
```

Each case is executed five times and the median execution time is saved.

The benchmark measures:

- execution time;
- steps;
- moves;
- comparisons.

The generated results are saved to:

```text
results/results.csv
```

## Benchmark Workloads

### W1 - Random Access

Compares:

- DynamicArray
- MyLinkedList

Performs 10,000 random `get(index)` operations.

### W2 - Search

Compares:

- DynamicArray
- MyLinkedList

Performs 1,000 `contains(x)` operations.

Half of the searched values are present and half are absent.

### W3 - Insert and Remove

Compares:

- DynamicArray
- MyLinkedList

Two variants are tested:

- `head`
- `middle`

Each variant performs:

- 1,000 insertions;
- 1,000 removals.

### W4 - Priority Processing

Tests `MinHeap`.

The benchmark inserts all generated values and then repeatedly calls:

```java
extractMin()
```

The returned values are checked to ensure they are in non-decreasing order.

## How to Generate Plots

After running the benchmark, generate plots using:

```bash
py plot_results.py
```

The generated PNG files are stored in:

```text
results/plots/
```

## Report

The full analysis is available in:

```text
REPORT.md
```

It contains:

- complexity tables;
- two loop invariant proofs;
- benchmark methodology;
- benchmark results;
- plots;
- performance discussion;
- conclusion.

## Git Workflow

The project uses the following branches:

```text
main
feature/array
feature/list
feature/heap
feature/metrics
```

The release version is tagged as:

```text
v1.0
```

## Repository

GitHub:

https://github.com/IShaymerden/DAA_Assignment2

## Author

Iskander Shaymerden
/**
 * TestMinHeap.java - Contoh penggunaan MinHeap tanpa interaktif
 * Berguna untuk testing dan demonstrasi fitur
 */
public class TestMinHeap {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     CONTOH PENGGUNAAN MIN-HEAP - AUTOMATIC TEST SUITE      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        // Test 1: Insert multiple elements
        testInsert();

        // Test 2: Delete specific element
        testDelete();

        // Test 3: Delete minimum
        testDeleteMin();

        // Test 4: Peek operation
        testPeek();

        // Test 5: Build from array
        testBuildFromArray();

        // Test 6: Complex operations
        testComplexOperations();

        System.out.println("\n✓ Semua test berhasil dijalankan!\n");
    }

    /**
     * Test 1: Insert Multiple Elements
     */
    private static void testInsert() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 1: Insert Multiple Elements");
        System.out.println("=".repeat(60));

        MinHeap heap = new MinHeap();
        int[] values = { 10, 3, 2, 4, 5, 1 };

        System.out.println("Inserting: " + arrayToString(values));
        for (int value : values) {
            heap.insert(value);
            System.out.print("After insert " + value + ": ");
            heap.display();
        }

        System.out.println("\nFinal heap size: " + heap.size());
        heap.visualize();
    }

    /**
     * Test 2: Delete Specific Element
     */
    private static void testDelete() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 2: Delete Specific Element");
        System.out.println("=".repeat(60));

        MinHeap heap = new MinHeap();
        int[] values = { 13, 16, 31, 41, 51, 100 };

        System.out.println("Building heap from: " + arrayToString(values));
        for (int value : values) {
            heap.insert(value);
        }

        System.out.print("\nInitial heap: ");
        heap.display();

        System.out.println("\nDeleting element 13...");
        heap.delete(13);
        System.out.print("After delete: ");
        heap.display();

        System.out.println("\nDeleting element 31...");
        heap.delete(31);
        System.out.print("After delete: ");
        heap.display();

        heap.visualize();
    }

    /**
     * Test 3: Delete Minimum
     */
    private static void testDeleteMin() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 3: Delete Minimum (Root)");
        System.out.println("=".repeat(60));

        MinHeap heap = new MinHeap();
        int[] values = { 51, 41, 31, 16, 13 };

        System.out.println("Building heap from: " + arrayToString(values));
        for (int value : values) {
            heap.insert(value);
        }

        System.out.print("Initial heap: ");
        heap.display();

        System.out.println("\nDeleting minimum (3 times):");
        for (int i = 1; i <= 3; i++) {
            int min = heap.deleteMin();
            System.out.print("Deleted: " + min + ", Heap now: ");
            heap.display();
        }

        heap.visualize();
    }

    /**
     * Test 4: Peek Operation
     */
    private static void testPeek() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 4: Peek (Get Minimum)");
        System.out.println("=".repeat(60));

        MinHeap heap = new MinHeap();
        int[] values = { 51, 41, 31, 16, 13 };

        System.out.println("Building heap from: " + arrayToString(values));
        for (int value : values) {
            heap.insert(value);
        }

        System.out.print("Heap: ");
        heap.display();

        System.out.println("\nPeek operations (without deletion):");
        for (int i = 1; i <= 3; i++) {
            int peak = heap.peek();
            System.out.println("Peek #" + i + ": " + peak);
        }

        System.out.print("Heap unchanged: ");
        heap.display();
    }

    /**
     * Test 5: Build from Array
     */
    private static void testBuildFromArray() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 5: Build Min-Heap from Array (O(n))");
        System.out.println("=".repeat(60));

        int[] arr = { 2, 3, 10, 4, 5, 1 };

        System.out.println("Original array: " + arrayToString(arr));

        MinHeap heap = new MinHeap(arr);

        System.out.print("Min-heap after heapify: ");
        heap.display();

        heap.visualize();
    }

    /**
     * Test 6: Complex Operations
     */
    private static void testComplexOperations() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 6: Complex Operations Sequence");
        System.out.println("=".repeat(60));

        MinHeap heap = new MinHeap();

        System.out.println("Step 1: Insert 5 elements: 20, 15, 10, 8, 2");
        for (int value : new int[] { 20, 15, 10, 8, 2 }) {
            heap.insert(value);
        }
        System.out.print("Heap: ");
        heap.display();

        System.out.println("\nStep 2: Peek minimum");
        System.out.println("Minimum: " + heap.peek());

        System.out.println("\nStep 3: Delete minimum");
        heap.deleteMin();
        System.out.print("Heap: ");
        heap.display();

        System.out.println("\nStep 4: Insert new element 1");
        heap.insert(1);
        System.out.print("Heap: ");
        heap.display();

        System.out.println("\nStep 5: Delete specific element 15");
        heap.delete(15);
        System.out.print("Heap: ");
        heap.display();

        System.out.println("\nStep 6: Final visualization");
        heap.visualize();

        System.out.println("Final heap size: " + heap.size());
        System.out.println("Heap is empty: " + heap.isEmpty());
    }

    /**
     * Helper: Convert array to string
     */
    private static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

import java.util.ArrayList;
import java.util.Arrays;

public class minHeapify {

    public static void heapify(int[] arr, int i, int n) {
        int smallest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // If left child exists and is smaller than root
        if (l < n && arr[l] < arr[smallest])
            smallest = l;

        // If right child exists and is smaller than smallest so far
        if (r < n && arr[r] < arr[smallest])
            smallest = r;

        // If smallest is not root, swap and continue heapifying
        if (smallest != i) {
            int temp = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = temp;

            // Recursively heapify
            heapify(arr, smallest, n);
        }
    }

    public static ArrayList<Integer> buildMinHeap(int[] arr) {
        int n = arr.length;
        // Build min-heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, i, n);

        // Convert array to ArrayList before returning
        ArrayList<Integer> result = new ArrayList<>();
        for (int x : arr)
            result.add(x);

        return result;
    }

    public static void main(String[] args) {
        int[] arr = { 2, 3, 10, 4, 5, 1 };

        System.out.print("Original array: ");
        for (int x : arr)
            System.out.print(x + " ");

        // Build min-heap: perform heapify from last
        // non-leaf node up to root
        ArrayList<Integer> minHeap = buildMinHeap(arr);
        // Print array after min-heapify
        System.out.print("\nMin-Heap after heapify operation: ");
        for (int x : minHeap)
            System.out.print(x + " ");
    }
}
import java.util.ArrayList;

public class maxHeapify {

    static void heapify(int[] arr, int i, int n) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        // If left child exists and is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child exists and is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            int temp = arr[i];
            arr[i] = arr[largest];
            arr[largest] = temp;
            // Recursively heapify
            heapify(arr, largest, n);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 10, 5, 15, 2, 20, 30 };
        System.out.print("Original array: ");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");

        // Build max-heap:
        for (int i = arr.length / 2 - 1; i >= 0; i--)
            heapify(arr, i, arr.length);

        // Print array after max-heapify
        System.out.print("\nMax-Heap after heapify operation: ");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
    }
}
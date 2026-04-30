import java.util.ArrayList;

public class peakMinHeap {

    // Function to insert an element into min-heap
    public static void insert(ArrayList<Integer> heap, int value) {

        // Add the new element at the end
        heap.add(value);

        // Heapify-up to maintain min-heap property
        int index = heap.size() - 1;
        while (index > 0 && heap.get((index - 1) / 2) > heap.get(index)) {
            int temp = heap.get(index);
            heap.set(index, heap.get((index - 1) / 2));
            heap.set((index - 1) / 2, temp);
            index = (index - 1) / 2;
        }
    }

    // Function to get the peak element of min-heap
    public static int top(ArrayList<Integer> heap) {
        if (!heap.isEmpty())
            // Root element
            return heap.get(0);
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> minHeap = new ArrayList<>();

        // Insert elements into the min-heap
        insert(minHeap, 51);
        insert(minHeap, 41);
        insert(minHeap, 31);
        insert(minHeap, 16);
        insert(minHeap, 13);

        // Get the peak element (smallest in min-heap)
        int peakElement = top(minHeap);
        System.out.println("Peak element: " + peakElement);
    }
}
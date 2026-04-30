import java.util.ArrayList;

public class peakMaxHeap {

    // Function to insert an element into max-heap
    static void insert(ArrayList<Integer> heap, int value) {

        // Add the new element at the end
        heap.add(value);

        // Heapify-up to maintain max-heap property
        int index = heap.size() - 1;
        while (index > 0 && heap.get((index - 1) / 2) < heap.get(index)) {
            int temp = heap.get(index);
            heap.set(index, heap.get((index - 1) / 2));
            heap.set((index - 1) / 2, temp);
            index = (index - 1) / 2;
        }
    }

    // Function to get the peak element of max-heap
    static int top(ArrayList<Integer> heap) {
        if (!heap.isEmpty())
            // Root element
            return heap.get(0);
        return -1;
    }

    public static void main(String[] args) {
        ArrayList<Integer> maxHeap = new ArrayList<>();

        // Insert elements into the max-heap
        insert(maxHeap, 9);
        insert(maxHeap, 8);
        insert(maxHeap, 7);
        insert(maxHeap, 6);
        insert(maxHeap, 5);
        insert(maxHeap, 4);
        insert(maxHeap, 3);
        insert(maxHeap, 2);
        insert(maxHeap, 1);

        // Get the peak element (largest in max-heap)
        int peakElement = top(maxHeap);
        System.out.println("Peak element: " + peakElement);
    }
}
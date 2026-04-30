import java.util.ArrayList;

public class deletionHeap {

    public static void insert(ArrayList<Integer> heap, int value) {
        // Add the new element to the end of the heap
        heap.add(value);

        // Get the index of the last element
        int index = heap.size() - 1;

        // Compare the new element with its parent and swap if
        // necessary
        while (index > 0 && heap.get((index - 1) / 2) > heap.get(index)) {
            int temp = heap.get(index);
            heap.set(index, heap.get((index - 1) / 2));
            heap.set((index - 1) / 2, temp);

            // Move up the tree to the parent of the current
            // element
            index = (index - 1) / 2;
        }
    }

    // Function to delete a node from the min-heap
    public static void deleteMin(ArrayList<Integer> heap, int value) {
        // Find the index of the element to be deleted
        int index = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i) == value) {
                index = i;
                break;
            }
        }

        // If the element is not found, return
        if (index == -1) {
            return;
        }

        // Replace the element to be deleted with the last
        // element
        heap.set(index, heap.get(heap.size() - 1));

        // Remove the last element
        heap.remove(heap.size() - 1);

        // Heapify the tree starting from the element at the
        // deleted index
        while (true) {
            int left_child = 2 * index + 1;
            int right_child = 2 * index + 2;
            int smallest = index;

            if (left_child < heap.size() && heap.get(left_child) < heap.get(smallest)) {
                smallest = left_child;
            }
            if (right_child < heap.size() && heap.get(right_child) < heap.get(smallest)) {
                smallest = right_child;
            }
            if (smallest != index) {
                int temp = heap.get(index);
                heap.set(index, heap.get(smallest));
                heap.set(smallest, temp);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        int[] values = { 13, 16, 31, 41, 51, 100 };
        int n = values.length;

        for (int i = 0; i < n; i++) {
            insert(arr, values[i]);
        }

        System.out.print("Initial heap: ");
        for (int j = 0; j < arr.size(); j++) {
            System.out.print(arr.get(j) + " ");
        }
        System.out.println();

        deleteMin(arr, 13);
        System.out.print("Heap after deleting 13: ");
        for (int j = 0; j < arr.size(); j++) {
            System.out.print(arr.get(j) + " ");
        }
        System.out.println();
    }
}
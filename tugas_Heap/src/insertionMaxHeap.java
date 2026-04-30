import java.util.ArrayList;

public class insertionMaxHeap {

    public static void insert(ArrayList<Integer> heap, int value) {
        // Add the new element to the end of the heap
        heap.add(value);

        // Get the index of the last element
        int index = heap.size() - 1;

        // Compare the new element with its parent and swap if necessary
        while (index > 0 && heap.get((index - 1) / 2) < heap.get(index)) { // max-heap
            int temp = heap.get(index);
            heap.set(index, heap.get((index - 1) / 2));
            heap.set((index - 1) / 2, temp);

            // Move up the tree to the parent of the current element
            index = (index - 1) / 2;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        int[] values = { 10, 7, 11, 5, 4, 13 };

        for (int value : values) {
            insert(arr, value);
            System.out.print("Inserted " + value + " into the max-heap: ");
            for (int i = 0; i < arr.size(); i++) {
                System.out.print(arr.get(i) + " ");
            }
            System.out.println();
        }
    }
}
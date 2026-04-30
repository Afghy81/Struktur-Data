import java.util.ArrayList;

public class insertionHeap {

    public static void insert(ArrayList<Integer> heap, int value) {
        // Add the new element to the end of the heap
        heap.add(value);

        // Get the index of the last element
        int index = heap.size() - 1;

        // Compare the new element with
        // its parent and swap if necessary
        while (index > 0 && heap.get((index - 1) / 2) > heap.get(index)) {
            int temp = heap.get(index);
            heap.set(index, heap.get((index - 1) / 2));
            heap.set((index - 1) / 2, temp);

            // Move up the tree to the
            // parent of the current element
            index = (index - 1) / 2;
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        int[] values = { 3, 2, 4, 5, 1, 99 };
        int n = values.length;
        for (int i = 0; i < n; i++) {
            insert(arr, values[i]);
            System.out.print("Inserted " + values[i] + " into the min-heap: ");
            for (int j = 0; j < arr.size(); j++) {
                System.out.print(arr.get(j) + " ");
            }
            System.out.println();
        }
    }
}
import java.util.ArrayList;

/**
 * MinHeapData class - Implementasi Min-Heap untuk DataEntry (sorted ascending by id)
 * Menyediakan operasi: insert, delete, peek, dan display
 */
public class MinHeapData {
    private ArrayList<DataEntry> heap;

    /**
     * Constructor - inisialisasi heap kosong
     */
    public MinHeapData() {
        this.heap = new ArrayList<>();
    }

    /**
     * Operasi INSERT - menambahkan elemen ke min-heap
     * Kompleksitas: O(log n)
     */
    public void insert(DataEntry entry) {
        heap.add(entry);
        int index = heap.size() - 1;

        // Heapify-up: bandingkan dengan parent dan swap jika perlu
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).compareTo(heap.get(parentIndex)) < 0) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * Operasi PEEK - mendapatkan elemen terkecil (root) tanpa menghapusnya
     * Kompleksitas: O(1)
     */
    public DataEntry peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    /**
     * Operasi DELETE MIN - menghapus elemen terkecil (root)
     * Kompleksitas: O(log n)
     */
    public DataEntry deleteMin() {
        if (heap.isEmpty()) {
            return null;
        }

        DataEntry min = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heapifyDown(0);
        }

        return min;
    }

    /**
     * Operasi DELETE - menghapus entry spesifik dari heap
     * Kompleksitas: O(n)
     */
    public boolean delete(int id) {
        int index = findIndex(id);

        if (index == -1) {
            return false;
        }

        heap.set(index, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        if (index < heap.size()) {
            heapifyDown(index);
        }

        return true;
    }

    /**
     * Helper: Heapify-down - mempertahankan sifat min-heap ke bawah
     * Kompleksitas: O(log n)
     */
    private void heapifyDown(int index) {
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if (leftChild < heap.size() && heap.get(leftChild).compareTo(heap.get(smallest)) < 0) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild).compareTo(heap.get(smallest)) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * Helper: Tukar dua elemen di heap
     */
    private void swap(int i, int j) {
        DataEntry temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Helper: Cari index entry dengan id tertentu
     */
    private int findIndex(int id) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Utility: Cek apakah heap kosong
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Utility: Dapatkan ukuran heap
     */
    public int size() {
        return heap.size();
    }

    /**
     * Utility: Dapatkan semua data dalam urutan min-heap (ascending)
     */
    public ArrayList<DataEntry> getAllSorted() {
        ArrayList<DataEntry> result = new ArrayList<>();
        MinHeapData tempHeap = new MinHeapData();

        // Copy semua elemen
        for (DataEntry entry : heap) {
            tempHeap.insert(entry);
        }

        // Extract semua elemen dalam urutan ascending
        while (!tempHeap.isEmpty()) {
            result.add(tempHeap.deleteMin());
        }

        return result;
    }

    /**
     * Utility: Display heap
     */
    public void display() {
        if (heap.isEmpty()) {
            System.out.println("Min-Heap kosong!");
            return;
        }

        ArrayList<DataEntry> sorted = getAllSorted();
        System.out.println("┌─────┬─────────────────────────────┐");
        System.out.println("│ ID  │ NAMA                        │");
        System.out.println("├─────┼─────────────────────────────┤");
        for (DataEntry entry : sorted) {
            System.out.println("│ " + entry.toString() + " │");
        }
        System.out.println("└─────┴─────────────────────────────┘");
    }

    /**
     * Utility: Get heap list (internal structure)
     */
    public ArrayList<DataEntry> getHeapList() {
        return new ArrayList<>(heap);
    }
}

import java.util.ArrayList;

/**
 * MinHeap class - Implementasi Min-Heap yang efisien dan adaptif
 * Menyediakan operasi: insert, delete, peek, dan heapify
 */
public class MinHeap {
    private ArrayList<Integer> heap;

    /**
     * Constructor - inisialisasi heap kosong
     */
    public MinHeap() {
        this.heap = new ArrayList<>();
    }

    /**
     * Constructor - inisialisasi dengan array
     */
    public MinHeap(int[] arr) {
        this.heap = new ArrayList<>();
        buildMinHeap(arr);
    }

    /**
     * Operasi INSERT - menambahkan elemen ke heap
     * Kompleksitas: O(log n)
     */
    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        // Heapify-up: bandingkan dengan parent dan swap jika perlu
        while (index > 0 && heap.get(getParentIndex(index)) > heap.get(index)) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    /**
     * Operasi PEEK - mendapatkan elemen terkecil (root) tanpa menghapusnya
     * Kompleksitas: O(1)
     */
    public int peek() {
        if (heap.isEmpty()) {
            System.out.println("Error: Heap kosong!");
            return -1;
        }
        return heap.get(0);
    }

    /**
     * Operasi DELETE - menghapus elemen spesifik dari heap
     * Kompleksitas: O(n) untuk pencarian, O(log n) untuk heapify
     */
    public boolean delete(int value) {
        int index = findIndex(value);

        // Jika elemen tidak ditemukan
        if (index == -1) {
            System.out.println("Error: Elemen " + value + " tidak ditemukan di heap!");
            return false;
        }

        // Ganti dengan elemen terakhir
        heap.set(index, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        // Jika index masih valid, lakukan heapify-down
        if (index < heap.size()) {
            heapifyDown(index);
        }

        return true;
    }

    /**
     * Operasi DELETE MIN - menghapus elemen terkecil (root)
     * Kompleksitas: O(log n)
     */
    public int deleteMin() {
        if (heap.isEmpty()) {
            System.out.println("Error: Heap kosong!");
            return -1;
        }

        int min = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heapifyDown(0);
        }

        return min;
    }

    /**
     * Operasi HEAPIFY - membangun min-heap dari array
     * Kompleksitas: O(n)
     */
    public void buildMinHeap(int[] arr) {
        heap.clear();
        for (int value : arr) {
            heap.add(value);
        }

        // Mulai dari node non-leaf terakhir
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Helper: Heapify-down - mempertahankan sifat min-heap ke bawah
     * Kompleksitas: O(log n)
     */
    private void heapifyDown(int index) {
        int smallest = index;
        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);

        // Cari node terkecil antara node dan anak-anaknya
        if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
            smallest = rightChild;
        }

        // Jika node terkecil bukan node saat ini, swap dan rekursi
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    /**
     * Helper: Tukar dua elemen di heap
     */
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Helper: Dapatkan index parent
     */
    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    /**
     * Helper: Dapatkan index anak kiri
     */
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    /**
     * Helper: Dapatkan index anak kanan
     */
    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    /**
     * Helper: Cari index elemen
     */
    private int findIndex(int value) {
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i) == value) {
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
     * Utility: Tampilkan isi heap
     */
    public void display() {
        if (heap.isEmpty()) {
            System.out.println("Heap kosong!");
            return;
        }
        System.out.print("Heap: ");
        for (int value : heap) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    /**
     * Utility: Dapatkan array dari heap
     */
    public ArrayList<Integer> getHeap() {
        return new ArrayList<>(heap);
    }

    /**
     * Utility: Visualisasi struktur heap
     */
    public void visualize() {
        if (heap.isEmpty()) {
            System.out.println("Heap kosong!");
            return;
        }

        System.out.println("\n=== Visualisasi Struktur Heap ===");
        visualizeHelper(0, "", true);
        System.out.println();
    }

    /**
     * Helper: Rekursif visualisasi
     */
    private void visualizeHelper(int index, String prefix, boolean isLeft) {
        if (index >= heap.size()) {
            return;
        }

        System.out.println(prefix + (isLeft ? "├── " : "└── ") + heap.get(index));

        int leftChild = getLeftChildIndex(index);
        int rightChild = getRightChildIndex(index);

        if (leftChild < heap.size() || rightChild < heap.size()) {
            if (leftChild < heap.size()) {
                visualizeHelper(leftChild, prefix + (isLeft ? "│   " : "    "), true);
            }
            if (rightChild < heap.size()) {
                visualizeHelper(rightChild, prefix + (isLeft ? "│   " : "    "), false);
            }
        }
    }
}

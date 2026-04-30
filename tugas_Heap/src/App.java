import java.util.Scanner;
import java.util.ArrayList;

/**
 * App class - Program menu interaktif untuk Min-Heap
 * Menyediakan interface untuk operasi: insert, delete, peek, heapify, dan
 * visualisasi
 */
public class App {
    private static MinHeap minHeap = new MinHeap();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        displayBanner();

        while (true) {
            displayMenu();
            int choice = getIntInput("Pilih operasi (0-7): ");

            switch (choice) {
                case 1:
                    insertElement();
                    break;
                case 2:
                    deleteElement();
                    break;
                case 3:
                    deleteMinElement();
                    break;
                case 4:
                    peekElement();
                    break;
                case 5:
                    buildHeapFromArray();
                    break;
                case 6:
                    displayHeap();
                    break;
                case 7:
                    visualizeHeap();
                    break;
                case 0:
                    System.out.println("\n✓ Terima kasih! Program selesai.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("✗ Pilihan tidak valid! Silakan coba lagi.\n");
            }
        }
    }

    /**
     * Menampilkan banner sambutan
     */
    private static void displayBanner() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       PROGRAM MIN-HEAP - STRUKTUR DATA                     ║");
        System.out.println("║  Operasi: Insert, Delete, Peek, Heapify, Visualisasi      ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Menampilkan menu utama
     */
    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║          MENU UTAMA                ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║ 1. Insert Elemen                   ║");
        System.out.println("║ 2. Delete Elemen Spesifik          ║");
        System.out.println("║ 3. Delete Elemen Minimum (Root)    ║");
        System.out.println("║ 4. Peek (Lihat Elemen Terkecil)    ║");
        System.out.println("║ 5. Build Heap dari Array           ║");
        System.out.println("║ 6. Tampilkan Heap                  ║");
        System.out.println("║ 7. Visualisasi Struktur Heap       ║");
        System.out.println("║ 0. Keluar                          ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    /**
     * Operasi: Insert elemen ke heap
     */
    private static void insertElement() {
        System.out.println("\n--- Insert Elemen ke Heap ---");
        int value = getIntInput("Masukkan nilai yang akan diinsert: ");
        minHeap.insert(value);
        System.out.println("✓ Elemen " + value + " berhasil diinsert ke heap");
        minHeap.display();
    }

    /**
     * Operasi: Delete elemen spesifik
     */
    private static void deleteElement() {
        System.out.println("\n--- Delete Elemen Spesifik ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Heap kosong! Tidak ada yang bisa dihapus.");
            return;
        }
        int value = getIntInput("Masukkan nilai yang akan dihapus: ");
        if (minHeap.delete(value)) {
            System.out.println("✓ Elemen " + value + " berhasil dihapus dari heap");
            minHeap.display();
        }
    }

    /**
     * Operasi: Delete elemen minimum (root)
     */
    private static void deleteMinElement() {
        System.out.println("\n--- Delete Elemen Minimum (Root) ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Heap kosong! Tidak ada yang bisa dihapus.");
            return;
        }
        int minValue = minHeap.deleteMin();
        System.out.println("✓ Elemen minimum " + minValue + " berhasil dihapus dari heap");
        minHeap.display();
    }

    /**
     * Operasi: Peek elemen terkecil
     */
    private static void peekElement() {
        System.out.println("\n--- Peek Elemen Terkecil ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Heap kosong!");
            return;
        }
        int peek = minHeap.peek();
        System.out.println("✓ Elemen terkecil (root) di heap: " + peek);
    }

    /**
     * Operasi: Build heap dari array
     */
    private static void buildHeapFromArray() {
        System.out.println("\n--- Build Heap dari Array ---");
        System.out.print("Masukkan jumlah elemen: ");
        int n = getIntInput("");
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Masukkan elemen ke-" + (i + 1) + ": ");
            arr[i] = getIntInput("");
        }

        System.out.println("\nArray original: ");
        displayArray(arr);

        minHeap.buildMinHeap(arr);
        System.out.println("\n✓ Heap berhasil dibangun dari array");
        minHeap.display();
    }

    /**
     * Operasi: Tampilkan isi heap
     */
    private static void displayHeap() {
        System.out.println("\n--- Tampilkan Isi Heap ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Heap kosong!");
            return;
        }
        System.out.println("Ukuran Heap: " + minHeap.size());
        minHeap.display();
    }

    /**
     * Operasi: Visualisasi struktur heap
     */
    private static void visualizeHeap() {
        System.out.println("\n--- Visualisasi Struktur Heap ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Heap kosong!");
            return;
        }
        minHeap.visualize();
    }

    /**
     * Utility: Input integer dengan validasi
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("✗ Input tidak valid! Masukkan angka yang benar.\n");
            }
        }
    }

    /**
     * Utility: Tampilkan array
     */
    private static void displayArray(int[] arr) {
        System.out.print("[ ");
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println("]");
    }
}

import java.util.Scanner;
import java.util.ArrayList;

/**
 * heapPROGRAM.java - Program utama untuk mengelola Min-Heap dan Max-Heap
 * dengan data id dan nama yang diambil dari file Excel
 */
public class heapPROGRAM {
    private static MinHeapData minHeap = new MinHeapData();
    private static MaxHeapData maxHeap = new MaxHeapData();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayBanner();
        loadInitialData();
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Pilih operasi (0-5): ");

            switch (choice) {
                case 1:
                    addNewData();
                    break;
                case 2:
                    displayMinHeap();
                    break;
                case 3:
                    displayMaxHeap();
                    break;
                case 4:
                    deleteFromMinHeap();
                    break;
                case 5:
                    deleteFromMaxHeap();
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
        System.out.println("║     PROGRAM HEAP DATA - MIN-HEAP & MAX-HEAP              ║");
        System.out.println("║         (Data dengan ID dan Nama dari Excel)              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }

    /**
     * Load data awal dari Excel ke dalam heap
     */
    private static void loadInitialData() {
        System.out.println("⏳ Memuat data awal dari file Excel...\n");
        
        // Data dari Excel (100 entries)
        int[][] data = {
            {5288, 0}, {5993, 1}, {8689, 2}, {8043, 3}, {8699, 4},
            {2156, 5}, {4457, 6}, {8938, 7}, {2618, 8}, {9033, 9},
            {9971, 10}, {3874, 11}, {5914, 12}, {2398, 13}, {3725, 14},
            {5210, 15}, {7363, 16}, {7631, 17}, {4513, 18}, {5656, 19},
            {6453, 20}, {8783, 21}, {8194, 22}, {9783, 23}, {3685, 24},
            {4490, 25}, {8294, 26}, {8563, 27}, {1070, 28}, {5408, 29},
            {8258, 30}, {9309, 31}, {1138, 32}, {2751, 33}, {3258, 34},
            {6402, 35}, {7921, 36}, {9781, 37}, {3818, 38}, {5204, 39},
            {6119, 40}, {1928, 41}, {4207, 42}, {7255, 43}, {5309, 44},
            {2897, 45}, {8028, 46}, {1660, 47}, {3248, 48}, {5641, 49},
            {7376, 50}, {3525, 51}, {4492, 52}, {7187, 53}, {1305, 54},
            {6602, 55}, {8153, 56}, {3561, 57}, {5082, 58}, {7151, 59},
            {7524, 60}, {9178, 61}, {9817, 62}, {4304, 63}, {6820, 64},
            {9151, 65}, {3482, 66}, {3316, 67}, {5192, 68}, {7572, 69},
            {7660, 70}, {9224, 71}, {5083, 72}, {6362, 73}, {6465, 74},
            {9888, 75}, {4159, 76}, {4969, 77}, {5097, 78}, {6271, 79},
            {9250, 80}, {3409, 81}, {4577, 82}, {6244, 83}, {8612, 84},
            {4650, 85}, {6799, 86}, {9298, 87}, {4361, 88}, {4379, 89},
            {6928, 90}, {3195, 91}, {5741, 92}, {6852, 93}, {8147, 94},
            {8902, 95}, {8967, 96}, {1302, 97}, {2363, 98}, {6861, 99}
        };
        
        String[] names = {
            "pensil", "pulpen", "penghapus", "buku", "sampul",
            "penggaris", "kertas", "cat", "stabilo", "mobil",
            "motor", "becak", "sepeda", "kereta", "pesawat",
            "perahu", "kapal", "rakit", "kipas", "charger",
            "peci", "sarung", "sajadah", "smartphone", "jam",
            "televisi", "laptop", "komputer", "mouse", "keyboard",
            "tablet", "jendela", "kaca", "pintu", "kompor",
            "lemari", "kasur", "ranjang", "bantal", "baju",
            "kaos", "celana", "mukena", "jilbab", "pigura",
            "antena", "kulkas", "dispenser", "meja", "kursi",
            "kemoceng", "sapu", "gayung", "sabun", "sikat",
            "shampo", "botol", "gelas", "piring", "panci",
            "wajan", "blender", "galon", "cobek", "termos",
            "kran", "selang", "karpet", "tikar", "keset",
            "sepatu", "kaos kaki", "jaket", "piama", "piano",
            "gitar", "angklung", "suling", "toples", "parfum",
            "sisir", "topi", "gunting", "pisau", "kaleng",
            "tisu", "tas", "ikat pinggang", "korek api", "kopi",
            "gula", "cabai", "wortel", "timun", "apel",
            "jeruk", "tomat", "pisang", "pepaya", "bawang"
        };

        for (int i = 0; i < data.length; i++) {
            int id = data[i][0];
            String nama = names[data[i][1]];
            DataEntry entry = new DataEntry(id, nama);
            minHeap.insert(entry);
            maxHeap.insert(entry);
        }

        System.out.println("✓ Data berhasil dimuat!");
        System.out.println("  Total data: " + minHeap.size() + " entries\n");
    }

    /**
     * Menampilkan menu utama
     */
    private static void displayMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║              MENU UTAMA                    ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ 1. Tambah Data Baru (ke Min & Max Heap)    ║");
        System.out.println("║ 2. Tampilkan Data (Min-Heap - Ascending)   ║");
        System.out.println("║ 3. Tampilkan Data (Max-Heap - Descending)  ║");
        System.out.println("║ 4. Hapus Data dari Min-Heap                ║");
        System.out.println("║ 5. Hapus Data dari Max-Heap                ║");
        System.out.println("║ 0. Keluar                                  ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    /**
     * Operasi: Tambah data baru ke kedua heap sekaligus
     */
    private static void addNewData() {
        System.out.println("\n--- Tambah Data Baru ---");
        int id = getIntInput("Masukkan ID: ");
        
        // Cek apakah ID sudah ada
        if (isIdExists(id)) {
            System.out.println("✗ ID " + id + " sudah ada di database!");
            return;
        }
        
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine().trim();
        
        if (nama.isEmpty()) {
            System.out.println("✗ Nama tidak boleh kosong!");
            return;
        }
        
        DataEntry entry = new DataEntry(id, nama);
        minHeap.insert(entry);
        maxHeap.insert(entry);
        
        System.out.println("✓ Data berhasil ditambahkan ke kedua heap!");
        System.out.println("  - Min-Heap: " + minHeap.size() + " entries");
        System.out.println("  - Max-Heap: " + maxHeap.size() + " entries");
    }

    /**
     * Operasi: Tampilkan data dari Min-Heap (ascending)
     */
    private static void displayMinHeap() {
        System.out.println("\n--- Data dari Min-Heap (ID Ascending) ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Min-Heap kosong!");
            return;
        }
        
        System.out.println("Total data: " + minHeap.size() + " entries\n");
        minHeap.display();
    }

    /**
     * Operasi: Tampilkan data dari Max-Heap (descending)
     */
    private static void displayMaxHeap() {
        System.out.println("\n--- Data dari Max-Heap (ID Descending) ---");
        if (maxHeap.isEmpty()) {
            System.out.println("✗ Max-Heap kosong!");
            return;
        }
        
        System.out.println("Total data: " + maxHeap.size() + " entries\n");
        maxHeap.display();
    }

    /**
     * Operasi: Hapus data dari Min-Heap
     */
    private static void deleteFromMinHeap() {
        System.out.println("\n--- Hapus Data dari Min-Heap ---");
        if (minHeap.isEmpty()) {
            System.out.println("✗ Min-Heap kosong!");
            return;
        }
        
        int id = getIntInput("Masukkan ID yang ingin dihapus: ");
        
        if (minHeap.delete(id)) {
            maxHeap.delete(id);  // Hapus juga dari max-heap untuk konsistensi
            System.out.println("✓ Data dengan ID " + id + " berhasil dihapus dari kedua heap!");
            System.out.println("  - Min-Heap: " + minHeap.size() + " entries");
            System.out.println("  - Max-Heap: " + maxHeap.size() + " entries");
        } else {
            System.out.println("✗ Data dengan ID " + id + " tidak ditemukan!");
        }
    }

    /**
     * Operasi: Hapus data dari Max-Heap
     */
    private static void deleteFromMaxHeap() {
        System.out.println("\n--- Hapus Data dari Max-Heap ---");
        if (maxHeap.isEmpty()) {
            System.out.println("✗ Max-Heap kosong!");
            return;
        }
        
        int id = getIntInput("Masukkan ID yang ingin dihapus: ");
        
        if (maxHeap.delete(id)) {
            minHeap.delete(id);  // Hapus juga dari min-heap untuk konsistensi
            System.out.println("✓ Data dengan ID " + id + " berhasil dihapus dari kedua heap!");
            System.out.println("  - Min-Heap: " + minHeap.size() + " entries");
            System.out.println("  - Max-Heap: " + maxHeap.size() + " entries");
        } else {
            System.out.println("✗ Data dengan ID " + id + " tidak ditemukan!");
        }
    }

    /**
     * Helper: Cek apakah ID sudah ada
     */
    private static boolean isIdExists(int id) {
        for (DataEntry entry : minHeap.getHeapList()) {
            if (entry.getId() == id) {
                return true;
            }
        }
        return false;
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
}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class hash_table {
    private static final int DEFAULT_BUCKET_COUNT = 101;

    private static final int[] INITIAL_KEYS = {
        42, 517, 893, 126, 754, 311, 968, 205, 679, 34,
        821, 456, 990, 143, 588, 267, 732, 91, 604, 379,
        850, 12, 699, 234, 941, 118, 563, 807, 326, 471,
        75, 916, 284, 637, 159, 702, 448, 995, 23, 531,
        864, 196, 715, 352, 609, 88, 973, 240, 681, 407,
        129, 556, 814, 367, 925, 51, 493, 770, 218, 645,
        390, 100, 836, 274, 577, 958, 6, 724, 439, 187,
        662, 301, 889, 134, 548, 791, 253, 610, 967, 72,
        415, 830, 199, 703, 346, 581, 912, 29, 468, 755,
        320, 647, 108, 884, 227, 540, 799, 64, 371, 936
    };

    // Jumlah bucket pada hash table
    private int bucketCount;

    // Setiap bucket berisi linked list untuk menangani collision
    private List<List<Integer>> table;

    private int dataCount;

    public hash_table(int buckets) {
        bucketCount = buckets;
        dataCount = 0;
        table = new ArrayList<>();

        for (int index = 0; index < bucketCount; index++) {
            table.add(new LinkedList<>());
        }
    }

    public boolean insert(int key) {
        if (search(key)) {
            return false;
        }

        int index = getHashIndex(key);
        table.get(index).add(key);
        dataCount++;
        return true;
    }

    public boolean remove(int key) {
        int index = getHashIndex(key);
        boolean isRemoved = table.get(index).remove(Integer.valueOf(key));

        if (isRemoved) {
            dataCount--;
        }

        return isRemoved;
    }

    public boolean search(int key) {
        int index = getHashIndex(key);
        return table.get(index).contains(key);
    }

    public int getIndexIfFound(int key) {
        int index = getHashIndex(key);

        if (table.get(index).contains(key)) {
            return index;
        }

        return -1;
    }

    public int getChainPositionIfFound(int key) {
        int index = getHashIndex(key);
        List<Integer> chain = table.get(index);

        for (int position = 0; position < chain.size(); position++) {
            if (chain.get(position).equals(key)) {
                return position + 1;
            }
        }

        return -1;
    }

    public void display() {
        System.out.println("\nIsi hash table:");
        for (int index = 0; index < bucketCount; index++) {
            System.out.print(index);

            for (int key : table.get(index)) {
                System.out.print(" --> " + key);
            }

            System.out.println();
        }
    }

    public int getDataCount() {
        return dataCount;
    }

    private int getHashIndex(int key) {
        return Math.abs(key) % bucketCount;
    }

    private static void loadInitialData(hash_table hashTable) {
        for (int key : INITIAL_KEYS) {
            hashTable.insert(key);
        }
    }

    private static int readNumber(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            if (input.hasNextInt()) {
                return input.nextInt();
            }

            System.out.println("Input harus berupa angka.");
            input.next();
        }
    }

    private static void showMenu() {
        System.out.println("\n===== MENU HASH TABLE =====");
        System.out.println("1. INPUT DATA");
        System.out.println("2. HAPUS DATA");
        System.out.println("3. CARI DATA");
        System.out.println("4. TAMPILKAN HASH TABLE");
        System.out.println("0. KELUAR");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        hash_table hashTable = new hash_table(DEFAULT_BUCKET_COUNT);
        loadInitialData(hashTable);

        System.out.println("Program Hash Table dengan Separate Chaining");
        System.out.println("Data awal sebanyak " + hashTable.getDataCount() + " angka random unik sudah dimasukkan.");

        int choice;
        do {
            showMenu();
            choice = readNumber(input, "Pilih menu: ");

            switch (choice) {
                case 1:
                    int keyToInsert = readNumber(input, "Masukkan data numerik: ");
                    if (hashTable.insert(keyToInsert)) {
                        System.out.println("Data " + keyToInsert + " berhasil ditambahkan.");
                    } else {
                        System.out.println("Data " + keyToInsert + " sudah ada, tidak boleh duplikat.");
                    }
                    break;
                case 2:
                    int keyToRemove = readNumber(input, "Masukkan data yang akan dihapus: ");
                    if (hashTable.remove(keyToRemove)) {
                        System.out.println("Data " + keyToRemove + " berhasil dihapus.");
                    } else {
                        System.out.println("Data " + keyToRemove + " tidak ditemukan.");
                    }
                    break;
                case 3:
                    int keyToSearch = readNumber(input, "Masukkan data yang dicari: ");
                    int foundIndex = hashTable.getIndexIfFound(keyToSearch);
                    if (foundIndex != -1) {
                        int chainPosition = hashTable.getChainPositionIfFound(keyToSearch);
                        System.out.println("Data " + keyToSearch + " ditemukan pada index "
                                + foundIndex + ", posisi linked list ke-" + chainPosition + ".");
                    } else {
                        System.out.println("Data " + keyToSearch + " tidak ditemukan.");
                    }
                    break;
                case 4:
                    hashTable.display();
                    break;
                case 0:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia.");
                    break;
            }
        } while (choice != 0);

        input.close();
    }
}

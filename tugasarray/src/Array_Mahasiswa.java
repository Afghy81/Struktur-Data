import java.util.Scanner;

class Mahasiswa {
    String nim;
    String nama;

    Mahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }
}

public class Array_Mahasiswa {
    static final int MAX = 10;
    static Mahasiswa[] arr = new Mahasiswa[MAX];
    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== MENU DATA MAHASISWA ===");
            System.out.println("1. Insert at Beginning");
            System.out.println("2. Insert at Given Position");
            System.out.println("3. Insert at End");
            System.out.println("4. Delete from Beginning");
            System.out.println("5. Delete at Given Position");
            System.out.println("6. Delete from End");
            System.out.println("7. Delete First Occurrence");
            System.out.println("8. Show Data");
            System.out.println("9. Exit");
            System.out.print("Pilih menu: ");
            
            String input = sc.nextLine();

        if (!input.matches("\\d+")) {
            System.out.println("Input harus angka!");
            continue;
        }

        int menu = Integer.parseInt(input);
            switch (menu) {
                case 1:
                    insertAtBeginning();
                    break;
                case 2:
                    insertAtPosition();
                    break;
                case 3:
                    insertAtEnd();
                    break;
                case 4:
                    deleteFromBeginning();
                    break;
                case 5:
                    deleteAtPosition();
                    break;
                case 6:
                    deleteFromEnd();
                    break;
                case 7:
                    deleteFirstOccurrence();
                    break;
                case 8:
                    showData();
                    break;
                case 9:
                    System.out.println("Program dihentikan.");
                    return;
                default:
                    System.out.println("Menu tidak valid.");
            }
        }
    }

    static String inputNim() {
        while (true) {
            System.out.print("\nMasukkan NIM (ENTER = batal): ");
            String nim = sc.nextLine().trim();

            if (nim.isEmpty()) {
                return null; // keluar ke menu
            }

            if (!nim.matches("\\d+")) {
                System.out.println("NIM hanya boleh angka!");
            } else if (nim.length() != 11){
                System.out.println("NIM harus 11 digit!");
            } else if (nim.contains(" ")) {
                System.out.println("NIM tidak boleh mengandung spasi!");
            } else {
                return nim;
            }
        }
    }

    static String inputNama() {
        while (true) {
            System.out.print("\nMasukkan Nama (ENTER = batal): ");
            String nama = sc.nextLine().trim();

            if (nama.isEmpty()) {
                return null;
            }

            nama = nama.replaceAll("\\s+", " ");

            if (!nama.replace(" ", "").matches("[a-zA-Z]+")) {
                System.out.println("Nama hanya boleh huruf dan spasi!");
            } else {
                 String[] parts = nama.toLowerCase().split(" ");
                 StringBuilder result = new StringBuilder();

            for (String p : parts) {
                result.append(Character.toUpperCase(p.charAt(0)))
                      .append(p.substring(1))
                      .append(" ");
            }
                return result.toString().trim();
            }
        }
    }

    static void insertAtBeginning() {
        if (count == MAX) {
            System.out.println("Array penuh (overflow). Tidak bisa menambah data.");
            return;
        }

        String nim = inputNim();
        if (nim == null) {
            System.out.println("Input NIM dibatalkan.");
            return;
        }
        String nama = inputNama();
        if (nama == null) {
            System.out.println("Input Nama dibatalkan.");
            return;
        }
        for (int i = count; i > 0; i--) {
            arr[i] = arr[i - 1];
        }
        arr[0] = new Mahasiswa(nim, nama);
        count++;
        System.out.println("Data berhasil ditambahkan di awal.");
    }

    static void insertAtPosition() {
        if (count == MAX) {
            System.out.println("Array penuh (overflow). Tidak bisa menambah data.");
            return;
        }
        int pos;
        while (true) {
            System.out.print("Masukkan posisi (0-" + count + "): ");
            String input = sc.nextLine();

            if (!input.matches("\\d+")) {
                System.out.println("Input harus angka!");
                continue;
            }

            pos = Integer.parseInt(input);

            if (pos < 0 || pos > count) {
                System.out.println("Posisi tidak valid.");
                continue;
            }

            break;
        }

        String nim = inputNim();
        if (nim == null) {
            System.out.println("Input NIM dibatalkan.");
            return;
        }
        String nama = inputNama();
        if (nama == null) {
            System.out.println("Input Nama dibatalkan.");
            return;
        }

        for (int i = count; i > pos; i--) {
            arr[i] = arr[i - 1];
        }
        arr[pos] = new Mahasiswa(nim, nama);
        count++;
        System.out.println("Data berhasil ditambahkan di posisi " + pos + ".");
    }

    static void insertAtEnd() {
        if (count == MAX) {
            System.out.println("Array penuh (overflow). Tidak bisa menambah data.");
            return;
        }

        String nim = inputNim();
        if (nim == null){
            System.out.println("Input NIM dibatalkan.");
            return;
        }
        String nama = inputNama();
        if (nama == null) {
            System.out.println("Input Nama dibatalkan.");
            return;
        }

        arr[count] = new Mahasiswa(nim, nama);
        count++;
        System.out.println("Data berhasil ditambahkan di akhir.");
    }

    static void deleteFromBeginning() {
        if (count == 0) {
            System.out.println("Array kosong (underflow). Tidak bisa menghapus data.");
            return;
        }
        for (int i = 0; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null;
        count--;
        System.out.println("Data awal berhasil dihapus.");
    }

    static void deleteAtPosition() {
        if (count == 0) {
            System.out.println("Array kosong (underflow). Tidak bisa menghapus data.");
            return;
        }
        int pos;
        while (true) {
            System.out.print("Masukkan posisi (0-" + (count - 1) + "): ");
            String input = sc.nextLine();

            if (!input.matches("\\d+")) {
                System.out.println("Input harus angka!");
                continue;
            }

            pos = Integer.parseInt(input);

            if (pos < 0 || pos >= count) {
                System.out.println("Posisi tidak valid.");
                continue;
            }

            break;
        }
        for (int i = pos; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null;
        count--;
        System.out.println("Data di posisi " + pos + " berhasil dihapus.");
    }

    static void deleteFromEnd() {
        if (count == 0) {
            System.out.println("Array kosong (underflow). Tidak bisa menghapus data.");
            return;
        }
        arr[count - 1] = null;
        count--;
        System.out.println("Data akhir berhasil dihapus.");
    }

    static void deleteFirstOccurrence() {
        if (count == 0) {
            System.out.println("Array kosong (underflow). Tidak bisa menghapus data.");
            return;
        }
        System.out.print("Masukkan NIM yang ingin dihapus: ");
        String nim = sc.nextLine();
        int idx = -1;
        for (int i = 0; i < count; i++) {
            if (arr[i].nim.equals(nim)) {
                idx = i;
                break;
            }
        }
        if (idx == -1) {
            System.out.println("Data dengan NIM " + nim + " tidak ditemukan.");
            return;
        }
        for (int i = idx; i < count - 1; i++) {
            arr[i] = arr[i + 1];
        }
        arr[count - 1] = null;
        count--;
        System.out.println("Data dengan NIM " + nim + " berhasil dihapus.");
    }

    static void showData() {
        if (count == 0) {
            System.out.println("Array kosong.");
            return;
        }
        System.out.println("\n=== DATA MAHASISWA ===");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". NIM: " + arr[i].nim + ", Nama: " + arr[i].nama);
        }
    }
}

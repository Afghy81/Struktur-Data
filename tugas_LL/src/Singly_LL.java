import java.util.Scanner;

// Node class for Mahasiswa
class Node {
    String nim;
    String nama;
    Node next;

    // Constructor
    Node(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
        this.next = null;
    }
}

// Singly Linked List class
class SinglyLinkedList {
    Node head;
    int count;

    // Constructor
    public SinglyLinkedList() {
        head = null;
        count = 0;
    }

    // Insert node at the beginning
    public void insertAtBeginning(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        newNode.next = head;
        head = newNode;
        count++;
    }

    // Insert node at a given position (1-based)
    public boolean insertAtPosition(int pos, String nim, String nama) {
        if (pos < 1 || pos > count + 1) return false;
        if (pos == 1) {
            insertAtBeginning(nim, nama);
            return true;
        }
        Node newNode = new Node(nim, nama);
        Node curr = head;
        for (int i = 1; i < pos - 1; i++) {
            curr = curr.next;
        }
        newNode.next = curr.next;
        curr.next = newNode;
        count++;
        return true;
    }

    // Insert node at the end
    public void insertAtEnd(String nim, String nama) {
        Node newNode = new Node(nim, nama);
        if (head == null) {
            head = newNode;
        } else {
            Node curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        count++;
    }

    // Delete node from the beginning
    public boolean deleteFromBeginning() {
        if (head == null) return false;
        head = head.next;
        count--;
        return true;
    }

    // Delete node from a given position (1-based)
    public boolean deleteAtPosition(int pos) {
        if (pos < 1 || pos > count || head == null) return false;
        if (pos == 1) {
            return deleteFromBeginning();
        }
        Node curr = head;
        for (int i = 1; i < pos - 1; i++) {
            curr = curr.next;
        }
        if (curr.next == null) return false;
        curr.next = curr.next.next;
        count--;
        return true;
    }

    // Delete node from the end
    public boolean deleteFromEnd() {
        if (head == null) return false;
        if (head.next == null) {
            head = null;
            count--;
            return true;
        }
        Node curr = head;
        while (curr.next.next != null) {
            curr = curr.next;
        }
        curr.next = null;
        count--;
        return true;
    }

    // Delete first occurrence by NIM
    public boolean deleteByNim(String nim) {
        if (head == null) return false;
        if (head.nim.equals(nim)) {
            head = head.next;
            count--;
            return true;
        }
        Node curr = head;
        while (curr.next != null) {
            if (curr.next.nim.equals(nim)) {
                curr.next = curr.next.next;
                count--;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Show all data
    public void showData() {
        if (head == null) {
            System.out.println("List kosong.");
            return;
        }
        Node curr = head;
        int idx = 1;
        System.out.println("\nData Mahasiswa:");
        while (curr != null) {
            System.out.printf("%d. NIM: %s, Nama: %s\n", idx, curr.nim, curr.nama);
            curr = curr.next;
            idx++;
        }
        System.out.println("\nTotal data: " + count);
    }
}

public class Singly_LL {
    // Validasi NIM: hanya angka, tidak boleh spasi, tidak boleh kosong
    public static String inputNim(Scanner sc) {
        while (true) {
            System.out.print("Masukkan NIM (ENTER untuk batal): ");
            String nim = sc.nextLine().trim();
            if (nim.isEmpty()) return null;
            if (!nim.matches("\\d+")) {
                System.out.println("NIM hanya boleh berisi angka!");
                continue;
            }
            if (nim.length() <= 5 || nim.length() >= 13) {
                System.out.println("NIM harus antara 5-13 digit!");
                continue;
            }
            return nim;
        }
    }

    // Validasi Nama: hanya huruf dan spasi, tidak boleh diawali spasi/angka, capitalize tiap kata
    public static String inputNama(Scanner sc) {
        while (true) {
            System.out.print("Masukkan Nama (ENTER untuk batal): ");
            String nama = sc.nextLine();
            if (nama.trim().isEmpty()) return null;
            nama = nama.trim().replaceAll("\\s+", " ");
            if (!nama.matches("[a-zA-Z][a-zA-Z ]*")) {
                System.out.println("Nama hanya boleh huruf dan spasi!");
                continue;
            }
            // Capitalize tiap kata
            String[] words = nama.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String w : words) {
                if (w.length() > 0) {
                    sb.append(Character.toUpperCase(w.charAt(0)));
                    if (w.length() > 1) sb.append(w.substring(1).toLowerCase());
                    sb.append(" ");
                }
            }
            return sb.toString().trim();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SinglyLinkedList list = new SinglyLinkedList();
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Insert at beginning");
            System.out.println("2. Insert at given position");
            System.out.println("3. Insert at end");
            System.out.println("4. Delete from beginning");
            System.out.println("5. Delete given position");
            System.out.println("6. Delete from end");
            System.out.println("7. Delete first occurrence");
            System.out.println("8. Show data");
            System.out.println("9. Exit");
            System.out.print("Pilih menu [1-9]: ");
            String menu = sc.nextLine();
            switch (menu) {
                case "1": {
                    String nim = inputNim(sc);
                    if (nim == null) break;
                    String nama = inputNama(sc);
                    if (nama == null) break;
                    list.insertAtBeginning(nim, nama);
                    System.out.println("Data berhasil ditambah di awal.");
                    break;
                }
                case "2": {
                    if (list.count == 0) {
                        System.out.println("List kosong, gunakan menu 1 atau 3.");
                        break;
                    }
                    System.out.printf("Masukkan posisi [1-%d] (ENTER untuk batal): ", list.count + 1);
                    String posStr = sc.nextLine();
                    if (posStr.trim().isEmpty()) break;
                    int pos;
                    try {
                        pos = Integer.parseInt(posStr);
                    } catch (Exception e) {
                        System.out.println("Input posisi tidak valid!");
                        break;
                    }
                    if (pos < 1 || pos > list.count + 1) {
                        System.out.println("Posisi di luar rentang!");
                        break;
                    }
                    String nim = inputNim(sc);
                    if (nim == null) break;
                    String nama = inputNama(sc);
                    if (nama == null) break;
                    if (list.insertAtPosition(pos, nim, nama))
                        System.out.println("Data berhasil ditambah di posisi " + pos + ".");
                    else
                        System.out.println("Gagal menambah data di posisi " + pos + ".");
                    break;
                }
                case "3": {
                    String nim = inputNim(sc);
                    if (nim == null) break;
                    String nama = inputNama(sc);
                    if (nama == null) break;
                    list.insertAtEnd(nim, nama);
                    System.out.println("Data berhasil ditambah di akhir.");
                    break;
                }
                case "4": {
                    if (list.deleteFromBeginning())
                        System.out.println("Data awal berhasil dihapus.");
                    else
                        System.out.println("List kosong!");
                    break;
                }
                case "5": {
                    if (list.count == 0) {
                        System.out.println("List kosong!");
                        break;
                    }
                    System.out.printf("Masukkan posisi [1-%d] (ENTER untuk batal): ", list.count);
                    String posStr = sc.nextLine();
                    if (posStr.trim().isEmpty()) break;
                    int pos;
                    try {
                        pos = Integer.parseInt(posStr);
                    } catch (Exception e) {
                        System.out.println("Input posisi tidak valid!");
                        break;
                    }
                    if (pos < 1 || pos > list.count) {
                        System.out.println("Posisi di luar rentang!");
                        break;
                    }
                    if (list.deleteAtPosition(pos))
                        System.out.println("Data posisi " + pos + " berhasil dihapus.");
                    else
                        System.out.println("Gagal menghapus data di posisi " + pos + ".");
                    break;
                }
                case "6": {
                    if (list.deleteFromEnd())
                        System.out.println("Data akhir berhasil dihapus.");
                    else
                        System.out.println("List kosong!");
                    break;
                }
                case "7": {
                    if (list.count == 0) {
                        System.out.println("List kosong!");
                        break;
                    }
                    String nim = inputNim(sc);
                    if (nim == null) break;
                    if (list.deleteByNim(nim))
                        System.out.println("Data dengan NIM " + nim + " berhasil dihapus.");
                    else
                        System.out.println("NIM tidak ditemukan!");
                    break;
                }
                case "8": {
                    list.showData();
                    break;
                }
                case "9": {
                    System.out.println("Keluar program. Terima kasih!");
                    sc.close();
                    return;
                }
                default:
                    System.out.println("Menu tidak valid!");
            }
        }
    }
}

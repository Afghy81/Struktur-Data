import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // =========================
    // NODE AVL
    // =========================
    static class Node {
        int id;
        String nama;
        int height;
        Node left, right;

        Node(int id, String nama) {
            this.id = id;
            this.nama = nama;
            this.height = 1;
        }
    }

    static Node root = null;

    // =========================
    // UTILITAS AVL
    // =========================
    static int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    static int balanceFactor(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    static Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    static Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    static Node minValueNode(Node node) {
        Node current = node;
        while (current != null && current.left != null) {
            current = current.left;
        }
        return current;
    }

    // =========================
    // INSERT / TAMBAH DATA
    // =========================
    static Node insert(Node node, int id, String nama) {
        if (node == null) {
            return new Node(id, nama);
        }

        if (id < node.id) {
            node.left = insert(node.left, id, nama);
        } else if (id > node.id) {
            node.right = insert(node.right, id, nama);
        } else {
            // Jika ID sama, data diperbarui agar tetap unik
            node.nama = nama;
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && id < node.left.id) {
            return rightRotate(node);
        }

        // Right Right
        if (balance < -1 && id > node.right.id) {
            return leftRotate(node);
        }

        // Left Right
        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // =========================
    // SEARCH BY ID
    // =========================
    static Node searchById(Node node, int id) {
        while (node != null) {
            if (id == node.id)
                return node;
            if (id < node.id)
                node = node.left;
            else
                node = node.right;
        }
        return null;
    }

    // =========================
    // SEARCH BY NAME
    // =========================
    static void searchByName(Node node, String nama, List<Node> result) {
        if (node == null)
            return;

        if (node.nama.equalsIgnoreCase(nama)) {
            result.add(node);
        }

        searchByName(node.left, nama, result);
        searchByName(node.right, nama, result);
    }

    // =========================
    // DELETE BY ID
    // =========================
    static Node delete(Node node, int id) {
        if (node == null)
            return null;

        if (id < node.id) {
            node.left = delete(node.left, id);
        } else if (id > node.id) {
            node.right = delete(node.right, id);
        } else {
            // Node ditemukan
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;

                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // Dua anak: gunakan inorder successor
                Node succ = minValueNode(node.right);
                node.id = succ.id;
                node.nama = succ.nama;
                node.right = delete(node.right, succ.id);
            }
        }

        if (node == null)
            return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && balanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // Left Right
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right
        if (balance < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // Right Left
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // =========================
    // TRAVERSAL
    // =========================
    static void inorder(Node node, List<Node> result) {
        if (node == null)
            return;
        inorder(node.left, result);
        result.add(node);
        inorder(node.right, result);
    }

    static void preorder(Node node, List<Node> result) {
        if (node == null)
            return;
        result.add(node);
        preorder(node.left, result);
        preorder(node.right, result);
    }

    static void postorder(Node node, List<Node> result) {
        if (node == null)
            return;
        postorder(node.left, result);
        postorder(node.right, result);
        result.add(node);
    }

    // =========================
    // POSISI NODE BERDASARKAN INORDER
    // =========================
    static int getInorderPositionById(int id) {
        List<Node> list = new ArrayList<>();
        inorder(root, list);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == id) {
                return i + 1;
            }
        }
        return -1;
    }

    static int getInorderPositionByNode(Node target) {
        if (target == null)
            return -1;

        List<Node> list = new ArrayList<>();
        inorder(root, list);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == target) {
                return i + 1;
            }
        }
        return -1;
    }

    // =========================
    // OUTPUT DATA
    // =========================
    static void printNodes(String title, List<Node> nodes) {
        System.out.println("\n=== " + title + " ===");
        if (nodes.isEmpty()) {
            System.out.println("(data kosong)");
            return;
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            int pos = getInorderPositionByNode(n);
            System.out.println((i + 1) + ". ID: " + n.id + " | Nama: " + n.nama + " | Posisi inorder: " + pos);
        }
    }

    static void showSingleNode(Node node) {
        if (node == null) {
            System.out.println("Data tidak ditemukan.");
            return;
        }

        int pos = getInorderPositionByNode(node);
        System.out.println("Data anda berhasil ditemukan pada urutan node ke-" + pos);
        System.out.println("ID   : " + node.id);
        System.out.println("Nama : " + node.nama);
    }

    // =========================
    // PARSE INPUT FLEXIBLE
    // =========================
    static class ParsedRecord {
        int id;
        String nama;

        ParsedRecord(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }
    }

    static ParsedRecord parseRecord(String input) {
        if (input == null)
            return null;

        String line = input.trim();
        if (line.isEmpty())
            return null;

        String[] parts;
        if (line.contains("\t")) {
            parts = line.split("\t", 2);
        } else if (line.contains(",")) {
            parts = line.split(",", 2);
        } else if (line.contains(";")) {
            parts = line.split(";", 2);
        } else {
            parts = line.split("\\s+", 2);
        }

        if (parts.length < 2)
            return null;

        try {
            int id = Integer.parseInt(parts[0].trim());
            String nama = parts[1].trim();
            if (nama.isEmpty())
                return null;
            return new ParsedRecord(id, nama);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // =========================
    // MENU UTAMA
    // =========================
    static void showMainMenu() {
        System.out.println("\n==============================");
        System.out.println("   PROGRAM AVL + BST DATA");
        System.out.println("==============================");
        System.out.println("1. Tambah data");
        System.out.println("2. Cari data");
        System.out.println("3. Hapus data");
        System.out.println("4. Traversal");
        System.out.println("5. Keluar");
        System.out.print("Pilih menu: ");
    }

    static void showSearchMenu() {
        System.out.println("\n--- MENU CARI DATA ---");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan Nama");
        System.out.println("3. Kembali");
        System.out.print("Pilih: ");
    }

    static void showDeleteMenu() {
        System.out.println("\n--- MENU HAPUS DATA ---");
        System.out.println("1. Hapus berdasarkan ID");
        System.out.println("2. Hapus berdasarkan Nama");
        System.out.println("3. Kembali");
        System.out.print("Pilih: ");
    }

    static void showTraversalMenu() {
        System.out.println("\n--- MENU TRAVERSAL ---");
        System.out.println("1. Inorder");
        System.out.println("2. Preorder");
        System.out.println("3. Postorder");
        System.out.println("4. Kembali");
        System.out.print("Pilih: ");
    }

    // =========================
    // MODE INPUT DATA
    // =========================
    static void addSingleBySingle(Scanner sc) {
        while (true) {
            System.out.println("\nMasukkan data dengan format:");
            System.out.println("  ID,Nama");
            System.out.println("  atau hasil paste Excel: ID<TAB>Nama");
            System.out.println("Ketik 'menu' untuk kembali ke menu utama.");

            System.out.print("Input data: ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("menu")) {
                return;
            }

            ParsedRecord rec = parseRecord(line);
            if (rec == null) {
                System.out.println("Format input salah. Contoh: 1001,Budi Santoso");
                continue;
            }

            root = insert(root, rec.id, rec.nama);
            System.out.println("Data berhasil ditambahkan.");

            System.out.print("Kembali ke menu? (y/n): ");
            String opsi = sc.nextLine().trim();
            if (opsi.equalsIgnoreCase("y")) {
                return;
            }
        }
    }

    static void addBulkPasteMode(Scanner sc) {
        System.out.println("\nMode paste banyak data aktif.");
        System.out.println("Tempel data per baris dengan format:");
        System.out.println("  ID,Nama");
        System.out.println("  atau ID<TAB>Nama dari Excel");
        System.out.println("Akhiri dengan baris kosong atau ketik 'SELESAI'.");

        while (true) {
            System.out.print("Input baris: ");
            String line = sc.nextLine().trim();

            if (line.isEmpty() || line.equalsIgnoreCase("selesai")) {
                System.out.println("Kembali ke menu utama.");
                return;
            }

            ParsedRecord rec = parseRecord(line);
            if (rec == null) {
                System.out.println("Format salah, baris dilewati.");
                continue;
            }

            root = insert(root, rec.id, rec.nama);
            System.out.println("Masuk: ID=" + rec.id + " | Nama=" + rec.nama);
        }
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            showMainMenu();

            String choiceLine = sc.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("Pilihan harus angka.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\n--- TAMBAH DATA ---");
                    System.out.println("1. Input satu per satu");
                    System.out.println("2. Paste banyak data");
                    System.out.print("Pilih mode input: ");

                    String modeLine = sc.nextLine().trim();
                    int mode;
                    try {
                        mode = Integer.parseInt(modeLine);
                    } catch (NumberFormatException e) {
                        System.out.println("Mode tidak valid.");
                        continue;
                    }

                    if (mode == 1) {
                        addSingleBySingle(sc);
                    } else if (mode == 2) {
                        addBulkPasteMode(sc);
                    } else {
                        System.out.println("Mode tidak valid.");
                    }
                }

                case 2 -> {
                    while (true) {
                        showSearchMenu();
                        String sLine = sc.nextLine().trim();
                        int s;
                        try {
                            s = Integer.parseInt(sLine);
                        } catch (NumberFormatException e) {
                            System.out.println("Pilihan harus angka.");
                            continue;
                        }

                        if (s == 1) {
                            System.out.print("Masukkan ID: ");
                            String idLine = sc.nextLine().trim();
                            try {
                                int id = Integer.parseInt(idLine);
                                Node result = searchById(root, id);

                                if (result != null) {
                                    showSingleNode(result);
                                } else {
                                    System.out.println("Data tidak ditemukan.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ID harus angka.");
                            }

                        } else if (s == 2) {
                            System.out.print("Masukkan Nama: ");
                            String nama = sc.nextLine().trim();

                            List<Node> results = new ArrayList<>();
                            searchByName(root, nama, results);

                            if (results.isEmpty()) {
                                System.out.println("Data tidak ditemukan.");
                            } else {
                                System.out.println("Data ditemukan sebanyak " + results.size() + " data.");
                                for (Node n : results) {
                                    showSingleNode(n);
                                    System.out.println("--------------------------");
                                }
                            }

                        } else if (s == 3) {
                            break;
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                    }
                }

                case 3 -> {
                    while (true) {
                        showDeleteMenu();
                        String dLine = sc.nextLine().trim();
                        int d;
                        try {
                            d = Integer.parseInt(dLine);
                        } catch (NumberFormatException e) {
                            System.out.println("Pilihan harus angka.");
                            continue;
                        }

                        if (d == 1) {
                            System.out.print("Masukkan ID yang akan dihapus: ");
                            String idLine = sc.nextLine().trim();
                            try {
                                int id = Integer.parseInt(idLine);
                                Node found = searchById(root, id);

                                if (found == null) {
                                    System.out.println("Data tidak ditemukan.");
                                } else {
                                    root = delete(root, id);
                                    System.out.println("Data berhasil dihapus.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("ID harus angka.");
                            }

                        } else if (d == 2) {
                            System.out.print("Masukkan Nama yang akan dihapus: ");
                            String nama = sc.nextLine().trim();

                            List<Node> results = new ArrayList<>();
                            searchByName(root, nama, results);

                            if (results.isEmpty()) {
                                System.out.println("Data tidak ditemukan.");
                            } else if (results.size() == 1) {
                                Node target = results.get(0);
                                root = delete(root, target.id);
                                System.out.println("Data berhasil dihapus.");
                            } else {
                                System.out.println("Ditemukan beberapa data dengan nama yang sama:");
                                for (Node n : results) {
                                    System.out.println("ID: " + n.id + " | Nama: " + n.nama + " | Posisi inorder: "
                                            + getInorderPositionByNode(n));
                                }
                                System.out.print("Masukkan ID yang ingin dihapus: ");
                                String idLine = sc.nextLine().trim();
                                try {
                                    int id = Integer.parseInt(idLine);
                                    Node found = searchById(root, id);
                                    if (found != null && found.nama.equalsIgnoreCase(nama)) {
                                        root = delete(root, id);
                                        System.out.println("Data berhasil dihapus.");
                                    } else {
                                        System.out.println("ID tidak cocok dengan nama yang dicari.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("ID harus angka.");
                                }
                            }

                        } else if (d == 3) {
                            break;
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                    }
                }

                case 4 -> {
                    while (true) {
                        showTraversalMenu();
                        String tLine = sc.nextLine().trim();
                        int t;
                        try {
                            t = Integer.parseInt(tLine);
                        } catch (NumberFormatException e) {
                            System.out.println("Pilihan harus angka.");
                            continue;
                        }

                        if (t == 1) {
                            List<Node> result = new ArrayList<>();
                            inorder(root, result);
                            printNodes("TRAVERSAL INORDER", result);

                        } else if (t == 2) {
                            List<Node> result = new ArrayList<>();
                            preorder(root, result);
                            printNodes("TRAVERSAL PREORDER", result);

                        } else if (t == 3) {
                            List<Node> result = new ArrayList<>();
                            postorder(root, result);
                            printNodes("TRAVERSAL POSTORDER", result);

                        } else if (t == 4) {
                            break;
                        } else {
                            System.out.println("Pilihan tidak valid.");
                        }
                    }
                }

                case 5 -> {
                    System.out.println("Program selesai.");
                    sc.close();
                    return;
                }

                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
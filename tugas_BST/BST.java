import java.util.Scanner;

class Node {
    int id;
    String nama;
    Node left, right;

    Node(int id, String nama) {
        this.id = id;
        this.nama = nama;
        left = right = null;
    }
}

class BSTree {
    Node root;

    // INSERT
    Node insert(Node root, int id, String nama) {
        if (root == null) return new Node(id, nama);

        if (id < root.id)
            root.left = insert(root.left, id, nama);
        else if (id > root.id)
            root.right = insert(root.right, id, nama);

        return root;
    }

    // SEARCH
    Node search(Node root, int id) {
        if (root == null || root.id == id) return root;

        if (id < root.id)
            return search(root.left, id);
        return search(root.right, id);
    }

    // MIN VALUE
    Node minValue(Node root) {
        while (root.left != null)
            root = root.left;
        return root;
    }

    // DELETE
    Node delete(Node root, int id) {
        if (root == null) return root;

        if (id < root.id)
            root.left = delete(root.left, id);
        else if (id > root.id)
            root.right = delete(root.right, id);
        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            Node temp = minValue(root.right);
            root.id = temp.id;
            root.nama = temp.nama;
            root.right = delete(root.right, temp.id);
        }
        return root;
    }

    // TRAVERSAL
    void inorder(Node root) {
        if (root != null) {
            inorder(root.left);
            System.out.println(root.id + " - " + root.nama);
            inorder(root.right);
        }
    }

    void preorder(Node root) {
        if (root != null) {
            System.out.println(root.id + " - " + root.nama);
            preorder(root.left);
            preorder(root.right);
        }
    }

    void postorder(Node root) {
        if (root != null) {
            postorder(root.left);
            postorder(root.right);
            System.out.println(root.id + " - " + root.nama);
        }
    }
}

public class BST {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BSTree tree = new BSTree();

        // ================= DATA AWAL (EXCEL) =================
        int[] ids = {
            5288,5993,8689,8043,8699,2156,4457,8938,2618,9033,
            9971,3874,5914,2398,3725,5210,7363,7631,4513,5656,
            6453,8783,8194,9783,3685,4490,8294,8563,1070,5408,
            8258,9309,1138,2751,3258,6402,7921,9781,3818,5204,
            6119,1928,4207,7255,5309,2897,8028,1660,3248,5641,
            7376,3525,4492,7187,1305,6602,8153,3561,5082,7151,
            7524,9178,9817,4304,6820,9151,3482,3316,5192,7572,
            7660,9224,5083,6362,6465,9888,4159,4969,5097,6271,
            9250,3409,4577,6244,8612,4650,6799,9298,4361,4379,
            6928,3195,5741,6852,8147,8902,8967,1302,2363,6861
        };

        String[] nama = {
            "pensil","pulpen","penghapus","buku","sampul","penggaris","kertas","cat","stabilo","mobil",
            "motor","becak","sepeda","kereta","pesawat","perahu","kapal","rakit","kipas","charger",
            "peci","sarung","sajadah","smartphone","jam","televisi","laptop","komputer","mouse","keyboard",
            "tablet","jendela","kaca","pintu","kompor","lemari","kasur","ranjang","bantal","baju",
            "kaos","celana","mukena","jilbab","pigura","antena","kulkas","dispenser","meja","kursi",
            "kemoceng","sapu","gayung","sabun","sikat","shampo","botol","gelas","piring","panci",
            "wajan","blender","galon","cobek","termos","kran","selang","karpet","tikar","keset",
            "sepatu","kaos kaki","jaket","piama","piano","gitar","angklung","suling","toples","parfum",
            "sisir","topi","gunting","pisau","kaleng","tisu","tas","ikat pinggang","korek api","kopi",
            "gula","cabai","wortel","timun","apel","jeruk","tomat","pisang","pepaya","bawang"
        };

        for (int i = 0; i < ids.length; i++) {
            tree.root = tree.insert(tree.root, ids[i], nama[i]);
        }

        // ================= MENU =================
        int pilihan;
        do {
            System.out.println("\n=== MENU BST ===");
            System.out.println("1. Tambah Data");
            System.out.println("2. Cari Data");
            System.out.println("3. Hapus Data");
            System.out.println("4. Inorder");
            System.out.println("5. Preorder");
            System.out.println("6. Postorder");
            System.out.println("0. Keluar");
            System.out.print("Pilih: ");
            pilihan = input.nextInt();

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan ID: ");
                    int id = input.nextInt();
                    input.nextLine();
                    System.out.print("Masukkan Nama: ");
                    String namaBaru = input.nextLine();
                    tree.root = tree.insert(tree.root, id, namaBaru);
                    System.out.println("Data berhasil ditambahkan!");
                    break;

                case 2:
                    System.out.print("Masukkan ID yang dicari: ");
                    int cari = input.nextInt();
                    Node hasil = tree.search(tree.root, cari);
                    if (hasil != null)
                        System.out.println("Ditemukan: " + hasil.nama);
                    else
                        System.out.println("Data tidak ditemukan!");
                    break;

                case 3:
                    System.out.print("Masukkan ID yang dihapus: ");
                    int hapus = input.nextInt();
                    tree.root = tree.delete(tree.root, hapus);
                    System.out.println("Data berhasil dihapus!");
                    break;

                case 4:
                    System.out.println("\nInorder:");
                    tree.inorder(tree.root);
                    break;

                case 5:
                    System.out.println("\nPreorder:");
                    tree.preorder(tree.root);
                    break;

                case 6:
                    System.out.println("\nPostorder:");
                    tree.postorder(tree.root);
                    break;

                case 0:
                    System.out.println("Program selesai.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid!");
            }

        } while (pilihan != 0);

        input.close();
    }
}
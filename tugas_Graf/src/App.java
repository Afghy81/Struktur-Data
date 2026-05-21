import java.util.Scanner;

public class App {
    private static Graph graph;
    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        scanner = new Scanner(System.in);
        graph = new Graph(10); // Max 10 vertices

        int choice;
        boolean running = true;

        while (running) {
            displayMenu();
            System.out.print("Pilih menu (1-8): ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        tambahVertex();
                        break;
                    case 2:
                        hapusVertex();
                        break;
                    case 3:
                        tambahEdge();
                        break;
                    case 4:
                        hapusEdge();
                        break;
                    case 5:
                        tampilkanGraph();
                        break;
                    case 6:
                        traversalDFS();
                        break;
                    case 7:
                        traversalBFS();
                        break;
                    case 8:
                        running = false;
                        System.out.println("Program selesai. Sampai jumpa!");
                        break;
                    default:
                        System.out.println("Menu tidak valid! Silakan pilih 1-8.\n");
                }
            } catch (Exception e) {
                System.out.println("Input tidak valid! Silakan masukkan angka.\n");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("        MENU GRAPH");
        System.out.println("=".repeat(30));
        System.out.println("1. Tambah Vertex");
        System.out.println("2. Hapus Vertex");
        System.out.println("3. Tambah Edge");
        System.out.println("4. Hapus Edge");
        System.out.println("5. Tampilkan Graph");
        System.out.println("6. Traversal DFS");
        System.out.println("7. Traversal BFS");
        System.out.println("8. Quit");
        System.out.println("=".repeat(30));
    }

    private static void tambahVertex() {
        System.out.print("\nMasukkan label vertex: ");
        String label = scanner.nextLine().trim();

        if (label.isEmpty()) {
            System.out.println("Label tidak boleh kosong!");
            return;
        }

        graph.addVertex(label);
    }

    private static void hapusVertex() {
        if (graph.getVertexCount() == 0) {
            System.out.println("\nGraph kosong! Tidak ada vertex yang bisa dihapus.");
            return;
        }

        System.out.print("\nMasukkan label vertex yang ingin dihapus: ");
        String label = scanner.nextLine().trim();
        graph.removeVertex(label);
    }

    private static void tambahEdge() {
        if (graph.getVertexCount() < 2) {
            System.out.println("\nMinimal ada 2 vertex untuk membuat edge!");
            return;
        }

        System.out.print("\nMasukkan vertex asal: ");
        String from = scanner.nextLine().trim();
        System.out.print("Masukkan vertex tujuan: ");
        String to = scanner.nextLine().trim();

        graph.addEdge(from, to);
    }

    private static void hapusEdge() {
        System.out.print("\nMasukkan vertex asal: ");
        String from = scanner.nextLine().trim();
        System.out.print("Masukkan vertex tujuan: ");
        String to = scanner.nextLine().trim();

        graph.removeEdge(from, to);
    }

    private static void tampilkanGraph() {
        graph.displayGraph();
    }

    private static void traversalDFS() {
        if (graph.getVertexCount() == 0) {
            System.out.println("\nGraph kosong! Tidak ada yang bisa di-traverse.");
            return;
        }

        System.out.print("\nMasukkan vertex awal untuk DFS: ");
        String startVertex = scanner.nextLine().trim();
        graph.dfs(startVertex);
    }

    private static void traversalBFS() {
        if (graph.getVertexCount() == 0) {
            System.out.println("\nGraph kosong! Tidak ada yang bisa di-traverse.");
            return;
        }

        System.out.print("\nMasukkan vertex awal untuk BFS: ");
        String startVertex = scanner.nextLine().trim();
        graph.bfs(startVertex);
    }
}

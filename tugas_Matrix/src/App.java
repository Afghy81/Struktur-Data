import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static Matrix originalMatrix = null; // Simpan matrix asli
    static Matrix matrix = null; // Matrix untuk operasi

    public static void main(String[] args) {
        displayWelcome();
        mainMenu();
        scanner.close();
    }

    static void displayWelcome() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║     PROGRAM STRUKTUR DATA MATRIX     ║");
        System.out.println("╚══════════════════════════════════════╝\n");
    }

    static void mainMenu() {
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║             MAIN MENU                ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Input/Buat Matrix                 ║");
            System.out.println("║ 2. Lihat Matrix                      ║");
            System.out.println("║ 3. Operasi Matrix                    ║");
            System.out.println("║ 4. Keluar                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Pilih opsi (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    inputMatrix();
                    break;
                case "2":
                    if (originalMatrix != null) {
                        originalMatrix.print();
                    } else {
                        System.out.println("✗ Matrix belum dibuat!\n");
                    }
                    break;
                case "3":
                    if (originalMatrix != null) {
                        operationMenu();
                    } else {
                        System.out.println("✗ Buat matrix terlebih dahulu!\n");
                    }
                    break;
                case "4":
                    System.out.println("\nTerima kasih! Program berakhir.\n");
                    return;
                default:
                    System.out.println("✗ Opsi tidak valid!\n");
            }
        }
    }

    static void inputMatrix() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         INPUT MATRIX                 ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        System.out.print("Masukkan jumlah baris: ");
        int rows = getIntInput();
        System.out.print("Masukkan jumlah kolom: ");
        int cols = getIntInput();

        if (rows <= 0 || cols <= 0) {
            System.out.println("✗ Dimensi harus positif!\n");
            return;
        }

        originalMatrix = new Matrix(rows, cols);

        System.out.println("\nMasukkan elemen matrix (baris per baris):");
        int[][] data = originalMatrix.getData();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Matrix[%d][%d]: ", i, j);
                data[i][j] = getIntInput();
            }
        }
        System.out.println("✓ Matrix berhasil dibuat!\n");
    }

    // Method untuk membuat copy dari matrix
    static Matrix copyMatrix(Matrix original) {
        Matrix copy = new Matrix(original.getRows(), original.getCols());
        int[][] originalData = original.getData();
        int[][] copyData = copy.getData();

        for (int i = 0; i < original.getRows(); i++) {
            for (int j = 0; j < original.getCols(); j++) {
                copyData[i][j] = originalData[i][j];
            }
        }
        return copy;
    }

    static void operationMenu() {
        while (true) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║         MENU OPERASI MATRIX          ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. Sort Matrix                       ║");
            System.out.println("║ 2. Rotate Matrix                     ║");
            System.out.println("║ 3. Traversal Matrix                  ║");
            System.out.println("║ 4. Print Matrix Spiral Form          ║");
            System.out.println("║ 5. Transpose Matrix                  ║");
            System.out.println("║ 6. Kembali ke Menu Utama             ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Pilih opsi (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    sortMenu();
                    break;
                case "2":
                    rotateMenu();
                    break;
                case "3":
                    traversalMenu();
                    break;
                case "4":
                    matrix = copyMatrix(originalMatrix);
                    originalMatrix.print();
                    System.out.println("╔════════════════════════════════════════╗");
                    System.out.println("║ Spiral Form Result:                    ║");
                    System.out.println("╚════════════════════════════════════════╝");
                    matrix.spiralTraversal();
                    break;
                case "5":
                    matrix = copyMatrix(originalMatrix);
                    matrix.printBefore("Transpose");
                    matrix.transpose();
                    matrix.printAfter("Transpose");
                    break;
                case "6":
                    return;
                default:
                    System.out.println("✗ Opsi tidak valid!\n");
            }
        }
    }

    static void sortMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         MENU SORT MATRIX             ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ a. Sort Matrix Row-wise              ║");
        System.out.println("║ b. Sort Matrix Column-wise           ║");
        System.out.println("║ c. Kembali                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Pilih opsi (a/b/c): ");

        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "a":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Sort Row-wise");
                matrix.sortRowWise();
                matrix.printAfter("Sort Row-wise");
                break;
            case "b":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Sort Column-wise");
                matrix.sortColumnWise();
                matrix.printAfter("Sort Column-wise");
                break;
            case "c":
                break;
            default:
                System.out.println("✗ Opsi tidak valid!\n");
        }
    }

    static void rotateMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        MENU ROTATE MATRIX            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ a. Rotate Clockwise by 1             ║");
        System.out.println("║ b. Rotate Counter-Clockwise by 1     ║");
        System.out.println("║ c. Rotate by 90 Degrees              ║");
        System.out.println("║ d. Rotate by 180 Degrees             ║");
        System.out.println("║ e. Kembali                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Pilih opsi (a/b/c/d/e): ");

        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "a":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Rotate Clockwise by 1");
                matrix.rotateClockwiseBy1();
                matrix.printAfter("Rotate Clockwise by 1");
                break;
            case "b":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Rotate Counter-Clockwise by 1");
                matrix.rotateCounterClockwiseBy1();
                matrix.printAfter("Rotate Counter-Clockwise by 1");
                break;
            case "c":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Rotate by 90 Degrees");
                matrix.rotateBy90();
                matrix.printAfter("Rotate by 90 Degrees");
                break;
            case "d":
                matrix = copyMatrix(originalMatrix);
                matrix.printBefore("Rotate by 180 Degrees");
                matrix.rotateBy180();
                matrix.printAfter("Rotate by 180 Degrees");
                break;
            case "e":
                break;
            default:
                System.out.println("✗ Opsi tidak valid!\n");
        }
    }

    static void traversalMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       MENU TRAVERSAL MATRIX          ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ a. Row-wise Traversal                ║");
        System.out.println("║ b. Column-wise Traversal             ║");
        System.out.println("║ c. Kembali                           ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("Pilih opsi (a/b/c): ");

        String choice = scanner.nextLine().trim().toLowerCase();

        switch (choice) {
            case "a":
                matrix = copyMatrix(originalMatrix);
                originalMatrix.print();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║ Row-wise Traversal Result:             ║");
                System.out.println("╚════════════════════════════════════════╝");
                matrix.rowWiseTraversal();
                break;
            case "b":
                matrix = copyMatrix(originalMatrix);
                originalMatrix.print();
                System.out.println("╔════════════════════════════════════════╗");
                System.out.println("║ Column-wise Traversal Result:          ║");
                System.out.println("╚════════════════════════════════════════╝");
                matrix.columnWiseTraversal();
                break;
            case "c":
                break;
            default:
                System.out.println("✗ Opsi tidak valid!\n");
        }
    }

    static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.print("✗ Input tidak valid! Masukkan angka: ");
            return getIntInput();
        }
    }
}

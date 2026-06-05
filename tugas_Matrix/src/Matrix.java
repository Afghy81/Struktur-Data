import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private int[][] data;
    private int rows;
    private int cols;

    // Constructor
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new int[rows][cols];
    }

    // Getters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] newData) {
        this.data = newData;
    }

    // Print Matrix
    public void print() {
        System.out.println("\n=== Matrix ===");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%6d", data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Print Before
    public void printBefore(String operationName) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ BEFORE: " + operationName);
        System.out.println("╚════════════════════════════════════════╝");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%6d", data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Print After
    public void printAfter(String operationName) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║ AFTER: " + operationName);
        System.out.println("╚════════════════════════════════════════╝");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%6d", data[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    // 1a. Sort Matrix Row-wise
    public void sortRowWise() {
        for (int i = 0; i < rows; i++) {
            Arrays.sort(data[i]);
        }
        System.out.println("✓ Matrix sorted row-wise");
    }

    // 1b. Sort Matrix Column-wise
    public void sortColumnWise() {
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows - 1; i++) {
                for (int k = i + 1; k < rows; k++) {
                    if (data[i][j] > data[k][j]) {
                        int temp = data[i][j];
                        data[i][j] = data[k][j];
                        data[k][j] = temp;
                    }
                }
            }
        }
        System.out.println("✓ Matrix sorted column-wise");
    }

    // 2a. Rotate Matrix Clockwise by 1 (Perimeter approach)
    public void rotateClockwiseBy1() {
        if (rows != cols) {
            System.out.println("✗ Matrix harus persegi untuk rotasi!");
            return;
        }

        int n = rows;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;

            // Extract perimeter elements in clockwise order
            List<Integer> perimeter = new ArrayList<>();

            // Top row (left to right)
            for (int i = first; i <= last; i++) {
                perimeter.add(data[first][i]);
            }

            // Right column (top to bottom, excluding top-right)
            for (int i = first + 1; i <= last; i++) {
                perimeter.add(data[i][last]);
            }

            // Bottom row (right to left, excluding bottom-right)
            if (last > first) {
                for (int i = last - 1; i >= first; i--) {
                    perimeter.add(data[last][i]);
                }
            }

            // Left column (bottom to top, excluding bottom-left and top-left)
            if (last > first) {
                for (int i = last - 1; i > first; i--) {
                    perimeter.add(data[i][first]);
                }
            }

            // Rotate perimeter: shift RIGHT by 1 (for clockwise rotation)
            int temp = perimeter.get(perimeter.size() - 1);
            for (int i = perimeter.size() - 1; i > 0; i--) {
                perimeter.set(i, perimeter.get(i - 1));
            }
            perimeter.set(0, temp);

            // Put elements back
            int index = 0;

            // Top row
            for (int i = first; i <= last; i++) {
                data[first][i] = perimeter.get(index++);
            }

            // Right column
            for (int i = first + 1; i <= last; i++) {
                data[i][last] = perimeter.get(index++);
            }

            // Bottom row
            if (last > first) {
                for (int i = last - 1; i >= first; i--) {
                    data[last][i] = perimeter.get(index++);
                }
            }

            // Left column
            if (last > first) {
                for (int i = last - 1; i > first; i--) {
                    data[i][first] = perimeter.get(index++);
                }
            }
        }

        System.out.println("✓ Matrix rotated clockwise by 1");
    }

    // 2b. Rotate Matrix Counter-Clockwise by 1 (Perimeter approach)
    public void rotateCounterClockwiseBy1() {
        if (rows != cols) {
            System.out.println("✗ Matrix harus persegi untuk rotasi!");
            return;
        }

        int n = rows;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;

            // Extract perimeter elements in clockwise order
            List<Integer> perimeter = new ArrayList<>();

            // Top row (left to right)
            for (int i = first; i <= last; i++) {
                perimeter.add(data[first][i]);
            }

            // Right column (top to bottom, excluding top-right)
            for (int i = first + 1; i <= last; i++) {
                perimeter.add(data[i][last]);
            }

            // Bottom row (right to left, excluding bottom-right)
            if (last > first) {
                for (int i = last - 1; i >= first; i--) {
                    perimeter.add(data[last][i]);
                }
            }

            // Left column (bottom to top, excluding bottom-left and top-left)
            if (last > first) {
                for (int i = last - 1; i > first; i--) {
                    perimeter.add(data[i][first]);
                }
            }

            // Rotate perimeter: shift LEFT by 1 (for counter-clockwise rotation)
            int temp = perimeter.get(0);
            for (int i = 0; i < perimeter.size() - 1; i++) {
                perimeter.set(i, perimeter.get(i + 1));
            }
            perimeter.set(perimeter.size() - 1, temp);

            // Put elements back
            int index = 0;

            // Top row
            for (int i = first; i <= last; i++) {
                data[first][i] = perimeter.get(index++);
            }

            // Right column
            for (int i = first + 1; i <= last; i++) {
                data[i][last] = perimeter.get(index++);
            }

            // Bottom row
            if (last > first) {
                for (int i = last - 1; i >= first; i--) {
                    data[last][i] = perimeter.get(index++);
                }
            }

            // Left column
            if (last > first) {
                for (int i = last - 1; i > first; i--) {
                    data[i][first] = perimeter.get(index++);
                }
            }
        }

        System.out.println("✓ Matrix rotated counter-clockwise by 1");
    }

    // 2c. Rotate Matrix by 90 degrees (Clockwise)
    public void rotateBy90() {
        if (rows != cols) {
            System.out.println("✗ Matrix harus persegi untuk rotasi!");
            return;
        }
        for (int i = 0; i < rows / 2; i++) {
            for (int j = i; j < cols - i - 1; j++) {
                int top = data[i][j];
                data[i][j] = data[rows - 1 - j][i];
                data[rows - 1 - j][i] = data[rows - 1 - i][cols - 1 - j];
                data[rows - 1 - i][cols - 1 - j] = data[j][cols - 1 - i];
                data[j][cols - 1 - i] = top;
            }
        }
        System.out.println("✓ Matrix rotated by 90 degrees");
    }

    // 2d. Rotate Matrix by 180 degrees
    public void rotateBy180() {
        if (rows != cols) {
            System.out.println("✗ Matrix harus persegi untuk rotasi!");
            return;
        }
        int[][] temp = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                temp[rows - 1 - i][cols - 1 - j] = data[i][j];
            }
        }
        data = temp;
        System.out.println("✓ Matrix rotated by 180 degrees");
    }

    // 3a. Row-wise Traversal
    public void rowWiseTraversal() {
        System.out.println("\n=== Row-wise Traversal ===");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(data[i][j] + " ");
            }
        }
        System.out.println("\n");
    }

    // 3b. Column-wise Traversal
    public void columnWiseTraversal() {
        System.out.println("\n=== Column-wise Traversal ===");
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                System.out.print(data[i][j] + " ");
            }
        }
        System.out.println("\n");
    }

    // 4. Print Matrix in Spiral Form
    public void spiralTraversal() {
        System.out.println("\n=== Spiral Form ===");
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;

        while (top <= bottom && left <= right) {
            // Traverse top row
            for (int i = left; i <= right; i++) {
                System.out.print(data[top][i] + " ");
            }
            top++;

            // Traverse right column
            for (int i = top; i <= bottom; i++) {
                System.out.print(data[i][right] + " ");
            }
            right--;

            // Traverse bottom row
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    System.out.print(data[bottom][i] + " ");
                }
                bottom--;
            }

            // Traverse left column
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.print(data[i][left] + " ");
                }
                left++;
            }
        }
        System.out.println("\n");
    }

    // 5. Transpose Matrix
    public void transpose() {
        int[][] temp = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                temp[j][i] = data[i][j];
            }
        }
        // Swap dimensions
        int tempRows = rows;
        rows = cols;
        cols = tempRows;
        data = temp;
        System.out.println("✓ Matrix transposed. Dimensi baru: " + rows + " x " + cols);
    }
}

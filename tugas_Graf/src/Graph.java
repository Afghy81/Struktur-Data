import java.util.*;

public class Graph {
    private int maxVertices;
    private String[] vertices;
    private int[][] adjMatrix;
    private int vertexCount;
    private List<Edge> edges;

    private static class Edge {
        String from;
        String to;

        Edge(String from, String to) {
            this.from = from;
            this.to = to;
        }
    }

    public Graph(int maxVertices) {
        this.maxVertices = maxVertices;
        this.vertices = new String[maxVertices];
        this.adjMatrix = new int[maxVertices][maxVertices];
        this.vertexCount = 0;
        this.edges = new ArrayList<>();
    }

    // Tambah Vertex
    public boolean addVertex(String label) {
        if (vertexCount >= maxVertices) {
            System.out.println("Graph sudah penuh!");
            return false;
        }

        // Cek apakah vertex sudah ada
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(label)) {
                System.out.println("Vertex '" + label + "' sudah ada!");
                return false;
            }
        }

        vertices[vertexCount] = label;
        vertexCount++;
        System.out.println("Vertex '" + label + "' berhasil ditambahkan!");
        return true;
    }

    // Hapus Vertex
    public boolean removeVertex(String label) {
        int index = -1;

        // Cari index vertex
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(label)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Vertex '" + label + "' tidak ditemukan!");
            return false;
        }

        edges.removeIf(edge -> edge.from.equals(label) || edge.to.equals(label));

        // Hapus vertex dengan shift array
        for (int i = index; i < vertexCount - 1; i++) {
            vertices[i] = vertices[i + 1];
        }

        // Hapus baris dan kolom dari matrix
        for (int i = index; i < vertexCount - 1; i++) {
            for (int j = 0; j < vertexCount; j++) {
                adjMatrix[i][j] = adjMatrix[i + 1][j];
            }
        }

        for (int j = index; j < vertexCount - 1; j++) {
            for (int i = 0; i < vertexCount; i++) {
                adjMatrix[i][j] = adjMatrix[i][j + 1];
            }
        }

        vertexCount--;
        System.out.println("Vertex '" + label + "' berhasil dihapus!");
        return true;
    }

    // Dapatkan index vertex
    private int getVertexIndex(String label) {
        for (int i = 0; i < vertexCount; i++) {
            if (vertices[i].equals(label)) {
                return i;
            }
        }
        return -1;
    }

    // Tambah Edge (directed)
    public boolean addEdge(String from, String to) {
        int fromIndex = getVertexIndex(from);
        int toIndex = getVertexIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            System.out.println("Salah satu atau kedua vertex tidak ditemukan!");
            return false;
        }

        if (adjMatrix[fromIndex][toIndex] > 0) {
            System.out.println("Edge dari '" + from + "' ke '" + to + "' sudah ada!");
            return false;
        }

        adjMatrix[fromIndex][toIndex] = 1;
        edges.add(new Edge(from, to));
        System.out.println("Edge dari '" + from + "' ke '" + to + "' berhasil ditambahkan!");
        return true;
    }

    // Hapus Edge
    public boolean removeEdge(String from, String to) {
        int fromIndex = getVertexIndex(from);
        int toIndex = getVertexIndex(to);

        if (fromIndex == -1 || toIndex == -1) {
            System.out.println("Salah satu atau kedua vertex tidak ditemukan!");
            return false;
        }

        if (adjMatrix[fromIndex][toIndex] == 0) {
            System.out.println("Edge dari '" + from + "' ke '" + to + "' tidak ada!");
            return false;
        }

        adjMatrix[fromIndex][toIndex] = 0;
        edges.removeIf(edge -> edge.from.equals(from) && edge.to.equals(to));
        System.out.println("Edge dari '" + from + "' ke '" + to + "' berhasil dihapus!");
        return true;
    }

    // Tampilkan Graph sebagai Adjacency Matrix dan visualisasi berarah
    public void displayGraph() {
        if (vertexCount == 0) {
            System.out.println("Graph kosong!");
            return;
        }

        displayDirectedGraphVisualization();
        System.out.println();
    }

    // DFS Traversal
    public void dfs(String startLabel) {
        int startIndex = getVertexIndex(startLabel);

        if (startIndex == -1) {
            System.out.println("Vertex '" + startLabel + "' tidak ditemukan!");
            return;
        }

        if (vertexCount == 0) {
            System.out.println("Graph kosong!");
            return;
        }

        System.out.println("\n=== DFS Traversal dimulai dari '" + startLabel + "' ===");
        boolean[] visited = new boolean[vertexCount];
        List<String> order = new ArrayList<>();
        List<String> moves = new ArrayList<>();

        dfsHelper(startIndex, visited, order, moves);
        displayDirectedGraphVisualization();
        System.out.println("Urutan DFS: " + String.join(" -> ", order));
        printTraversalMoves("DFS", moves);
        System.out.println();
    }

    private void dfsHelper(int index, boolean[] visited, List<String> order, List<String> moves) {
        visited[index] = true;
        order.add(vertices[index]);

        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[index][i] > 0 && !visited[i]) {
                moves.add(formatMove(index, i));
                dfsHelper(i, visited, order, moves);
            }
        }
    }

    // BFS Traversal
    public void bfs(String startLabel) {
        int startIndex = getVertexIndex(startLabel);

        if (startIndex == -1) {
            System.out.println("Vertex '" + startLabel + "' tidak ditemukan!");
            return;
        }

        if (vertexCount == 0) {
            System.out.println("Graph kosong!");
            return;
        }

        boolean[] visited = new boolean[vertexCount];
        Queue<Integer> queue = new LinkedList<>();

        visited[startIndex] = true;
        queue.add(startIndex);

        System.out.println("\n=== BFS Traversal dimulai dari '" + startLabel + "' ===");
        List<String> order = new ArrayList<>();
        List<String> moves = new ArrayList<>();

        bfsHelper(visited, queue, order, moves);
        displayDirectedGraphVisualization();
        System.out.println("Urutan BFS: " + String.join(" -> ", order));
        printTraversalMoves("BFS", moves);
        System.out.println();
    }

    private void bfsHelper(boolean[] visited, Queue<Integer> queue, List<String> order, List<String> moves) {
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(vertices[current]);

            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[current][i] > 0 && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                    moves.add(formatMove(current, i));
                }
            }
        }
    }

    private void displayDirectedGraphVisualization() {
        printAdjacencyMatrix();
        System.out.println();
        System.out.println("=== VISUALISASI GRAPH ASCII ===");
        if (edges.isEmpty()) {
            System.out.println("(Belum ada edge)");
            return;
        }

        printAsciiGraphDiagram();
    }

    private void printAdjacencyMatrix() {
        System.out.println("=== ADJACENCY MATRIX ===");

        // Header kolom
        System.out.print("    ");
        for (int i = 0; i < vertexCount; i++) {
            System.out.printf("%4s", vertices[i]);
        }
        System.out.println();

        // Baris matrix
        for (int i = 0; i < vertexCount; i++) {
            System.out.printf("%4s", vertices[i]);
            for (int j = 0; j < vertexCount; j++) {
                System.out.printf("%4d", adjMatrix[i][j]);
            }
            System.out.println();
        }
    }

    private String formatMove(int fromIndex, int toIndex) {
        return "[" + vertices[fromIndex] + "] ---> [" + vertices[toIndex] + "]";
    }

    private void printTraversalMoves(String traversalName, List<String> moves) {
        if (moves.isEmpty()) {
            System.out.println("Pergerakan " + traversalName + ": tidak ada vertex baru yang dapat dikunjungi dari vertex awal.");
            return;
        }

        System.out.println("Pergerakan " + traversalName + ":");
        for (int i = 0; i < moves.size(); i++) {
            System.out.println((i + 1) + ". " + moves.get(i));
        }
    }

    private void printAsciiGraphDiagram() {
        int maxLabelLength = getMaxVertexLabelLength();
        int labelWidth = maxLabelLength + 4;
        int horizontalGap = Math.max(13, maxLabelLength + 12);
        int rowGap = 8;
        int rows = (vertexCount + 1) / 2;
        int width = (labelWidth * 2) + horizontalGap + 6;
        int height = ((rows - 1) * rowGap) + 1;
        char[][] canvas = createCanvas(height, width);
        int[] xPositions = new int[vertexCount];
        int[] yPositions = new int[vertexCount];
        boolean[][] drawnEdges = new boolean[vertexCount][vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            int row = i / 2;
            int col = i % 2;

            xPositions[i] = col == 0 ? 1 : labelWidth + horizontalGap + 1;
            yPositions[i] = row * rowGap;
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (adjMatrix[i][j] == 0 || drawnEdges[i][j]) {
                    continue;
                }

                boolean bidirectional = adjMatrix[j][i] > 0;
                drawEdge(canvas, i, j, xPositions, yPositions, bidirectional);
                drawnEdges[i][j] = true;

                if (bidirectional) {
                    drawnEdges[j][i] = true;
                }
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            placeText(canvas[yPositions[i]], xPositions[i], getVertexText(i));
        }

        for (char[] line : canvas) {
            System.out.println(trimRight(new String(line)));
        }
    }

    private void drawEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions, boolean bidirectional) {
        int fromRow = fromIndex / 2;
        int fromCol = fromIndex % 2;
        int toRow = toIndex / 2;
        int toCol = toIndex % 2;

        if (fromRow == toRow && Math.abs(fromCol - toCol) == 1) {
            drawHorizontalEdge(canvas, fromIndex, toIndex, xPositions, yPositions, bidirectional);
            return;
        }

        if (fromCol == toCol && Math.abs(fromRow - toRow) == 1) {
            drawVerticalEdge(canvas, fromIndex, toIndex, xPositions, yPositions, bidirectional);
            return;
        }

        if (fromCol != toCol) {
            drawDiagonalEdge(canvas, fromIndex, toIndex, xPositions, yPositions, bidirectional);
            return;
        }

        drawBentEdge(canvas, fromIndex, toIndex, xPositions, yPositions, bidirectional);
    }

    private void drawHorizontalEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions, boolean bidirectional) {
        int y = yPositions[fromIndex];

        if (bidirectional) {
            int leftIndex = xPositions[fromIndex] < xPositions[toIndex] ? fromIndex : toIndex;
            int rightIndex = leftIndex == fromIndex ? toIndex : fromIndex;
            int start = xPositions[leftIndex] + getVertexText(leftIndex).length();
            int end = xPositions[rightIndex] - 1;

            putEdgeChar(canvas, y, start, '<');
            for (int x = start + 1; x < end; x++) {
                putEdgeChar(canvas, y, x, '-');
            }
            putEdgeChar(canvas, y, end, '>');
            return;
        }

        if (xPositions[fromIndex] < xPositions[toIndex]) {
            int start = xPositions[fromIndex] + getVertexText(fromIndex).length();
            int end = xPositions[toIndex] - 1;

            for (int x = start; x < end; x++) {
                putEdgeChar(canvas, y, x, '-');
            }
            putEdgeChar(canvas, y, end, '>');
        } else {
            int start = xPositions[toIndex] + getVertexText(toIndex).length();
            int end = xPositions[fromIndex] - 1;

            putEdgeChar(canvas, y, start, '<');
            for (int x = start + 1; x <= end; x++) {
                putEdgeChar(canvas, y, x, '-');
            }
        }
    }

    private void drawVerticalEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions, boolean bidirectional) {
        int x = xPositions[fromIndex] + (getVertexText(fromIndex).length() / 2);
        int fromY = yPositions[fromIndex];
        int toY = yPositions[toIndex];

        if (bidirectional) {
            int upperY = Math.min(fromY, toY);
            int lowerY = Math.max(fromY, toY);

            putEdgeChar(canvas, upperY + 1, x, '^');
            for (int y = upperY + 2; y < lowerY - 1; y++) {
                putEdgeChar(canvas, y, x, '|');
            }
            putEdgeChar(canvas, lowerY - 1, x, 'v');
            return;
        }

        if (fromY < toY) {
            for (int y = fromY + 1; y < toY - 1; y++) {
                putEdgeChar(canvas, y, x, '|');
            }
            putEdgeChar(canvas, toY - 1, x, 'v');
        } else {
            putEdgeChar(canvas, toY + 1, x, '^');
            for (int y = toY + 2; y < fromY; y++) {
                putEdgeChar(canvas, y, x, '|');
            }
        }
    }

    private void drawBentEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions, boolean bidirectional) {
        if (bidirectional) {
            drawBidirectionalBentEdge(canvas, fromIndex, toIndex, xPositions, yPositions);
            return;
        }

        int fromX = xPositions[fromIndex] + (getVertexText(fromIndex).length() / 2);
        int toX = xPositions[toIndex] + (getVertexText(toIndex).length() / 2);
        int fromY = yPositions[fromIndex];
        int toY = yPositions[toIndex];

        if (fromY < toY) {
            for (int y = fromY + 1; y < toY; y++) {
                putEdgeChar(canvas, y, fromX, '|');
            }
        } else if (fromY > toY) {
            for (int y = fromY - 1; y > toY; y--) {
                putEdgeChar(canvas, y, fromX, '|');
            }
        }

        if (fromX < toX) {
            int targetStart = xPositions[toIndex] - 1;

            for (int x = fromX; x < targetStart; x++) {
                putEdgeChar(canvas, toY, x, '-');
            }
            putEdgeChar(canvas, toY, targetStart, '>');
        } else if (fromX > toX) {
            int targetEnd = xPositions[toIndex] + getVertexText(toIndex).length();

            putEdgeChar(canvas, toY, targetEnd, '<');
            for (int x = targetEnd + 1; x <= fromX; x++) {
                putEdgeChar(canvas, toY, x, '-');
            }
        }
    }

    private void drawDiagonalEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions, boolean bidirectional) {
        int fromX = xPositions[fromIndex] + (getVertexText(fromIndex).length() / 2);
        int toX = xPositions[toIndex] + (getVertexText(toIndex).length() / 2);
        int fromY = yPositions[fromIndex];
        int toY = yPositions[toIndex];
        int startY = fromY;
        int endY = toY;
        int startX = fromX;
        int endX = toX;

        if (bidirectional && fromY > toY) {
            startY = toY;
            endY = fromY;
            startX = toX;
            endX = fromX;
        }

        int deltaY = endY - startY;
        int deltaX = endX - startX;

        if (deltaY == 0) {
            drawHorizontalEdge(canvas, fromIndex, toIndex, xPositions, yPositions, false);
            return;
        }

        char diagonalChar = (deltaX > 0 && deltaY > 0) || (deltaX < 0 && deltaY < 0) ? '\\' : '/';
        int stepY = deltaY > 0 ? 1 : -1;

        for (int y = startY + stepY; y != endY; y += stepY) {
            double progress = (double) (y - startY) / deltaY;
            int x = startX + (int) Math.round(deltaX * progress);
            putEdgeChar(canvas, y, x, diagonalChar);
        }

        placeDiagonalArrow(canvas, fromX, fromY, toX, toY, deltaX, deltaY, bidirectional, false);

        if (bidirectional) {
            placeDiagonalArrow(canvas, toX, toY, fromX, fromY, deltaX, deltaY, true, true);
        }
    }

    private void placeDiagonalArrow(char[][] canvas, int fromX, int fromY, int toX, int toY, int deltaX, int deltaY, boolean diagonalIsBidirectional, boolean reverseArrow) {
        int distanceY = Math.abs(toY - fromY);

        if (distanceY == 0) {
            return;
        }

        int arrowStep = Math.min(2, Math.max(1, distanceY - 1));
        int arrowY = fromY + (toY > fromY ? arrowStep : -arrowStep);
        double progress = (double) (arrowY - fromY) / (toY - fromY);
        int arrowX = fromX + (int) Math.round((toX - fromX) * progress);
        char arrow = toY > fromY ? 'v' : '^';

        if (diagonalIsBidirectional && reverseArrow) {
            arrow = toY > fromY ? 'v' : '^';
        }

        putEdgeChar(canvas, arrowY, arrowX, arrow);
    }

    private void drawBidirectionalBentEdge(char[][] canvas, int fromIndex, int toIndex, int[] xPositions, int[] yPositions) {
        int rightIndex = xPositions[fromIndex] > xPositions[toIndex] ? fromIndex : toIndex;
        int leftIndex = rightIndex == fromIndex ? toIndex : fromIndex;
        int upperIndex = yPositions[fromIndex] < yPositions[toIndex] ? fromIndex : toIndex;
        int lowerIndex = upperIndex == fromIndex ? toIndex : fromIndex;
        int bendX = xPositions[rightIndex] + (getVertexText(rightIndex).length() / 2);
        int upperY = yPositions[upperIndex];
        int lowerY = yPositions[lowerIndex];

        putEdgeChar(canvas, upperY + 1, bendX, '^');
        for (int y = upperY + 2; y < lowerY; y++) {
            putEdgeChar(canvas, y, bendX, '|');
        }

        if (lowerIndex == leftIndex) {
            int targetEnd = xPositions[leftIndex] + getVertexText(leftIndex).length();

            putEdgeChar(canvas, lowerY, targetEnd, '<');
            for (int x = targetEnd + 1; x <= bendX; x++) {
                putEdgeChar(canvas, lowerY, x, '-');
            }
        } else {
            int targetStart = xPositions[rightIndex] - 1;

            for (int x = xPositions[leftIndex] + (getVertexText(leftIndex).length() / 2); x < targetStart; x++) {
                putEdgeChar(canvas, lowerY, x, '-');
            }
            putEdgeChar(canvas, lowerY, targetStart, '>');
        }
    }

    private char[][] createCanvas(int height, int width) {
        char[][] canvas = new char[height][width];

        for (int i = 0; i < height; i++) {
            Arrays.fill(canvas[i], ' ');
        }

        return canvas;
    }

    private void putEdgeChar(char[][] canvas, int y, int x, char value) {
        if (y < 0 || y >= canvas.length || x < 0 || x >= canvas[y].length) {
            return;
        }

        if (canvas[y][x] == ' ' || canOverrideEdgeChar(canvas[y][x], value)) {
            canvas[y][x] = value;
        } else if (isLineChar(canvas[y][x]) && isLineChar(value) && canvas[y][x] != value) {
            canvas[y][x] = '+';
        }
    }

    private boolean canOverrideEdgeChar(char current, char next) {
        boolean nextIsArrow = next == '^' || next == 'v' || next == '<' || next == '>';

        return isLineChar(current) && nextIsArrow;
    }

    private boolean isLineChar(char value) {
        return value == '|' || value == '-' || value == '/' || value == '\\' || value == '+';
    }

    private void placeText(char[] line, int start, String text) {
        for (int i = 0; i < text.length() && start + i < line.length; i++) {
            line[start + i] = text.charAt(i);
        }
    }

    private String trimRight(String text) {
        int end = text.length();

        while (end > 0 && text.charAt(end - 1) == ' ') {
            end--;
        }

        return text.substring(0, end);
    }

    private String getVertexText(int index) {
        return "[ " + vertices[index] + " ]";
    }

    private int getMaxVertexLabelLength() {
        int maxLength = 1;

        for (int i = 0; i < vertexCount; i++) {
            maxLength = Math.max(maxLength, vertices[i].length());
        }

        return maxLength;
    }

    // Dapatkan daftar vertex
    public String[] getAllVertices() {
        String[] result = new String[vertexCount];
        System.arraycopy(vertices, 0, result, 0, vertexCount);
        return result;
    }

    // Dapatkan jumlah vertex
    public int getVertexCount() {
        return vertexCount;
    }
}

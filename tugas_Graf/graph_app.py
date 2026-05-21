import sys
import math
from PyQt5.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, 
                             QHBoxLayout, QPushButton, QLabel, QLineEdit, 
                             QComboBox, QMessageBox, QTextEdit)
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QFont, QColor
import pyqtgraph as pg
import networkx as nx

class GraphApp(QMainWindow):
    def __init__(self):
        super().__init__()
        self.graph = nx.Graph()
        self.pos = {}
        self.init_ui()
        self.update_vertex_combos()
        
    def init_ui(self):
        self.setWindowTitle('Visualisasi Undirected Graph - PyQTGraph')
        self.setGeometry(100, 100, 1400, 900)
        
        # Central widget
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        
        # Main layout (Horizontal - Menu left, Output right)
        main_layout = QHBoxLayout()
        
        # LEFT PANEL - Control menu
        control_layout = QVBoxLayout()
        
        # Title
        title = QLabel("MENU GRAPH")
        title.setStyleSheet("font-weight: bold; font-size: 14px;")
        control_layout.addWidget(title)
        
        # 1. Tambah Vertex
        control_layout.addWidget(QLabel("1. TAMBAH VERTEX"))
        self.vertex_input = QLineEdit()
        self.vertex_input.setPlaceholderText("Masukkan label vertex")
        control_layout.addWidget(self.vertex_input)
        add_vertex_btn = QPushButton("Tambah Vertex")
        add_vertex_btn.clicked.connect(self.add_vertex)
        control_layout.addWidget(add_vertex_btn)
        
        control_layout.addSpacing(10)
        
        # 2. Hapus Vertex
        control_layout.addWidget(QLabel("2. HAPUS VERTEX"))
        self.delete_vertex_combo = QComboBox()
        self.delete_vertex_combo.setPlaceholderText("Pilih vertex")
        control_layout.addWidget(self.delete_vertex_combo)
        delete_vertex_btn = QPushButton("Hapus Vertex")
        delete_vertex_btn.clicked.connect(self.delete_vertex)
        control_layout.addWidget(delete_vertex_btn)
        
        control_layout.addSpacing(10)
        
        # 3. Tambah Edge
        control_layout.addWidget(QLabel("3. TAMBAH EDGE"))
        self.edge_from_combo = QComboBox()
        self.edge_from_combo.setPlaceholderText("Vertex pertama")
        control_layout.addWidget(self.edge_from_combo)
        self.edge_to_combo = QComboBox()
        self.edge_to_combo.setPlaceholderText("Vertex kedua")
        control_layout.addWidget(self.edge_to_combo)
        add_edge_btn = QPushButton("Tambah Edge")
        add_edge_btn.clicked.connect(self.add_edge)
        control_layout.addWidget(add_edge_btn)
        
        control_layout.addSpacing(10)
        
        # 4. Hapus Edge
        control_layout.addWidget(QLabel("4. HAPUS EDGE"))
        delete_edge_btn = QPushButton("Hapus Edge (pilih dua vertex)")
        delete_edge_btn.clicked.connect(self.delete_edge)
        control_layout.addWidget(delete_edge_btn)
        
        control_layout.addSpacing(10)
        
        # 5. Tampilkan Graph
        control_layout.addWidget(QLabel("5. TAMPILKAN GRAPH"))
        display_graph_btn = QPushButton("Tampilkan Graph")
        display_graph_btn.clicked.connect(self.display_graph)
        control_layout.addWidget(display_graph_btn)
        
        control_layout.addSpacing(10)
        
        # 6. Traversal DFS
        control_layout.addWidget(QLabel("6. TRAVERSAL DFS"))
        self.dfs_start_combo = QComboBox()
        self.dfs_start_combo.setPlaceholderText("Mulai dari vertex")
        control_layout.addWidget(self.dfs_start_combo)
        dfs_btn = QPushButton("Traversal DFS")
        dfs_btn.clicked.connect(self.traversal_dfs)
        control_layout.addWidget(dfs_btn)
        
        control_layout.addSpacing(10)
        
        # 7. Traversal BFS
        control_layout.addWidget(QLabel("7. TRAVERSAL BFS"))
        self.bfs_start_combo = QComboBox()
        self.bfs_start_combo.setPlaceholderText("Mulai dari vertex")
        control_layout.addWidget(self.bfs_start_combo)
        bfs_btn = QPushButton("Traversal BFS")
        bfs_btn.clicked.connect(self.traversal_bfs)
        control_layout.addWidget(bfs_btn)
        
        control_layout.addSpacing(10)
        
        # Quit button
        quit_btn = QPushButton("Quit")
        quit_btn.clicked.connect(self.close)
        control_layout.addWidget(quit_btn)
        
        control_layout.addStretch()
        
        # RIGHT PANEL - Output text (full width output)
        output_layout = QVBoxLayout()
        output_label = QLabel("Output:")
        output_label.setStyleSheet("font-weight: bold; font-size: 12px;")
        output_layout.addWidget(output_label)
        
        self.output_text = QTextEdit()
        self.output_text.setReadOnly(True)
        self.output_text.setStyleSheet("background-color: white; color: black; font-family: Courier; font-size: 10px;")
        output_layout.addWidget(self.output_text)
        
        # Create widgets for left and right panels
        left_widget = QWidget()
        left_widget.setLayout(control_layout)
        left_widget.setMaximumWidth(300)
        
        right_widget = QWidget()
        right_widget.setLayout(output_layout)
        
        # Add to main layout with proportions
        main_layout.addWidget(left_widget, 1)
        main_layout.addWidget(right_widget, 2)
        
        central_widget.setLayout(main_layout)
        
    def update_vertex_combos(self):
        """Update all comboboxes with current vertices"""
        vertices = [str(v) for v in self.graph.nodes()]
        
        self.delete_vertex_combo.blockSignals(True)
        self.edge_from_combo.blockSignals(True)
        self.edge_to_combo.blockSignals(True)
        self.dfs_start_combo.blockSignals(True)
        self.bfs_start_combo.blockSignals(True)
        
        self.delete_vertex_combo.clear()
        self.delete_vertex_combo.addItems(vertices)
        
        self.edge_from_combo.clear()
        self.edge_from_combo.addItems(vertices)
        
        self.edge_to_combo.clear()
        self.edge_to_combo.addItems(vertices)
        
        self.dfs_start_combo.clear()
        self.dfs_start_combo.addItems(vertices)
        
        self.bfs_start_combo.clear()
        self.bfs_start_combo.addItems(vertices)
        
        self.delete_vertex_combo.blockSignals(False)
        self.edge_from_combo.blockSignals(False)
        self.edge_to_combo.blockSignals(False)
        self.dfs_start_combo.blockSignals(False)
        self.bfs_start_combo.blockSignals(False)
        
    def add_vertex(self):
        label = self.vertex_input.text().strip()
        
        if not label:
            QMessageBox.warning(self, "Error", "Label vertex tidak boleh kosong!")
            return
        
        if label in self.graph.nodes():
            QMessageBox.warning(self, "Error", f"Vertex '{label}' sudah ada!")
            return
        
        self.graph.add_node(label)
        
        # Generate position untuk vertex baru
        angle = (len(self.graph.nodes()) - 1) * 2 * math.pi / max(len(self.graph.nodes()), 1)
        radius = 100
        self.pos[label] = (radius * math.cos(angle), radius * math.sin(angle))
        
        self.vertex_input.clear()
        self.output_text.append(f"[OK] Vertex '{label}' berhasil ditambahkan!")
        self.update_vertex_combos()
        
    def delete_vertex(self):
        if len(self.graph.nodes()) == 0:
            QMessageBox.warning(self, "Error", "Graph kosong!")
            return
        
        label = self.delete_vertex_combo.currentText()
        if not label or label.strip() == "":
            QMessageBox.warning(self, "Error", "Pilih vertex yang ingin dihapus!")
            return
        
        self.graph.remove_node(label)
        if label in self.pos:
            del self.pos[label]
        
        self.output_text.append(f"[OK] Vertex '{label}' berhasil dihapus!")
        self.update_vertex_combos()
        
    def add_edge(self):
        if len(self.graph.nodes()) < 2:
            QMessageBox.warning(self, "Error", "Minimal ada 2 vertex untuk membuat edge!")
            return
        
        from_vertex = self.edge_from_combo.currentText().strip()
        to_vertex = self.edge_to_combo.currentText().strip()
        
        if not from_vertex or not to_vertex:
            QMessageBox.warning(self, "Error", "Pilih kedua vertex!")
            return
        
        if from_vertex == to_vertex:
            QMessageBox.warning(self, "Error", "Tidak bisa membuat edge ke vertex yang sama!")
            return
        
        if self.graph.has_edge(from_vertex, to_vertex):
            QMessageBox.warning(self, "Error", f"Edge antara '{from_vertex}' dan '{to_vertex}' sudah ada!")
            return
        
        self.graph.add_edge(from_vertex, to_vertex)
        self.output_text.append(f"[OK] Edge antara '{from_vertex}' dan '{to_vertex}' berhasil ditambahkan!")
        
    def delete_edge(self):
        if len(self.graph.edges()) == 0:
            QMessageBox.warning(self, "Error", "Tidak ada edge untuk dihapus!")
            return
        
        from_vertex = self.edge_from_combo.currentText().strip()
        to_vertex = self.edge_to_combo.currentText().strip()
        
        if not from_vertex or not to_vertex:
            QMessageBox.warning(self, "Error", "Pilih kedua vertex!")
            return
        
        if not self.graph.has_edge(from_vertex, to_vertex):
            QMessageBox.warning(self, "Error", f"Edge antara '{from_vertex}' dan '{to_vertex}' tidak ada!")
            return
        
        self.graph.remove_edge(from_vertex, to_vertex)
        self.output_text.append(f"[OK] Edge antara '{from_vertex}' dan '{to_vertex}' berhasil dihapus!")
    
    def display_graph(self):
        if len(self.graph.nodes()) == 0:
            QMessageBox.warning(self, "Error", "Graph kosong!")
            return
        
        self.output_text.append("\n" + self.get_graph_text())
        
    def traversal_dfs(self):
        if len(self.graph.nodes()) == 0:
            QMessageBox.warning(self, "Error", "Graph kosong!")
            return
        
        start = self.dfs_start_combo.currentText().strip()
        if not start:
            QMessageBox.warning(self, "Error", "Pilih vertex awal untuk DFS!")
            return
        
        if start not in self.graph.nodes():
            QMessageBox.warning(self, "Error", f"Vertex '{start}' tidak ditemukan!")
            return
        
        visited = set()
        result = []
        
        def dfs_helper(node):
            visited.add(node)
            result.append(node)
            # Urutkan neighbors secara alfanumerik agar traversal konsisten
            # dan DFS menjelajah ke dalam secara mendalam (depth-first)
            sorted_neighbors = sorted(self.graph.neighbors(node), key=lambda x: (int(x) if str(x).isdigit() else float('inf'), str(x)))
            for neighbor in sorted_neighbors:
                if neighbor not in visited:
                    dfs_helper(neighbor)
        
        dfs_helper(start)
        
        traversal_result = " - ".join(result)
        self.output_text.append(f"\n=== DFS Traversal dari '{start}' ===")
        self.output_text.append(self.get_graph_text())
        self.output_text.append(f"Urutan: {traversal_result}")
        self.highlight_traversal(result)
        
    def traversal_bfs(self):
        if len(self.graph.nodes()) == 0:
            QMessageBox.warning(self, "Error", "Graph kosong!")
            return
        
        start = self.bfs_start_combo.currentText().strip()
        if not start:
            QMessageBox.warning(self, "Error", "Pilih vertex awal untuk BFS!")
            return
        
        if start not in self.graph.nodes():
            QMessageBox.warning(self, "Error", f"Vertex '{start}' tidak ditemukan!")
            return
        
        from collections import deque
        visited = set()
        queue = deque()
        result = []
        
        visited.add(start)
        queue.append(start)
        
        while queue:
            node = queue.popleft()
            result.append(node)
            for neighbor in self.graph.neighbors(node):
                if neighbor not in visited:
                    visited.add(neighbor)
                    queue.append(neighbor)
        
        traversal_result = " - ".join(result)
        self.output_text.append(f"\n=== BFS Traversal dari '{start}' ===")
        self.output_text.append(self.get_graph_text())
        self.output_text.append(f"Urutan: {traversal_result}")
        self.highlight_traversal(result)
        
    def highlight_traversal(self, traversal_order):
        """Highlight nodes dalam urutan traversal"""
        # Ini bisa di-enhance dengan animasi jika diinginkan
        pass
    
    def get_graph_text(self):
        return self.format_adjacency_matrix() + "\n\n" + self.format_ascii_graph()
    
    def format_adjacency_matrix(self):
        vertices = list(self.graph.nodes())
        if not vertices:
            return "Graph kosong!"
        
        label_width = max(4, max(len(str(vertex)) for vertex in vertices) + 2)
        lines = ["=== ADJACENCY MATRIX (UNDIRECTED) ==="]
        lines.append(" " * label_width + "".join(f"{vertex:>{label_width}}" for vertex in vertices))
        
        for row_vertex in vertices:
            row = [f"{row_vertex:>{label_width}}"]
            for col_vertex in vertices:
                value = 1 if self.graph.has_edge(row_vertex, col_vertex) else 0
                row.append(f"{value:>{label_width}}")
            lines.append("".join(row))
        
        return "\n".join(lines)
    
    def format_ascii_graph(self):
        vertices = sorted([str(v) for v in self.graph.nodes()])
        if not vertices:
            return "Graph kosong!"
        
        lines = ["=== VISUALISASI GRAPH (UNDIRECTED) ==="]
        edges = [(str(v1), str(v2)) for v1, v2 in self.graph.edges()]
        
        if len(edges) == 0:
            lines.append("Vertices: " + ", ".join(vertices))
            lines.append("(Belum ada edge)")
            return "\n".join(lines)
        
        # Layout vertices: 3 kolom, baru ke bawah
        cols = 3
        rows = (len(vertices) + cols - 1) // cols
        
        # Grid lebih tinggi untuk memberi ruang arc edge (rows*14 + extra)
        col_spacing = 28
        grid_width = cols * col_spacing + 4
        grid_height = rows * 14 + 6
        grid = [[" " for _ in range(grid_width)] for _ in range(grid_height)]
        
        # Place vertices & simpan info kolom/baris grid
        vertex_pos = {}        # (x, y) pixel di grid
        vertex_col = {}        # kolom layout (0,1,2,...)
        vertex_row_idx = {}    # baris layout (0,1,...)
        vertex_width_actual = {}
        for idx, vertex in enumerate(vertices):
            col = idx % cols
            row = idx // cols
            x = col * col_spacing + 3
            y = row * 14 + 2
            vertex_pos[vertex] = (x, y)
            vertex_col[vertex] = col
            vertex_row_idx[vertex] = row
            vertex_text = f"[ {vertex} ]"
            vertex_width_actual[vertex] = len(vertex_text)
            for i, char in enumerate(vertex_text):
                if x + i < grid_width:
                    grid[y][x + i] = char
        
        # Pisahkan edge berdasarkan tipe untuk routing yang tepat
        # same_row_adjacent: selisih kolom == 1  → garis lurus
        # same_row_skip:     selisih kolom >= 2  → arc melengkung di bawah
        # same_col:          selisih baris == 1  → vertikal lurus
        # diagonal:          berbeda baris & kolom
        same_row_adj   = []
        same_row_skip  = []
        same_col_edges = []
        diag_edges     = []

        for v1, v2 in edges:
            if v1 not in vertex_pos or v2 not in vertex_pos:
                continue
            r1, c1 = vertex_row_idx[v1], vertex_col[v1]
            r2, c2 = vertex_row_idx[v2], vertex_col[v2]
            if r1 == r2:  # same row
                if abs(c1 - c2) == 1:
                    same_row_adj.append((v1, v2))
                else:
                    same_row_skip.append((v1, v2))
            elif c1 == c2:  # same col
                same_col_edges.append((v1, v2))
            else:
                diag_edges.append((v1, v2))

        # 1) Garis horizontal adjacent - langsung
        for v1, v2 in same_row_adj:
            x1, y1 = vertex_pos[v1]
            x2, y2 = vertex_pos[v2]
            w1, w2 = vertex_width_actual[v1], vertex_width_actual[v2]
            self._draw_horizontal_straight(grid, x1, w1, x2, w2, y1)

        # 2) Arc melengkung untuk same-row non-adjacent
        # Setiap arc menggunakan level berbeda agar tidak overlap satu sama lain
        arc_level_counter = {}  # keyed by (row_idx, arc_depth)
        for v1, v2 in same_row_skip:
            x1, y1 = vertex_pos[v1]
            x2, y2 = vertex_pos[v2]
            w1 = vertex_width_actual[v1]
            w2 = vertex_width_actual[v2]
            col_gap = abs(vertex_col[v1] - vertex_col[v2])
            row_i = vertex_row_idx[v1]
            # Tentukan kedalaman arc berdasarkan col_gap
            base_depth = col_gap + 1          # gap=2 → depth=3, dll
            key = (row_i, col_gap)
            offset = arc_level_counter.get(key, 0)
            arc_level_counter[key] = offset + 2
            depth = base_depth + offset
            # x1_right: ujung kanan vertex kiri; x2_left: ujung kiri vertex kanan
            if x1 < x2:
                x_left_end = x1 + w1
                x_right_start = x2
            else:
                x_left_end = x2 + w2
                x_right_start = x1
            self._draw_arc_below(grid, x_left_end, x_right_start, y1, depth)

        # 3) Vertikal lurus
        for v1, v2 in same_col_edges:
            x1, y1 = vertex_pos[v1]
            x2, y2 = vertex_pos[v2]
            w1 = vertex_width_actual[v1]
            cx = x1 + w1 // 2
            self._draw_vertical_straight(grid, cx, y1, y2)

        # 4) Diagonal - dengan offset agar tidak overlap
        # Kelompokkan diagonal yang melewati jalur yang sama
        # Kunci jalur: (row_atas, row_bawah, col_kiri, col_kanan, arah)
        diag_groups = {}
        for v1, v2 in diag_edges:
            r1, c1 = vertex_row_idx[v1], vertex_col[v1]
            r2, c2 = vertex_row_idx[v2], vertex_col[v2]
            rmin, rmax = min(r1,r2), max(r1,r2)
            cmin, cmax = min(c1,c2), max(c1,c2)
            # arah: True = backslash (\), False = forward slash (/)
            direction = (r1 < r2 and c1 < c2) or (r1 > r2 and c1 > c2)
            key = (rmin, rmax, cmin, cmax, direction)
            diag_groups.setdefault(key, []).append((v1, v2))

        for key, group in diag_groups.items():
            for idx, (v1, v2) in enumerate(group):
                x1, y1 = vertex_pos[v1]
                x2, y2 = vertex_pos[v2]
                w1 = vertex_width_actual[v1]
                w2 = vertex_width_actual[v2]
                # offset agar garis diagonal tidak tepat bertumpuk
                offset = idx  # setiap edge dalam group digeser 1 char
                self._draw_diagonal_offset(grid, x1, y1, x2, y2, w1, w2, offset)
        
        # Convert grid to string
        for row in grid:
            line = "".join(row).rstrip()
            if line.strip():
                lines.append(line)
        
        return "\n".join(lines)
    
    # ------------------------------------------------------------------ #
    #  Metode gambar edge yang baru                                        #
    # ------------------------------------------------------------------ #

    def _draw_horizontal_straight(self, grid, x1, w1, x2, w2, y):
        """Garis horizontal lurus untuk edge adjacent (selisih 1 kolom)"""
        if x1 < x2:
            start, end = x1 + w1, x2
        else:
            start, end = x2 + w2, x1
        for x in range(start, end):
            if 0 <= y < len(grid) and x < len(grid[y]) and grid[y][x] == " ":
                grid[y][x] = "-"

    def _draw_arc_below(self, grid, x_left_end, x_right_start, y, depth):
        """
        Arc melengkung ke bawah untuk edge same-row non-adjacent.
        Bentuk:
            [ A ]                  [ B ]
                \                 /
                 \_______________/
        depth = jumlah baris turun sebelum garis horizontal arc.
        """
        arc_y = y + depth          # baris horizontal bagian bawah arc
        # Pastikan arc_y tidak melebihi grid
        if arc_y >= len(grid):
            depth = len(grid) - y - 2
            arc_y = y + depth
        if depth <= 0:
            # Fallback ke garis lurus jika tidak cukup ruang
            for x in range(x_left_end, x_right_start):
                if x < len(grid[y]) and grid[y][x] == " ":
                    grid[y][x] = "-"
            return

        # Sisi kiri: diagonal \ turun
        for step in range(1, depth + 1):
            cy = y + step
            cx = x_left_end + step - 1
            if 0 <= cy < len(grid) and 0 <= cx < len(grid[cy]):
                if grid[cy][cx] == " ":
                    grid[cy][cx] = "\\"

        # Bagian bawah: garis horizontal _
        hx_start = x_left_end + depth
        hx_end   = x_right_start - depth
        for cx in range(hx_start, hx_end):
            if 0 <= arc_y < len(grid) and 0 <= cx < len(grid[arc_y]):
                if grid[arc_y][cx] == " ":
                    grid[arc_y][cx] = "_"

        # Sisi kanan: diagonal / naik
        for step in range(depth, 0, -1):
            cy = y + step
            cx = x_right_start - step
            if 0 <= cy < len(grid) and 0 <= cx < len(grid[cy]):
                if grid[cy][cx] == " ":
                    grid[cy][cx] = "/"

    def _draw_vertical_straight(self, grid, cx, y1, y2):
        """Garis vertikal lurus untuk edge same-col"""
        top_y    = min(y1, y2)
        bottom_y = max(y1, y2)
        for y in range(top_y + 1, bottom_y):
            if 0 <= y < len(grid) and 0 <= cx < len(grid[y]):
                if grid[y][cx] == " ":
                    grid[y][cx] = "|"

    def _draw_diagonal_offset(self, grid, x1, y1, x2, y2, w1, w2, offset=0):
        """
        Diagonal dengan offset horizontal agar edge yang melewati jalur
        yang sama tetap bisa dibedakan satu sama lain.
        offset=0 → titik tengah vertex (seperti semula)
        offset=1 → geser 1 char ke kanan dari tengah
        dst.
        """
        # Center + offset agar edge berbeda tidak bertumpuk persis
        cx1 = x1 + w1 // 2 + offset
        cx2 = x2 + w2 // 2 + offset

        # Normalize ke arah atas → bawah
        if y1 > y2:
            cx1, cx2 = cx2, cx1
            y1, y2   = y2, y1

        dy = y2 - y1
        dx = cx2 - cx1
        if dy == 0:
            return

        # Pilih simbol berdasarkan arah diagonal
        going_right = dx > 0
        char = "\\" if going_right else "/"

        for step in range(1, dy):
            y  = y1 + step
            cx = cx1 + int(dx * step / dy)
            if 0 <= y < len(grid) and 0 <= cx < len(grid[y]):
                # Hanya timpa spasi atau tanda yang sama (bukan vertex / edge lain)
                if grid[y][cx] in (" ", char):
                    grid[y][cx] = char

    # ------------------------------------------------------------------ #
    #  Stub lama (dipanggil oleh kode lain jika ada) – aman untuk kosong  #
    # ------------------------------------------------------------------ #
    def draw_precise_edge(self, grid, positions, vertex_widths, v1, v2):
        """Stub – routing sekarang dilakukan langsung di format_ascii_graph"""
        pass
        
    def update_graph(self):
        """Update graph state (visualization removed)"""
        # Graph visualization telah dihapus, method ini bisa diperluas untuk kebutuhan lain
        pass


def main():
    app = QApplication(sys.argv)
    window = GraphApp()
    window.show()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()

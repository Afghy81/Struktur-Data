import sys
import math
from PyQt5.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, 
                             QHBoxLayout, QPushButton, QLabel, QLineEdit, 
                             QComboBox, QMessageBox, QTextEdit)
from PyQt5.QtCore import Qt, QTimer
from PyQt5.QtGui import QFont, QColor, QPen, QBrush
import pyqtgraph as pg
from pyqtgraph import PlotWidget
import numpy as np
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
        self.setGeometry(100, 100, 1500, 900)
        
        # Central widget
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        
        # Main layout (Horizontal - Menu left, Content right)
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
        
        # RIGHT PANEL - Graph visualization (top) + Output text (bottom)
        right_layout = QVBoxLayout()

        # --- Graph Visualization Panel ---
        graph_panel_label = QLabel("Visualisasi Graf (Adjacency Matrix):")
        graph_panel_label.setStyleSheet("font-weight: bold; font-size: 12px;")
        right_layout.addWidget(graph_panel_label)

        # pyqtgraph PlotWidget as the graph canvas
        pg.setConfigOptions(antialias=True)
        self.graph_plot = pg.PlotWidget()
        self.graph_plot.setBackground('#1e1e2e')
        self.graph_plot.setAspectLocked(True)
        self.graph_plot.hideAxis('bottom')
        self.graph_plot.hideAxis('left')
        self.graph_plot.setMinimumHeight(350)
        self.graph_plot.setMaximumHeight(420)
        self.graph_plot.getViewBox().setMouseEnabled(x=True, y=True)
        right_layout.addWidget(self.graph_plot)

        # --- Output Text Panel ---
        output_label = QLabel("Output:")
        output_label.setStyleSheet("font-weight: bold; font-size: 12px;")
        right_layout.addWidget(output_label)
        
        self.output_text = QTextEdit()
        self.output_text.setReadOnly(True)
        self.output_text.setStyleSheet(
            "background-color: #1e1e2e; color: #cdd6f4; "
            "font-family: Courier; font-size: 10px; border: 1px solid #45475a;"
        )
        right_layout.addWidget(self.output_text)
        
        # Create widgets for left and right panels
        left_widget = QWidget()
        left_widget.setLayout(control_layout)
        left_widget.setMaximumWidth(300)
        
        right_widget = QWidget()
        right_widget.setLayout(right_layout)
        
        # Add to main layout with proportions
        main_layout.addWidget(left_widget, 1)
        main_layout.addWidget(right_widget, 3)
        
        central_widget.setLayout(main_layout)
        
        # Track node/edge visual items for redraw
        self._graph_items = []
        
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
        self.draw_graph_panel()
        
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
        self.draw_graph_panel()
        
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
        self.draw_graph_panel()
        
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
        self.draw_graph_panel()
    
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
        self._dfs_last = True
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
        self._dfs_last = False
        self.highlight_traversal(result)
        
    def highlight_traversal(self, traversal_order):
        """Highlight nodes dalam urutan traversal dengan animasi step-by-step"""
        # Redraw dengan semua node abu-abu dulu
        self.draw_graph_panel(highlight_nodes=[], traversal_order=traversal_order)
        
        # Animasi step-by-step: highlight tiap node satu per satu
        self._traversal_step = 0
        self._traversal_order = traversal_order
        self._traversal_timer = QTimer()
        self._traversal_timer.timeout.connect(self._step_traversal_highlight)
        self._traversal_timer.start(500)  # 500ms per step

    def _step_traversal_highlight(self):
        """Timer callback untuk animasi traversal step-by-step"""
        if self._traversal_step >= len(self._traversal_order):
            self._traversal_timer.stop()
            return
        highlighted = self._traversal_order[:self._traversal_step + 1]
        self.draw_graph_panel(highlight_nodes=highlighted, traversal_order=self._traversal_order)
        self._traversal_step += 1

    def draw_graph_panel(self, highlight_nodes=None, traversal_order=None):
        """Gambar ulang graf pada pyqtgraph PlotWidget berdasarkan adjacency matrix saat ini."""
        self.graph_plot.clear()

        nodes = list(self.graph.nodes())
        if not nodes:
            # Tampilkan teks kosong
            text = pg.TextItem(text='Graph kosong.\nTambahkan vertex terlebih dahulu.',
                               color=(160, 160, 160), anchor=(0.5, 0.5))
            self.graph_plot.addItem(text)
            text.setPos(0, 0)
            return

        # Hitung posisi node melingkar (circular layout)
        n = len(nodes)
        radius = 1.0 if n > 1 else 0.0
        angles = [2 * math.pi * i / n - math.pi / 2 for i in range(n)]
        node_pos = {}
        for i, node in enumerate(nodes):
            x = radius * math.cos(angles[i])
            y = radius * math.sin(angles[i])
            node_pos[node] = (x, y)

        # Warna
        COLOR_BG        = (30, 30, 46)      # background
        COLOR_EDGE_DEF  = (100, 150, 255)   # edge default (biru terang)
        COLOR_EDGE_HL   = (255, 200, 60)    # edge highlight traversal (kuning)
        COLOR_NODE_DEF  = (69, 133, 255)    # node default
        COLOR_NODE_HL   = (250, 179, 135)   # node highlight (oranye)
        COLOR_NODE_START= (166, 227, 161)   # start node (hijau)
        COLOR_TEXT      = (205, 214, 244)   # label text
        COLOR_TEXT_HL   = (30, 30, 46)      # label text saat highlight

        highlight_set = set(highlight_nodes) if highlight_nodes else set()
        traversal_set = set(traversal_order) if traversal_order else set()

        # Set traversal edges (edge yang dilalui traversal)
        traversal_edges = set()
        if traversal_order and len(traversal_order) > 1:
            for i in range(len(traversal_order) - 1):
                a, b = traversal_order[i], traversal_order[i + 1]
                if self.graph.has_edge(a, b):
                    traversal_edges.add((a, b))
                    traversal_edges.add((b, a))

        # Gambar edges
        for u, v in self.graph.edges():
            x1, y1 = node_pos[u]
            x2, y2 = node_pos[v]
            is_traversal_edge = (u, v) in traversal_edges
            # Cek apakah kedua ujung edge sudah di-highlight
            both_highlighted = (u in highlight_set and v in highlight_set)
            if both_highlighted and is_traversal_edge:
                pen = pg.mkPen(color=COLOR_EDGE_HL, width=3)
            elif traversal_order and not (u in traversal_set and v in traversal_set):
                pen = pg.mkPen(color=(70, 70, 90), width=1.5, style=Qt.DashLine)
            else:
                pen = pg.mkPen(color=COLOR_EDGE_DEF, width=2)
            edge_item = self.graph_plot.plot([x1, x2], [y1, y2], pen=pen)

        # Gambar nodes
        node_size = max(18, 32 - n)
        for node in nodes:
            x, y = node_pos[node]
            is_start  = (traversal_order and node == traversal_order[0])
            is_hl     = node in highlight_set
            is_trail  = (traversal_order is not None) and (node in traversal_set) and not is_hl

            if is_hl and is_start:
                brush_color = COLOR_NODE_START
                pen_color   = (100, 200, 100)
            elif is_hl:
                brush_color = COLOR_NODE_HL
                pen_color   = (220, 140, 80)
            elif is_trail:
                brush_color = (60, 60, 80)
                pen_color   = (100, 100, 130)
            else:
                brush_color = COLOR_NODE_DEF
                pen_color   = (50, 80, 180)

            scatter = pg.ScatterPlotItem(
                [x], [y],
                size=node_size,
                pen=pg.mkPen(pen_color, width=2),
                brush=pg.mkBrush(*brush_color)
            )
            self.graph_plot.addItem(scatter)

            # Label node
            txt_color = COLOR_TEXT_HL if (is_hl or is_start) else COLOR_TEXT
            label = pg.TextItem(
                text=str(node),
                color=txt_color,
                anchor=(0.5, 0.5)
            )
            font = QFont('Arial', 9, QFont.Bold)
            label.setFont(font)
            label.setPos(x, y)
            self.graph_plot.addItem(label)

        # Judul panel
        title_text = 'Graf Adjacency Matrix'
        if traversal_order:
            algo = 'DFS' if hasattr(self, '_dfs_last') and self._dfs_last else 'BFS'
            title_text = f'Traversal {algo}: ' + ' → '.join(traversal_order[:len(highlight_nodes or [])+1])
        title_item = pg.TextItem(
            text=title_text,
            color=(166, 227, 161),
            anchor=(0.5, 1.0)
        )
        title_font = QFont('Arial', 10, QFont.Bold)
        title_item.setFont(title_font)
        # Posisikan judul di atas semua node
        title_item.setPos(0, radius + 0.22)
        self.graph_plot.addItem(title_item)

        # Auto-range
        padding = radius + 0.35
        self.graph_plot.setXRange(-padding, padding)
        self.graph_plot.setYRange(-padding, padding)
    
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

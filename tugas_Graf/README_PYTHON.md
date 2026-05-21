# Visualisasi Graf dengan PyQTGraph

Program untuk memvisualisasikan dan memanipulasi graf secara real-time menggunakan PyQTGraph dan PyQt5.

## Fitur-Fitur

1. **Tambah Vertex** - Menambahkan node/vertex baru ke dalam graf
2. **Hapus Vertex** - Menghapus vertex beserta semua edge-nya
3. **Tambah Edge** - Menambahkan koneksi (edge) antar vertex
4. **Hapus Edge** - Menghapus koneksi antar vertex
5. **Tampilkan Graph** - Visualisasi real-time graf dengan PyQTGraph (otomatis ditampilkan)
6. **Traversal DFS** - Depth-First Search dari vertex pilihan
7. **Traversal BFS** - Breadth-First Search dari vertex pilihan
8. **Keluar** - Menutup program

## Instalasi

### 1. Pastikan Python Terinstall
Pastikan Python versi 3.8 atau lebih tinggi sudah terinstall di komputer Anda.

### 2. Instalasi Dependency
Buka Command Prompt/PowerShell di folder project dan jalankan:

```bash
pip install -r requirements.txt
```

Atau instalasi manual:
```bash
pip install PyQt5 pyqtgraph networkx
```

## Cara Menjalankan

```bash
python graph_app.py
```

Aplikasi akan membuka jendela GUI dengan visualisasi graf di sebelah kiri dan panel kontrol di sebelah kanan.

## Panduan Penggunaan

### 1. Menambah Vertex
- Masukkan nama/label vertex di kolom input "Masukkan label vertex"
- Klik tombol "Tambah Vertex"
- Vertex akan langsung muncul di visualisasi graf sebagai lingkaran merah

### 2. Menghapus Vertex
- Pilih vertex yang ingin dihapus dari dropdown "Pilih vertex"
- Klik tombol "Hapus Vertex"
- Vertex dan semua edge-nya akan dihapus dari graf

### 3. Menambah Edge
- Pilih vertex asal dari dropdown "Dari vertex"
- Pilih vertex tujuan dari dropdown "Ke vertex"
- Klik tombol "Tambah Edge"
- Garis cyan akan menghubungkan kedua vertex

### 4. Menghapus Edge
- Pilih vertex asal dari dropdown "Dari vertex"
- Pilih vertex tujuan dari dropdown "Ke vertex"
- Klik tombol "Hapus Edge (pilih dari/ke)"
- Garis penghubung akan dihapus

### 5. Traversal DFS (Depth-First Search)
- Pilih vertex awal dari dropdown "Mulai dari vertex"
- Klik tombol "Traversal DFS"
- Hasil traversal akan ditampilkan di bagian "Output" dalam format: Vertex1 → Vertex2 → Vertex3...

### 6. Traversal BFS (Breadth-First Search)
- Pilih vertex awal dari dropdown "Mulai dari vertex"
- Klik tombol "Traversal BFS"
- Hasil traversal akan ditampilkan di bagian "Output" dalam format: Vertex1 → Vertex2 → Vertex3...

## Tampilan Aplikasi

**Sebelah Kiri:** Area visualisasi graf dengan:
- Node berwarna merah dengan border gelap
- Garis cyan menghubungkan antar node
- Label vertex ditampilkan di tengah setiap node

**Sebelah Kanan:** Panel kontrol dengan:
- Input field dan tombol untuk setiap operasi
- Dropdown untuk memilih vertex
- Area output untuk menampilkan hasil traversal

## Jenis Graf

- **Tipe:** Undirected Graph (graf tak berarah)
- Setiap edge menghubungkan dua vertex secara dua arah
- Algoritma layout: Spring Layout (penempatan node otomatis)

## Fitur Visualisasi

✅ Visualisasi real-time saat menambah/menghapus vertex dan edge  
✅ Penempatan node otomatis menggunakan spring algorithm  
✅ Warna yang jelas untuk membedakan node dan edge  
✅ Label vertex yang mudah dibaca  
✅ Auto-zoom untuk menampilkan seluruh graf  

## Troubleshooting

### Error: "ModuleNotFoundError: No module named 'PyQt5'"
Jalankan: `pip install PyQt5`

### Error: "ModuleNotFoundError: No module named 'pyqtgraph'"
Jalankan: `pip install pyqtgraph`

### Error: "ModuleNotFoundError: No module named 'networkx'"
Jalankan: `pip install networkx`

### Aplikasi lambat dengan banyak vertex
PyQTGraph sudah dioptimalkan untuk performa tinggi. Aplikasi dapat menangani ratusan vertex dengan smooth. Untuk performa maksimal, hindari membuat edge terlalu banyak sekaligus.

## Struktur File

- **graph_app.py** - File utama aplikasi dengan GUI PyQt5
- **requirements.txt** - File berisi daftar dependency Python
- **README_PYTHON.md** - Panduan penggunaan ini

## Library yang Digunakan

- **PyQt5** - Framework untuk membuat GUI
- **PyQTGraph** - Library visualisasi grafik high-performance
- **NetworkX** - Library untuk manipulasi dan analisis struktur graf

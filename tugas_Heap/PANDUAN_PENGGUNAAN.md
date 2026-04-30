# PANDUAN MENJALANKAN PROGRAM MIN-HEAP

## 📋 Daftar File

```
src/
├── App.java          - Program menu interaktif (PROGRAM UTAMA)
├── MinHeap.java      - Implementasi kelas Min-Heap
└── TestMinHeap.java  - Contoh penggunaan otomatis (untuk testing)

bin/
├── App.class         - File bytecode App
├── MinHeap.class     - File bytecode MinHeap
└── TestMinHeap.class - File bytecode TestMinHeap
```

## 🚀 Cara Menjalankan

### Step 1: Compile Semua File

```bash
cd tugas_Heap
javac -d bin src/*.java
```

### Step 2A: Jalankan Program Interaktif (Utama)

```bash
java -cp bin App
```

Tampilan:

```
╔════════════════════════════════════════════════════════════╗
║       PROGRAM MIN-HEAP - STRUKTUR DATA                     ║
║  Operasi: Insert, Delete, Peek, Heapify, Visualisasi      ║
╚════════════════════════════════════════════════════════════╝

╔════════════════════════════════╗
║          MENU UTAMA            ║
╠════════════════════════════════╣
║ 1. Insert Elemen               ║
║ 2. Delete Elemen Spesifik      ║
║ 3. Delete Elemen Minimum       ║
║ 4. Peek (Lihat Terkecil)       ║
║ 5. Build Heap dari Array       ║
║ 6. Tampilkan Heap              ║
║ 7. Visualisasi Struktur Heap   ║
║ 0. Keluar                      ║
╚════════════════════════════════╝
```

### Step 2B: Jalankan Program Testing (Optional)

```bash
java -cp bin TestMinHeap
```

Menjalankan 6 test case untuk mendemonstrasikan semua fitur:

- Test 1: Insert Multiple Elements
- Test 2: Delete Specific Element
- Test 3: Delete Minimum
- Test 4: Peek Operation
- Test 5: Build from Array
- Test 6: Complex Operations

## 📝 Contoh Penggunaan Program Interaktif

### Contoh 1: Insert Elemen

```
Pilih operasi: 1
Masukkan nilai: 10
✓ Elemen 10 berhasil diinsert
Heap: 10

Pilih operasi: 1
Masukkan nilai: 5
✓ Elemen 5 berhasil diinsert
Heap: 5 10

Pilih operasi: 1
Masukkan nilai: 15
✓ Elemen 15 berhasil diinsert
Heap: 5 10 15
```

### Contoh 2: Peek Elemen

```
Pilih operasi: 4
✓ Elemen terkecil (root) di heap: 5
```

### Contoh 3: Delete Minimum

```
Pilih operasi: 3
✓ Elemen minimum 5 berhasil dihapus
Heap: 10 15
```

### Contoh 4: Visualisasi

```
Pilih operasi: 7

=== Visualisasi Struktur Heap ===
├── 1
│   ├── 4
│   │   ├── 10
│   │   └── 5
│   └── 2
│       ├── 3
```

### Contoh 5: Build Dari Array

```
Pilih operasi: 5
Masukkan jumlah elemen: 6
Masukkan elemen ke-1: 2
Masukkan elemen ke-2: 3
Masukkan elemen ke-3: 10
Masukkan elemen ke-4: 4
Masukkan elemen ke-5: 5
Masukkan elemen ke-6: 1

Array original: [ 2 3 10 4 5 1 ]
✓ Heap berhasil dibangun dari array
Heap: 1 3 2 4 5 10
```

## 🎯 Operasi yang Tersedia

| Menu | Operasi         | Kompleksitas | Kegunaan              |
| ---- | --------------- | ------------ | --------------------- |
| 1    | Insert Elemen   | O(log n)     | Tambah elemen baru    |
| 2    | Delete Spesifik | O(n)         | Hapus elemen tertentu |
| 3    | Delete Minimum  | O(log n)     | Hapus elemen terkecil |
| 4    | Peek            | O(1)         | Lihat elemen terkecil |
| 5    | Build Heap      | O(n)         | Buat heap dari array  |
| 6    | Tampilkan       | O(n)         | Lihat isi heap        |
| 7    | Visualisasi     | O(n)         | Lihat struktur tree   |

## ✨ Fitur Spesial

- ✅ Input validation untuk mencegah error
- ✅ Menu interaktif yang user-friendly
- ✅ Visualisasi tree structure dengan ASCII art
- ✅ Support untuk berbagai operasi heap
- ✅ Pesan error yang jelas dan helpful
- ✅ Kompleksitas waktu optimal

## 🐛 Troubleshooting

### Error: "cannot find symbol: class MinHeap"

**Solusi**: Pastikan compile semua file di src/

```bash
javac -d bin src/*.java
```

### Program lambat saat delete

**Info**: Delete operasi pencarian O(n) karena harus cari elemen dulu.
Gunakan "Delete Minimum" jika hanya ingin hapus elemen terkecil.

### Heap terlihat aneh di visualisasi

**Info**: Heap adalah complete binary tree, struktur mungkin tidak perfectly balanced.
Ini adalah sifat normal dari min-heap.

## 📚 Referensi Kode

Struktur Min-Heap di array:

```
Index:     0   1   2   3   4   5
Value:     1   4   2  10   3   5

Parent dari index i: (i-1)/2
Left child dari index i: 2i+1
Right child dari index i: 2i+2

Tree Structure:
       1
      / \
     4   2
    / \ /
   10 3 5
```

## ℹ️ Informasi Program

- **Bahasa**: Java
- **Tipe Data**: Integer
- **Memory**: O(n)
- **Supported Operasi**: 7 operasi utama
- **Status**: ✓ Fully Functional

---

**Selamat menggunakan program Min-Heap!** 🎉
Untuk pertanyaan atau masalah, silakan cek README.md untuk dokumentasi lengkap.

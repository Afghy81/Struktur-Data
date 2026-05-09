# Program Min-Heap - Struktur Data

## Deskripsi Program

Program ini mengimplementasikan struktur data **Min-Heap** dengan fitur lengkap meliputi:

- **Insert**: Menambahkan elemen ke heap dengan heapify-up ⬆️
- **Delete**: Menghapus elemen spesifik dari heap dengan heapify-down ⬇️
- **Peek**: Melihat elemen terkecil (root) tanpa menghapus 👀
- **Heapify**: Membangun heap dari array dengan efisien 🏗️
- **Visualisasi**: Menampilkan struktur tree secara visual 🌳

## Folder Structure

```
tugas_Heap/
├── src/
│   ├── App.java       (Menu interaktif & controller)
│   └── MinHeap.java   (Implementasi Min-Heap)
├── bin/               (File .class hasil kompilasi)
└── README.md          (Dokumentasi)
```

## Kompleksitas Waktu Operasi

| Operasi    | Kompleksitas | Keterangan                         |
| ---------- | ------------ | ---------------------------------- |
| Insert     | O(log n)     | Heapify-up dari leaf ke root       |
| Delete     | O(log n)     | Heapify-down setelah remove        |
| Delete Min | O(log n)     | Delete elemen root                 |
| Peek       | O(1)         | Akses langsung ke root             |
| Heapify    | O(n)         | Build heap dari array              |
| Search     | O(n)         | Linear search untuk mencari elemen |

## Cara Menggunakan

### 1. Kompilasi Program

```bash
cd tugas_Heap
javac -d bin src/*.java
```

### 2. Menjalankan Program

```bash
java -cp bin App
```

### 3. Menu Operasi

```
┌─────────────────────────────────────┐
│       MENU UTAMA MIN-HEAP           │
├─────────────────────────────────────┤
│ 1. Insert Elemen                    │
│ 2. Delete Elemen Spesifik           │
│ 3. Delete Elemen Minimum (Root)     │
│ 4. Peek (Lihat Elemen Terkecil)     │
│ 5. Build Heap dari Array            │
│ 6. Tampilkan Heap                   │
│ 7. Visualisasi Struktur Heap        │
│ 0. Keluar                           │
└─────────────────────────────────────┘
```

## Penjelasan Setiap Menu

### 1️⃣ Insert Elemen

Menambahkan satu elemen ke heap dengan menjaga sifat min-heap

- Elemen ditambahkan di posisi terakhir
- Dilakukan heapify-up untuk menjaga properti heap
- Kompleksitas: O(log n)

### 2️⃣ Delete Elemen Spesifik

Mencari dan menghapus elemen tertentu dari heap

- Mencari elemen dengan linear search O(n)
- Mengganti dengan elemen terakhir
- Melakukan heapify-down jika diperlukan
- Kompleksitas total: O(n)

### 3️⃣ Delete Elemen Minimum (Root)

Menghapus elemen terkecil (elemen di root) secara efisien

- Mengambil nilai root
- Mengganti dengan elemen terakhir
- Heapify-down dari root
- Kompleksitas: O(log n)

### 4️⃣ Peek (Lihat Elemen Terkecil)

Menampilkan elemen terkecil tanpa menghapus

- Akses langsung ke index 0 (root)
- Tidak merubah struktur heap
- Kompleksitas: O(1)

### 5️⃣ Build Heap dari Array

Membangun heap baru dari array input

- User memasukkan jumlah elemen dan nilainya
- Heap dibangun dengan algoritma heapify yang efisien
- Kompleksitas: O(n)

### 6️⃣ Tampilkan Heap

Menampilkan semua elemen dalam format linear

- Menampilkan elemen satu baris
- Juga menampilkan ukuran heap
- Format: Heap: [elem1] [elem2] [elem3] ...

### 7️⃣ Visualisasi Struktur Heap

Menampilkan struktur tree secara visual

- Menampilkan tree structure dengan ASCII art
- Mudah memahami parent-child relationships
- Berguna untuk debugging

### 0️⃣ Keluar

Mengakhiri program dengan graceful

## Contoh Penggunaan

### Contoh 1: Insert Multiple Elements

```
Insert: 10, 3, 2, 4, 5, 1

Hasil Heap: 1 4 2 10 3 5

Visualisasi:
    1
   / \
  4   2
 / \ /
10 3 5
```

### Contoh 2: Build from Array

```
Input Array: [2, 3, 10, 4, 5, 1]
Hasil Heap: 1 4 2 3 5 10

Visualisasi:
    1
   / \
  4   2
 / \ / \
3  5 10
```

### Contoh 3: Delete & Peek Operations

```
Peek: 1 (elemen terkecil)
Delete Min: 1 dihapus
Heap baru: 2 4 5 3 10
```

## Fitur Khusus Program

### ✨ Heapify-Up (Insert Operation)

```
Proses:
1. Tambah elemen di akhir
2. Bandingkan dengan parent: (i-1)/2
3. Jika elemen < parent, swap
4. Pindah ke parent, ulangi
5. Berhenti saat kondisi heap terpenuhi
```

### ✨ Heapify-Down (Delete Operation)

```
Proses:
1. Ambil posisi untuk di-heapify
2. Bandingkan dengan left child (2i+1) dan right child (2i+2)
3. Tentukan anak terkecil
4. Jika parent > anak terkecil, swap
5. Pindah ke anak, ulangi
6. Berhenti saat kondisi heap terpenuhi
```

### ✨ Build Heap (O(n) Algorithm)

```
Proses:
1. Copy array ke heap
2. Mulai dari node non-leaf terakhir: n/2 - 1
3. Lakukan heapify-down untuk setiap node
4. Iterasi mundur ke root
5. Hasil: Valid min-heap dengan O(n) kompleksitas
```

### ✨ Visualisasi Tree Structure

```
Menampilkan:
├── Parent Node
│   ├── Left Child
│   └── Right Child
│       ├── Grandchild
│       └── Grandchild
└── ...
```

### ✨ Input Validation

- Validasi input untuk memastikan hanya angka yang diterima
- Pesan error yang jelas jika terjadi kesalahan input
- Retry otomatis jika input tidak valid

## Sifat Min-Heap Yang Dipertahankan

Min-Heap harus memenuhi tiga kondisi:

### 1. Min-Heap Property

```
Setiap parent ≤ kedua child-nya
Parent[i] ≤ Child[2i+1] dan Child[2i+2]
```

### 2. Complete Binary Tree

```
Semua level penuh kecuali level terakhir
Level terakhir left-aligned (tidak boleh ada gap)
```

### 3. Array Representation

```
Index: 0  1  2  3  4  5  6
Value: 1  4  2  10 3  5  8

Parent dari index i: (i-1)/2
Left child dari index i: 2i+1
Right child dari index i: 2i+2
```

## Implementasi Detail

### MinHeap.java

File utama yang berisi semua logika min-heap:

- `insert(value)` - Tambah elemen
- `delete(value)` - Hapus elemen spesifik
- `deleteMin()` - Hapus elemen minimum
- `peek()` - Lihat elemen terkecil
- `buildMinHeap(arr)` - Bangun dari array
- `heapifyDown(index)` - Helper untuk bubbling down
- `visualize()` - Tampilkan struktur tree

### App.java

File controller/menu yang berisi:

- `main()` - Entry point dan loop menu
- `displayMenu()` - Tampilkan menu utama
- `insertElement()` - Handle insert operation
- `deleteElement()` - Handle delete specific element
- `deleteMinElement()` - Handle delete minimum
- `peekElement()` - Handle peek operation
- `buildHeapFromArray()` - Handle build from array
- `visualizeHeap()` - Handle visualization
- `displayHeap()` - Handle display operation

## Testing

Program telah ditest dengan berbagai skenario:

✅ Insert multiple elements  
✅ Delete specific element  
✅ Delete minimum element  
✅ Peek operation  
✅ Build from array  
✅ Visualize tree structure  
✅ Empty heap handling  
✅ Input validation

## Catatan Pengembangan

### Efisiensi

- Menggunakan ArrayList untuk dynamic array
- Operasi dasar menggunakan O(1) array access
- Heapify-up/down optimal dengan O(log n)

### Adaptivitas

- Support berbagai ukuran heap
- Dynamically grow/shrink sesuai kebutuhan
- Input validation yang robust

### Maintainability

- Kode well-documented dengan javadoc
- Mudah dipahami dan dimodifikasi
- Helper methods terpisah untuk logic clarity

## Potential Improvements

1. Generic implementation untuk tipe data apapun
2. Priority queue implementation
3. Bulk operations (insertAll, deleteAll)
4. Heap sort implementation
5. Min-Max Heap variant

## Author

Program Struktur Data - Min-Heap  
Tugas Perkuliahan Semester 4

## Version

v1.0 - Release Awal

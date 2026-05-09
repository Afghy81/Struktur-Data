# PROGRAM HEAP DATA - MIN-HEAP & MAX-HEAP YANG TERINTEGRASI

## 📋 Deskripsi Program

Program ini menggabungkan **Min-Heap** dan **Max-Heap** untuk mengelola data dengan struktur berikut:

- **ID**: Identitas unik (integer)
- **Nama**: Nama barang/item (string)

Total **100 data entries** yang dimuat dari file Excel langsung ke dalam program.

## 📁 File-File Program

```
src/
├── DataEntry.java          - Class untuk merepresentasikan satu entry data (id, nama)
├── MinHeapData.java        - Implementasi Min-Heap dengan DataEntry
├── MaxHeapData.java        - Implementasi Max-Heap dengan DataEntry
└── heapPROGRAM.java        - Program utama dengan menu interaktif
```

## 🏗️ Struktur Kelas

### 1. DataEntry.java

```java
public class DataEntry implements Comparable<DataEntry> {
    private int id;
    private String nama;

    // Constructor, getters, compareTo, toString, dll
}
```

- **compareTo()**: Untuk Min-Heap (ascending by id)
- **reverseCompareTo()**: Untuk Max-Heap (descending by id)

### 2. MinHeapData.java

```java
public class MinHeapData {
    private ArrayList<DataEntry> heap;

    // Methods:
    - insert(DataEntry)      // O(log n)
    - deleteMin()             // O(log n)
    - delete(int id)          // O(n)
    - peek()                  // O(1)
    - getAllSorted()          // O(n log n) - untuk display
    - display()               // Display semua data ascending
}
```

### 3. MaxHeapData.java

```java
public class MaxHeapData {
    private ArrayList<DataEntry> heap;

    // Methods (sama seperti MinHeapData):
    - insert(DataEntry)      // O(log n)
    - deleteMax()             // O(log n)
    - delete(int id)          // O(n)
    - peek()                  // O(1)
    - getAllSorted()          // O(n log n) - untuk display
    - display()               // Display semua data descending
}
```

### 4. heapPROGRAM.java

Program utama dengan menu interaktif dan pemuat data otomatis.

## 🚀 Cara Menjalankan

### Step 1: Compile Semua File

```bash
cd tugas_Heap
javac -d bin src/DataEntry.java src/MinHeapData.java src/MaxHeapData.java src/heapPROGRAM.java
```

### Step 2: Jalankan Program

```bash
java -cp bin heapPROGRAM
```

## 📊 Menu Operasi

```
╔════════════════════════════════════════════╗
║              MENU UTAMA                    ║
╠════════════════════════════════════════════╣
║ 1. Tambah Data Baru (ke Min & Max Heap)    ║
║ 2. Tampilkan Data (Min-Heap - Ascending)   ║
║ 3. Tampilkan Data (Max-Heap - Descending)  ║
║ 4. Hapus Data dari Min-Heap                ║
║ 5. Hapus Data dari Max-Heap                ║
║ 0. Keluar                                  ║
╚════════════════════════════════════════════╝
```

## 📝 Fitur Program

### 1️⃣ Tambah Data Baru

- Menambahkan data baru ke kedua heap sekaligus
- Validasi untuk memastikan ID unik
- Kompleksitas: O(log n) untuk setiap heap

**Contoh:**

```
Pilih operasi: 1
Masukkan ID: 10000
Masukkan Nama: contoh barang
✓ Data berhasil ditambahkan ke kedua heap!
  - Min-Heap: 101 entries
  - Max-Heap: 101 entries
```

### 2️⃣ Tampilkan Data Min-Heap (Ascending)

- Menampilkan semua data urut berdasarkan ID dari kecil ke besar
- Kompleksitas: O(n log n)
- Format: Tabel dengan ID dan NAMA

**Contoh Output:**

```
┌─────┬─────────────────────────────┐
│ ID  │ NAMA                        │
├─────┼─────────────────────────────┤
│ 1070  | mouse                       │
│ 1138  | kaca                        │
│ 1302  | pisang                      │
│ ...  | ...                         │
└─────┴─────────────────────────────┘
```

### 3️⃣ Tampilkan Data Max-Heap (Descending)

- Menampilkan semua data urut berdasarkan ID dari besar ke kecil
- Kompleksitas: O(n log n)
- Format: Tabel dengan ID dan NAMA (urutan terbalik)

**Contoh Output:**

```
┌─────┬─────────────────────────────┐
│ ID  │ NAMA                        │
├─────┼─────────────────────────────┤
│ 9971  | motor                       │
│ 9888  | gitar                       │
│ 9817  | galon                       │
│ ...  | ...                         │
└─────┴─────────────────────────────┘
```

### 4️⃣ Hapus Data dari Min-Heap

- Menghapus data spesifik dari Min-Heap
- Otomatis dihapus juga dari Max-Heap untuk konsistensi
- Kompleksitas: O(n)

### 5️⃣ Hapus Data dari Max-Heap

- Menghapus data spesifik dari Max-Heap
- Otomatis dihapus juga dari Min-Heap untuk konsistensi
- Kompleksitas: O(n)

## 📊 Data Awal (100 Entries)

Program dimuat dengan 100 data dari file Excel:

| ID        | Nama            | ID   | Nama    | ID   | Nama   |
| --------- | --------------- | ---- | ------- | ---- | ------ |
| 5288      | pensil          | 4457 | kertas  | 3195 | cabai  |
| 5993      | pulpen          | 8938 | cat     | 5741 | wortel |
| 8689      | penghapus       | 2618 | stabilo | 6852 | timun  |
| 8043      | buku            | 9033 | mobil   | 8147 | apel   |
| 8699      | sampul          | 9971 | motor   | 8902 | jeruk  |
| ...       | ...             | ...  | ...     | ...  | ...    |
| **Total** | **100 entries** |      |         |      |        |

## 🔧 Implementasi Detail

### Min-Heap Properties

- Parent ≤ Left Child dan Right Child
- Digunakan untuk menampilkan data ascending
- Root adalah elemen dengan ID terkecil

### Max-Heap Properties

- Parent ≥ Left Child dan Right Child
- Digunakan untuk menampilkan data descending
- Root adalah elemen dengan ID terbesar

### Array Indexing

```
Index:     0   1   2   3   4   5
           R   L1  R1  L2  R2  L3

Parent dari index i: (i-1)/2
Left child dari index i: 2i+1
Right child dari index i: 2i+2
```

## ⏱️ Kompleksitas Waktu

| Operasi          | Min-Heap   | Max-Heap   | Keterangan              |
| ---------------- | ---------- | ---------- | ----------------------- |
| Insert           | O(log n)   | O(log n)   | Heapify-up              |
| Delete Min/Max   | O(log n)   | O(log n)   | Heapify-down            |
| Delete (by id)   | O(n)       | O(n)       | Linear search + heapify |
| Peek             | O(1)       | O(1)       | Akses root              |
| Display (sorted) | O(n log n) | O(n log n) | Extract all             |

## 💾 Ruang Penyimpanan

- **Memory per entry**: ~20-30 bytes (int id + String nama + overhead)
- **Total memory untuk 100 entries**: ~2-3 KB
- **Complexity ruang**: O(n)

## 🎯 Contoh Skenario Penggunaan

### Skenario 1: Lihat Data Urut Ascending (Min-Heap)

```
Pilih: 2
Output: Menampilkan semua 100 data dari ID terkecil ke terbesar
Min-Heap Root: 1070 (mouse)
```

### Skenario 2: Lihat Data Urut Descending (Max-Heap)

```
Pilih: 3
Output: Menampilkan semua 100 data dari ID terbesar ke terkecil
Max-Heap Root: 9971 (motor)
```

### Skenario 3: Tambah Data Baru

```
Pilih: 1
Input ID: 5000
Input Nama: produk baru
Output: Data ditambahkan ke kedua heap
Total sekarang: 101 entries
```

### Skenario 4: Hapus Data

```
Pilih: 4
Input ID: 5288
Output: Data dengan ID 5288 (pensil) dihapus dari kedua heap
Total sekarang: 99 entries
```

## ⚙️ Algoritma Kunci

### Insert Operation

```
1. Tambahkan entry di akhir array
2. Tentukan index (size - 1)
3. Sementara index > 0:
   a. Hitung parent index: (index-1)/2
   b. Jika entry < parent (min-heap) atau entry > parent (max-heap):
      - Swap entry dengan parent
      - index = parent index
   c. Else: break
```

### Delete Min/Max Operation

```
1. Simpan root value
2. Ganti root dengan last element
3. Hapus last element dari array
4. Heapify-down dari root (0)
5. Return simpanan value
```

### Heapify-Down (Min-Heap)

```
1. smallest = index
2. left_child = 2 * index + 1
3. right_child = 2 * index + 2
4. Jika left_child < smallest: smallest = left_child
5. Jika right_child < smallest: smallest = right_child
6. Jika smallest != index:
   - Swap
   - Rekursi dengan smallest
```

## 🔍 Tips & Tricks

1. **Konsistensi**: Data selalu sinkron antara Min-Heap dan Max-Heap
2. **Validasi**: ID harus unik dalam system
3. **Performance**: Untuk dataset besar, insert/delete still O(log n)
4. **Display**: Kompleksitas display O(n log n) karena perlu extract semua elemen

## 📌 Catatan Penting

- **Synchronization**: Ketika delete dari satu heap, otomatis delete dari heap lain
- **Stability**: Sorting stabil melalui ID
- **Thread Safety**: Belum thread-safe (untuk production perlu synchronization)

## 🚀 Future Enhancements

1. Implementasi generics untuk tipe data lain
2. Thread-safe implementation
3. Persistence ke database
4. Advanced filtering dan searching
5. Batch operations
6. Undo/Redo functionality

---

**Status**: ✓ Fully Functional
**Versi**: 1.0
**Total Data**: 100 entries
**Terakhir Update**: 2026

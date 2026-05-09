# PANDUAN CEPAT - heapPROGRAM

## 🎯 Quick Start

### Compile & Run

```bash
javac -d bin src/DataEntry.java src/MinHeapData.java src/MaxHeapData.java src/heapPROGRAM.java
java -cp bin heapPROGRAM
```

## 📋 File Struktur

| File               | Fungsi                       | Baris |
| ------------------ | ---------------------------- | ----- |
| `DataEntry.java`   | Class model data (id + nama) | ~60   |
| `MinHeapData.java` | Min-Heap implementation      | ~150  |
| `MaxHeapData.java` | Max-Heap implementation      | ~150  |
| `heapPROGRAM.java` | Main program + Menu          | ~300  |

## 🎮 Menu Operations

### Menu 1: Add New Data

```
Pilih: 1
ID: [masukkan integer]
Nama: [masukkan string]
→ Data ditambahkan ke Min-Heap & Max-Heap
```

### Menu 2: Show Min-Heap (Ascending)

```
Pilih: 2
→ Tampil semua 100 data urut ascending by ID
Tabel format: ID | NAMA
```

### Menu 3: Show Max-Heap (Descending)

```
Pilih: 3
→ Tampil semua 100 data urut descending by ID
Tabel format: ID | NAMA
```

### Menu 4: Delete from Min-Heap

```
Pilih: 4
ID: [masukkan integer]
→ Data dihapus dari Min-Heap & Max-Heap
```

### Menu 5: Delete from Max-Heap

```
Pilih: 5
ID: [masukkan integer]
→ Data dihapus dari Max-Heap & Min-Heap
```

### Menu 0: Exit

```
Pilih: 0
→ Program selesai
```

## 📊 Data Sample (dari Excel)

```
ID    | Nama
------|------------------
1070  | mouse
1138  | kaca
1302  | pisang
...   | ...
9971  | motor
```

Total: 100 entries

## ⏱️ Performance

| Operation          | Complexity | Est. Time (100 items) |
| ------------------ | ---------- | --------------------- |
| Add Data           | O(log n)   | ~0.001s               |
| Display Ascending  | O(n log n) | ~0.01s                |
| Display Descending | O(n log n) | ~0.01s                |
| Delete             | O(n)       | ~0.005s               |

## 🔑 Key Features

✅ Auto-load 100 data saat startup  
✅ Min-Heap untuk sorting ascending  
✅ Max-Heap untuk sorting descending  
✅ Add/Delete operasi sinkron di kedua heap  
✅ User-friendly menu interface  
✅ Input validation

## 💡 Example Usage

### Use Case 1: Lihat data terkecil

```
Program start → Menu 2 (Min-Heap)
Output: ID 1070 (mouse) di posisi pertama
```

### Use Case 2: Lihat data terbesar

```
Program start → Menu 3 (Max-Heap)
Output: ID 9971 (motor) di posisi pertama
```

### Use Case 3: Hapus dan tambah data

```
Menu 4 → Hapus ID 5288 (pensil)
Menu 1 → Tambah ID 10000 (produk baru)
Menu 2 → Lihat data baru dengan 99 old + 1 new = 100 total
```

## 🐛 Troubleshooting

### Error: "Cannot find symbol: class DataEntry"

**Solution**: Compile semua 4 file (DataEntry, MinHeapData, MaxHeapData, heapPROGRAM)

### Program lambat saat display

**Info**: Normal untuk 100 items (O(n log n) complexity)

### Data tidak match di Min & Max Heap

**Info**: Harusnya selalu match karena add/delete sinkron

## 📁 Input Examples

### Add Data Input

```
1
5555
barang test
```

### Delete Input

```
4
5288
```

### Display Input

```
2
(atau 3 untuk max-heap)
```

## 🎓 Learning Outcomes

Setelah menggunakan program ini, Anda akan memahami:

- Perbedaan Min-Heap vs Max-Heap
- Operasi insert/delete dalam O(log n)
- Heapify-up dan heapify-down
- Real-world heap applications
- Data structure synchronization

---

**Version**: 1.0  
**Status**: Production Ready ✓  
**Data Size**: 100 entries  
**Memory**: ~2-3 KB

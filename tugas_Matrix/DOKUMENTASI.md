# Program Struktur Data Matrix

## Deskripsi

Program interaktif untuk operasi-operasi dasar pada struktur data matrix dengan menu yang user-friendly.

---

## Fitur & Menu Utama

### Menu Utama (3 Pilihan)

1. **Input/Buat Matrix** - Membuat dan input elemen matrix baru
2. **Lihat Matrix** - Menampilkan matrix yang telah dibuat
3. **Operasi Matrix** - Mengakses berbagai operasi pada matrix
4. **Keluar** - Keluar dari program

---

## Menu Operasi Matrix

### 1. SORT MATRIX

- **1a. Sort Row-wise** - Mengurutkan setiap baris dari kecil ke besar
- **1b. Sort Column-wise** - Mengurutkan setiap kolom dari atas ke bawah

### 2. ROTATE MATRIX (Hanya untuk matrix persegi)

- **2a. Rotate Clockwise by 1** - Rotasi searah jarum jam 1 tingkat
- **2b. Rotate Counter-Clockwise by 1** - Rotasi berlawanan arah jarum jam 1 tingkat
- **2c. Rotate by 90 Degrees** - Rotasi sebesar 90°
- **2d. Rotate by 180 Degrees** - Rotasi sebesar 180°

### 3. TRAVERSAL MATRIX

- **3a. Row-wise Traversal** - Menampilkan elemen baris per baris
- **3b. Column-wise Traversal** - Menampilkan elemen kolom per kolom

### 4. SPIRAL FORM

- **Spiral Traversal** - Menampilkan elemen matrix dalam bentuk spiral (luar ke dalam)

### 5. TRANSPOSE MATRIX

- **Transpose** - Mengubah posisi baris menjadi kolom dan sebaliknya

---

## Contoh Penggunaan

### Contoh Input

```
Dimensi: 3 x 3
Matrix:
  2  8  1
  5  3  9
  7  4  6
```

### Contoh Operasi

**Sort Row-wise:**

```
  1  2  8
  3  5  9
  4  6  7
```

**Rotate Clockwise 90°:**

```
  7  5  2
  4  3  8
  6  9  1
```

**Spiral Form:**
2 → 8 → 1 → 9 → 6 → 4 → 7 → 5 → 3

**Transpose:**

```
  2  5  7
  8  3  4
  1  9  6
```

---

## Struktur File

```
tugas_Matrix/
├── src/
│   ├── App.java          (Main program dengan menu)
│   ├── Matrix.java       (Class untuk operasi matrix)
│   ├── App.class         (Compiled binary)
│   └── Matrix.class      (Compiled binary)
├── bin/                  (Output binary folder)
├── lib/                  (Library folder)
└── README.md
```

---

## Cara Menjalankan

### Kompilasi

```bash
cd src
javac App.java Matrix.java
```

### Jalankan

```bash
java App
```

---

## Catatan Penting

1. **Matrix Persegi untuk Rotasi** - Operasi rotasi hanya bisa dilakukan pada matrix persegi (n×n)
2. **Transpose Mengubah Dimensi** - Setelah transpose, dimensi matrix akan berubah (m×n menjadi n×m)
3. **Input Validation** - Program memvalidasi input dan menampilkan pesan error jika perlu
4. **Format Output** - Setiap output matrix ditampilkan dengan format tabel yang rapi

---

## Author

Program Struktur Data - Semester 4

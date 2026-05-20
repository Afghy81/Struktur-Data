# Program Hash Table

Program ini menyimpan himpunan data numerik menggunakan hash table dengan teknik collision separate chaining. Setiap bucket pada hash table berisi linked list, sehingga beberapa angka yang masuk ke index yang sama tetap dapat disimpan.

## Fitur Program

1. INPUT DATA
2. HAPUS DATA
3. CARI DATA
4. TAMPILKAN HASH TABLE

Saat program dijalankan, sudah ada 100 angka unik yang dimasukkan dari array `INITIAL_KEYS` pada file `src/hash_table.java`.

## Struktur Hash Table

Program menggunakan:

```java
private int bucketCount;
private List<List<Integer>> table;
```

`bucketCount` adalah jumlah bucket pada hash table. Pada program ini nilainya `101`.

`table` adalah list yang berisi banyak bucket. Setiap bucket berisi `LinkedList<Integer>`. Bentuk sederhananya seperti ini:

```text
index 0  --> linked list
index 1  --> linked list
index 2  --> linked list
...
index 100 --> linked list
```

## Rumus Hash

Index tempat data disimpan dihitung dengan fungsi:

```java
index = Math.abs(key) % bucketCount;
```

Karena `bucketCount = 101`, maka rumusnya menjadi:

```text
index = angka % 101
```

Contoh:

```text
42 % 101 = 42
143 % 101 = 42
850 % 101 = 42
```

Ketiga angka tersebut menghasilkan index yang sama, yaitu `42`.

## Apa yang Terjadi Saat Collision?

Collision terjadi ketika dua atau lebih angka menghasilkan index hash table yang sama.

Yang sama bukan angka aslinya, tetapi hasil modulo dari angka tersebut.

Contoh dari data awal program:

```text
42  % 101 = 42
143 % 101 = 42
850 % 101 = 42
```

Walaupun angka `42`, `143`, dan `850` berbeda, ketiganya masuk ke index `42` karena hasil modulo terhadap `101` sama.

Maka isi bucket index `42` menjadi:

```text
42 --> 143 --> 850
```

Inilah alasan program tidak mencari bucket kosong lain. Pada separate chaining, jika index hasil hash sudah berisi data, data baru tetap dimasukkan ke index tersebut, tetapi disambungkan ke linked list pada bucket yang sama.

## Proses Insert Data

Misalnya program memasukkan angka `143`.

1. Hitung index:

```text
143 % 101 = 42
```

2. Program melihat bucket index `42`.
3. Jika bucket kosong, angka langsung disimpan.
4. Jika bucket sudah berisi angka lain, angka baru ditambahkan ke linked list bucket tersebut.

Contoh:

```text
Sebelum insert 143:
42 --> 42

Sesudah insert 143:
42 --> 42 --> 143
```

Angka pertama setelah nomor index adalah data yang berada di linked list bucket tersebut.

## Proses Search Data

Saat mencari angka, program melakukan langkah berikut:

1. Hitung index dengan rumus `angka % 101`.
2. Masuk ke bucket pada index tersebut.
3. Telusuri linked list pada bucket itu.
4. Jika angka ditemukan, program menampilkan index dan posisi angka dalam linked list.

Contoh pencarian angka `143`:

```text
143 % 101 = 42
```

Program hanya perlu mencari di bucket index `42`. Jika ditemukan, outputnya seperti:

```text
Data 143 ditemukan pada index 42, posisi linked list ke-2.
```

Posisi linked list dihitung mulai dari `1` agar lebih mudah dibaca.

## Contoh Collision dari Data Awal

Beberapa collision nyata dari array `INITIAL_KEYS`:

```text
Index 42: 42, 143, 850
Index 12: 517, 12
Index 25: 126, 732, 227
Index 8 : 311, 715
Index 3 : 205, 609, 407, 912
```

Contoh index `3`:

```text
205 % 101 = 3
609 % 101 = 3
407 % 101 = 3
912 % 101 = 3
```

Maka semua angka tersebut berada di bucket index `3` dan disimpan sebagai linked list.

## Cara Menjalankan Program

Compile:

```powershell
javac src\hash_table.java
```

Run:

```powershell
java -cp src hash_table
```

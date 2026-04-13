AVL + BST GUI App

Files:
- Main.java : aplikasi utama Swing GUI dengan visual tree AVL
- data_avl.txt : file penyimpanan otomatis yang dibuat saat program menyimpan data

Cara menjalankan:
1. Simpan Main.java pada satu folder.
2. Compile:
   javac src/Main.java
3. Run:
   java -cp src Main

Catatan input bulk:
- Bisa paste data dari Excel dalam format dua kolom.
- Contoh:
  1001<TAB>Budi Santoso
  1002<TAB>Siti Aminah
- Atau:
  1001,Budi Santoso
  1002,Siti Aminah

Fitur:
- Tambah / update data
- Cari berdasarkan ID atau Nama
- Hapus berdasarkan ID atau Nama
- Traversal inorder / preorder / postorder
- Visual tree AVL
- Auto save dan auto load
- Reset dari menu

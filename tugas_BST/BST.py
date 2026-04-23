class Node:
    def __init__(self, id, nama):
        self.id = id
        self.nama = nama
        self.left = None
        self.right = None


class BST:
    def __init__(self):
        self.root = None

    # INSERT
    def insert(self, root, id, nama):
        if root is None:
            return Node(id, nama)
        if id < root.id:
            root.left = self.insert(root.left, id, nama)
        elif id > root.id:
            root.right = self.insert(root.right, id, nama)
        return root

    # SEARCH
    def search(self, root, id):
        if root is None or root.id == id:
            return root
        if id < root.id:
            return self.search(root.left, id)
        return self.search(root.right, id)

    # MIN VALUE
    def min_value(self, root):
        while root.left:
            root = root.left
        return root

    # DELETE
    def delete(self, root, id):
        if root is None:
            return root

        if id < root.id:
            root.left = self.delete(root.left, id)
        elif id > root.id:
            root.right = self.delete(root.right, id)
        else:
            if root.left is None:
                return root.right
            elif root.right is None:
                return root.left

            temp = self.min_value(root.right)
            root.id = temp.id
            root.nama = temp.nama
            root.right = self.delete(root.right, temp.id)

        return root

    # TRAVERSAL
    def inorder(self, root):
        if root:
            self.inorder(root.left)
            print(root.id, "-", root.nama)
            self.inorder(root.right)

    def preorder(self, root):
        if root:
            print(root.id, "-", root.nama)
            self.preorder(root.left)
            self.preorder(root.right)

    def postorder(self, root):
        if root:
            self.postorder(root.left)
            self.postorder(root.right)
            print(root.id, "-", root.nama)


# ================= DATA AWAL (EXCEL) =================
data = [
    (5288,"pensil"),(5993,"pulpen"),(8689,"penghapus"),(8043,"buku"),
    (8699,"sampul"),(2156,"penggaris"),(4457,"kertas"),(8938,"cat"),
    (2618,"stabilo"),(9033,"mobil"),(9971,"motor"),(3874,"becak"),
    (5914,"sepeda"),(2398,"kereta"),(3725,"pesawat"),(5210,"perahu"),
    (7363,"kapal"),(7631,"rakit"),(4513,"kipas"),(5656,"charger"),
    (6453,"peci"),(8783,"sarung"),(8194,"sajadah"),(9783,"smartphone"),
    (3685,"jam"),(4490,"televisi"),(8294,"laptop"),(8563,"komputer"),
    (1070,"mouse"),(5408,"keyboard"),(8258,"tablet"),(9309,"jendela"),
    (1138,"kaca"),(2751,"pintu"),(3258,"kompor"),(6402,"lemari"),
    (7921,"kasur"),(9781,"ranjang"),(3818,"bantal"),(5204,"baju"),
    (6119,"kaos"),(1928,"celana"),(4207,"mukena"),(7255,"jilbab"),
    (5309,"pigura"),(2897,"antena"),(8028,"kulkas"),(1660,"dispenser"),
    (3248,"meja"),(5641,"kursi"),(7376,"kemoceng"),(3525,"sapu"),
    (4492,"gayung"),(7187,"sabun"),(1305,"sikat"),(6602,"shampo"),
    (8153,"botol"),(3561,"gelas"),(5082,"piring"),(7151,"panci"),
    (7524,"wajan"),(9178,"blender"),(9817,"galon"),(4304,"cobek"),
    (6820,"termos"),(9151,"kran"),(3482,"selang"),(3316,"karpet"),
    (5192,"tikar"),(7572,"keset"),(7660,"sepatu"),(9224,"kaos kaki"),
    (5083,"jaket"),(6362,"piama"),(6465,"piano"),(9888,"gitar"),
    (4159,"angklung"),(4969,"suling"),(5097,"toples"),(6271,"parfum"),
    (9250,"sisir"),(3409,"topi"),(4577,"gunting"),(6244,"pisau"),
    (8612,"kaleng"),(4650,"tisu"),(6799,"tas"),(9298,"ikat pinggang"),
    (4361,"korek api"),(4379,"kopi"),(6928,"gula"),(3195,"cabai"),
    (5741,"wortel"),(6852,"timun"),(8147,"apel"),(8902,"jeruk"),
    (8967,"tomat"),(1302,"pisang"),(2363,"pepaya"),(6861,"bawang")
]

# ================= MAIN =================
tree = BST()

# insert semua data awal
for id, nama in data:
    tree.root = tree.insert(tree.root, id, nama)

# ================= MENU =================
while True:
    print("\n=== MENU BST ===")
    print("1. Tambah Data")
    print("2. Cari Data")
    print("3. Hapus Data")
    print("4. Inorder")
    print("5. Preorder")
    print("6. Postorder")
    print("0. Keluar")

    pilihan = input("Pilih: ")

    if pilihan == "1":
        id = int(input("Masukkan ID: "))
        nama = input("Masukkan Nama: ")
        tree.root = tree.insert(tree.root, id, nama)
        print("Data berhasil ditambahkan!")

    elif pilihan == "2":
        cari = int(input("Masukkan ID: "))
        hasil = tree.search(tree.root, cari)
        if hasil:
            print("Ditemukan:", hasil.nama)
        else:
            print("Data tidak ditemukan!")

    elif pilihan == "3":
        hapus = int(input("Masukkan ID: "))
        tree.root = tree.delete(tree.root, hapus)
        print("Data berhasil dihapus!")

    elif pilihan == "4":
        print("\nInorder:")
        tree.inorder(tree.root)

    elif pilihan == "5":
        print("\nPreorder:")
        tree.preorder(tree.root)

    elif pilihan == "6":
        print("\nPostorder:")
        tree.postorder(tree.root)

    elif pilihan == "0":
        print("Program selesai.")
        break

    else:
        print("Pilihan tidak valid!")

class hashNode:
    # Konstruktor node untuk menyimpan key dan value
    def __init__(self, key, value):
        self.key = key
        self.value = value


class hashMap:
    # Konstruktor hash table
    def __init__(self):
        self.capacity = 101
        self.size = 0
        self.arr = [None] * self.capacity
        self.dummy = hashNode(-1, -1)

    # Fungsi hash untuk menentukan index awal
    def hashCode(self, key):
        return abs(key) % self.capacity

    # Menambahkan key-value pair menggunakan linear probing
    def insertNode(self, key, value):
        if key == -1:
            return False, -1, -1

        hashIndex = self.hashCode(key)
        startIndex = hashIndex
        firstDeletedIndex = -1
        counter = 0

        while counter < self.capacity:
            currentNode = self.arr[hashIndex]

            if currentNode is None:
                targetIndex = firstDeletedIndex if firstDeletedIndex != -1 else hashIndex
                self.arr[targetIndex] = hashNode(key, value)
                self.size += 1
                return True, startIndex, targetIndex

            if currentNode.key == -1 and firstDeletedIndex == -1:
                firstDeletedIndex = hashIndex
            elif currentNode.key == key:
                return False, startIndex, hashIndex

            hashIndex = (hashIndex + 1) % self.capacity
            counter += 1

        if firstDeletedIndex != -1:
            self.arr[firstDeletedIndex] = hashNode(key, value)
            self.size += 1
            return True, startIndex, firstDeletedIndex

        return False, startIndex, -1

    # Menghapus data berdasarkan key
    def deleteNode(self, key):
        hashIndex = self.hashCode(key)
        counter = 0

        while self.arr[hashIndex] is not None and counter < self.capacity:
            if self.arr[hashIndex].key == key:
                deletedValue = self.arr[hashIndex].value
                deletedIndex = hashIndex
                self.arr[hashIndex] = self.dummy
                self.size -= 1
                return deletedValue, deletedIndex

            hashIndex = (hashIndex + 1) % self.capacity
            counter += 1

        return -1, -1

    # Mencari value berdasarkan key
    def get(self, key):
        hashIndex = self.hashCode(key)
        startIndex = hashIndex
        counter = 0

        while self.arr[hashIndex] is not None and counter < self.capacity:
            if self.arr[hashIndex].key == key:
                return self.arr[hashIndex].value, startIndex, hashIndex

            hashIndex = (hashIndex + 1) % self.capacity
            counter += 1

        return -1, startIndex, -1

    # Mengembalikan jumlah data dalam hash table
    def sizeofMap(self):
        return self.size

    # Mengecek apakah hash table kosong
    def isEmpty(self):
        return self.size == 0

    # Menampilkan seluruh isi hash table
    def display(self):
        print("\nIsi hash table:")
        for index in range(self.capacity):
            currentNode = self.arr[index]

            if currentNode is not None and currentNode.key != -1:
                print(f"Index {index}: {currentNode.key}")
            else:
                print(f"Index {index}: -")


INITIAL_KEYS = [
    42, 517, 893, 126, 754, 311, 968, 205, 679, 34,
    821, 456, 990, 143, 588, 267, 732, 91, 604, 379,
    850, 12, 699, 234, 941, 118, 563, 807, 326, 471,
    75, 916, 284, 637, 159, 702, 448, 995, 23, 531,
    864, 196, 715, 352, 609, 88, 973, 240, 681, 407,
    129, 556, 814, 367, 925, 51, 493, 770, 218, 645,
    390, 100, 836, 274, 577, 958, 6, 724, 439, 187,
    662, 301, 889, 134, 548, 791, 253, 610, 967, 72,
    415, 830, 199, 703, 346, 581, 912, 29, 468, 755,
    320, 647, 108, 884, 227, 540, 799, 64, 371, 936
]


def loadInitialData(hashTable):
    for key in INITIAL_KEYS:
        hashTable.insertNode(key, key)


def readNumber(message):
    while True:
        try:
            return int(input(message))
        except ValueError:
            print("Input harus berupa angka.")


def showMenu():
    print("\n===== MENU HASH TABLE =====")
    print("1. INPUT DATA")
    print("2. HAPUS DATA")
    print("3. CARI DATA")
    print("4. TAMPILKAN HASH TABLE")
    print("0. KELUAR")


if __name__ == "__main__":
    hashTable = hashMap()
    loadInitialData(hashTable)

    print("Program Hash Table dengan Linear Probing")
    print(f"Data awal sebanyak {hashTable.sizeofMap()} angka random unik sudah dimasukkan.")

    while True:
        showMenu()
        choice = readNumber("Pilih menu: ")

        if choice == 1:
            keyToInsert = readNumber("Masukkan data numerik: ")
            isInserted, hashIndex, storedIndex = hashTable.insertNode(keyToInsert, keyToInsert)

            if isInserted:
                print(
                    f"Data {keyToInsert} berhasil ditambahkan. "
                    f"Hash index: {hashIndex}, stored index: {storedIndex}."
                )
            elif keyToInsert == -1:
                print("Data -1 tidak dapat digunakan karena dipakai sebagai penanda data terhapus.")
            elif storedIndex == -1:
                print(f"Data {keyToInsert} gagal ditambahkan karena hash table sudah penuh.")
            else:
                print(f"Data {keyToInsert} sudah ada pada index {storedIndex}, tidak boleh duplikat.")

        elif choice == 2:
            keyToDelete = readNumber("Masukkan data yang akan dihapus: ")
            deletedValue, deletedIndex = hashTable.deleteNode(keyToDelete)

            if deletedValue != -1:
                print(f"Data {keyToDelete} berhasil dihapus dari index {deletedIndex}.")
            else:
                print(f"Data {keyToDelete} tidak ditemukan.")

        elif choice == 3:
            keyToSearch = readNumber("Masukkan data yang dicari: ")
            value, hashIndex, storedIndex = hashTable.get(keyToSearch)

            if value != -1:
                print(
                    f"Data {keyToSearch} ditemukan. "
                    f"Hash index: {hashIndex}, stored index: {storedIndex}."
                )
            else:
                print(f"Data {keyToSearch} tidak ditemukan. Hash index awal: {hashIndex}.")

        elif choice == 4:
            hashTable.display()

        elif choice == 0:
            print("Program selesai.")
            break

        else:
            print("Pilihan tidak tersedia.")

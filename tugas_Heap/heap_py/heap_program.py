from data_entry import DataEntry
from max_heap_data import MaxHeapData
from min_heap_data import MinHeapData


min_heap = MinHeapData()
max_heap = MaxHeapData()


def main() -> None:
    display_banner()
    load_initial_data()

    while True:
        display_menu()
        choice = get_int_input("Pilih operasi (0-5): ")

        if choice == 1:
            add_new_data()
        elif choice == 2:
            display_min_heap()
        elif choice == 3:
            display_max_heap()
        elif choice == 4:
            delete_from_min_heap()
        elif choice == 5:
            delete_from_max_heap()
        elif choice == 0:
            print("\nTerima kasih! Program selesai.")
            break
        else:
            print("Pilihan tidak valid! Silakan coba lagi.\n")


def display_banner() -> None:
    print("+------------------------------------------------------------+")
    print("|          PROGRAM HEAP DATA - MIN-HEAP & MAX-HEAP          |")
    print("|              Data dengan ID dan Nama dari Excel            |")
    print("+------------------------------------------------------------+\n")


def load_initial_data() -> None:
    print("Memuat data awal dari file Excel...\n")

    data = [
        (5288, 0), (5993, 1), (8689, 2), (8043, 3), (8699, 4),
        (2156, 5), (4457, 6), (8938, 7), (2618, 8), (9033, 9),
        (9971, 10), (3874, 11), (5914, 12), (2398, 13), (3725, 14),
        (5210, 15), (7363, 16), (7631, 17), (4513, 18), (5656, 19),
        (6453, 20), (8783, 21), (8194, 22), (9783, 23), (3685, 24),
        (4490, 25), (8294, 26), (8563, 27), (1070, 28), (5408, 29),
        (8258, 30), (9309, 31), (1138, 32), (2751, 33), (3258, 34),
        (6402, 35), (7921, 36), (9781, 37), (3818, 38), (5204, 39),
        (6119, 40), (1928, 41), (4207, 42), (7255, 43), (5309, 44),
        (2897, 45), (8028, 46), (1660, 47), (3248, 48), (5641, 49),
        (7376, 50), (3525, 51), (4492, 52), (7187, 53), (1305, 54),
        (6602, 55), (8153, 56), (3561, 57), (5082, 58), (7151, 59),
        (7524, 60), (9178, 61), (9817, 62), (4304, 63), (6820, 64),
        (9151, 65), (3482, 66), (3316, 67), (5192, 68), (7572, 69),
        (7660, 70), (9224, 71), (5083, 72), (6362, 73), (6465, 74),
        (9888, 75), (4159, 76), (4969, 77), (5097, 78), (6271, 79),
        (9250, 80), (3409, 81), (4577, 82), (6244, 83), (8612, 84),
        (4650, 85), (6799, 86), (9298, 87), (4361, 88), (4379, 89),
        (6928, 90), (3195, 91), (5741, 92), (6852, 93), (8147, 94),
        (8902, 95), (8967, 96), (1302, 97), (2363, 98), (6861, 99),
    ]

    names = [
        "pensil", "pulpen", "penghapus", "buku", "sampul",
        "penggaris", "kertas", "cat", "stabilo", "mobil",
        "motor", "becak", "sepeda", "kereta", "pesawat",
        "perahu", "kapal", "rakit", "kipas", "charger",
        "peci", "sarung", "sajadah", "smartphone", "jam",
        "televisi", "laptop", "komputer", "mouse", "keyboard",
        "tablet", "jendela", "kaca", "pintu", "kompor",
        "lemari", "kasur", "ranjang", "bantal", "baju",
        "kaos", "celana", "mukena", "jilbab", "pigura",
        "antena", "kulkas", "dispenser", "meja", "kursi",
        "kemoceng", "sapu", "gayung", "sabun", "sikat",
        "shampo", "botol", "gelas", "piring", "panci",
        "wajan", "blender", "galon", "cobek", "termos",
        "kran", "selang", "karpet", "tikar", "keset",
        "sepatu", "kaos kaki", "jaket", "piama", "piano",
        "gitar", "angklung", "suling", "toples", "parfum",
        "sisir", "topi", "gunting", "pisau", "kaleng",
        "tisu", "tas", "ikat pinggang", "korek api", "kopi",
        "gula", "cabai", "wortel", "timun", "apel",
        "jeruk", "tomat", "pisang", "pepaya", "bawang",
    ]

    for id_data, name_index in data:
        entry = DataEntry(id_data, names[name_index])
        min_heap.insert(entry)
        max_heap.insert(entry)

    print("Data berhasil dimuat!")
    print(f"Total data: {min_heap.size()} entries\n")


def display_menu() -> None:
    print("\n+--------------------------------------------+")
    print("|                 MENU UTAMA                 |")
    print("+--------------------------------------------+")
    print("| 1. Tambah Data Baru (ke Min & Max Heap)    |")
    print("| 2. Tampilkan Data (Min-Heap - Ascending)   |")
    print("| 3. Tampilkan Data (Max-Heap - Descending)  |")
    print("| 4. Hapus Data dari Min-Heap                |")
    print("| 5. Hapus Data dari Max-Heap                |")
    print("| 0. Keluar                                  |")
    print("+--------------------------------------------+")


def add_new_data() -> None:
    print("\n--- Tambah Data Baru ---")
    id_data = get_int_input("Masukkan ID: ")

    if is_id_exists(id_data):
        print(f"ID {id_data} sudah ada di database!")
        return

    nama = input("Masukkan Nama: ").strip()

    if not nama:
        print("Nama tidak boleh kosong!")
        return

    entry = DataEntry(id_data, nama)
    min_heap.insert(entry)
    max_heap.insert(entry)

    print("Data berhasil ditambahkan ke kedua heap!")
    print(f"- Min-Heap: {min_heap.size()} entries")
    print(f"- Max-Heap: {max_heap.size()} entries")


def display_min_heap() -> None:
    print("\n--- Data dari Min-Heap (ID Ascending) ---")

    if min_heap.is_empty():
        print("Min-Heap kosong!")
        return

    print(f"Total data: {min_heap.size()} entries\n")
    min_heap.display()


def display_max_heap() -> None:
    print("\n--- Data dari Max-Heap (ID Descending) ---")

    if max_heap.is_empty():
        print("Max-Heap kosong!")
        return

    print(f"Total data: {max_heap.size()} entries\n")
    max_heap.display()


def delete_from_min_heap() -> None:
    print("\n--- Hapus Data dari Min-Heap ---")

    if min_heap.is_empty():
        print("Min-Heap kosong!")
        return

    id_data = get_int_input("Masukkan ID yang ingin dihapus: ")

    if min_heap.delete(id_data):
        max_heap.delete(id_data)
        print(f"Data dengan ID {id_data} berhasil dihapus dari kedua heap!")
        print(f"- Min-Heap: {min_heap.size()} entries")
        print(f"- Max-Heap: {max_heap.size()} entries")
    else:
        print(f"Data dengan ID {id_data} tidak ditemukan!")


def delete_from_max_heap() -> None:
    print("\n--- Hapus Data dari Max-Heap ---")

    if max_heap.is_empty():
        print("Max-Heap kosong!")
        return

    id_data = get_int_input("Masukkan ID yang ingin dihapus: ")

    if max_heap.delete(id_data):
        min_heap.delete(id_data)
        print(f"Data dengan ID {id_data} berhasil dihapus dari kedua heap!")
        print(f"- Min-Heap: {min_heap.size()} entries")
        print(f"- Max-Heap: {max_heap.size()} entries")
    else:
        print(f"Data dengan ID {id_data} tidak ditemukan!")


def is_id_exists(id_data: int) -> bool:
    for entry in min_heap.get_heap_list():
        if entry.get_id() == id_data:
            return True
    return False


def get_int_input(prompt: str) -> int:
    while True:
        try:
            return int(input(prompt).strip())
        except ValueError:
            print("Input tidak valid! Masukkan angka yang benar.\n")


if __name__ == "__main__":
    main()

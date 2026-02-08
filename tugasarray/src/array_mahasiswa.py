# Fixed-size array implementation for student data
# NIM (integer), Nama (string)

MAX = 15
arr_nim = [None] * MAX
arr_nama = [None] * MAX
count = 0

def input_nim():
    while True:
        nim = input("Masukkan NIM (Enter = batal): ").strip()

        if nim == "":
            print("Input dibatalkan.")
            return None
        elif " " in nim:
            print("NIM tidak boleh mengandung spasi!")
        elif not nim.isdigit():
            print("NIM harus berupa angka!")
        elif len(nim) < 10:
            print("NIM minimal 10 digit!")
        else:
            return nim
        
def input_nama():
    while True:
        raw = input("Masukkan Nama (Enter = batal): ").strip()

        if raw == "":
            print("Input dibatalkan.")
            return None

        nama = " ".join(raw.split())
        if not nama.replace(" ", "").isalpha():
            print("Nama hanya boleh huruf!")
        else:
            return nama.title()
    
def insert_at_beginning(nim, nama):
    global count
    if count == MAX:
        print("Array penuh (overflow). Tidak bisa menambah data.")
        return

    for i in range(count, 0, -1):
        arr_nim[i] = arr_nim[i-1]
        arr_nama[i] = arr_nama[i-1]
    arr_nim[0] = nim
    arr_nama[0] = nama
    count += 1
    print("Data berhasil ditambahkan di awal.")

def insert_at_position(pos, nim, nama):
    global count
    if count == MAX:
        print("Array penuh (overflow). Tidak bisa menambah data.")
        return
    
    for i in range(count, pos, -1):
        arr_nim[i] = arr_nim[i-1]
        arr_nama[i] = arr_nama[i-1]
    arr_nim[pos] = nim
    arr_nama[pos] = nama
    count += 1
    print(f"Data berhasil ditambahkan di posisi {pos}.")

def insert_at_end(nim, nama):
    global count
    if count == MAX:
        print("Array penuh (overflow). Tidak bisa menambah data.")
        return

    arr_nim[count] = nim
    arr_nama[count] = nama
    count += 1
    print("Data berhasil ditambahkan di akhir.")

def delete_from_beginning():
    global count
    if count == 0:
        print("Array kosong (underflow). Tidak bisa menghapus data.")
        return
    for i in range(0, count-1):
        arr_nim[i] = arr_nim[i+1]
        arr_nama[i] = arr_nama[i+1]
    arr_nim[count-1] = None
    arr_nama[count-1] = None
    count -= 1
    print("Data awal berhasil dihapus.")

def delete_at_position(pos):
    global count
    for i in range(pos, count-1):
        arr_nim[i] = arr_nim[i+1]
        arr_nama[i] = arr_nama[i+1]
    arr_nim[count-1] = None
    arr_nama[count-1] = None
    count -= 1
    print(f"Data di posisi {pos} berhasil dihapus.")

def delete_from_end():
    global count
    if count == 0:
        print("Array kosong (underflow). Tidak bisa menghapus data.")
        return
    arr_nim[count-1] = None
    arr_nama[count-1] = None
    count -= 1
    print("Data akhir berhasil dihapus.")

def delete_first_occurrence(nim):
    global count
    idx = -1
    for i in range(count):
        if arr_nim[i] == nim:
            idx = i
            break
    if idx == -1:
        print(f"Data dengan NIM {nim} tidak ditemukan.")
        return
    for i in range(idx, count-1):
        arr_nim[i] = arr_nim[i+1]
        arr_nama[i] = arr_nama[i+1]
    arr_nim[count-1] = None
    arr_nama[count-1] = None
    count -= 1
    print(f"Data dengan NIM {nim} berhasil dihapus.")

def show_data():
    if count == 0:
        print("Array kosong.")
        return
    print("\n=== DATA MAHASISWA ===")
    for i in range(count):
        print(f"{i+1}. NIM: {arr_nim[i]}, Nama: {arr_nama[i]}")

def main():
    while True:
        print("\n=== MENU DATA MAHASISWA ===")
        print("1. Insert at Beginning")
        print("2. Insert at Given Position")
        print("3. Insert at End")
        print("4. Delete from Beginning")
        print("5. Delete at Given Position")
        print("6. Delete from End")
        print("7. Delete First Occurrence")
        print("8. Show Data")
        print("9. Exit")
        menu = input("Pilih menu: ")

        if menu == '1':
            nim = input_nim()
            if nim is None:
                continue
            nama = input_nama()
            if nama is None:
                continue
            insert_at_beginning(nim, nama)

        elif menu == '2':
            while True:
                pos = input(f"Masukkan posisi (0-{count}): ")
                if not pos.isdigit():
                    print("Input harus angka!")
                    continue
                pos = int(pos)
                if pos < 0 or pos > count:
                    print("Posisi tidak valid.")
                    continue
                break
            nim = input_nim()
            if nim is None:
                continue
            nama = input_nama()
            if nama is None:
                continue
            insert_at_position(pos, nim, nama)

        elif menu == '3':
            nim = input_nim()
            if nim is None:
                continue
            nama = input_nama()
            if nama is None:
                continue
            insert_at_end(nim, nama)

        elif menu == '4':
            delete_from_beginning()

        elif menu == '5':
            if count == 0:
                print("Array kosong (underflow). Tidak bisa menghapus data.")
                continue
            while True:
                pos = input(f"Masukkan posisi (0-{count-1}): ")
                if not pos.isdigit():
                    print("Input harus angka!")
                    continue
                pos = int(pos)
                if pos < 0 or pos >= count:
                    print("Posisi tidak valid.")
                    continue
                break
            delete_at_position(pos)

        elif menu == '6':
            delete_from_end()

        elif menu == '7':
            if count == 0:
                print("Array kosong (underflow). Tidak bisa menghapus data.")
                continue
            nim = input("Masukkan NIM yang ingin dihapus: ")
            delete_first_occurrence(nim)

        elif menu == '8':
            show_data()

        elif menu == '9':
            print("Program dihentikan.")
            break
        else:
            print("Menu tidak valid.")

if __name__ == "__main__":
    main()

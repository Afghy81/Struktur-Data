# Singly Linked List Mahasiswa Terminal Program
# Tidak menggunakan list atau library struktur data

import re

class Node:
	def __init__(self, nim, nama):
		self.nim = nim
		self.nama = nama
		self.next = None

class SinglyLinkedList:
	def __init__(self):
		self.head = None
		self.count = 0

	def insert_at_beginning(self, nim, nama):
		new_node = Node(nim, nama)
		new_node.next = self.head
		self.head = new_node
		self.count += 1
		print("\n[SUKSES] Data berhasil ditambahkan di awal.")

	def insert_at_end(self, nim, nama):
		new_node = Node(nim, nama)
		if not self.head:
			self.head = new_node
		else:
			curr = self.head
			while curr.next:
				curr = curr.next
			curr.next = new_node
		self.count += 1
		print("\n[SUKSES] Data berhasil ditambahkan di akhir.")

	def insert_at_position(self, pos, nim, nama):
		if pos < 1 or pos > self.count + 1:
			print("[GAGAL] Posisi tidak valid.")
			return
		if pos == 1:
			self.insert_at_beginning(nim, nama)
			return
		if pos == self.count + 1:
			self.insert_at_end(nim, nama)
			return
		new_node = Node(nim, nama)
		curr = self.head
		idx = 1
		while idx < pos - 1:
			curr = curr.next
			idx += 1
		new_node.next = curr.next
		curr.next = new_node
		self.count += 1
		print(f"\n[SUKSES] Data berhasil ditambahkan di posisi {pos}.")

	def delete_from_beginning(self):
		if not self.head:
			print("[GAGAL] List kosong.")
			return
		deleted = self.head
		self.head = self.head.next
		self.count -= 1
		print(f"\n[SUKSES] Data NIM {deleted.nim} di awal berhasil dihapus.")

	def delete_from_end(self):
		if not self.head:
			print("[GAGAL] List kosong.")
			return
		if not self.head.next:
			deleted = self.head
			self.head = None
			self.count -= 1
			print(f"\n[SUKSES] Data NIM {deleted.nim} di akhir berhasil dihapus.")
			return
		curr = self.head
		while curr.next.next:
			curr = curr.next
		deleted = curr.next
		curr.next = None
		self.count -= 1
		print(f"\n[SUKSES] Data NIM {deleted.nim} di akhir berhasil dihapus.")

	def delete_at_position(self, pos):
		if pos < 1 or pos > self.count:
			print("[GAGAL] Posisi tidak valid.")
			return
		if pos == 1:
			self.delete_from_beginning()
			return
		curr = self.head
		idx = 1
		while idx < pos - 1:
			curr = curr.next
			idx += 1
		deleted = curr.next
		curr.next = deleted.next
		self.count -= 1
		print(f"\n[SUKSES] Data NIM {deleted.nim} di posisi {pos} berhasil dihapus.")

	def delete_by_nim(self, nim):
		curr = self.head
		prev = None
		found = False
		idx = 1
		while curr:
			if curr.nim == nim:
				found = True
				break
			prev = curr
			curr = curr.next
			idx += 1
		if not found:
			print(f"[GAGAL] Data dengan NIM {nim} tidak ditemukan.")
			return
		if prev is None:
			self.head = curr.next
		else:
			prev.next = curr.next
		self.count -= 1
		print(f"\n[SUKSES] Data NIM {nim} berhasil dihapus (first occurrence).")

	def show(self):
		if not self.head:
			print("[INFO] List kosong.")
			return
		print("\nData Mahasiswa:")
		print("No | NIM         | Nama")
		print("---+-------------+----------------------")
		curr = self.head
		idx = 1
		while curr:
			print(f"{idx:2d} | {curr.nim:11s} | {curr.nama}")
			curr = curr.next
			idx += 1
		print(f"\nTotal data: {self.count}\n")

# --- Input Validation Functions ---
def input_nim():
	while True:
		nim = input("Masukkan NIM (angka, ENTER batal): ").strip()
		if nim == "":
			print("[INFO] Input dibatalkan.")
			return None
		if not nim.isdigit():
			print("[ERROR] NIM hanya boleh angka!")
			continue
		return nim

def input_nama():
	while True:
		nama = input("Masukkan Nama (ENTER batal): ").strip()
		if nama == "":
			print("[INFO] Input dibatalkan.")
			return None
		# Validasi: hanya huruf dan spasi, tidak diawali spasi/angka
		if not re.match(r'^[A-Za-z][A-Za-z ]*$', nama):
			print("[ERROR] Nama hanya boleh huruf dan spasi!")
			continue
		# Rapihkan spasi berlebih dan kapitalisasi
		nama = ' '.join(nama.split())
		nama = nama.title()
		return nama

def main():
	sll = SinglyLinkedList()
	while True:
		print("""
======= MENU =======
1. Insert at beginning
2. Insert at given position
3. Insert at end
4. Delete from beginning
5. Delete given position
6. Delete from end
7. Delete first occurrence (berdasarkan NIM)
8. Show data
9. Exit
====================
		""")
		menu = input("Pilih menu [1-9]: ").strip()
		if menu == "1":
			nim = input_nim()
			if nim is None:
				continue
			nama = input_nama()
			if nama is None:
				continue
			sll.insert_at_beginning(nim, nama)
		elif menu == "2":
			if sll.count == 0:
				print("[INFO] List kosong, insert di awal saja.")
				continue
			try:
				pos = int(input(f"Masukkan posisi [1-{sll.count+1}] (ENTER batal): ").strip())
			except ValueError:
				print("[INFO] Input dibatalkan atau tidak valid.")
				continue
			if pos < 1 or pos > sll.count + 1:
				print("\n[ERROR] Posisi tidak valid.")
				continue
			nim = input_nim()
			if nim is None:
				continue
			nama = input_nama()
			if nama is None:
				continue
			sll.insert_at_position(pos, nim, nama)
		elif menu == "3":
			nim = input_nim()
			if nim is None:
				continue
			nama = input_nama()
			if nama is None:
				continue
			sll.insert_at_end(nim, nama)
		elif menu == "4":
			sll.delete_from_beginning()
		elif menu == "5":
			if sll.count == 0:
				print("[INFO] List kosong.")
				continue
			try:
				pos = int(input(f"Masukkan posisi [1-{sll.count}] (ENTER batal): ").strip())
			except ValueError:
				print("[INFO] Input dibatalkan atau tidak valid.")
				continue
			if pos < 1 or pos > sll.count:
				print("\n[ERROR] Posisi tidak valid.")
				continue
			sll.delete_at_position(pos)
		elif menu == "6":
			sll.delete_from_end()
		elif menu == "7":
			if sll.count == 0:
				print("[INFO] List kosong.")
				continue
			nim = input_nim()
			if nim is None:
				continue
			sll.delete_by_nim(nim)
		elif menu == "8":
			sll.show()
		elif menu == "9":
			print("Terima kasih!")
			break
		else:
			print("\n[ERROR] Menu tidak valid.")

if __name__ == "__main__":
	main()

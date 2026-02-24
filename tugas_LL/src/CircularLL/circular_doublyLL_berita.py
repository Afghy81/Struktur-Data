import time
import sys
import threading
import shutil


SCROLL_SPEED = 0.2   # kecepatan animasi huruf
DELAY_BETWEEN = 3     # delay antar berita (3 detik)

#WIDTH = flexible, disesuaikan dengan lebar terminal
def get_terminal_width():
    return shutil.get_terminal_size().columns

def wait_for_enter(stop_event):
		input()  # tunggu ENTER
		stop_event.set()

# Node class for CDLL
class Node:
	def __init__(self, berita):
		self.berita = berita
		self.next = None
		self.prev = None


# Circular Doubly Linked List class
class CircularDoublyLinkedList:
	def __init__(self):
		self.head = None
		self.size = 0
	
	

	# Insert berita di akhir list
	def insert(self, berita):
		"""Menambahkan berita di akhir list."""
		new_node = Node(berita)
		if self.head is None:
			self.head = new_node
			new_node.next = new_node
			new_node.prev = new_node
		else:
			tail = self.head.prev
			tail.next = new_node
			new_node.prev = tail
			new_node.next = self.head
			self.head.prev = new_node
		self.size += 1

	# Hapus berita berdasarkan nomor urut (index mulai dari 1)
	def delete_by_index(self, index):
		"""Menghapus berita berdasarkan nomor urut (mulai dari 1)."""
		if self.head is None or index < 1 or index > self.size:
			return False
		if self.size == 1:
			self.head = None
			self.size = 0
			return True
		current = self.head
		if index == 1:
			tail = self.head.prev
			self.head = self.head.next
			tail.next = self.head
			self.head.prev = tail
			self.size -= 1
			return True
		for _ in range(1, index):
			current = current.next
		prev_node = current.prev
		next_node = current.next
		prev_node.next = next_node
		next_node.prev = prev_node
		self.size -= 1
		return True
	
	

	# Tampilkan berita forward dengan delay 3 detik
	def display_forward(self):
		if self.head is None:
			print("Belum ada berita.")
			return

		stop_event = threading.Event()
		threading.Thread(target=wait_for_enter, args=(stop_event,), daemon=True).start()

		print("Tekan ENTER untuk kembali ke menu...\n")

		current = self.head
		no = 1

		while not stop_event.is_set():

			text = f"[{no}] {current.berita}"
			width = shutil.get_terminal_size().columns
			padding = " " * width
			scroll_text = padding + text + padding

			position = 0

			# Animasi kanan ➜ kiri
			while position + width <= len(scroll_text) and not stop_event.is_set():
				frame = scroll_text[position:position + width]
				sys.stdout.write("\r" + frame)
				sys.stdout.flush()
				time.sleep(SCROLL_SPEED)
				position += 1

			# Delay 3 detik antar berita (tetap bisa keluar saat delay)
			start = time.time()
			while time.time() - start < DELAY_BETWEEN:
				if stop_event.is_set():
					break
				time.sleep(0.1)

			current = current.next
			if current == self.head:
				no = 1
			else:
				no += 1

		# Bersihkan baris
		width = get_terminal_width()
		sys.stdout.write("\r" + " " * width + "\r")
		print("Kembali ke menu...")

	# Tampilkan berita backward dengan delay 3 detik
	def display_backward(self):
		if self.head is None:
			print("Belum ada berita.")
			return

		stop_event = threading.Event()
		threading.Thread(target=wait_for_enter, args=(stop_event,), daemon=True).start()

		print("Tekan ENTER untuk kembali ke menu...\n")

		current = self.head.prev
		no = self.size

		while not stop_event.is_set():

			text = f"[{no}] {current.berita}"
			width = shutil.get_terminal_size().columns
			padding = " " * width
			scroll_text = padding + text + padding

			position = 0

			# Animasi kanan ➜ kiri
			while position + width <= len(scroll_text) and not stop_event.is_set():
				frame = scroll_text[position:position + width]
				sys.stdout.write("\r" + frame)
				sys.stdout.flush()
				time.sleep(SCROLL_SPEED)
				position += 1

			# Delay 3 detik antar berita
			start = time.time()
			while time.time() - start < DELAY_BETWEEN:
				if stop_event.is_set():
					break
				time.sleep(0.1)

			current = current.prev
			if current == self.head.prev:
				no = self.size
			else:
				no -= 1

		width = get_terminal_width()
		sys.stdout.write("\r" + " " * width + "\r")
		print("Kembali ke menu...")

	# Tampilkan berita tertentu berdasarkan nomor urut
	def display_by_index(self, index):
		"""Menampilkan berita tertentu berdasarkan nomor urut."""
		if self.head is None or index < 1 or index > self.size:
			print("Nomor berita tidak valid.")
			return
		current = self.head
		for _ in range(1, index):
			current = current.next
		print(f"{index}. {current.berita}")

	# Tampilkan semua berita (tanpa delay, untuk debug)
	def display_all(self):
		if self.head is None:
			print("Belum ada berita.")
			return
		current = self.head
		no = 1
		while True:
			print(f"{no}. {current.berita}")
			current = current.next
			no += 1
			if current == self.head:
				break

def clean_text(text):
	"""Membersihkan spasi depan, belakang, dan spasi ganda."""
	if text is None:
		return ''
	text = text.strip()
	text = ' '.join(text.split())
	return text

def main():
	cdll = CircularDoublyLinkedList()
	while True:
		print("===== MENU BERITA =====")
		print("1. Insert berita")
		print("2. Hapus berita")
		print("3. Tampilkan berita Forward (delay 3 detik)")
		print("4. Tampilkan berita Backward (delay 3 detik)")
		print("5. Tampil berita tertentu")
		print("6. Exit")
		pilihan = input("Pilih menu: ").strip()
		if not pilihan:
			continue
		if pilihan == '1':
			berita = input("Masukkan berita: ")
			if not berita:
				continue
			berita = clean_text(berita)
			if not berita:
				print("Error: Berita tidak boleh kosong!")
				continue
			cdll.insert(berita)
			print("Berita berhasil ditambahkan.")
		elif pilihan == '2':
			idx_str = input("Masukkan nomor urut berita yang akan dihapus: ").strip()
			if not idx_str:
				continue
			if not idx_str.isdigit():
				print("Error: Input harus berupa angka!")
				continue
			idx = int(idx_str)
			if not cdll.delete_by_index(idx):
				print("Error: Nomor urut tidak valid!")
			else:
				print("Berita berhasil dihapus.")
		elif pilihan == '3':
			cdll.display_forward()
		elif pilihan == '4':
			cdll.display_backward()
		elif pilihan == '5':
			idx_str = input("Masukkan nomor urut berita: ").strip()
			if not idx_str:
				continue
			if not idx_str.isdigit():
				print("Error: Input harus berupa angka!")
				continue
			idx = int(idx_str)
			cdll.display_by_index(idx)
		elif pilihan == '6':
			print("Terima kasih!")
			break
		elif pilihan == '7':
			cdll.display_all() 	# menu debug
		else:
			print("Menu tidak valid!")
		print()

if __name__ == "__main__":
	main()

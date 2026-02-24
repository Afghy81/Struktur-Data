package CircularLL;

import java.util.Scanner;
import java.io.IOException;

// Node class for CDLL
class Node {
	String berita;
	Node next;
	Node prev;

	Node(String berita) {
		this.berita = berita;
		this.next = null;
		this.prev = null;
	}
}

// Circular Doubly Linked List class
class CircularDoublyLinkedList {
	Node head;
	int size;

	public CircularDoublyLinkedList() {
		head = null;
		size = 0;
	}

	// Insert berita di akhir list
	public void insert(String berita) {
		Node newNode = new Node(berita);
		if (head == null) {
			head = newNode;
			head.next = head;
			head.prev = head;
		} else {
			Node tail = head.prev;
			tail.next = newNode;
			newNode.prev = tail;
			newNode.next = head;
			head.prev = newNode;
		}
		size++;
	}

	// Hapus berita berdasarkan nomor urut (index mulai dari 1)
	public boolean deleteByIndex(int index) {
		if (head == null || index < 1 || index > size) {
			return false;
		}
		if (size == 1) {
			head = null;
			size = 0;
			return true;
		}
		Node current = head;
		if (index == 1) {
			Node tail = head.prev;
			head = head.next;
			tail.next = head;
			head.prev = tail;
			size--;
			return true;
		}
		for (int i = 1; i < index; i++) {
			current = current.next;
		}
		Node prevNode = current.prev;
		Node nextNode = current.next;
		prevNode.next = nextNode;
		nextNode.prev = prevNode;

		// Update head
		if (current == head) {
			head = head.next; 
		}
		size--;
		return true;
	}

	private static final int WIDTH = 50; // lebar console (bisa disesuaikan)
	// private static final int DELAY = 300; // 100ms per frame (smooth)

	private boolean isEnterPressed() {
		try {
			while (System.in.available() > 0) {
				System.in.read();
				return true;
			}
		} catch (IOException ignored) {}
		return false;
	}

	public void scrollForward() {
    if (head == null) {
        System.out.println("Belum ada berita.");
        return;
    }

    System.out.println("\nTekan ENTER untuk kembali ke menu...\n");

    Node current = head;
    int no = 1;

    while (true) {

        String text = "[" + no + "] " + current.berita;
        String padding = " ".repeat(WIDTH);
        String scrollText = padding + text + padding;

        int position = 0;

        // animasi geser kanan ➜ kiri
			while (position + WIDTH <= scrollText.length()) {

				System.out.print("\r" + scrollText.substring(position, position + WIDTH));
				position++;

				try {
					Thread.sleep(100); // kecepatan gerak huruf
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}

				if (isEnterPressed()) {
					System.out.print("\r" + " ".repeat(WIDTH) + "\r");
					System.out.println("Kembali ke menu...");
					return;
				}
			}

			// Delay 3 detik setelah 1 berita selesai lewat
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}

			current = current.next;
			if (current == head) {
				no = 1;
			} else {
				no++;
			}
		}
	}
	
	public void scrollBackward() {
    if (head == null) {
        System.out.println("Belum ada berita.");
        return;
    }

    System.out.println("\nTekan ENTER untuk kembali ke menu...\n");

    Node current = head.prev; // mulai dari terakhir
    int no = size;

		while (true) {

			String text = "[" + no + "] " + current.berita;
			String padding = " ".repeat(WIDTH);
			String scrollText = padding + text + padding;

			int position = 0;

			// animasi tetap kanan ➜ kiri
			while (position + WIDTH <= scrollText.length()) {

				System.out.print("\r" + scrollText.substring(position, position + WIDTH));
				position++;

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}

				if (isEnterPressed()) {
					System.out.print("\r" + " ".repeat(WIDTH) + "\r");
					System.out.println("Kembali ke menu...");
					return;
				}
			}

			// Delay 3 detik antar berita
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return;
			}

			current = current.prev;
			if (current == head.prev) {
				no = size;
			} else {
				no--;
			}
		}
	}

	// Tampilkan berita tertentu berdasarkan nomor urut
	public void displayByIndex(int index) {
		if (head == null || index < 1 || index > size) {
			System.out.println("Nomor berita tidak valid.");
			return;
		}
		Node current = head;
		for (int i = 1; i < index; i++) {
			current = current.next;
		}
		System.out.println(index + ". " + current.berita);
	}

	// Tampilkan semua berita (tanpa delay, untuk debug)
	public void displayAll() {
		if (head == null) {
			System.out.println("Belum ada berita.");
			return;
		}
		Node current = head;
		int no = 1;
		do {
			System.out.println(no + ". " + current.berita);
			current = current.next;
			no++;
		} while (current != head);
	}
}

public class Circular_DoublyLL_Berita {
	// Membersihkan spasi depan, belakang, dan spasi ganda
	public static String cleanText(String text) {
		if (text == null) return "";
		text = text.trim();
		text = text.replaceAll("\\s+", " ");
		return text;
	}

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
            CircularDoublyLinkedList cdll = new CircularDoublyLinkedList();
            while (true) {
                System.out.println("===== MENU BERITA =====");
                System.out.println("1. Insert berita (ditambahkan di akhir)");
                System.out.println("2. Hapus berita (berdasarkan nomor urut)");
                System.out.println("3. Tampilkan berita Forward (delay 3 detik)");
                System.out.println("4. Tampilkan berita Backward (delay 3 detik)");
                System.out.println("5. Tampil berita tertentu (berdasarkan nomor urut)");
                System.out.println("6. Exit");
                System.out.print("Pilih menu: ");

                String input = sc.nextLine();
                if (input.isEmpty()) continue;

                switch (input) {
                    case "1":
                        System.out.print("Masukkan berita: ");
                        String berita = sc.nextLine();
                        if (berita.isEmpty()) break;
                        berita = cleanText(berita);
                        if (berita.isEmpty()) {
                            System.out.println("Error: Berita tidak boleh kosong!");
                            break;
                        }
                        cdll.insert(berita);
                        System.out.println("Berita berhasil ditambahkan.");
                        break;
                    case "2":
                        System.out.print("Masukkan nomor urut berita yang akan dihapus: ");
                        String idxStr = sc.nextLine();
                        if (idxStr.isEmpty()) break;
                        if (!idxStr.matches("\\d+")) {
                            System.out.println("Error: Input harus berupa angka!");
                            break;
                        }
                        int idx = Integer.parseInt(idxStr);
                        if (!cdll.deleteByIndex(idx)) {
                            System.out.println("Error: Nomor urut tidak valid!");
                        } else {
                            System.out.println("Berita berhasil dihapus.");
                        }
                        break;
                    case "3":
                        cdll.scrollForward();
						sc.nextLine();
                        break;
                    case "4":
                        cdll.scrollBackward();
						sc.nextLine();
                        break;
                    case "5":
                        System.out.print("Masukkan nomor urut berita: ");
                        String idxStr2 = sc.nextLine();
                        if (idxStr2.isEmpty()) break;
                        if (!idxStr2.matches("\\d+")) {
                            System.out.println("Error: Input harus berupa angka!");
                            break;
                        }
                        int idx2 = Integer.parseInt(idxStr2);
                        cdll.displayByIndex(idx2);
                        break;
                    case "6":
                        System.out.println("Terima kasih!");
                        return;
                    case "7":
                        cdll.displayAll();
                        break;
                    default:
                        System.out.println("Menu tidak valid!");
                        
                }
                
                System.out.println();
            }
		}
	}
}

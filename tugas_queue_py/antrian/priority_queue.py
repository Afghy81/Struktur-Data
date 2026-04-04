class Node:
    def __init__(self, nomor, nama, prioritas=0):
        self.nomor = nomor
        self.nama = nama
        self.prioritas = prioritas
        self.next = None


class QueueLinkedList:
    def __init__(self):
        self.front = None
        self.rear = None   # ✅ tambah rear biar efisien
        self.count = 0

    def isEmpty(self):
        return self.front is None

    def size(self):
        return self.count

    def enqueue(self, nomor, nama, prioritas=0):
        new_node = Node(nomor, nama, prioritas)

        # Jika kosong
        if self.isEmpty():
            self.front = self.rear = new_node
        else:
            self.rear.next = new_node
            self.rear = new_node

        self.count += 1

    def dequeue(self):
        if self.isEmpty():
            return None

        removed = self.front
        self.front = self.front.next

        if self.front is None:
            self.rear = None  # reset jika kosong

        self.count -= 1
        return removed

    def get_all(self):
        result = []
        current = self.front  # ✅ FIX DI SINI

        while current:
            result.append({
                "nomor": current.nomor,
                "nama": current.nama
            })
            current = current.next

        return result
from data_entry import DataEntry


class MinHeapData:
    """Implementasi Min-Heap untuk DataEntry, diurutkan ascending berdasarkan id."""

    def __init__(self):
        self.heap: list[DataEntry] = []

    def insert(self, entry: DataEntry) -> None:
        """Menambahkan elemen ke min-heap. Kompleksitas: O(log n)."""
        self.heap.append(entry)
        self._heapify_up(len(self.heap) - 1)

    def peek(self) -> DataEntry | None:
        """Mengambil elemen terkecil tanpa menghapusnya. Kompleksitas: O(1)."""
        if self.is_empty():
            return None
        return self.heap[0]

    def delete_min(self) -> DataEntry | None:
        """Menghapus elemen terkecil/root. Kompleksitas: O(log n)."""
        if self.is_empty():
            return None

        min_entry = self.heap[0]
        last_entry = self.heap.pop()

        if not self.is_empty():
            self.heap[0] = last_entry
            self._heapify_down(0)

        return min_entry

    def delete(self, id_data: int) -> bool:
        """Menghapus entry spesifik berdasarkan id. Kompleksitas: O(n)."""
        index = self._find_index(id_data)

        if index == -1:
            return False

        last_entry = self.heap.pop()

        if index < len(self.heap):
            self.heap[index] = last_entry
            self._fix_heap_after_replace(index)

        return True

    def is_empty(self) -> bool:
        return len(self.heap) == 0

    def size(self) -> int:
        return len(self.heap)

    def get_all_sorted(self) -> list[DataEntry]:
        """Mengambil semua data dalam urutan ascending tanpa merusak heap asli."""
        result: list[DataEntry] = []
        temp_heap = MinHeapData()

        for entry in self.heap:
            temp_heap.insert(entry)

        while not temp_heap.is_empty():
            result.append(temp_heap.delete_min())

        return result

    def display(self) -> None:
        if self.is_empty():
            print("Min-Heap kosong!")
            return

        sorted_entries = self.get_all_sorted()
        print("+-------+--------------------------------+")
        print("| ID    | NAMA                           |")
        print("+-------+--------------------------------+")
        for entry in sorted_entries:
            print(f"| {entry} |")
        print("+-------+--------------------------------+")

    def get_heap_list(self) -> list[DataEntry]:
        return self.heap.copy()

    def _heapify_up(self, index: int) -> None:
        while index > 0:
            parent_index = (index - 1) // 2
            if self.heap[index].compare_to(self.heap[parent_index]) < 0:
                self._swap(index, parent_index)
                index = parent_index
            else:
                break

    def _heapify_down(self, index: int) -> None:
        while True:
            smallest = index
            left_child = 2 * index + 1
            right_child = 2 * index + 2

            if (
                left_child < len(self.heap)
                and self.heap[left_child].compare_to(self.heap[smallest]) < 0
            ):
                smallest = left_child

            if (
                right_child < len(self.heap)
                and self.heap[right_child].compare_to(self.heap[smallest]) < 0
            ):
                smallest = right_child

            if smallest == index:
                break

            self._swap(index, smallest)
            index = smallest

    def _fix_heap_after_replace(self, index: int) -> None:
        parent_index = (index - 1) // 2
        if index > 0 and self.heap[index].compare_to(self.heap[parent_index]) < 0:
            self._heapify_up(index)
        else:
            self._heapify_down(index)

    def _swap(self, i: int, j: int) -> None:
        self.heap[i], self.heap[j] = self.heap[j], self.heap[i]

    def _find_index(self, id_data: int) -> int:
        for index, entry in enumerate(self.heap):
            if entry.get_id() == id_data:
                return index
        return -1

class DataEntry:
    """Merepresentasikan satu entri data dengan id dan nama."""

    def __init__(self, id_data: int, nama: str):
        self.id = id_data
        self.nama = nama

    def get_id(self) -> int:
        return self.id

    def get_nama(self) -> str:
        return self.nama

    def set_nama(self, nama: str) -> None:
        self.nama = nama

    def compare_to(self, other: "DataEntry") -> int:
        """Perbandingan untuk Min-Heap: id kecil lebih prioritas."""
        return self.id - other.id

    def reverse_compare_to(self, other: "DataEntry") -> int:
        """Perbandingan untuk Max-Heap: id besar lebih prioritas."""
        return other.id - self.id

    def to_long_string(self) -> str:
        return f"ID: {self.id}, Nama: {self.nama}"

    def __str__(self) -> str:
        return f"{self.id:<5} | {self.nama:<30}"

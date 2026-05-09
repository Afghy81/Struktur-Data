/**
 * DataEntry class - Merepresentasikan satu entri data dengan id dan nama
 * Digunakan untuk Min-Heap dan Max-Heap
 */
public class DataEntry implements Comparable<DataEntry> {
    private int id;
    private String nama;

    /**
     * Constructor
     */
    public DataEntry(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    /**
     * Getter untuk id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter untuk nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * Setter untuk nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * CompareTo - untuk Min-Heap (ascending by id)
     */
    @Override
    public int compareTo(DataEntry other) {
        return Integer.compare(this.id, other.id);
    }

    /**
     * Reverse compare - untuk Max-Heap (descending by id)
     */
    public int reverseCompareTo(DataEntry other) {
        return Integer.compare(other.id, this.id);
    }

    /**
     * ToString untuk display
     */
    @Override
    public String toString() {
        return String.format("%-5d | %-30s", id, nama);
    }

    /**
     * Untuk display dengan format lebih panjang
     */
    public String toLongString() {
        return "ID: " + id + ", Nama: " + nama;
    }
}

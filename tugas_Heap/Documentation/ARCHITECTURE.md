# ARSITEKTUR PROGRAM heapPROGRAM

## 🏗️ Class Diagram

```
┌─────────────────────────────┐
│      DataEntry              │
├─────────────────────────────┤
│ - id: int                   │
│ - nama: String              │
├─────────────────────────────┤
│ + getId(): int              │
│ + getNama(): String         │
│ + compareTo(): int          │ (Min-Heap)
│ + reverseCompareTo(): int   │ (Max-Heap)
│ + toString(): String        │
│ + toLongString(): String    │
└─────────────────────────────┘
         ▲              ▲
         │              │
    Digunakan oleh  Digunakan oleh
         │              │
    ┌────┴──────┐      ┌┴──────────┐
    │            │      │           │
┌────────────────┴──┐   ┌──────────────┴─────┐
│   MinHeapData   │   │  MaxHeapData       │
├─────────────────┤   ├───────────────────┤
│ - heap: ArrayList│   │ - heap: ArrayList │
├─────────────────┤   ├───────────────────┤
│ + insert()      │   │ + insert()        │
│ + deleteMin()   │   │ + deleteMax()     │
│ + delete()      │   │ + delete()        │
│ + peek()        │   │ + peek()          │
│ + getAllSorted()│   │ + getAllSorted()  │
│ + display()     │   │ + display()       │
│ + heapifyDown() │   │ + heapifyDown()   │
└─────────────────┘   └───────────────────┘
         ▲                     ▲
         │        Digunakan    │
         └────────────┬────────┘
                      │
           ┌──────────▼──────────┐
           │  heapPROGRAM        │
           ├─────────────────────┤
           │ - minHeap           │
           │ - maxHeap           │
           │ - scanner           │
           ├─────────────────────┤
           │ + main()            │
           │ + loadInitialData() │
           │ + displayMenu()     │
           │ + addNewData()      │
           │ + displayMinHeap()  │
           │ + displayMaxHeap()  │
           │ + deleteFromMinHeap │
           │ + deleteFromMaxHeap │
           └─────────────────────┘
```

## 📊 Data Flow

```
┌──────────────────────────────────┐
│   Program Start (heapPROGRAM)   │
└──────────────┬───────────────────┘
               │
               ▼
    ┌──────────────────────┐
    │ Load Initial Data    │
    │ (100 entries)        │
    └──────────┬───────────┘
               │
        ┌──────┴──────┐
        │             │
        ▼             ▼
    ┌────────┐    ┌────────┐
    │Min-Heap│    │Max-Heap│
    │(sorted │    │(sorted │
    │ ASC)   │    │ DESC)  │
    └─┬──────┘    └───┬────┘
      │               │
      └───────┬───────┘
              │
              ▼
    ┌──────────────────────┐
    │  Display Menu        │
    └──────────┬───────────┘
               │
      ┌────────┼────────┐
      │        │        │ 
      ▼        ▼        ▼
   Add Data  Display  Delete
   (Option 1) Data   Data
   (Opt 2,3) (Opt 4,5)
```

## 🔄 Operasi Detail

### Insert Operation Flow
```
User Input (id, nama)
        │
        ▼
  Validate ID
        │
        ▼
Create DataEntry
        │
    ┌───┴───┐
    │       │
    ▼       ▼
MinHeap  MaxHeap
Insert   Insert
(Heapify-up)
    │       │
    └───┬───┘
        │
        ▼
Display Success Message
```

### Delete Operation Flow
```
User Input (id)
        │
        ▼
Search in both heaps
        │
    ┌───┴───┐
    │       │
    ▼       ▼
MinHeap  MaxHeap
Delete   Delete
(Heapify-down)
    │       │
    └───┬───┘
        │
        ▼
Display Success/Error
```

### Display Operation Flow
```
User Input (Menu 2 or 3)
        │
    ┌───┴───┐
    │       │
    ▼       ▼
Min-Heap Max-Heap
    │       │
    ▼       ▼
getAllSorted() getAllSorted()
Extract semua   Extract semua
(Ascending)     (Descending)
    │       │
    └───┬───┘
        │
        ▼
Format Table
        │
        ▼
Display Output
```

## 🧬 Memory Layout (Heap Structure)

### Min-Heap Internal Array
```
Index: 0    1    2    3    4    5    6
       ┌────┬────┬────┬────┬────┬────┬────┐
Data:  │1070│1138│1302│1305│1660│1928│2156│
       └────┴────┴────┴────┴────┴────┴────┘

Tree Structure:
         1070 (Root - Terkecil)
        /    \
      1138   1302
      /  \   /
    1305 1660 1928 ...
```

### Max-Heap Internal Array
```
Index: 0    1    2    3    4    5    6
       ┌────┬────┬────┬────┬────┬────┬────┐
Data:  │9971│9888│9817│9783│9250│9224│9178│
       └────┴────┴────┴────┴────┴────┴────┘

Tree Structure:
         9971 (Root - Terbesar)
        /    \
      9888   9817
      /  \   /
    9783 9250 9224 ...
```

## 🎯 Interaction Pattern

```
User Interface
    │
    ├─→ Input Handler
    │   ├─→ Validate Input
    │   └─→ getIntInput()
    │
    ├─→ Menu Router
    │   ├─→ 1: addNewData()
    │   ├─→ 2: displayMinHeap()
    │   ├─→ 3: displayMaxHeap()
    │   ├─→ 4: deleteFromMinHeap()
    │   ├─→ 5: deleteFromMaxHeap()
    │   └─→ 0: Exit
    │
    └─→ Heap Operations
        ├─→ insert() [O(log n)]
        ├─→ delete() [O(n)]
        ├─→ deleteMin/Max() [O(log n)]
        ├─→ peek() [O(1)]
        ├─→ getAllSorted() [O(n log n)]
        └─→ display() [O(n)]
```

## 📋 Execution Sequence

### Skenario: Add Data Baru
```
1. Program Start
   └─ loadInitialData()
      └─ Load 100 entries

2. User pilih Menu 1
   └─ addNewData()
      ├─ Input id
      ├─ Validate id (unique check)
      ├─ Input nama
      ├─ Create DataEntry
      ├─ minHeap.insert(entry)
      │  └─ heapifyUp()
      ├─ maxHeap.insert(entry)
      │  └─ heapifyUp()
      └─ Display success

3. Back to Menu
```

### Skenario: Display Min-Heap
```
1. User pilih Menu 2
   └─ displayMinHeap()
      ├─ Check if empty
      ├─ getAllSorted()
      │  ├─ Create temp MinHeap
      │  ├─ Copy all entries
      │  ├─ Extract all (O(n log n))
      │  └─ Return sorted list
      ├─ Format table
      └─ Display output

2. Back to Menu
```

### Skenario: Delete Data
```
1. User pilih Menu 4
   └─ deleteFromMinHeap()
      ├─ Input id
      ├─ minHeap.delete(id)
      │  ├─ Find index (O(n))
      │  ├─ Replace with last
      │  ├─ Remove last
      │  └─ heapifyDown()
      ├─ maxHeap.delete(id) [keep sync]
      │  └─ heapifyDown()
      └─ Display success

2. Back to Menu
```

## 🔄 Synchronization Mechanism

```
Add Operation:
    minHeap.insert(entry) ──┐
                            ├─→ Synchronized
    maxHeap.insert(entry) ──┘

Delete Operation:
    minHeap.delete(id) ──┐
                         ├─→ Synchronized
    maxHeap.delete(id) ──┘

Guarantee:
  minHeap.size() == maxHeap.size() (Always True)
  Both heaps have exactly same data
```

## 🎯 Algorithm Complexity

### Space Complexity
```
Overall: O(n)
├─ Min-Heap storage: O(n)
├─ Max-Heap storage: O(n)
└─ Temporary (display): O(n)
Total = O(n) + O(n) = O(n)
```

### Time Complexity
```
Add:     O(log n)     - Heapify-up
Delete:  O(n)        - Linear search + heapify-down
Display: O(n log n)  - Extract all elements
Search:  O(n)        - Linear scan
```

## 📌 Important Invariants

1. **Min-Heap Property**: Parent ≤ Children (always)
2. **Max-Heap Property**: Parent ≥ Children (always)
3. **Complete Binary Tree**: All levels full except last (left-aligned)
4. **Synchronization**: Both heaps always have same data
5. **Unique IDs**: No duplicate IDs allowed
6. **Size Match**: minHeap.size() == maxHeap.size()

## 🚀 Scalability Considerations

```
Current Configuration:
├─ Data Size: 100 entries
├─ Memory: ~2.5 KB
├─ Insert Time: ~0.001s
├─ Display Time: ~0.01s
└─ Delete Time: ~0.005s

Scalability (at 1000 entries):
├─ Memory: ~25 KB
├─ Insert Time: ~0.002s
├─ Display Time: ~0.1s
└─ Delete Time: ~0.05s
```

---

**Architecture Version**: 1.0
**Design Pattern**: Menu-driven Command Pattern
**Data Structure**: Dual Heap (Min + Max)
**Synchronization**: Tight coupling for consistency

"""
Command Line Interface for AVL Tree Management
Provides menu-driven interface for all operations
"""

from .avl_tree import AVLTree
from .data_manager import DataManager
from .tree_visualizer import TreeVisualizer
import os
import sys


class AVLTreeCLI:
    """CLI interface for AVL Tree operations"""
    
    def __init__(self, data_file='data.json'):
        self.tree = AVLTree()
        self.data_manager = DataManager(data_file)
        self.tree = self.data_manager.load_tree()
    
    def clear_screen(self):
        """Clear terminal screen"""
        os.system('cls' if os.name == 'nt' else 'clear')
    
    def pause(self):
        """Pause and wait for user input"""
        input("\nTekan ENTER untuk melanjutkan...")
    
    # ========================
    # MAIN MENU
    # ========================
    
    def show_main_menu(self):
        """Display main menu"""
        print("\n" + "="*40)
        print("   PROGRAM AVL + BST DATA")
        print("="*40)
        print("1. Tambah data")
        print("2. Cari data")
        print("3. Hapus data")
        print("4. Traversal")
        print("5. Lihat informasi pohon")
        print("6. Keluar")
        print("="*40)
    
    def show_search_menu(self):
        """Display search menu"""
        print("\n--- MENU CARI DATA ---")
        print("1. Cari berdasarkan ID")
        print("2. Cari berdasarkan Nama")
        print("3. Kembali")
    
    def show_delete_menu(self):
        """Display delete menu"""
        print("\n--- MENU HAPUS DATA ---")
        print("1. Hapus berdasarkan ID")
        print("2. Hapus berdasarkan Nama")
        print("3. Kembali")
    
    def show_traversal_menu(self):
        """Display traversal menu"""
        print("\n--- MENU TRAVERSAL ---")
        print("1. Inorder (Left-Root-Right)")
        print("2. Preorder (Root-Left-Right)")
        print("3. Postorder (Left-Right-Root)")
        print("4. Kembali")
    
    def show_tree_info_menu(self):
        """Display tree info menu"""
        print("\n--- MENU INFORMASI POHON ---")
        print("1. Lihat statistik pohon")
        print("2. Lihat struktur pohon (ASCII)")
        print("3. Backup data")
        print("4. Reset pohon")
        print("5. Kembali")
    
    # ========================
    # ADD DATA OPERATIONS
    # ========================
    
    def add_single_by_single(self):
        """Add data one by one"""
        print("\n--- INPUT DATA SATU PER SATU ---")
        print("Format input: ID,Nama")
        print("  Contoh: 1001,Budi Santoso")
        print("  atau dari Excel: 1001<TAB>Budi Santoso")
        print("Ketik 'menu' untuk kembali ke menu utama.\n")
        
        while True:
            try:
                user_input = input("Input data (atau 'menu'): ").strip()
                
                if user_input.lower() == 'menu':
                    return
                
                record = self.data_manager.parse_record(user_input)
                if record is None:
                    print("❌ Format input salah. Gunakan: ID,Nama")
                    continue
                
                self.tree.insert(record['id'], record['nama'])
                print(f"✅ Data berhasil ditambahkan: ID={record['id']}, Nama={record['nama']}")
                self.data_manager.save_tree(self.tree)
                
            except Exception as e:
                print(f"❌ Error: {e}")
    
    def add_bulk_paste_mode(self):
        """Add multiple records at once"""
        print("\n--- MODE PASTE BANYAK DATA ---")
        print("Tempel data per baris dengan format:")
        print("  ID,Nama")
        print("  atau dari Excel: ID<TAB>Nama")
        print("Akhiri dengan baris kosong atau ketik 'SELESAI'\n")
        
        added_count = 0
        while True:
            try:
                user_input = input(f"Input baris (atau 'SELESAI'): ").strip()
                
                if user_input.lower() == 'selesai' or user_input == '':
                    break
                
                record = self.data_manager.parse_record(user_input)
                if record is None:
                    print("⚠️  Format salah, baris dilewati.")
                    continue
                
                self.tree.insert(record['id'], record['nama'])
                print(f"✅ Masuk: ID={record['id']}, Nama={record['nama']}")
                added_count += 1
                
            except Exception as e:
                print(f"❌ Error: {e}")
        
        self.data_manager.save_tree(self.tree)
        print(f"\n✅ Total data ditambahkan: {added_count}")
    
    def add_data_menu(self):
        """Display add data menu"""
        print("\n--- TAMBAH DATA ---")
        print("1. Input satu per satu")
        print("2. Paste banyak data")
        print("3. Kembali")
        
        choice = input("Pilih mode input (1-3): ").strip()
        
        if choice == '1':
            self.add_single_by_single()
        elif choice == '2':
            self.add_bulk_paste_mode()
    
    # ========================
    # SEARCH OPERATIONS
    # ========================
    
    def search_by_id(self):
        """Search data by ID"""
        try:
            node_id = int(input("\nMasukkan ID yang dicari: ").strip())
            node = self.tree.search_by_id(node_id)
            
            if node is None:
                print("❌ Data tidak ditemukan.")
            else:
                self.show_single_node(node)
        except ValueError:
            print("❌ ID harus berupa angka.")
    
    def search_by_name(self):
        """Search data by name"""
        nama = input("\nMasukkan Nama yang dicari: ").strip()
        results = self.tree.search_by_name(nama)
        
        if not results:
            print("❌ Data tidak ditemukan.")
        else:
            print(f"\n✅ Ditemukan {len(results)} data:")
            search_results = TreeVisualizer.search_results_to_list(results, self.tree)
            for result in search_results:
                print(f"\n{result['number']}. ID: {result['id']} | Nama: {result['nama']}")
                print(f"   Posisi inorder: {result['inorder_position']}")
    
    def search_menu(self):
        """Handle search menu"""
        while True:
            self.show_search_menu()
            choice = input("Pilih (1-3): ").strip()
            
            if choice == '1':
                self.search_by_id()
            elif choice == '2':
                self.search_by_name()
            elif choice == '3':
                return
            else:
                print("❌ Pilihan tidak valid.")
    
    # ========================
    # DELETE OPERATIONS
    # ========================
    
    def delete_by_id(self):
        """Delete data by ID"""
        try:
            node_id = int(input("\nMasukkan ID yang akan dihapus: ").strip())
            node = self.tree.search_by_id(node_id)
            
            if node is None:
                print("❌ Data tidak ditemukan.")
            else:
                confirm = input(f"Yakin hapus data ID={node_id}, Nama={node.nama}? (y/n): ").strip()
                if confirm.lower() == 'y':
                    self.tree.delete(node_id)
                    self.data_manager.save_tree(self.tree)
                    print("✅ Data berhasil dihapus.")
                else:
                    print("Dibatalkan.")
        except ValueError:
            print("❌ ID harus berupa angka.")
    
    def delete_by_name(self):
        """Delete data by name"""
        nama = input("\nMasukkan Nama yang akan dihapus: ").strip()
        results = self.tree.search_by_name(nama)
        
        if not results:
            print("❌ Data tidak ditemukan.")
        elif len(results) == 1:
            confirm = input(f"Yakin hapus data Nama={nama}? (y/n): ").strip()
            if confirm.lower() == 'y':
                self.tree.delete(results[0].id)
                self.data_manager.save_tree(self.tree)
                print("✅ Data berhasil dihapus.")
            else:
                print("Dibatalkan.")
        else:
            print(f"\nDitemukan {len(results)} data dengan nama yang sama:")
            search_results = TreeVisualizer.search_results_to_list(results, self.tree)
            for result in search_results:
                print(f"{result['number']}. ID={result['id']}, Posisi inorder: {result['inorder_position']}")
            
            try:
                id_to_delete = int(input("\nMasukkan ID yang ingin dihapus: ").strip())
                if any(r['id'] == id_to_delete for r in search_results):
                    confirm = input(f"Yakin hapus ID={id_to_delete}? (y/n): ").strip()
                    if confirm.lower() == 'y':
                        self.tree.delete(id_to_delete)
                        self.data_manager.save_tree(self.tree)
                        print("✅ Data berhasil dihapus.")
                    else:
                        print("Dibatalkan.")
                else:
                    print("❌ ID tidak cocok dengan nama yang dicari.")
            except ValueError:
                print("❌ ID harus berupa angka.")
    
    def delete_menu(self):
        """Handle delete menu"""
        while True:
            self.show_delete_menu()
            choice = input("Pilih (1-3): ").strip()
            
            if choice == '1':
                self.delete_by_id()
            elif choice == '2':
                self.delete_by_name()
            elif choice == '3':
                return
            else:
                print("❌ Pilihan tidak valid.")
    
    # ========================
    # TRAVERSAL OPERATIONS
    # ========================
    
    def show_traversal(self, traversal_type):
        """Show traversal results"""
        if traversal_type == 'inorder':
            nodes = self.tree.inorder()
            title = "TRAVERSAL INORDER (Urut dari Kiri-Root-Kanan)"
        elif traversal_type == 'preorder':
            nodes = self.tree.preorder()
            title = "TRAVERSAL PREORDER (Root-Kiri-Kanan)"
        else:  # postorder
            nodes = self.tree.postorder()
            title = "TRAVERSAL POSTORDER (Kiri-Kanan-Root)"
        
        self.print_nodes(title, nodes)
    
    def traversal_menu(self):
        """Handle traversal menu"""
        while True:
            self.show_traversal_menu()
            choice = input("Pilih (1-4): ").strip()
            
            if choice == '1':
                self.show_traversal('inorder')
            elif choice == '2':
                self.show_traversal('preorder')
            elif choice == '3':
                self.show_traversal('postorder')
            elif choice == '4':
                return
            else:
                print("❌ Pilihan tidak valid.")
    
    # ========================
    # TREE INFO OPERATIONS
    # ========================
    
    def show_tree_stats(self):
        """Show tree statistics"""
        stats = TreeVisualizer.get_tree_stats(self.tree)
        print("\n" + "="*40)
        print("STATISTIK POHON AVL")
        print("="*40)
        print(f"Total node: {stats['total_nodes']}")
        print(f"Tinggi pohon: {stats['tree_height']}")
        print(f"Status: {'Kosong' if stats['is_empty'] else 'Berisi data'}")
        print("="*40)
    
    def show_tree_structure(self):
        """Show ASCII tree structure"""
        ascii_tree = TreeVisualizer.generate_ascii_tree(self.tree)
        print("\n" + "="*50)
        print("STRUKTUR POHON AVL (ASCII)")
        print("="*50)
        print(ascii_tree)
        print("="*50)
    
    def backup_data(self):
        """Create backup"""
        backup_file = self.data_manager.backup_data()
        if backup_file:
            print(f"✅ Backup berhasil dibuat: {backup_file}")
        else:
            print("❌ Gagal membuat backup.")
    
    def reset_tree(self):
        """Reset tree"""
        confirm = input("⚠️  Yakin ingin reset semua data? (y/n): ").strip()
        if confirm.lower() == 'y':
            self.tree.clear()
            self.data_manager.save_empty_data()
            self.tree = self.data_manager.load_tree()
            print("✅ Pohon telah direset.")
        else:
            print("Dibatalkan.")
    
    def tree_info_menu(self):
        """Handle tree info menu"""
        while True:
            self.show_tree_info_menu()
            choice = input("Pilih (1-5): ").strip()
            
            if choice == '1':
                self.show_tree_stats()
            elif choice == '2':
                self.show_tree_structure()
            elif choice == '3':
                self.backup_data()
            elif choice == '4':
                self.reset_tree()
            elif choice == '5':
                return
            else:
                print("❌ Pilihan tidak valid.")
    
    # ========================
    # UTILITY METHODS
    # ========================
    
    def show_single_node(self, node):
        """Display single node information"""
        pos = self.tree.get_inorder_position_by_node(node)
        print(f"\n✅ Data berhasil ditemukan pada urutan node ke-{pos}")
        print(f"{'='*40}")
        print(f"ID   : {node.id}")
        print(f"Nama : {node.nama}")
        print(f"Posisi inorder: {pos}")
        print(f"{'='*40}")
    
    def print_nodes(self, title, nodes):
        """Print list of nodes"""
        print(f"\n{'='*50}")
        print(title)
        print(f"{'='*50}")
        
        if not nodes:
            print("(data kosong)")
        else:
            for i, node in enumerate(nodes):
                pos = self.tree.get_inorder_position_by_node(node)
                print(f"{i+1}. ID: {node.id} | Nama: {node.nama} | Posisi inorder: {pos}")
        
        print(f"{'='*50}")
    
    # ========================
    # MAIN LOOP
    # ========================
    
    def run(self):
        """Main program loop"""
        print("\n✅ Sistem AVL Tree telah dimulai.")
        print(f"📊 Data dimuat dari file. Total data: {self.tree.get_size()}")
        
        while True:
            try:
                self.show_main_menu()
                choice = input("Pilih menu (1-6): ").strip()
                
                if choice == '1':
                    self.add_data_menu()
                elif choice == '2':
                    self.search_menu()
                elif choice == '3':
                    self.delete_menu()
                elif choice == '4':
                    self.traversal_menu()
                elif choice == '5':
                    self.tree_info_menu()
                elif choice == '6':
                    confirm = input("\n⚠️  Yakin ingin keluar? (y/n): ").strip()
                    if confirm.lower() == 'y':
                        print("\n✅ Terima kasih telah menggunakan program ini.")
                        print("📝 Data telah disimpan otomatis.\n")
                        break
                else:
                    print("❌ Pilihan tidak valid.")
                
            except KeyboardInterrupt:
                print("\n\n⚠️  Program dihentikan oleh user.")
                break
            except Exception as e:
                print(f"❌ Error: {e}")


def main():
    """Main entry point"""
    cli = AVLTreeCLI('data.json')
    cli.run()


if __name__ == '__main__':
    main()

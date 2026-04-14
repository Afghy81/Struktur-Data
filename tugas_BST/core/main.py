#!/usr/bin/env python3
"""
Main Entry Point - AVL Tree Application Launcher
Choose between CLI or Web Interface
"""

import sys
import subprocess
import platform


def print_header():
    """Print application header"""
    print("\n" + "="*60)
    print("  🌳 AVL TREE - DATA MANAGER & VISUALIZER 🌳")
    print("="*60)
    print()


def print_menu():
    """Display main menu"""
    print("Pilih mode aplikasi:")
    print()
    print("  1️⃣  CLI Mode (Terminal Interface)")
    print("  2️⃣  Web Mode (Browser Interface - localhost:5000)")
    print("  3️⃣  Info")
    print("  0️⃣  Keluar")
    print()


def show_info():
    """Show information about the application"""
    print("\n" + "="*60)
    print("  ℹ️  INFORMASI APLIKASI")
    print("="*60)
    print("""
🌳 AVL Tree - Self-Balancing Binary Search Tree

Fitur Utama:
  ✅ Tambah, Cari, dan Hapus Data
  ✅ Support ID (int) + Nama (string)
  ✅ Traversal: Inorder, Preorder, Postorder
  ✅ Visualisasi Tree Real-time
  ✅ Data Persistence (JSON)
  ✅ Flexible Input Format (CSV, Tab, etc)
  ✅ Excel Support

Mode:
  📱 CLI: Interface berbasis terminal
  🌐 WEB: Interface web modern + D3.js visualization

Data:
  📁 Tersimpan otomatis dalam data.json
  💾 Backup & Reset support
  
Kompleksitas:
  ⚡ O(log n) untuk semua operasi (Search, Insert, Delete)
  🚀 Lebih efisien dari regular BST

Untuk detail lebih lanjut, baca README.md
""")
    print("="*60 + "\n")


def run_cli():
    """Run CLI application"""
    print("\n🚀 Memulai CLI Mode...\n")
    try:
        import core.cli as cli
        cli_app = cli.AVLTreeCLI('data.json')
        cli_app.run()
    except ImportError:
        print("❌ Error: Module cli.py tidak ditemukan")
    except Exception as e:
        print(f"❌ Error: {e}")


def run_web():
    """Run Web application"""
    print("\n🚀 Memulai Web Mode...\n")
    print("⏳ Mohon tunggu, server sedang dimulai...")
    print()
    
    try:
        import core.app as app
        print("\n" + "="*60)
        print("✅ Flask Server Started!")
        print("="*60)
        print()
        print("🌐 BUKA BROWSER ANDA KE:")
        print("   👉 http://localhost:5000")
        print()
        print("📊 API Available at:")
        print("   👉 http://localhost:5000/api")
        print()
        print("⚠️  Tekan CTRL+C untuk menghentikan server")
        print("="*60 + "\n")
        
        app.app.run(debug=True, host='localhost', port=5000)
    except ImportError:
        print("❌ Error: Flask tidak terinstall")
        print("   Jalankan: pip install -r requirements.txt")
    except OSError as e:
        if "Address already in use" in str(e):
            print("❌ Error: Port 5000 sudah digunakan")
            print("   Solusi:")
            print("   - Tutup aplikasi lain yang menggunakan port 5000")
            print("   - Atau ubah port di file app.py")
        else:
            print(f"❌ Error: {e}")
    except Exception as e:
        print(f"❌ Error: {e}")


def check_dependencies():
    """Check if all dependencies are installed"""
    print("\n🔍 Checking dependencies...")
    
    missing = []
    
    try:
        import flask
        print("  ✅ Flask installed")
    except ImportError:
        missing.append("Flask")
    
    if missing:
        print(f"\n❌ Missing dependencies: {', '.join(missing)}")
        print("🔧 Install with:")
        print("   pip install -r requirements.txt")
        return False
    
    print("  ✅ All dependencies OK\n")
    return True


def main():
    """Main application loop"""
    print_header()
    
    # Check dependencies
    if not check_dependencies():
        input("Tekan ENTER untuk keluar...")
        sys.exit(1)
    
    while True:
        print_menu()
        
        try:
            choice = input("Pilih (0-3): ").strip()
            
            if choice == '1':
                run_cli()
            elif choice == '2':
                run_web()
            elif choice == '3':
                show_info()
            elif choice == '0':
                print("\n👋 Terima kasih telah menggunakan AVL Tree!\n")
                sys.exit(0)
            else:
                print("❌ Pilihan tidak valid. Silakan coba lagi.\n")
        
        except KeyboardInterrupt:
            print("\n\n⚠️  Program dihentikan oleh user.")
            print("👋 Terima kasih telah menggunakan AVL Tree!\n")
            sys.exit(0)
        except Exception as e:
            print(f"❌ Error: {e}\n")


if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        print(f"\n❌ Fatal Error: {e}\n")
        sys.exit(1)

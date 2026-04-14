#!/usr/bin/env python3
"""
🌳 AVL TREE - Main Launcher
Entry point from project root
Handles proper imports from core package
"""

import sys
import os
from pathlib import Path

# Add project root to path for proper imports
PROJECT_ROOT = Path(__file__).parent
sys.path.insert(0, str(PROJECT_ROOT))

# Now import from core package
from core.cli import AVLTreeCLI
from core import app as flask_app


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

Struktur Folder:
  core/           - Core modules (avl_tree, data_manager, etc)
  templates/      - HTML templates
  static/         - CSS, JS files
  data.json       - Database (auto-generated)
""")
    print("="*60 + "\n")


def run_cli():
    """Run CLI application"""
    print("\n🚀 Memulai CLI Mode...\n")
    try:
        data_file = PROJECT_ROOT / 'data.json'
        cli = AVLTreeCLI(str(data_file))
        cli.run()
    except Exception as e:
        print(f"❌ Error: {e}")
        import traceback
        traceback.print_exc()


def run_web():
    """Run Web application"""
    print("\n🚀 Memulai Web Mode...\n")
    print("⏳ Mohon tunggu, server sedang dimulai...")
    print()
    
    try:
        # Verify Flask is importable
        import flask
        print("✅ Flask terdeteksi\n")
        
        print("="*60)
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
        
        flask_app.app.run(debug=True, host='localhost', port=5000, use_reloader=False)
    except ImportError:
        print("❌ Error: Flask tidak terinstall")
        print("\n🔧 Solusi:")
        print("   1. Aktifkan virtual environment:")
        print("      .venv\\Scripts\\activate  (Windows)")
        print("      source .venv/bin/activate  (Linux/Mac)")
        print("\n   2. Install requirements:")
        print("      pip install -r requirements.txt")
        print("\n   3. Coba jalankan lagi")
    except OSError as e:
        if "Address already in use" in str(e) or "48" in str(e):
            print("❌ Error: Port 5000 sudah digunakan")
            print("\n🔧 Solusi:")
            print("   - Tutup aplikasi lain yang menggunakan port 5000")
            print("   - Atau ubah port di file core/app.py (line: app.run(port=5001))")
        else:
            print(f"❌ Error: {e}")
    except Exception as e:
        print(f"❌ Error: {e}")
        import traceback
        traceback.print_exc()


def check_dependencies():
    """Check if all dependencies are installed"""
    print("\n🔍 Checking dependencies...")
    
    missing = []
    
    try:
        import flask
        print("  ✅ Flask installed")
    except ImportError:
        print("  ❌ Flask NOT found")
        missing.append("Flask")
    
    if missing:
        print(f"\n❌ Missing dependencies: {', '.join(missing)}")
        print("\n🔧 Install with:")
        print("   pip install -r requirements.txt")
        print("\n   Or specifically:")
        print("   pip install Flask==3.0.0")
        return False
    
    print("  ✅ All dependencies OK\n")
    return True


def main():
    """Main application loop"""
    print_header()
    
    # Check dependencies once at startup
    if not check_dependencies():
        print("\n⚠️  Jalankan command di atas untuk install dependencies")
        print("   Kemudian buka terminal baru dan jalankan lagi:\n")
        print("   python main.py\n")
        input("Tekan ENTER untuk keluar...")
        sys.exit(1)
    
    while True:
        try:
            print_menu()
            
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
            import traceback
            traceback.print_exc()


if __name__ == '__main__':
    try:
        main()
    except Exception as e:
        print(f"\n❌ Fatal Error: {e}\n")
        import traceback
        traceback.print_exc()
        sys.exit(1)

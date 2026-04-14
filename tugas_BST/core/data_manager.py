"""
Data Persistence Manager
Handles saving and loading AVL tree data to/from JSON file
"""

import json
import os
from datetime import datetime
from .avl_tree import AVLTree, Node


class DataManager:
    """Manages data persistence for AVL tree"""
    
    def __init__(self, data_file='data.json'):
        self.data_file = data_file
        self.ensure_file_exists()
    
    def ensure_file_exists(self):
        """Create data file if it doesn't exist"""
        if not os.path.exists(self.data_file):
            self.save_empty_data()
    
    def save_empty_data(self):
        """Save empty data structure"""
        data = {
            'nodes': [],
            'last_updated': datetime.now().isoformat(),
            'version': '1.0'
        }
        with open(self.data_file, 'w', encoding='utf-8') as f:
            json.dump(data, f, indent=2, ensure_ascii=False)
    
    def save_tree(self, tree):
        """Save entire tree to JSON"""
        inorder_list = tree.inorder()
        nodes_data = []
        
        for node in inorder_list:
            nodes_data.append({
                'id': node.id,
                'nama': node.nama
            })
        
        data = {
            'nodes': nodes_data,
            'last_updated': datetime.now().isoformat(),
            'version': '1.0',
            'total_records': len(nodes_data)
        }
        
        with open(self.data_file, 'w', encoding='utf-8') as f:
            json.dump(data, f, indent=2, ensure_ascii=False)
    
    def load_tree(self):
        """Load tree from JSON"""
        tree = AVLTree()
        
        if not os.path.exists(self.data_file):
            return tree
        
        try:
            with open(self.data_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            for node_data in data.get('nodes', []):
                tree.insert(node_data['id'], node_data['nama'])
            
            return tree
        except (json.JSONDecodeError, KeyError, IOError) as e:
            print(f"Error loading data: {e}")
            return tree
    
    def export_to_csv(self, tree, filename='export.csv'):
        """Export tree data to CSV format"""
        inorder_list = tree.inorder()
        
        with open(filename, 'w', encoding='utf-8') as f:
            f.write('ID,Nama,Inorder Position\n')
            for i, node in enumerate(inorder_list):
                f.write(f'{node.id},{node.nama},{i+1}\n')
        
        return filename
    
    def import_from_csv(self, tree, filename):
        """Import data from CSV"""
        try:
            with open(filename, 'r', encoding='utf-8') as f:
                lines = f.readlines()
            
            # Skip header
            for line in lines[1:]:
                line = line.strip()
                if not line:
                    continue
                
                parts = line.split(',', 1)
                if len(parts) >= 2:
                    try:
                        node_id = int(parts[0])
                        nama = parts[1]
                        tree.insert(node_id, nama)
                    except ValueError:
                        continue
            
            return True
        except IOError as e:
            print(f"Error importing CSV: {e}")
            return False
    
    def parse_record(self, input_str):
        """Parse flexible input format (ID, Name)"""
        if not input_str:
            return None
        
        line = input_str.strip()
        if not line:
            return None
        
        # Try different separators
        separators = ['\t', ',', ';', ' ']
        parts = None
        
        for sep in separators:
            if sep in line:
                parts = line.split(sep, 1)
                break
        
        if parts is None:
            parts = line.split(None, 1)
        
        if len(parts) < 2:
            return None
        
        try:
            node_id = int(parts[0].strip())
            nama = parts[1].strip()
            if not nama:
                return None
            return {'id': node_id, 'nama': nama}
        except ValueError:
            return None
    
    def get_file_info(self):
        """Get information about data file"""
        if not os.path.exists(self.data_file):
            return None
        
        try:
            with open(self.data_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            file_stats = os.stat(self.data_file)
            return {
                'total_records': len(data.get('nodes', [])),
                'last_updated': data.get('last_updated'),
                'file_size': file_stats.st_size,
                'file_path': os.path.abspath(self.data_file)
            }
        except Exception as e:
            return None
    
    def backup_data(self):
        """Create backup of current data"""
        if not os.path.exists(self.data_file):
            return None
        
        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        backup_file = f'backup_{timestamp}.json'
        
        try:
            with open(self.data_file, 'r', encoding='utf-8') as f:
                data = json.load(f)
            
            with open(backup_file, 'w', encoding='utf-8') as f:
                json.dump(data, f, indent=2, ensure_ascii=False)
            
            return backup_file
        except Exception as e:
            print(f"Error creating backup: {e}")
            return None

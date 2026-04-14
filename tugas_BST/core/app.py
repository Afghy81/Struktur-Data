"""
Flask Web Application for AVL Tree Visualization
Provides REST API and web interface
"""

from flask import Flask, render_template, jsonify, request
from .avl_tree import AVLTree
from .data_manager import DataManager
from .tree_visualizer import TreeVisualizer
import json
import os
from pathlib import Path

# Get correct paths
BASE_DIR = Path(__file__).parent.parent
TEMPLATE_DIR = BASE_DIR / 'UI' / 'templates'
STATIC_DIR = BASE_DIR / 'UI' / 'static'

app = Flask(__name__, 
            template_folder=str(TEMPLATE_DIR),
            static_folder=str(STATIC_DIR))
app.config['JSON_SORT_KEYS'] = False

# Initialize components
data_manager = DataManager(str(BASE_DIR / 'data.json'))
tree = data_manager.load_tree()


# ========================
# HELPER FUNCTIONS
# ========================

def save_tree():
    """Save tree to file"""
    data_manager.save_tree(tree)


# ========================
# PAGE ROUTES
# ========================

@app.route('/')
def index():
    """Main page"""
    return render_template('index.html')


@app.route('/about')
def about():
    """About page"""
    return render_template('about.html')


# ========================
# API ROUTES - TREE OPERATIONS
# ========================

@app.route('/api/tree/data', methods=['GET'])
def get_tree_data():
    """Get complete tree structure as JSON"""
    tree_json = TreeVisualizer.generate_tree_json(tree)
    return jsonify({
        'success': True,
        'data': tree_json
    })


@app.route('/api/tree/stats', methods=['GET'])
def get_tree_stats():
    """Get tree statistics"""
    stats = TreeVisualizer.get_tree_stats(tree)
    return jsonify({
        'success': True,
        'data': stats
    })


@app.route('/api/tree/ascii', methods=['GET'])
def get_ascii_tree():
    """Get ASCII representation of tree"""
    ascii_repr = TreeVisualizer.generate_ascii_tree(tree)
    return jsonify({
        'success': True,
        'data': ascii_repr
    })


@app.route('/api/tree/clear', methods=['POST'])
def clear_tree():
    """Clear entire tree"""
    tree.clear()
    data_manager.save_empty_data()
    return jsonify({
        'success': True,
        'message': 'Pohon telah direset'
    })


# ========================
# API ROUTES - ADD DATA
# ========================

@app.route('/api/insert', methods=['POST'])
def insert_data():
    """Insert single data"""
    try:
        data = request.json
        node_id = int(data.get('id'))
        nama = str(data.get('nama', '')).strip()
        
        if not nama:
            return jsonify({
                'success': False,
                'message': 'Nama tidak boleh kosong'
            }), 400
        
        tree.insert(node_id, nama)
        save_tree()
        
        return jsonify({
            'success': True,
            'message': f'Data berhasil ditambahkan: ID={node_id}, Nama={nama}'
        })
    
    except ValueError as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 400
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


@app.route('/api/insert-bulk', methods=['POST'])
def insert_bulk():
    """Insert multiple records"""
    try:
        data = request.json
        records = data.get('records', [])
        
        added = 0
        failed = 0
        errors = []
        
        for record in records:
            try:
                node_id = int(record.get('id'))
                nama = str(record.get('nama', '')).strip()
                
                if not nama:
                    failed += 1
                    errors.append(f"ID {node_id}: Nama kosong")
                    continue
                
                tree.insert(node_id, nama)
                added += 1
            except Exception as e:
                failed += 1
                errors.append(str(e))
        
        save_tree()
        
        return jsonify({
            'success': True,
            'added': added,
            'failed': failed,
            'errors': errors,
            'message': f'{added} data berhasil ditambahkan, {failed} gagal'
        })
    
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


@app.route('/api/parse-input', methods=['POST'])
def parse_input():
    """Parse flexible input format"""
    try:
        data = request.json
        input_str = data.get('input', '')
        
        parsed = data_manager.parse_record(input_str)
        
        if parsed is None:
            return jsonify({
                'success': False,
                'message': 'Format tidak valid. Gunakan: ID,Nama'
            }), 400
        
        return jsonify({
            'success': True,
            'data': parsed
        })
    
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


# ========================
# API ROUTES - SEARCH
# ========================

@app.route('/api/search/id/<int:node_id>', methods=['GET'])
def search_by_id(node_id):
    """Search by ID"""
    node = tree.search_by_id(node_id)
    
    if node is None:
        return jsonify({
            'success': False,
            'found': False,
            'message': 'Data tidak ditemukan'
        }), 404
    
    result = TreeVisualizer.search_result_to_dict(node, tree)
    return jsonify({
        'success': True,
        'data': result
    })


@app.route('/api/search/name/<name>', methods=['GET'])
def search_by_name(name):
    """Search by name"""
    nodes = tree.search_by_name(name)
    
    if not nodes:
        return jsonify({
            'success': False,
            'found': False,
            'message': 'Data tidak ditemukan'
        }), 404
    
    results = TreeVisualizer.search_results_to_list(nodes, tree)
    return jsonify({
        'success': True,
        'count': len(results),
        'data': results
    })


# ========================
# API ROUTES - DELETE
# ========================

@app.route('/api/delete/<int:node_id>', methods=['DELETE'])
def delete_by_id(node_id):
    """Delete by ID"""
    node = tree.search_by_id(node_id)
    
    if node is None:
        return jsonify({
            'success': False,
            'message': 'Data tidak ditemukan'
        }), 404
    
    tree.delete(node_id)
    save_tree()
    
    return jsonify({
        'success': True,
        'message': f'Data ID={node_id} berhasil dihapus'
    })


@app.route('/api/delete-by-name', methods=['DELETE'])
def delete_by_name():
    """Delete by name"""
    try:
        data = request.json
        name = data.get('nama', '').strip()
        node_id = data.get('id')
        
        if not name:
            return jsonify({
                'success': False,
                'message': 'Nama tidak boleh kosong'
            }), 400
        
        nodes = tree.search_by_name(name)
        
        if not nodes:
            return jsonify({
                'success': False,
                'message': 'Data tidak ditemukan'
            }), 404
        
        if node_id is not None:
            node_id = int(node_id)
            if not any(n.id == node_id for n in nodes):
                return jsonify({
                    'success': False,
                    'message': 'ID tidak cocok dengan nama yang dicari'
                }), 400
            tree.delete(node_id)
        else:
            if len(nodes) > 1:
                return jsonify({
                    'success': False,
                    'message': 'Ditemukan beberapa data, tentukan ID',
                    'candidates': TreeVisualizer.search_results_to_list(nodes, tree)
                }), 400
            tree.delete(nodes[0].id)
        
        save_tree()
        
        return jsonify({
            'success': True,
            'message': 'Data berhasil dihapus'
        })
    
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


# ========================
# API ROUTES - TRAVERSAL
# ========================

@app.route('/api/traversal/inorder', methods=['GET'])
def traversal_inorder():
    """Get inorder traversal"""
    nodes = tree.inorder()
    data = [{'id': n.id, 'nama': n.nama, 'position': i+1} for i, n in enumerate(nodes)]
    return jsonify({
        'success': True,
        'count': len(data),
        'data': data
    })


@app.route('/api/traversal/preorder', methods=['GET'])
def traversal_preorder():
    """Get preorder traversal"""
    nodes = tree.preorder()
    data = [{'id': n.id, 'nama': n.nama, 'position': i+1} for i, n in enumerate(nodes)]
    return jsonify({
        'success': True,
        'count': len(data),
        'data': data
    })


@app.route('/api/traversal/postorder', methods=['GET'])
def traversal_postorder():
    """Get postorder traversal"""
    nodes = tree.postorder()
    data = [{'id': n.id, 'nama': n.nama, 'position': i+1} for i, n in enumerate(nodes)]
    return jsonify({
        'success': True,
        'count': len(data),
        'data': data
    })


@app.route('/api/traversal/all', methods=['GET'])
def traversal_all():
    """Get all traversal types"""
    data = TreeVisualizer.generate_traversal_data(tree)
    return jsonify({
        'success': True,
        'data': data
    })


# ========================
# API ROUTES - EXPORT/IMPORT
# ========================

@app.route('/api/export/csv', methods=['GET'])
def export_csv():
    """Export data as CSV"""
    try:
        filename = data_manager.export_to_csv(tree, 'export.csv')
        return jsonify({
            'success': True,
            'message': 'Export berhasil',
            'file': filename
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


@app.route('/api/backup', methods=['POST'])
def create_backup():
    """Create data backup"""
    try:
        backup_file = data_manager.backup_data()
        return jsonify({
            'success': True,
            'message': 'Backup berhasil dibuat',
            'file': backup_file
        })
    except Exception as e:
        return jsonify({
            'success': False,
            'message': f'Error: {str(e)}'
        }), 500


# ========================
# ERROR HANDLERS
# ========================

@app.errorhandler(404)
def not_found(error):
    """Handle 404 errors"""
    return jsonify({
        'success': False,
        'message': 'Resource tidak ditemukan'
    }), 404


@app.errorhandler(500)
def server_error(error):
    """Handle 500 errors"""
    return jsonify({
        'success': False,
        'message': 'Server error'
    }), 500


# ========================
# MAIN
# ========================

if __name__ == '__main__':
    # Create templates directory if not exists
    os.makedirs('templates', exist_ok=True)
    os.makedirs('static', exist_ok=True)
    
    print("✅ Flask server starting...")
    print("🌐 Open http://localhost:5000 in your browser")
    print("📊 API Base: http://localhost:5000/api")
    
    app.run(debug=True, host='localhost', port=5000)

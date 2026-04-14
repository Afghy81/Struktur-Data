"""
Tree Visualization Module
Generates tree structure for D3.js visualization
"""

import json


class TreeVisualizer:
    """Generates JSON representation for tree visualization"""
    
    @staticmethod
    def generate_tree_json(tree):
        """Generate complete tree structure as JSON"""
        if tree.root is None:
            return {
                'name': 'Empty Tree',
                'children': [],
                'size': 0
            }
        
        tree_data = TreeVisualizer._node_to_dict(tree.root)
        tree_data['size'] = tree.get_size()
        
        return tree_data
    
    @staticmethod
    def _node_to_dict(node):
        """Recursively convert node to dictionary"""
        if node is None:
            return None
        
        node_dict = {
            'id': node.id,
            'name': f'ID: {node.id}\n{node.nama}',
            'value': node.id,
            'label': node.nama,
            'height': node.height,
            'balance': TreeVisualizer._calculate_balance(node)
        }
        
        children = []
        if node.left is not None:
            children.append(TreeVisualizer._node_to_dict(node.left))
        if node.right is not None:
            children.append(TreeVisualizer._node_to_dict(node.right))
        
        if children:
            node_dict['children'] = children
        
        return node_dict
    
    @staticmethod
    def _calculate_balance(node):
        """Calculate balance factor"""
        if node is None:
            return 0
        left_height = 0 if node.left is None else node.left.height
        right_height = 0 if node.right is None else node.right.height
        return left_height - right_height
    
    @staticmethod
    def generate_ascii_tree(tree):
        """Generate ASCII representation of tree"""
        if tree.root is None:
            return "Empty Tree"
        
        lines = []
        TreeVisualizer._ascii_tree_recursive(tree.root, "", True, lines)
        return '\n'.join(lines)
    
    @staticmethod
    def _ascii_tree_recursive(node, prefix, is_tail, lines):
        """Recursively generate ASCII tree lines"""
        if node is None:
            return
        
        # Add current node
        connector = "└── " if is_tail else "├── "
        lines.append(prefix + connector + f"ID:{node.id} ({node.nama})")
        
        # Prepare prefix for children
        extension = "    " if is_tail else "│   "
        
        # Add children
        if node.left is not None or node.right is not None:
            if node.left is not None:
                is_last = node.right is None
                TreeVisualizer._ascii_tree_recursive(
                    node.left, prefix + extension, is_last, lines
                )
            
            if node.right is not None:
                TreeVisualizer._ascii_tree_recursive(
                    node.right, prefix + extension, True, lines
                )
    
    @staticmethod
    def generate_traversal_data(tree):
        """Generate data for all traversal types"""
        inorder_list = tree.inorder()
        preorder_list = tree.preorder()
        postorder_list = tree.postorder()
        
        return {
            'inorder': [{'id': n.id, 'nama': n.nama, 'position': i+1} 
                       for i, n in enumerate(inorder_list)],
            'preorder': [{'id': n.id, 'nama': n.nama, 'position': i+1} 
                        for i, n in enumerate(preorder_list)],
            'postorder': [{'id': n.id, 'nama': n.nama, 'position': i+1} 
                         for i, n in enumerate(postorder_list)]
        }
    
    @staticmethod
    def search_result_to_dict(node, tree):
        """Convert search result to detailed dictionary"""
        if node is None:
            return None
        
        position = tree.get_inorder_position_by_node(node)
        
        return {
            'found': True,
            'id': node.id,
            'nama': node.nama,
            'inorder_position': position,
            'height': node.height,
            'balance_factor': TreeVisualizer._calculate_balance(node)
        }
    
    @staticmethod
    def search_results_to_list(nodes, tree):
        """Convert search results list to detailed format"""
        results = []
        for i, node in enumerate(nodes):
            position = tree.get_inorder_position_by_node(node)
            results.append({
                'number': i + 1,
                'id': node.id,
                'nama': node.nama,
                'inorder_position': position
            })
        return results
    
    @staticmethod
    def get_tree_stats(tree):
        """Get statistics about the tree"""
        inorder_list = tree.inorder()
        
        return {
            'total_nodes': tree.get_size(),
            'tree_height': 0 if tree.root is None else tree.root.height,
            'is_empty': tree.is_empty(),
            'inorder_traversal': [{'id': n.id, 'nama': n.nama} for n in inorder_list]
        }
    
    @staticmethod
    def generate_dot_format(tree):
        """Generate GraphViz DOT format for tree visualization"""
        if tree.root is None:
            return "digraph G { }"
        
        lines = ["digraph G {"]
        lines.append("  node [style=filled, fillcolor=lightblue, shape=box];")
        
        TreeVisualizer._add_dot_nodes(tree.root, lines)
        
        lines.append("}")
        return '\n'.join(lines)
    
    @staticmethod
    def _add_dot_nodes(node, lines, parent_id=None):
        """Recursively add nodes to DOT format"""
        if node is None:
            return
        
        node_id = f"node_{node.id}"
        label = f"{node.id}\\n{node.nama}"
        lines.append(f'  {node_id} [label="{label}"];')
        
        if parent_id is not None:
            lines.append(f"  {parent_id} -> {node_id};")
        
        if node.left is not None:
            TreeVisualizer._add_dot_nodes(node.left, lines, node_id)
        
        if node.right is not None:
            TreeVisualizer._add_dot_nodes(node.right, lines, node_id)

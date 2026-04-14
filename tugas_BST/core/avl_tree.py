"""
AVL Tree Implementation with BST Properties
Core data structure for efficient data management
"""

class Node:
    """Represents a single node in the AVL tree"""
    def __init__(self, node_id, nama):
        self.id = node_id
        self.nama = nama
        self.height = 1
        self.left = None
        self.right = None
    
    def to_dict(self):
        """Convert node to dictionary for JSON serialization"""
        return {
            'id': self.id,
            'nama': self.nama,
            'height': self.height
        }


class AVLTree:
    """AVL Tree with self-balancing properties"""
    
    def __init__(self):
        self.root = None
    
    # ========================
    # UTILITY METHODS
    # ========================
    
    @staticmethod
    def height(node):
        """Get height of a node"""
        return 0 if node is None else node.height
    
    @staticmethod
    def balance_factor(node):
        """Calculate balance factor"""
        return 0 if node is None else AVLTree.height(node.left) - AVLTree.height(node.right)
    
    @staticmethod
    def update_height(node):
        """Update height of a node"""
        if node is not None:
            node.height = 1 + max(AVLTree.height(node.left), AVLTree.height(node.right))
    
    @staticmethod
    def right_rotate(y):
        """Perform right rotation"""
        x = y.left
        t2 = x.right
        
        x.right = y
        y.left = t2
        
        AVLTree.update_height(y)
        AVLTree.update_height(x)
        
        return x
    
    @staticmethod
    def left_rotate(x):
        """Perform left rotation"""
        y = x.right
        t2 = y.left
        
        y.left = x
        x.right = t2
        
        AVLTree.update_height(x)
        AVLTree.update_height(y)
        
        return y
    
    @staticmethod
    def min_value_node(node):
        """Find node with minimum value"""
        current = node
        while current is not None and current.left is not None:
            current = current.left
        return current
    
    # ========================
    # INSERT OPERATIONS
    # ========================
    
    def insert(self, node_id, nama):
        """Insert new data into tree"""
        self.root = self._insert_recursive(self.root, node_id, nama)
    
    def _insert_recursive(self, node, node_id, nama):
        """Recursive insertion with balancing"""
        if node is None:
            return Node(node_id, nama)
        
        if node_id < node.id:
            node.left = self._insert_recursive(node.left, node_id, nama)
        elif node_id > node.id:
            node.right = self._insert_recursive(node.right, node_id, nama)
        else:
            # Update existing node with same ID
            node.nama = nama
            return node
        
        AVLTree.update_height(node)
        return self._balance_node(node, node_id)
    
    def _balance_node(self, node, node_id):
        """Apply balancing rotations"""
        balance = AVLTree.balance_factor(node)
        
        # Left Left Case
        if balance > 1 and node_id < node.left.id:
            return AVLTree.right_rotate(node)
        
        # Right Right Case
        if balance < -1 and node_id > node.right.id:
            return AVLTree.left_rotate(node)
        
        # Left Right Case
        if balance > 1 and node_id > node.left.id:
            node.left = AVLTree.left_rotate(node.left)
            return AVLTree.right_rotate(node)
        
        # Right Left Case
        if balance < -1 and node_id < node.right.id:
            node.right = AVLTree.right_rotate(node.right)
            return AVLTree.left_rotate(node)
        
        return node
    
    # ========================
    # SEARCH OPERATIONS
    # ========================
    
    def search_by_id(self, node_id):
        """Search node by ID"""
        current = self.root
        while current is not None:
            if node_id == current.id:
                return current
            elif node_id < current.id:
                current = current.left
            else:
                current = current.right
        return None
    
    def search_by_name(self, nama):
        """Search all nodes by name (case-insensitive)"""
        results = []
        self._search_by_name_recursive(self.root, nama, results)
        return results
    
    def _search_by_name_recursive(self, node, nama, results):
        """Recursive search by name"""
        if node is None:
            return
        
        if node.nama.lower() == nama.lower():
            results.append(node)
        
        self._search_by_name_recursive(node.left, nama, results)
        self._search_by_name_recursive(node.right, nama, results)
    
    # ========================
    # DELETE OPERATIONS
    # ========================
    
    def delete(self, node_id):
        """Delete node by ID"""
        self.root = self._delete_recursive(self.root, node_id)
    
    def _delete_recursive(self, node, node_id):
        """Recursive deletion with balancing"""
        if node is None:
            return None
        
        if node_id < node.id:
            node.left = self._delete_recursive(node.left, node_id)
        elif node_id > node.id:
            node.right = self._delete_recursive(node.right, node_id)
        else:
            # Node found
            if node.left is None or node.right is None:
                temp = node.left if node.left is not None else node.right
                node = temp
            else:
                # Two children: use inorder successor
                succ = AVLTree.min_value_node(node.right)
                node.id = succ.id
                node.nama = succ.nama
                node.right = self._delete_recursive(node.right, succ.id)
        
        if node is None:
            return None
        
        AVLTree.update_height(node)
        return self._balance_node_after_delete(node)
    
    def _balance_node_after_delete(self, node):
        """Apply balancing after deletion"""
        balance = AVLTree.balance_factor(node)
        
        # Left Left Case
        if balance > 1 and AVLTree.balance_factor(node.left) >= 0:
            return AVLTree.right_rotate(node)
        
        # Left Right Case
        if balance > 1 and AVLTree.balance_factor(node.left) < 0:
            node.left = AVLTree.left_rotate(node.left)
            return AVLTree.right_rotate(node)
        
        # Right Right Case
        if balance < -1 and AVLTree.balance_factor(node.right) <= 0:
            return AVLTree.left_rotate(node)
        
        # Right Left Case
        if balance < -1 and AVLTree.balance_factor(node.right) > 0:
            node.right = AVLTree.right_rotate(node.right)
            return AVLTree.left_rotate(node)
        
        return node
    
    # ========================
    # TRAVERSAL OPERATIONS
    # ========================
    
    def inorder(self):
        """Inorder traversal (Left, Root, Right)"""
        result = []
        self._inorder_recursive(self.root, result)
        return result
    
    def preorder(self):
        """Preorder traversal (Root, Left, Right)"""
        result = []
        self._preorder_recursive(self.root, result)
        return result
    
    def postorder(self):
        """Postorder traversal (Left, Right, Root)"""
        result = []
        self._postorder_recursive(self.root, result)
        return result
    
    def _inorder_recursive(self, node, result):
        """Recursive inorder traversal"""
        if node is None:
            return
        self._inorder_recursive(node.left, result)
        result.append(node)
        self._inorder_recursive(node.right, result)
    
    def _preorder_recursive(self, node, result):
        """Recursive preorder traversal"""
        if node is None:
            return
        result.append(node)
        self._preorder_recursive(node.left, result)
        self._preorder_recursive(node.right, result)
    
    def _postorder_recursive(self, node, result):
        """Recursive postorder traversal"""
        if node is None:
            return
        self._postorder_recursive(node.left, result)
        self._postorder_recursive(node.right, result)
        result.append(node)
    
    # ========================
    # POSITION UTILITIES
    # ========================
    
    def get_inorder_position_by_id(self, node_id):
        """Get inorder position by ID"""
        inorder_list = self.inorder()
        for i, node in enumerate(inorder_list):
            if node.id == node_id:
                return i + 1
        return -1
    
    def get_inorder_position_by_node(self, target_node):
        """Get inorder position by node object"""
        if target_node is None:
            return -1
        inorder_list = self.inorder()
        for i, node in enumerate(inorder_list):
            if node is target_node:
                return i + 1
        return -1
    
    # ========================
    # TREE INFO
    # ========================
    
    def is_empty(self):
        """Check if tree is empty"""
        return self.root is None
    
    def get_size(self):
        """Get total number of nodes"""
        return self._count_nodes(self.root)
    
    def _count_nodes(self, node):
        """Recursively count nodes"""
        if node is None:
            return 0
        return 1 + self._count_nodes(node.left) + self._count_nodes(node.right)
    
    def clear(self):
        """Clear entire tree"""
        self.root = None

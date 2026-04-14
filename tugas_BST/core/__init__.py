"""
AVL Tree Package
Core modules for data structure and web UI
"""

from .avl_tree import AVLTree, Node
from .data_manager import DataManager
from .tree_visualizer import TreeVisualizer
from .cli import AVLTreeCLI

__all__ = ['AVLTree', 'Node', 'DataManager', 'TreeVisualizer', 'AVLTreeCLI']
__version__ = '1.0.0'

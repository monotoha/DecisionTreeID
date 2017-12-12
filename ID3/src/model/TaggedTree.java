package model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * TaggedTree represents a generic tree with labeled edges.
 * These labels are String class by definition.
 * 
 * @author MariotoA
 *
 * @param <T> The generic class of the node.
 */
public class TaggedTree <T> {
	private Node<T> node;
	
	/**
	 * It creates an empty TaggedTree
	 */
	public TaggedTree() {
		node=null;
	}
	/**
	 * It creates a TaggedTree with only a leaf node.
	 * @param root. The leaf node value.
	 */
	public TaggedTree(T root) {
		this(root,null);
	}
	/**
	 * It creates a TaggedTree using a root node value and a 
	 * Map containing every subtree tag (key) and the subtrees themselves (value).
	 * @param root. Root node value.
	 * @param mapEdgeSubTree. Map(Tag -> SubTaggedTree)
	 */
	public TaggedTree(T root, Map<String,TaggedTree<T>> mapEdgeSubTree) {
		node = new Node<>();
		node.root = root;
		node.mapEdgeSubTree = mapEdgeSubTree != null && !mapEdgeSubTree.isEmpty()?
				mapEdgeSubTree : null;
		node.setHeight();
		node.setDepth(0);
	}
	
	/**
	 * 
	 * @return Is the tree empty?
	 */
	public boolean isEmpty() {
		return node == null;
	}
	/**
	 * 
	 * @return Is tree root a leaf node?
	 */
	public boolean isLeafNode() {
		return node != null && this.node.isLeaf();
	}
	
	/**
	 * Raise RuntimeException if Tree is empty
	 * @return Value of the node.
	 */
	public T getNodeValue() throws RuntimeException {
		if (isEmpty()) {
			throw new RuntimeException("Empty node does not have value.");
		}
		return this.node.root;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("Tree(").append(this.node.toString()).append(")").toString();
	}
	
	/**
	 * Raise RuntimeException if Tree is empty or leaf node.
	 * Should be used to iterate over tree.
	 * @return tree graph edges.
	 */
	public Map<String,TaggedTree<T>> getEdgesAssociatedToSubTrees() 
			throws RuntimeException {
		if (isEmpty() || node.isLeaf()) {
			throw new RuntimeException("Empty node or Leaf node");
		}
		return this.node.mapEdgeSubTree;
	}
	
	/**
	 * 
	 * @return Max of number of levels under current node + 1. 
	 */
	public int getNodeHeight() {
		if (isEmpty()) {
			throw new RuntimeException("Empty node does not have depth.");
		}
		return this.node.height;
	}
	
	/**
	 * 
	 * @return Depth of current node. 0 if root.
	 */
	public int getNodeDepth() {
		if (isEmpty()) {
			throw new RuntimeException("Empty node does not have depth.");
		}
		return this.node.depth;
	}
	
	public boolean equals(Object o) {
		boolean res = o instanceof TaggedTree<?>;
		if (res) {
			TaggedTree<T> t = (TaggedTree<T>) o;
			res = t.isEmpty() && this.isEmpty() || this.node.equals(t.node);
		}
		return res;
	}
	
	
	private static class Node<B> {
		B root;
		Map<String,TaggedTree<B>> mapEdgeSubTree;
		int height,depth;
		
		public boolean isLeaf() {
			return mapEdgeSubTree==null;
		}
		
		public void setHeight() {
			height = this.mapEdgeSubTree==null?
					0:
					this.mapEdgeSubTree.values().stream()
					.map(i -> i.node.height).mapToInt(Integer::intValue)
					.max().getAsInt() + 1;
		}
		
		public void setDepth(int val) {
			depth = val;
			if (this.mapEdgeSubTree!=null) {
				this.mapEdgeSubTree.values().forEach(i-> i.node.setDepth(val+1));
			}
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			return sb.append(root).append(", ").append(mapEdgeSubTree).toString();
		}
		public boolean equals(Object o) {
			boolean res = o instanceof Node<?>;
			if (res) {
				Node<B> node = (Node<B>) o;
				res = this.isLeaf() && node.isLeaf()
								||
								!this.isLeaf() && !node.isLeaf() 
								&& this.mapEdgeSubTree.equals(node.mapEdgeSubTree);
				res = res && this.root.equals(node.root);
			}
			return res;
		}
	}
	
}
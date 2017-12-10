package model;


import java.util.Map;
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
	
	private static class Node<B> {
		B root;
		Map<String,TaggedTree<B>> mapEdgeSubTree;
		
		public boolean isLeaf() {
			return mapEdgeSubTree==null;
		}
	}
	
}
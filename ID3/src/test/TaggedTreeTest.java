package test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import model.TaggedTree;

public class TaggedTreeTest {

	@Test
	public void testIsEmpty() {
		TaggedTree<String> t1 = new TaggedTree<>();
		assertTrue(t1.isEmpty());
	}

	@Test
	public void testIsLeafWithoutMap() {
		TaggedTree<String> t1 = new TaggedTree<>("Leaf");
		assertTrue(t1.isLeafNode());
	}
	
	@Test
	public void testIsLeafWithEmptyMap() {
		TaggedTree<String> t1 = 
				new TaggedTree<>("Leaf",new HashMap<String,TaggedTree<String>>());
		assertTrue(t1.isLeafNode());
	}

	@Test
	public void testGetTag() {
		String tag = "Leaf";
		TaggedTree<String> t1 = new TaggedTree<>(tag);
		assertEquals(t1.getNodeValue(),tag);
	}
	@Test(expected = RuntimeException.class)
	public void testGetTagWhenEmptyTree() {
		String tag = "Leaf";
		TaggedTree<String> t1 = new TaggedTree<>();
		assertEquals(t1.getNodeValue(),tag);
	}
	@Test
	public void testGetEdgesAssociatedToSubTrees() {
		TaggedTree<String> leaf = new TaggedTree<>("Leaf");
		Map<String,TaggedTree<String>> map = new HashMap<>();
		map.put("1", leaf);
		TaggedTree<String> t1 = 
				new TaggedTree<>("A",map);
		assertEquals(t1.getEdgesAssociatedToSubTrees(),map);
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetEdgesAssociatedToSubTreesShouldRaiseExceptionIfEmptyTree() {
		TaggedTree<String> empty = new TaggedTree<>();
		empty.getEdgesAssociatedToSubTrees();
	}
	
	@Test(expected = RuntimeException.class)
	public void testGetEdgesAssociatedToSubTreesShouldRaiseExceptionIfRootIsLeaf() {
		TaggedTree<String> leaf = new TaggedTree<>("TREE");
		leaf.getEdgesAssociatedToSubTrees();
	}
	
	@Test(expected = RuntimeException.class)
	public void testNodeHeightShouldRaiseAnExceptionIfEmptyTree() {
		TaggedTree<String> emp = new TaggedTree<>();
		emp.getNodeHeight();
	}
	@Test
	public void testNodeHeightLeafNodeShouldBeZero() {
		TaggedTree<String> leaf = new TaggedTree<>("TREE");
		assertEquals(0,leaf.getNodeHeight());
	}
	
	@Test
	public void testNodeHeightTwoLevelsTreeShouldBeOne() {
		TaggedTree<String> leaf = new TaggedTree<>("Leaf");
		Map<String,TaggedTree<String>> map = new HashMap<>();
		map.put("1", leaf);
		TaggedTree<String> t1 = 
				new TaggedTree<>("A",map);
		assertEquals(1,t1.getNodeHeight());
	}
	
	@Test(expected = RuntimeException.class)
	public void testNodeDepthShouldRaiseAnExceptionIfEmptyTree() {
		TaggedTree<String> emp = new TaggedTree<>();
		emp.getNodeDepth();
	}
	@Test
	public void testNodeDepthLeafNodeShouldBeZero() {
		TaggedTree<String> leaf = new TaggedTree<>("TREE");
		assertEquals(0,leaf.getNodeDepth());
	}
	
	@Test
	public void testNodeHeightTwoLevelsTreeShouldBeZero() {
		TaggedTree<String> leaf = new TaggedTree<>("Leaf");
		Map<String,TaggedTree<String>> map = new HashMap<>();
		map.put("1", leaf);
		TaggedTree<String> t1 = 
				new TaggedTree<>("A",map);
		assertEquals(0,t1.getNodeDepth());
	}
}

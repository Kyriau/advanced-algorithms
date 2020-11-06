import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VEBTreeTest {
	
	@Test
	public void testConstructorExceptions() {
		
		assertThrows(IllegalArgumentException.class, () -> new VEBTree(0));
		assertThrows(IllegalArgumentException.class, () -> new VEBTree(-21));
		
		VEBTree tree = new VEBTree(1);
		tree.insert(0);
		assertTrue(tree.contains(0));
		assertFalse(tree.contains(1));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(1));
		
	}
	
	@Test
	public void testInsertionExceptions() {
		
		VEBTree tree = new VEBTree(64);
		
		assertThrows(IllegalArgumentException.class, () -> tree.insert(64));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(222));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(-12));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(-1));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(Integer.MAX_VALUE));
		assertThrows(IllegalArgumentException.class, () -> tree.insert(Integer.MIN_VALUE));
		
		tree.insert(5);
		assertThrows(IllegalArgumentException.class, () -> tree.insert(5));
		
	}
	
	@Test
	public void testInsertionSuccessive() {
		
		VEBTree tree = new VEBTree(64);
		
		for(int i = 0; i < 16; i++) {
			tree.insert(4 * i);
		}
		for(int i = 0; i < 16; i++) {
			assertTrue(tree.contains(4 * i));
		}
		
	}
	
	@Test
	public void testInsertionReverse() {
		
		VEBTree tree = new VEBTree(64);
		
		for(int i = 15; i >= 0; i--) {
			tree.insert(4 * i);
		}
		for(int i = 15; i >= 0; i--) {
			assertTrue(tree.contains(4 * i));
		}
		
	}
	
	@Test
	public void testInsertionDense() {
		
		VEBTree tree = new VEBTree(32);
		
		for(int i = 0; i < 16; i++) {
			tree.insert(i);
		}
		for(int i = 0; i < 16; i++) {
			assertTrue(tree.contains(i));
		}
		
		for(int i = 31; i >= 16; i--) {
			tree.insert(i);
		}
		for(int i = 31; i >= 16; i--) {
			assertTrue(tree.contains(i));
		}
		
	}
	
	@Test
	public void testDeletionSimple() {
	
		VEBTree tree = new VEBTree(45);
		
		tree.insert(12);
		assertTrue(tree.contains(12));
		tree.delete(12);
		assertFalse(tree.contains(12));
		
		tree.insert(12);
		tree.insert(14);
		assertTrue(tree.contains(12));
		assertFalse(tree.contains(13));
		assertTrue(tree.contains(14));
		
		assertThrows(IllegalArgumentException.class, () -> tree.delete(16));
	
	}
	
	@Test
	public void testDeletionSuccessive() {
		
		VEBTree tree = new VEBTree(64);
		
		for(int i = 0; i < 16; i++) {
			tree.insert(4 * i);
		}
		for(int i = 0; i < 16; i++) {
			tree.delete(4 * i);
			assertFalse(tree.contains(4 * i));
		}
	
	}
	
	@Test
	public void testDeletionReverse() {
		
		VEBTree tree = new VEBTree(64);
		
		for(int i = 15; i >= 0; i--) {
			tree.insert(4 * i);
		}
		for(int i = 15; i >= 0; i--) {
			tree.delete(4 * i);
			assertFalse(tree.contains(4 * i));
		}
		
	}
	
	@Test
	public void testPredecessorSort() {
	
	}
	
	@Test
	public void testSuccessorSort() {
	
	}

}
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
		
		VEBTree tree1 = new VEBTree(64);
		
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(64));
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(222));
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(-12));
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(-1));
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(Integer.MAX_VALUE));
		assertThrows(IllegalArgumentException.class, () -> tree1.insert(Integer.MIN_VALUE));
		
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
	public void testDeletion() {
	
	
	
	}

}
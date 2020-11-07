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
	public void testPredecessor() {
		
		VEBTree tree = new VEBTree(100);
		
		assertNull(tree.predecessor(4));
		
		int[] values = {60, 7, 70, 39, 18, 6, 9, 29, 53, 25};
		for(int i = 0; i < values.length; i++) {
			tree.insert(values[i]);
		}
		
		assertNull(tree.predecessor(-4));
		assertNull(tree.predecessor(0));
		assertNull(tree.predecessor(4));
		assertEquals(7, tree.predecessor(9));
		assertEquals(9, tree.predecessor(10));
		assertEquals(29, tree.predecessor(39));
		assertEquals(70, tree.predecessor(99));
		assertEquals(70, tree.predecessor(100));
		assertEquals(70, tree.predecessor(120));
		
	}
	
	@Test
	public void testPredecessorSort() {
		
		VEBTree tree = new VEBTree(80);
		int[] expected = new int[80];
		for(int i = 0; i < 80; i++) {
			expected[i] = i;
		}
		int[] unsorted = {27, 28, 51, 63, 66, 49, 60, 7, 70, 39, 18, 6, 9, 29, 53, 25, 48, 16, 24, 45, 57, 46, 59, 10, 47, 5, 15, 68, 17, 21, 37, 69, 50, 71, 61, 8, 35, 20, 36, 72, 65, 23, 34, 30, 73, 11, 62, 74, 32, 1, 44, 67, 75, 58, 76, 77, 0, 54, 78, 79, 13, 19, 12, 26, 3, 31, 41, 43, 52, 4, 38, 14, 33, 55, 22, 56, 42, 64, 2, 40};
		
		for(int i = 0; i < unsorted.length; i++) {
			tree.insert(unsorted[i]);
		}
		
		Integer x = Integer.MAX_VALUE;
		for(int i = 0; i < 80; i++) {
			x = tree.predecessor(x);
			assertEquals(expected[79 - i], x);
		}
		
	}
	
	@Test
	public void testSuccessorSort() {
		
		VEBTree tree = new VEBTree(80);
		int[] expected = new int[80];
		for(int i = 0; i < 80; i++) {
			expected[i] = i;
		}
		int[] unsorted = {23, 37, 63, 21, 33, 39, 50, 65, 71, 73, 18, 35, 26, 74, 75, 6, 1, 5, 41, 43, 47, 52, 76, 61, 77, 78, 59, 79, 24, 13, 28, 36, 54, 40, 66, 68, 27, 72, 17, 2, 11, 3, 12, 44, 8, 30, 45, 14, 10, 0, 38, 16, 29, 46, 7, 15, 48, 51, 25, 55, 9, 32, 57, 20, 22, 34, 56, 49, 42, 4, 58, 60, 62, 64, 53, 19, 67, 31, 69, 70};
		
		for(int i = 0; i < unsorted.length; i++) {
			tree.insert(unsorted[i]);
		}
		
		Integer x = -1;
		for(int i = 0; i < 80; i++) {
			x = tree.successor(x);
			assertEquals(expected[i], x);
		}
		
	}
	
	@Test
	public void testSuccessor() {
		
		VEBTree tree = new VEBTree(100);
		
		assertNull(tree.successor(4));
		
		int[] values = {60, 7, 70, 39, 18, 6, 9, 29, 53, 25};
		for(int i = 0; i < values.length; i++) {
			tree.insert(values[i]);
		}
		
		assertEquals(6, tree.successor(-4));
		assertEquals(6, tree.successor(0));
		assertEquals(6, tree.successor(4));
		assertEquals(18, tree.successor(9));
		assertEquals(18, tree.successor(10));
		assertEquals(53, tree.successor(39));
		assertNull(tree.successor(99));
		assertNull(tree.successor(100));
		assertNull(tree.successor(120));
		
	}

}
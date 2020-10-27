import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VEBTreeTest {
	
	@Test
	public void testInsertion() {
		
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
	public void testInsertionComplete() {
		
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

}
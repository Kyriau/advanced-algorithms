import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SplayTreeTest {
	
	@Test
	public void testSize() {
		
		SplayTree<Integer> tree = new SplayTree<>();
		
		assertEquals(0, tree.size());
		
		tree.insert(1);
		assertEquals(1, tree.size());
		
		tree.insert(4);
		assertEquals(2, tree.size());
		
		tree.insert(10);
		tree.insert(11);
		assertEquals(4, tree.size());
		
		tree.insert(11);
		assertEquals(4, tree.size());
		
		tree.delete(4);
		assertEquals(3, tree.size());
		tree.delete(4);
		assertEquals(3, tree.size());
		
		tree.delete(11);
		tree.delete(1);
		tree.delete(10);
		assertEquals(0, tree.size());
		
	}
	
}
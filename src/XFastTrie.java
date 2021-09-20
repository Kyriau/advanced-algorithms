import java.util.Hashtable;

public class XFastTrie {
	
	private Hashtable<Integer, IntegerNode> onesTrie;
	
	public XFastTrie(int size) {
		
		if(size > Integer.MAX_VALUE >> 1 || size <= 0)
			throw new IllegalArgumentException("Cannot create x-fast trie of size " + size + ".");
		
		onesTrie = new Hashtable<>(size << 1);
		
	}
	
	public boolean contains(int value) {
		return onesTrie.containsKey(value);
	}
	
	public int predecessor(int value) {
		
		if(onesTrie.containsKey(value))
			return onesTrie.get(value).predecessor.value;
		
		
			
	}
	
	public int successor(int value) {
		
		if(onesTrie.containsKey(value))
			return onesTrie.get(value).successor.value;
		
		
		
	}
	
	public void insert(int value) {
	
	}
	
	public void delete(int value) {
	
	}
	
	private int ancestor(int index, int k) {
		return index >> k;
	}
	
	private int leftChild(int index) {
		return index << 1;
	}
	
	private int rightChild(int index) {
		return (index << 1) + 1;
	}
	
	private static class IntegerNode {
		int value;
		IntegerNode predecessor;
		IntegerNode successor;
		public boolean equals(Object other) {
			return other.equals(value);
		}
	}
	
}
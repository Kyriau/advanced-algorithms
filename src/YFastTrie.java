public class YFastTrie {
	
	boolean[] trie;
	
	public YFastTrie(int size) {
		trie = new boolean[size << 1];
	}
	
	public boolean contains(int value) {
	
	}
	
	public int predecessor(int value) {
	
	}
	
	public int successor(int value) {
	
	}
	
	public void insert(int value) {
	
	}
	
	private int getLeaf(int value) {
	
	}
	
	private int ancestor(int index, int n) {
		return index >> n;
	}
	
	private int leftChild(int index) {
		return index << 1;
	}
	
	private int rightChild(int index) {
		return (index << 1) + 1;
	}
	
}
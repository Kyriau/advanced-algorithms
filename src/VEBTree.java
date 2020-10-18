public class VEBTree {
	
	private int u;
	private int w;
	private int size;
	private int min;
	private int max;
	private VEBTree summary;
	private VEBTree[] cluster;
	
	public VEBTree(int w) {
		u = 0b1 << w;
		this.w = w;
		size = 0;
		min = Integer.MAX_VALUE;
		max = 0;
	}
	
	public int predecessor(int x) {
		if(x > max)
			return max;
		if(cluster[c(x)].min < x)
			return cluster[c(x)].predecessor(i(x));
		int c2 = summary.predecessor(c(x));
		return cluster[c2].max;
	}
	
	public void insert(int x) {
		if(size == 0) {
			min = x;
			max = x;
			size++;
			return;
		}
		if(x < min) {
			int swap = min;
			min = x;
			x = swap;
		}
		if(x > max)
			max = x;
		if(cluster[c(x)].min == 0)
			summary.insert(c(x));
		cluster[c(x)].insert(i(x));
	}
	
	private int c(int x) {
		return x >> (w >> 1);
	}
	
	private int i(int x) {
		return x & (0xFFFFFFFF >> (w >> 1));
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.toBinaryString(0b0101 >> 2));
	}

}
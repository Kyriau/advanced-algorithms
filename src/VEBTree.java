import java.util.Hashtable;

public class VEBTree {
	
	private int w;
	
	private Integer min;
	private Integer max;
	
	private VEBTree summary;
	private Hashtable<Integer, VEBTree> cluster;
	
	/**
	 * Construct a new VEBTree that represents a set within a universe of size at least u.
	 * @param u The universe size for this VEBTree.
	 */
	public VEBTree(int u) {
		
		if(u <= 0)
			throw new IllegalArgumentException("VEBTree universe must be of at least size 1.");
		
		w = 32 - Integer.numberOfLeadingZeros(u - 1); // ceil(log2(u))
		
		if(w > 1)
			cluster = new Hashtable<>(w);
		
	}
	
	/**
	 * Determine whether a given int x is a member of the set represented by the VEBTree.
	 * @param x The queried integer.
	 * @return Whether x is in the set or not.
	 */
	public boolean member(int x) {
		
		if(min == null)
			return false;
		if(x == min || x == max)
			return true;
		if(w == 1)
			return false;
		
		int c = high(x);
		int i = low(x);
		if(cluster.contains(c))
			return cluster.get(c).member(i);
		return false;
		
	}
	
	public Integer predecessor(int x) {
		
		if(min == null)
			return null;
		if(w == 1)
			return x == 1 && min == 0 ? 0 : null;
		if(x > max)
			return max;
		
		int c = high(x);
		int i = low(x);
		
		Integer minLow = cluster.contains(c) ? cluster.get(c).min : null;
		if(minLow != null && i > minLow)
			return append(c, cluster.get(c).predecessor(i));
		
		Integer prevCluster = summary != null ? summary.predecessor(c) : null;
		if(prevCluster == null)
			return x > min ? min : null;
		
		return append(prevCluster, cluster.get(prevCluster).max);
		
	}
	
	public Integer successor(int x) {
		
		if(min == null)
			return null;
		if(w == 1)
			return x == 0 && max == 1 ? 1 : null;
		if(x < min)
			return min;
		
		int c = high(x);
		int i = low(x);
		
		Integer maxLow = cluster.contains(c) ? cluster.get(c).max : null;
		if(maxLow != null && i < maxLow)
			return append(c, cluster.get(c).successor(i));
		
		Integer nextCluster = summary != null ? summary.successor(c) : null;
		return nextCluster == null ? null : append(nextCluster, cluster.get(nextCluster).min);
		
	}
	
	public void insert(int x) {
		
		System.out.println("Inserting " + x + " into tree with w = " + w);
		
		if(min == null) {
			min = x;
			max = x;
			return;
		}
		
		if(x < min) {
			int temp = x;
			x = min;
			min = temp;
		}
		
		if(x > max)
			max = x;
		
		int c = high(x);
		int i = low(x);
		
		if(!cluster.contains(c)) {
			cluster.put(c, new VEBTree(1 << (w >> 1)));
			if (summary == null)
				summary = new VEBTree(1 << (w >> 1));
			summary.insert(c);
		}
		
		cluster.get(c).insert(i);
		
	}
	
	public void delete(int x) {
	
		if(x == min && x == max) {
			min = null;
			max = null;
			return;
		}
		
		if(x == min) {
			x = append(summary.min, cluster.get(summary.min).min);
			min = x;
		}
		
		int c = high(x);
		int i = low(x);
		
		// This is the condition in which we deleted an element which does not exist.
		if(cluster.get(c) == null)
			return;
		
		cluster.get(c).delete(i);
		if(cluster.get(c).min == null) {
			cluster.remove(c);
			summary.delete(c);
		}
		
		if(x == max) {
			if(summary.min == null) {
				summary = null;
				max = min;
			} else
				max = append(summary.max, cluster.get(summary.max).max);
		}
		
	}
	
	private int high(int x) {
		return x >> (w >> 1);
	}
	
	private int low(int x) {
		return x & ((1 << (w >> 1)) - 1);
	}
	
	private int append(int high, int low) {
		return high << (w >> 1) | low;
	}

}
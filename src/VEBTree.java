import java.util.Hashtable;
import java.util.Random;

public class VEBTree {
	
	private int u, w;
	
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
			throw new IllegalArgumentException("Universe size must be at least 1.");
		
		this.u = u;
		w = 32 - Integer.numberOfLeadingZeros(u - 1); // ceil(log2(u))
		
		if(w > 0)
			cluster = new Hashtable<>(w);
		
	}
	
	/**
	 * Determine whether a given int x is a member of the set.
	 * @param x The queried integer.
	 * @return Whether x is in the set or not.
	 */
	public boolean contains(int x) {
		
		if(min == null)
			return false;
		if(x == min || x == max)
			return true;
		if(w == 0)
			return false;
		
		int c = high(x);
		int i = low(x);
		if(cluster.containsKey(c))
			return cluster.get(c).contains(i);
		return false;
		
	}
	
	/**
	 * Determine the preceding element of the set relative to a given value.
	 * @param x The value whose predecessor is to be returned.
	 * @return The element contained in the set which precedes x.
	 */
	public Integer predecessor(int x) {
		
		if(min == null)
			return null;
		if(w == 1)
			return x == 1 && min == 0 ? 0 : null;
		if(x > max)
			return max;
		
		int c = high(x);
		int i = low(x);
		
		Integer minLow = cluster.containsKey(c) ? cluster.get(c).min : null;
		if(minLow != null && i > minLow)
			return append(c, cluster.get(c).predecessor(i));
		
		Integer prevCluster = summary != null ? summary.predecessor(c) : null;
		if(prevCluster == null)
			return x > min ? min : null;
		
		return append(prevCluster, cluster.get(prevCluster).max);
		
	}
	
	/**
	 * Determine the succeeding element of the set relative to a given value.
	 * @param x The value whose successor is to be returned.
	 * @return The element contained in the set which succeeds x.
	 */
	public Integer successor(int x) {
		
		if(min == null)
			return null;
		if(w == 1)
			return x == 0 && max == 1 ? 1 : null;
		if(x < min)
			return min;
		
		int c = high(x);
		int i = low(x);
		
		Integer maxLow = cluster.containsKey(c) ? cluster.get(c).max : null;
		if(maxLow != null && i < maxLow)
			return append(c, cluster.get(c).successor(i));
		
		Integer nextCluster = summary != null ? summary.successor(c) : null;
		return nextCluster == null ? null : append(nextCluster, cluster.get(nextCluster).min);
		
	}
	
	public void insert(int x) {
		
		if(x >= u || x < 0)
			throw new IllegalArgumentException("Values inserted must be within [0, " + u + ").");
		
		if(min == null) {
			min = x;
			max = x;
			return;
		}
		
		if(x == min)
			throw new IllegalArgumentException("The value " + x + " was already part of the VEBTree and so could not be inserted.");
		
		if(x < min) {
			int temp = x;
			x = min;
			min = temp;
		}
		
		if(x > max)
			max = x;
		
		int c = high(x);
		int i = low(x);
		
		if(!cluster.containsKey(c)) {
			cluster.put(c, new VEBTree(1 << ((w + 1) >> 1)));
			if(summary == null)
				summary = new VEBTree(1 << ((w + 1) >> 1));
			if(!summary.contains(c))
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
		
		if(cluster.get(c) == null)
			throw new IllegalArgumentException("The value " + x + " was not part of the VEBTree and so could not be deleted.");
		
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
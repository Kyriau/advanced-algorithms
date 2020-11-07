import java.util.Hashtable;
import java.util.Random;

public class VEBTree {
	
	/**
	 * The universe size of the VEBTree. All values stored must be within [0, U).
	 */
	private int u;
	
	/**
	 * This value is equal to ceil(log2(U)). It is more convenient to use in internal operations than U.
	 */
	private int w;
	
	/**
	 * The minimum value stored in the VEBTree. It is unique in that this is the only place in the tree where this value is stored.
	 */
	private Integer min;
	
	/**
	 * The maximum value stored in the VEBTree. It is stored only to improve computational time of the successor function.
	 */
	private Integer max;
	
	/**
	 * This subtree keeps track of which entries of the hashtable are nonempty.
	 */
	private VEBTree summary;
	
	/**
	 * This hashtable of subtrees stores values which are within the set. The high-order bits become the key for the hashtable, and the low-order bits become the value stored.
	 */
	private Hashtable<Integer, VEBTree> cluster;
	
	/**
	 * Construct a new VEBTree that represents a set within a universe of size at least u.
	 * @param u The universe size for this VEBTree.
	 */
	public VEBTree(int u) {
		
		if(u <= 0)
			throw new IllegalArgumentException("Universe size must be at least 1.");
		
		this.u = u;
		
		// ceil(log2(u)), see documentation for numberOfLeadingZeros
		w = 32 - Integer.numberOfLeadingZeros(u - 1);
		
		// If w = 0, we don't need any more storage than just min.
		// Using w for the initial size of the hashtable is a wild guess.
		if(w > 0)
			cluster = new Hashtable<>(w);
		
	}
	
	/**
	 * Get the universe size of this VEBTree, defined at construction.
	 * @return The universe size U.
	 */
	public int getU() {
		return u;
	}
	
	/**
	 * Determine whether a given int x is a member of the set.
	 * @param x The queried integer.
	 * @return Whether x is in the set or not.
	 */
	public boolean contains(int x) {
		
		// If min is null, then the set is empty.
		if(min == null)
			return false;
		
		if(x == min || x == max)
			return true;
		if(w == 0)
			return false;
		
		int c = high(x);
		int i = low(x);
		
		// If the cluster has the relevant values, then we need to recurse.
		if(cluster.containsKey(c))
			return cluster.get(c).contains(i);
		
		// Otherwise, we definitely don't contain the value.
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
	
	/**
	 * Insert a value into the set represented by the VEBTree.
	 * @param x The new value to insert.
	 */
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
	
	/**
	 * Remove a value from the set represented by the VEBTree.
	 * @param x The value to remove.
	 */
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
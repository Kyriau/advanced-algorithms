public class SplayTree<E extends Comparable<E>> {
	
	private int size;
	private Node<E> root;
	
	public SplayTree() {
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean contains(E element) {
		Node<E> n = find(element);
		if(n != null)
			splay(n);
		return find(element) != null;
	}
	
	public boolean insert(E element) {
		
		if(root == null) {
			root = new Node<>();
			root.element = element;
			size = 1;
			return true;
		}
		
		Node<E> n1 = root;
		Node<E> n2 = null;
		
		while(n1 != null) {
			n2 = n1;
			int compare = element.compareTo(n1.element);
			if(compare > 0)
				n1 = n1.right;
			else if(compare < 0)
				n1 = n1.left;
			else
				return false;
		}
		
		n1 = new Node<>();
		n1.element = element;
		n1.parent = n2;
		
		int compare = element.compareTo(n2.element);
		if(compare > 0)
			n2.right = n1;
		else
			n2.left = n1;
		
		splay(n1);
		size++;
		return true;
		
	}
	
	public boolean delete(E element) {
		
		Node<E> n1 = find(element);
		if(n1 == null)
			return false;
		
		splay(n1);
		
		Node<E> n2 = n1.right;
		n1 = n1.left;
		
		Node<E> max = null;
		if(n1 != null) {
			n1.parent = null;
			max = maxLeaf(n1);
			splay(max);
			root = max;
		}
		if(n2 != null) {
			if(n1 != null)
				max.right = n2;
			else
				root = n2;
			n2.parent = max;
		}
		
		size--;
		return true;
		
	}
	
	public E minimum() {
		return minLeaf(root).element;
	}
	
	public E maximum() {
		return maxLeaf(root).element;
	}
	
	private Node<E> find(E element) {
		
		Node<E> n = root;
		while(n != null) {
			int compare = element.compareTo(n.element);
			if(compare > 0)
				n = n.right;
			else if(compare < 0)
				n = n.left;
			else
				return n;
		}
		return null;
		
	}
	
	private Node<E> minLeaf(Node<E> n) {
		while(n.left != null) {
			n = n.left;
		}
		return n;
	}
	
	private Node<E> maxLeaf(Node<E> n) {
		while(n.right != null) {
			n = n.right;
		}
		return n;
	}
	
	private void splay(Node<E> n) {
		
		while(n.parent != null) {
			
			if(n.parent.parent == null) {
				
				if(n == n.parent.left)
					rightRotate(n.parent);
				else
					leftRotate(n.parent);
				
			} else if(n == n.parent.left && n.parent.parent.left == n.parent) {
				rightRotate(n.parent.parent);
				rightRotate(n.parent);
			} else if(n == n.parent.right && n.parent.parent.right == n.parent) {
				leftRotate(n.parent.parent);
				leftRotate(n.parent);
			} else if(n == n.parent.left && n.parent.parent.right == n.parent) {
				rightRotate(n.parent);
				leftRotate(n.parent);
			} else {
				leftRotate(n.parent);
				rightRotate(n.parent);
			}
			
		}
		
	}
	
	private void leftRotate(Node<E> n1) {
		
		Node<E> n2 = n1.right;
		
		if(n2 != null) {
			n1.right = n2.left;
			if(n2.left != null)
				n2.left.parent = n1;
			n2.parent = n1.parent;
		}
		
		if(n1.parent == null)
			root = n2;
		else if(n1 == n1.parent.left)
			n1.parent.left = n2;
		else
			n1.parent.right = n2;
		
		if(n2 != null)
			n2.left = n1;
		
		n1.parent = n2;
	}
	
	private void rightRotate(Node<E> n1) {
		
		Node<E> n2 = n1.left;
		
		if(n2 != null) {
			n1.left = n2.right;
			if(n2.right != null)
				n2.right.parent = n1;
			n2.parent = n1.parent;
		}
		
		if(n1.parent == null)
			root = n2;
		else if(n1 == n1.parent.left)
			n1.parent.left = n2;
		else
			n1.parent.right = n2;
		
		if(n2 != null)
			n2.right = n1;
		
		n1.parent = n2;
		
	}
	
	private static class Node<E> {
		Node<E> parent;
		Node<E> left;
		Node<E> right;
		E element;
	}
	
}
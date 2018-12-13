
/**
 * BST.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */

import java.util.NoSuchElementException;

public class BST<T extends Comparable<T>> {
	private class Node {
		private T data;
		private Node left;
		private Node right;

		public Node(T data) {
			this.data = data;
			left = null;
			right = null;
		}
	}

	private Node root;

	/*** CONSTRUCTORS ***/

	/**
	 * Default constructor for BST sets root to null
	 */
	public BST() {
		root = null;
	}

//	/**
//	 * Copy constructor for BST
//	 * 
//	 * @param bst the BST to make a copy of
//	 */
//	public BST(final BST<T> bst) {
//		if (bst.getSize() == 0) {
//			root = null;
//		}
//
//		else {
//			copyHelper(bst.root);
//		}
//	}
//
//	/**
//	 * Helper method for copy constructor
//	 * 
//	 * @param node the node containing data to copy
//	 */
//	private void copyHelper(Node node) {
//
//		if (node == null) {
//			return;
//		}
//
//		else {
//			insert(node.data);
//			copyHelper(node.left);
//			copyHelper(node.right);
//		}
//
//	}

	/*** ACCESSORS ***/

	/**
	 * Returns the data stored in the root
	 * 
	 * @precondition !isEmpty()
	 * @return the data stored in the root
	 * @throws NoSuchElementException when preconditon is violated
	 */
	public T getRoot() throws NoSuchElementException {

		if (isEmpty()) {
			throw new NoSuchElementException("getRoot: Tree is Empty. No data to access!\n");
		}

		return root.data;
	}

	/**
	 * Determines whether the tree is empty
	 * 
	 * @return whether the tree is empty
	 */
	public boolean isEmpty() {
		return getSize() == 0;
	}

	/**
	 * Returns the current size of the tree (number of nodes)
	 * 
	 * @return the size of the tree
	 */
	public int getSize() {
		return getSize(root);
	}

	/**
	 * Helper method for the getSize method
	 * 
	 * @param node the current node to count
	 * @return the size of the tree
	 */
	private int getSize(Node node) {
		if (node == null) {
			return 0;
		}

		else {
			return 1 + getSize(node.left) + getSize(node.right);
		}
	}

	/**
	 * Returns the height of tree by counting edges.
	 * 
	 * @return the height of the tree
	 */
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * Helper method for getHeight method
	 * 
	 * @param node the current node whose height to count
	 * @return the height of the tree
	 */
	private int getHeight(Node node) {
		if (node == null) {
			return -1;
		} else {
			return 1 + Math.max(getHeight(node.left), getHeight(node.right));
		}
	}

	/**
	 * Returns the smallest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the smallest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMin() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMin: Tree is Empty. No data to access!\n");
		}

		return findMin(root);
	}

	/**
	 * Helper method to findMin method
	 * 
	 * @param node the current node to check if it is the smallest
	 * @return the smallest value in the tree
	 */
	private T findMin(Node node) {
		if (node.left == null) {
			return node.data;
		}
		return findMin(node.left);
	}

	/**
	 * Returns the largest value in the tree
	 * 
	 * @precondition !isEmpty()
	 * @return the largest value in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public T findMax() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("findMax: Tree is Empty. No data to access!\n");
		}

		return findMax(root);
	}

	/**
	 * Helper method to findMax method
	 * 
	 * @param node the current node to check if it is the largest
	 * @return the largest value in the tree
	 */
	private T findMax(Node node) {
		if (node.right == null) {
			return node.data;
		}
		return findMax(node.right);
	}

	/**
	 * Searches for a specified value in the tree
	 * 
	 * @param data the value to search for
	 * @return whether the value is stored in the tree
	 */
	public boolean search(T data) {

		return search(data, root);
	}

	/**
	 * Helper method for the search method
	 * 
	 * @param data the data to search for
	 * @param node the current node to check
	 * @return whether the data is stored in the tree
	 */
	private boolean search(T data, Node node) {

		if (node == null) {
			return false;
		}

		if (data.compareTo(node.data) == 0) { // Do NOT use "node.data.equals(data)". compareTo is for sorting. equals is for equivalence check
			return true;
		}

		if (data.compareTo(node.data) < 0) {
			return search(data, node.left);
		}

		else {
			return search(data, node.right);
		}

	}

	/*** MUTATORS ***/

	/**
	 * Inserts a new node in the tree
	 * 
	 * @param data the data to insert
	 */
	public void insert(T data) {

		// checking if the node is null must happen in the wrapper rather than in the
		// helper
		if (root == null) {
			root = new Node(data);
		}

		else {
			insert(data, root);
		}
	}

	/**
	 * Helper method to insert Inserts a new value in the tree
	 * 
	 * @param data the data to insert
	 * @param node the current node in the search for the correct location in which
	 *             to insert
	 */
	private void insert(T data, Node node) {

		if (data.compareTo(node.data) <= 0) {
			if (node.left == null) {
				node.left = new Node(data);
			} else {
				insert(data, node.left);
			}
		}

		else {
			if (node.right == null) {
				node.right = new Node(data);
			} else {
				insert(data, node.right);
			}
		}

	}

	/**
	 * Removes a value from the BST
	 * 
	 * @param data the value to remove
	 * @precondition !isEmpty()
	 * @precondition the data is located in the tree
	 * @throws NoSuchElementException when the precondition is violated
	 */
	public void remove(T data) throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("remove: Tree is Empty. No data to access!\n");
		}
		if (!search(data, root)) {
			throw new NoSuchElementException("remove: Data not found in the tree. Cannot remove.\n");
		}

		root = remove(data, root);
	}

	/**
	 * Helper method to the remove method
	 * 
	 * @param data: the data to remove
	 * @param node: the current node
	 * @return an updated reference variable
	 */
	private Node remove(T data, Node node) {

		// if we don't find the data in the end
		if (node == null) {
			return node;
		}

		// if the data to remove is smaller than the node's data,
		// then search the node's left subtree
		if (data.compareTo(node.data) < 0) {
			node.left = remove(data, node.left);

		}

		// if the data to remove is greater than the node's data,
		// then search the node's right subtree
		else if (data.compareTo(node.data) > 0) {
			node.right = remove(data, node.right);
		}

		// if the data is equal to the node's data
		else {
			if (node.right == null && node.left == null) {
				node = null;

			}

			else if (node.left != null && node.right == null) {
				node = node.left;
			}

			else if (node.left == null && node.right != null) {
				node = node.right;
			}

			else {
				T temp = findMin(node.right);
				node.data = temp;
				node.right = remove(temp, node.right);
			}

		}

		return node;

	}

	/*** ADDITONAL OPERATIONS ***/

	/**
	 * return the data in post order  
	 */
	public List<T> getOrderedItems() {
		List<T> itemList = new List<>();
		getOrderedItems(itemList, root);
		return itemList;

	}

	/**
	 * Helper method to getOrderedItems method
	 * Return the data in post order  
	 */
	private void getOrderedItems(List<T> itemList, Node node) {
		if (node == null) {
			return;
		} else {
			getOrderedItems(itemList, node.left);
			itemList.addLast(node.data);
			getOrderedItems(itemList, node.right);
		}
	}


	/**
	 * Prints the data in sorted order to the console
	 */
	public void inOrderPrint() {
		inOrderPrint(root);
		System.out.println();

	}

	/**
	 * Helper method to inOrderPrint method Prints the data in sorted order to the
	 * console
	 */
	private void inOrderPrint(Node node) {
		if (node == null) {
			return;
		} else {
			inOrderPrint(node.left);
			System.out.print(node.data + " ");
			inOrderPrint(node.right);

		}

	}


}
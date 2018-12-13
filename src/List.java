

/**
 * List.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */



import java.util.NoSuchElementException;

public class List<T extends Comparable<T>> {

	private class Node {
		private T data;
		private Node next;
		private Node prev;

		public Node(T data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}

	private int length;
	private Node first;
	private Node last;
	private Node iterator;

	/**** CONSTRUCTOR ****/

	/**
	 * Instantiates a new List with default values
	 * 
	 * @postcondition A linked list of length 0 is created with no Node in it
	 */

	public List() {
		length = 0;
		first = last = iterator = null;
	}

//	/**
//	 * Create a new List by copying from an exiting List
//	 * 
//	 * @postcondition A List composed of the same contents as in the original List
//	 */
//
//	public List(List ogList) {
//		if (ogList.isEmpty()) {
//			length = 0;
//			first = last = iterator = null;
//
//		} else {
//			Node temp = ogList.first;
//			while (temp != null) {
//				addLast(temp.data);
//				temp = temp.next;
//			}
//
//		}
//	}

	/**** ACCESSORS ****/

	/**
	 * Determines whether a List is sorted by calling the recursive helper method
	 * isSorted Note: A List that is empty can be considered to be (trivially)
	 * sorted
	 * 
	 * @return whether this List is sorted
	 */

	public boolean isSorted() {
		return isSorted(first);
	}

	/**
	 * Recursively determines whether a List is sorted in ascending order
	 * 
	 * @return whether this List is sorted
	 */

	// Logic: Check Recursively that node.data <= node.next.data. If not,
	// return false and that is a terminated condition to come out from recursion.
	// else call the method recursively for next node.
	private boolean isSorted(Node n) {

		if (n == null || n.next == null) {
			return true;
		}

		// If current node.data is greater than the next node's data, return false
		else if (n.data.compareTo(n.next.data) > 0) {
			return false;
		}

		else {
			return (isSorted(n.next));
		}
	}

	/**
	 * Returns the current index of the iterator from 0 to n - 1.
	 * 
	 * @precondition !offEnd()
	 * @return the index of the iterator
	 * @throws NullPointerException when the precondition is violated
	 */
	public int getIndex() throws NullPointerException {
		if (offEnd()) {
			throw new NullPointerException("getIndex: Iterator is pointing to null. No index to return.\n");
		}
		int i = 0;
		Node temp = first;
		while (temp != iterator) {
			i++;
			temp = temp.next;
		}
		return i;
	}

	/**
	 * Searches the List for the specified value using the iterative linear search
	 * algorithm
	 * 
	 * @param value: the value to search for
	 * @return the index of value in the list or ­-1 to indicate not found. Note
	 *         that if the List is empty we will consider the element to be not
	 *         found.
	 * @postcondition: position of the iterator remains unchanged!
	 */
	public int linearSearch(T value) {
		if (isEmpty()) {
			return -1;
		}

		// Note that if the value appears more than once in the linked list, then only
		// the index of the first node found would be returned.
		Node temp = first;
		for (int i = 0; i < length; i++) {
			if (temp.data.equals(value)) {
				return i;
			}
			temp = temp.next;
		}

		return -1;
	}

	/**
	 * Returns the index from 1 to length where value is located in the List by
	 * calling the private helper method binarySearch
	 * 
	 * @param value the value to search for
	 * @return the index where value is stored from 1 to length, or ­1 to indicate
	 *         not found
	 * @precondition isSorted()
	 * @postcondition the position of the iterator must remain unchanged!
	 * @throws IllegalStateException when the precondition is violated.
	 */
	public int binarySearch(T value) throws IllegalStateException {
		if (!isSorted()) {
			throw new IllegalStateException("List not sorted. Binary search cannot be applied.\n");
		}

		// Passing in 1 and length instead of 0 and length - 1 as parameters
		// ensures the returned index is between [1, length]
		return binarySearch(1, length, value);
	}

	/**
	 * Searches for the specified value in the List by implementing the recursive
	 * binarySearch algorithm
	 * 
	 * @param low: the lowest bounds of the search
	 * @param high: the highest bounds of the search
	 * @param value: the value to search for
	 * @return the index at which value is located or ­-1 to indicate not found
	 * @postcondition the location of the iterator must remain unchanged
	 */

	// Note that due to the binary split, if the value appears more than once in the
	// linked list,
	// then any index of the value might be returned, not necessarily the
	// smallest index.
	private int binarySearch(int low, int high, T value) {
		if (high < low) {
			return -1;
		}

		int mid = low + (high - low) / 2;

		Node temp = first;
		for (int i = 1; i < mid; i++) {
			temp = temp.next;
		}

		T midValue = temp.data;

		if (midValue.compareTo(value) == 0) {
			return mid;
		}

		else if (midValue.compareTo(value) < 0) {
			return binarySearch(mid + 1, high, value);
		}

		else {
			return binarySearch(low, mid - 1, value);
		}

	}

	/**
	 * Returns the value stored in the first node
	 * 
	 * @precondition -- First element is not null or length >= 1
	 * @return the integer value stored at node first
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getFirst() {
		if (length == 0) {
			throw new NoSuchElementException("getFirst: List is Empty. No data to access!\n");
		}
		return first.data;
	}

	/**
	 * Returns the value stored in the last node
	 * 
	 * @precondition -- Last element is not null or length >= 1
	 * @return the integer value stored in the node last
	 * @throws NoSuchElementException when precondition is violated
	 */
	public T getLast() {
		if (length == 0) {
			throw new NoSuchElementException("getLast: List is Empty. No data to access!\n");
		}
		return last.data;
	}

	/**
	 * Returns whether the List is currently empty
	 * 
	 * @return whether the List is empty
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * Returns the current length of the List
	 * 
	 * @return the length of the List from 0 to n
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the value currently pointed at by the iterator
	 * 
	 * @precondition iterator cannot be null
	 * @return the integer value stored in the node last
	 * @throws NullPointerException when precondition is violated
	 */
	public T getIterator() {
		if (iterator == null) {
			throw new NullPointerException("getIterator: Iterator is off end. No data to return!\n");
		}
		return iterator.data;
	}

	/**
	 * Returns whether the iterator is off the end of the list
	 * 
	 * @return whether the iterator is null
	 */
	public boolean offEnd() {
		return iterator == null;
	}

	/**
	 * Determines whether two Lists have the same data in the same order
	 * 
	 * @param L the List to compare to this List
	 * @return whether the two Lists are equal
	 */

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof List)) {
			return false;
		} else {
			List L = (List) o;
			if (this.length != L.length) {
				return false;
			} else {
				Node temp1 = this.first;
				Node temp2 = L.first;
				while (temp1 != null) { // Lists are same length
					if (!temp1.data.equals(temp2.data)) {
						return false;
					}
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				return true;
			}
		}
	}

	/**** MUTATORS ****/

	/**
	 * Points the iterator at first and then iteratively advances it to the
	 * specified index
	 * 
	 * @param index: the index where the iterator should be placed
	 * @precondition 1 <= index <= length
	 * @throws IndexOutOfBoundsException when precondition is violated
	 */

	public void moveToIndex(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException("moveToIndex: Specified index is out of bounds. Cannot move.\n");
		} else {
			pointIterator();
			for (int i = 0; i < index; i++) {
				advanceIterator();
			}
		}

	}

	/**
	 * removes the element at the front of the List
	 * 
	 * @precondition !isEmpty()
	 * @postcondition First element removed from the List, length - 1
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeFirst() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("List is empty! No first Node to remove.\n");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == first) {
				iterator = null;
			}
			first.next.prev = null;
			first = first.next;
		}
		length--;
	}

	/**
	 * removes the element at the end of the List
	 * 
	 * @precondition !isEmpty()
	 * @postcondition Last element removed from the linked list, length - 1
	 * @throws NoSuchElementException when precondition is violated
	 */
	public void removeLast() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException("List is empty! No last Node to remove.\n");
		} else if (length == 1) {
			first = last = iterator = null;
		} else {
			if (iterator == last) {
				iterator = null;
			}
			last.prev.next = null;
			last = last.prev;
		}
		length--;
	}

	/**
	 * Creates a new first element
	 * 
	 * @param data: the data to insert at the front of the List
	 * @postcondition First node in the List updated, length + 1
	 */
	public void addFirst(T data) {

		if (isEmpty()) { // The list is empty (edge case)
			first = last = new Node(data); // Must update last as well
		} else { // The list has one or more Nodes (general case)
			Node toAdd = new Node(data);
			toAdd.next = first;
			first.prev = toAdd;
			first = toAdd;

		}
		length++;
	}

	/**
	 * Creates a new last element
	 * 
	 * @param data: the data to insert at the end of the List
	 * @postcondition Last node in the List updated, length + 1
	 */
	public void addLast(T data) {
		if (isEmpty()) {
			first = last = new Node(data);
		} else {
			Node toAdd = new Node(data);
			last.next = toAdd;
			toAdd.prev = last;
			last = toAdd;

		}
		length++;
	}

	/**
	 * Point the iterator to the beginning of the list
	 * 
	 * @postcondition iterator pointed to the first node of the list
	 */

	public void pointIterator() {
		iterator = first;
	}

	/**
	 * Removes the node pointed to by the iterator
	 * 
	 * @precondition !offEnd()
	 * @postcondition node pointed by iterator removed; iterator == null
	 * @throws NullPointerException when the precondition is violated
	 */
	public void removeIterator() {
		// precondition
		if (offEnd()) {
			throw new NullPointerException("removeIterator: Iterator is off end. No data to remove!\n");
		}

		// edge case 1: iterator is pointing to first
		else if (iterator == first) {
			removeFirst();
		}

		// edge case 2: iterator is pointing to last
		else if (iterator == last) {
			removeLast();
		}

		// general case
		else {
			iterator.prev.next = iterator.next;
			iterator.next.prev = iterator.prev;
			length--;
			// edge cases don't need length -- because they call methods that already have
			// this

		}

		iterator = null;
	}

	/**
	 * Inserts an element after the node currently pointed at by the iterator
	 * 
	 * @precondition iterator != null / !offEnd()
	 * @postcondition a new node inserted after the node pointed at by the iterator
	 * @throws NullPointerException when precondition is violated
	 */

	public void addIterator(T data) {
		if (offEnd()) {
			throw new NullPointerException("addIterator: Iterator is off end. Cannot add node!\n");
		} else if (iterator == last) { // edge case: iterator is pointing to the last node
			addLast(data);
		} else { // general case: iterator is pointing to 0th ~ (n - 2)th node;
			Node toAdd = new Node(data);

			toAdd.next = iterator.next;
			iterator.next.prev = toAdd;

			toAdd.prev = iterator;
			iterator.next = toAdd;

			length++;
			// Note that edge case doesn't need length ++ because it's already in addLast()
		}
	}

	/**
	 * Moves the iterator up by one node
	 * 
	 * @precondition iterator is not null / !offEnd()
	 * @postcondition iterator advanced by one node toward the end of the List
	 * @throws NullPointerException when precondition is violated
	 */

	public void advanceIterator() {
		if (iterator == null) {
			throw new NullPointerException("advanceIterator: Iterator is off end. Cannot advance!\n");
		}
		iterator = iterator.next;
	}

	/**
	 * Moves the iterator down by one node
	 * 
	 * @precondition iterator is not null / !offEnd()
	 * @postcondition iterator reversed by one node toward the beginning of the List
	 * @throws NullPointerException when precondition is violated
	 */

	public void reverseIterator() {
		if (iterator == null) {
			throw new NullPointerException("reverseIterator: Iterator is off end. Cannot reverse!\n");
		}
		iterator = iterator.prev;
	}

	/**** ADDITIONAL OPERATIONS ****/

	/**
	 * List with each value separated by a blank space Add a new line at the end of
	 * the List
	 * 
	 * @return the List as a String for display
	 */
	@Override
	public String toString() {
		
//		if (isEmpty()) {
//			return "List is empty.";
//		}
		
		String result = "";
		Node temp = first;
		while (temp != null) {
			result += temp.data + " ";
			temp = temp.next;
		}

		result = result.trim() + "\n";
		return result;
	}

	public String toStrByComma() {

//		if (isEmpty()) {
//			return "List is empty.";
//		}
		
		String result = "";
		Node temp = first;

		while (temp != null) {
			result += temp.data + ", ";
			temp = temp.next;
		}

//			result = result.trim() + "\n";
		if (result.length() >= 2) {
			result = result.substring(0, result.length() - 2);
		}
		return result;

	}

	
	public String toStrByNewLine() {

//		if (isEmpty()) {
//			return "List is empty.";
//		}
		
		String result = "";
		Node temp = first;

		while (temp != null) {
			result += temp.data + "\n";
			temp = temp.next;
		}
		
		return result;

	}

	/**
	 * Prints the contents of the List to the screen in the format #. <element>
	 * followed by a newline
	 * 
	 * @return the numbered List as a String for display
	 */

	public String printNumberedList() {
		String result = "";
		int index = 0;
		Node temp = first;

		while (temp != null) {
			result += index + ". " + temp.data + "\n";
			index++;
			temp = temp.next;
		}

		result = result.trim() + "\n";
		return result;
	}


}


/**
* Hash.java
* @author Feng Hong
* CIS 22C, Lab 7
*/
import java.util.ArrayList;

public class Hash<T extends Comparable<T>> {

	// Because Movie implements Comparable<Movie> fits <T extends Comparable<T>>,
	// methods such as search() can be applied on Movie class. If Movie did not
	// extend Comparable, then the methods in Hash class cannot be applied to it.

	private int numElements; // personal note: number of elements currently in the Table
	private ArrayList<List<T>> table;
	private boolean[] hasBeenUsed;

	/**
	 * Constructor for the Hash.java class. Initializes the Table to be sized
	 * according to value passed in as a parameter.
	 * 
	 * @param size the table size
	 */
	public Hash(int size) {
		numElements = 0;
		hasBeenUsed = new boolean[size];

		table = new ArrayList<List<T>>();
		for (int i = 0; i < size; i++) {
			table.add(new List<T>());
		}

	}

	/**
	 * Returns the hash value in the Table for a given key by taking modulus of the
	 * hashCode value for that key and the size of the table
	 * 
	 * @param t the key
	 * @return the index in the Table
	 */
	private int hash(T t) {
		int index = t.hashCode() % table.size();
		return index;

	}

	/**
	 * Counts the number of keys at this index
	 * 
	 * @param index the index in the Table
	 * @precondition 0 <= index < Table.length
	 * @return the count of keys at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= table.size()) {
			throw new IndexOutOfBoundsException("countBucket(): " + "index is outside bounds of the table");
		}
		return table.get(index).getLength();
	}

	
	/**
	 * Check if a particular key is already in the hash table
	 * 
	 * @return true if the key can be located in the hash table, false otherwise
	 */
	
	public boolean containsKey(T t) {
		return (findIndex(t) != -1);
	}

	
	/**
	 * Finds the array index of an object with a particular key
	 * 
	 * @return index of the object if it is found in the hash table, -1 if 
	 * 		   the object has not been stored in the hash.
	 */	
	public int findIndex(T t) {
		
		int count = 0;
		int index = hash(t);
	
		
		while ((count < table.size()) && (hasBeenUsed[index])) {
			
			if (table.get(index).linearSearch(t) != -1) {
				return index;
			}
			count++;
			index = nextIndex(index);
		}
		
		return -1;
	}

	/** Accessors */

	/**
	 * Returns total number of keys in the Table
	 * 
	 * @return total number of keys
	 */
	public int getNumElements() {
		return numElements;
	}
	
	
	/**
	 * Returns the usage overview of the hash table  
	 */
	public String getHasBeenUsed() {
		
		String result = "";
		int sum = 0;
		
		for (boolean b : hasBeenUsed) {
			String str = String.valueOf(b);
			
			System.out.println(str);

			result += str + "\n";
			if (b == true) {
				sum++;
			}
		}
		
		return result + "total used: " + sum;
	}
 


	/**
	 * Searches for the linked list of objects that share the same key in the Table
	 * 
	 * @param t the key to search for
	 * @return the linked list stored in the index corresponding to the key
	 */
	public List searchList(T t) {
		int ind = findIndex(t);

		// if t is not in the hash table
		if (ind == -1) {
			return null;
		}

		else {
			return table.get(ind);
		}
	}

	
	/**
	 * Advance the index of the hash. 
	 * The return value is normally index + 1. But if index + 1 is hash table size,
	 * then the return value is zero instead.
	 * @param index
	 * @return advanced index
	 */
	private int nextIndex(int index)
	
	{
		if (index + 1 == table.size())
			return 0;
		else
			return index + 1;
	}

	/** Manipulation Procedures */

	/**
	 * Inserts a new key in the Table 
	 * Calls the hash method to determine placement
	 * 
	 * @param t the key to insert
	 */

	// Notes: Each time we insert a new object into the table, we need to complete
	// these steps:
	// 1. Call the hash method to determine at which index or bucket to place the
	// object inside the table
	// 2. Insert the object at the correct index in the ArrayList
	// 3. Call the correct method from the List class to insert this object onto the
	// END of the chain

	public void insert(T t) {
		
		int index = findIndex(t);
		
		if (index != -1) { // The key is already in the table.
			table.get(index).addLast(t);
			numElements ++;
		}
		
		else {	// The key has not been stored in the table, need to find the correct location and insert
			
			index = hash(t);
			
			while (!table.get(index).isEmpty() && table.get(index).linearSearch(t) == -1) {
				index = nextIndex(index);	
			}
			table.get(index).addLast(t);
			 hasBeenUsed[index] = true; 
			 numElements ++;

		}
		
//		System.out.println("Inserted to Hash for Name -- bucket " + index + ": " + t);

	}

	/**
	 * Removes the key t from the Table. Calls the hash method on the key to
	 * determine correct placement. Has no effect if t is not in the Table.
	 * 
	 * @param t the key to remove
	 */
	public void remove(T t) {
		if (findIndex(t) == -1) {
			System.out.println("Item not found in the data. Cannot remove.");
			return;
		}

		else {
			int hashInd = hash(t);
			List<T> dataList = table.get(hashInd);
			int listInd = dataList.linearSearch(t);
			dataList.moveToIndex(listInd);
			dataList.removeIterator();
			numElements--;
		}
	}

	/** Additional Methods */

	/**
	 * Prints all the keys at a specified bucket in the Table. Each key displayed on
	 * its own line, with a blank line separating each key Above the keys, prints
	 * the message "Printing bucket #<bucket>:" Note that there is no <> in the
	 * output
	 * 
	 * @param bucket the index in the Table
	 */
	public void printBucket(int bucket) throws IndexOutOfBoundsException {

		if (bucket < 0 || bucket >= table.size()) {
			throw new IndexOutOfBoundsException("printBucket(): " + "bucket is outside bounds of the table");
		}

		List<T> dataList = table.get(bucket);
		int listLength = dataList.getLength();

		dataList.pointIterator();

		System.out.println("Printing bucket # " + bucket + ":\n");
		for (int i = 0; i < listLength; i++) {
			T item = dataList.getIterator();
			System.out.println(item);
			dataList.advanceIterator();
		}

	}

	/**
	 * Prints the first key at each bucket along with a count of the total keys with
	 * the message "+ <count> -1 more at this bucket." Each bucket separated with
	 * two blank lines. When the bucket is empty, prints the message "This bucket is
	 * empty." followed by two blank lines.
	 */
	public void printTable() {

		if (numElements == 0) {
			System.out.println("Table is empty. Nothing to print.");
			return;
		}

		else {
			for (int bucket = 0; bucket < table.size(); bucket++) {
				List<T> listData = table.get(bucket);

				if (listData.isEmpty()) {
					System.out.println("Bucket: " + bucket);
					System.out.println("This bucket is empty.\n\n");
				}

				else {
					System.out.println("Bucket: " + bucket);
					System.out.println(listData.getFirst());
					System.out.println("+ " + (listData.getLength() - 1) + " more at this bucket\n\n");
				}

			}
		}

	}
}

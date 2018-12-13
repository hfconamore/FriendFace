
/**
 * HashByInterest.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */



import java.util.ArrayList;

public class HashByInterest {

	private int numElements; // personal note: number of elements currently in the interestHash
	private ArrayList<List<User>> interestHash;
	private ArrayList<String> interestNames;
	private boolean[] hasBeenUsed;

	/**
	 * Constructor for the Hash.java class. Initializes the interestHash to be sized
	 * according to value passed in as a parameter. Inserts size empty Lists into
	 * the interestHash. Sets numElements to 0.
	 * 
	 * @param size the interestHash size
	 */
	public HashByInterest(int size) {
		numElements = 0;
		interestHash = new ArrayList<List<User>>();
		interestNames = new ArrayList<String>();
		hasBeenUsed = new boolean[size];

		for (int i = 0; i < size; i++) {
			interestHash.add(new List<User>());
			interestNames.add(null);
		}
	}

	public boolean containsKey(String interest) {
		return (findIndex(interest) != -1);
	}

	/**
	 * If the specified key is found in the interestHash, then the return value is the
	 * index of the specified key. Otherwise, the return value is -1
	 * 
	 * @param interest: the interest for whom we're checking the existence of index
	 * @return index of the interest of -1 if interest is not found in hash interestHash
	 */
	private int findIndex(String interest) {
		int count = 0;
		int index = hash(interest);
		while ((count < interestHash.size()) && (hasBeenUsed[index])) {

			if (interest.equals(interestNames.get(index))) {
				return index;
			}
			count++;
			index = nextIndex(index);
		}
		return -1; // if the interest has not been stored in the interestHash yet
	}

	private int nextIndex(int index)
	// The return value is normally index + 1. But if i+1 is data.length, then
	// the return value is zero instead.
	{
		if (index + 1 == interestHash.size()) {
			return 0;
		} else
			return index + 1;
	}

	/**
	 * Returns the hash value in the interestHash for a given key by taking modulus of the
	 * hashCode value for that key and the size of the interestHash
	 * 
	 * @param t the key
	 * @return the index in the interestHash
	 */
	private int hash(String interest) {

		int sum = 0;

		for (int i = 0; i < interest.length(); i++) {
			sum += (int) interest.charAt(i);
		}

		int index = sum % interestHash.size();
		return index;

	}

	/**
	 * Counts the number of keys at this index
	 * 
	 * @param index the index in the interestHash
	 * @precondition 0 <= index < interestHash.length
	 * @return the count of keys at this index
	 * @throws IndexOutOfBoundsException
	 */
	public int countBucket(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= interestHash.size()) {
			throw new IndexOutOfBoundsException("countBucket(): " + "index is outside bounds of the interestHash");
		}
		return interestHash.get(index).getLength();
	}

	/** Accessors */

	/**
	 * Returns total number of keys in the interestHash
	 * 
	 * @return total number of keys
	 */
	public int getNumElements() {
		return numElements;
	}

	/**
	 * Searches for a specified key in the interestHash
	 * 
	 * @param String interest: the key to search for
	 * @return the linked list stored in the corresponding bucket
	 */
	public List searchByInterest(String interest) {
		int ind = findIndex(interest);

		// if t is not in the hash interestHash
		if (ind == -1) {
			return new List<User>();
		}

		else {
			return interestHash.get(ind);
		}
	}

	
	/**
	 * Returns the usage overview of the hash interestHash  
	 */
	public String getHasBeenUsed() {

		String result = "";
		int sum = 0;

		for (boolean b : hasBeenUsed) {
			
			String str = String.valueOf(b);
			
			System.out.println("hello" + str);
			result += str + "\n";
			if (b == true) {
				sum++;
			}
		}

		return result + "total used: " + sum;
	}

	/** Manipulation Procedures */

	/**
	 * Inserts a new key in the interestHash Calls the hash method to determine placement
	 * 
	 * @param t the key to insert
	 */

	public void insert(User u) {

		String[] interests = u.getInterests().split(", ");

		// a user might be added to interestHash in multiple buckets
		// depending on how many interests she has
		for (final String interest : interests) {

			int index = findIndex(interest);

			// if the interest has already been in the hash
			if (index != -1) {
				interestHash.get(index).addLast(u);
				numElements ++;
			}

			// if the interest has not been stored in the hash
			else {
				// when findeIndex() == -1, use hash() as starting point for finding the first
				// unused index
				index = hash(interest);

				// find the next available bucket that has not been taken by other interests
				while (interestNames.get(index) != null) {
					index = nextIndex(index);
				}
				interestNames.set(index, interest);
				interestHash.get(index).addLast(u);
				hasBeenUsed[index] = true;
				numElements ++;
			}

//			System.out.println("Inserted to bucket " + index + " interest " + interest);
		}
	}

//	/**
//	 * Removes the key t from the interestHash. Calls the hash method on the key to
//	 * determine correct placement. Has no effect if t is not in the interestHash.
//	 * 
//	 * @param t the key to remove
//	 */
//	public void remove(User u, String interest) {
//		// User u does not have the interest and is thus not
//		// stored in the corresponding bucket
//		if (searchByInterest(interest).linearSearch(u) == -1) {
//			return;
//		}
//
//		else {
//			int bucketInd = hash(interest);
//			List<User> userList = interestHash.get(bucketInd);
//			int userInd = userList.linearSearch(u);
//			userList.moveToIndex(userInd);
//			userList.removeIterator();
//			numElements--;
//		}
//	}

	/** Additional Methods */

	/**
	 * Prints all the keys at a specified bucket in the interestHash. Each key displayed on
	 * its own line, with a blank line separating each key Above the keys, prints
	 * the message "Printing bucket #<bucket>:" Note that there is no <> in the
	 * output
	 * 
	 * @param bucket the index in the interestHash
	 */
	public void printBucket(int bucket) throws IndexOutOfBoundsException {

		if (bucket < 0 || bucket >= interestHash.size()) {
			throw new IndexOutOfBoundsException("printBucket(): " + "bucket is outside bounds of the interestHash");
		}

		System.out.println("Printing bucket # " + bucket + ":\n");
		System.out.println("This bucket has interest: " + interestNames.get(bucket)
				+ "\nThe following users have this interest:\n");

		List<User> userList = interestHash.get(bucket);
		int userCount = userList.getLength();
		userList.pointIterator();

		for (int i = 0; i < userCount; i++) {
			User u = userList.getIterator();
			System.out.println(u);
			userList.advanceIterator();
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
			System.out.println("Hash Table by interest is empty. Nothing to print.");
			return;
		}

		else {
			for (int bucket = 0; bucket < interestHash.size(); bucket++) {
				System.out
						.println("Now showing interest '" + interestNames.get(bucket) + "' in bucket " + bucket + ":");

				List<User> userList = interestHash.get(bucket);

				if (userList.isEmpty()) {
//					System.out.println("Bucket: " + bucket);
					System.out.println("This bucket is empty.\n\n");
				}

				else {
//					System.out.println("Bucket: " + bucket);
					System.out.println(userList.getFirst());
					System.out.println("+ " + (userList.getLength() - 1) + " more at this bucket\n\n");
				}

			}
		}

	}
}


/**
 * Graph.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */

import java.util.ArrayList;

public class Graph {

	private int vertices;
	private int edges;
	private ArrayList<List<User>> adj;
	private ArrayList<Character> color;
	private ArrayList<Integer> distance;
	private ArrayList<User> parent;

	private List<User> userList;

	/** Constructors */

	/**
	 * initializes an empty graph, with n vertices and 0 edges
	 * 
	 * @param n the number of vertices in the graph
	 */
	public Graph(int n, List<User> userList) {
		vertices = n;
		edges = 0;

		adj = new ArrayList<List<User>>();
		color = new ArrayList<Character>();
		distance = new ArrayList<Integer>();
		parent = new ArrayList<User>();
		this.userList = userList;

		for (int i = 0; i <= n; i++) {
			adj.add(new List<User>());
			color.add('W');
			distance.add(-1);
			parent.add(null);
		}

	}

	/*** Accessors ***/

	/**
	 * Returns the number of edges in the graph
	 * 
	 * @return the number of edges
	 */
	public int getNumEdges() {
		return edges;
	}

	/**
	 * Returns the number of vertices in the graph
	 * 
	 * @return the number of vertices
	 */
	public int getNumVertices() {
		return vertices;
	}

	/**
	 * Returns whether the graph is empty (no vertices)
	 * 
	 * @return whether the graph is empty
	 */
	public boolean isEmpty() {
		return vertices == 0;
	}

	/**
	 * Returns the distance value for a given user
	 * 
	 * @param u a vertex in the graph
	 * @precondition u is in the graph
	 * @return the distance of vertex u
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public Integer getDistance(User u) throws IndexOutOfBoundsException {

		int userIndex = userList.linearSearch(u);

		if (userIndex == -1) {
			throw new IndexOutOfBoundsException("getDistance: Input User not found in the database.");
		}

		else {
			return distance.get(userIndex);
		}
	}

	/**
	 * Returns the parent of a given user
	 * 
	 * @param u a vertex in the graph
	 * @precondition u is in the graph
	 * @return the parent of vertex u
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public User getParent(User u) throws IndexOutOfBoundsException {

		int userIndex = userList.linearSearch(u);

		if (userIndex == -1) {
			throw new IndexOutOfBoundsException("getParent: Input User not found in the database.");
		}

		else {
			return parent.get(userIndex);
		}
	}

	/**
	 * Returns the color of a given user
	 * 
	 * @param u a vertex in the graph
	 * @precondition u is in the graph
	 * @return the color of vertex u
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public Character getColor(User u) throws IndexOutOfBoundsException {

		int userIndex = userList.linearSearch(u);

		if (userIndex == -1) {
			throw new IndexOutOfBoundsException("getColor: Input User not found in the database.");
		}

		else {
			return color.get(userIndex);
		}
	}

	/**
	 * Returns the adjacency list of a given user
	 * 
	 * @param u a vertex in the graph
	 * @precondition u is in the graph
	 * @return the color of vertex u
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */
	public List<User> getAdj(User u) throws IndexOutOfBoundsException {

		int userIndex = userList.linearSearch(u);

		if (userIndex == -1) {
			throw new IndexOutOfBoundsException("getAdj: Input User not found in the database.");
		}

		else {
			return adj.get(userIndex);
		}
	}

	/**
	 * Returns all the vertex pairs
	 * 
	 * @precondition Graph is not empty
	 * @return the color of vertex v
	 * @throws IndexOutOfBoundsException when the precondition is violated
	 */

	public String getUserPairs() throws IllegalStateException {
		if (isEmpty()) {
			return "";
		}

		String results = "";

		// Start with the first user in the database
		userList.pointIterator();

		while (!userList.offEnd()) {
			User currentU = userList.getIterator();

			List<User> currentAdj = getAdj(currentU);

			// if current user doesn't have any friend, move to the next user
			if (currentAdj.isEmpty()) {
				userList.advanceIterator();
				continue;
			}

			// else get the pairs
			currentAdj.pointIterator();

			while (!currentAdj.offEnd()) {

				User friend = currentAdj.getIterator();

				if (currentU.getID() < friend.getID()) {
					results += currentU.getID() + " " + friend.getID() + "\n";
				}
				// Note: If current user's ID >= friend's ID, then the pair should have been
				// printed when we go over the friend's adjacency list

				currentAdj.advanceIterator();
			}

			userList.advanceIterator();
		}

		return results.trim();

	}


	/*** Manipulation Procedures ***/

	/**
	 * Inserts user v into the adjacency list of user u and inserts u into the
	 * adjacent list of v.
	 * 
	 * @precondition u and v are both in the user list
	 */
	public boolean addUndirectedEdge(User u, User v) {

		if (userList.linearSearch(u) == -1 || userList.linearSearch(v) == -1) {
			throw new IndexOutOfBoundsException("addUndirectedEdge: At least one of the users is not in the database.");
		}

		// if the 2 users are already friends, then don't do anything
		if (getAdj(u).linearSearch(v) != -1 && getAdj(v).linearSearch(u) != -1) {
			return false;
		}

		else {
			getAdj(u).addLast(v);
			getAdj(v).addLast(u);
			edges++;
			return true;
		}

	}

	/*** Additional Operations ***/

	/**
	 * Creates a String representation of the Graph. Prints the adjacency list of
	 * each vertex in the graph, vertex: <space separated list of adjacent vertices>
	 */
	@Override
	public String toString() {

		String result = "";

		userList.pointIterator();
		User currentU = null;

		while (!userList.offEnd()) {
			currentU = userList.getIterator();

			List<User> currentAdj = getAdj(currentU);

			if (currentAdj.isEmpty()) {
				result += currentU + ": No friend found.\n\n";
			}

			else {
				result += currentU + ": " + currentAdj.toStrByComma() + "\n\n";
			}

			userList.advanceIterator();
		}

		return result;

	}

	/**
	 * Prints the current values in the parallel ArrayLists after executing BFS.
	 * First prints the heading: v <tab> c <tab> p <tab> d. Then, prints out this
	 * information for each vertex in the graph. Note that this method is intended
	 * purely to help you debug your code.
	 */
	public void printBFS() {

		userList.pointIterator();

		System.out.println("v\tc\tp\td");

		while (!userList.offEnd()) {
			User currentU = userList.getIterator();
			System.out.println(
					currentU + "\t" + getColor(currentU) + "\t" + getParent(currentU) + "\t" + getDistance(currentU));
			userList.advanceIterator();
		}

	}

	/**
	 * Performs breath first search on this Graph give a source vertex
	 * 
	 * @param source
	 * @precondition graph must not be empty
	 * @precondition source is a vertex in the graph
	 * @throws IllegalStateException     if the graph is empty
	 * @throws IndexOutOfBoundsException when the source vertex is invalid
	 */

	public void BFS(User source) throws IllegalStateException, IllegalArgumentException {

		if (isEmpty()) {
			throw new IllegalStateException("BFS: Graph is empty. BFS cannot be performed.");
		}

		int userIndex = userList.linearSearch(source);

		if (userIndex == -1) {
			throw new IllegalArgumentException("BFS: Input user is not in the database.");
		}

		// Reset color, distance, and parent before each BFS execution
		for (int i = 0; i < vertices; i++) {
			color.set(i, 'W');
			distance.set(i, -1);
			parent.set(i, null);
		}

		color.set(source.getID(), 'G');
		distance.set(source.getID(), 0);

		List<User> queue = new List<User>();
		queue.addLast(source);

		while (!queue.isEmpty()) {

			User currentU = queue.getFirst();
			queue.removeFirst();

			List<User> currentAdj = getAdj(currentU);

			// If there's no vertex in the adjacency list, then move on to next
			// element in the queue.
			if (currentAdj.isEmpty()) {
				color.set(currentU.getID(), 'B');
				continue;
			}

			currentAdj.pointIterator();

			while (!currentAdj.offEnd()) {

				User friend = currentAdj.getIterator();

				if (getColor(friend) == 'W') {
					color.set(friend.getID(), 'G');
					distance.set(friend.getID(), getDistance(currentU) + 1);
					parent.set(friend.getID(), currentU);
					queue.addLast(friend);
				}

				currentAdj.advanceIterator();
			}
			color.set(currentU.getID(), 'B');

		}

	}

	/**
	 * Recursive method to make a String containing the path from the source to the
	 * destination vertex
	 * 
	 * @param source: the source vertex when performing BFS
	 * @param destination: the destination vertex
	 * @param path: String containing the path
	 * @return a String containing the path
	 */
	// Prints to the console or to an output file given the ostream parameter
	public String printPath(User source, User destination, String path) {
		if (source == destination) {
			return source + " --> " + path;
		}

		else if (getParent(destination) == null) {
			return "No path from " + source + " to " + destination + " exists.";
		}

		else {
			return printPath(source, getParent(destination), destination + " --> " + path);
		}

	}

}

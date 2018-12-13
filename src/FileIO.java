
/**
 * FileIO.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */

import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileIO {

//	static Scanner userInput = new Scanner(System.in);
	int profileCount; // number of profiles read into the program

	// For storing user profile data
	private int hashSize;
	private Hash hashByName;
	private HashByInterest hashByInterest;
	private List<User> userList;

	// For storing user connection
	private Graph graph;

	/************ Constructor ************/

	public FileIO() throws IOException {
		profileCount = 0;

		// Default hashSize should be big enough for the number of users and distinct interests
		hashSize = 67; 
		hashByName = new Hash(hashSize);
		hashByInterest = new HashByInterest(hashSize);

		userList = new List<User>();

		// Load profile data first so that profileCount and userList would be updated
		loadProfileElements();

		graph = new Graph(profileCount, userList);

		loadConnections();

	}

	/************ Accessors ************/
	public int getProfileCount() {
		return profileCount;
	}

	public int getHashSize() {
		return hashSize;
	}

	public Hash getHashByName() {
		return hashByName;
	}

	public HashByInterest getHashByInterest() {
		return hashByInterest;
	}

	public List<User> getUserList() {
		return userList;
	}

	public Graph getGraph() {
		return graph;
	}

	/************ Other Methods - Reading Data ************/

	/**
	 * Load user profile data
	 * 
	 * @throws IOException
	 */

	private void loadProfileElements() throws IOException {

		File file = new File("users.txt");
		Scanner input = new Scanner(file);
		String line;

		final int linesInAProfile = 7;
		String[] profileElements = new String[linesInAProfile];

		int lineID = 1;

		System.out.println("\nReading user profiles from " + file + "...\n");
		while (input.hasNextLine()) {

			if (lineID % (linesInAProfile + 1) == 0) {
				input.nextLine(); // eat the blank line
				lineID = 1; // reset the lineID
				continue;
			}

			else {
				line = input.nextLine();
				profileElements[lineID - 1] = line;

				// When lineID reaches linesInAProfile, we arrive at the last
				// line of a user's profile.
				if (lineID == linesInAProfile) {
					User newUser = new User(profileElements[0], profileElements[1], profileElements[2],
							profileElements[3], Integer.parseInt(profileElements[4]), profileElements[5],
							profileElements[6].split(", "));

					hashByName.insert(newUser);
					hashByInterest.insert(newUser);
					userList.addLast(newUser);

					profileCount++;

					profileElements = new String[linesInAProfile]; // reset the user profile

				}

				lineID++;

			}

		}

		System.out.println(profileCount + " profiles successfully loaded to the program.\n");

	}

	/**
	 * Load user connection Data
	 * 
	 * @throws IOException
	 */

	private void loadConnections() throws IOException {
		File file = new File("connections.txt");
		Scanner input = new Scanner(file);
		String line;

		System.out.println("\nReading user connection data from " + file + "...\n");

		while (input.hasNextLine()) {

			line = input.nextLine();
			
			if (line == "\n") {
				continue;
			}

			String[] pairs = line.trim().split(" ");
			int firstNum = Integer.parseInt(pairs[0]);
			int secondNum = Integer.parseInt(pairs[1]);

			userList.moveToIndex(firstNum);
			User firstU = userList.getIterator();

			userList.moveToIndex(secondNum);
			User secondU = userList.getIterator();

			// Update the connection graph
			graph.addUndirectedEdge(firstU, secondU);

			// Add the pair of users to each other's friend BST
			firstU.addFriend(secondU);
			secondU.addFriend(firstU);

		}

		System.out.println(graph.getNumEdges() + " user connections successfully loaded to the program.\n");

	}

	public String getOrderedFriendsForAll() {

		String result = "";
		userList.pointIterator();

		User currentU = null;

		while (!userList.offEnd()) {

			currentU = userList.getIterator();
			result += currentU + ": " + currentU.getFriends() + "\n\n";
			userList.advanceIterator();

		}
		return result;
	}

	/************ Other Methods - Writing Data ************/

	public void writeConnections(String s) {

		String connectionFileName = "connections.txt";
		try (PrintWriter out = new PrintWriter(connectionFileName)) {
			out.println(s);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nDatabase has been updated.");

	}

}

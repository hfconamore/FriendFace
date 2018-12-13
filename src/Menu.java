
/**
 * Menu.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import javax.xml.ws.FaultAction;

public class Menu {

	static Scanner userInput = new Scanner(System.in);
	private User userLoggedIn;
	private List<User> userList;
	private Hash hashByName;
	private HashByInterest hashByInterest;
	private Graph graph;
	private ArrayList<String> potentialFriendNames;

	public Menu(User userLoggedIn, List<User> userList, Hash hashByName, HashByInterest hashByInterest, Graph graph) {
		this.userLoggedIn = userLoggedIn;
		this.userList = userList;
		this.hashByName = hashByName;
		this.hashByInterest = hashByInterest;
		this.graph = graph;
		potentialFriendNames = new ArrayList<String>();
	}

	/****************** 2 Levels of Menu Options Setup *******************/

	static public Set<String> topLevelSetup() {

		final Set<String> options = new HashSet<>();

		options.add("VMF"); // View My Friend
		options.add("SNF"); // Search for a New Friend
		options.add("GFR"); // Get Friend Recommendation
		options.add("Q"); // Quit

		return options;

	}

	static public Set<String> VMFSetup() {

		final Set<String> options = new HashSet<>();
		options.add("VAF");
		options.add("VFP");
		options.add("RF");
		options.add("GB"); // go back

		return options;

	}

	static public Set<String> SNFSetup() {

		final Set<String> options = new HashSet<>();
		options.add("SN");
		options.add("SI");
		options.add("GB"); // go back

		return options;

	}

	/****************** Messages to the User *******************/

	public static void printTopMenuMsg() {
		System.out.print("\n###### Main Menu ###### - Please select from one of the following options:\n\n"
				+ "VMF: View My Friends\n" + "   - View All Friends\n" + "   - View a Friend's Profile\n"
				+ "   - Remove a Friend\n" + "SNF: Search for a New Friend\n" + "   - Search by Name\n"
				+ "   - Search by Interest\n" + "GFR: Get Friend Recommendations\n" + "Q: Quit\n\n"
				+ "Enter your choice: ");
	}

	public static void printVMFMsg() {
		System.out.print("\n###### View My Friends ###### - Please select from one of the following options:\n\n"
				+ "VAF: View All Friends\n" + "VFP: View a Friend's Profile\n" + "RF: Remove a Friend\n"
				+ "GB: Go Back to Main Menu\n\n" + "Enter your choice: ");
	}

	public static void printSNFMsg() {
		System.out
				.print("\n###### Search for a New Friend ###### - Please select from one of the following options:\n\n"
						+ "SN: Search by Name\n" + "SI: Search by Interest\n" + "GB: Go Back to Main Menu\n\n"
						+ "Enter your choice: ");
	}

	public static void printVFPMsg() {
		System.out.println("\nPlease enter your friend's name or 'GB' to go back: ");

	}

	public static void printRFMsg() {
		System.out.println("\nPlease enter an eligible name to remove or 'GB' to go back: ");

	}

	public static void printSNMsg() {
		System.out.println("\nPlease enter a name for searching or 'GB' to go back: ");

	}

	public static void printSIMsg() {
		System.out.println("\nPlease enter an interest or 'GB' to go back: ");
	}

	/****************** Start Menu *******************/

	public void promptOptions() {

		printTopMenuMsg();

		Set<String> mainOptions = topLevelSetup();

		while (userInput.hasNextLine()) {

			String userSelection = userInput.nextLine();

			// (1) Prompt the user for a valid input until done
			if (!mainOptions.contains(userSelection.toUpperCase())) {
				System.out.println("\nInvalid Selection!");
				printTopMenuMsg();
				continue;
			}

			// (2) If user input is Q, then quit the App
			else if (userSelection.toUpperCase().equals("Q")) {
				quitApp();
				break;
			}

			// (3) Go to View My Friends section
			else if (userSelection.toUpperCase().equals("VMF")) {
				VMF();
				printTopMenuMsg();
				continue;
			}

			// (4) Go to Search for a New Friend section
			else if (userSelection.toUpperCase().equals("SNF")) {
				SNF();
				printTopMenuMsg();
				continue;
			}

			// (5) Get Friend Recommendation:
			else if (userSelection.toUpperCase().equals("GFR")) {
				GFR();
				printTopMenuMsg();
				continue;
			}

		}

	}

	/****************** Option Execution Helpers *******************/

	/************ Top Level Menu Option Helpers *************/

	public void VMF() {

		printVMFMsg();
		Set<String> VMFOptions = VMFSetup();

		while (userInput.hasNextLine()) {

			String userSelection = userInput.nextLine();

			// (1) Prompt the user for a valid input until done
			if (!VMFOptions.contains(userSelection.toUpperCase())) {
				System.out.println("\nInvalid Selection!");
				printVMFMsg();
				continue;
			}

			// (2) If user input is GB, then go back to main menu
			else if (userSelection.toUpperCase().equals("GB")) {
				System.out.println("\nGo back to main menu.");
				break;
			}

			// (3) If user wants to view all friends
			else if (userSelection.toUpperCase().equals("VAF")) {
				VAF();
				printVMFMsg();
				continue;
			}

			// (4) If user wants to view a particular profile
			else if (userSelection.toUpperCase().equals("VFP")) {
				VFP();
				printVMFMsg();
				continue;
			}

			// (5) If user wants to remove a friend
			else if (userSelection.toUpperCase().equals("RF")) {
				RF();
				printVMFMsg();
				continue;
			}
		}

	}

	public void SNF() {

		printSNFMsg();
		Set<String> SNFOptions = SNFSetup();

		while (userInput.hasNextLine()) {

			String userSelection = userInput.nextLine();

			// (1) Prompt the user for a valid input until done
			if (!SNFOptions.contains(userSelection.toUpperCase())) {
				System.out.println("\nInvalid Selection!");
				printSNFMsg();
				continue;
			}

			// (2) If user input is GB, then go back to main menu
			else if (userSelection.toUpperCase().equals("GB")) {
				System.out.println("\nGo back to main menu.");
				break;
			}

			// (3) If user wants to search by name
			else if (userSelection.toUpperCase().equals("SN")) {
				SN();
				printSNFMsg();
				continue;
			}

			// (4) If user wants to search by interest
			else if (userSelection.toUpperCase().equals("SI")) {
				SI();
				printSNFMsg();
				continue;
			}
		}

	}

	public void GFR() {
		System.out.println("\n###### Get Friend Recommendation ######");

		// ADT Setup for friend recommendation
		potentialFriendNames = new ArrayList<String>();
		BST<PotentialFriend> potentialFriendTree = new BST<PotentialFriend>();

		// Step 1: Run BFS and implicitly get the distance of each user to the logged-in
		// user
		graph.BFS(userLoggedIn);
		graph.printBFS();

		userList.pointIterator();

		while (!userList.offEnd()) {
			User currentU = userList.getIterator();

			// If someone and the logged in user do not have common friends,
			// or if they are already friend of each other, then skip her
            
            // 	Or use " if(graph.getDistance(currentU) <= 1 ) "  1 - already a friend; 0 - self; -1 - no connection
            if (graph.getParent(currentU) == null || graph.getAdj(userLoggedIn).linearSearch(currentU) != -1) {
				userList.advanceIterator();
				continue;
			}

			// Step 2: Set up for calculating # of shared interests
			String[] selfInterests = userLoggedIn.getInterests().split(", ");
			Set<String> s1 = new HashSet(Arrays.asList(selfInterests));

			String[] currentUInterests = currentU.getInterests().split(", ");
			Set<String> s2 = new HashSet(Arrays.asList(currentUInterests));

			System.out.println("before: self interests are " + s1);
			System.out.println("before: currentU's interests are " + s2);

			s1.retainAll(s2);
			System.out.println(s1);

			int sharedInterestCt = s1.size();

			// Calculate total distance = distance in the graph - number of shared interests
			// Note that when recommending friends, we're ranking candidates from ascending
			// total distance. Therefore, the smaller the distance in the graph, the better.
			// And the more shared interests they have, the smaller the total distance.
			int totalDist = graph.getDistance(currentU) - sharedInterestCt;

			System.out.println(userLoggedIn + " --and-- " + currentU + " has: score = " + totalDist + " = "
					+ graph.getDistance(currentU) + " - " + sharedInterestCt + "\n\n");

			potentialFriendNames.add(currentU.getFullName());
			potentialFriendTree.insert(new PotentialFriend(currentU.getFullName(), totalDist));

			userList.advanceIterator();

		}

		// If there's no friend to recommend
		if (potentialFriendNames.isEmpty()) {
			System.out.println("\nSorry, there's no friend to recommend based on your network and interests.");
		}

		// If the App finds 1 or more friend to recommend
		else {
			System.out.println("\nBased on your network and interests, we'd like to recommend the following "
					+ "users to you (ranked by descending recommendability):\n"
					+ potentialFriendTree.getOrderedItems().toStrByNewLine());

//			System.out.println("For testing, print out potentialFriendNames (ArrayList):\n" + potentialFriendNames);

			// Give user the option of adding one or more of these recommendations to her
			// friend list.
			System.out.print("\nDo you want to add new friends from the recommendation? (Y/N) ");

			checkIfToAddNewFriend();
		}
	}

	public String quitApp() {
		return graph.getUserPairs();

	}

	/************ 2nd Level Menu Option Helpers *************/

	public void VAF() {

		if (userLoggedIn.getFriendsBST().isEmpty()) {
			System.out.println("\nCurrently you don't have any friends.\n");
		}

		else {
			System.out.println("\nYou currently have the following friends:\n");
			System.out.println(userLoggedIn.getFriends());
//			System.out.println("From Graph adjacency list: " + graph.getAdj(userLoggedIn).toStrByComma());
		}
	}

	public void VFP() {

		if (userLoggedIn.getFriendsBST().isEmpty()) {
			System.out.println("\nCurrently you don't have any friends.\n");
			return;
		}

		printVFPMsg();

		while (userInput.hasNextLine()) {

			String nameToView = userInput.nextLine();

			if (nameToView.toUpperCase().equals("GB")) {
				break;
			}

			String[] fnAndLn = nameToView.split(" ");

			// If the user input name is not valid, call the RF() method again
			if (fnAndLn.length != 2) {
				System.out.println("What you entered was not a valid name.");
				printVFPMsg();
				continue;
			}

			// If user types in a legit full name
			User toView = new User(fnAndLn[0], fnAndLn[1]);

			if (userLoggedIn.getFriendsBST().search(toView) == true) { // If the input is indeed a friend's name
				User locatedF = (User) hashByName.searchList(toView).getFirst();
				System.out.println("Here's your friend " + locatedF + "'s profile info:\n" + locatedF.getBasicInfo());
				break;
			}

			else {
				System.out.println("You don't have a friend named '" + toView + "'.\n");
				break;
			}
		}

	}

	public void RF() {

		// First check if user has at least 1 friend. If not, prompt the message and go
		// back
		if (userLoggedIn.getFriendsBST().isEmpty()) {
			System.out.println("\nSorry, currently you don't have any friends. Cannot remove.");
			return;
		}

		VAF();
		printRFMsg();

		while (userInput.hasNextLine()) {

			// Give the user the option of going back
			String nameToDelete = userInput.nextLine();
			if (nameToDelete.toUpperCase().equals("GB")) {
				break;
			}

			String[] fnAndLn = nameToDelete.split(" ");

			// If the user input name is not valid, call the RF() method again
			if (fnAndLn.length != 2) {
				System.out.println("What you entered was not a valid name.");
				printRFMsg();
				continue;
			}

			// If user entered a legit full name
			User toDelete = new User(fnAndLn[0], fnAndLn[1]);

			// If the entered name does not belong to any of the friend
			if (userLoggedIn.getFriendsBST().search(toDelete) == false) {
				System.out.println("You don't have a friend named '" + toDelete + "'.\n");
				printRFMsg();
				continue;
			}

			// Otherwise, execute the removing steps
			// 1. Search for the actual User object in the hash table using the name
			/*
			 * Note that since we've verified that there is a user by the entered name and
			 * since that the logged-in user's friend list is a subset of all users,
			 * searching the hash table will find the actual user for sure.
			 */

			toDelete = (User) hashByName.searchList(toDelete).getFirst();
			System.out.println("We're to delete this user:\n" + toDelete.getBasicInfo());

			// 2. update the friend BST for both the logged-in user and her friend-to-be
			userLoggedIn.removeFriend(toDelete);
			toDelete.removeFriend(userLoggedIn);

			// 3. Update the connection graph
			int toDelIndex = graph.getAdj(userLoggedIn).linearSearch(toDelete);

			graph.getAdj(userLoggedIn).moveToIndex(toDelIndex);
			graph.getAdj(userLoggedIn).removeIterator();

			int userIndex = graph.getAdj(toDelete).linearSearch(userLoggedIn);
			graph.getAdj(toDelete).moveToIndex(userIndex);
			graph.getAdj(toDelete).removeIterator();

			System.out.println(toDelete.getFullName() + " successfully removed from your friend list!\n");
			VAF();
			break;

		}

	}

	public void SN() {
		printSNMsg();
		while (userInput.hasNextLine()) {

			String nameToSearch = userInput.nextLine();

			if (nameToSearch.toUpperCase().equals("GB")) {
				break;
			}

			String[] fnAndLn = nameToSearch.split(" ");

			// If the user input name is not valid, call the RF() method again
			if (fnAndLn.length != 2) {
				System.out.println("What you entered was not a valid name.");
				printSNMsg();
				continue;
			}

			// If user types in a legit full name
			User toSearch = new User(fnAndLn[0], fnAndLn[1]);

			int index = hashByName.findIndex(toSearch);
			// If the user input name is not valid, call the RF() method again
			if (index == -1) {
				System.out.println("\nWe don't have any user with name '" + toSearch + "' in the data base.\n");
//				printSNMsg();
				break;
			}

			else {

				// Note that we cannot use User toSearch directly as that fake User only has
				// the same name as the actual user but does not have any other info such as
				// city and interests. Need to locate the actual user from the hash table.

				User foundU = (User) hashByName.searchList(toSearch).getFirst();
				// In Lakers, every player's name is unique, getFirst() = getLast()

				System.out.println("\nWe found " + foundU + "in our database: ");
				System.out.println(foundU.getBasicInfo());

				// Offer the option of adding the user found only if
				// 1) the name is different from the user herself, and
				// 2) searched result is not a friend of the user already
				if (!userLoggedIn.getFullName().equals(foundU.getFullName())
						&& userLoggedIn.getFriendsBST().search(foundU) == false) {

					potentialFriendNames = new ArrayList<String>();
					potentialFriendNames.add(foundU.getFullName());

					System.out.print("\nDo you want to add this person as your friend? (Y/N) ");
					checkIfToAddNewFriend();
				}
				break;

			}
		}

	}

	public void SI() {

		printSIMsg();

		String intToSearch = userInput.nextLine();

		if (intToSearch.toUpperCase().equals("GB")) {
			return;
		}

		String userNamesFound = hashByInterest.searchByInterest(intToSearch).toStrByNewLine();

		// If no one is interested in the thing user searched for
		if (userNamesFound.equals("")) {
			System.out.println("\nNo one is interested in " + intToSearch + ".");
			return;
		}

		// If at least 1 user has the interest
		System.out.println("\nHere're people who're interested in " + intToSearch + ":\n\n" + userNamesFound);

		// Update the potential friend name list, removing user's existing friends and
		// user herself if applicable
		potentialFriendNames = new ArrayList<String>(Arrays.asList(userNamesFound.split("\n")));
		potentialFriendNames
				.removeIf(n -> (userLoggedIn.getFriends().contains(n) || n.equals(userLoggedIn.getFullName())));

		if (potentialFriendNames.size() > 0) {
			System.out.print("\nYou can add 1 or more friends from the following list:\n" + potentialFriendNames
					+ "\n\nDo you want to add a new friend? (Y/N) ");
			checkIfToAddNewFriend();
		}

	}

	public void addNewFriend() {
		System.out.print("\nEnter the name of the person you'd like to add: ");

		while (userInput.hasNextLine()) {

			String nameToAdd = userInput.nextLine();

			// (1) Prompt the user for a valid name (from the search list or recommendation
			// list) until done
			if (potentialFriendNames.contains(nameToAdd) == false) {
				System.out.println("\nInvalid Selection! Please enter an eligible name: ");
				continue;
			}

			// (2) If user input is valid, add the friend
			else {

				// 1. Search for the actual User object in the hash table using the name
				String[] fnAndLn = nameToAdd.split(" ");
				User toAdd = (User) hashByName.searchList(new User(fnAndLn[0], fnAndLn[1])).getFirst();

				// 2. update the friend BST for both the logged-in user and her friend-to-be
				userLoggedIn.addFriend(toAdd);
				toAdd.addFriend(userLoggedIn);

				// 3. Update the graph for both the logged-in user and her friend-to-be
				graph.getAdj(userLoggedIn).addLast(toAdd);
				graph.getAdj(toAdd).addLast(userLoggedIn);

				// 4. remove the added friend from the name list
				potentialFriendNames.remove(nameToAdd);

				System.out.println("\n" + nameToAdd + " successfully added to your friend list!");
				break;
			}

		}

		// After adding a friend, check if there are more friends that the user can add
		if (potentialFriendNames.size() == 0) {
			System.out.println("\n" + "You have no more friend to add!");
			return;
		}

		else {
			System.out.print("\nYou can add 1 or more friends from the following list:\n" + potentialFriendNames
					+ "\n\nDo you want to add a new friend? (Y/N) ");
			checkIfToAddNewFriend();
		}
	}

	private void checkIfToAddNewFriend() {
		while (userInput.hasNextLine()) {

			String userYorN = userInput.nextLine();

			// (1) Prompt the user for a valid input until done
			if (!userYorN.toUpperCase().equals("Y") && !userYorN.toUpperCase().equals("N")) {
				System.out.print("\nInvalid Selection! Please enter 'Y' or 'N': ");
				continue;
			}

			// (2) If user input is Y, call the addNewFriend() method
			else if (userYorN.toUpperCase().equals("Y")) {
				addNewFriend();
				break;
			}

			// (3) If user doesn't want to add new friends, then end the GFR() method
			else if (userYorN.toUpperCase().equals("N")) {
				return;
			}
		}
	}

}

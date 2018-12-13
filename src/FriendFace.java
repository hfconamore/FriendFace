

/**
 * FriendFace.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */


import java.io.IOException;

public class FriendFace {

	public static void main(String[] args) throws IOException {
		FileIO allData = new FileIO();

		int profileCount = allData.getProfileCount();
		List<User> userList = allData.getUserList();
		Hash hashByName = allData.getHashByName();
		HashByInterest hashByInterest = allData.getHashByInterest();
		Graph graph = allData.getGraph();	

		System.out.println(graph.toString());
//		System.out.println(graph.getUserPairs());


//		hashByName.printTable();
//		hashByInterest.printTable();

//		hashByName.printBucket(5);
//		hashByInterest.printBucket(42);

		System.out.println("\nWe have the following users in our database:\n\n" 
							+ userList.printNumberedList());

		
//		System.out.println("hashByName: " + allData.getHashByName().getNumElements());
//		System.out.println(allData.getProfileCount());
//		System.out.println("hashByInterest: " + allData.getHashByInterest().getNumElements());

//		PotentialFriend a = new PotentialFriend("hello world", 78234798);
//		PotentialFriend b = new PotentialFriend("hello world", 3324);
//		System.out.println(a.equals(b));
//		System.out.println(a.compareTo(b));

		Login login = new Login(userList);
		

		User userLoggedIn = login.getUserLoggedIn();
		
		
		Menu menu = new Menu(userLoggedIn, userList, hashByName, hashByInterest, graph);
		menu.promptOptions();
		
//		System.out.println(graph.toString());
//		System.out.println(graph.getUserPairs());
		
		allData.writeConnections(menu.quitApp());
		
		System.out.println( "\n\nBye bye " + userLoggedIn + " ~~~~~");

	}

}

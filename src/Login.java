import java.util.Scanner;

public class Login {

	private String inputUN;
	private String inputPW;
	private List<User> userList;
	private User userLoggedIn;
	
	static Scanner userInput = new Scanner(System.in);

	public Login(List<User> appUserList) {
		userList = appUserList;
		promptLogin();
		
	}
	
	
	public User getUserLoggedIn() {
		return userLoggedIn;
	}
	
	
	private void promptLogin() {
		
		
		System.out.print("\n\nWelcome to FriendFace! Please log in.\n\nEnter your user name: ");

		while (userInput.hasNextLine()) {
			inputUN = userInput.nextLine();
			System.out.print("Enter your password: ");
			inputPW = userInput.nextLine();
			
			userLoggedIn = matchUser(inputUN, inputPW);
			
			if (userLoggedIn == null) {
				System.out.println("\nSorry, your user name and / or password was wrong.\nEnter your user name: ");
				continue;
			}

			else {
				System.out.println("\n\nHello " + userLoggedIn.getFullName() + "!\n");
				System.out.println("Here is your profile detail:\n" + userLoggedIn.getBasicInfo() + "\n");

				break;
			}

		}
		
			
	}
	
	
	
	private User matchUser(String un, String pw) {
		
		userList.pointIterator();
		
		while (!userList.offEnd()) {
			
			User currentU = userList.getIterator();

			if ( currentU.matchCredentials(un, pw) == true ) {
				userLoggedIn = currentU;
				return userLoggedIn;
			}
			userList.advanceIterator();
			
		}
		
		return null;
		
	}
	
	
	
}

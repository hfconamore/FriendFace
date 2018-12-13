import java.util.NoSuchElementException;

/**
 * User.java
 * @author Feng Hong
 * CIS 22C - Final Project
 */


public class User implements Comparable<User> {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private int userID;
	private String city;
	private BST<User> friends;
	private List<String> interests;

	/**** CONSTRUCTOR ****/

	// helper constructor -- for temp users
	public User(String fn, String ln) {
		firstName = fn;
		lastName = ln;
		userName = null;
		password = null;
		userID = -10086; // an invalid id for temp user
		city = null;
		friends = new BST<User>();
		interests = new List<String>();

	}

	// actual constructor for creating valid profiles
	public User(String fn, String ln, String un, String pw, int id, String city, String... interests) {
		firstName = fn;
		lastName = ln;
		userName = un;
		password = pw;
		userID = id;
		this.city = city;
		friends = new BST<User>();
		this.interests = new List<String>();
		for (String interest : interests) {
			this.interests.addLast(interest);
		}
//		 System.out.println(friends.toString());
	}

	/**** Mutators ****/
	public void addFriend(User newFriend) {
		friends.insert(newFriend);
	}
	
	public void removeFriend(User toDelete) {
		try {
		friends.remove(toDelete);
		}
		catch (NoSuchElementException e){
            System.out.println("The person you tried to remove was not your friend.");	
		}
	}

	public boolean matchCredentials(String un, String pw) {
		return userName.equals(un) && password.equals(pw);

	}
	
	
	/**** Accessors ****/

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getCity() {
		return city;
	}

	public int getID() {
		return userID;
	}

	public String getFriends() {
		if (friends.isEmpty()) {
			return "No friend found.";
		}
		return friends.getOrderedItems().toStrByComma();
	}
	
	public BST<User> getFriendsBST() {
		
		return friends;
	}

	public String getInterests() {
		return interests.toStrByComma();
	}
	
	public String getBasicInfo() {
		return getFullName() + " from " + getCity() + " interested in " + getInterests() + ".\n";
	}
	


	/**** Other Methods ****/

	@Override
	public int compareTo(User someGuy) {
		return this.getFullName().compareTo(someGuy.getFullName());

	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof User)) {
			return false;
		} else {
			User other = (User) o;
			if (this.getFullName().equals(other.getFullName())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return getFullName();
	}

	@Override
	public int hashCode() {
		String key = firstName + lastName; // define key for hashing by name
		int sum = 0;

		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;

	}

}

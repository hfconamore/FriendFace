Course Project Information


Project Overview

As a team of five to six programmers, you will assist in the implementation of a data processing system. Each member of the team is to code one or more functional areas of the project. When complete, the individual components will be integrated into one program, which will be finalized in a project demonstration held the last week of the quarter. While the final result is one integrated program, each team member’s work is to be maintained as separate file(s). If this is not possible, clearly document each portion of the project including the name of the developer.



Project Goal

The purpose of this project is to create an interactive social media program, connecting users. The program will maintain user profiles (non-graphic is fine), storing information about their names, locations, interests and a list of their friends, as well as a user name and password for each member. Members should be able to log in to their profiles, add new friends, search for friends, and also get friend recommendations from the program.



Menu Requirements

After logging in, the program should display the user profile and also present a menu of options to the user.

Your project must offer the following options to the user:

- View My Friends
    -View All Friends
    -View a Friend's Profile
    -Remove a Friend
- Search for a New Friend
    - Search by Name
    - Search by Interest
- Get Friend Recommendations
- Quit

The Search for a Friend and Get Friend Recommendations options should also allow the user to add the friend to their network of friends.

At the end of the program, when the user selects the Quit option, the data should all be written to a file to allow for modifications to the data to be read in the next time the program is executed.



Data and Topic Requirements

Data from a file must be preloaded into your program upon start up. An initial file containing at least 15 user profiles is required for the purposes of this project. Your team must select a unique group of famous persons as the initial input to your program. These persons should all come from one specific area of U.S. culture. Some possible examples:
1940s film stars
1990s rap artists
19th Century Poets
San Jose Sharks Team Members
Celebrity Chefs
Note that no other team in the class should have your same data set. Therefore, the instructor may require you to select a different data set if your first choice is already selected by another team (or if deemed inappropriate for a professional setting)

Once you have gathered your collection of data, you will be required to create a class whose fields are the attributes of this data set. For example:

public class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String city;
    private BST<User> friends;
    private List<String> interests;
}

Each record within your data set should be an instance of this class. Note that you should store at least 2 interests per user.


Required Data Structures and Additional Specifications

The system’s data structures must include:

1. A Binary Search Tree, sorted according to friend name, to maintain the list of friends for each user. This can also be used to search your friend list via name.

2. Two hash tables - one using interest as the key and one using name as the key. The two hash table will allow users to search for friends by name or by interest. Once a person is located, the user should have the option to add this person to his or her friend list.

3. A graph to store the network of relationships among the users. Since the graph will be storing data about Users. Unlike your lab assignment where each vertex is an integer, here each vertex will be a String or User Object. Therefore, you will need to assign each user an integer id. You can keep track of which user was assigned which id by storing an id field in your User class (look up the id for a parcticular User), and also keeping a vector where id numbers are the index and the vector stores User objects (look up by index which User was assigned that index).

4. Optional: A linked list to store a list of interests in the User class.

Part of the project will require you to draw a UML class diagram to show the Hash Tables, the BST and the Graph, and other classes, as well as how they interrelate within the program.


Friend Recommendations and the Friend Graph
The friend network among users must be stored as a graph in the form of a vector of linked lists.
When a user selects the Get Friend Recommendations menu option, the program must perform a Breadth First Search to find all the friends within the user's social network (these will include both friends and friends of friends, but will not include anyone who does not share a friend in common with the user).
These friends should then be ranked according to both distance (how many degrees of separation between the user and the person being recommended - i.e. how many edges away in the graph) and whether the potential friend shares one or more interests. Both considerations should be taken into account when ranking friend recommendations. However, it is up to your team to design your own ranking algorithm. You may also include additional information about the user when designing your ranking algorithm, if you wish.
The ranked friend recommendation list should be displayed to the user and the user should then have the option of adding one or more of these recommendations to their friend list.
Note that people who are already friends of the user should not be recommended by the program as potential friends.

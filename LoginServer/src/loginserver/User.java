package loginserver;

import java.util.ArrayList;

/**
 *
 * @author ShaftMaster
 */
public class User {

    private String username;
    private String password;
    private Boolean admin;
    private static ArrayList<User> users = new ArrayList<User>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        User.users = users;
    }

    //Empty constructor
    public User() {

    }

    //Constructor for user, adds object to static arraylist
    public User(String username, String password,
            Boolean admin) {
        this.username = username;
        this.password = String.valueOf(password.hashCode());
        this.admin = admin;
        users.add(this);
    }

    //Generates string to send user details to admin client
    public String sendUser(User user) {
        String send = "Username: " + user.getUsername()
                + "\nPassword: " + user.getPassword();
        return send;
    }

    //Displays one user
    public void displayUser(User user) {
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
    }

    //Displays all users
    public void displayAllUsers(ArrayList<User> users) {
        for (User user : users) {
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
        }
    }

    //Searches for user matching the login details entered
    public User searchUsers(ArrayList<User> users, String searchTerm) {
        User result = null;
        for (User user : users) {
            if (searchTerm.equalsIgnoreCase(user.getUsername())) {
                result = user;
                break;
            }
        }
        return result;
    }
}

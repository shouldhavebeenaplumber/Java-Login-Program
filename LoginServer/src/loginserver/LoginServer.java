package loginserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ShaftMaster
 */
public class LoginServer {

    public static void main(String[] args) throws IOException {
        
        String logAdmin = "logged in as Admin.";
        String logUser = "logged in successfully";
        String logFail = "Login failed.";
        String allUsers = "All users: ";
        
        //Instantiate user class
        User user = new User();

        //Create users
        User user1 = new User("John", "pass1", false);
        User user2 = new User("Jane", "pass2", false);
        User user3 = new User("Joe", "pass3", false);
        User user4 = new User("Jill", "pass4", true); //Admin account

        //Set port number
        final int PORTNUM = 4041;

        //New socket
        ServerSocket serverSocket = new ServerSocket(PORTNUM);
        System.out.println("Server started.");

        //Display users
        System.out.println(allUsers);
        user.displayAllUsers(User.getUsers());

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {
                @Override
                public void run() {
                    try ( PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                            true);  Scanner in = new Scanner(clientSocket.getInputStream());) {
                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            String[] userPass = input.split(":");
                            String usernameInput = userPass[0];
                            String passwordInput = String.valueOf(userPass[1].hashCode());

                            System.out.println("Client login attempt: " + input);

                            User loginAttempt = user.searchUsers(User.getUsers(), usernameInput);
                            
                            //If admin account
                            if (((usernameInput.equalsIgnoreCase(loginAttempt.getUsername()))
                                    && (passwordInput.equalsIgnoreCase(loginAttempt.getPassword())
                                    && (loginAttempt.getAdmin() == true)))) {
                                System.out.println(loginAttempt.getUsername() 
                                        + " " + logAdmin);
                                out.println(loginAttempt.getUsername() 
                                        + " " + logAdmin);
                                
                                //Display users to client if logged in as admin
                                out.println(allUsers);
                                for (User user : User.getUsers()){
                                    out.println(user.sendUser(user));
                                }
                            }
                            //If regular user
                            else if (((usernameInput.equalsIgnoreCase(loginAttempt.getUsername()))
                                    && (passwordInput.equalsIgnoreCase(loginAttempt.getPassword())
                                    && (loginAttempt.getAdmin() == false)))) {
                                System.out.println(loginAttempt.getUsername() 
                                        + " " + logUser);
                                out.println(loginAttempt.getUsername() 
                                        + " " + logUser);
                            } else if (input.equalsIgnoreCase("exit")) {
                                break;
                            } else {
                                System.out.println(logFail);
                                out.println(logFail);
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            };
            t.start();
        }
    }
}

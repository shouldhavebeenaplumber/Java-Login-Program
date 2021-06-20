package loginclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author ShaftMaster
 */
public class LoginClient {
    
    public static void main(String[] args) throws IOException {
        
        //Variables for socket
        final String ADDRESS = "127.0.0.1";
        final int PORT = 4041;
        
        try (
            Socket s = new Socket(ADDRESS, PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            Scanner in = new Scanner(s.getInputStream());
            Scanner sc = new Scanner(System.in);
        ) {
            while (true) {
                //Get user to input login details
                System.out.print("Enter username: ");
                String inputUsername = sc.nextLine();
                
                System.out.print("Enter password: ");
                String inputPassword = sc.nextLine();
                
                //Concatenate before sending
                String sendLogin = inputUsername + ":" + inputPassword;
                
                //Send login string to server
                out.println(sendLogin);
                
                //Exit option
                if (sendLogin.equalsIgnoreCase("exit")) break;
                
                //Display communication from server
                while(in.hasNextLine()){
                    System.out.println("Server: " + in.nextLine());
                }
            }
        }
    }
}

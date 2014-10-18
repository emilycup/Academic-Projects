/**
 * PROJECT: Echo Server And Client
 * CREATED BY: Emily Le
 * LAST UPDATED: 10/7/2014
 * PROFESSOR: Nic Pantic
 *
 * DESCRIPTION: 
 * The client will connect to the server and then the user can type in messages. 
 * These messages are transferred to the server which will then echo them back to the client. 
 * The client should then print the server’s response.
 */	

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import java.util.Scanner;
import java.io.PrintStream;

public final class EchoClient {

    public static void main(String[] args) throws Exception {
    	while (true){
        	try (Socket socket = SocketFactory.getDefault().createSocket("localhost", 22222)) {
        		Scanner kb = new Scanner(System.in);
        		System.out.print("Client> ");
        		String message = kb.nextLine();

        		// Write to server socket
        		PrintStream out = new PrintStream(socket.getOutputStream());
        		out.println(message);

        		// Read from server socket
            	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            	System.out.println(br.readLine());
        	}
        }	
    }
}
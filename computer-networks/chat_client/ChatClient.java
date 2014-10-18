/**
 * PROJECT: Chat Client
 * CREATED BY: Emily Le
 * LAST UPDATED: 10/15/2014
 * PROFESSOR: Nic Pantic
 *
 * DESCRIPTION: 
 * This is a chat client that will connect with the respective server.
 * This client will be able to chat with other clients that are connected on the same server.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import javax.net.SocketFactory;
import java.util.Scanner;
import java.io.PrintStream;

public final class ChatClient {
	Socket socket;
	BufferedReader bufferedReader;
	PrintStream printStream;
	Scanner kb;

	// this is the main method that will start the chat client
	public static void main(String[] args) throws Exception{
		ChatClient chat = new ChatClient();
		chat.go();
	}

	// Sets up the socket parameters and will write user input to the socket. 
	// It will also start a thread that will read incoming messages from the server.
	public void go() throws UnknownHostException, IOException {
		socket = SocketFactory.getDefault().createSocket(
				"76.91.123.97", 22222);
		InputStreamReader streamReader = new InputStreamReader(
				socket.getInputStream());
		bufferedReader = new BufferedReader(streamReader);
		printStream = new PrintStream(socket.getOutputStream());

		// writing to the server socket. The first string entered here will be
		// the user name
		kb = new Scanner(System.in);
		String message = kb.nextLine();
		printStream.println(message);

		// a new thread is started here, calling on the 'IncomingReader' class.
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		while (true) {

			// writing to the server socket
			message = kb.nextLine();
			printStream.println(message);
		}
	}

	// Thread that reads and prints the message(s) from the server.
	public class IncomingReader implements Runnable {
		public void run() {
			String incomingMessage;
			try {
				while ((incomingMessage = bufferedReader.readLine()) != null) {
					System.out.println(incomingMessage + "\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

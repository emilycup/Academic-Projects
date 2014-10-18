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

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class EchoServer {

    public static void main(String[] args) throws Exception {
        int loopCounter = 0;
        ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(22222);

        while (true) {
            try (Socket socket = serverSocket.accept()) {
                if(loopCounter == 0){
                    System.out.println("Client connected: " + socket.getInetAddress());
                }

                // read from client socket
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String echo = br.readLine();

                // write to client socket
                PrintStream out = new PrintStream(socket.getOutputStream());
                out.println("Server> " + echo);           
            }
            loopCounter++;
        }
    }
}
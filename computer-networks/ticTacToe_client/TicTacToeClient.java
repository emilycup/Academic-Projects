// *****************************************************************************
// PROJECT: Tic Tac Toe Client
// CREATED BY: Emily Le 
// LAST UPDATED: 11/22/2014
// PROFESSOR: Nic Pantic
//
// PROJECT SUMMARY: This is a Tic Tac Toe Client that will interact with the 
// specified server. The program will first prompt a user to enter in a user 
// name. The program will then respond by prompting a list of available players 
// on the server. The user can then select a player to play against on the 
// server or type 'computer' to play against an AI. During game-play, the user 
// can choose to: move, surrender, or exit the game.
// *****************************************************************************

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.net.SocketFactory;

public class TicTacToeClient implements Serializable
{
	// Default serial version ID
	private static final long serialVersionUID = 1L;

	Socket socket;
	BufferedReader bufferedReader;
	ObjectInputStream objectInStream;
	ObjectOutputStream objectOutputStream;
	boolean inGame = false;

	// initialize objects
	BoardMessage boardMessage;
	CommandMessage commandMessage;
	ConnectMessage connectMessage;
	ErrorMessage errorMessage;
	MoveMessage moveMessage;
	PlayerListMessage playerListMessage;
	StartGameMessage startGameMessage;

	Scanner kb = new Scanner(System.in);

	// *************************************************************************
	// MAIN METHOD:
	// calls on other methods to run program
	// *************************************************************************
	public static void main(String[] args) throws UnknownHostException,
			IOException
	{
		TicTacToeClient art = new TicTacToeClient();
		art.go();
	}

	// will set up the connection to the server
	public void go() throws UnknownHostException, IOException
	{
		socket = SocketFactory.getDefault().createSocket("76.91.123.97", 22222);
		objectInStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

		System.out.println("********** WELCOME TO TIC TAC TOE! **********");
		System.out.println();

		// sends serialized connectMessage object with username to server
		System.out.println("Please enter a user name: ");
		String username = kb.nextLine();
		connectMessage = new ConnectMessage(username);
		objectOutputStream.writeObject(connectMessage);

		// start reading thread
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		if (!inGame)
		{
			// send command to get list of players from server
			commandMessage = new CommandMessage(
					CommandMessage.Command.LIST_PLAYERS);
			objectOutputStream.writeObject(commandMessage);

			// start game with a second player
			String playerChoice = kb.nextLine();

			if (playerChoice.compareTo("computer") == 0)
			{
				playerChoice = null;
				startGameMessage = new StartGameMessage(playerChoice);
				objectOutputStream.writeObject(startGameMessage);
			}
			else
			{
				startGameMessage = new StartGameMessage(playerChoice);
				objectOutputStream.writeObject(startGameMessage);
			}
		}
	}

	// *************************************************************************
	// INCOMING READER THREAD:
	// A class that holds a separate thread for receiving messages from the
	// server.
	// *************************************************************************
	public class IncomingReader implements Runnable
	{
		public void run()
		{
			Object incomingMessage;
			try
			{
				while ((incomingMessage = objectInStream.readObject()) != null)
				{
					switch (((Message) incomingMessage).getType())
					{
						case CONNECT:
							System.out.println("Connect message");
							break;
						case COMMAND:
							System.out.println("Command message");
							break;
						case BOARD:
							inGame = true;
							recieveBoardMessage((BoardMessage) incomingMessage);
							handleBoardMessages((BoardMessage) incomingMessage);
							while (inGame)
							{
								promptPosition();
								inGame = false;
							}

							break;
						case MOVE:
							System.out.println("Move message");

							break;
						case ERROR:
							System.out.println(((ErrorMessage) incomingMessage)
									.getError());
							handleErrors(((ErrorMessage) incomingMessage));
							break;
						case START_GAME:
							System.out.println("Start message");

							break;
						case PLAYER_LIST:
							recievePlayerList((PlayerListMessage) incomingMessage);
							break;
						default:
							System.out.println("IT'S BROKEN!");

							break;
					}
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// *************************************************************************
	// PROMPT POSITION:
	// This method will prompt the user to enter a position by giving a row and
	// column.
	// *************************************************************************
	public void promptPosition() throws IOException
	{
		System.out.println("Please choose from the following: ");
		System.out.println("1. Move");
		System.out.println("2. Surrender");
		System.out.println("3. Quit");

		String choice = kb.nextLine();
		switch (choice)
		{
			case "1":
				System.out
						.println("Enter the Row and Column separated by a space:");
				String[] input = kb.nextLine().split(" ");

				byte row = Byte.parseByte(input[0]);
				byte col = Byte.parseByte(input[1]);

				moveMessage = new MoveMessage(row, col);
				objectOutputStream.writeObject(moveMessage);
				break;
			case "2":
				System.out.println("You have surrendured!");
				objectOutputStream
						.writeObject(CommandMessage.Command.SURRENDER);
				exitProgram();
				break;
			case "3":
				System.out.println("You have exited the game.");
				exitProgram();
				break;
			default:
				System.out.println("Invalid input. Try Again.");
				promptPosition();
		}
	}

	// *************************************************************************
	// HANDLE ERRORS:
	// Handles errors the server sends to the client
	// *************************************************************************
	public void handleErrors(ErrorMessage error) throws IOException
	{
		String handle = error.getError();
		if (handle.contains("No player found: "))
		{
			exitProgram();
		}
		if (handle.contains("is already in a game."))
		{
			exitProgram();
		}
		switch (handle)
		{
			case "Name in use.":
				exitProgram();
				break;
			case "It's not your turn.":
				break;
			case "That move is illegal.":
				exitProgram();
				break;
		}
	}

	// *************************************************************************
	// HANDLE BOARD MESSAGES:
	// This method handles board messages for certain situations in which the
	// client or opponent: surrenders, wins, loses, or have reached a stale
	// mate.
	// *************************************************************************
	public void handleBoardMessages(BoardMessage message) throws IOException
	{
		switch (message.getStatus())
		{
			case PLAYER1_SURRENDER:
				System.out.println("You just surrendured. You Lose. :(");
				exitProgram();
				break;
			case PLAYER2_SURRENDER:
				System.out.println("Your opponent surrendured. You win!");
				exitProgram();
				break;
			case PLAYER1_VICTORY:
				System.out.println("Congratulations you won!");
				exitProgram();
				break;
			case PLAYER2_VICTORY:
				System.out.println("You lose! :(");
				exitProgram();
				break;
			case STALEMATE:
				System.out
						.println("You have reached a stalemate. Nobody wins!");
				exitProgram();
				break;
			case IN_PROGRESS:
				break;
			case ERROR:
				break;
		}
	}

	// *************************************************************************
	// EXIT PROGRAM:
	// This method exits out of the game and removes the player from the server.
	// *************************************************************************
	public void exitProgram() throws IOException
	{
		System.out.println("exiting now...");
		commandMessage = new CommandMessage(CommandMessage.Command.EXIT);
		objectOutputStream.writeObject(commandMessage);
		System.exit(0);
	}

	// *************************************************************************
	// RECIEVE BOARD MESSAGE:
	// Prints out the board layout.
	// *************************************************************************
	public void recieveBoardMessage(BoardMessage boardMessage)
			throws IOException
	{
		byte[][] board = boardMessage.getBoard();

		System.out.println();
		System.out.println("   0  1  2");
		for (int i = 0; i < board.length; i++)
		{
			System.out.print(i + " ");
			for (int j = 0; j < board.length; j++)
			{
				switch (board[i][j])
				{
					case 0:
						System.out.print(" _ ");
						break;
					case 1:
						System.out.print(" O ");
						break;
					case 2:
						System.out.print(" X ");
						break;
				}
			}
			System.out.println();
		}
	}

	// *************************************************************************
	// RECIEVE PLAYER LIST
	// *************************************************************************
	public void recievePlayerList(PlayerListMessage playerListMessage)
			throws IOException
	{
		String[] playerList = playerListMessage.getPlayers();

		System.out
				.println("Enter the name of the person you would like to play against: ");
		System.out.println("(Type 'computer' to play against the computer)");
		System.out.println();

		System.out.println("List of online players:");
		for (int i = 1; i <= playerList.length; i++)
		{
			System.out.println(i + ". " + playerList[i - 1]);
		}
	}
}

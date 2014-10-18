import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is the User InterfaceClass that will allow the user to directly interact
 * with the program.
 * 
 * @author Emily Le
 * 
 */
class UserInterface {

	/**
	 * This is the Scanner object that will read in all of the user's input.
	 */
	Scanner kb = new Scanner(System.in);

	/**
	 * This is a variable that will hold the value of the root object.
	 */
	BSTNode<Integer> root = new BSTNode<Integer>(null, null, null);

	/**
	 * This is a variable that will hold the value of the user's input.
	 */
	int userInput;

	/**
	 * This method holds the initial messages that will appear at the start of
	 * the program. This method will also fill the initial tree with numbers
	 * specified by the user.
	 */

	public void makeInitialMessages() {
		System.out.println("--- WELCOME TO THE BINARY SEARCH TREE! ---");

		boolean validInput = true;
		int moreInput = 0;
		do {
			validInput = true;
			System.out.println("Please enter the initial sequence of values:");
			String input = kb.nextLine();

			@SuppressWarnings("resource")
			Scanner inputScanner = new Scanner(input);
			while (inputScanner.hasNext()) {
				try {
					moreInput = inputScanner.nextInt();
				} catch (InputMismatchException e) {

					System.out
							.println("You have entered one or more invalid inputs. Please Try again.");
					validInput = false;
					break;
				}
				root.add(root, moreInput);
			}
		} while (!validInput);

		printTraversal();
	}

	/**
	 * 
	 * This method prints prints out all of the specified traversals: Pre-Order
	 * Traversal, In-Order Traversal, and Post-Order Traversal. This will be
	 * called by the {@link #makeInitialMessages()} method.
	 */
	public void printTraversal() {
		// pre-order traversal
		System.out.print("Pre-Order: ");
		root.preOrderTraversal(root);
		System.out.println();

		// in-order traversal
		System.out.print("In-Order: ");
		root.inOrderTraversal(root);
		System.out.println();

		// post-order traversal
		System.out.print("Post-Order: ");
		root.postOrderTraversal(root);
		System.out.println();
	}

	/**
	 * This method just prints the specific In-Order Traversal. This contrasts
	 * with the initial {@link #printTraversal()} method. This will be called by
	 * the {@link #insertValue()} and {@link #deleteValue()} method.
	 */
	public void printInOrder() {
		// in-order traversal
		System.out.print("In-Order: ");
		root.inOrderTraversal(root);
		System.out.println();
	}

	/**
	 * This method creates and displays the main menu that will allow the user
	 * to interact with the program and choose from the following choices:
	 * Insert a value, Delete a Value, Exit the program, and Display this
	 * Message.
	 */
	public void makeMainMenu() {
		String userInput;
		boolean validInput = true;
		do {
			validInput = true;
			System.out.println("Please enter a command (Type 'H' for help): ");
			userInput = kb.nextLine().toUpperCase();

			if (userInput.compareTo("I") != 0 && userInput.compareTo("D") != 0
					&& userInput.compareTo("E") != 0
					&& userInput.compareTo("H") != 0) {
				System.out
						.println("This is an invalid command. Please try again.");
				validInput = false;
			}
		} while (!validInput);

		switch (userInput) {

		// insert a value
		case "I":
			insertValue();
			break;

		// delete a value
		case "D":
			deleteValue();
			break;

		// exit the program
		case "E":
			exitProgram();
			break;

		// display this message
		case "H":
			displayMessage();
			break;
		}

	}

	/**
	 * This method will insert a user-specified node value. This method will be
	 * called by the {@link #makeMainMenu()} method.
	 */
	public void insertValue() {
		boolean validInput = true;
		do {
			validInput = true;
			System.out.print("Please enter a value: ");
			try {
				userInput = kb.nextInt();
				kb.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.out
						.println("You entered an invalid input. Please try again.");
				kb.nextLine();
				validInput = false;
			}
		} while (!validInput);

		if (!root.add(root, userInput)) {
			System.out.println("There already exists '" + userInput
					+ "' in the Binary Search Tree.");
		}
		// prints In-Order Traversal
		printInOrder();
	}

	/**
	 * This method will delete a user-specified node value (if it exists). This
	 * will be called by the {@link #makeMainMenu()} method.
	 */
	public void deleteValue() {
		boolean validInput = true;
		boolean elementFound;
		do {
			validInput = true;
			System.out.println("Please enter a value: ");

			try {
				userInput = kb.nextInt();
				kb.nextLine();

			} catch (InputMismatchException e) {
				System.out
						.println("You entered an invalid input. Please Try again.");
				validInput = false;
				kb.nextLine();
			}
			// calls remove method
			elementFound = root.remove(root, userInput);

			if (!elementFound) {
				System.out
						.println("This number does not exist in the Binary Search Tree.");
			}

			// prints in-order traversal
			if (validInput) {
				printInOrder();
			}
		} while (!validInput);
	}

	/**
	 * This method will exit the program when selected and is called by the
	 * {@link #makeMainMenu()} method.
	 */
	public void exitProgram() {
		System.out
				.println("--- The Program has exited. Thank you for using! ---");
		System.exit(0);
	}

	/**
	 * This method will display the 'help' message. This will be called by the
	 * {@link #makeMainMenu()} method.
	 */
	public void displayMessage() {
		System.out
				.println(" 'I'   Insert a Value \n 'D'   Delete a value \n 'E'   Exit the Program \n 'H' Display this Message");
	}

}

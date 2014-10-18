

/**
 * This is the Main class that will make calls to other classes and their
 * methods in order to run the program.
 * 
 * @author Emily Le
 * 
 */
public class Project1 {

	/**
	 * This is the main method that will run the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface userInterface = new UserInterface();

		userInterface.makeInitialMessages();
		do {
			userInterface.makeMainMenu();
		} while (true);

	}

}
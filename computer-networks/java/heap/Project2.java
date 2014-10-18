/**
 * This is the Main class that will make calls to other classes and their
 * methods in order to run the program.
 * 
 * @author Emily Le
 * 
 */
public class Project2 {

	/**
	 * This is the main method that will run the program and will display the
	 * first initial message.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Heap heap = new Heap();
		UserInterface userInterface = new UserInterface();

		System.out.println("--- WELCOME TO THE HEAP PROGRAM! ---");
		do {
			userInterface.makeMainMenu();
		} while (true);
	}

}
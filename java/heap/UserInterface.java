import java.util.Random;
import java.util.Scanner;

/**
 * This is the user interface that will allow the user to interact with the
 * program.
 * 
 * @author Emily Le
 * 
 */
class UserInterface {

	Scanner kb = new Scanner(System.in);
	Random rd = new Random();

	Heap heap = new Heap();

	String userInput;
	boolean validInput = true;

	/**
	 * This method will make the main menu of the program. It will ask the use
	 * to choose from three choices: 1. Test the program with 100 randomly
	 * generated integers, 2. Test the program with fixed values (1-100), and
	 * 3.Exit the program. This method will also call on other methods to
	 * execute the desired choice.
	 */
	public void makeMainMenu() {
		String choicesInput;

		do {
			validInput = true;
			System.out.println("Please choose one of the following choices:");
			System.out
					.println("1. Test the program with 100 randomly generated integers");
			System.out.println("2. Test the program with fixed values (1-100)");
			System.out.println("3. Exit the program");
			userInput = kb.nextLine();

			if (userInput.compareTo("1") != 0 && userInput.compareTo("2") != 0
					&& userInput.compareTo("3") != 0) {
				System.out.println();
				System.out.println("Invalid input. Please try again!");
				validInput = false;
			}

		} while (!validInput);

		switch (userInput) {
		case "1":
			choicesInput = makeChoices();
			if (choicesInput.compareTo("1") == 0)
				insertionRandomIntegers();
			if (choicesInput.compareTo("2") == 0)
				optimalRandomIntegers();
			break;
		case "2":
			choicesInput = makeChoices();
			if (choicesInput.compareTo("1") == 0)
				insertionFixedValues();
			if (choicesInput.compareTo("2") == 0)
				optimalFixedValues();
			break;
		case "3":
			exit();
			break;
		}
	}

	/**
	 * This is a sub-menu that will give the user more choices on how to fill
	 * the heap: (1) With Sequential Insertions (2) The Optimal Method.
	 * 
	 * @return
	 */
	public String makeChoices() {

		do {
			validInput = true;
			System.out.println("How would you want to fill the heap?: ");
			System.out
					.println("1. With Sequential Insertions \n2. The Optimal method");
			userInput = kb.nextLine();
			if (userInput.compareTo("1") != 0 && userInput.compareTo("2") != 0) {
				System.out.println("Invalid input. Please try again!");
				validInput = false;
			}
		} while (!validInput);

		return userInput;
	}

	/**
	 * This method will execute one of the choices from the main menu: (test the
	 * program with 100 randomly generated integers). This method will add in
	 * random numbers with a series of insertions. The number of swaps will also
	 * display itself after the heap has been sorted and filled.
	 */
	public void insertionRandomIntegers() {
		for (int i = 0; i < 100; i++) {
			int randomInteger = rd.nextInt();
			heap.addInsertionWay(randomInteger);
		}

		System.out.print("Heap: ");
		heap.print();
		System.out.println("Swap Count: " + heap.getSwapCount());
		System.out.println();

		heap = new Heap();
	}

	/**
	 * This method will execute one of the choices from the main menu: (test the
	 * program with 100 randomly generated integers). This method will add in
	 * random numbers by using the optimal method. The number of swaps will also
	 * display itself after the heap has been sorted and filled.
	 */
	public void optimalRandomIntegers() {
		for (int i = 0; i < 100; i++) {
			int randomInteger = rd.nextInt();
			heap.add(randomInteger);
		}
		// will do an optimal reheap on the filled heap
		heap.optimalReheapUp(99);

		System.out.print("Heap: ");
		heap.print();
		System.out.println("Swap Count: " + heap.getSwapCount());
		System.out.println();

		heap = new Heap();

	}

	/**
	 * This method will execute one from the choices from the main menu: (test
	 * the program with fixed values (1-100)). This method will add in
	 * sequential numbers by the insertion method. Only the first 10 numbers
	 * will display. This method will then perform 10 removals and will display
	 * again the new 10 first integers. The The number of swaps will also
	 * display each time the heap has been sorted and filled.
	 */
	public void insertionFixedValues() {
		for (int i = 1; i < 101; i++) {
			heap.addInsertionWay(i);
		}

		// print first 10 values
		System.out.print("First 10 Values: ");
		heap.printFirstTen();
		System.out.println();
		System.out.println("Swap Count: " + heap.getSwapCount());

		// removes first 10 values
		heap.removeFirstTen();

		// prints first 10 values
		System.out.print("First 10 values after removal: ");
		heap.printFirstTen();
		System.out.println();
		System.out.println("Swap Count: " + heap.getSwapCount());
		System.out.println();

		heap = new Heap();
	}

	/**
	 * This method will execute one from the choices from the main menu: (test
	 * the program with fixed values (1-100)).This method will add in the
	 * sequential numbers by the optimal method. This method will then perform
	 * 10 removals and will display again the new 10 first integers. The The
	 * number of swaps will also display each time the heap has been sorted and
	 * filled.
	 */
	public void optimalFixedValues() {
		for (int i = 1; i < 101; i++) {
			heap.add(i);
		}

		// will do an optimal reheap on the filled heap.
		heap.optimalReheapUp(99);

		// print first 10 values
		System.out.print("First 10 Values: ");
		heap.printFirstTen();
		System.out.println();
		System.out.println("Swap Count: " + heap.getSwapCount());

		// removes first 10 values
		heap.removeFirstTen();

		// prints first 10 values
		System.out.print("First 10 values after removal: ");
		heap.printFirstTen();
		System.out.println();
		System.out.println("Swap Count: " + heap.getSwapCount());
		System.out.println();

		heap = new Heap();
	}

	/**
	 * This method will exit the program. It will also print the final messages
	 * to indicate the program has exited.
	 */
	public void exit() {
		System.out.println("--- Thank you for using this program! ---");
		System.exit(0);
	}

}

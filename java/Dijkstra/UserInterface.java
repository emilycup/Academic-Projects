import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This is the user interface class that will allow the user to interact
 * directly with the program.
 * 
 * @author Emily Le
 * 
 */
class UserInterface {
	Scanner kb = new Scanner(System.in);
	Diagraph diagraph;
	String userInput;
	boolean validInput;

	/**
	 * This is the constructor that will declare the diagraph object.
	 */
	public UserInterface(Diagraph mainDiagraph) {
		diagraph = mainDiagraph;
	}

	/**
	 * This method will make display the main menu and will allow the user to
	 * choose from a set of 6 choices: Q. query the city information by entering
	 * the city code, D. find the minimum distance between two cities, I. insert
	 * a road by entering two city codes and a distance, R. remove an existing
	 * road by entering two city codes and a distance, and E. exit.
	 */
	public void makeMainMenu() {
		do {
			validInput = true;
			System.out.println();
			System.out.println("Please choose a command: ");
			System.out
					.println("Q. Query the city information by entering the city code "
							+ "\nD. Find the minimum distance between two cities "
							+ "\nI. Insert a road by inserting two city codes and distance"
							+ "\nR. Remove an existing road by entering two city codes and distance"
							+ "\nE. Exit");
			userInput = kb.nextLine().toUpperCase();
			if (userInput.compareTo("Q") != 0 && userInput.compareTo("D") != 0
					&& userInput.compareTo("I") != 0
					&& userInput.compareTo("R") != 0
					&& userInput.compareTo("E") != 0) {
				System.out.println("Invalid input. Please try again!");
				validInput = false;
			}
		} while (!validInput);
		switch (userInput) {
		case "Q":
			queryCityInfo();
			break;
		case "D":
			findMinimumDistance();
			break;
		case "I":
			insertRoad();
			break;
		case "R":
			removeRoad();
			break;
		case "E":
			exit();
			break;
		}
	}

	/**
	 * This method will query in a specific city chosen by the user. This method
	 * will be called by the {@link #makeMainMenu()} method.
	 */
	public void queryCityInfo() {
		do {
			validInput = true;
			System.out
					.println("Please type in a city code (type 'CODES' to see code list): ");
			userInput = kb.nextLine().toUpperCase();
			if (userInput.compareTo("CODES") == 0) {
				printCityCodes();
				validInput = false;
			}
			// print city info
			int index = diagraph.binarySearch(userInput);
			if (index == -1) {
				System.out.println("Invalid Input! Please try again.");
				System.out.println();
				validInput = false;
			} else
				diagraph.printcityinfo(index);
		} while (!validInput);
	}

	/**
	 * This method display the minimum distance and path from one city to
	 * another. This method will be called by the {@link #makeMainMenu()}
	 * method.
	 */
	// work on this method
	public void findMinimumDistance() {
		String city1 = null;
		String city2 = null;

		do {
			validInput = true;
			System.out
					.println("Please enter in 2 city codes (type 'CODES' to see code list)");
			String userInput = kb.nextLine().toUpperCase();

			if (userInput.compareTo("CODES") == 0) {
				printCityCodes();
				validInput = false;
			} else {
				@SuppressWarnings("resource")
				Scanner inputScanner = new Scanner(userInput);
				try {
					city1 = inputScanner.next();
					city2 = inputScanner.next();
					if (inputScanner.hasNext()) {
						System.out.println("Invalid input. Please try again.");
						validInput = false;
					}
				} catch (Exception e) {
					System.out.println("Invalid input. Please try again.");
					validInput = false;
				}
			}
		} while (!validInput);
		if (city1.compareTo(city2) == 0) {
			System.out.println("You are already in the city!");
		} else {

			int indexCity1 = diagraph.binarySearch(city1);
			int indexCity2 = diagraph.binarySearch(city2);

			if (indexCity1 == -1 || indexCity2 == -1) {
				System.out.println("This road does not exist.");
				validInput = false;
			} else {
				// call method to calculate shortest distance
				int[] previous = diagraph.shortestDistance(indexCity1);

				int totalDistance = diagraph.getDistance(indexCity2);
				if (previous != null) {
					System.out.print("The minimum distance between " + city1
							+ " and " + city2 + " is " + totalDistance + " "
							+ "through the route: " + " ");
					diagraph.printprevious(previous, indexCity1, indexCity2);
				} else {
					System.out.println("There is no shortest path.");
				}
			}
		}
	}

	/**
	 * This method will insert a road given 2 cities and the distance between
	 * them. This method will be called by the {@link #makeMainMenu()} method.
	 */
	// make a case to display if user input the same cities
	public void insertRoad() {
		String city1 = null;
		String city2 = null;
		int distance = 0;

		do {
			validInput = true;
			System.out
					.println("Please enter in 2 city codes and a distance(type 'CODES' to see code list): ");
			String userInput = kb.nextLine().toUpperCase();

			if (userInput.compareTo("CODES") == 0) {
				printCityCodes();
				validInput = false;
			} else {
				@SuppressWarnings("resource")
				Scanner inputScanner = new Scanner(userInput);
				try {
					city1 = inputScanner.next();
					city2 = inputScanner.next();
					distance = inputScanner.nextInt();
					if (inputScanner.hasNext()) {
						System.out.println("Invalid input. Please try again.");
						validInput = false;
					}
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again.");
					validInput = false;
				}
			}
		} while (!validInput);

		if (city1.compareTo(city2) == 0) {
			System.out.println("This is the same city. Nice try.");
		} else {
			// search for city indexes
			int indexCity1 = diagraph.binarySearch(city1);
			int indexCity2 = diagraph.binarySearch(city2);
			// road already exists
			if (diagraph.isEdge(indexCity1, indexCity2)) {
				System.out.println("This road already exists.");
				// road does not exist
			} else {
				if (indexCity1 == -1 || indexCity2 == -1)
					System.out
							.println("This road does not exist. Please try again.");
				else {
					// add road
					diagraph.addRoad(indexCity1, indexCity2, distance);
					System.out.println("you have inserted " + city1 + " and "
							+ city2 + " with a distance of " + distance + ".");
				}
			}
		}
	}

	/**
	 * This method will remove a road given two cities. This method will be
	 * called by the {@link #makeMainMenu()} method.
	 */
	public void removeRoad() {
		String city1 = null;
		String city2 = null;

		do {
			validInput = true;
			System.out
					.println("Please enter in 2 city codes (type 'CODES' to see code list): ");
			String userInput = kb.nextLine().toUpperCase();

			if (userInput.compareTo("CODES") == 0) {
				printCityCodes();
				validInput = false;
			} else {
				Scanner inputScanner = new Scanner(userInput);
				try {
					city1 = inputScanner.next();
					city2 = inputScanner.next();
					if (inputScanner.hasNext()) {
						System.out.println("Invalid input. Please try again.");
						validInput = false;
					}
				} catch (Exception e) {
					System.out.println("Invalid input! Please try again.");
					validInput = false;
				}
			}
		} while (!validInput);

		if (city1.compareTo(city2) == 0) {
			System.out.println("The road does not exist!");
		} else {
			// search for city indexes
			int indexCity1 = diagraph.binarySearch(city1);
			int indexCity2 = diagraph.binarySearch(city2);
			if (indexCity1 == -1 || indexCity2 == -1
					|| !diagraph.isEdge(indexCity1, indexCity2))
				System.out.println("This road does not exist.");
			else {
				// method call to remove edge
				diagraph.removeEdge(indexCity1, indexCity2);
				System.out.println("you have removed " + city1 + " and "
						+ city2 + " from the map.");
			}
		}
	}

	/**
	 * This method will allow the user to exit the program and will display a
	 * final message. This method will be called by the {@link #makeMainMenu()}
	 * method.
	 */
	public void exit() {
		System.out.println("--- Thank you for using this program! ---");
		System.exit(0);
	}

	/**
	 * This method will display all of the possible city codes along with their
	 * corresponding cities.
	 */
	public void printCityCodes() {
		System.out.println("AN   ANAHEIM");
		System.out.println("BK   BAKERSFIELD");
		System.out.println("BO   BOSSTOWN");
		System.out.println("BR   BREA CANYON");
		System.out.println("CH   CHINO HILLS");
		System.out.println("ED   EDWIN DOM");
		System.out.println("FI   FORT IRWIN");
		System.out.println("GD   GARDENA");
		System.out.println("GG   GARDEN GROVE");
		System.out.println("KV   KEANVILLE");
		System.out.println("LI   LAKE ISABELLA");
		System.out.println("LV   LEE VINING");
		System.out.println("MP   MOUNTAIN PASS");
		System.out.println("PD   PARKER DAM");
		System.out.println("PM   POMONA");
		System.out.println("PR   PICO RIVERA");
		System.out.println("SB   SAN BERADINO");
		System.out.println("TR   TORRANCE");
		System.out.println("VV   VICTORVILLE");
		System.out.println("WW   WRIGHTWOOD");
	}
}

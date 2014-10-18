import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the Main class that will make calls to other classes and their
 * methods in order to run the program.
 * 
 * @author Emily Le
 * 
 */
public class Project3 {
	/**
	 * This is the main method that will run the program and will display the
	 * first initial message.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Diagraph diagraph = importFile();
		UserInterface userInterface = new UserInterface(diagraph);
		System.out.println("--- WELCOME TO THE MAP APPLICATION ---");
		do {
			userInterface.makeMainMenu();
		} while (true);
	}

	/**
	 * This method will read in the 'city.dat' and 'road.dat' file and will load
	 * the information into the diagraph data structure.
	 * 
	 */
	public static Diagraph importFile() {
		Diagraph diagraph = new Diagraph(20);

		File city = new File("city.dat");
		File road = new File("road.dat");
		Scanner cityInfo = null;
		Scanner roadInfo = null;

		try {
			cityInfo = new Scanner(city);
			roadInfo = new Scanner(road);
		} catch (FileNotFoundException e) {
			System.out.println("File was not found.");
		}

		while (cityInfo.hasNext()) {
			int index = cityInfo.nextInt() - 1;
			String cityCode = cityInfo.next();
			String cityMisc = cityInfo.nextLine();
			diagraph.addCity(index, cityCode, cityMisc);
		}
		cityInfo.close();

		while (roadInfo.hasNext()) {
			int source = roadInfo.nextInt() - 1;
			int target = roadInfo.nextInt() - 1;
			int distance = roadInfo.nextInt();
			diagraph.addRoad(source, target, distance);
		}
		roadInfo.close();
		return diagraph;
	}
}

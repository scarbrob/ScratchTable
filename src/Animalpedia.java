import java.io.*;
import java.util.Scanner;

/**
 * This is a program that creates two different hash tables from a file called
 * "animals.tsv" and interacts with the user by responding with the type of
 * animal and how many legs it has after an animal name is entered.
 * 
 * @author Ben Scarbrough
 * @Version 1.0
 *
 */
class Animalpedia {
	private static HashTable<String, String> animalType;
	private static HashTable<String, Integer> animalLegs;

	/**
	 * A private method to create a HashTable from a file.
	 * 
	 * @param fileName
	 *            The file to load.
	 * @return A SequentialSearchSymbolTable of the file.
	 */
	private static void loadFileIn(String fileName) {
		animalType = new HashTable<>(101);
		animalLegs = new HashTable<>(101);
		try {
			Scanner file_in = new Scanner(new File(fileName));
			while (file_in.hasNextLine()) {
				String line = file_in.nextLine();
				String[] tokens = line.split("\t");
				Integer legs = Integer.parseInt(tokens[2]);
				animalType.put(tokens[0], tokens[1]);
				animalLegs.put(tokens[0], legs);
			}
			file_in.close();
		} catch (IOException exception) {
			System.out.println(exception.toString());
			System.exit(0);
		}
	}

	// Main Method
	private static void queryUser() {
		Scanner scan = new Scanner(System.in);
		String fileName = "animals.tsv";
		System.out.println("Welcome to Animalpedia!");
		System.out.println("File " + "\"" + fileName + "\"" + " loaded. \n");
		loadFileIn(fileName);
		System.out.println("Please enter an animal, or hit enter to quit.");
		System.out.println("\n");
		while (true) {
			System.out.print("Your animal: ");
			String input = scan.nextLine();
			if (input.length() == 0) {
				System.out.println("Goodbye!");
				scan.close();
				System.exit(0);
			} else if (animalType.get(input) == null || (animalLegs.get(input) == null)) {
				System.out.println("I'm sorry. I dont know what " + "\"" + input + "\"" + " is. \n");
			} else {
				System.out.println("A " + input + " is a " + animalType.get(input) + ", that has "
						+ animalLegs.get(input) + " legs. \n");
			}
		}
	}

	public static void main(String[] args) {
		queryUser();
	}
}

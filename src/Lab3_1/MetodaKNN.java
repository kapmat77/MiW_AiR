package Lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MetodaKNN {

	private static String DATA_PATH = "src/Lab3_1/resources/dataIris.txt";
	private static List<Object> objectsList = new ArrayList<>(); //TODO ???? WTF
	private static Iris inputIris = new Iris();
	
	public static void main(String[] args) {
//		choseObjectsToRead();
		readDataFromFile();
		setInputIris();
//		countDistances();
	}

	private static void setInputIris() {
		List<String> input = new ArrayList<>();
		for (int i=0; i<4; i++) {
			System.out.println("Podaj parametr nr."+(i+1)+": ");
			Scanner in = new Scanner(System.in);
			input.add(in.nextLine());
		}
		inputIris = new Iris(Double.valueOf(input.get(0)), Double.valueOf(input.get(1)), Double.valueOf(input.get(2)),
				Double.valueOf(input.get(3)), Iris.convertStringToType("none"));
		System.out.println(inputIris.toString());
	}
	
	private static int readDataFromFile() {
		File dataFile = new File(DATA_PATH);
		try {
			Scanner in = new Scanner(dataFile);
			String[] parts;
			Iris singleIris;
			String line = in.nextLine();
			while(in.hasNextLine()) {
				line = in.nextLine();
				line = line.replace(",", ".");
				parts = line.split("\t");
				singleIris = new Iris(Double.valueOf(parts[0]), Double.valueOf(parts[1]), Double.valueOf(parts[2]),
						Double.valueOf(parts[3]), Iris.convertStringToType(parts[4]));
				objectsList.add(singleIris);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Plik nie zostal wczytany poprawnie");
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
}

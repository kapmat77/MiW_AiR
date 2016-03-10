package Lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MetodaKNN {

	private static List<Object> objectsList = new ArrayList<>(); //TODO ???? WTF
	private static Iris inputIris = new Iris();
	
	public static void main(String[] args) {
		String dataType = chooseType();
		String dataPath = "src/Lab3_1/resources/data" + dataType +".txt";
		readDataFromFile(dataPath, dataType);
		setInputIris();
//		countDistances();
	}

	private static String chooseType() {
		System.out.println("Wpisz numer wczytywanego obiektu");
		System.out.println("1.Iris");
		System.out.println("2.Wine");
		Scanner in = new Scanner(System.in);
		while (true) {
			switch (in.nextLine()) {
				case "1":
					return "Iris";
				case "2":
					return "Wine";
				default:
					System.out.println("Zła liczba. Wybierz ponownie.");
			}
		}
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
	
	private static int readDataFromFile(String path, String typeOfObject) {
		Object singleObject = null;
		File dataFile = new File(path);
		try {
			Scanner in = new Scanner(dataFile);
			String[] parameters;
			String line = in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				line = line.replace(",", ".");
				parameters = line.split("\t");
				switch (typeOfObject) {
					case "Iris":
						singleObject = new Iris(parameters);
						break;
					case "Wine":
						singleObject = new Wine(parameters);
						break;
				}
				objectsList.add(singleObject);
				System.out.println(singleObject.toString());
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

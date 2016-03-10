/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MetodaKNN {

	private static List<Object> objectsList = new ArrayList<>(); //TODO ???? WTF
	private static Object inputObject = null;
	
	public static void main(String[] args) {
		String dataType = chooseType();
		String dataPath = "src/Lab3_1/resources/data" + dataType +".txt";
		readDataFromFile(dataPath,dataType);

		System.out.println("Wprowadz dane nowego obiektu");
		setInputObject(dataType);

		System.out.println(inputObject.toString());
//		countDistances();
	}

	private static void setInputObject(String dataType) {
		switch (dataType) {
			case "Iris":
				inputObject = new Iris(Iris.getInputParameters());
				break;
			case "Wine":
				inputObject = new Wine(Wine.getInputParameters());
				break;
		}
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

/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MetodaKNN {

//	private static List<Object> objectsList = new ArrayList<>(); //TODO ???? WTF
	
	public static void main(String[] args) throws Exception {
		DataType dType = chooseType();
		String dataPath = "src/Lab3_1/resources/data" + dType.name() +".txt";

		//Read data from txt file
		switch (dType) {
			case IRIS:
				Iris irisObject = new Iris();
				readDataFromFile(dataPath, irisObject);
				irisObject = new Iris(Iris.getInputParameters());
				System.out.println(irisObject.toString());
				break;
			case WINE:
				Wine wineObject = new Wine();
				readDataFromFile(dataPath, wineObject);
				wineObject = new Wine(Wine.getInputParameters());
				System.out.println(wineObject.toString());
				break;
			default:
				System.exit(-1);
		}

//		countDistances();
	}

	private static DataType chooseType() {
		System.out.println("Wpisz numer wczytywanego obiektu");
		System.out.println("1.Iris");
		System.out.println("2.Wine");
		Scanner in = new Scanner(System.in);
		while (true) {
			switch (in.nextLine()) {
				case "1":
					return DataType.IRIS;
				case "2":
					return DataType.WINE;
				default:
					System.out.println("Zła liczba. Wybierz ponownie.");
			}
		}
	}

	private static <T extends InputData> Map<T,Double> countDistances(T inputObject, List<T> objectsList) {
		Map<T,Double> distanceMap = new HashMap<>();
		double sum = 0;
		double dif = 0;
		double result = 0;
		for (T singleObject: objectsList) {
			for (int i=0; i<singleObject.numberOfParameters(); i++) {
				dif = inputObject.getParameterById(i+1) - objectsList.get(i).getParameterById(i+1);
				sum += Math.pow(dif,2);
			}
			result = Math.sqrt(sum);
			distanceMap.put(singleObject, result);
		}
		return distanceMap;
	}

	private static <T extends InputData> List<T> readDataFromFile(String path, T singleObject) throws Exception {
		List<T> objectsList = new ArrayList<>();
		File dataFile = new File(path);
		try {
			Scanner in = new Scanner(dataFile);
			String[] parameters;
			String line = in.nextLine();
			while (in.hasNextLine()) {
				line = in.nextLine();
				line = line.replace(",", ".");
				parameters = line.split("\t");
				singleObject.setParamFromStringTab(parameters);
				objectsList.add(singleObject);
				System.out.println(singleObject.toString());
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Plik nie zostal wczytany poprawnie - " + e.getMessage());
		}
		return objectsList;
	}

	private enum DataType {
		IRIS, WINE
	}
}

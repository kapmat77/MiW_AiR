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
//		DataType dType = chooseType();
		DataType dType = DataType.IRIS;
		String dataPath = "src/Lab3_1/resources/data" + dType.name() +".txt";
		int parK = 5;

		//Read data from txt file
		switch (dType) {
			case IRIS:
				Iris inputIris = new Iris(Iris.getInputParameters());
				List<Iris> irisList = Iris.readDataFromFile(dataPath);
				Map<Iris,Double> distanceIrisMap = simpleCountDistances(inputIris, irisList);
				//find K neighbours
				while (parK>1) {
					parK--;
					findBestNeighbour(distanceIrisMap);
				}
				break;
			case WINE:
				Wine inputWine = new Wine(Wine.getInputParameters());
				List<Wine> wineList = Wine.readDataFromFile(dataPath);
				Map<Wine,Double> distanceWineMap = simpleCountDistances(inputWine, wineList);
				//find K neighbours
				findBestNeighbour(distanceWineMap);
				break;
			default:
				System.exit(-1);
		}
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

	private static <T> void findBestNeighbour(Map<T,Double> distanceMap) {
		//iterator po wszystkich elementach
		//zwracamy dane najlepszego obiektu i go usuwamy z Mapy (tylko jak :-O)
	}

	private static <T extends InputData> Map<T,Double> simpleCountDistances(T inputObject, List<T> objectsList) {
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

	private enum DataType {
		IRIS, WINE
	}
}

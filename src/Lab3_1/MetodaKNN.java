/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import java.io.FileNotFoundException;
import java.util.*;

public class MetodaKNN {
	
	public static void main(String[] args) throws Exception {
		DataType dType = chooseType();
		String dataPath = "src/Lab3_1/resources/data" + dType.name() +".txt";
		Integer paramK = getK();
		executeProgram(dType, paramK, dataPath);
	}

	private static void executeProgram(DataType dType, int parK, String dataPath) throws FileNotFoundException {
		switch (dType) {
			case IRIS:
				Iris inputIris = new Iris(Iris.getInputParameters());

				System.out.println("Input Iris:");
				System.out.println(inputIris.toString() + "\n");

				List<Iris> irisList = Iris.readDataFromFile(dataPath);
				Map<Iris,Double> distanceIrisMap = createSimpleDistanceMap(inputIris, irisList);

				ArrayList<Iris> bestIrisNeighbours = new ArrayList<>();

				while (parK>0) {
					parK--;
					Iris bestIris = findBestNeighbour(distanceIrisMap);
					distanceIrisMap.remove(findBestNeighbour(distanceIrisMap));
					bestIrisNeighbours.add(bestIris);
				}

				System.out.println("Best neighbours:");
				for (Iris singleIris: bestIrisNeighbours) {
					System.out.println(singleIris.toString() + " " + countSingleDistance(inputIris, singleIris));
				}

				assignInputObject(bestIrisNeighbours);

				break;

			case WINE:
				Wine inputWine = new Wine(Wine.getInputParameters());

				System.out.println("Input Wine:");
				System.out.println(inputWine.toString() + "\n");

				List<Wine> wineList = Wine.readDataFromFile(dataPath);
				Map<Wine,Double> distanceWineMap = createSimpleDistanceMap(inputWine, wineList);

				ArrayList<Wine> bestWineNeighbours = new ArrayList<>();

				while (parK>0) {
					parK--;
					Wine bestWine = findBestNeighbour(distanceWineMap);
					distanceWineMap.remove(findBestNeighbour(distanceWineMap));
					bestWineNeighbours.add(bestWine);
				}

				System.out.println("Best neighbours:");
				for (Wine singleWine: bestWineNeighbours) {
					System.out.println(singleWine.toString() + " " + countSingleDistance(inputWine, singleWine));
				}

				assignInputObject(bestWineNeighbours);

				break;

			default:
				System.exit(-1);
		}
	}

	private static <T extends InputData> void assignInputObject(List<T> bestObjectList) {
		Map<String,Integer> counterMap = new HashMap<>();
		for (T singleBestObject: bestObjectList) {
			if (!counterMap.containsKey(singleBestObject.getObjectType())) {
				counterMap.put(singleBestObject.getObjectType(),1);
			} else {
				counterMap.replace(singleBestObject.getObjectType(), counterMap.get(singleBestObject.getObjectType()),
						counterMap.get(singleBestObject.getObjectType())+1);
			}
		}

		Integer maxNeighbours = Collections.max(counterMap.values());

		System.out.println("Wprowadzony obiekt jest typu:");
		for (Map.Entry<String,Integer> entry: counterMap.entrySet()) {
			if (entry.getValue().equals(maxNeighbours)) {
				System.out.print(entry.getKey() + " ");
			}
		}

		System.out.println();
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

	private static <T extends InputData> T findBestNeighbour(Map<T,Double> distanceMap) {
		Double min = Collections.min(distanceMap.values());
		for (Map.Entry<T, Double> entry: distanceMap.entrySet()) {
			if (entry.getValue().equals(min)) {
				return entry.getKey();
			}
		}
		System.exit(-1);
		return null;
	}

	private static <T extends InputData> Map<T,Double> createSimpleDistanceMap(T inputObject, List<T> objectsList) {
		Map<T,Double> distanceMap = new HashMap<>();
		double sum = 0;
		double dif = 0;
		double result = 0;
		for (T singleObject: objectsList) {
			for (int i=0; i<singleObject.numberOfParameters(); i++) {
				dif = inputObject.getParameterById(i+1) - singleObject.getParameterById(i+1);
				sum += Math.pow(dif,2);
			}
			result = Math.sqrt(sum);
			sum = 0;
			distanceMap.put(singleObject, result);
		}
		return distanceMap;
	}

	private static <T extends InputData> double countSingleDistance(T inputObject, T singleObject) {
		double sum = 0;
		double dif = 0;
		double result = 0;
		for (int i=0; i<singleObject.numberOfParameters(); i++) {
			dif = inputObject.getParameterById(i+1) - singleObject.getParameterById(i+1);
			sum += Math.pow(dif,2);
		}
		result = Math.sqrt(sum);
		return result;
	}

	public static int getK() {
		int parK = 0;
		System.out.println("Wprowadz K:");
		while (parK==0) {
			Scanner input = new Scanner(System.in);
			parK = Integer.valueOf(input.nextLine());
		}
		return parK;
	}

	private enum DataType {
		IRIS, WINE
	}
}

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
		System.out.println("1.Prosta metoda KNN z podaną wartością K");
		System.out.println("2.Prosta metoda KNN porównująca wyniki dla różnych K ");
		Integer numberOfOperation = 2;
		executeProgram(numberOfOperation, dType, dataPath);
	}

	private static void executeProgram(Integer numberOfOperation, DataType dType,  String dataPath) throws FileNotFoundException {
		Integer parK = 10;
		List<String> types;
		Map<String,Integer> countBestAssign = new HashMap<>();
		switch (dType) {
			case IRIS:
				Iris inputIris = new Iris(Iris.getInputParameters());
				System.out.println("Input Iris:");
				System.out.println(inputIris.toString() + "\n");
				switch (numberOfOperation) {
					case 1:
						parK = getK();
						types = findAssignmentIris(parK, dataPath, inputIris);
						System.out.println("Typ obiektu: " + types.toString());
						break;
					case 2:
						while (parK>0) {
							types = findAssignmentIris(parK, dataPath, inputIris);
							parK--;
							for (String type : types) {
								if (!countBestAssign.containsKey(type)) {
									countBestAssign.put(type, 1);
								} else {
									countBestAssign.replace(type, countBestAssign.get(type),
											countBestAssign.get(type) + 1);
								}
							}
						}
						Integer maxNeighbours = Collections.max(countBestAssign.values());
						System.out.println("Wprowadzony obiekt jest typu:");
						for (Map.Entry<String,Integer> entry: countBestAssign.entrySet()) {
							if (entry.getValue().equals(maxNeighbours)) {
								System.out.print(entry.getKey() + " ");
							}
						}
					default:
						break;
				}
				break;
			case WINE:
				Wine inputWine = new Wine(Wine.getInputParameters());
				System.out.println("Input Wine:");
				System.out.println(inputWine.toString() + "\n");
				switch (numberOfOperation) {
					case 1:
						parK = getK();
						types = findAssignmentWine(parK, dataPath, inputWine);
						System.out.println("Typ obiektu: " + types);
						break;
					case 2:
						while (parK>0) {
							types = findAssignmentWine(parK, dataPath, inputWine);
							parK--;
							for (String type : types) {
								if (!countBestAssign.containsKey(type)) {
									countBestAssign.put(type, 1);
								} else {
									countBestAssign.replace(type, countBestAssign.get(type),
											countBestAssign.get(type) + 1);
								}
							}
						}
						Integer maxNeighbours = Collections.max(countBestAssign.values());
						System.out.println("Wprowadzony obiekt jest typu:");
						for (Map.Entry<String,Integer> entry: countBestAssign.entrySet()) {
							if (entry.getValue().equals(maxNeighbours)) {
								System.out.print(entry.getKey() + " ");
							}
						}
					default:
						break;
				}
				break;
			default:
				System.exit(-1);
		}
	}

	private static List<String> findAssignmentIris(int parK, String dataPath, Iris inputIris) throws FileNotFoundException {
		List<Iris> irisList = Iris.readDataFromFile(dataPath);
		Map<Iris,Double> distanceIrisMap = createSimpleDistanceMap(inputIris, irisList);

		ArrayList<Iris> bestIrisNeighbours = new ArrayList<>();

		int loopNumber = parK;
		while (loopNumber>0) {
			loopNumber--;
			Iris bestIris = findBestNeighbour(distanceIrisMap);
			distanceIrisMap.remove(findBestNeighbour(distanceIrisMap));
			bestIrisNeighbours.add(bestIris);
		}

		System.out.println("Best neighbours:");
		for (Iris singleIris: bestIrisNeighbours) {
			System.out.println(singleIris.toString() + " " + countSingleDistance(inputIris, singleIris));
		}

		return assignInputObject(bestIrisNeighbours, parK);
	}

	private static List<String> findAssignmentWine(int parK, String dataPath, Wine inputWine) throws FileNotFoundException {
		List<Wine> wineList = Wine.readDataFromFile(dataPath);
		Map<Wine,Double> distanceWineMap = createSimpleDistanceMap(inputWine, wineList);

		ArrayList<Wine> bestWineNeighbours = new ArrayList<>();

		int loopNumber = parK;
		while (loopNumber>0) {
			loopNumber--;
			Wine bestWine = findBestNeighbour(distanceWineMap);
			distanceWineMap.remove(findBestNeighbour(distanceWineMap));
			bestWineNeighbours.add(bestWine);
		}

		System.out.println("Best neighbours:");
		for (Wine singleWine: bestWineNeighbours) {
			System.out.println(singleWine.toString() + " " + countSingleDistance(inputWine, singleWine));
		}

		return assignInputObject(bestWineNeighbours, parK);
	}

	private static <T extends InputData> List<String> assignInputObject(List<T> bestObjectList, int parK) {
		Map<String,Integer> counterMap = new HashMap<>();
		String type;
		for (T singleBestObject: bestObjectList) {
			type = singleBestObject.getObjectType();
			if (!counterMap.containsKey(type)) {
				counterMap.put(type,1);
			} else {
				counterMap.replace(type, counterMap.get(type),
						counterMap.get(type)+1);
			}
		}

		Integer maxNeighbours = Collections.max(counterMap.values());

		List<String> returnTypes = new ArrayList<>();
		System.out.println("Wprowadzony obiekt dla K=" + parK + " jest typu:");
		for (Map.Entry<String,Integer> entry: counterMap.entrySet()) {
			if (entry.getValue().equals(maxNeighbours)) {
				System.out.print(entry.getKey() + " ");
				returnTypes.add(entry.getKey());
			}
		}
		System.out.println("\n");

		return returnTypes;
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

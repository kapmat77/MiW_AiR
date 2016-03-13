/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import java.io.FileNotFoundException;
import java.util.*;

public class MetodaKNN {

	private static final int MAX_K = 10;

	public static void main(String[] args) throws Exception {
		DataType dType = chooseType();
		String dataPath = "src/Lab3_1/resources/data" + dType.name() +".txt";
		System.out.println("1.Prosta metoda KNN z podaną wartością K");
		System.out.println("2.Prosta metoda KNN porównująca wyniki dla różnych K ");
		int numberOfOperation = 1;
		System.out.println();
		System.out.println("1.Zwykłe odległości");
		System.out.println("2.Ważone odległości");
		int distanceImportanceType = 1;
		executeProgram(numberOfOperation, dType, dataPath, distanceImportanceType);
	}

	private static void executeProgram(Integer numberOfOperation, DataType dType,  String dataPath, int distanceImportanceType) throws FileNotFoundException {
		Integer parK = MAX_K;
		List<String> types;
		Map<String,Integer> countBestAssign = new HashMap<>();
		switch (dType) {
			case IRIS:
				Iris inputIris = new Iris(Iris.getInputParameters());
				System.out.println("Input Iris:");
				System.out.println(inputIris.toString() + "\n");
				List<Iris> irisList = Iris.readDataFromFile(dataPath);
				Map<Iris,Double> distanceIrisMap = createDistanceMap(inputIris, irisList);
				switch (numberOfOperation) {
					case 1:
						parK = getK();
						types = findAssignment(parK, distanceImportanceType, distanceIrisMap);
						System.out.println("Typ obiektu: " + types);
						break;
					case 2:
						while (parK>0) {
							types = findAssignment(parK, distanceImportanceType, distanceIrisMap);
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
				List<Wine> wineList = Wine.readDataFromFile(dataPath);
				Map<Wine,Double> distanceWineMap = createDistanceMap(inputWine, wineList);
				switch (numberOfOperation) {
					case 1:
						parK = getK();
						types = findAssignment(parK, distanceImportanceType, distanceWineMap);
						System.out.println("Typ obiektu: " + types);
						break;
					case 2:
						while (parK>0) {
							types = findAssignment(parK, distanceImportanceType, distanceWineMap);
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

	private static <T extends InputData> List<String> findAssignment(int parK, int distanceImportanceType,
	                                               Map<T,Double> distanceIrisMap) throws FileNotFoundException {

		Map<T,Double> bestIrisNeighbours = new HashMap<>();

		int loopNumber = parK;
		while (loopNumber>0) {
			loopNumber--;
			findBestNeighbour(distanceIrisMap);
			Map.Entry<T, Double> neighbour = findBestNeighbour(distanceIrisMap);
			distanceIrisMap.remove(neighbour.getKey());
			bestIrisNeighbours.put(neighbour.getKey(), neighbour.getValue());
		}

		System.out.println("Best neighbours:");
		for (Map.Entry<T, Double> entry: bestIrisNeighbours.entrySet()) {
			System.out.println(entry.getKey().toString() + " " + entry.getValue().toString());
		}

		switch (distanceImportanceType) {
			case 1:
				return simpleAssignInputObject(bestIrisNeighbours, parK);
			case 2:
				countVotingCoefficient(bestIrisNeighbours);
//				return importanceAssignInputObject(bestIrisNeighbours, parK);
			default:
				System.exit(-1);
		}
		System.exit(-1);
		return null;
	}

	private static <T extends InputData> List<String> simpleAssignInputObject(Map<T,Double> bestObjectMap, int parK) {
		Map<String,Integer> counterMap = new HashMap<>();
		String type;
		for (Map.Entry<T,Double> singleBestObject: bestObjectMap.entrySet()) {
			type = singleBestObject.getKey().getObjectType();
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

	private static <T extends InputData> Map<String, Double> countVotingCoefficient(Map<T,Double> bestObjectList) {
		Map<String,Integer> numbersOfObjectMap = new HashMap<>();
		Map<String,Double> votingCoefficientMap = new HashMap<>();
		for (Map.Entry<T,Double> entry: bestObjectList.entrySet()) {
			if (!votingCoefficientMap.containsKey(entry.getKey().getObjectType())) {
				numbersOfObjectMap.put(entry.getKey().getObjectType(), 1);
				votingCoefficientMap.put(entry.getKey().getObjectType(), entry.getValue());
			} else {
				numbersOfObjectMap.replace(entry.getKey().getObjectType(), numbersOfObjectMap.get(entry.getKey().getObjectType()),
						numbersOfObjectMap.get(entry.getKey().getObjectType()) + 1);
				votingCoefficientMap.replace(entry.getKey().getObjectType(), votingCoefficientMap.get(entry.getKey().getObjectType()),
						votingCoefficientMap.get(entry.getKey().getObjectType()) + entry.getValue());
			}
		}
		for (Map.Entry<String,Integer> entry: numbersOfObjectMap.entrySet()) {
			votingCoefficientMap.replace(entry.getKey(), votingCoefficientMap.get(entry.getKey()), votingCoefficientMap.get(entry.getKey())/entry.getValue());
		}

		return votingCoefficientMap;
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

	private static <T extends InputData> Map.Entry<T, Double> findBestNeighbour(Map<T,Double> distanceMap) {
		Double min = Collections.min(distanceMap.values());
		for (Map.Entry<T, Double> entry: distanceMap.entrySet()) {
			if (entry.getValue().equals(min)) {
				return entry;
			}
		}
		System.exit(-1);
		return null;
	}

	private static <T extends InputData> Map<T,Double> createDistanceMap(T inputObject, List<T> objectsList) {
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

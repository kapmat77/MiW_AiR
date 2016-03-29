/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import HelpfulClasses.WrapperKey;
import Interf.InputData;
import DataClass.Iris;
import DataClass.Wine;

import java.io.FileNotFoundException;
import java.util.*;

public class MetodaKNN {

	private static final int MAX_K = 10;
	private static final int MIN_K = 3;

	public static void main(String[] args) throws Exception {
		while (true) {
			DataType dType = chooseType();
//			String dataPath = "Resources/data" + dType.name() +".txt";
			String dataPath = "src/Resources/data" + dType.name() +".txt";
			System.out.println("1.Prosta metoda KNN z podaną wartością K");
			System.out.println("2.Metoda KNN z walidacją krzyżową");
			System.out.println("Wprowadz dowolny inny znak aby zakonczyc");
			Scanner input = new Scanner(System.in);
			int numberOfOperation = Integer.valueOf(input.nextLine());
			if (!(numberOfOperation==1 || numberOfOperation==2)) {
				return;
			}
			System.out.println();
			System.out.println("1.Zwykłe odległości");
			System.out.println("2.Ważone odległości");
			input = new Scanner(System.in);
			int distanceImportanceType = Integer.valueOf(input.nextLine());
			executeProgram(numberOfOperation, dType, dataPath, distanceImportanceType);
		}
	}

	private static void executeProgram(Integer numberOfOperation, DataType dType,  String dataPath, int distanceImportanceType) throws FileNotFoundException {
		Integer parK = MAX_K;
		List<String> types;
		List<String> typesLearning;
		Map<String,Integer> countBestAssign = new HashMap<>();
		Map<String,Integer> countBestAssignLearning = new HashMap<>();
		Map<WrapperKey,Double> votCoefExtendMap = new HashMap<>();
		switch (dType) {
			case IRIS:
				Iris inputIris = new Iris(Iris.getInputParameters());
				System.out.println("Input Iris:");
				System.out.println(inputIris.toString() + "\n");
				List<Iris> irisList = Iris.readDataFromFile(dataPath);
				Map<Iris,Double> distanceIrisMap = createDistanceMap(inputIris, irisList, false);
				switch (numberOfOperation) {
					case 1:
						parK = inputK();
						types = findAssignment(parK, distanceImportanceType, distanceIrisMap, votCoefExtendMap);
						System.out.println("Typ obiektu: " + types);
						break;
					case 2:
						while (parK>MIN_K-1) {
							assignToLearningOrValidation(irisList, 1, parK);
							Map<Iris, Double> distanceIrisMapValidation = createDistanceMap(inputIris, irisList, true);
							Map<Iris, Double> distanceIrisMapLearning = createDistanceMap(inputIris, irisList, false);
							typesLearning = findAssignment(MAX_K, distanceImportanceType, distanceIrisMapLearning, votCoefExtendMap);
							types = findAssignment(MAX_K, distanceImportanceType, distanceIrisMapValidation, votCoefExtendMap);
							parK--;
							for (String type : typesLearning) {
								if (!countBestAssignLearning.containsKey(type)) {
									countBestAssignLearning.put(type, 1);
								} else {
									countBestAssignLearning.replace(type, countBestAssignLearning.get(type),
											countBestAssignLearning.get(type) + 1);
								}
							}

							int maxNeighbours = Collections.max(countBestAssignLearning.values());
							System.out.println("\nUCZENIE:");
							for (Map.Entry<String,Integer> entry: countBestAssignLearning.entrySet()) {
								if (entry.getValue().equals(maxNeighbours)) {
									System.out.print(entry.getKey() + " ");
								}
							}
							for (String type : types) {
								if (!countBestAssign.containsKey(type)) {
									countBestAssign.put(type, 1);
								} else {
									countBestAssign.replace(type, countBestAssign.get(type),
											countBestAssign.get(type) + 1);
								}
							}
							maxNeighbours = Collections.max(countBestAssign.values());
							System.out.println("\nWALIDACJA:");
							for (Map.Entry<String, Integer> entry : countBestAssign.entrySet()) {
								if (entry.getValue().equals(maxNeighbours)) {
									System.out.print(entry.getKey() + " ");
								}
							}
							System.out.println();
							System.out.println();
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
				Map<Wine,Double> distanceWineMap = createDistanceMap(inputWine, wineList, false);
				switch (numberOfOperation) {
					case 1:
						parK = inputK();
						types = findAssignment(parK, distanceImportanceType, distanceWineMap, votCoefExtendMap);
						System.out.println("Typ obiektu: " + types);
						break;
					case 2:
						while (parK>MIN_K-1) {
							assignToLearningOrValidation(wineList,1,parK);
							Map<Wine,Double> distanceWineMapValidation = createDistanceMap(inputWine, wineList, true);
							Map<Wine,Double> distanceWineMapLearning = createDistanceMap(inputWine, wineList, false);
							typesLearning = findAssignment(MAX_K, distanceImportanceType, distanceWineMapLearning, votCoefExtendMap);
							types = findAssignment(MAX_K, distanceImportanceType, distanceWineMapValidation, votCoefExtendMap);
							parK--;
							for (String type : typesLearning) {
								if (!countBestAssignLearning.containsKey(type)) {
									countBestAssignLearning.put(type, 1);
								} else {
									countBestAssignLearning.replace(type, countBestAssignLearning.get(type),
											countBestAssignLearning.get(type) + 1);
								}
							}

							int maxNeighbours = Collections.max(countBestAssignLearning.values());
							System.out.println("\nUCZENIE:");
							for (Map.Entry<String,Integer> entry: countBestAssignLearning.entrySet()) {
								if (entry.getValue().equals(maxNeighbours)) {
									System.out.print(entry.getKey() + " ");
								}
							}
							for (String type : types) {
								if (!countBestAssign.containsKey(type)) {
									countBestAssign.put(type, 1);
								} else {
									countBestAssign.replace(type, countBestAssign.get(type),
											countBestAssign.get(type) + 1);
								}
							}
							maxNeighbours = Collections.max(countBestAssign.values());
							System.out.println("WALIDACJA:");
							for (Map.Entry<String, Integer> entry : countBestAssign.entrySet()) {
								if (entry.getValue().equals(maxNeighbours)) {
									System.out.print(entry.getKey() + " ");
								}
							}

							System.out.println();
							System.out.println();
						}
//						Integer maxNeighbours = Collections.max(countBestAssign.values());
//						System.out.println("Wprowadzony obiekt jest typu:");
//						for (Map.Entry<String,Integer> entry: countBestAssign.entrySet()) {
//							if (entry.getValue().equals(maxNeighbours)) {
//								System.out.print(entry.getKey() + " ");
//							}
//						}
					default:
						break;
				}
				break;
			default:
				System.exit(-1);
		}
	}

	private static <T extends InputData> List<String> findAssignment(int parK, int distanceImportanceType,
	                                               Map<T,Double> distanceIrisMap, Map<WrapperKey,Double> votCoefExtendMap) throws FileNotFoundException {

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
				WrapperKey<Integer,String> wrapKey;
				Map<String,Double> votCoefMap = countVotingCoefficient(bestIrisNeighbours);
				for (Map.Entry<String,Double> entry: votCoefMap.entrySet()) {
					wrapKey = new WrapperKey<>(parK, entry.getKey());
					votCoefExtendMap.put(wrapKey,entry.getValue());
				}

				Double minAverageDistance = Collections.min(votCoefMap.values());

				List<String> returnTypes = new ArrayList<>();
				System.out.println("Wprowadzony obiekt dla K=" + parK + " jest typu:");
				for (Map.Entry<String,Double> entry: votCoefMap.entrySet()) {
					if (entry.getValue().equals(minAverageDistance)) {
						System.out.print(entry.getKey() + " ");
						returnTypes.add(entry.getKey());
					}
				}
				System.out.println("\n");
				return returnTypes;
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

	private static <T extends InputData> Map.Entry<T,Double> findBestNeighbour(Map<T,Double> distanceMap) {
		Double min = Collections.min(distanceMap.values());
		for (Map.Entry<T, Double> entry: distanceMap.entrySet()) {
			if (entry.getValue().equals(min)) {
				return entry;
			}
		}
		System.exit(-1);
		return null;
	}

	private static <T extends InputData> Map<T,Double> createDistanceMap(T inputObject, List<T> objectsList, boolean validation) {
		Map<T,Double> distanceMap = new HashMap<>();
		double sum = 0;
		double dif = 0;
		double result = 0;
		for (T singleObject: objectsList) {
			if (singleObject.getValidation()==validation) {
				for (int i=0; i<singleObject.numberOfParameters(); i++) {
					dif = inputObject.getParameterById(i+1) - singleObject.getParameterById(i+1);
					sum += Math.pow(dif,2);
				}
				result = Math.sqrt(sum);
				sum = 0;
				distanceMap.put(singleObject, result);
			}
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

	private static <T extends InputData> void assignToLearningOrValidation(List<T> dataFromFile, int type, int part) {
		Map<String,Integer> typesMap = new HashMap<>();
		for (T singleObject: dataFromFile) {
			if (!typesMap.containsKey(singleObject.getObjectType())) {
				typesMap.put(singleObject.getObjectType(),1);
			} else {
				typesMap.replace(singleObject.getObjectType(), typesMap.get(singleObject.getObjectType())+1);
			}
		}
		int amountOfLearners;
		boolean first;
		int lastIndex = 0;
		int firstIndex = 0;

		//assing all object to learning
		for (int i = 0; i < dataFromFile.size(); i++) {
			dataFromFile.get(i).setValidation(false);
		}

		switch (type) {
			//proporcjonalnie
			case 1:
				for (Map.Entry<String, Integer> entry : typesMap.entrySet()) {
					amountOfLearners = (int) (entry.getValue() / MAX_K);
					first = true;
					for (int i = 0; i < dataFromFile.size(); i++) {
						if (entry.getKey().equals(dataFromFile.get(i).getObjectType())) {
							if (first) {
								firstIndex = i + amountOfLearners * (part - 1);
								lastIndex = firstIndex + amountOfLearners;
								first = false;
							}
							if (i >= firstIndex && i < lastIndex) {
								dataFromFile.get(i).setValidation(true);
							}
						}
					}
				}
				break;
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

	private static int inputK() {
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

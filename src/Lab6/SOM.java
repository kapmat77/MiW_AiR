/**
 * Created by Kapmat on 2016-05-09.
 **/

package Lab6;

import DataClass.Iris;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import Interf.InputData;

public class SOM {

	private static final int SIZE = 5;

	private static Iris[][] neuronTable = new Iris[SIZE][SIZE];
	private static Iris[][] prevNeuronTable = new Iris[SIZE][SIZE];
	private static List<Iris> listOfIrises = new ArrayList<>();
	private static List<Iris> randomListOfIrises = new ArrayList<>();

	private static final double MAX_LL = 7.9;
	private static final double MIN_LL = 4.3;
	private static final double MAX_LW = 4.4;
	private static final double MIN_LW = 2;
	private static final double MAX_PL = 6.9;
	private static final double MIN_PL = 1;
	private static final double MAX_PW = 2.5;
	private static final double MIN_PW = 0.1;

	private static final double WINNER = 0.0001;

//	private static final double THRESHOLD = 1000;
	private static final double REAL_TRESHOLD = 0.001;

	private static boolean tooLargeDistance = true;


	public static void main(String[] args) throws FileNotFoundException {

		start();

	}

	private static void start() throws FileNotFoundException {
		createNetwork();

		//Show input objects
		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.println("X:" +i + " Y:" + j + " " +neuronTable[i][j].toString());
			}
		}

		copyArray();

		List<Double> bestNeighbParam = new ArrayList<>();
		//Main loop
//		boolean tooLargeDistance = true;
		int counter;
		int iter = 0;
		while(tooLargeDistance) {
			iter++;
			counter = 0;
//			System.out.println("L");
			for (int j = 0; j<randomListOfIrises.size(); j++) {
				bestNeighbParam = findBestNeuron(randomListOfIrises.get(j));
				updateMap(bestNeighbParam, j);
			}
			for (int i = 0; i<SIZE; i++) {
				for (int j = 0; j < SIZE; j++) {
					if (neuronTable[i][j].isActive()) {
						counter++;
					}
				}
			}
			if (counter>=(SIZE*SIZE)) {
//				tooLargeDistance = false;
			} else {
				for (int i = 0; i<SIZE; i++) {
					for (int j = 0; j < SIZE; j++) {
						neuronTable[i][j].setActive(false);
//						neuronTable[i][j].setType(Iris.IrisType.NONE);
					}
				}
			}
			checkStopCondition();
			copyArray();
		}

		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (neuronTable[i][j].getType().equals(Iris.IrisType.NONE)) {
					neuronTable[i][j].setType(Iris.IrisType.VIRGINICA);
				}
			}
		}


		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.println("X:" +i + " Y:" + j + " " +neuronTable[i][j].toString());
			}
		}

		System.out.println("ITERACJE: " + iter +"\n");

		System.out.println("1 - VIRGINICA");
		System.out.println("2 - VERSICOLOR");
		System.out.println("3 - SETOSA");

		String type;
		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (neuronTable[i][j].getType()== Iris.IrisType.VIRGINICA) {
					type = "1";
				} else if (neuronTable[i][j].getType()== Iris.IrisType.VERSICOLOR) {
					type = "2";
				} else if (neuronTable[i][j].getType()== Iris.IrisType.SETOSA) {
					type = "3";
				} else {
					type = "3";
				}
				System.out.print("["+type+"]");
			}
			System.out.println();
		}
	}

	private static void updateMap(List<Double> bestNeighbParam, int index) {
		changeAttributes(randomListOfIrises.get(index), getBestNodeFromParam(bestNeighbParam), WINNER);
		int bestX = bestNeighbParam.get(1).intValue();
		int bestY = bestNeighbParam.get(2).intValue();

//		for(int i = bestX-1; i<bestX+1; i++) {
//			for (int j = bestY - 1; j < bestY + 1; j++) {
//				if (i != bestX && j != bestY && i <= SIZE && i >= 0 && j <= SIZE && j >= 0) {
//					changeAttributes(randomListOfIrises.get(index), neuronTable[i][j], SECOND_WINNER);
//				}
//			}
//		}

		double sum;
		double difX, difY;
		double result = 0;
		for(int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (i != bestX && j != bestY) {

					difX = i - bestX;
					difY = j - bestY;
					sum = Math.pow(difX,2) + Math.pow(difY,2);
					result = Math.sqrt(sum);

					changeAttributes(randomListOfIrises.get(index), neuronTable[i][j], WINNER*Math.exp(-(Math.pow(result, 2))/2));
				}
			}
		}
	}

	private static void createNetwork() throws FileNotFoundException {
		listOfIrises = Iris.readDataFromFile("src/Resources/dataIris.txt");
		double leafLength, leafWidth, petalLength, petalWidth;
		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j<SIZE; j++) {
				leafLength = randomDouble(MIN_LL, MAX_LL);
				leafWidth = randomDouble(MIN_LW, MAX_LW);
				petalLength = randomDouble(MIN_PL, MAX_PL);
				petalWidth = randomDouble(MIN_PW, MAX_PW);
				neuronTable[i][j] = new Iris(leafLength, leafWidth, petalLength, petalWidth, Iris.IrisType.NONE);
			}
		}
		//Create random list of Irises
		int counter = listOfIrises.size();
		Iris singleIris;
		while (counter>0) {
			singleIris = listOfIrises.get((int)(Math.random()*(listOfIrises.size())));
			if (!singleIris.isActive()) {
				randomListOfIrises.add(singleIris);
				singleIris.setActive(true);
				counter--;
			}
		}
	}

	private static List<Double> findBestNeuron(Iris input) {
		double bestDistance = 0;
		int bestX = 0;
		int bestY = 0;
		double newDistance = 0;
		boolean first = true;
		for (int i = 0; i<SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (first) {
					bestDistance = countSingleDistance(neuronTable[i][j], input);
					bestX = i;
					bestY = j;
					first = false;
				} else {
					newDistance = countSingleDistance(neuronTable[i][j], input);
					if (bestDistance < newDistance) {
						bestDistance = newDistance;
						bestX = i;
						bestY = j;
					}
				}

			}
		}

//		if (bestDistance<THRESHOLD) {
//			neuronTable[bestX][bestY].setActive(true);
			neuronTable[bestX][bestY].setType(input.getType());
//		}

		List<Double> bestNeuronParam = new ArrayList<>();
		bestNeuronParam.add(bestDistance);
		bestNeuronParam.add((double)bestX);
		bestNeuronParam.add((double)bestY);

		return bestNeuronParam;
	}

	private static Iris getBestNodeFromParam(List<Double> bestNeuronParam) {
		return neuronTable[bestNeuronParam.get(1).intValue()][bestNeuronParam.get(2).intValue()];
	}

	private static void changeAttributes(Iris inputNeuron, Iris neuron, double valueToChange) {
		//LL
		if (Math.abs(inputNeuron.getLeafLength()-neuron.getLeafLength())>=valueToChange) {
			if (inputNeuron.getLeafLength()>neuron.getLeafLength()) {
				neuron.setLeafLength(neuron.getLeafLength() + valueToChange);
			} else {
				neuron.setLeafLength(neuron.getLeafLength() - valueToChange);
			}
		} else {
			neuron.setLeafLength(inputNeuron.getLeafLength());
		}

		//LW
		if (Math.abs(inputNeuron.getLeafWidth()-neuron.getLeafWidth())>=valueToChange) {
			if (inputNeuron.getLeafWidth()>neuron.getLeafWidth()) {
				neuron.setLeafWidth(neuron.getLeafWidth() + valueToChange);
			} else {
				neuron.setLeafWidth(neuron.getLeafWidth() - valueToChange);
			}
		} else {
			neuron.setLeafWidth(inputNeuron.getLeafWidth());
		}

		//PL
		if (Math.abs(inputNeuron.getPetalLength()-neuron.getPetalLength())>=valueToChange) {
			if (inputNeuron.getPetalLength()>neuron.getPetalLength()) {
				neuron.setPetalLength(neuron.getPetalLength() + valueToChange);
			} else {
				neuron.setPetalLength(neuron.getPetalLength() - valueToChange);
			}
		} else {
			neuron.setPetalLength(inputNeuron.getPetalLength());
		}

		//PW
		if (Math.abs(inputNeuron.getPetalWidth()-neuron.getPetalWidth())>=valueToChange) {
			if (inputNeuron.getPetalWidth()>neuron.getPetalWidth()) {
				neuron.setPetalWidth(neuron.getPetalWidth() + valueToChange);
			} else {
				neuron.setPetalWidth(neuron.getPetalWidth() - valueToChange);
			}
		} else {
			neuron.setPetalWidth(inputNeuron.getPetalWidth());
		}
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

	private static double randomDouble(double min, double max) {
		double range = (max - min);
		int valueInt = (int)((((Math.random() * range) + min))*10);
		double valueDouble = (double)valueInt/10;
		return valueDouble;
	}

	private static void checkStopCondition() {
		tooLargeDistance = false;
		for (int i = 0; i<neuronTable.length; i++) {
			for (int j = 0; j < neuronTable.length; j++) {
				if (Math.abs(prevNeuronTable[i][j].getLeafLength()-neuronTable[i][j].getLeafLength()) > REAL_TRESHOLD ||
						Math.abs(prevNeuronTable[i][j].getLeafWidth()-neuronTable[i][j].getLeafWidth()) > REAL_TRESHOLD ||
						Math.abs(prevNeuronTable[i][j].getPetalLength()-neuronTable[i][j].getPetalLength()) > REAL_TRESHOLD ||
						Math.abs(prevNeuronTable[i][j].getPetalWidth()-neuronTable[i][j].getPetalWidth()) > REAL_TRESHOLD) {
					tooLargeDistance = true;
				}
			}
		}
	}

	private static void copyArray() {
		for (int i = 0; i<neuronTable.length; i++) {
			for (int j = 0; j<neuronTable.length; j++) {
				prevNeuronTable[i][j] = new Iris();
				prevNeuronTable[i][j].setLeafLength(neuronTable[i][j].getLeafLength());
				prevNeuronTable[i][j].setLeafWidth(neuronTable[i][j].getLeafWidth());
				prevNeuronTable[i][j].setPetalLength(neuronTable[i][j].getPetalLength());
				prevNeuronTable[i][j].setPetalWidth(neuronTable[i][j].getPetalWidth());
			}
		}
	}
}

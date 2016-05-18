/**
 * Created by Kapmat on 2016-05-09.
 **/

package Lab6;

import DataClass.Iris;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import DataClass.Iris;
import Interf.InputData;

public class SOM {

	private static final int SIZE = 50;

	private static Iris[][] neuronTable = new Iris[SIZE][SIZE];
	private static List<Iris> listOfIrises = new ArrayList<>();

	private static final double MAX_LL = 7.9;
	private static final double MIN_LL = 4.3;
	private static final double MAX_LW = 4.4;
	private static final double MIN_LW = 2;
	private static final double MAX_PL = 6.9;
	private static final double MIN_PL = 1;
	private static final double MAX_PW = 2.5;
	private static final double MIN_PW = 0.1;

	public static void main(String[] args) throws FileNotFoundException {
		createNetwork();
		System.out.println(countSingleDistance(listOfIrises.get(0), neuronTable[7][7]));
	}

	public static double countDistance() {
		double distance = 0;

		return distance;
	}

	public static void createNetwork() throws FileNotFoundException {
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

	public static double randomDouble(double min, double max) {
		double range = (max - min);
		int valueInt = (int)((((Math.random() * range) + min))*10);
		double valueDouble = (double)valueInt/10;
		return valueDouble;
	}
}

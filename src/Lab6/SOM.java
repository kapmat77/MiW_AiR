/**
 * Created by Kapmat on 2016-05-09.
 **/

package Lab6;

import DataClass.Iris;

import java.io.FileNotFoundException;
import java.util.List;

public class SOM {

	private static Neuron[][] neuronTable = new Neuron[100][100];
	private static List<Iris> listOfIrises;

	public static void main(String[] args) throws FileNotFoundException {
		listOfIrises = Iris.readDataFromFile("src/Resources/dataIris.txt");
		for (int i = 0; i<100; i++) {
			for (int j = 0; j<100; j++) {
				neuronTable[i][j] = new Neuron(randomDouble(0,1));
				System.out.println(neuronTable[i][j].getCoeff());
			}
		}
	}

//	public static double euklidesDistance() {
//
//	}

	public static double randomDouble(double min, double max)
	{
		double range = (max - min);
		int valueInt = (int)((((Math.random() * range) + min))*100);
		double valueDouble = (double)valueInt/100;
		return valueDouble;
	}
}

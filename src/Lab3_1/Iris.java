/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Iris implements InputData{
	
	private double leafLength;
	private double leafWidth;
	private double petalLength;
	private double petalWidth;
	private IrisType type;
	
	private enum IrisType {
		SETOSA, VERSICOLOR, VIRGINICA, NONE
	}
	
	public Iris() {
		this.leafLength = 0;
		this.leafWidth = 0;
		this.petalLength = 0;
		this.petalWidth = 0;
		this.type = IrisType.NONE;
	}
	
	public Iris(double leafLength, double leafWidth, double petalLength, double petalWidth, IrisType type) {
		this.leafLength = leafLength;
		this.leafWidth = leafWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.type = type;
	}

	public Iris(String[] parameters) {
		this.leafLength = Double.valueOf(parameters[0]);
		this.leafWidth = Double.valueOf(parameters[1]);
		this.petalLength = Double.valueOf(parameters[2]);
		this.petalWidth = Double.valueOf(parameters[3]);
		this.type = Iris.convertStringToType(parameters[4]);
	}

	public IrisType getType() {
		return type;
	}
	
	public static IrisType convertStringToType(String strType) {
		switch(strType) {
			case "Iris-setosa":
				return IrisType.SETOSA;
			case "Iris-versicolor":
				return IrisType.VERSICOLOR;
			case "Iris-virginica":
				return IrisType.VIRGINICA;
			default:
				return IrisType.NONE;
		}
	}

	public static String[] getInputParameters() {
		System.out.println("Wprowadz parametry nowego obiektu");

		String setOfParameters[] = new String[5];
		Scanner input = new Scanner(System.in);

		System.out.println("leafLength: ");
		setOfParameters[0] = input.nextLine();
		System.out.println("leafWidth: ");
		setOfParameters[1] = input.nextLine();
		System.out.println("petalLength: ");
		setOfParameters[2] = input.nextLine();
		System.out.println("petalWidth: ");
		setOfParameters[3] = input.nextLine();
		setOfParameters[4] = IrisType.NONE.name();

		return setOfParameters;
	}

	public static List<Iris> readDataFromFile(String path) throws FileNotFoundException {
		List<Iris> objectsList = new ArrayList<>();
		File dataFile = new File(path);
		try {
			Scanner in = new Scanner(dataFile);
			String[] parameters;
			String line = in.nextLine();
			while (in.hasNextLine()) {
				Iris singleObject = new Iris();
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

	@Override
	public double getParameterById(int id) {
		switch (id) {
			case 1:
				return leafLength;
			case 2:
				return leafLength;
			case 3:
				return leafLength;
			case 4:
				return leafLength;
			default:
				return 0;
		}
	}

	@Override
	public void setParamFromStringTab(String[] parameters) {
		this.leafLength = Double.valueOf(parameters[0]);
		this.leafWidth = Double.valueOf(parameters[1]);
		this.petalLength = Double.valueOf(parameters[2]);
		this.petalWidth = Double.valueOf(parameters[3]);
		this.type = Iris.convertStringToType(parameters[4]);
	}

	@Override
	public int numberOfParameters() {
		return 4;
	}

	@Override
	public String toString() {
		return "Iris {" +
				"leafLength=" + leafLength +
				", leafWidth=" + leafWidth +
				", petalLength=" + petalLength +
				", petalWidth=" + petalWidth +
				", type=" + type +
				"}";
	}
}

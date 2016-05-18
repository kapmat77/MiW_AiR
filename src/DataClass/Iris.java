/**
 * Created by Kapmat on 2016-03-08.
 **/

package DataClass;

import Interf.InputData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Iris implements InputData {
	
	private double leafLength;
	private double leafWidth;
	private double petalLength;
	private double petalWidth;
	private IrisType type;

	private Integer index = 0;

	private boolean isValidation  = false;

	private double similarity;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public enum IrisType {
		SETOSA, VERSICOLOR, VIRGINICA, NONE;
	}

	public static IrisType getTypeFromString(String t) {
		switch (t.toUpperCase()) {
			case "SETOSA":
				return IrisType.SETOSA;
			case "VERSICOLOR":
				return IrisType.VERSICOLOR;
			case "VIRGINICA":
				return IrisType.VIRGINICA;
			default:
				return IrisType.NONE;
		}
	}


//	public enum KindOfParam {

//		LEAF_LENGTH, LEAF_WIDTH, PETAL_LENGTH, PETAL_WIDTH

//	}
	
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

	public Iris(double leafLength, double leafWidth, double petalLength, double petalWidth, IrisType type, double similarity, Integer index) {
		this.leafLength = leafLength;
		this.leafWidth = leafWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.type = type;
		this.similarity = similarity;
		this.index = index;
	}

	public Iris(String[] parameters) {
		this.leafLength = Double.valueOf(parameters[0]);
		this.leafWidth = Double.valueOf(parameters[1]);
		this.petalLength = Double.valueOf(parameters[2]);
		this.petalWidth = Double.valueOf(parameters[3]);
		this.type = Iris.convertStringToType(parameters[4]);
	}

	public Iris(Iris anotherIris) {
		this.leafLength = anotherIris.leafLength;
		this.leafWidth = anotherIris.leafWidth;
		this.petalLength = anotherIris.petalLength;
		this.petalWidth = anotherIris.petalWidth;
		this.type = anotherIris.type;
	}

	public void setLeafLength(double leafLength) {
		this.leafLength = leafLength;
	}

	public void setLeafWidth(double leafWidth) {
		this.leafWidth = leafWidth;
	}

	public void setPetalLength(double petalLength) {
		this.petalLength = petalLength;
	}

	public void setPetalWidth(double petalWidth) {
		this.petalWidth = petalWidth;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
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
		System.out.println("\nWprowadz parametry nowego obiektu");
		System.out.println("(Liczby z maksymalnie jednym miejscem po przecinku,");
		System.out.println("większa ilość miejsc po przecinku jest zaokrąglana)");
		String setOfParameters[] = new String[5];
		Scanner input = new Scanner(System.in);

		System.out.println("leafLength: ");
		setOfParameters[0] = input.nextLine();
		setOfParameters[0] = setOfParameters[0].replace(",", ".");
		System.out.println("leafWidth: ");
		setOfParameters[1] = input.nextLine();
		setOfParameters[1] = setOfParameters[1].replace(",", ".");
		System.out.println("petalLength: ");
		setOfParameters[2] = input.nextLine();
		setOfParameters[2] = setOfParameters[2].replace(",", ".");
		System.out.println("petalWidth: ");
		setOfParameters[3] = input.nextLine();
		setOfParameters[3] = setOfParameters[3].replace(",", ".");
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
//				System.out.println(singleObject.toString());
			}
			in.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Plik nie zostal wczytany poprawnie - " + e.getMessage());
		}
		return objectsList;
	}

	public double getLeafLength() {
		return leafLength;
	}

	public double getLeafWidth() {
		return leafWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public IrisType getType() {
		return type;
	}

	public double getParameterByEnum(KindOfParam kind) {
		switch (kind) {
			case LEAF_LENGTH:
				return leafLength;
			case LEAF_WIDTH:
				return leafWidth;
			case PETAL_LENGTH:
				return petalLength;
			case PETAL_WIDTH:
				return petalWidth;
			default:
				return 0.0;
		}
	}

	@Override
	public boolean compare(Object obj) {
		Iris iris = (Iris) obj;
		if (this.leafLength==iris.leafLength && this.leafWidth==iris.leafWidth &&
				this.petalLength==iris.petalLength && this.petalWidth==iris.petalWidth) {
			return true;
		}
		return false;
	}

	@Override
	public double getParameterById(int id) {
		switch (id) {
			case 1:
				return leafLength;
			case 2:
				return leafWidth;
			case 3:
				return petalLength;
			case 4:
				return petalWidth;
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
	public String getObjectType() {
		return type.name();
	}

	@Override
	public void setValidation(boolean b) {
		isValidation = b;
	}

	@Override
	public boolean getValidation() {
		return isValidation;
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

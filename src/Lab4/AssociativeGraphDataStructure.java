/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import DataClass.Iris;
import HelpfulClasses.NodesBox;
import Interf.InputData;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class AssociativeGraphDataStructure{

//	private static final Node<String> PARAM_NODE = new Node<>(Node.Level.PARAM, Node.Level.PARAM.name());

	public static void main(String[] args) throws FileNotFoundException {
//		DataType dType = chooseType();
		DataType dType = DataType.IRIS;
		String dataPath = "src/Resources/data" + dType.name() +".txt";

		Long startTime;
		Long endTime;

		startTime = System.nanoTime();

		buildGraphAGDS(dataPath);

		List<Node> fitNodes = new ArrayList<>();


		double similarityThreshold = 0.9;
		findPatternsInGraph(similarityThreshold);

		NodesBox.getParamNode();

		fitNodes = findPatternsInGraphWithFilter();

		findPatternsInTable();

		findPatternsInTableWithFilter();

		// Input - only INDEX list nodes
//		showPatterns(fitNodes);

		endTime = System.nanoTime();
		System.out.println("Execution time for graph: " + (endTime-startTime) + " nanosecond");
	}

	private static void buildGraphAGDS(String dataPath) throws FileNotFoundException {
		List<Iris> listOfIris = Iris.readDataFromFile(dataPath);

		//Create CLASS_OF_OBJECT node
		Node<String> classNode = new Node<>(Node.Level.CLASS_OF_OBJECT, Node.Level.CLASS_OF_OBJECT.name());

		//Create KIND_OF_PARAM nodes
		Node<String> lLength = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.LEAF_LENGTH.name());
		Node<String> lWidth = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.LEAF_WIDTH.name());
		Node<String> pLength = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.PETAL_LENGTH.name());
		Node<String> pWidth = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.PETAL_WIDTH.name());

		//Create TYPE_OF_OBJECT nodes
		Node<Iris.IrisType> irisSetosa = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.SETOSA);
		Node<Iris.IrisType> irisVersicolor = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.VERSICOLOR);
		Node<Iris.IrisType> irisVirginica = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.VIRGINICA);

		//Create INDEX nodes
		List<Node> indexNodes = new ArrayList<>();
		for (int i = 0; i<listOfIris.size(); i++) {
			Node<Integer> singleIndex = new Node<>(Node.Level.INDEX, i+1);
			indexNodes.add(singleIndex);
		}

		//Create VALUE_OF_PARAM nodes
		List<Node> valueLLNodes = new ArrayList<>();
		List<Node> valueLWNodes = new ArrayList<>();
		List<Node> valuePLNodes = new ArrayList<>();
		List<Node> valuePWNodes = new ArrayList<>();
		List<double[]> valuelist = new ArrayList<>();
		boolean eq = false;
		for (Iris singleIris: listOfIris) {
			Node<Double> llValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_LENGTH));

			Node<Double> lwValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_WIDTH));

			Node<Double> plValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_LENGTH));

			Node<Double> pwValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_WIDTH));

//			double[] tabDouble = {llValue.getValue(), lwValue.getValue(), plValue.getValue(), pwValue.getValue()};
//
//			for (double[] values: valuelist) {
//				if (Arrays.equals(values, tabDouble)) {
//					eq = true;
//				}
//			}
//			if (!eq) {
//
//				valuelist.add(tabDouble);
				valueLLNodes.add(llValue);
				valueLWNodes.add(lwValue);
				valuePLNodes.add(plValue);
				valuePWNodes.add(pwValue);
//			}
//			eq = false;
		}

		//Set PARAM children
		List<Node> childrenParam = new ArrayList<>();
		childrenParam.add(lLength);
		childrenParam.add(lWidth);
		childrenParam.add(pLength);
		childrenParam.add(pWidth);
		childrenParam.add(classNode);
		NodesBox.getParamNode().setChildren(childrenParam);

		//Set CLASS_OF_OBJECT parent
		List<Node> parentClass = new ArrayList<>();
		parentClass.add(NodesBox.getParamNode());
		classNode.setParents(parentClass);

		//Set CLASS_OF_OBJECT children
		List<Node> childrenClass = new ArrayList<>();
		childrenClass.add(irisSetosa);
		childrenClass.add(irisVersicolor);
		childrenClass.add(irisVirginica);
		classNode.setChildren(childrenClass);

		//Set KIND_OF_PARAM parent
		List<Node> kindParent = new ArrayList<>();
		kindParent.add(NodesBox.getParamNode());
		lLength.setParents(kindParent);
		lWidth.setParents(kindParent);
		pLength.setParents(kindParent);
		pWidth.setParents(kindParent);

		//Set KIND_OF_PARAM children
		lLength.setChildren(valueLLNodes);
		lWidth.setChildren(valueLWNodes);
		pLength.setChildren(valuePLNodes);
		pWidth.setChildren(valuePWNodes);

		//Set TYPE_OF_OBJECT parents
		List<Node> parentsType = new ArrayList<>();
		parentsType.add(classNode);
		irisSetosa.setParents(parentsType);
		irisVersicolor.setParents(parentsType);
		irisVirginica.setParents(parentsType);

		//Set TYPE_OF_OBJECT children
		int l = 0;
		List<Node> childrenSetosaType = new ArrayList<>();
		List<Node> childrenVersicolorType = new ArrayList<>();
		List<Node> childrenVirginicaType = new ArrayList<>();
		for (Iris singleIris: listOfIris) {
			switch (singleIris.getObjectType()) {
				case "SETOSA":
					childrenSetosaType.add(indexNodes.get(l));
					break;
				case "VERSICOLOR":
					childrenVersicolorType.add(indexNodes.get(l));
					break;
				case "VIRGINICA":
					childrenVirginicaType.add(indexNodes.get(l));
					break;
			}
			l++;
		}
		irisSetosa.setChildren(childrenSetosaType);
		irisVersicolor.setChildren(childrenVersicolorType);
		irisVirginica.setChildren(childrenVirginicaType);

		//Set VALUE_OF_PARAM children
		int i = 0;
		for (Node singleValueNode: valueLLNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode: valueLWNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode: valuePLNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode: valuePWNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}

		//Set VALUE_OF_PARAM parents
		for (Node singleValueNode: valueLLNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(lLength);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode: valueLWNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(lWidth);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode: valuePLNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(pLength);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode: valuePWNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(pWidth);
			singleValueNode.setParents(parentsValue);
		}
//		for (Node singleValueNode: valueLLNodes) {
//			List<Node> parentsValue = new ArrayList<>();
//			parentsValue.add(lLength);
//			singleValueNode.setParents(parentsValue);
//		}
//		for (Node singleValueNode: valueLWNodes) {
//			List<Node> parentsValue = new ArrayList<>();
//			parentsValue.add(lWidth);
//			singleValueNode.setParents(parentsValue);
//		}
//		for (Node singleValueNode: valuePLNodes) {
//			List<Node> parentsValue = new ArrayList<>();
//			parentsValue.add(pLength);
//			singleValueNode.setParents(parentsValue);
//		}
//		for (Node singleValueNode: valuePWNodes) {
//			List<Node> parentsValue = new ArrayList<>();
//			parentsValue.add(pWidth);
//			singleValueNode.setParents(parentsValue);
//		}

		//TODO
		//Set INDEX parents
		int j = 0;
		for (Node singleIndexNode: indexNodes) {
			List<Node> parentsIndex = new ArrayList<>();
			parentsIndex.add(valueLLNodes.get(j));
			parentsIndex.add(valueLWNodes.get(j));
			parentsIndex.add(valuePLNodes.get(j));
			parentsIndex.add(valuePWNodes.get(j));
			singleIndexNode.setParents(parentsIndex);
			j++;
		}
//		int j = 0;
//		for (Node singleIndexNode: indexNodes) {
//			List<Node> parentsIndex = new ArrayList<>();
//			parentsIndex.add(valueLLNodes.get(j));
//			parentsIndex.add(valueLWNodes.get(j));
//			parentsIndex.add(valuePLNodes.get(j));
//			parentsIndex.add(valuePWNodes.get(j));
//			singleIndexNode.setParents(parentsIndex);
//			j++;
//		}

		//Set INDEX children
		int k = 0;
		for (Node singleIndexNode: indexNodes) {
			Iris singleIris = listOfIris.get(k);
			List<Node> childrenIndex = new ArrayList<>();
			switch (singleIris.getObjectType()) {
				case "SETOSA":
					childrenIndex.add(irisSetosa);
					break;
				case  "VERSICOLOR":
					childrenIndex.add(irisVersicolor);
					break;
				case "VIRGINICA":
					childrenIndex.add(irisVirginica);
					break;
			}
			singleIndexNode.setChildren(childrenIndex);
			k++;
		}

		//Sort date
		Collections.sort(valueLLNodes);
		Collections.sort(valueLWNodes);
		Collections.sort(valuePLNodes);
		Collections.sort(valuePWNodes);

		//Set MIN, MAX, RANGE
		setAdditionalParam(NodesBox.getKindOfParamNodes());

		deleteRedundantNodes();
	}

	private static void deleteRedundantNodes() {
		//TODO
		// usuwamy niepotrzebne VALUE nodes i powtarzające się INDEX nodes
		List<Node> kindOfParam = NodesBox.getKindOfParamNodes();
		for (Node singleNode: kindOfParam) {
//			switch ((InputData.KindOfParam)singleNode.getValue()) {
//				case LEAF_LENGTH:
//					break;
//				case LEAF_WIDTH:
//					break;
//				case PETAL_LENGTH:
//					break;
//				case PETAL_WIDTH:
//					break;
//				case ALCOHOL:
//					break;
//				case MALIC_ACID:
//					break;
//			}
		}

	}

	private static void setAdditionalParam(List<Node> nodes) {
		for (Node singleNode: nodes) {
			if (singleNode.getLevel().equals(Node.Level.KIND_OF_PARAM)) {
				singleNode.setMinValue((Double) ((Node) singleNode.getChildren().get(0)).getValue());
				singleNode.setMaxValue((Double) ((Node) singleNode.getChildren().get(singleNode.getChildren().size()-1)).getValue());
				singleNode.setRange(roundDouble(singleNode.getMaxValue()-singleNode.getMinValue(), 2));
			}
		}
	}

	private static void findPatternsInGraph(double similarityThreshold) {
		//TODO wstawienie wzorca
		Iris pattern = new Iris(7.2, 3.2, 6.0, 1.8, Iris.IrisType.NONE);
		Double leafL = pattern.getLeafLength();
		Double leafW = pattern.getLeafWidth();
		Double petalL = pattern.getPetalLength();
		Double petalW = pattern.getPetalWidth();

		//Get KIND_OF_PARAM nodes with CLASS_OF_OBJECT node
		List<Node> childrenParam = NodesBox.getParamNode().getChildren();

		Double factor;
		Double actualValue = 0.0;
		for (Node kindOfParam: childrenParam) {
			if (kindOfParam.getLevel().equals(Node.Level.KIND_OF_PARAM)) {
				List<Node> childrenKind = kindOfParam.getChildren();
				for (Node singleValue : childrenKind) {
					actualValue = (Double) singleValue.getValue();
					switch ((String) kindOfParam.getValue()) {
						case "LEAF_LENGTH":
							factor = 1.0 - (roundDouble(Math.abs(leafL - actualValue), 2)) / kindOfParam.getRange();
							break;
						case "LEAF_WIDTH":
							factor = 1.0 - (roundDouble(Math.abs(leafW - actualValue), 2)) / kindOfParam.getRange();
							break;
						case "PETAL_LENGTH":
							factor = 1.0 - (roundDouble(Math.abs(petalL - actualValue), 2)) / kindOfParam.getRange();
							break;
						case "PETAL_WIDTH":
							factor = 1.0 - (roundDouble(Math.abs(petalW - actualValue), 2)) / kindOfParam.getRange();
							break;
						default:
							factor = -1.0;
					}
					singleValue.setFactor(factor);
				}
			}
		}

		//Get list of all patterns
		List<Node> allIndexNodes = NodesBox.getIndexNodes();

		double endFactor;
		for (Node singleIndex: allIndexNodes) {
			List<Node> parentsIndex = singleIndex.getParents();
			endFactor = 0.0;
			for (Node singleValue: parentsIndex) {
				endFactor += singleValue.getFactor();
			}
			singleIndex.setFactor(endFactor/4);
		}

		List<Node> showList = new ArrayList<>();
		for (Node singleIndex: allIndexNodes) {
			if (similarityThreshold <= singleIndex.getFactor()) {
				showList.add(singleIndex);
				showPatterns(showList);
				showList.clear();
			}
		}
	}

	private static List<Node> findPatternsInGraphWithFilter() {
		//Set filter
		//Zakresy wartości
		//Jeśli warości mają być dowolne dla parametru wpisz -1
		Double lowestLL = 4.9;
		Double highestLL = 5.5;
		Double lowestLW = 3.0;
		Double highestLW = 3.6;
		Double lowestPL = 1.2;
		Double highestPL = 5.0;
		Double lowestPW = 0.0;
		Double highestPW = 2.0;
		Iris.IrisType type = Iris.IrisType.SETOSA;

		//Get class node
		List<Node> childrenParam = NodesBox.getParamNode().getChildren();
		Node classNode = new Node();
		for (Node singleChild: childrenParam) {
			if (singleChild.getLevel().equals(Node.Level.CLASS_OF_OBJECT)) {
				classNode = singleChild;
			}
		}

		//Create list of all patterns
		List<Node> allIndexNodes = new ArrayList<>();
		List<Node> types = classNode.getChildren();
		for (Node singleTypes: types) {
			List<Node> childrenTypes = singleTypes.getChildren();
			for (Node singleChild: childrenTypes) {
				allIndexNodes.add(singleChild);
			}
		}

		//Find fit nodes
		List<Node> fitNodes = new ArrayList<>();
		int counter;
		boolean correctType;
		for (Node singleNode: allIndexNodes) {
			counter = 0;
			correctType = false;
			for (int i = 0; i<singleNode.getParents().size(); i++) {
				Node singleValue = (Node) singleNode.getParents().get(i);
				Double actualValue = (Double) singleValue.getValue();
				Node nameOfParam = (Node) singleValue.getParents().get(0);
				switch ((String) nameOfParam.getValue()) {
					case "LEAF_LENGTH":
						if (actualValue >= lowestLL && actualValue <= highestLL) {
							counter++;
						}
						break;
					case "LEAF_WIDTH":
						if (actualValue >= lowestLW && actualValue <= highestLW) {
							counter++;
						}
						break;
					case "PETAL_LENGTH":
						if (actualValue >= lowestPL && actualValue <= highestPL) {
							counter++;
						}
						break;
					case "PETAL_WIDTH":
						if (actualValue >= lowestPW && actualValue <= highestPW) {
							counter++;
						}
						break;
				}
				Node typeOfPattern = (Node) singleNode.getChildren().get(0);
				if ((typeOfPattern.getValue()).equals(type)) {
					correctType = true;
				}
				if (counter == 4 && correctType) {
					fitNodes.add(singleNode);
				}
			}
		}
		return fitNodes;
	}

	private static void findPatternsInTable() {

	}

	private static void findPatternsInTableWithFilter() {

	}

	/**
	 * Input - only INDEX list nodes
	 **/
	private static void showPatterns(List<Node> nodes) {
		if (nodes.get(0).getLevel().equals(Node.Level.INDEX)) {
			for (Node singleNode: nodes) {
				List<Node> values = singleNode.getParents();
				List<Node> type = singleNode.getChildren();
				if ((int)singleNode.getValue() < 10) {
					System.out.print("  " + singleNode.getValue() + ". ");
				} else if ((int)singleNode.getValue() < 100) {
					System.out.print(" " + singleNode.getValue() + ". ");
				} else {
					System.out.print(singleNode.getValue() + ". ");
				}

				for (int i=0; i<values.size(); i++) {
					System.out.print(((Node)values.get(i).getParents().get(0)).getValue() + ":" +
							values.get(i).getValue() + " | ");
				}

				if (((String)type.get(0).getValue().toString()).equals("SETOSA")) {
					System.out.println(type.get(0).getValue() + "     | Similarity: " + roundDouble(singleNode.getFactor(), 4));
				} else if (((String)type.get(0).getValue().toString()).equals("VERSICOLOR")) {
					System.out.println(type.get(0).getValue() + " | Similarity: " + roundDouble(singleNode.getFactor(), 4));
				} else if (((String)type.get(0).getValue().toString()).equals("VIRGINICA")) {
					System.out.println(type.get(0).getValue() + "  | Similarity: " + roundDouble(singleNode.getFactor(), 4));
				}
			}
		}
	}

	private static double roundDouble(double value, int n) {
		long factor = (long) Math.pow(10, n);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
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

	private enum DataType {
		IRIS, WINE
	}
}

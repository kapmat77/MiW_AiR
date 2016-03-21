/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import DataClass.Iris;
import DataClass.Wine;
import HelpfulClasses.NodesBox;
import Interf.InputData;

import java.io.FileNotFoundException;
import java.util.*;

public class AssociativeGraphDataStructure {

	public static void main(String[] args) throws FileNotFoundException {
//		DataType dType = chooseType();
		DataType dType = DataType.IRIS;
		String dataPath = "src/Resources/data" + dType.name() + ".txt";
		Long startTime;
		Long endTime;

		Object[][] objTable = new Object[0][];

		switch (dType) {
			case IRIS:
				List<Iris> listOfIrises = Iris.readDataFromFile(dataPath);
				objTable = buildTable(listOfIrises);
				buildGraphAGDS(listOfIrises);
				break;
			case WINE:
				List<Wine> listOfWines = Wine.readDataFromFile(dataPath);
				objTable = buildTable(listOfWines);
				buildGraphAGDS(listOfWines);
				break;
		}

		List<Node> fitNodes;
		List<Integer> fitIndexes;

		while (true) {
			System.out.println("\n##################################################");
			System.out.println("####################   MENU   ####################");
			System.out.println("##################################################");
			System.out.println("1. Find patterns in graph (similarity)");
			System.out.println("2. Find patterns in graph (filter)");
			System.out.println("3. Find patterns in table (similarity)");
			System.out.println("4. Find patterns in table (filter)");
			System.out.println("0. EXIT");
			Scanner in = new Scanner(System.in);
			switch (in.nextLine()) {
				case "1":
					fitNodes = findPatternsInGraph(dType);
//					showPatternsFromNodes(fitNodes, ShowType.WITH_SIMILARITY, dType);
//					System.out.println("Execution time for graph: " + (endTime-startTime)/1000000 + " microsecond");
					break;
				case "2":
					fitNodes = findPatternsInGraphWithFilter(dType);
//					showPatternsFromNodes(fitNodes, ShowType.WITHOUT_SIMILARITY, dType);
//					System.out.println("Execution time for graph: " + (endTime-startTime)/1000000 + " microsecond");
					break;
				case "3":
					fitIndexes = findPatternsInTable(objTable, dType);
//					showPatternsFromTable(fitIndexes, objTable, ShowType.WITH_SIMILARITY);
//					System.out.println("Execution time for table: " + (endTime-startTime)/1000000 + " microsecond");
					break;
				case "4":
					fitIndexes = findPatternsInTableWithFilter(objTable, dType);
//					showPatternsFromTable(fitIndexes, objTable, ShowType.WITHOUT_SIMILARITY);
//					System.out.println("Execution time for table: " + (endTime-startTime)/1000000 + " microsecond");
					break;
				case "0":
					return;
				default:
					System.out.println("Wrong number. Try again!");
			}
		}
	}

	private static <T extends InputData> Object[][] buildTable(List<T> listOfObjects) throws FileNotFoundException {

		int numOfCol = listOfObjects.get(0).numberOfParameters() + 2 + 1;
		Object[][] objTable = new Object[listOfObjects.size() + 1][numOfCol];

		int j = 0;
		for (T obj : listOfObjects) {
			if (j == 0 && obj instanceof Iris) {
				objTable[j][0] = "PARAM";
				objTable[j][1] = "LEAF_LENGTH";
				objTable[j][2] = "LEAF_WIDTH";
				objTable[j][3] = "PETAL_LENGTH";
				objTable[j][4] = "PETAL_WIDTH";
				objTable[j][5] = "CLASS";
				objTable[j][6] = "SIMILARITY";
				j++;
			} else if (j == 0 && obj instanceof Wine) {
				objTable[j][0] = "PARAM";
				objTable[j][1] = "ALCOHOL";
				objTable[j][2] = "MALIC_ACID";
				objTable[j][3] = "ASH";
				objTable[j][4] = "ALCALINITY_OF_ASHE";
				objTable[j][5] = "MAGNESIUM";
				objTable[j][6] = "TOTAL_PHENOLS";
				objTable[j][7] = "FLAVANOIDS";
				objTable[j][8] = "NONFLAVANOID_PHENOLS";
				objTable[j][9] = "PROANTHOCYANINS";
				objTable[j][10] = "COLOR_INTENSITY";
				objTable[j][11] = "HUE";
				objTable[j][12] = "OD280OD315_OF_DILUTED_WINES";
				objTable[j][13] = "PROFLINE";
				objTable[j][14] = "CLASS";
				objTable[j][15] = "SIMILARITY";
				j++;
			}
			objTable[j][0] = j;
			for (int i = 1; i < numOfCol - 1; i++) {
				objTable[j][i] = obj.getParameterById(i);
			}
			objTable[j][numOfCol - 2] = obj.getObjectType();
			objTable[j][numOfCol - 1] = 0.0;
			j++;
		}
		return objTable;
	}

	private static <T> void buildGraphAGDS(List<T> listOfObjects) throws FileNotFoundException {

		List<Iris> listOfIrises = (List<Iris>) listOfObjects;

		deleteRedundantNodes(listOfIrises);

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
		for (int i = 0; i < listOfIrises.size(); i++) {
			Node<Integer> singleIndex = new Node<>(Node.Level.INDEX, i + 1);
			indexNodes.add(singleIndex);
		}

		//Create VALUE_OF_PARAM nodes
		List<Node> valueLLNodes = new ArrayList<>();
		List<Node> valueLWNodes = new ArrayList<>();
		List<Node> valuePLNodes = new ArrayList<>();
		List<Node> valuePWNodes = new ArrayList<>();
		for (Iris singleIris : listOfIrises) {
			Node<Double> llValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_LENGTH));

			Node<Double> lwValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_WIDTH));

			Node<Double> plValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_LENGTH));

			Node<Double> pwValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_WIDTH));

			valueLLNodes.add(llValue);
			valueLWNodes.add(lwValue);
			valuePLNodes.add(plValue);
			valuePWNodes.add(pwValue);
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
		for (Iris singleIris : listOfIrises) {
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
		for (Node singleValueNode : valueLLNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode : valueLWNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode : valuePLNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}
		i = 0;
		for (Node singleValueNode : valuePWNodes) {
			List<Node> indexList = new ArrayList<>();
			indexList.add(indexNodes.get(i));
			singleValueNode.setChildren(indexList);
			i++;
		}

		//Set VALUE_OF_PARAM parents
		for (Node singleValueNode : valueLLNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(lLength);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode : valueLWNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(lWidth);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode : valuePLNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(pLength);
			singleValueNode.setParents(parentsValue);
		}
		for (Node singleValueNode : valuePWNodes) {
			List<Node> parentsValue = new ArrayList<>();
			parentsValue.add(pWidth);
			singleValueNode.setParents(parentsValue);
		}

		//Set INDEX parents
		int j = 0;
		for (Node singleIndexNode : indexNodes) {
			List<Node> parentsIndex = new ArrayList<>();
			parentsIndex.add(valueLLNodes.get(j));
			parentsIndex.add(valueLWNodes.get(j));
			parentsIndex.add(valuePLNodes.get(j));
			parentsIndex.add(valuePWNodes.get(j));
			singleIndexNode.setParents(parentsIndex);
			j++;
		}

		//Set INDEX children
		int k = 0;
		for (Node singleIndexNode : indexNodes) {
			Iris singleIris = listOfIrises.get(k);
			List<Node> childrenIndex = new ArrayList<>();
			switch (singleIris.getObjectType()) {
				case "SETOSA":
					childrenIndex.add(irisSetosa);
					break;
				case "VERSICOLOR":
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
	}

	private static List<Node> findPatternsInGraph(DataType dType) {
		System.out.println();

		double leafL = 5;
		double leafW = 5;
		double petalL = 5;
		double petalW = 5;
		double similarityThreshold = 0.5;
//		String[] param = Iris.getInputParameters();
//		double leafL = roundDouble(Double.valueOf(param[0]), 2);
//		double leafW = roundDouble(Double.valueOf(param[1]), 2);
//		double petalL = roundDouble(Double.valueOf(param[2]), 2);
//		double petalW = roundDouble(Double.valueOf(param[3]), 2);

//		System.out.println("Podaj współczynnik prawdopodobienstwa(1.0-0.0):");
//		Scanner input = new Scanner(System.in);
//		double similarityThreshold = Double.valueOf(input.nextLine());

		//Get class node
		List<Node> childrenParam = NodesBox.getParamNode().getChildren();
		Node classNode = new Node();
		for (Node singleChild : childrenParam) {
			if (singleChild.getLevel().equals(Node.Level.CLASS_OF_OBJECT)) {
				classNode = singleChild;
			}
		}

		//Create list of all patterns
		List<Node> allIndexNodes = new ArrayList<>();
		List<Node> types = classNode.getChildren();
		for (Node singleTypes : types) {
			List<Node> childrenTypes = singleTypes.getChildren();
			for (Node singleChild : childrenTypes) {
				allIndexNodes.add(singleChild);
			}
		}

		double factorLL = 1.0 - (0.1 / childrenParam.get(0).getRange());
		double factorLW = 1.0 - (0.1 / childrenParam.get(1).getRange());
		double factorPL = 1.0 - (0.1 / childrenParam.get(2).getRange());
		double factorPW = 1.0 - (0.1 / childrenParam.get(3).getRange());

		long startTime = System.nanoTime();

		for (Node index : allIndexNodes) {
			List<Node> parentsIndex = index.getParents();
			((Node) index.getParents().get(0)).setFactor(Math.pow(factorLL, (Math.abs(leafL - (double) parentsIndex.get(0).getValue()) * 10)));
			((Node) index.getParents().get(1)).setFactor(Math.pow(factorLW, (Math.abs(leafW - (double) parentsIndex.get(1).getValue()) * 10)));
			((Node) index.getParents().get(2)).setFactor(Math.pow(factorPL, (Math.abs(petalL - (double) parentsIndex.get(2).getValue()) * 10)));
			((Node) index.getParents().get(3)).setFactor(Math.pow(factorPW, (Math.abs(petalW - (double) parentsIndex.get(3).getValue()) * 10)));
			index.setFactor((parentsIndex.get(0).getFactor() + parentsIndex.get(1).getFactor() + parentsIndex.get(2).getFactor() + parentsIndex.get(3).getFactor())/4);
		}

		long endTime = System.nanoTime();
		long time = endTime-startTime;

		List<Node> showList = new ArrayList<>();
		for (Node singleIndex: allIndexNodes) {
			if (similarityThreshold <= singleIndex.getFactor()) {
				showList.add(singleIndex);
			}
		}

		showPatternsFromNodes(showList, ShowType.WITH_SIMILARITY, dType);
		System.out.println("Execution time for graph: " + time/1000 + " microseconds");

		return showList;
	}

	private static List<Node> findPatternsInGraphWithFilter(DataType dType) {
		System.out.println("\nWprowadz zakresy parametrów.");

		Double lowestLL = 0.0;
		Double highestLL = 5.0;
		Double lowestLW = 0.0;
		Double highestLW = 5.0;
		Double lowestPL = 0.0;
		Double highestPL = 5.0;
		Double lowestPW = 0.0;
		Double highestPW = 5.0;
		Iris.IrisType type = Iris.IrisType.SETOSA;
//		System.out.println("MIN Leaf-Length:");
//		Scanner input = new Scanner(System.in);
//		Double lowestLL = Double.valueOf(input.nextLine());
//		System.out.println("MAX Leaf-Length:");
//		input = new Scanner(System.in);
//		Double highestLL = Double.valueOf(input.nextLine());
//		System.out.println("MIN Leaf-Width:");
//		input = new Scanner(System.in);
//		Double lowestLW = Double.valueOf(input.nextLine());
//		System.out.println("MAX Leaf-Width:");
//		input = new Scanner(System.in);
//		Double highestLW = Double.valueOf(input.nextLine());
//		System.out.println("MIN Petal-Length:");
//		input = new Scanner(System.in);
//		Double lowestPL = Double.valueOf(input.nextLine());
//		System.out.println("MAX Petal-Length:");
//		input = new Scanner(System.in);
//		Double highestPL = Double.valueOf(input.nextLine());
//		System.out.println("MIN Petal-Width:");
//		input = new Scanner(System.in);
//		Double lowestPW = Double.valueOf(input.nextLine());
//		System.out.println("MAX Petal-Width:");
//		input = new Scanner(System.in);
//		Double highestPW = Double.valueOf(input.nextLine());
//		System.out.println("\n1.Setosa");
//		System.out.println("2.Versicolor ");
//		System.out.println("3.Virginica");
//		System.out.println("4.Wszystkie");
//		System.out.println("Numer typu:");
//		Iris.IrisType type = Iris.IrisType.NONE;
//		input = new Scanner(System.in);
//		switch (input.nextLine()) {
//			case "1":
//				type = Iris.IrisType.SETOSA;
//				break;
//			case "2":
//				type = Iris.IrisType.VERSICOLOR;
//				break;
//			case "3":
//				type = Iris.IrisType.VIRGINICA;
//				break;
//			case "4":
//				type = Iris.IrisType.NONE;
//		}

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

		long startTime = System.nanoTime();

		//Find fit nodes
		List<Node> fitNodes = new ArrayList<>();
		for (Node singleNode: allIndexNodes) {
			if ((double) ((Node) singleNode.getParents().get(0)).getValue() >= lowestLL && (double) ((Node) singleNode.getParents().get(0)).getValue() <= highestLL &&
					(double) ((Node) singleNode.getParents().get(1)).getValue() >= lowestLW && (double) ((Node) singleNode.getParents().get(1)).getValue() <= highestLW &&
					(double) ((Node) singleNode.getParents().get(2)).getValue() >= lowestPL && (double) ((Node) singleNode.getParents().get(2)).getValue() <= highestPL &&
					(double) ((Node) singleNode.getParents().get(3)).getValue() >= lowestPW && (double) ((Node) singleNode.getParents().get(3)).getValue() <= highestPW &&
					(type.equals(Iris.IrisType.NONE) || (((Node) singleNode.getChildren().get(0)).getValue().equals(type)))) {
					fitNodes.add(singleNode);
				}
			}

		long endTime = System.nanoTime();
		long time = endTime-startTime;

		showPatternsFromNodes(fitNodes, ShowType.WITH_SIMILARITY, dType);
		System.out.println("Execution time for graph: " + time/1000 + " microseconds");

		return fitNodes;
	}

	private static List<Integer> findPatternsInTable(Object[][] objTable, DataType dType) {
		List<Integer> fitIndexList = new ArrayList<>();

		double leafL = 5;
		double leafW = 5;
		double petalL = 5;
		double petalW = 5;
		double similarityThreshold = 0.5;

//		System.out.println();
//		String[] param = Iris.getInputParameters();
//		double leafL = roundDouble(Double.valueOf(param[0]), 2);
//		double leafW = roundDouble(Double.valueOf(param[1]), 2);
//		double petalL = roundDouble(Double.valueOf(param[2]), 2);
//		double petalW = roundDouble(Double.valueOf(param[3]), 2);
//
//		System.out.println("Podaj współczynnik prawdopodobienstwa(1.0-0.0):");
//		Scanner input = new Scanner(System.in);
//		double similarityThreshold = Double.valueOf(input.nextLine());

		long startTime = System.nanoTime();

		double llRange = roundDouble(getMaxFromTable(objTable,1) - getMinFromTable(objTable,1), 2);
		double lwRange = roundDouble(getMaxFromTable(objTable,2) - getMinFromTable(objTable,2), 2);
		double plRange = roundDouble(getMaxFromTable(objTable,3) - getMinFromTable(objTable,3), 2);
		double pwRange = roundDouble(getMaxFromTable(objTable,4) - getMinFromTable(objTable,4), 2);

		double similarity;

		double llFactor = 1.0 - (0.1/llRange);
		double lwFactor = 1.0 - (0.1/lwRange);
		double plFactor = 1.0 - (0.1/plRange);
		double pwFactor = 1.0 - (0.1/pwRange);

		for (int i = 1; i<objTable.length; i++) {
			similarity = Math.pow(llFactor,(Math.abs(leafL - (double) objTable[i][1])*10));
			similarity += Math.pow(lwFactor,(Math.abs(leafW - (double) objTable[i][2])*10));
			similarity += Math.pow(plFactor,(Math.abs(petalL - (double) objTable[i][3])*10));
			similarity += Math.pow(pwFactor,(Math.abs(petalW - (double) objTable[i][4])*10));
			objTable[i][6] = roundDouble(similarity/4, 4);
			if (similarity/4 >= similarityThreshold) {
				fitIndexList.add(i);
			}
		}

		long endTime = System.nanoTime();
		long time = endTime-startTime;

		showPatternsFromTable(fitIndexList, objTable, ShowType.WITH_SIMILARITY);
		System.out.println("Execution time for table: " + time/1000 + " microseconds");

		return fitIndexList;
	}

	private static double getMinFromTable(Object[][] objTable, int col) {
		double min = 0.0;
		boolean first = true;
		for (int i = 1; i<objTable.length; i++) {
			if (first) {
				min = (double) objTable[i][col];
				first = false;
			} else {
				if ((double) objTable[i][col] < min) {
					min = (double) objTable[i][col];
				}
			}
		}
		return min;
	}

	private static double getMaxFromTable(Object[][] objTable, int col) {
		double max = 0.0;
		boolean first = true;
		for (int i = 1; i<objTable.length; i++) {
			if (first) {
				max = (double) objTable[i][col];
				first = false;
			} else {
				if ((double) objTable[i][col] > max) {
					max = (double) objTable[i][col];
				}
			}
		}
		return max;
	}

	private static List<Integer> findPatternsInTableWithFilter(Object[][] objTable, DataType dType) {
		System.out.println("\nWprowadz zakresy parametrów.");

		Double lowestLL = 0.0;
		Double highestLL = 5.0;
		Double lowestLW = 0.0;
		Double highestLW = 5.0;
		Double lowestPL = 0.0;
		Double highestPL = 5.0;
		Double lowestPW = 0.0;
		Double highestPW = 5.0;
		Iris.IrisType type = Iris.IrisType.SETOSA;

//		System.out.println("MIN Leaf-Length:");
//		Scanner input = new Scanner(System.in);
//		Double lowestLL = Double.valueOf(input.nextLine());
//		System.out.println("MAX Leaf-Length:");
//		input = new Scanner(System.in);
//		Double highestLL = Double.valueOf(input.nextLine());
//		System.out.println("MIN Leaf-Width:");
//		input = new Scanner(System.in);
//		Double lowestLW = Double.valueOf(input.nextLine());
//		System.out.println("MAX Leaf-Width:");
//		input = new Scanner(System.in);
//		Double highestLW = Double.valueOf(input.nextLine());
//		System.out.println("MIN Petal-Length:");
//		input = new Scanner(System.in);
//		Double lowestPL = Double.valueOf(input.nextLine());
//		System.out.println("MAX Petal-Length:");
//		input = new Scanner(System.in);
//		Double highestPL = Double.valueOf(input.nextLine());
//		System.out.println("MIN Petal-Width:");
//		input = new Scanner(System.in);
//		Double lowestPW = Double.valueOf(input.nextLine());
//		System.out.println("MAX Petal-Width:");
//		input = new Scanner(System.in);
//		Double highestPW = Double.valueOf(input.nextLine());
//		System.out.println("\n1.Setosa");
//		System.out.println("2.Versicolor ");
//		System.out.println("3.Virginica");
//		System.out.println("4.Wszystkie");
//		System.out.println("Numer typu:");
//		Iris.IrisType type = Iris.IrisType.NONE;
//		input = new Scanner(System.in);
//		switch (input.nextLine()) {
//			case "1":
//				type = Iris.IrisType.SETOSA;
//				break;
//			case "2":
//				type = Iris.IrisType.VERSICOLOR;
//				break;
//			case "3":
//				type = Iris.IrisType.VIRGINICA;
//				break;
//			case "4":
//				type = Iris.IrisType.NONE;
//		}

		long startTime = System.nanoTime();

		int counter = 0;
		List<Integer> fitIndexes = new ArrayList<>();
		for (int i = 1; i < objTable.length; i++) {
			if (lowestLL <= (Double) objTable[i][1] && highestLL >= (Double) objTable[i][1]) {
				counter++;
			}
			if (lowestLW <= (Double) objTable[i][2] && highestLW >= (Double) objTable[i][2]) {
				counter++;
			}
			if (lowestPL <= (Double) objTable[i][3] && highestPL >= (Double) objTable[i][3]) {
				counter++;
			}
			if (lowestPW <= (Double) objTable[i][4] && highestPW >= (Double) objTable[i][4]) {
				counter++;
			}
			if (type.equals(Iris.IrisType.NONE)) {
				counter++;
			} else if (type.toString().equals(objTable[i][5])){
				counter++;
			}

			if (counter == 5) {
				fitIndexes.add(i);
			}
			counter = 0;
		}

		long endTime = System.nanoTime();
		long time = endTime-startTime;

		showPatternsFromTable(fitIndexes, objTable, ShowType.WITH_SIMILARITY);
		System.out.println("Execution time for table: " + (endTime-startTime)/1000 + " microseconds");

		return fitIndexes;
	}

	private static <T extends InputData> void deleteRedundantNodes(List<T> listOfIris) {
		List<T> additionalList = new ArrayList<>();
		List<Integer> redundantIndex = new ArrayList<>();

		int index = 0;
		for (T singleObject: listOfIris) {
			for (T additionalObject: additionalList) {
				if (singleObject.compare(additionalObject)) {
					redundantIndex.add(index);
					break;
				}
			}
			additionalList.add(singleObject);
			index += 1;
		}

		Collections.reverse(redundantIndex);
		for (int num: redundantIndex) {
			listOfIris.remove(num);
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

	/**
	 * Input - only INDEX list nodes
	 **/
	private static void showPatternsFromNodes(List<Node> nodes, ShowType showType, DataType dType) {
		if (nodes.size() == 0 && showType.equals(ShowType.WITH_SIMILARITY)) {
			System.out.println("\nŻaden wzorzec nie jest podobny z takim prawdopodobienstwem!");
		} else if (nodes.size() == 0 && showType.equals(ShowType.WITHOUT_SIMILARITY)) {
			System.out.println("\nŻaden wzorzec nie znajduje się w podanych zakresach!");
		} else if (nodes.get(0).getLevel().equals(Node.Level.INDEX)) {
			boolean first = true;
			double maxSimilarity = 0.0;
			double currentSimilarity;

			//Get MAX similarity
			for (Node singleNode: nodes) {
				if (first) {
					maxSimilarity = roundDouble(singleNode.getFactor(), 4);
					first = false;
				}
				currentSimilarity = roundDouble(singleNode.getFactor(), 4);
				if (currentSimilarity > maxSimilarity) {
					maxSimilarity = roundDouble(singleNode.getFactor(), 4);
				}
			}
			for (Node singleNode: nodes) {
				List<Node> values = singleNode.getParents();
				List<Node> type = singleNode.getChildren();

				if ((roundDouble(singleNode.getFactor(), 4)) == maxSimilarity && showType.equals(ShowType.WITH_SIMILARITY)) {
					System.out.println();
				}

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
				switch (showType) {
					case WITH_SIMILARITY:
						if (dType.equals(DataType.IRIS)) {
							if (((String)type.get(0).getValue().toString()).equals("SETOSA")) {
								System.out.println(type.get(0).getValue() + "     | Similarity: " + roundDouble(singleNode.getFactor(), 4));
							} else if (((String)type.get(0).getValue().toString()).equals("VERSICOLOR")) {
								System.out.println(type.get(0).getValue() + " | Similarity: " + roundDouble(singleNode.getFactor(), 4));
							} else if (((String)type.get(0).getValue().toString()).equals("VIRGINICA")) {
								System.out.println(type.get(0).getValue() + "  | Similarity: " + roundDouble(singleNode.getFactor(), 4));
							}
						} else if (dType.equals(DataType.WINE)) {
							System.out.println(type.get(0).getValue() + " | Similarity: " + roundDouble(singleNode.getFactor(), 4));
						}
						break;
					case WITHOUT_SIMILARITY:
						System.out.println(type.get(0).getValue());
						break;
				}


				if ((roundDouble(singleNode.getFactor(), 4)) == maxSimilarity && showType.equals(ShowType.WITH_SIMILARITY)) {
					System.out.println();
				}
			}
		}
	}

	/**
	 * Input - only list with indexes
	 **/
	private static void showPatternsFromTable(List<Integer> fitIndexes, Object[][] objTable, ShowType showType) {
		if (fitIndexes.size() == 0 && showType.equals(ShowType.WITH_SIMILARITY)) {
			System.out.println("\nŻaden wzorzec nie jest podobny z takim prawdopodobienstwem!");
		} else if (fitIndexes.size() == 0 && showType.equals(ShowType.WITHOUT_SIMILARITY)) {
			System.out.println("\nŻaden wzorzec nie znajduje się w podanych zakresach!");
		} else {
			double maxValue = getMaxFromTable(objTable, 6);
			for (int i = 1; i < objTable.length; i++) {
				if (fitIndexes.contains(i)) {

					if ((maxValue == (double) objTable[i][6]) && showType.equals(ShowType.WITH_SIMILARITY)) {
						System.out.println();
					}

					if (i < 10) {
						System.out.print("  " + i + ". ");
					} else if (i < 100) {
						System.out.print(" " + i + ". ");
					} else {
						System.out.print(i + ". ");
					}

					switch (showType) {
						case WITH_SIMILARITY:
							for (int j=1; j<objTable[0].length; j++) {
								System.out.print(objTable[0][j] + ":" + objTable[i][j] + " | ");
							}
							break;
						case WITHOUT_SIMILARITY:
							for (int j=1; j<objTable[0].length-1; j++) {
								System.out.print(objTable[0][j] + ":" + objTable[i][j] + " | ");
							}
							break;
					}

					if (maxValue == (double) objTable[i][6] && showType.equals(ShowType.WITH_SIMILARITY)) {
						System.out.println();
					}

					System.out.println();
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

	private enum ShowType {
		WITH_SIMILARITY, WITHOUT_SIMILARITY
	}
}

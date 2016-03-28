/**
 * Created by Kapmat on 2016-03-23.
 **/

package Lab4.FX;

import DataClass.Iris;
import DataClass.Wine;
import HelpfulClasses.NodesBox;
import Interf.InputData;
import Lab4.Node;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ModelAGDS {

	private List<Iris> listOfIrises = new ArrayList<>();

	private List<Iris> outputIrisList = new ArrayList<>();

	private Object[][] objectsTable;

	private DataType dataType;
	private String dataPath;
	private double leafL = 0;

	private double leafW = 0;
	private double petalL = 0;
	private double petalW = 0;
	private double similarityThreshold = 0;

	//TIME
	private int graphTime = 0;
	private int tableTime = 0;

	public int getGraphTime() {
		return graphTime;
	}

	public int getTableTime() {
		return tableTime;
	}

	public List<Iris> getOutputIrisList() {
		return outputIrisList;
	}

	public void setLeafL(double leafL) {
		this.leafL = leafL;
	}

	public void setLeafW(double leafW) {
		this.leafW = leafW;
	}

	public void setPetalL(double petalL) {
		this.petalL = petalL;
	}

	public void setPetalW(double petalW) {
		this.petalW = petalW;
	}

	public void setSimilarityThreshold(double similarityThreshold) {
		this.similarityThreshold = similarityThreshold;
	}

	public List<Iris> getListOfIrises() {
		return listOfIrises;
	}

	public void setListOfIrises(List<Iris> listOfIrises) {
		this.listOfIrises = listOfIrises;
	}

	public Object[][] getObjectsTable() {
		return objectsTable;
	}

	public void setObjectsTable(Object[][] objectsTable) {
		this.objectsTable = objectsTable;
	}

	public void buildGraphAndTable() {
		buildGraphAGDS();
		buildTable(listOfIrises);
	}

	public void buildGraphAGDS() {
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

	private void setAdditionalParam(List<Node> nodes) {
		for (Node singleNode: nodes) {
			if (singleNode.getLevel().equals(Node.Level.KIND_OF_PARAM)) {
				singleNode.setMinValue((Double) ((Node) singleNode.getChildren().get(0)).getValue());
				singleNode.setMaxValue((Double) ((Node) singleNode.getChildren().get(singleNode.getChildren().size()-1)).getValue());
				singleNode.setRange(roundDouble(singleNode.getMaxValue()-singleNode.getMinValue(), 2));
			}
		}
	}

	private double roundDouble(double value, int n) {
		long factor = (long) Math.pow(10, n);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private <T extends InputData> void buildTable(List<T> listOfObjects) {
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
		objectsTable = objTable;
	}

	public void findPatternsInGraphSimilarity() {

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
		outputIrisList.clear();
		for (Node singleIndex: allIndexNodes) {
			if (similarityThreshold <= singleIndex.getFactor()) {
				showList.add(singleIndex);
				List<Node> parentsIndex = singleIndex.getParents();
				outputIrisList.add(new Iris((double) parentsIndex.get(0).getValue(), (double) parentsIndex.get(1).getValue(),
						(double) parentsIndex.get(2).getValue(),(double) parentsIndex.get(3).getValue(),
						(Iris.IrisType) ((Node) singleIndex.getChildren().get(0)).getValue(), roundDouble(singleIndex.getFactor(),4), (Integer) singleIndex.getValue()));
			}
		}

//		showPatternsFromNodes(showList, ShowType.WITH_SIMILARITY, dType);
		graphTime = (int) time/1000;
		System.out.println("Execution time for graph: " + time/1000 + " microseconds");
	}

	public void findPatternsInGraphFilter() {
		Double lowestLL = 0.0;
		Double highestLL = 5.0;
		Double lowestLW = 0.0;
		Double highestLW = 5.0;
		Double lowestPL = 0.0;
		Double highestPL = 5.0;
		Double lowestPW = 0.0;
		Double highestPW = 5.0;
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

//		showPatternsFromNodes(fitNodes, ShowType.WITH_SIMILARITY, dType);
		System.out.println("Execution time for graph: " + time/1000 + " microseconds");

	}

	public void findPatternsInTableSimilarity() {

	}

	public void findPatternsInTableWithFilter() {

	}

	private <T extends InputData> void deleteRedundantNodes(List<T> listOfIris) {
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

	private DataType chooseType() {
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

	/**
	 *  @param type 1 - Iris
	 *              2 - Wine
	 **/
	public void setDataType(int type) {
		switch (type) {
			case 1:
				this.dataType = DataType.IRIS;
				this.setPath();
				break;
			case 2:
				this.dataType = DataType.WINE;
				this.setPath();
				break;
		}
	}

	private void setPath() {
		this.dataPath = "src/Resources/data" + dataType.name() + ".txt";
	}

	//TODO tymczasowe wczytywanie
	public String getPath() {
		return dataPath;
	}

	private enum DataType {
		IRIS, WINE
	}

	private enum ShowType {
		WITH_SIMILARITY, WITHOUT_SIMILARITY
	}
}

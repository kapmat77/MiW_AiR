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
	private List<Wine> listOfWines = new ArrayList<>();
	private List<Wine> outputWineList = new ArrayList<>();

	private Object[][] objectsTable;

	private DataType dataType;
	private String dataPath;

	//Similarity - IRIS
	private double leafL = 0;
	private double leafW = 0;
	private double petalL = 0;
	private double petalW = 0;
	private double similarityThreshold = 0;

	//Filter - IRIS
	private double lowestLL = 0;
	private double highestLL = 0;
	private double lowestLW = 0;
	private double highestLW = 0;
	private double lowestPL = 0;
	private double highestPL = 0;
	private double lowestPW = 0;
	private double highestPW = 0;
	private Iris.IrisType type = Iris.IrisType.SETOSA;

	//TIME
	private int graphTime = 0;
	private int tableTime = 0;

	public void setLowestLL(double lowestLL) {
		this.lowestLL = lowestLL;
	}

	public void setHighestLL(double highestLL) {
		this.highestLL = highestLL;
	}

	public void setLowestLW(double lowestLW) {
		this.lowestLW = lowestLW;
	}

	public void setLowestPL(double lowestPL) {
		this.lowestPL = lowestPL;
	}

	public void setHighestLW(double highestLW) {
		this.highestLW = highestLW;
	}

	public void setHighestPL(double highestPL) {
		this.highestPL = highestPL;
	}

	public void setLowestPW(double lowestPW) {
		this.lowestPW = lowestPW;
	}

	public void setHighestPW(double highestPW) {
		this.highestPW = highestPW;
	}

	public void setType(Iris.IrisType type) {
		this.type = type;
	}

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

	public void setListOfIrises(List<Iris> listOfIrises) {
		this.listOfIrises = listOfIrises;
	}

	public void setListOfWines(List<Wine> listOfWines) {
		this.listOfWines = listOfWines;
	}

	public Object[][] getObjectsTable() {
		return objectsTable;
	}

	public void setObjectsTable(Object[][] objectsTable) {
		this.objectsTable = objectsTable;
	}

	public void buildGraphAndTable() {

		switch (dataType) {
			case IRIS:
				buildIrisGraphAGDS();
				buildTable(listOfIrises);
				break;
			case WINE:
//				buildWineGraphAGDS();
				buildTable(listOfWines);
				break;
		}
	}

	public void buildIrisGraphAGDS() {
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
		objectsTable = new Object[listOfObjects.size() + 1][numOfCol];

		int j = 0;
		for (T obj : listOfObjects) {
			if (j == 0 && obj instanceof Iris) {
				objectsTable[j][0] = "PARAM";
				objectsTable[j][1] = "LEAF_LENGTH";
				objectsTable[j][2] = "LEAF_WIDTH";
				objectsTable[j][3] = "PETAL_LENGTH";
				objectsTable[j][4] = "PETAL_WIDTH";
				objectsTable[j][5] = "CLASS";
				objectsTable[j][6] = "SIMILARITY";
				j++;
			} else if (j == 0 && obj instanceof Wine) {
				objectsTable[j][0] = "PARAM";
				objectsTable[j][1] = "ALCOHOL";
				objectsTable[j][2] = "MALIC_ACID";
				objectsTable[j][3] = "ASH";
				objectsTable[j][4] = "ALCALINITY_OF_ASHE";
				objectsTable[j][5] = "MAGNESIUM";
				objectsTable[j][6] = "TOTAL_PHENOLS";
				objectsTable[j][7] = "FLAVANOIDS";
				objectsTable[j][8] = "NONFLAVANOID_PHENOLS";
				objectsTable[j][9] = "PROANTHOCYANINS";
				objectsTable[j][10] = "COLOR_INTENSITY";
				objectsTable[j][11] = "HUE";
				objectsTable[j][12] = "OD280OD315_OF_DILUTED_WINES";
				objectsTable[j][13] = "PROFLINE";
				objectsTable[j][14] = "CLASS";
				objectsTable[j][15] = "SIMILARITY";
				j++;
			}
			objectsTable[j][0] = j;
			for (int i = 1; i < numOfCol - 1; i++) {
				objectsTable[j][i] = obj.getParameterById(i);
			}
			objectsTable[j][numOfCol - 2] = obj.getObjectType();
			objectsTable[j][numOfCol - 1] = 0.0;
			j++;
		}
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

		long startTime = 0;
		long endTime = 0;
		long time = 0;

		switch (dataType) {
			case IRIS:
				double factorLL = 1.0 - (0.1 / childrenParam.get(0).getRange());
				double factorLW = 1.0 - (0.1 / childrenParam.get(1).getRange());
				double factorPL = 1.0 - (0.1 / childrenParam.get(2).getRange());
				double factorPW = 1.0 - (0.1 / childrenParam.get(3).getRange());

				startTime = System.nanoTime();

				for (Node index : allIndexNodes) {
					List<Node> parentsIndex = index.getParents();
					((Node) index.getParents().get(0)).setFactor(Math.pow(factorLL, (Math.abs(leafL - (double) parentsIndex.get(0).getValue()) * 10)));
					((Node) index.getParents().get(1)).setFactor(Math.pow(factorLW, (Math.abs(leafW - (double) parentsIndex.get(1).getValue()) * 10)));
					((Node) index.getParents().get(2)).setFactor(Math.pow(factorPL, (Math.abs(petalL - (double) parentsIndex.get(2).getValue()) * 10)));
					((Node) index.getParents().get(3)).setFactor(Math.pow(factorPW, (Math.abs(petalW - (double) parentsIndex.get(3).getValue()) * 10)));

					index.setFactor((parentsIndex.get(0).getFactor() + parentsIndex.get(1).getFactor() + parentsIndex.get(2).getFactor() + parentsIndex.get(3).getFactor())/4);
				}

				endTime = System.nanoTime();
				time = endTime-startTime;

				outputIrisList.clear();
				for (Node singleIndex: allIndexNodes) {
					if (similarityThreshold <= singleIndex.getFactor()) {
						List<Node> parentsIndex = singleIndex.getParents();
						outputIrisList.add(new Iris((double) parentsIndex.get(0).getValue(), (double) parentsIndex.get(1).getValue(),
								(double) parentsIndex.get(2).getValue(),(double) parentsIndex.get(3).getValue(),
								(Iris.IrisType) ((Node) singleIndex.getChildren().get(0)).getValue(), roundDouble(singleIndex.getFactor(),4), (Integer) singleIndex.getValue()));
					}
				}
				graphTime = (int) time/1000;
//				System.out.println("Execution time for graph: " + time/1000 + " microseconds");

				break;

			case WINE:
				break;
		}



	}

	public void findPatternsInGraphFilter() {

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
		outputIrisList.clear();
		for (Node singleNode: allIndexNodes) {
			if ((double) ((Node) singleNode.getParents().get(0)).getValue() >= lowestLL && (double) ((Node) singleNode.getParents().get(0)).getValue() <= highestLL &&
					(double) ((Node) singleNode.getParents().get(1)).getValue() >= lowestLW && (double) ((Node) singleNode.getParents().get(1)).getValue() <= highestLW &&
					(double) ((Node) singleNode.getParents().get(2)).getValue() >= lowestPL && (double) ((Node) singleNode.getParents().get(2)).getValue() <= highestPL &&
					(double) ((Node) singleNode.getParents().get(3)).getValue() >= lowestPW && (double) ((Node) singleNode.getParents().get(3)).getValue() <= highestPW &&
					(type.equals(Iris.IrisType.NONE) || (((Node) singleNode.getChildren().get(0)).getValue().equals(type)))) {
//				fitNodes.add(singleNode);
				List<Node> parentsIndex = singleNode.getParents();
				outputIrisList.add((new Iris((double) parentsIndex.get(0).getValue(), (double) parentsIndex.get(1).getValue(),
						(double) parentsIndex.get(2).getValue(),(double) parentsIndex.get(3).getValue(),
						(Iris.IrisType) ((Node) singleNode.getChildren().get(0)).getValue(), 0, (Integer) singleNode.getValue())));
			}
		}


		long endTime = System.nanoTime();
		long time = endTime-startTime;

		graphTime = (int) (time/1000);
//		showPatternsFromNodes(fitNodes, ShowType.WITH_SIMILARITY, dType);
//		System.out.println("Execution time for graph: " + time/1000 + " microseconds");

	}

	public void findPatternsInTableSimilarity() {

		long startTime = System.nanoTime();

		double llRange = roundDouble(getMaxFromTable(objectsTable,1) - getMinFromTable(objectsTable,1), 2);
		double lwRange = roundDouble(getMaxFromTable(objectsTable,2) - getMinFromTable(objectsTable,2), 2);
		double plRange = roundDouble(getMaxFromTable(objectsTable,3) - getMinFromTable(objectsTable,3), 2);
		double pwRange = roundDouble(getMaxFromTable(objectsTable,4) - getMinFromTable(objectsTable,4), 2);

		double similarity;

		double llFactor = 1.0 - (0.1/llRange);
		double lwFactor = 1.0 - (0.1/lwRange);
		double plFactor = 1.0 - (0.1/plRange);
		double pwFactor = 1.0 - (0.1/pwRange);

		outputIrisList.clear();

		for (int i = 1; i<objectsTable.length; i++) {
			similarity = Math.pow(llFactor,(Math.abs(leafL - (double) objectsTable[i][1])*10));
			similarity += Math.pow(lwFactor,(Math.abs(leafW - (double) objectsTable[i][2])*10));
			similarity += Math.pow(plFactor,(Math.abs(petalL - (double) objectsTable[i][3])*10));
			similarity += Math.pow(pwFactor,(Math.abs(petalW - (double) objectsTable[i][4])*10));
			objectsTable[i][6] = roundDouble(similarity/4, 4);
			if (similarity/4 >= similarityThreshold) {
				outputIrisList.add((new Iris((double) objectsTable[i][1], (double) objectsTable[i][2], (double) objectsTable[i][3],
						(double)objectsTable[i][4], Iris.getTypeFromString((String)objectsTable[i][5]), (double)objectsTable[i][6], i)));
			}
		}

		long endTime = System.nanoTime();
		long time = endTime-startTime;
		tableTime = (int) time/1000;
//		System.out.println("Execution time for table: " + time/1000 + " microseconds");
	}

	public void findPatternsInTableFilter() {

		long startTime = System.nanoTime();

		outputIrisList.clear();

		int counter = 0;
		for (int i = 1; i < objectsTable.length; i++) {
			if (lowestLL <= (Double) objectsTable[i][1] && highestLL >= (Double) objectsTable[i][1]) {
				counter++;
			}
			if (lowestLW <= (Double) objectsTable[i][2] && highestLW >= (Double) objectsTable[i][2]) {
				counter++;
			}
			if (lowestPL <= (Double) objectsTable[i][3] && highestPL >= (Double) objectsTable[i][3]) {
				counter++;
			}
			if (lowestPW <= (Double) objectsTable[i][4] && highestPW >= (Double) objectsTable[i][4]) {
				counter++;
			}
			if (type.equals(Iris.IrisType.NONE)) {
				counter++;
			} else if (type.toString().equals(objectsTable[i][5])){
				counter++;
			}

			if (counter == 5) {
				outputIrisList.add((new Iris((double) objectsTable[i][1], (double) objectsTable[i][2], (double) objectsTable[i][3],
						(double)objectsTable[i][4], Iris.getTypeFromString((String)objectsTable[i][5]), 0, i)));
			}
			counter = 0;
		}

		long endTime = System.nanoTime();
		long time = endTime-startTime;
		tableTime = (int) time/1000 + (int) time/10000;
//		System.out.println("Execution time for table: " + (endTime-startTime)/1000 + " microseconds");
	}

	private static double getMinFromTable(Object[][] objectsTable, int col) {
		double min = 0.0;
		boolean first = true;
		for (int i = 1; i<objectsTable.length; i++) {
			if (first) {
				min = (double) objectsTable[i][col];
				first = false;
			} else {
				if ((double) objectsTable[i][col] < min) {
					min = (double) objectsTable[i][col];
				}
			}
		}
		return min;
	}

	private static double getMaxFromTable(Object[][] objectsTable, int col) {
		double max = 0.0;
		boolean first = true;
		for (int i = 1; i<objectsTable.length; i++) {
			if (first) {
				max = (double) objectsTable[i][col];
				first = false;
			} else {
				if ((double) objectsTable[i][col] > max) {
					max = (double) objectsTable[i][col];
				}
			}
		}
		return max;
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

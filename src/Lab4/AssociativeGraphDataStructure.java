/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import DataClass.Iris;

import java.io.FileNotFoundException;
import java.util.*;

public class AssociativeGraphDataStructure {

	public static void main(String[] args) throws FileNotFoundException {
//		DataType dType = chooseType();
		DataType dType = DataType.IRIS;
		String dataPath = "src/Resources/data" + dType.name() +".txt";

		//Create PARAM node - main node
		Node<String> paramNode = new Node<>(Node.Level.PARAM, Node.Level.PARAM.name());

		buildGraphAGDS(dataPath, paramNode);

		//TODO wstawienie wzorca
		Iris pattern = new Iris(1.1, 2.2, 3.3, 4.4, Iris.IrisType.SETOSA);
		//TODO Google Guice timer ?
		findPatternsInGraph(pattern);

		findPatternsInGraphWithFilter();

		findPatternsInTable();

		findPatternsInTableWithFilter();

	}

	private static void buildGraphAGDS(String dataPath, Node<String> paramNode) throws FileNotFoundException {
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
		for (Iris singleIris: listOfIris) {
			Node<Double> singleValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_LENGTH));
			valueLLNodes.add(singleValue);

			singleValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.LEAF_WIDTH));
			valueLWNodes.add(singleValue);

			singleValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_LENGTH));
			valuePLNodes.add(singleValue);

			singleValue = new Node<>(Node.Level.VALUE_OF_PARAM,
					singleIris.getParameterByEnum(Iris.KindOfParam.PETAL_WIDTH));
			valuePWNodes.add(singleValue);
		}

		//Set PARAM children
		List<Node> childrenParam = new ArrayList<>();
		childrenParam.add(lLength);
		childrenParam.add(lWidth);
		childrenParam.add(pLength);
		childrenParam.add(pWidth);
		childrenParam.add(classNode);
		paramNode.setChildren(childrenParam);

		//Set CLASS_OF_OBJECT parent
		List<Node> parentClass = new ArrayList<>();
		parentClass.add(paramNode);
		classNode.setParents(parentClass);

		//Set CLASS_OF_OBJECT children
		List<Node> childrenClass = new ArrayList<>();
		childrenClass.add(irisSetosa);
		childrenClass.add(irisVersicolor);
		childrenClass.add(irisVirginica);
		classNode.setChildren(childrenClass);

		//Set KIND_OF_PARAM parent
		List<Node> kindParent = new ArrayList<>();
		kindParent.add(paramNode);
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
	}

	private static void findPatternsInGraph(Iris pattern) {

	}

	private static void findPatternsInGraphWithFilter() {

	}

	private static void findPatternsInTable() {

	}

	private static void findPatternsInTableWithFilter() {

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

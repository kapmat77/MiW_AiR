/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssociativeGraphDataStructure {

	public static void main(String[] args) throws FileNotFoundException {
		DataType dType = chooseType();
		String dataPath = "src/Lab4/resources/data" + dType.name() +".txt";
		buildGraphAGDS(dataPath);
	}

	private static void buildGraphAGDS(String dataPath) throws FileNotFoundException {
		List<Iris> listOfIris = Iris.readDataFromFile(dataPath);

		//Create CLASS_OF_OBJECT node
		Node<String> classNode = new Node<>(Node.Level.CLASS_OF_OBJECT, Node.Level.CLASS_OF_OBJECT.name());

		//Create PARAM node
		Node<String> paramNode = new Node<>(Node.Level.PARAM, Node.Level.PARAM.name());

		//Create KIND_OF_PARAM nodes
		List<Node> kindOfParamList = new ArrayList<>();
		Node<String> lLength = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.LEAF_LENGTH.name());
		Node<String> lWidth = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.LEAF_WIDTH.name());
		Node<String> pLength = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.PETAL_LENGTH.name());
		Node<String> pWidth = new Node<>(Node.Level.KIND_OF_PARAM, Iris.KindOfParam.PETAL_WIDTH.name());
		kindOfParamList.add(lLength);
		kindOfParamList.add(lWidth);
		kindOfParamList.add(pLength);
		kindOfParamList.add(pWidth);

		//Create TYPE_OF_OBJECT nodes
		Node<Iris.IrisType> irisSetosa = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.SETOSA);
		Node<Iris.IrisType> irisVersicolor = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.VERSICOLOR);
		Node<Iris.IrisType> irisVirginica = new Node<>(Node.Level.TYPE_OF_OBJECT, Iris.IrisType.VIRGINICA);

		//Create INDEX nodes
		List<Node> indexNodes = new ArrayList<>();
		int i = 0;
		for (Iris singleIris: listOfIris) {
			Node<Integer> singleIndex = new Node<>(Node.Level.INDEX, i);
			indexNodes.add(singleIndex);
			i++;
		}

		//Create VALUE_OF_PARAM nodes


		ArrayList<Node> parentsParam = new ArrayList<>();
		parentsParam.add(classNode);
		paramNode.setParents(parentsParam);
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

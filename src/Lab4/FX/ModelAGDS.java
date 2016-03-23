/**
 * Created by Kapmat on 2016-03-23.
 **/

package Lab4.FX;

import DataClass.Iris;
import Interf.InputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ModelAGDS {

	private List<Iris> listOfIrises = new ArrayList<>();
	private Object[][] objectsTable;
	private DataType dataType;
	private String dataPath;


	public void buildGraphAndTable() {
		buildGraphAGDS();
		buildTable();
	}

	public void buildGraphAGDS() {
		deleteRedundantNodes(listOfIrises);

	}

	public void buildTable() {

	}

	public void findPatternsInGraphSimilarity() {

	}

	public void findPatternsInGraphFilter() {

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

	private enum DataType {
		IRIS, WINE
	}

	private enum ShowType {
		WITH_SIMILARITY, WITHOUT_SIMILARITY
	}
}

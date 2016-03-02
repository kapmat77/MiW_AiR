/**
 * Created by Kapmat on 2016-03-01.
 **/
package Lab2;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends EnumElements{

	private static int idCounter = 0;
	private int id = 0;
	private List<Element> elements;

	public Transaction() {
		idCounter++;
		id = idCounter;
		elements = new ArrayList<Element>();
	}

	public Transaction(ArrayList<Element> newElements) {
		idCounter++;
		id = idCounter;
		elements = new ArrayList<Element>(newElements);
	}

	public int getId() {
		return id;
	}

	public List<Element> getElements() {
		return elements;
	}

	public void resetCounter() {
		idCounter = 0;
	}

	public void showSingleTransaction() {
		System.out.println();
		for (int i=0; i<elements.size(); i++) {
			System.out.print(elements.get(i) + " ");
		}
	}
}

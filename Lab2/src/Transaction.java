/**
 * Created by Kapmat on 2016-03-01.
 **/

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	private static int idCounter = 0;
	private int id = 0;
	private List<String> elements;

	public Transaction() {
		idCounter++;
		id = idCounter;
		elements = new ArrayList<String>();
	}

	public Transaction(ArrayList<String> newElements) {
		idCounter++;
		id = idCounter;
		elements = new ArrayList<String>(newElements);
	}

	public int getId() {
		return id;
	}

	public List<String> getElements() {
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

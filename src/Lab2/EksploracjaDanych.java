/**
 * Created by Kapmat on 2016-03-01.
 **/
package Lab2;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.*;

public class EksploracjaDanych {

	private static final List<Transaction> transactionList = new ArrayList<>();
	private static final Map<EnumElements.Element, Integer> quantityElements = new HashMap<>();
	private static final int PERCENTAGE_TRESHOLD = 35;
	private static boolean addOne = false;

	public static void main(String[] args) {
		//Wczytanie danych z pliku
		readDataFromFile("src/Lab2/resources/dane.txt");

		//Dane częste >50%
		countFrequentData();
		showFrequentData();

		//Obliczenie wsparcia (support) s

		//Pewność (confidence) c

		//menu bazujące na switchu

	}

	private static void countFrequentData() {
		List<EnumElements.Element> keys;
		int actualQuantity;
		for (int i=0; i<transactionList.size(); i++) {
			keys = transactionList.get(i).getElements();
			for (int j=0; j<keys.size(); j++) {
				if (quantityElements.containsKey(keys.get(j))) {
					actualQuantity = quantityElements.get(keys.get(j)) + 1;
					quantityElements.put(keys.get(j), actualQuantity);
				} else {
					actualQuantity = 1;
					quantityElements.put(keys.get(j), actualQuantity);
				}
			}
		}
	}

	private static void showFrequentData() {
		double transationLength = transactionList.size();
		double value,percentageValue;
		System.out.println("\nFrequent data(" + PERCENTAGE_TRESHOLD +"% treshold):");
		Set<EnumElements.Element> typesOfElements = quantityElements.keySet();
		for (Iterator<EnumElements.Element> it = typesOfElements.iterator(); it.hasNext(); ) {
			EnumElements.Element singleElement = it.next();
			value = quantityElements.get(singleElement);
			percentageValue = (value/transationLength)*100;
			if (percentageValue>(PERCENTAGE_TRESHOLD)) {
				System.out.println(singleElement.name() + " " + Integer.valueOf((int) roundAndCutDouble(2, percentageValue)) + "%");
			}
		}
	}

	/**
	 *  Metoda zaokrągla liczbę do podanej liczby miejsc po przecinku
	 *
	 *	@param precision Ilość miejsc po przecinku
	 *  @param value Liczba do zaokrąglenia
	 *
	 *  @return Zaokrąglona liczba
	 */
	private static double roundAndCutDouble(int precision, double value) {
		value = value*(Math.pow(10, precision+1));
		int intValue = (int) value;
		char[] charValue = String.valueOf(intValue).toCharArray();
		addOne = false;
		for (int i=1; i<(precision); i++) {
			if (recursionRound(i, charValue)==-1) {
					break;
			}
		}
		if (addOne) {
			value = (Double.valueOf(1+String.valueOf(charValue)))/(Math.pow(10, 1));
		} else {
			value = (Double.valueOf(String.valueOf(charValue)))/(Math.pow(10, 1));
		}
		intValue = (int) value;
		value = intValue;
		value = value/(Math.pow(10, precision));
		return value;
	}

	private static int recursionRound(int iteration, char[] charValue) {
		String strValue = String.valueOf(charValue[charValue.length-iteration]);
		int compare = Integer.parseInt(strValue);
		if (compare>=5 && !(iteration==charValue.length && compare==9)) {
			charValue[charValue.length-iteration] = '0';
			if (charValue[charValue.length-(iteration+1)]=='9') {
				recursionRound(iteration+1, charValue);
			} else {
				charValue[charValue.length-(iteration+1)] = (char) (charValue[charValue.length-(iteration+1)] + 1);
			}
		} else if (iteration==charValue.length && compare==9) {
			charValue[charValue.length-iteration] = '0';
			addOne = true;
			return  0;
		} else if (compare<5) {
			return -1;
		}
		return 0;
	}

	private static int readDataFromFile(String dataName) {
		Path path = Paths.get(dataName);
		try {
			Scanner in = new Scanner(path);
			String[] parts;
			ArrayList<EnumElements.Element> elem = new ArrayList<>();
			Transaction singleTransaction;
			String line = in.nextLine();
			while(in.hasNextLine()) {
				line = replacePolishCharacters(in.nextLine());
				parts = line.split("\t");
				for(int i=1; i<parts.length; i++) {
					if(!(parts[i].isEmpty())) {
						elem.add(EnumElements.getEnumValue(parts[i]));
					}
				}
				singleTransaction = new Transaction(elem);
				elem.clear();
				singleTransaction.showSingleTransaction();
				transactionList.add(singleTransaction);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Plik nie zostal wczytany poprawnie");
			e.printStackTrace();
		}
		return 1;
	}

	private static String replacePolishCharacters(String singleLine) {
		char[] txt = singleLine.toCharArray();
		for (int i=0; i<txt.length; i++) {
			switch (txt[i]) {
				case 'ą':
					txt[i] = 'a';
					break;
				case 'ę':
					txt[i] = 'e';
					break;
				case 'ć':
					txt[i] = 'c';
					break;
				case 'ś':
					txt[i] = 's';
					break;
				case 'ż':
					txt[i] = 'z';
					break;
				case 'ź':
					txt[i] = 'z';
					break;
				case 'ó':
					txt[i] = 'o';
					break;
				case 'ł':
					txt[i] = 'l';
					break;
				case 'ń':
					txt[i] = 'n';
					break;
				default:
					break;
			}
		}
		return String.valueOf(txt);
	}
}

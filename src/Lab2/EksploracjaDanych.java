/**
 * Created by Kapmat on 2016-03-01.
 **/
package Lab2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class EksploracjaDanych {

	private static final List<Transaction> transactionList = new ArrayList<>();
	private static final Map<EnumElements.Element, Integer> quantityElements = new HashMap<>();
	private static final Map<EnumElements.Element, Integer> percentageMap = new HashMap<>();
	private static List<Map.Entry<EnumElements.Element, Integer>> sortedMap = new ArrayList<>();
	private static int supportTreshold = 50;
	private static int confidenceTreshold = 50;
	private static boolean addOne = false;

	public static void main(String[] args) {
		//Wczytanie danych z pliku
		System.out.println("Dane wczytywane są z pliku Resources/dane_lab2.txt");
		readDataFromFile("src/Lab2/dane_prezentacja_lab2.txt");
//		readDataFromFile("Resources/dane_lab2.txt");

		countFrequentData();
		countProbability();
		sortPercentageMap();

		if (mainMenu()==-1){
			return;
		}
	}

	private static void sortPercentageMap() {
		sortedMap = new ArrayList<>(percentageMap.entrySet());
		ElementComparator comparator = new ElementComparator();
		comparator.setSortBy(ElementComparator.Order.Key);
		Collections.sort(sortedMap, comparator);
		Collections.reverse(sortedMap);
	}

	private static int mainMenu() {
		while (true) {
			System.out.println("\n########################################");
			System.out.println("###############   MENU   ###############");
			System.out.println("########################################");
			System.out.println("1. Pokaż dane częste ");
			System.out.println("2. Oblicz wsparcie");
			System.out.println("3. Reguły asocjacyjne ");
			System.out.println("Wybierz opcję. Aby wyjść wciśnij i zatwierdź dowolny inny klawisz:");
			Scanner input = new Scanner(System.in);
			String text = input.nextLine();
			switch (text) {
				case "1":
					showMostFrequentData();
					break;
				case "2":
					showProbability();
					break;
				case "3":
					coutAndShowAssociation();
					break;
				default:
					return -1;
			}
		}
	}

	private static void coutAndShowAssociation() {
		EnumElements.Element firstProduct = EnumElements.Element.NONE;
		EnumElements.Element secondProduct = EnumElements.Element.NONE;
		Scanner input;
		String in;
		//TODO zabezpieczyc
		System.out.println("\nPodaj prog procentowy wsparcia(support):");
		input = new Scanner(System.in);
		in = input.nextLine();
		supportTreshold = Integer.valueOf(in);
		//TODO zabezpieczyc
		System.out.println("\nPodaj prog procentowy pewności(confidence):");
		input = new Scanner(System.in);
		in = input.nextLine();
		confidenceTreshold = Integer.valueOf(in);

		//TODO wybór czy wszystkie możliwe komibnacje czy chcemy wybrać konkretną parę

		System.out.println("Wprowadz produkty! (bez polskich znaków)");
		System.out.println("Produkt pierwszy: ");
		while (firstProduct.name().equalsIgnoreCase("none")) {
			input = new Scanner(System.in);
			in = input.nextLine();
			firstProduct = EnumElements.getEnumValue(replacePolishCharacters(in).toUpperCase());
			if (firstProduct.name().equalsIgnoreCase("none")) {
				System.out.println("Brak produktu o podanej nazwie. Wprowadz produkt ponownie.");
			}
		}
		System.out.println("Produkt drugi: ");
		while (secondProduct.name().equalsIgnoreCase("none")) {
			input = new Scanner(System.in);
			in = input.nextLine();
			secondProduct = EnumElements.getEnumValue(replacePolishCharacters(in).toUpperCase());
			if (firstProduct.name().equalsIgnoreCase("none")) {
				System.out.println("Brak produktu o podanej nazwie. Wprowadz produkt ponownie.");
			}
		}
		int freqAll = 0;
		int freqProduct = 0;
		int transactionWithProduct = 0;
		for (int i = 0; i<transactionList.size(); i++) {
			if (transactionList.get(i).isElement(firstProduct) || transactionList.get(i).isElement(secondProduct)) {
				freqAll+=1;
			}
			if (transactionList.get(i).isElement(firstProduct) && transactionList.get(i).isElement(secondProduct)) {
				freqProduct+=1;
			}
			if (transactionList.get(i).isElement(firstProduct)) {
				transactionWithProduct+=1;
			}
		}
		double firstParameter = (freqAll*100/transactionList.size());
		double secondParameter = (freqProduct*100/transactionWithProduct);
		if (firstParameter>=supportTreshold && secondParameter>=confidenceTreshold)
		System.out.println(firstProduct.name() + "->" +secondProduct.name() + " (" + firstParameter + "%, " + secondParameter + "%)");
	}

	private static void countProbability() {
		double transationLength = transactionList.size();
		double value,percentageValue;
		Set<EnumElements.Element> typesOfElements = quantityElements.keySet();
		for (Iterator<EnumElements.Element> it = typesOfElements.iterator(); it.hasNext(); ) {
			EnumElements.Element singleElement = it.next();
			value = quantityElements.get(singleElement);
			percentageValue = (value/transationLength)*100;
			percentageMap.put(singleElement, Integer.valueOf((int) roundAndCutDouble(2, percentageValue)));
		}
	}

	private static void showProbability() {
		System.out.println("\nWsparcie:");
/*		for (Iterator<EnumElements.Element> it = percentageMap.keySet().iterator(); it.hasNext(); ) {
			EnumElements.Element singleElement = it.next();
			System.out.println(singleElement.name() + " " + percentageMap.get(singleElement) + "%");
		}	*/
		for (int i = 0; i< sortedMap.size(); i++ ) {
			System.out.println(sortedMap.get(i).getKey().name() + " " + sortedMap.get(i).getValue() + "%");
		}
		//Sort map
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

	private static void showMostFrequentData() {
		System.out.println("\nPodaj prog procentowy:");
		Scanner input = new Scanner(System.in);
		String in = input.nextLine();
		supportTreshold = Integer.valueOf(in);
		System.out.println("\nElementy częste:");
//		for (Iterator<EnumElements.Element> it = percentageMap.keySet().iterator(); it.hasNext(); ) {
//			EnumElements.Element singleElement = it.next();
//			if (percentageMap.get(singleElement)>(supportTreshold)) {
//				System.out.println(singleElement.name() + " " + percentageMap.get(singleElement) + "%");
//			}
//		}
		for (int i = 0; i< sortedMap.size(); i++ ) {
			if (sortedMap.get(i).getValue()>(supportTreshold))
			System.out.println(sortedMap.get(i).getKey().name() + " " + sortedMap.get(i).getValue() + "%");
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
					if (parts[i].contains("mi")) {
						parts[i] = "miod";
					} else if (parts[i].contains("atki")) {
						parts[i] = "platki";
					} else if (parts[i].contains("mas")) {
						parts[i] = "maslo";
					}
					if(!(parts[i].isEmpty())) {
						elem.add(EnumElements.getEnumValue(parts[i]));
					}
				}
				singleTransaction = new Transaction(elem);
				elem.clear();
				transactionList.add(singleTransaction);
			}
			in.close();
		} catch (IOException e) {
			System.out.println("Plik nie zostal wczytany poprawnie");
			e.printStackTrace();
			System.exit(-1);
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

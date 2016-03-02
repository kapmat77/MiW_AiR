/**
 * Created by Kapmat on 2016-03-01.
 **/
package Lab2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EksploracjaDanych {

	private static final List<Transaction> transactionList = new ArrayList<>();

	public static void main(String[] args) {
		//Wczytanie danych z pliku
		readDataFromFile("src/Lab2/resources/dane.txt");

		//Dane częste >50%
		showFrequentData(50);

		//Obliczenie wsparcia (support) s

		//Pewność (confidence) c

		//menu bazujące na switchu

	}

	private static void showFrequentData(int percentTreshold) {

	}

	private static int readDataFromFile(String dataName) {
		Path path = Paths.get(dataName);
		try {
			Scanner in = new Scanner(path);
			String[] parts;
			ArrayList<EnumElements.Element> elem = new ArrayList<>();
			Transaction singleTransaction = null;
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

	public static String replacePolishCharacters(String singleLine) {
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

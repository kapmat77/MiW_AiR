/**
 * Created by Kapmat on 2016-03-01.
 **/
package Lab2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
				line = in.nextLine();
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
}

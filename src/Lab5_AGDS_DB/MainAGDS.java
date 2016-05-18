/**
 * Created by Kapmat on 2016-04-12.
 **/

package Lab5_AGDS_DB;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.*;

public class MainAGDS {

	public static List<String> inputAttributesList = new ArrayList<>();
	public static List<String> inputValuesList = new ArrayList<>();
	static DatabaseConnect dbConnect;

	public static void main(String[] args) throws SQLException {
		dbConnect = new DatabaseConnect();
		List<Node> root = dbConnect.rootList;

		while(true) {
			System.out.println("\n******************************");
			System.out.println(inputAttributesList.toString());
			System.out.println(inputValuesList.toString());
			System.out.println("1.Dodaj parametr do wyszukania");
			System.out.println("2.Szukaj");
			System.out.println("3.Exit");
			Scanner input = new Scanner(System.in);
			switch (input.nextLine()) {
				case "1":
					System.out.println("1.Imie");
					System.out.println("2.Nazwisko");
					System.out.println("3.Stypendium");
					System.out.println("4.Rok studiów");
					System.out.println("5.Kierunek");
					System.out.println("6.Miasto");
					System.out.println("7.Ulica");
					System.out.println("8.Numer domu");
					Scanner param = new Scanner(System.in);
					Scanner str;
					switch (param.nextLine()) {
					case "1":
						inputAttributesList.add("Imie");
						System.out.println("Podaj imie:");
						str = new Scanner(System.in);
						inputValuesList.add(str.nextLine());
						break;
					case "2":
							inputAttributesList.add("Nazwisko");
							System.out.println("Podaj nazwisko:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "3":
							inputAttributesList.add("Stypendium");
							System.out.println("Podaj stypendium:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "4":
							inputAttributesList.add("Rok studiów");
							System.out.println("Podaj rok studiów:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "5":
							inputAttributesList.add("Nazwa kierunku");
							System.out.println("Podaj kierunek:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "6":
							inputAttributesList.add("Miasto");
							System.out.println("Podaj miasto:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "7":
							inputAttributesList.add("Ulica");
							System.out.println("Podaj ulice:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
						case "8":
							inputAttributesList.add("Numer");
							System.out.println("Podaj numer domu:");
							str = new Scanner(System.in);
							inputValuesList.add(str.nextLine());
							break;
					}
					break;
				case "2":
					if (inputAttributesList.size()>0) {
						findStudentsByParamGraph(root);
						inputValuesList.clear();
						inputAttributesList.clear();
					} else {
						System.out.println("Wprowadz parametry !!");
					}
					break;
				case "3":
					dbConnect.closeConnections();
					System.exit(0);
		}


//		addStudentToGraph(root);
//		deleteStudentFromGraph(root);
//		addStudentToDatabase();
//		deleteStudentFromDatabase();
		}

	}

	private static void addStudentToGraph(List<Node> root) {
		String name = "Mateusz";
		String surname = "Kaproń";
		String scholarship = "0";
		String year = "4";
		String town = "Kraków";
		String street = "Tokarskiego";
		String number = "8";
		String fieldOfStudy = "Automatyka i Robotyka";

		Map<String, String> newStudentMap = new HashMap();
		newStudentMap.put("Imie", name);
		newStudentMap.put("Nazwisko", surname);
		newStudentMap.put("Stypendium", scholarship);
		newStudentMap.put("Nazwa kierunku", fieldOfStudy);
		newStudentMap.put("Rok studiów", year);
		newStudentMap.put("Miasto", town);
		newStudentMap.put("Ulica", street);
		newStudentMap.put("Numer", number);

		Node studentNode = new Node(Node.Level.INDEX, "Studenci10");
		Node addressNode = new Node(Node.Level.INDEX, "Adres6");
		Node kierunekNode = new Node(Node.Level.INDEX, "Kierunek5");

		List<Node> studentParents = new ArrayList<>();
		studentParents.add(addressNode);
		studentParents.add(kierunekNode);

		List<Node> addressChild = new ArrayList<>();
		addressChild.add(studentNode);

		List<Node> kierunekChild = new ArrayList<>();
		kierunekChild.add(studentNode);

		/**
		 Add missing value nodes
		 */
		for (Node attribute: root) {
			List<Node> attributeChildren = attribute.getChildren();
			boolean valueExist = false;
			for (Node attNode: attributeChildren) {
				if (newStudentMap.get(attribute.getValue()).equals(attNode.getValue())){
					valueExist = true;
					if (attribute.getValue().equals("Miasto") || attribute.getValue().equals("Ulica") || attribute.getValue().equals("Numer")) {
						List<Node> parenList = new ArrayList<>();
						parenList.add(attNode);
						addressNode.setOrExtendParents(parenList);
					} else if (attribute.getValue().equals("Nazwa kierunku")) {
						List<Node> parenList = new ArrayList<>();
						parenList.add(attNode);
						kierunekNode.setOrExtendParents(parenList);
					} else {
						List<Node> parenList = new ArrayList<>();
						parenList.add(attNode);
						studentNode.setOrExtendParents(parenList);
						List<Node> childrenList = new ArrayList<>();
						childrenList.add(studentNode);
						attNode.setOrExtendChildren(childrenList);
					}
				}
			}
			if (!valueExist) {
				Node newNode = new Node(Node.Level.VALUE, newStudentMap.get(attribute.getValue()));
				List<Node> newNodeList = new ArrayList<>();
				newNodeList.add(newNode);
				attribute.setOrExtendChildren(newNodeList);
				List<Node> newNodeParent = new ArrayList<>();
				newNodeParent.add(attribute);
				newNode.setOrExtendParents(newNodeParent);
				if (attribute.getValue().equals("Miasto") || attribute.getValue().equals("Ulica") || attribute.getValue().equals("Numer")) {
					List<Node> parenList = new ArrayList<>();
					parenList.add(newNode);
					addressNode.setOrExtendParents(parenList);
				} else if (attribute.getValue().equals("Nazwa kierunku")) {
					List<Node> parenList = new ArrayList<>();
					parenList.add(newNode);
					kierunekNode.setOrExtendParents(parenList);
				} else {
					List<Node> parenList = new ArrayList<>();
					parenList.add(newNode);
					studentNode.setOrExtendParents(parenList);
					List<Node> childrenList = new ArrayList<>();
					childrenList.add(studentNode);
					newNode.setOrExtendChildren(childrenList);
				}
			}
		}
		/**
		 Check if new address-index-node exist
		 */
		List<Node> addressList = new ArrayList<>();
		addressList.add(addressNode);
		boolean exist = false;
		for (Node node: DatabaseConnect.INDEXES) {
			List<Node> firstParents = node.getParents();
			List<Node> secondParents = addressNode.getParents();
			if (node.getValue().toString().contains("Adres") && firstParents.equals(secondParents)) {
				node.setOrExtendChildren(addressChild);
				List<Node> studentPar = new ArrayList<>();
				studentPar.add(node);
				studentNode.setOrExtendParents(studentPar);
				exist = true;
				break;
			}
		}
		if (!exist) {
			DatabaseConnect.INDEXES.add(addressNode);
			List<Node> address = new ArrayList<>();
			address.add(addressNode);
			studentNode.setOrExtendParents(address);
			addressNode.setOrExtendChildren(addressChild);
			List<Node> parents = addressNode.getParents();
			for (Node parent: parents) {
				parent.setOrExtendChildren(address);
			}
		}

		/**
		 Check if new kierunek-index-node exist
		 */
		exist = false;
		for (Node node: DatabaseConnect.INDEXES) {
			List<Node> firstParents = node.getParents();
			List<Node> secondParents = kierunekNode.getParents();
			if (node.getValue().toString().contains("Kierunek") && firstParents.equals(secondParents)) {
				node.setOrExtendChildren(kierunekChild);
				List<Node> studentPar = new ArrayList<>();
				studentPar.add(node);
				studentNode.setOrExtendParents(studentPar);
				exist = true;
				break;
			}
		}
		if (!exist) {
			DatabaseConnect.INDEXES.add(kierunekNode);
			List<Node> kierunek = new ArrayList<>();
			kierunek.add(addressNode);
			studentNode.setOrExtendParents(kierunek);
			kierunekNode.setOrExtendChildren(kierunekChild);
			List<Node> parents = kierunekNode.getParents();
			for (Node parent: parents) {
				parent.setOrExtendChildren(kierunek);
			}
		}
	}

	private static void deleteStudentFromGraph(List<Node> root) {
//		String index = "Studenci10";

	}

	private static void findStudentsByParamGraph(List<Node> root) throws SQLException {

//		List<String> inputAttributesList = new ArrayList<>();
//		List<String> inputValuesList = new ArrayList<>();
		List<Node> studentList = new ArrayList<>();


//
//		inputAttributesList.add("Rok studiów");
//		inputValuesList.add("4");
//		inputAttributesList.add("Nazwa kierunku");
//		inputValuesList.add("Automatyka i Robotyka");

		long timeStart = System.nanoTime();

		for (Node attribute: root) {
			if (attribute.getValue().equals(inputAttributesList.get(0))) {
				List<Node> attributeChildren = attribute.getChildren();
				for (Node secondAttribute: attributeChildren) {
					if (secondAttribute.getValue().equals(inputValuesList.get(0))) {
						List<Node> objectIdList = secondAttribute.getChildren();

						if (!objectIdList.get(0).getValue().toString().contains(DatabaseConnect.MAIN_TABLE)) {
							for (Node object: objectIdList) {
								studentList.addAll(object.getChildren());
							}
							break;
						}
						studentList = objectIdList;
						break;
					}
				}
				break;
			}
		}

		System.out.println("*****KRYTERIA WYSZUKIWANIA*****");
		for (int i=0; i<inputAttributesList.size(); i++) {
			System.out.println(inputAttributesList.get(i) + ": " + inputValuesList.get(i));
		}
		System.out.println();

		List<Node> bestOfTheBestStudent = new ArrayList<>();
		List<Node> temporaryList = new ArrayList<>();

		if (inputAttributesList.size()>1) {
			for (int i=1; i<inputValuesList.size(); i++) {
				List<Node> bestStudent = new ArrayList<>();
				for (Node student: studentList) {
					List<Node> studentParents = student.getParents();
					for (Node parent : studentParents) {
						if (parent.getValue().equals(inputValuesList.get(i))) {
							if (((Node)parent.getParents().get(0)).getValue().equals(inputAttributesList.get(i))) {
								if (!bestStudent.contains(student)) {
									bestStudent.add(student);
								}
							}
						} else if (parent.getParents().size() == 1){
							Node valueNode = ((Node)parent.getParents().get(0));
							if (valueNode.getValue().equals(inputValuesList.get(i))){
								if (((Node)valueNode.getParents().get(0)).getValue().equals(inputAttributesList.get(i))) {
									if (!bestStudent.contains(student)) {
										bestStudent.add(student);
									}
								}
							}
						}
					}
				}
				if (temporaryList.isEmpty()) {
					temporaryList.addAll(bestStudent);
					bestOfTheBestStudent.addAll(bestStudent);
				} else {
					bestOfTheBestStudent.clear();
					for (Node newNode: bestStudent) {
						for (Node oldNode: temporaryList) {
							if (newNode.equals(oldNode)) {
								bestOfTheBestStudent.add(newNode);
							}
						}
					}
					temporaryList.clear();
					temporaryList.addAll(bestOfTheBestStudent);
				}
			}
		} else {
			for (Node student: studentList) {
				System.out.println(student.getValue() + ". " + ((Node)student.getParents().get(0)).getValue() + " " +
						((Node)student.getParents().get(1)).getValue() +" Stypendium:" + ((Node)student.getParents().get(2)).getValue()
						+" Rok:" + ((Node)student.getParents().get(3)).getValue());
			}
		}

		for (Node student: bestOfTheBestStudent) {
			System.out.println(student.getValue() + ". " + ((Node)student.getParents().get(0)).getValue() + " " +
					((Node)student.getParents().get(1)).getValue()+" Stypendium:" + ((Node)student.getParents().get(2)).getValue()
					+" Rok:" + ((Node)student.getParents().get(3)).getValue());
		}
		if (bestOfTheBestStudent.isEmpty() && inputAttributesList.size()>1) {
			System.out.println("Brak w bazie studenta o podanych parametrach");
		}

		long grapgT = (System.nanoTime()-timeStart);
		System.out.println("Czas szukania w grafie: " + grapgT + "ns");

		if (inputAttributesList.size()==1) {
			timeStart = System.nanoTime();
			dbConnect.findStudentInDB(inputAttributesList, inputValuesList);

			long dbT = (System.nanoTime() - timeStart);
			System.out.println("Czas szukania w bazie danych: " + dbT+ "ns");
			System.out.println("Różnica: " + (dbT - grapgT) + "ns");
		}
	}

	private static void addStudentToDatabase() {
	}

	private static void deleteStudentFromDatabase() {
	}
}

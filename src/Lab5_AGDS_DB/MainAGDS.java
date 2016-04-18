/**
 * Created by Kapmat on 2016-04-12.
 **/

package Lab5_AGDS_DB;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainAGDS {
	public static void main(String[] args) {
		DatabaseConnect dbConnect = new DatabaseConnect();
		List<Node> root = dbConnect.rootList;

		findStudentsByParam(root);
		addStudentToGraph(root);
		addStudentToDataBase();
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

//		studentNode.setOrExtendParents(studentParents);
//		addressNode.setOrExtendChildren(addressChild);
//		kierunekNode.setOrExtendChildren(kierunekChild);

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
//						List<Node> childrenList = new ArrayList<>();
//						childrenList.add(addressNode);
//						attNode.setOrExtendChildren(childrenList);
					} else if (attribute.getValue().equals("Nazwa kierunku")) {
						List<Node> parenList = new ArrayList<>();
						parenList.add(attNode);
						kierunekNode.setOrExtendParents(parenList);
//						List<Node> childrenList = new ArrayList<>();
//						childrenList.add(kierunekNode);
//						List<Node> attChildren = attNode.getChildren();
//						attNode.setOrExtendChildren(childrenList);
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
//					List<Node> childrenList = new ArrayList<>();
//					childrenList.add(addressNode);
//					newNode.setOrExtendChildren(childrenList);
				} else if (attribute.getValue().equals("Nazwa kierunku")) {
					List<Node> parenList = new ArrayList<>();
					parenList.add(newNode);
					kierunekNode.setOrExtendParents(parenList);
//					List<Node> childrenList = new ArrayList<>();
//					childrenList.add(kierunekNode);
//					newNode.setOrExtendChildren(childrenList);
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

	private static void addStudentToDataBase() {

	}

	private static void findStudentsByParam(List<Node> root) {

		List<String> inputAttributesList = new ArrayList<>();
		List<String> inputValuesList = new ArrayList<>();
		List<Node> studentList = new ArrayList<>();

		inputAttributesList.add("Ulica");
		inputValuesList.add("Piastowska");
		inputAttributesList.add("Nazwa kierunku");
		inputValuesList.add("Automatyka i Robotyka");

		for (Node attribute: root) {
			if (attribute.getValue().equals(inputAttributesList.get(0))) {
				List<Node> attributeChildren = attribute.getChildren();
				for (Node secondAttribute: attributeChildren) {
					if (secondAttribute.getValue().equals(inputValuesList.get(0))) {
						List<Node> objectIdList = secondAttribute.getChildren();

						//TODO tylko dla studenckiej tablicy
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
						((Node)student.getParents().get(1)).getValue());
			}
		}

		for (Node student: bestOfTheBestStudent) {
			System.out.println(student.getValue() + ". " + ((Node)student.getParents().get(0)).getValue() + " " +
					((Node)student.getParents().get(1)).getValue());
		}
		if (bestOfTheBestStudent.isEmpty() && inputAttributesList.size()>1) {
			System.out.println("Brak w bazie studenta o podanych parametrach");
		}
	}
}
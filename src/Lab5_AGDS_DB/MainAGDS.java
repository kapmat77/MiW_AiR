/**
 * Created by Kapmat on 2016-04-12.
 **/

package Lab5_AGDS_DB;

import java.util.ArrayList;
import java.util.List;

public class MainAGDS {
	public static void main(String[] args) {
		DatabaseConnect dbConnect = new DatabaseConnect();
		List<Node> root = dbConnect.rootList;

		findStudentsByParam(root);
	}

	private static void findStudentsByParam(List<Node> root) {

		List<String> inputAttributesList = new ArrayList<>();
		List<String> inputValuesList = new ArrayList<>();
		List<Node> studentList = new ArrayList<>();

/*		inputAttributesList.add("Rok studiów");
		inputValuesList.add("2");*/
/*		inputAttributesList.add("Nazwa kierunku");
		inputValuesList.add("Automatyka i Robotyka");*/
		inputAttributesList.add("Stypendium");
		inputValuesList.add("800");

		for (Node attribute: root) {
			if (attribute.getValue().equals(inputAttributesList.get(0))) {
				List<Node> attributeChildren = attribute.getChildren();
				for (Node secondAttribute: attributeChildren) {
					if (secondAttribute.getValue().equals(inputValuesList.get(0))) {
						List<Node> objectIdList = secondAttribute.getChildren();

						//TODO tylko dla studenckiej tablicy
						if (!objectIdList.get(0).getValue().toString().contains(DatabaseConnect.MAIN_TABLE)) {
							for (Node object: objectIdList) {
								studentList = object.getChildren();
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

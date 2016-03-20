/**
 * Created by Kapmat on 2016-03-19.
 **/

package HelpfulClasses;

import Lab4.Node;

import java.util.ArrayList;
import java.util.List;

public class NodesBox {

	private static final Node<String> PARAM_NODE = new Node<>(Node.Level.PARAM, Node.Level.PARAM.name());

	public static Node<String> getParamNode() {
		return PARAM_NODE;
	}

	public static List<Node> getIndexNodes() {
		List<Node> allIndexNodes = new ArrayList<>();
		List<Node> types = getClassNode().getChildren();
		for (Node singleTypes: types) {
			List<Node> childrenTypes = singleTypes.getChildren();
			for (Node singleChild: childrenTypes) {
				allIndexNodes.add(singleChild);
			}
		}
		return allIndexNodes;
	}

	public static Node getClassNode() {
		List<Node> childrenParam = PARAM_NODE.getChildren();
		Node classNode = new Node();
		for (Node singleChild: childrenParam) {
			if (singleChild.getLevel().equals(Node.Level.CLASS_OF_OBJECT)) {
				classNode = singleChild;
			}
		}
		return classNode;
	}

	public static List<Node> getKindOfParamNodes() {
		List<Node> childrenParam = PARAM_NODE.getChildren();
		List<Node> kindsOfParameter = new ArrayList<>();
		Node classNode = new Node();
		for (Node singleChild: childrenParam) {
			if (!singleChild.getLevel().equals(Node.Level.CLASS_OF_OBJECT)) {
				kindsOfParameter.add(singleChild);
			}
		}
		return kindsOfParameter;
	}

	public static List<Node> getTypeNodes() {
		return getClassNode().getChildren();
	}
}

/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import java.util.ArrayList;

public class Node <T> {

	private Level level;
	private T value;

	private ArrayList<Node> children;
	private ArrayList<Node> parents;

	public enum Level {
		PARAM, KIND_OF_PARAM, VALUE_OF_PARAM, INDEX, TYPE_OF_OBJECT, CLASS_OF_OBJECT, NONE
	}

	public Node() {
		level = Level.NONE;
		children = new ArrayList<>();
		parents = new ArrayList<>();
	}

	public Node(Level level, ArrayList<Node> children, ArrayList<Node> parents) {
		this.level = level;
		this.children = children;
		this.parents = parents;
	}

	public Node(Level level,T value) {
		this.level = level;
		this.value = value;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public void setParents(ArrayList<Node> parents) {
		this.parents = parents;
	}

	public ArrayList<Node> getChildren() {
		return this.children;
	}

	public ArrayList<Node> getParents() {
		return this.parents;
	}

}



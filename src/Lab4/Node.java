/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import java.util.ArrayList;

public class Node <T> {

	/*    Level
	 * 1 - param - String
	 * 2 - nameOfParam - String
	 * 3 - valueOfParam - Double
	 * 4 - index - Integer
	 * 5 - nameOfType - String
	 * 6 - valueOfType - String
	 */
	private int level;
	private T value;

	private ArrayList<Node> children;
	private ArrayList<Node> parents;

	public Node() {
		level = 0;
		children = new ArrayList<>();
		parents = new ArrayList<>();
	}

	public Node(int level, ArrayList<Node> children, ArrayList<Node> parents) {
		this.level = level;
		this.children = children;
		this.parents = parents;
	}

	public Node(int level,T value) {
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



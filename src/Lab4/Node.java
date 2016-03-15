/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab4;

import java.util.ArrayList;
import java.util.List;

public class Node <T> implements Comparable<T>{

	private Level level;
	private T value;

	private List<Node> children;
	private List<Node> parents;

	public enum Level {
		PARAM, KIND_OF_PARAM, VALUE_OF_PARAM, INDEX, TYPE_OF_OBJECT, CLASS_OF_OBJECT, NONE
	}

	public Node() {
		level = Level.NONE;
		children = new ArrayList<>();
		parents = new ArrayList<>();
	}

	public Node(Level level, List<Node> children, List<Node> parents) {
		this.level = level;
		this.children = children;
		this.parents = parents;
	}

	public Node(Level level,T value) {
		this.level = level;
		this.value = value;
	}

	public T getValue(){
		return value;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public void setParents(List<Node> parents) {
		this.parents = parents;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	public List<Node> getParents() {
		return this.parents;
	}

	@Override
	public int compareTo(T secondValue) {
		Double second;
		Double first;
		if (secondValue instanceof Node) {
			second = (Double) this.getValue();
			first = (Double) ((Node) secondValue).getValue();
			if (second < first) {
				return -1;
			} else if (second > first) {
				return 1;
			} else {
				return 0;
			}
		}
		return 0;
	}
}



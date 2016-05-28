/**
 * Created by Kapmat on 2016-03-15.
 **/

package Lab5_AGDS_DB;

import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Comparable<T>{

	private Level level;
	private T value;

	private List<Node> children = null;
	private List<Node> parents = null;

	//Only for KIND_OF_PARAM nodes
	private double minValue = 0.0;
	private double maxValue = 0.0;
	private double range = 0.0;

	public enum Level {
		COLUMN, VALUE, INDEX, NONE
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

	public Node(Level level, T value) {
		this.level = level;
		this.value = value;
	}

	public T getValue(){
		return value;
	}

	public Level getLevel(){
		return this.level;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public void setParents(List<Node> parents) {
		this.parents = parents;
	}

	public void setOrExtendChildren(List<Node> chil) {
		if (this.children == null) {
			setChildren(chil);
		} else {
			int sizeChil = chil.size();
			int sizeChildren = children.size();
			boolean extend = true;
			for (int i=0; i<sizeChil; i++) {
				extend = true;
				for (int j=0; j<sizeChildren; j++) {
					if (children.get(j).getValue().equals(chil.get(i).getValue())) {
						extend = false;
					}
				}
				if (extend) {
					this.children.add(chil.get(i));
				}
			}
		}
	}

	public void setOrExtendParents(List<Node> par) {
		if (this.parents == null) {
			setParents(par);
		} else {
			int sizePar = par.size();
			int sizeParents = parents.size();
			boolean extend = true;
			for (int i=0; i<sizePar; i++) {
				extend = true;
				for (int j=0; j<sizeParents; j++) {
					if (parents.get(j).getValue().equals(par.get(i).getValue())) {
						extend = false;
					}
				}
				if (extend) {
					this.parents.add(par.get(i));
				}
			}
		}
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
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



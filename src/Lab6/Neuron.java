/**
 * Created by Kapmat on 2016-05-16.
 **/

package Lab6;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

	private double coeff = 0;
	private List<Neuron> neighbours = new ArrayList<>();

	public Neuron() {
		super();
	}

	public Neuron(double c) {
		super();
		this.coeff = c;
	}

	public double getCoeff() {
		return coeff;
	}

	public void setCoeff(double c) {
		this.coeff = c;
	}

	public List<Neuron> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(List<Neuron> n) {
		this.neighbours = n;
	}

	public void addNeighbour(Neuron n) {
		neighbours.add(n);
	}

	public void removeNeighbour(Neuron n) {
		neighbours.remove(n);
	}
}

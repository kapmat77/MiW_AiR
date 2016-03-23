package Lab4.FX;

import DataClass.Iris;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kapmat on 2016-03-22.
 **/
public abstract class Variables {

	private static double leafL = 5;
	private static double leafW = 5;
	private static double petalL = 5;
	private static double petalW = 5;
	private static double similarityThreshold = 0.5;
	private static Double lowestLL = 0.0;

	private static Double highestLL = 5.0;
	private static Double lowestLW = 0.0;
	private static Double highestLW = 5.0;
	private static Double lowestPL = 0.0;
	private static Double highestPL = 5.0;
	private static Double lowestPW = 0.0;
	private static Double highestPW = 5.0;
	private static List<Iris> showIrises = new ArrayList<>();

	private static int type = 0;

	public static List<Iris> getShowIrises() {
		return showIrises;
	}

	public static void setShowIrises(List<Iris> showIrises) {
		Variables.showIrises = showIrises;
	}


	public static double getLeafL() {
		return leafL;
	}

	public static void setLeafL(double leafL) {
		Variables.leafL = leafL;
	}

	public static double getLeafW() {
		return leafW;
	}

	public static void setLeafW(double leafW) {
		Variables.leafW = leafW;
	}

	public static double getPetalL() {
		return petalL;
	}

	public static void setPetalL(double petalL) {
		Variables.petalL = petalL;
	}

	public static double getPetalW() {
		return petalW;
	}

	public static void setPetalW(double petalW) {
		Variables.petalW = petalW;
	}

	public static double getSimilarityThreshold() {
		return similarityThreshold;
	}

	public static void setSimilarityThreshold(double similarityThreshold) {
		Variables.similarityThreshold = similarityThreshold/100;
	}

	public static Double getLowestLL() {
		return lowestLL;
	}

	public static void setLowestLL(Double lowestLL) {
		Variables.lowestLL = lowestLL;
	}

	public static Double getHighestLL() {
		return highestLL;
	}

	public static void setHighestLL(Double highestLL) {
		Variables.highestLL = highestLL;
	}

	public static Double getLowestLW() {
		return lowestLW;
	}

	public static void setLowestLW(Double lowestLW) {
		Variables.lowestLW = lowestLW;
	}

	public static Double getHighestLW() {
		return highestLW;
	}

	public static void setHighestLW(Double highestLW) {
		Variables.highestLW = highestLW;
	}

	public static Double getLowestPL() {
		return lowestPL;
	}

	public static void setLowestPL(Double lowestPL) {
		Variables.lowestPL = lowestPL;
	}

	public static Double getHighestPL() {
		return highestPL;
	}

	public static void setHighestPL(Double highestPL) {
		Variables.highestPL = highestPL;
	}

	public static Double getLowestPW() {
		return lowestPW;
	}

	public static void setLowestPW(Double lowestPW) {
		Variables.lowestPW = lowestPW;
	}

	public static Double getHighestPW() {
		return highestPW;
	}

	public static void setHighestPW(Double highestPW) {
		Variables.highestPW = highestPW;
	}

	public static int getType() {
		return type;
	}

	public static void setType(int type) {
		Variables.type = type;
	}
}

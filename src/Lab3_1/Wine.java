/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

public class Wine {

	private int classType = 0;
	private double alcohol = 0;
	private double malicAcid = 0;
	private double ash = 0;
	private double alcalinityOfAshe = 0;
	private double magnesium = 0;
	private double totalPhenols = 0;
	private double nonflavanoidPhenols = 0;
	private double proanthocyanins = 0;
	private double colorIntensity = 0;
	private double hue = 0;
	private double od280od315OfDilutedWines = 0;
	private int proline = 0;

	public Wine() {
		this.classType = 0;
		this.alcohol = 0;
		this.malicAcid = 0;
		this.ash = 0;
		this.alcalinityOfAshe = 0;
		this.magnesium = 0;
		this.totalPhenols = 0;
		this.nonflavanoidPhenols = 0;
		this.proanthocyanins = 0;
		this.colorIntensity = 0;
		this.hue = 0;
		this.od280od315OfDilutedWines = 0;
		this.proline = 0;
	}

	public Wine(int classType, double alcohol, double malicAcid, double ash, double alcalinityOfAshe,
	            double magnesium, double totalPhenols, double nonflavanoidPhenols, double proanthocyanins,
	            double colorIntensity, double hue, double od280od315OfDilutedWines, int proline) {
		this.classType = classType;
		this.alcohol = alcohol;
		this.malicAcid = malicAcid;
		this.ash = ash;
		this.alcalinityOfAshe = alcalinityOfAshe;
		this.magnesium = magnesium;
		this.totalPhenols = totalPhenols;
		this.nonflavanoidPhenols = nonflavanoidPhenols;
		this.proanthocyanins = proanthocyanins;
		this.colorIntensity = colorIntensity;
		this.hue = hue;
		this.od280od315OfDilutedWines = od280od315OfDilutedWines;
		this.proline = proline;
	}

	public int getWineClassType() {
		return this.classType;
	}

	@Override
	public String toString() {
		return "Wine {" +
				"classType=" + classType +
				", alcohol=" + alcohol +
				", malicAcid=" + malicAcid +
				", ash=" + ash +
				", alcalinityOfAshe=" + alcalinityOfAshe +
				", magnesium=" + magnesium +
				", totalPhenols=" + totalPhenols +
				", nonflavanoidPhenols=" + nonflavanoidPhenols +
				", proanthocyanins=" + proanthocyanins +
				", colorIntensity=" + colorIntensity +
				", hue=" + hue +
				", od280od315OfDilutedWines=" + od280od315OfDilutedWines +
				", proline=" + proline +
				"}";
	}
}

/**
 * Created by Kapmat on 2016-03-08.
 **/

package Lab3_1;

public class Wine {

	private int classType;
	private double alcohol;
	private double malicAcid;
	private double ash;
	private double alcalinityOfAshe;
	private int magnesium;
	private double totalPhenols;
	private double flavanoids;
	private double nonflavanoidPhenols;
	private double proanthocyanins;
	private double colorIntensity;
	private double hue;
	private double od280od315OfDilutedWines;
	private int proline;

	public Wine() {
		this.classType = 0;
		this.alcohol = 0;
		this.malicAcid = 0;
		this.ash = 0;
		this.alcalinityOfAshe = 0;
		this.magnesium = 0;
		this.totalPhenols = 0;
		this.flavanoids = 0;
		this.nonflavanoidPhenols = 0;
		this.proanthocyanins = 0;
		this.colorIntensity = 0;
		this.hue = 0;
		this.od280od315OfDilutedWines = 0;
		this.proline = 0;
	}

	public Wine(int classType, double alcohol, double malicAcid, double ash, double alcalinityOfAshe,
	            int magnesium, double totalPhenols, double flavanoids, double nonflavanoidPhenols,
	            double proanthocyanins, double colorIntensity, double hue, double od280od315OfDilutedWines,
	            int proline) {
		this.classType = classType;
		this.alcohol = alcohol;
		this.malicAcid = malicAcid;
		this.ash = ash;
		this.alcalinityOfAshe = alcalinityOfAshe;
		this.magnesium = magnesium;
		this.totalPhenols = totalPhenols;
		this.flavanoids = flavanoids;
		this.nonflavanoidPhenols = nonflavanoidPhenols;
		this.proanthocyanins = proanthocyanins;
		this.colorIntensity = colorIntensity;
		this.hue = hue;
		this.od280od315OfDilutedWines = od280od315OfDilutedWines;
		this.proline = proline;
	}

	public Wine(String [] parameters) {
		this.classType = Integer.valueOf(parameters[0]);
		this.alcohol = Double.valueOf(parameters[1]);
		this.malicAcid = Double.valueOf(parameters[2]);
		this.ash = Double.valueOf(parameters[3]);
		this.alcalinityOfAshe = Double.valueOf(parameters[4]);
		this.magnesium = Integer.valueOf(parameters[5]);
		this.totalPhenols = Double.valueOf(parameters[6]);
		this.flavanoids = Double.valueOf(parameters[7]);
		this.nonflavanoidPhenols = Double.valueOf(parameters[8]);
		this.proanthocyanins = Double.valueOf(parameters[9]);
		this.colorIntensity = Double.valueOf(parameters[10]);
		this.hue = Double.valueOf(parameters[11]);
		this.od280od315OfDilutedWines = Double.valueOf(parameters[12]);
		this.proline = Integer.valueOf(parameters[13]);
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
				", flavanoids=" + flavanoids +
				", nonflavanoidPhenols=" + nonflavanoidPhenols +
				", proanthocyanins=" + proanthocyanins +
				", proanthocyanins=" + proanthocyanins +
				", colorIntensity=" + colorIntensity +
				", hue=" + hue +
				", od280od315OfDilutedWines=" + od280od315OfDilutedWines +
				", proline=" + proline +
				"}";
	}
}

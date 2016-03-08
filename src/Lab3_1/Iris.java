package Lab3_1;

public class Iris {
	
	private double leafLength = 0;
	private double leafWidth = 0;
	private double petalLength = 0;
	private double petalWidth = 0;
	private IrisType type = IrisType.NONE;
	
	private enum IrisType {
		SETOSA, VERSICOLOR, VIRGINICA, NONE
	}
	
	public Iris() {
		leafLength = 0;
		leafWidth = 0;
		petalLength = 0;
		petalWidth = 0;
		type = IrisType.NONE;
	}
	
	public Iris(double ll, double lw, double pl, double pw, IrisType tp) {
		leafLength = ll;
		leafWidth = lw;
		petalLength = pl;
		petalWidth = pw;
		type = tp;
	}
	
	public void showIris() {
		System.out.println("LeafLength: " + leafLength);
		System.out.println("LeafWidth: " + leafWidth);
		System.out.println("PetalLength: " + petalLength);
		System.out.println("PetalWidth: " + petalWidth);
		System.out.println("Type: " + type.name());
	}
	
	public IrisType getIrisType() {
		return type;
	}
	
	public static IrisType convertStringToType(String strType) {
		switch(strType) {
			case "Iris-setosa":
				return IrisType.SETOSA;
			case "Iris-versicolor":
				return IrisType.VERSICOLOR;
			case "Iris-virginica":
				return IrisType.VIRGINICA;
			default:
				return IrisType.NONE;
		}
	}
}

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
		this.leafLength = 0;
		this.leafWidth = 0;
		this.petalLength = 0;
		this.petalWidth = 0;
		this.type = IrisType.NONE;
	}
	
	public Iris(double leafLength, double leafWidth, double petalLength, double petalWidth, IrisType type) {
		this.leafLength = leafLength;
		this.leafWidth = leafWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.type = type;
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

	@Override
	public String toString() {
		return "Iris {" +
				"leafLength=" + leafLength +
				", leafWidth=" + leafWidth +
				", petalLength=" + petalLength +
				", petalWidth=" + petalWidth +
				", type=" + type +
				"}";
	}
}

/**
 * Created by Kapmat on 2016-03-10.
 **/

package Interf;

public interface  InputData<T> {

	double getParameterById(int id);

	int numberOfParameters();

	void setParamFromStringTab(String[] parameters);

	String getObjectType();

	void setValidation(boolean b);

	boolean getValidation();

	boolean compare(T obj);

	/**
	 * Enum for IRIS, WINE
	 **/
	enum KindOfParam{
		LEAF_LENGTH,
		LEAF_WIDTH,
		PETAL_LENGTH,
		PETAL_WIDTH,
		ALCOHOL,
		MALIC_ACID,
		ASH,
		ALCALINITY_OF_ASHE,
		MAGNESIUM,
		TOTAL_PHENOLS,
		FLAVANOIDS,
		NONFLAVANOID_PHENOLS,
		PROANTHOCYANINS,
		COLOR_INTENSITY,
		HUE,
		OD280OD315_OF_DILUTED_WINES,
		PROFLINE
		//TODO dodac parametry WINE. Zmienić nazwę na atrybut
	}
}

/**
 * Created by Kapmat on 2016-03-10.
 **/

package Interf;

import DataClass.Iris;

import java.util.Date;

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
	}
}

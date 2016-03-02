/**
 * Created by Kapmat on 2016-03-02.
 **/

package Lab2;

public class EnumElements {

	public enum Element {
		KAWA, MLEKO, CUKIER, ORZESZKI, JAJKA, CHLEB, MASLO, SER, PLATKI, MIOD, NONE
	}

	public static Element getEnumValue(String singleElement) {
		if (singleElement.equalsIgnoreCase(String.valueOf(Element.KAWA))) {
			return Element.KAWA;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.MLEKO))) {
			return Element.MLEKO;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.CUKIER))) {
			return Element.CUKIER;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.ORZESZKI))) {
			return Element.ORZESZKI;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.JAJKA))) {
			return Element.JAJKA;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.CHLEB))) {
			return Element.CHLEB;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.MASLO))) {
			return Element.MASLO;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.SER))) {
			return Element.SER;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.PLATKI))) {
			return Element.PLATKI;
		} else if (singleElement.equalsIgnoreCase(String.valueOf(Element.MIOD))) {
			return Element.MIOD;
		} else {
			return Element.NONE;
		}
	}
}

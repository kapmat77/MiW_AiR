/**
 * Created by Kapmat on 2016-03-06.
 **/

package Lab2;

import java.util.Comparator;
import java.util.Map;

public class ElementComparator implements Comparator<Map.Entry<EnumElements.Element, Integer>> {
	public enum Order {Value, Key}

	private Order sortBy = Order.Key;

	@Override
	public int compare(Map.Entry<EnumElements.Element, Integer> firstElement, Map.Entry<EnumElements.Element, Integer> secondElement) {
		switch (sortBy) {
			case Key: return firstElement.getValue().compareTo(secondElement.getValue());
			case Value: return firstElement.getKey().compareTo(secondElement.getKey());
		}
		return 0;
	}

	public void setSortBy(Order sBy) {
		this.sortBy = sBy;
	}
}

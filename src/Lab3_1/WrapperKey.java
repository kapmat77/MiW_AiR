/**
 * Created by Kapmat on 2016-03-13.
 **/

package Lab3_1;

public class WrapperKey<T,K> {

	private T firstKey;
	private K secondKey;

	public WrapperKey(T firstKey, K secondKey) {
		this.firstKey = firstKey;
		this.secondKey = secondKey;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WrapperKey)) {
			return false;
		}
		WrapperKey wrapKey = (WrapperKey) o;
		return firstKey == wrapKey.firstKey && secondKey == wrapKey.secondKey;
	}

	@Override
	public int hashCode() {
		return firstKey.hashCode()*31 + secondKey.hashCode();
	}
}

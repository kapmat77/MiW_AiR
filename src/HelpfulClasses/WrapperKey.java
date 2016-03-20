/**
 * Created by Kapmat on 2016-03-13.
 **/

package HelpfulClasses;

public class WrapperKey<T,K> {

	private T firstKey;
	private K secondKey;

	public WrapperKey() {
		this.firstKey = null;
		this.secondKey = null;
	}

	public WrapperKey(T firstKey, K secondKey) {
		this.firstKey = firstKey;
		this.secondKey = secondKey;
	}

	public T getFirstKey() {
		return firstKey;
	}

	public K getSecondKey() {
		return secondKey;
	}

	public WrapperKey put(T firstKey, K secondKey) {
		this.firstKey = firstKey;
		this.secondKey = secondKey;
		return this;
	}

	public WrapperKey putFirst(T firstKey) {
		this.firstKey = firstKey;
		return this;
	}

	public WrapperKey putSecond( K secondKey) {
		this.secondKey = secondKey;
		return this;
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

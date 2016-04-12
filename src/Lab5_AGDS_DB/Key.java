/**
 * Created by Kapmat on 2016-04-12.
 **/

package Lab5_AGDS_DB;

public class Key {

	private String tableName;
	private String columnName;
	private KeyType type;
	private Key connectWith = null;
	private int index;

	enum KeyType {
		PRIMARY, FOREIGN
	}

	public Key(String tableName, String columnName, KeyType type, int index) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.type = type;
		this.index = index;
	}

	public Key(String tableName, String columnName, KeyType type, int index, Key connectWith) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.type = type;
		this.index = index;
		this.connectWith = connectWith;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public KeyType getKeyType() {
		return type;
	}

	public void setKeyType(KeyType type) {
		this.type = type;
	}

	public Key getConnectWith() {
		return connectWith;
	}

	public void setConnectWith(Key connectWith) {
		this.connectWith = connectWith;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isPrimary() {
		return this.type.equals(KeyType.PRIMARY);
	}
}

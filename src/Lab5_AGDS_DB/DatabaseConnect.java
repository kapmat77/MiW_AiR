/**
 * Created by Kapmat on 2016-04-11.
 **/
package Lab5_AGDS_DB;
import java.sql.*;
import java.util.*;

public class DatabaseConnect {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://mysql.agh.edu.pl:3306/kapmat";
	private static final String USR = "kapmat";
	private static final String PASSWORD = "kapmatphp";
	private static final Map<String, Map<String, ColumnType>> tableMap =  new HashMap<>();
	private static final List<String> PRIMARY_KEYS = new ArrayList<>();
	private static final List<String> FOREIGN_KEYS = new ArrayList<>();
	private static final List<Key> KEYS = new ArrayList<>();
	private static final List<Node> INDEXES = new ArrayList<>();


	public DatabaseConnect() {
		Connection connection = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);

			//Open a connection
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL,USR,PASSWORD);

			//Obtain meta data instance
			DatabaseMetaData databaseMetaData = connection.getMetaData();

			String dbName = null;
			String schemaPattern = null;
			String tableName = null;
			String[] types = null;

			ResultSet resultTable = databaseMetaData.getTables(dbName, schemaPattern, tableName, types);

			ResultSet resultSchema = databaseMetaData.getSchemas();

			String columnNamePattern = null;
			ColumnType columnType = null;

			//Root list
			List<Node> rootList = new ArrayList<>();
			List<Node> fKeyList = new ArrayList<>();

			while(resultTable.next()) {
				tableName = resultTable.getString(3);
				readPrimaryKeys(databaseMetaData, tableName);
			}

			tableName = null;
			resultTable = databaseMetaData.getTables(dbName, schemaPattern, tableName, types);

			//Create root list
			while(resultTable.next()) {
				tableName = resultTable.getString(3);
				setConnectionsBetweenKeys(databaseMetaData, tableName, connection.getCatalog());

				ResultSet resultColumn = databaseMetaData.getColumns(dbName, schemaPattern, tableName, columnNamePattern);
				Node columnName;
				while (resultColumn.next()) {
					boolean isPrimary = false;
					for (Key key : KEYS) {
						if (key.getColumnName().equals(resultColumn.getString(4)) && key.getTableName().equals(resultTable.getString(3))) {
							isPrimary = true;
							columnName = new Node(Node.Level.COLUMN, resultColumn.getString(4));
							if (key.getKeyType().equals(Key.KeyType.FOREIGN)) {
								fKeyList.add(columnName);
							}
						}
					}
					if (!isPrimary) {
						columnName = new Node(Node.Level.COLUMN, resultColumn.getString(4));
						rootList.add(columnName);
					}
				}
			}

			tableName = null;
			resultTable = databaseMetaData.getTables(dbName, schemaPattern, tableName, types);

			//Create AGDS without connection between data from different tables
			while(resultTable.next()) {
				tableName = resultTable.getString(3);
				stmt = connection.createStatement();
				String sql = "SELECT * FROM " + tableName;
				ResultSet rs = stmt.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();

				while (rs.next()) {
					//TODO Sprawdzenie czy 1 kolumna to primaty key
					String indexName = tableName + rs.getString(1);
					Node nodeIndex = new Node(Node.Level.INDEX, indexName);
					INDEXES.add(nodeIndex);

					for (int i = 2; i < rsmd.getColumnCount() + 1; i++) {
						List<Node> indexParents = new ArrayList<>();
						List<Node> childrenColumn = new ArrayList<>();
						List<Node> parentValue = new ArrayList<>();
						List<Node> valuesChildren = new ArrayList<>();
						List<Node> indexChildren = new ArrayList<>();
						for (Node node : rootList) {
							if (node.getValue().equals(rsmd.getColumnLabel(i))) {
								String newValue = rs.getString(rsmd.getColumnLabel(i));
								Node newNode = new Node(Node.Level.VALUE, newValue);

								//Check if node-column children contain new value
								if (!(node.getChildren() == null)) {
									for (Node children : (List<Node>) node.getChildren()) {
										if (((Node) children).getValue().equals(newValue)) {
											newNode = children;
											break;
										}
									}
								}
								//Set column children
								childrenColumn.add(newNode);
								node.setOrExtendChildren(childrenColumn);

								//Set values parents
								parentValue.add(node);
								newNode.setOrExtendParents(parentValue);

								//Set index parents
								indexParents.add(newNode);
								nodeIndex.setOrExtendParents(indexParents);

								//Set values children
								valuesChildren.add(nodeIndex);
								newNode.setOrExtendChildren(valuesChildren);

								//Set index children

							}
						}
						boolean endLoop = false;
						for (Node node: fKeyList) {
							if (node.getValue().equals(rsmd.getColumnLabel(i))) {
								String newValue = rs.getString(rsmd.getColumnLabel(i));
								for (Key key: KEYS) {
									if (key.getTableName().equals(tableName) && key.getColumnName().equals(rsmd.getColumnLabel(i)) && !endLoop) {
//										Node newNode = new Node(Node.Level.VALUE, key.getConnectWith().getTableName()+newValue);
										for (Node indexNode: INDEXES) {
											if (indexNode.getValue().equals(key.getConnectWith().getTableName()+newValue)) {
												indexParents.add(indexNode);
												nodeIndex.setOrExtendParents(indexParents);
												indexChildren.add(nodeIndex);
												indexNode.setOrExtendChildren(indexChildren);
												endLoop = true;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
//			//Clean-up environment
//			rs.close();
//			stmt.close();
			connection.close();

		} catch (ClassNotFoundException|SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void readPrimaryKeys(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
		String catalog = null;
		String schema = null;

		ResultSet resultPrKeys = databaseMetaData.getPrimaryKeys(catalog, schema, tableName);

		while(resultPrKeys.next()){
			KEYS.add(new Key(tableName, resultPrKeys.getString(4), Key.KeyType.PRIMARY, KEYS.size()));
		}
	}

//	private void readForeignKeys(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
//		String catalog = null;
//		String schema = null;
//		ResultSet resultForKeys = databaseMetaData.getExportedKeys(catalog, schema, tableName);
//
//		while(resultForKeys.next()){
//			System.out.println(resultForKeys.getString(4));
//			KEYS.add(new Key(tableName, resultForKeys.getString(4), Key.KeyType.FOREIGN, KEYS.size()));
//		}
//	}

	private void setConnectionsBetweenKeys(DatabaseMetaData databaseMetaData, String tableName, String catalog) throws SQLException {
		ResultSet connectedKeys = databaseMetaData.getImportedKeys(catalog, null, tableName);
		while (connectedKeys.next()) {
			String fkTableName = connectedKeys.getString("FKTABLE_NAME");
			String fkColumnName = connectedKeys.getString("FKCOLUMN_NAME");
			String pkTableName = connectedKeys.getString("PKTABLE_NAME");
			String pkColumnName = connectedKeys.getString("PKCOLUMN_NAME");

			Key primaryKey = null;
			for(Key key: KEYS) {
				if (key.getTableName().equals(pkTableName) && key.getColumnName().equals(pkColumnName)) {
					primaryKey = key;
				}
			}

			boolean exist = false;
			for(Key key: KEYS) {
				if (key.getTableName().equals(fkTableName) && key.getColumnName().equals(fkColumnName)) {
					exist = true;
				}
			}

			if(!exist) {
				KEYS.add(new Key(fkTableName, fkColumnName, Key.KeyType.FOREIGN, KEYS.size(), primaryKey));
				primaryKey.setConnectWith(KEYS.get(KEYS.size()-1));
			}

			System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);
		}
	}

	private enum ColumnType {
		STRING, INTEGER, BIT, FLOAT, DOUBLE
	}
}

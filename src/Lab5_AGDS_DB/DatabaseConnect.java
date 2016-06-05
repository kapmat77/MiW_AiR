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
	private static final List<Key> KEYS = new ArrayList<>();
	public static final List<Node> INDEXES = new ArrayList<>();
	public static final List<Node> MAIN_INDEXES = new ArrayList<>();
	public static final String MAIN_TABLE = "Studenci";

	public List<Node> rootList = new ArrayList<>();
	private List<Node> fKeyList = new ArrayList<>();

	private Connection connection;
	private Statement st;

	/**
	 * Constructor load data from DB and create AGDS
	 */
	public DatabaseConnect() {
//		Connection connection = null;
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

			String columnNamePattern = null;

			while(resultTable.next()) {
				tableName = resultTable.getString(3);
				//TODO tymczasowo
				if (!tableName.equals("USER_DATA")) {
					readPrimaryKeys(databaseMetaData, tableName);
				}
			}

			tableName = null;
			resultTable = databaseMetaData.getTables(dbName, schemaPattern, tableName, types);

			//Create root list
			createRoot(resultTable, databaseMetaData, dbName, schemaPattern, columnNamePattern, connection);

			tableName = null;
			resultTable = databaseMetaData.getTables(dbName, schemaPattern, tableName, types);

			//Create AGDS
			createAGDS(resultTable, connection, stmt, tableName);

//			//Clean-up environment
			resultTable.close();
//			stmt.close();


		} catch (ClassNotFoundException|SQLException ex) {
			ex.printStackTrace();
		}
	}

	public void closeConnections() throws SQLException {
		connection.close();
	}

	public void findStudentInDB(String inputAttribute, List<String> inputValuesList) throws SQLException {
		String sql = "";
//		if (inputAttributesList.size()==1) {
			sql = "SELECT " + inputAttribute + " FROM Studenci WHERE " + inputAttribute  + "='" + inputValuesList.get(0)+"';";
//		} else if (inputAttributesList.size()==2) {
////			sql = "SELECT * FROM Studenci WHERE " + inputAttributesList.get(0) + "='" + inputValuesList.get(0) + "' " +
////					"AND " + inputAttributesList.get(1) + "='" + inputValuesList.get(1)+"';";
//		}

		ResultSet rs = connection.createStatement().executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
	}

	/**
	 * Create AGDS from DB
	 */
	private void createAGDS(ResultSet resultTable, Connection connection, Statement stmt, String tableName) throws SQLException {
		while(resultTable.next()) {
			tableName = resultTable.getString(3);
			if (!tableName.equals("USER_DATA")) {
				stmt = connection.createStatement();
				String sql = "SELECT * FROM " + tableName;
				ResultSet rs = stmt.executeQuery(sql);
				ResultSetMetaData rsmd = rs.getMetaData();

				while (rs.next()) {
					//TODO Sprawdzenie czy 1 kolumna to primaty key
					String indexName = tableName + rs.getString(1);
					Node nodeIndex = new Node(Node.Level.INDEX, indexName);
					INDEXES.add(nodeIndex);
					if (tableName.equals(MAIN_TABLE)) {
						MAIN_INDEXES .add(nodeIndex);
					}

					for (int i = 2; i < rsmd.getColumnCount() + 1; i++) {
						createPartPrimitiveAGDS(rsmd.getColumnLabel(i), rs, nodeIndex);
						connectIndexNodes(rsmd.getColumnLabel(i), rs, tableName, nodeIndex);
					}
				}
			}
		}
	}

	/**
	 * Create root with all main column
	 */
	private void createRoot(ResultSet resultTable, DatabaseMetaData databaseMetaData, String dbName, String schemaPattern, String columnNamePattern, Connection connection) throws SQLException {
		String tableName;
		while(resultTable.next()) {
			tableName = resultTable.getString(3);
			if (!tableName.equals("USER_DATA")) {
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
		}
	}

	/**
	 * Create a part of AGDS without connection between data from different tables
	 */
	private void createPartPrimitiveAGDS(String columnLabel, ResultSet rs, Node nodeIndex) throws SQLException {
		List<Node> indexParents = new ArrayList<>();
		List<Node> childrenColumn = new ArrayList<>();
		List<Node> parentValue = new ArrayList<>();
		List<Node> valuesChildren = new ArrayList<>();

		for (Node node : rootList) {
			if (node.getValue().equals(columnLabel)) {
				String newValue = rs.getString(columnLabel);
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
			}
		}
	}

	/**
	 * Connect single index node to another index node from different graph
	 */
	private void connectIndexNodes(String columnLabel, ResultSet rs, String tableName, Node nodeIndex) throws SQLException {
		boolean endLoop = false;
		List<Node> indexParents = new ArrayList<>();
		List<Node> indexChildren = new ArrayList<>();
		for (Node node: fKeyList) {
			if (node.getValue().equals(columnLabel)) {
				String newValue = rs.getString(columnLabel);
				for (Key key: KEYS) {
					if (key.getTableName().equals(tableName) && key.getColumnName().equals(columnLabel) && !endLoop) {
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

	/**
	 * Read primary keys from DB and put them to KEYS
	 */
	private void readPrimaryKeys(DatabaseMetaData databaseMetaData, String tableName) throws SQLException {
		String catalog = null;
		String schema = null;

		ResultSet resultPrKeys = databaseMetaData.getPrimaryKeys(catalog, schema, tableName);

		while(resultPrKeys.next()){
			KEYS.add(new Key(tableName, resultPrKeys.getString(4), Key.KeyType.PRIMARY, KEYS.size()));
		}
	}

	/**
	 * Set connections between primary keys and foreign keys
	 */
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

//			System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);
		}
	}

	/**
	 * Columny type
	 */
	private enum ColumnType {
		STRING, INTEGER, BIT, FLOAT, DOUBLE
	}
}

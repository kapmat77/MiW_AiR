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

	private static final String TABLE = "Studenci";

	public static void main(String[] args) {
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

			String columnNamePattern = null;
			ColumnType columnType = null;





			//Root list
			List<Node> rootList = new ArrayList<>();

			//Create AGDS from DB
			while(resultTable.next()) {
				tableName = resultTable.getString(3);

				DatabaseMetaData metaData = connection.getMetaData();
				ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
				while (foreignKeys.next()) {
					String fkTableName = foreignKeys.getString("FKTABLE_NAME");
					String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
					String pkTableName = foreignKeys.getString("PKTABLE_NAME");
					String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
					System.out.println(fkTableName + "." + fkColumnName + " -> " + pkTableName + "." + pkColumnName);
				}

				//Get primary/foreign(exported) key
				String   catalog   = null;
				String   schema    = null;

				ResultSet result = databaseMetaData.getPrimaryKeys(
						catalog, schema, tableName);

				while(result.next()){
					String columnName = result.getString(4);
//					System.out.println(columnName);
				}

				ResultSet resultColumn = databaseMetaData.getColumns(dbName, schemaPattern, tableName, columnNamePattern);
				Map<String, ColumnType> columnNames = new HashMap<>();
				while (resultColumn.next()) {
					switch (resultColumn.getInt(5)) {
						case -1:
							columnType = ColumnType.STRING;
							break;
						case -7:
							columnType = ColumnType.BIT;
							break;
						case 4:
							columnType = ColumnType.INTEGER;
							break;
						case 6:
							columnType = ColumnType.FLOAT;
							break;
						case 8:
							columnType = ColumnType.DOUBLE;
							break;
						default:
							columnType = ColumnType.STRING;
							break;
					}
					columnNames.put(resultColumn.getString(4), columnType);
				}
				tableMap.put(tableName, columnNames);
			}



//			System.out.println("Creating statement...");
//			stmt = conn.createStatement();
//			String sql = "SELECT * FROM " + TABLE;
//			ResultSet rs = stmt.executeQuery(sql);
//			ResultSetMetaData rsmd = rs.getMetaData();
//			System.out.println("Ilość kolumn: " + rsmd.getColumnCount());
//
//			//Extract data from result set
//			while(rs.next()){
//				//Retrieve by column name
//				int id  = rs.getInt("ID");
//				String address = rs.getString("IP_ADDRESS");
//
//				//Display values
//				System.out.print("ID: " + id + "\n");
//				System.out.print("IP_ADDRESS: " + address + "\n\n");
//
//			}
//
//			//Clean-up environment
//			rs.close();
//			stmt.close();
			connection.close();

		} catch (ClassNotFoundException|SQLException ex) {
			ex.printStackTrace();
		}
	}

	private enum ColumnType {
		STRING, INTEGER, BIT, FLOAT, DOUBLE
	}
}

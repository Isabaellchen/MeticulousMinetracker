package rocks.isor.meticulousminetracker.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class Database {

	// Inhibit instantiation
	private Database() {}

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://localhost:3306/meticulous_minedata");

		config.setUsername("meticulous_minedata");
		config.setPassword("FMa=h+yT5TxL-Ufj");

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("useServerPrepStmts", "true");
		config.addDataSourceProperty("useLocalSessionState", "true");
		config.addDataSourceProperty("rewriteBatchedStatements", "true");
		config.addDataSourceProperty("cacheResultSetMetadata", "true");
		config.addDataSourceProperty("cacheServerConfiguration", "true");
		config.addDataSourceProperty("elideSetAutoCommits", "true");
		config.addDataSourceProperty("maintainTimeStats", "false");

		ds = new HikariDataSource(config);
	}

	private static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public static List<HashMap<String, Object>> executeQuery(String query, Object... args) {
		List<HashMap<String, Object>> result = new ArrayList<>();

		try (Connection connection = Database.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			int index = 0;
			for (Object argument : args) {
				preparedStatement.setObject(++index, argument);
			}

			boolean hasResultSet = preparedStatement.execute();

			int count = 0;
			do {
				HashMap<String, Object> resultData = new HashMap<>();

				if (hasResultSet) {
					try (ResultSet resultSet = preparedStatement.getResultSet()) {

						ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
						int columnCount = resultSetMetaData.getColumnCount();

						while (resultSet.next()) {
							for (int i = 1; i <= columnCount; i++) {
								resultData.put(
										resultSetMetaData.getColumnName(i),
										resultSet.getObject(i)
								);
							}
						}
					}
				} else {
					count = preparedStatement.getUpdateCount();
					if (count >= 0) {
						resultData.put(
								"rows affected",
								count
						);
					}
				}
				result.add(resultData);

				hasResultSet = preparedStatement.getMoreResults();
			} while (hasResultSet || count != -1);

		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error executing SQL", e);
		}

		return result;
	}

}

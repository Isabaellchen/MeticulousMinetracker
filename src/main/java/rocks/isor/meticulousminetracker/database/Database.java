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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Database {

	/**
	 * Inhibit instantiation
 	 */
	private Database() {}

	/**
	 * Connection pool
	 */
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource CONNECTION_POOL;

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

		CONNECTION_POOL = new HikariDataSource(config);
	}

	private static Connection getConnection() throws SQLException {
		return CONNECTION_POOL.getConnection();
	}


	/**
	 * An interface to wrap multiple actions in a function
	 */
	@FunctionalInterface
	public interface TransactionBracket {
		void execute(Connection connection) throws SQLException;
	}

	/**
	 * Wraps the callback in a transaction and commits it
	 * @param bracket An implementation of a function, basically a callback
	 */
	public static void wrapTransactional(TransactionBracket bracket) {
		try (Connection connection = getConnection()) {
			boolean initialAutoCommitStatus = connection.getAutoCommit();
			connection.setAutoCommit(false);

			bracket.execute(connection);

			connection.commit();
			connection.setAutoCommit(initialAutoCommitStatus);
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error during transaction ", e);
		}
	}


	public static List<Map<String, Object>> executeQuery(String query, Object... args) {
		try (Connection connection = getConnection()) {
			return executeQuery(connection, query, args);
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error in db connection ", e);
			return Collections.emptyList();
		}
	}
	/**
	 * A generic database querying method
	 * @param query The query to be executed
	 * @param args If the query has any question marks, these will be added in by the values provided here
	 * @return The returned resultset as a list of maps
	 */
	public static List<Map<String, Object>> executeQuery(Connection connection, String query, Object... args) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
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
								"rows_affected",
								count
						);
					}
				}
				result.add(resultData);

				hasResultSet = preparedStatement.getMoreResults();
			} while (hasResultSet || count != -1);

		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error executing SQL ", e);
			connection.rollback();
			throw e;
		}

		return result;
	}


	public static long executeInsertQuery(String query, Object... args) {
		try (Connection connection = getConnection()) {
			return executeInsertQuery(connection, query, args);
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error in db connection ", e);
			return 0;
		}
	}

	public static long executeInsertQuery(Connection connection, String query, Object... args) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			int index = 0;
			for (Object argument : args) {
				preparedStatement.setObject(++index, argument);
			}

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Could not insert.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new SQLException("Failed to obtain ID.");
				}
			}
		} catch (SQLException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Error executing SQL ", e);
			connection.rollback();
			throw e;
		}
	}
}

package rocks.isor.meticulousminetracker.database.api;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
	BasicDataSource ds = new BasicDataSource();

	default Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}

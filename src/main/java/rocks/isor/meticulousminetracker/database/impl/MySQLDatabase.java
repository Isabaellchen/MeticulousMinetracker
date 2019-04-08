package rocks.isor.meticulousminetracker.database.impl;
import org.bukkit.configuration.Configuration;
import rocks.isor.meticulousminetracker.database.api.Database;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MySQLDatabase implements Database {

	@Inject
	public MySQLDatabase(Configuration configuration) {
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(
				"jdbc:mysql://" +
				configuration.getString("db.host") +
				':' +
				configuration.getString("db.port") +
				'/' +
				configuration.getString("db.database")
		);

		ds.setUsername(configuration.getString("db.user"));
		ds.setPassword(configuration.getString("db.pass"));

		ds.setMinIdle(5);
		ds.setMaxIdle(10);
		ds.setMaxOpenPreparedStatements(100);
	}
}

package rocks.isor.meticulousminetracker.database;

import dagger.Module;
import dagger.Provides;
import rocks.isor.meticulousminetracker.database.api.Database;
import rocks.isor.meticulousminetracker.database.impl.MySQLDatabase;

import javax.inject.Named;

@Module
public class DatabaseModule {

	@Provides
	@Named("persistent")
	Database getDatabase(MySQLDatabase database) {
		return database;
	}

}

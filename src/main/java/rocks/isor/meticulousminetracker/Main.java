package rocks.isor.meticulousminetracker;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import rocks.isor.meticulousminetracker.database.api.Database;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class Main extends JavaPlugin  {

	@Inject
	@Named("persistent")
	public Database database;

	@Inject
	public Configuration configuration;

	@Inject
	public Set<Listener> listeners;

	@Override
	public void onEnable() {
		DaggerMainComponent.create().inject(this);

		String databaseName = configuration.getString("db.database");

		try (Connection connection = database.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement("USE " + databaseName + ';')) {
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}

		listeners.forEach(listener -> {
			getLogger().info("-" + listener.getClass().getSimpleName());
			getServer().getPluginManager().registerEvents(listener, this);
		});
	}

	@Override
	public void onDisable() {
		listeners.forEach(HandlerList::unregisterAll);
		listeners.clear();
	}
}

package rocks.isor.meticulousminetracker;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.database.Database;
import rocks.isor.meticulousminetracker.tasks.DatabaseCleanupTasks;

import javax.inject.Inject;
import java.util.Set;

public class Main extends JavaPlugin  {

	@Inject
	public Configuration configuration;

	@Inject
	public Set<Listener> listeners;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@Inject
	public DatabaseCleanupTasks databaseCleanupTasks;

	@Override
	public void onEnable() {
		DaggerMainComponent.create().inject(this);

		String databaseName = configuration.getString("db.database");
		Database.executeQuery("USE " + databaseName + ';');

		bukkitScheduler.runTaskTimer(this,
				databaseCleanupTasks.cleanUpTables(), 0L, 2000L);

		listeners.forEach(listener -> {
			getLogger().info("-" + listener.getClass().getSimpleName());
			getServer().getPluginManager().registerEvents(listener, this);
		});
	}

	@Override
	public void onDisable() {
		listeners.forEach(HandlerList::unregisterAll);
	}
}

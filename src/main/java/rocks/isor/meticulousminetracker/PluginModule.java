package rocks.isor.meticulousminetracker;

import dagger.Module;
import dagger.Provides;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import javax.inject.Singleton;

@Module
public class PluginModule {

	@Provides
	@Singleton
	Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("MeticulousMinetracker");
	}

	@Provides
	@Singleton
	Configuration getConfig(Plugin plugin) {
		return plugin.getConfig();
	}

	@Provides
	@Singleton
	BukkitScheduler getBukkitScheduler() {
		return Bukkit.getScheduler();
	}

}

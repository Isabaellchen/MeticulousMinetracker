package rocks.isor.meticulousminetracker;

import dagger.Module;
import dagger.Provides;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

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

}

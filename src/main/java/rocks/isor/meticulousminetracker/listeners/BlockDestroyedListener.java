package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.BlockDestroyedTasks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockDestroyedListener implements Listener {

	@Inject
	public BlockDestroyedListener() {}

	@Inject
	public BlockDestroyedTasks blockDestroyedTasks;

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@EventHandler
	public void on(BlockBreakEvent event) {
		bukkitScheduler.runTaskAsynchronously(plugin,
				blockDestroyedTasks.registerBlockDestroyed(event.getBlock()));
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void on(EntityExplodeEvent event) {
		bukkitScheduler.runTaskAsynchronously(plugin,
				blockDestroyedTasks.registerBlockDestroyedCollection(event.blockList()));
	}
}

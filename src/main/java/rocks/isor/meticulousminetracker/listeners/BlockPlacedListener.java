package rocks.isor.meticulousminetracker.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import rocks.isor.meticulousminetracker.tasks.BlockPlacedTasks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BlockPlacedListener implements Listener {

	@Inject
	public BlockPlacedListener() {}

	@Inject
	public Plugin plugin;

	@Inject
	public BukkitScheduler bukkitScheduler;

	@Inject
	public BlockPlacedTasks blockPlacedTasks;

	@EventHandler
	public void on(BlockPlaceEvent event) {
		bukkitScheduler.runTaskAsynchronously(plugin,
				blockPlacedTasks.registerBlockPlaced(event.getBlockPlaced()));
	}
}
